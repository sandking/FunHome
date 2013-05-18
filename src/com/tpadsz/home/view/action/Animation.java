package com.tpadsz.home.view.action;

public class Animation
{
	private int id;

	/**
	 * frameAction, alphaAction, rotateAction, scaleAction, translateAction
	 */
	private Action[] actions;

	private int key;

	private String flowKey;

	public Animation(int id, Action[] actions)
	{
		this.id = id;
		this.actions = actions;
		this.key = getLastKey(actions);
	}

	public void setPlayOrder(boolean common)
	{
		int count = actions.length;
		for (int i = 0; i < count; i++)
		{
			Action action = actions[i];
			if (action == null) continue;
			action.setPlayOrder(common);
		}
	}

	public String getFlowKey()
	{
		return flowKey;
	}

	public void setFlowKey(String flowKey)
	{
		this.flowKey = flowKey;
	}

	public int getKey()
	{
		return key;
	}

	public int getID()
	{
		return id;
	}

	public Action[] getActions()
	{
		return actions;
	}

	protected int getLastKey(Action... actions)
	{
		if (actions == null) return -1;
		int len = actions.length;
		int[] keys = new int[len];
		for (int i = 0; i < len; i++)
		{
			Action action = actions[i];
			if (action == null) continue;
			keys[i] = action.getKey();
		}
		return getMax(keys);
	}

	protected int getMax(int[] keys)
	{
		if (keys == null) return -1;
		int max = keys[0];
		for (int i = 0; i < keys.length; i++)
		{
			if (keys[i] > max)
			{
				max = keys[i];
			}
		}
		return max;
	}

	public Action getLastAction()
	{
		if (actions == null) return null;
		Action action = null;
		float mDuration = -1;
		int len = actions.length;
		for (int i = 0; i < len; i++)
		{
			if (actions[i] == null) continue;
			float duration = actions[i].getDuration();
			if (duration > mDuration)
			{
				mDuration = duration;
				action = actions[i];
			}
		}
		return action;
	}
}
