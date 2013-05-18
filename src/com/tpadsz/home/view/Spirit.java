package com.tpadsz.home.view;

import java.nio.ByteBuffer;

import android.graphics.Bitmap;
import android.util.Log;
import com.tpadsz.home.jni.NativeInterface;
import com.tpadsz.home.view.action.Action;
import com.tpadsz.home.view.action.AlphaAction;
import com.tpadsz.home.view.action.Animation;
import com.tpadsz.home.view.action.FrameAction;
import com.tpadsz.home.view.action.RotateAction;
import com.tpadsz.home.view.action.ScaleAction;
import com.tpadsz.home.view.action.TranslateAction;
import com.tpadsz.home.view.helper.SpiritType;

public class Spirit implements SpiritType
{
	// ===========================================================
	// TODO Constant
	// ===========================================================
	public final static String TAG = Spirit.class.getSimpleName();
	public final static boolean _DEBUG_ = false;

	// ===========================================================
	// TODO Fields
	// ===========================================================
	// These parameter is the initial value.
	public int id;
	protected String name;
	protected String src;
	protected float x;
	protected float y;
	protected int width;
	protected int height;
	protected int layer;
	protected boolean touchable;
	protected boolean visible;

	private Type mType = Type.SPIRIT;
	private Bitmap bmp;

	// ===========================================================
	// TODO Constructors
	// ===========================================================
	public Spirit(int id, String name, String src, float x, float y, int width, int height, int layer, boolean touchable, boolean visible)
	{
		init(id, name, src, x, y, width, height, layer, touchable, visible);
		// TODO Before Engine V1.55
		NativeInterface.createSpriteFromSrc(obtainId(), src, x, y, width, height, layer, visible, touchable);

		// TODO After Engine V1.155
		// NativeInterface.createSpriteFromMemoryBuf(obtainId(), src, x, y,
		// width, height, layer, visible, touchable);
	}

	public Spirit(int mSprited, byte[] mData, int mLen, int width, int height,int widths,int heights, float x, float y, int mLayer, boolean isShown, boolean isTouchable, boolean isMoveable)
	{
		init(mSprited, name, src, x, y, width, height, mLayer, isTouchable, isMoveable);
		NativeInterface.createSpriteFromBuffer(obtainId(), mData, mLen, width, height,widths,heights, x, y, layer, visible, touchable, true);
	}

	public Spirit(int id, Bitmap bmp, int widths , int heights,float x, float y, int layer, boolean visible, boolean touchable)
	{
		if (bmp == null) throw new NullPointerException("Bitmap is null!!!");
		// get the bmp width and height
		this.bmp = bmp;
		width = bmp.getWidth();
		height = bmp.getHeight();
		int size = width * height * getBmpConfig(bmp);
		ByteBuffer byteBuffer = ByteBuffer.allocate(size);
		bmp.copyPixelsToBuffer(byteBuffer);
		byte[] buffer = byteBuffer.array();
		if (bmp != null && !bmp.isRecycled()) bmp.recycle();
		init(id, name, src, x, y, width, height, layer, touchable, visible);
		NativeInterface.createSpriteFromBuffer(obtainId(), buffer, size, width, height,widths,heights, x, y, layer, visible, touchable, true);
	}

	private void init(int id, String name, String src, float x, float y, int width, int height, int layer, boolean touchable, boolean visible)
	{
		this.id = id;
		this.name = name;
		this.src = src;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layer = layer;
		this.touchable = touchable;
		this.visible = visible;
		this.mType = getType();
	}

	private int obtainId()
	{
		return mType == Type.SPIRIT ? id : -1;
	}

	/**
	 * Default spirit , touchable & visible is false.
	 * 
	 * @param id
	 * @param bmp
	 * @param x
	 * @param y
	 * @param layer
	 */
	public Spirit(int id, Bitmap bmp, float x, float y, int layer)
	{
		this(id, bmp,bmp.getWidth(),bmp.getHeight(), x, y, layer, false, false);
	}

	// ===========================================================
	// TODO Getter & Setter
	// ===========================================================
	public String getName()
	{
		return name;
	}

	public Bitmap getContent()
	{
		return bmp;
	}

	public float getX()
	{
		return NativeInterface.getSpiritX(id);
	}

	public float getY()
	{
		return NativeInterface.getSpiritY(id);
	}

	public void setLayer(int layer)
	{
		NativeInterface.setLayer(id, layer);
	}

	public int getLayer()
	{
		return NativeInterface.getLayer(id);
	}

	public void setVisible(boolean visible)
	{
		NativeInterface.setSpriteVisible(id, visible);
	}

	public boolean getVisible()
	{
		return NativeInterface.getSpriteVisible(id);
	}

	public int getWidth()
	{
		return NativeInterface.getSpriteWidth(id);
	}

