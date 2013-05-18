package com.tpadsz.home.jni.page;

import java.util.HashMap;

import android.util.Log;

import com.tpadsz.home.DebugLog;
import com.tpadsz.home.view.Spirit;
import com.tpadsz.home.view.action.Animation;
import com.tpadsz.home.view.helper.XMLParserHelper;
import com.tpadsz.home.widget.SpiritGroup;
import com.tpadsz.home.xmlparser.data.DataAnimationFlow;
import com.tpadsz.home.xmlparser.data.DataPage;

public class AnimationFlow
{
	public final static String TAG = AnimationFlow.class.getSimpleName();
	private boolean _DEBUG_ = false;

	/**
	 * The state for scene .<br>{@link #FLOW_OUT }<br>{@link #FLOW_INTO }
	 */
	public enum State
	{
		FLOW_OUT, FLOW_INTO, FLOW_DEFAULT;
	}

	private BasePage mBasePage;
	private HashMap<Integer, HashMap<String, DataAnimationFlow>> intoAnimationFlow, outAnimationFlow;
	private HashMap<String, DataAnimationFlow> animationFlows;
	private int animationFlowCount;
	private int mCount;

	private AnimationFlowCallback mAnimationFlowCallback;
	private State mFlowState = State.FLOW_DEFAULT;

	public AnimationFlow(BasePage basePage, AnimationFlowCallback animationFlowCallback)
	{
		if (basePage == null || animationFlowCallback == null) DebugLog.LogNull(TAG, "Constructor AnimationFlow  --->  BasePage or AnimationFlowCallback is null!!");
		mBasePage = basePage;
		mAnimationFlowCallback = animationFlowCallback;
		XMLParserHelper xmlParserHelper = basePage.getParserHelper();
		DataPage mDataPage = xmlParserHelper.getPage(basePage.getPageID());
		intoAnimationFlow = mDataPage.getIntoAnimationFlow();
		outAnimationFlow = mDataPage.getOutAnimationFlow();
	}

	public void setDebug(boolean flag)
	{
		_DEBUG_ = flag;
	}

	/**
	 * Called when the animation which in animationFlow played over.
	 * 
	 * @param key
	 *            the key of the animationFlow
	 * 
	 * @see DataAnimationFlow#getNowIndex()
	 * @see DataAnimationFlow#getBeforeIndex()
	 */
	public void onAnimationCallback(String key)
	{
		if (key == null) return;
		mCount++;
		DebugLog.LogInfo(TAG, String.format("onAnimationCallback FlowKey[%s] , mCount[%d] , animationFlowCount[%d]", key, mCount, animationFlowCount));

		DataAnimationFlow mAnimationFlow = animationFlows.get(key);
		setVisible(mAnimationFlow, mAnimationFlow.isAfterAnimationVisible());
		if (mCount == animationFlowCount)
		{
			mAnimationFlowCallback.onAnimationFlowOver(mFlowState);
			mCount = 0;
		}
		else play(key);
	}

	/**
	 * Invoked by current page , when into scene start.
	 * 
	 * @param from
	 *            the id of the pre_page.
	 */
	public void intoAnimationStart(int from)
	{
		mFlowState = State.FLOW_INTO;
		animationFlows = intoAnimationFlow.get(from);
		if (animationFlows != null)
		{
			animationFlowCount = getFlowCount();
			initPlay();
		}
		else mAnimationFlowCallback.onAnimationFlowOver(mFlowState);
	}

	/**
	 * Invoked by current page , when out scene start.
	 * 
	 * @param to
	 *            the id of the next_page.
	 */
	public void outAnimationStart(int to)
	{
		mFlowState = State.FLOW_OUT;
		animationFlows = outAnimationFlow.get(to);
		if (animationFlows != null)
		{
			animationFlowCount = getFlowCount();
			initPlay();
		}
		else mAnimationFlowCallback.onAnimationFlowOver(mFlowState);
	}

	private int getFlowCount()
	{
		return animationFlows.size();
	}

	/**
	 * Exec every animationFlow.
	 * 
	 * @param key
	 */
	private void play(String key)
	{
		for (String flowKey : animationFlows.keySet())
		{
			DataAnimationFlow animationFlow = animationFlows.get(flowKey);
			if (animationFlow.getBeforeIndex().equals(key))
			{
				int spid = animationFlow.getSpiritId();
				int gpid = animationFlow.getGroupId();
				int animationId = animationFlow.getAnimationId();
				long delay = animationFlow.getDelayTime();
				boolean order = animationFlow.isAnimationOrder();
				DebugLog.LogInfo(TAG, String.format("AnimationFlow exec ( FlowKey[%s] , Spirit[%d] , Group[%d] , Animation[%d] , Order[%s] , Delay[%s] )	", flowKey, spid, gpid, animationId, order, delay));
				Animation animation = mBasePage.obtainAnimation(animationId, order, flowKey);
				if (spid > 0) mBasePage.playAnimation(spid, animation, delay);
				if (gpid > 0) mBasePage.playGroupAnimation(gpid, animation, delay);
			}
		}
	}

	private void initPlay()
	{
		play("0");
	}

	private void setVisible(DataAnimationFlow animationFlow, boolean visible)
	{
		SpiritGroup gp = mBasePage.findGroupByid(animationFlow.getGroupId());
		Spirit sp = mBasePage.findSpiritByid(animationFlow.getSpiritId());
		if (sp != null) sp.setVisible(visible);
		if (gp != null) gp.setVisible(visible);
	}

	public interface AnimationFlowCallback
	{
		/**
		 * Called when the animation-flow of (into | out) scene end.
		 * 
		 * @param state
		 *            the state of into or out.
		 * 
		 * @see State#FLOW_OUT
		 * @see State#FLOW_INTO
		 */
		void onAnimationFlowOver(State state);
	}

}
