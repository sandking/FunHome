package com.tpadsz.home.view.action;

public abstract class Action implements Cloneable
{
	// ===========================================================
	// TODO Constants
	// ===========================================================
	public final static boolean TYPE_ORDER_POSITIVE = true;
	public final static boolean TYPE_ORDER_INVERSE = false;

	/**
	 * Play value is 0 .
	 */
	public final static int TYPE_SPEED_CONSTANT = 0;

	/**
	 * Play value is 1 .
	 */
	public final static int TYPE_SPEED_CHANGED = 1;

	/**
	 * Play value is 0x0000 .
	 */
	public final static int TYPE_LOOP_ENDLESS = 0;

	/**
	 * Play value is 0x0001 .
	 */
	public final static int TYPE_LOOP_ONCE = 0x0001;

	/**
	 * Play value is 0x0002 .
	 */
	public final static int TYPE_LOOP_TWICE = 0x0002;

	/**
	 * Play a animation , from a value to a value. Play value is 0.
	 */
	public final static int TYPE_PATTERN_TO = 0;

	/**
	 * Play a animation , value add a value . Play value is 1.
	 */
	public final static int TYPE_PATTERN_BY = 1;

	// ===========================================================
	// TODO Fields
	// ===========================================================
	private static int Key;

	public Type type;

	private int animationType;

	private float duration;

	private int acceleration;

	/**
	 * {@link #TYPE_SPEED_CONSTANT} , {@link #TYPE_ORDER_INVERSE}
	 */
	private boolean playOrder;

	/**
	 * {@link #TYPE_ORDER_POSITIVE} , {@link #TYPE_SPEED_CHANGED}
	 */
	private int playMode;

	/**
	 * {@link #TYPE_LOOP_ENDLESS} , {@link #TYPE_LOOP_ONCE} ,
	 * {@link #TYPE_LOOP_TWICE} .
	 */
	private int playLooper;

	/**
	 * {@link #TYPE_PATTERN_BY} , {@link #TYPE_PATTERN_TO}
	 */
	private int playType;

	private int key;


	// ===========================================================
	// TODO Constructors
	// ===========================================================
	Action(int animationType, float duration, int acceleration, boolean playOrder, int playMode, int playLooper, int playType)
	{
		this.animationType = animationType;
		this.duration = duration;
		this.acceleration = acceleration;
		this.playOrder = playOrder;
		this.playMode = playMode;
		this.playLooper = playLooper;
		this.playType = playType;
		this.type = getType();
		this.key = KEY();
	}

	// ===========================================================
	// TODO Getter & Setter
	// ===========================================================

	public int getAnimationType()
	{
		return animationType;
	}

	public float getDuration()
	{
		return duration;
	}

	public int getAcceleration()
	{
		return acceleration;
	}

	public boolean isPlayOrder()
	{
		return playOrder;
	}

	public void setPlayOrder(boolean order)
	{
		this.playOrder = order;
	}

	public int getPlayMode()
	{
		return playMode;
	}

	public int getPlayLooper()
	{
		return playLooper;
	}

	public int getPlayType()
	{
		return playType;
	}

	public int getKey()
	{
		return key;
	}

	// ===========================================================
	// TODO Methods
	// ===========================================================

	public static int KEY()
	{
		return Key++;
	}

	protected abstract Type getType();

	public Action cloneOpposeAction()
	{
		Action action = null;
		try
		{
			action = (Action) clone();
			action.key = KEY();
			action.playOrder = !playOrder;
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return action;
	}

	// ===========================================================
	// TODO Inner and Anonymous Classes
	// ===========================================================
	/**
	 * @see FRAME
	 * @see ROTATE
	 * @see SCALE
	 * @see TRANSLATE
	 * @see ALPHA
	 * @see UNKNOW
	 */
	public enum Type
	{
		FRAME, ROTATE, SCALE, TRANSLATE, ALPHA, UNKNOW
	}

}
