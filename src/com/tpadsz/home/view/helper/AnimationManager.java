package com.tpadsz.home.view.helper;

import java.util.HashMap;

import android.util.Log;

import com.tpadsz.home.DebugLog;
import com.tpadsz.home.view.Program;
import com.tpadsz.home.view.action.Action;
import com.tpadsz.home.view.action.AlphaAction;
import com.tpadsz.home.view.action.Animation;
import com.tpadsz.home.view.action.FrameAction;
import com.tpadsz.home.view.action.RotateAction;
import com.tpadsz.home.view.action.ScaleAction;
import com.tpadsz.home.view.action.TranslateAction;
import com.tpadsz.home.xmlparser.animation.DataAlpha;
import com.tpadsz.home.xmlparser.animation.DataFrame;
import com.tpadsz.home.xmlparser.animation.DataMove;
import com.tpadsz.home.xmlparser.animation.DataRotate;
import com.tpadsz.home.xmlparser.animation.DataScale;
import com.tpadsz.home.xmlparser.data.DataAnimation;
import com.tpadsz.home.xmlparser.data.DataFrameFile;
import com.tpadsz.home.xmlparser.data.DataMoveline;

public class AnimationManager
{

	public final static String TAG = AnimationManager.class.getSimpleName();

	public HashMap<String, DataMoveline> DATA_MOVELINE_POOLS;
	public HashMap<String, DataFrameFile> DATA_FRAMEFILE_POOLS;

	private Program mProgram;
	private XMLParserHelper mHelper;
	private static AnimationManager manager;
	HashMap<Integer, Animation> MAPS_ID_ANIMATION = new HashMap<Integer, Animation>();

	private AnimationManager(Program program)
	{
		mProgram = program;
		mHelper = mProgram.getParserHelper();
		obtainData();
		// initAnimations();
	}

	public static AnimationManager getInstance(Program program)
	{
		if (manager == null) manager = new AnimationManager(program);
		return manager;
	}

	private void initAnimations()
	{
		for (int id : mHelper.DATA_ANIMATION_POOLS.keySet())
		{
			DataAnimation animation = mHelper.DATA_ANIMATION_POOLS.get(id);
			DataFrame dataFrame = mHelper.ANIMATION_FRAME_POOLS.get(animation.getFrameID());
			DataMove dataMove = mHelper.ANIMATION_MOVE_POOLS.get(animation.getMoveID());
			DataScale dataScale = mHelper.ANIMATION_SCALE_POOLS.get(animation.getZoomID());
			DataRotate dataRotate = mHelper.ANIMATION_ROTATE_POOLS.get(animation.getRotateID());
			DataAlpha dataAlpha = mHelper.ANIMATION_ALPHA_POOLS.get(animation.getAlphaID());

			FrameAction frameAction = dataFrame == null ? null : new FrameAction(dataFrame.getNum(), dataFrame.getSrc(), obtainFrameSrc(dataFrame.getFileName()), dataFrame.getRate(), dataFrame.getLoop(), true);
			AlphaAction alphaAction = dataAlpha == null ? null : new AlphaAction(dataAlpha.getAlphaStart(), dataAlpha.getAlphaEnd(), dataAlpha.getType(), dataAlpha.getDuration(), dataAlpha.getAlphaG(), true, dataAlpha.getSpeedType(), dataAlpha.getLoop(), dataAlpha.getToOrBy());
			RotateAction rotateAction = dataRotate == null ? null : new RotateAction(dataRotate.getStartX(), dataRotate.getStartY(), dataRotate.getStartZ(), dataRotate.getEndX(), dataRotate.getEndY(), dataRotate.getEndZ(), dataRotate.getAngle(), dataRotate.getType(), dataRotate.getDuration(), dataRotate.getRotateG(), true, dataRotate.getSpeedType(), dataRotate.getLoop(), dataRotate.getToOrBy());
			ScaleAction scaleAction = dataScale == null ? null : new ScaleAction(dataScale.getX_rate(), dataScale.getY_rate(), dataScale.getX(), dataScale.getY(), dataScale.getType(), dataScale.getDuration(), dataScale.getZoomG(), true, dataScale.getSpeed_type(), dataScale.getLoop(), dataScale.getToOrBy());
			TranslateAction translateAction = dataMove == null ? null : new TranslateAction(dataMove.getStart_X(), dataMove.getStart_Y(), dataMove.getEnd_X(), dataMove.getEnd_Y(), obtainMoveLine(dataMove.getMove_Line()), dataMove.getLine_Num(), dataMove.getType(), dataMove.getDuration(), dataMove.getMove_G(), true, dataMove.getSpeedType(), dataMove.getLoop(), dataMove.getTo_or_by());
			Action[] actions = new Action[] { frameAction, alphaAction, rotateAction, scaleAction, translateAction };
			MAPS_ID_ANIMATION.put(id, new Animation(id, actions));
		}
	}