	public int getHeight()
	{
		return NativeInterface.getSpriteHeight(id);
	}

	public int getInitWidth()
	{
		return width;
	}

	public int getInitHeight()
	{
		return height;
	}

	public void setWidth(int mWidth)
	{
		NativeInterface.setSpriteWidth(id, mWidth);
	}

	public void setHeight(int mHeight)
	{
		NativeInterface.setSpriteHeight(id, mHeight);
	}

	public void setTouchable(boolean touchable)
	{
		NativeInterface.setSpriteTouchable(id, touchable);
	}

	public boolean getTouchable()
	{
		return NativeInterface.getSpriteTouchable(id);
	}

	public void setAlpha(int alpha)
	{
		NativeInterface.setOpacity(id, alpha);
	}

	public int getAlpha()
	{
		return NativeInterface.getOpacity(id);
	}

	public void setScale(float x, float y)
	{
		NativeInterface.setSpriteScale(id, x, y);
	}

	// ===========================================================
	// TODO Public Methods
	// ===========================================================

	public int playAction(Action action, String flowKey)
	{
		int animation_key = -1;
		if (action != null)
		{
			animation_key = action.getKey();
			int type = action.getAnimationType();
			int playMode = action.getPlayMode();
			float duration = action.getDuration();
			int looper = action.getPlayLooper();
			float acceleration = action.getAcceleration();
			int playType = action.getPlayType();
			boolean isNormal = action.isPlayOrder();
			int spiritType = getSpiritType();

			if (action instanceof AlphaAction)
			{
				AlphaAction newAction = (AlphaAction) action;
				NativeInterface.runAlpha(id, spiritType, type, newAction.getAlphaStart(), newAction.getAlphaEnd(), playMode, duration, looper, acceleration, playType, animation_key, flowKey, isNormal);
			}
			else if (action instanceof RotateAction)
			{
				// Log.e(TAG, "RotateAction : " + action.getAnimationType());
				RotateAction newAction = (RotateAction) action;
				NativeInterface.runRotate(id, spiritType, type, newAction.getStartX(), newAction.getStartY(), newAction.getStartZ(), newAction.getEndX(), newAction.getEndY(), newAction.getEndZ(), newAction.getDegree(), playMode, duration, looper, acceleration, playType, animation_key, flowKey, isNormal);
			}
			else if (action instanceof ScaleAction)
			{

				ScaleAction newAction = (ScaleAction) action;
				if (_DEBUG_)
				{
					String str = String.format("id[%d] type[%d] x_rate[%f] y_rate[%f]", id, type, newAction.getxRate(), newAction.getyRate());
					Log.e(TAG, str);
				}
				NativeInterface.runScale(id, spiritType, type, newAction.getxRate(), newAction.getyRate(), newAction.getX(), newAction.getY(), playMode, duration, looper, acceleration, playType, animation_key, flowKey, isNormal);
			}
			else if (action instanceof TranslateAction)
			{
				TranslateAction newAction = (TranslateAction) action;
				if (_DEBUG_)
				{
					String str = String.format("id[%d] played Translate{type[%d] , sx[%f] , sy[%f] , ex[%f] , ey[%f] , toorby[%d] , order[%s]}", id, type, newAction.getStartX(), newAction.getStartY(), newAction.getEndX(), newAction.getEndY(), playType, isNormal);
					Log.e(TAG, str);
				}
				NativeInterface.runMove(id, spiritType, type, newAction.getStartX(), newAction.getStartY(), newAction.getEndX(), newAction.getEndY(), playMode, duration, looper, acceleration, newAction.getMoveLine(), newAction.getLineNum(), playType, animation_key, flowKey, isNormal);
			}
			else if (action instanceof FrameAction)
			{
				FrameAction newAction = (FrameAction) action;

				if (_DEBUG_)
				{
					Log.e(TAG, String.format("id[%d] , spiritType[%d] , number[%d] , rate[%f] , looper[%d] , key[%d] , flowKey[%s] , isnormal[%s]", id, spiritType, newAction.getNumber(), newAction.getRate(), newAction.getPlayLooper(), animation_key, flowKey, isNormal));
					for (int i = 0; i < newAction.getSrc().length; i++)
					{
						Log.e(TAG, "src :" + newAction.getSrc()[i]);
					}
				}
				NativeInterface.runFrames(id, spiritType, newAction.getNumber(), newAction.getSrc(), newAction.getRate(), newAction.getPlayLooper(), animation_key, flowKey, isNormal);
			}
		}
		return animation_key;
	}

	/**
	 * Played the specified action.
	 * 
	 * @param action
	 *            {@link Action}
	 * @return the key of action.
	 */
	public int playAction(Action action)
	{
		return playAction(action, null);
	}

	public void playWaves(float amplitude, float cycle_time, boolean isVertical, int gridNum, int key)
	{
		NativeInterface.runWaves2D(id, getSpiritType(), amplitude, cycle_time, isVertical, gridNum, key);
	}

