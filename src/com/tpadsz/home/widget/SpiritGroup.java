package com.tpadsz.home.widget;

import com.tpadsz.home.DebugLog;
import com.tpadsz.home.view.Spirit;
import com.tpadsz.home.view.Spirit.OnAnimationCallback;
import com.tpadsz.home.view.action.Action;
import com.tpadsz.home.view.action.Animation;

public class SpiritGroup
{
	public final static String TAG = SpiritGroup.class.getSimpleName();

	private int id;
	private Spirit[] spirits;

	public SpiritGroup(int id, Spirit... spirits)
	{
		if (spirits == null) DebugLog.LogNull(TAG, String.format("Constructor SpiritGroup  ---> id %d , spirits is null", id));
		checkSpirits(spirits);
		this.id = id;
		this.spirits = spirits;
	}

	public int getId()
	{
		return id;
	}

	public Spirit[] getContents()
	{
		return spirits;
	}

	private void checkSpirits(Spirit[] spirits)
	{
		int len = spirits.length;
		for (int i = 0; i < len; i++)
		{
			Spirit mSpirit = spirits[i];
			if (mSpirit == null) DebugLog.LogNull(TAG, "CheckSpirits  --->  spirit is null!!!");
		}
	}

	/**
	 * Play action for spirits in this group.
	 * 
	 * @param action
	 * @return
	 */
	public int playAction(Action action)
	{
		if (action == null) DebugLog.LogNull(TAG, String.format("SpiritGroup %d played action is null", id));
		for (Spirit spirit : spirits)
			spirit.playAction(action);
		return action.getKey();
	}

	/**
	 * Play animation for spirits in this group.
	 * 
	 * @param animation
	 * @return
	 */
	public int playAnimation(Animation animation)
	{
		if (animation == null) DebugLog.LogNull(TAG, String.format("SpiritGroup %d played animation is null", id));
		final Animation animationFlowKey = new Animation(animation.getID(), animation.getActions());
		int count = spirits.length;
		for (int i = 0; i < count; i++)
			spirits[i].playAnimation(i == 0 ? animation : animationFlowKey);
		return animation.getKey();
	}

	/**
	 * Move spirits which in this group .
	 * 
	 * @param x_distance
	 * @param y_distance
	 */
	public void moveBy(float x_distance, float y_distance)
	{
		for (Spirit spirit : spirits)
			spirit.moveBy(x_distance, y_distance);
	}

	/**
	 * Set the angle for spirits in this group.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param degrees
	 */
	public void setGroupRotationXY(float x1, float y1, float x2, float y2, float degrees)
	{
		for (Spirit spirit : spirits)
			spirit.setRotationXY(x1, y1, x2, y2, degrees);
	}

	/**
	 * Set visible for spirits in this group.
	 * 
	 * @param visible
	 */
	public void setVisible(boolean visible)
	{
		for (Spirit spirit : spirits)
			spirit.setVisible(visible);
	}

	/**
	 * Set the animationCallback for spirits in this group.
	 * 
	 * @param animationCallback
	 */
	public void setAnimationCallback(OnAnimationCallback animationCallback)
	{
		for (Spirit spirit : spirits)
			spirit.setOnAnimationCallback(animationCallback);
	}
}