	private void obtainData()
	{
		DATA_MOVELINE_POOLS = mHelper.DATA_MOVELINE_POOLS;
		DATA_FRAMEFILE_POOLS = mHelper.DATA_FRAMEFILE_POOLS;
	}

	private float[] obtainMoveLine(String str)
	{
		DataMoveline dataMoveline = DATA_MOVELINE_POOLS.get(str);
		float[] datas = dataMoveline == null ? null : dataMoveline.getDatas();
		return datas == null ? new float[0] : datas;
	}

	private String[] obtainFrameSrc(String str)
	{
		DataFrameFile dataFrameFile = DATA_FRAMEFILE_POOLS.get(str);
		String[] src = dataFrameFile == null ? null : dataFrameFile.getDatas().split(",");
		String[] newSrc = null;
		if (src != null)
		{
			int len = src.length;
			newSrc = new String[len];

			for (int i = 0; i < len; i++)
			{
				newSrc[i] = src[i].trim();
			}
		}
		return newSrc;
	}

	private Animation getAnimation(int id)
	{
		DataAnimation dataAnimation = mHelper.DATA_ANIMATION_POOLS.get(id);
		if (dataAnimation == null)
		{
			DebugLog.LogNull(TAG, String.format("DataAnimation %d  is null!!", id));
			return null;
		}
		DataFrame dataFrame = mHelper.ANIMATION_FRAME_POOLS.get(dataAnimation.getFrameID());
		DataMove dataMove = mHelper.ANIMATION_MOVE_POOLS.get(dataAnimation.getMoveID());
		DataScale dataScale = mHelper.ANIMATION_SCALE_POOLS.get(dataAnimation.getZoomID());
		DataRotate dataRotate = mHelper.ANIMATION_ROTATE_POOLS.get(dataAnimation.getRotateID());
		DataAlpha dataAlpha = mHelper.ANIMATION_ALPHA_POOLS.get(dataAnimation.getAlphaID());

		FrameAction frameAction = dataFrame == null ? null : new FrameAction(dataFrame.getNum(), dataFrame.getSrc(), obtainFrameSrc(dataFrame.getFileName()), dataFrame.getRate(), dataFrame.getLoop(), true);
		AlphaAction alphaAction = dataAlpha == null ? null : new AlphaAction(dataAlpha.getAlphaStart(), dataAlpha.getAlphaEnd(), dataAlpha.getType(), dataAlpha.getDuration(), dataAlpha.getAlphaG(), true, dataAlpha.getSpeedType(), dataAlpha.getLoop(), dataAlpha.getToOrBy());
		RotateAction rotateAction = dataRotate == null ? null : new RotateAction(dataRotate.getStartX(), dataRotate.getStartY(), dataRotate.getStartZ(), dataRotate.getEndX(), dataRotate.getEndY(), dataRotate.getEndZ(), dataRotate.getAngle(), dataRotate.getType(), dataRotate.getDuration(), dataRotate.getRotateG(), true, dataRotate.getSpeedType(), dataRotate.getLoop(), dataRotate.getToOrBy());
		ScaleAction scaleAction = dataScale == null ? null : new ScaleAction(dataScale.getX_rate(), dataScale.getY_rate(), dataScale.getX(), dataScale.getY(), dataScale.getType(), dataScale.getDuration(), dataScale.getZoomG(), true, dataScale.getSpeed_type(), dataScale.getLoop(), dataScale.getToOrBy());
		TranslateAction translateAction = dataMove == null ? null : new TranslateAction(dataMove.getStart_X(), dataMove.getStart_Y(), dataMove.getEnd_X(), dataMove.getEnd_Y(), obtainMoveLine(dataMove.getMove_Line()), dataMove.getLine_Num(), dataMove.getType(), dataMove.getDuration(), dataMove.getMove_G(), true, dataMove.getSpeedType(), dataMove.getLoop(), dataMove.getTo_or_by());
		Action[] actions = new Action[] { frameAction, alphaAction, rotateAction, scaleAction, translateAction };
		return new Animation(id, actions);
	}

	public Animation obtainAnimation(int id, boolean common, String flowKey)
	{
		Animation animation = getAnimation(id);
		if (animation != null)
		{
			animation.setFlowKey(flowKey);
			animation.setPlayOrder(common);
		}
		return animation;
	}

}