	/**
	 * Played the defined animation.
	 * 
	 * @param animation
	 *            {@link Animation}
	 * @see #playAction(Action)
	 * @return the key of this action.
	 */
	public int playAnimation(Animation animation)
	{
		Action[] actions = animation.getActions();
		if (actions == null) return -1;
		int len = actions.length;
		for (int i = 0; i < len; i++)
		{
			Action action = actions[i];
			if (action == null) continue;
			playAction(action, action.equals(animation.getLastAction()) ? animation.getFlowKey() : null);
		}
		return animation.getKey();
	}

	public void stopAnimation()
	{
		NativeInterface.stopAnimation(id, 0, 0, 0);
	}

	public void setPosition(float x, float y)
	{
		NativeInterface.setSpritePostion(id, x, y);
	}

	public void release()
	{
		NativeInterface.destroySprite(id);
	}

	public boolean isTouchedIn(float x, float y)
	{
		return NativeInterface.spIsTouched(id, x, y);
	}

	public void moveTo(float x, float y)
	{
		setPosition(x, y);
	}

	public void moveBy(float distance_x, float distance_y)
	{
		setPosition(getX() + distance_x, getY() + distance_y);
	}

	public void setTexture(Bitmap bmp)
	{
		if (bmp == null) return;
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int size = bmpWidth * bmpHeight * getBmpConfig(bmp);
		ByteBuffer buffer = ByteBuffer.allocate(size);
		bmp.copyPixelsToBuffer(buffer);
		NativeInterface.setSpriteTexture(id, buffer.array(), size, bmpWidth, bmpHeight);
	}

	public void setTexture(byte[] fileBuffer)
	{
		NativeInterface.setSpriteTexture(id, fileBuffer, 0, 0, 0);
	}

	public void setTexture(String fileName)
	{
		NativeInterface.setSpriteTexture(id, fileName);
	}

	public void setFather(int fatherid)
	{
		NativeInterface.setFatherSprite(id, fatherid);
	}

	public void reMoveFather()
	{
		NativeInterface.removeFather(id);
	}

	public void setOnTouchListener(OnTouchListener mListener)
	{
		NativeInterface.setOnTouchListener(id, mListener);
	}

	public void setOnAnimationCallback(OnAnimationCallback mCallback)
	{
		NativeInterface.setAnimationCallback(id, mCallback);
	}

	public void setSpriteRotationZ(float degrees)
	{
		NativeInterface.setSpriteRotationZ(id, degrees);
	}

	public void setRotationX(float degrees)
	{
		NativeInterface.setSpriteRotationX(id, degrees);
	}

	public void setRotationY(float degrees)
	{
		NativeInterface.setSpriteRotationY(id, degrees);
	}

	public void setRotationXY(float x1, float y1, float x2, float y2, float degrees)
	{
		NativeInterface.setSpriteRotationXY(id, x1, y1, x2, y2, degrees);
	}

	// ===========================================================
	// TODO Private Method
	// ===========================================================
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

	protected int getSpiritType()
	{
		return mType.value();
	}

	protected int getBmpConfig(Bitmap bmp)
	{
		int num = 2;
		switch (bmp.getConfig())
		{
		case ALPHA_8:
			num = 1;
			break;
		case ARGB_4444:
			num = 2;
			break;
		case RGB_565:
			num = 2;
			break;
		case ARGB_8888:
			num = 4;
			break;
		default:
			throw new RuntimeException("Unknow bmp config!!!");
		}
		return num;
	}

	// ===========================================================
	// TODO Inner and Anonymous Classes
	// ===========================================================
	/**
	 * Interface definition for a call back to be invoked when a animation's
	 * state is changed.
	 * 
	 */
	public interface OnAnimationCallback
	{
		/**
		 * Called when played begin
		 * 
		 * @param mAnimation
		 */
		public void onPlayedBegin(int mSpriteId, int mAnimationId, String flowKey, int key);

		/**
		 * Called when played end
		 * 
		 * @param mAnimation
		 */
		public void onPlayedEnd(int mSpriteId, int mAnimationId, String flowKey, int key);

		/**
		 * Called when played error
		 * 
		 * @param mAnimation
		 */
		public void onPlayedError(int mSpriteId, int mAnimationId, String flowKey, int key);
	}

	/**
	 * Interface definition for a call back to be invoked when a touch event is
	 * dispatched to this spirit.
	 */
	public interface OnTouchListener
	{
		/**
		 * Call back when a touch event is dispatched to a spirit.
		 * 
		 * @param spiritid
		 *            SpiritId of the spirit that was clicked.
		 */
		public void onTouch(int spiritid, float x, float y, int action);
	}

	@Override
	public Type getType()
	{
		return Type.SPIRIT;
	}

}
