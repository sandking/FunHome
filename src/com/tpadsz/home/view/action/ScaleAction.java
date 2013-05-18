package com.tpadsz.home.view.action;

public class ScaleAction extends Action
{

	// ===========================================================
	// TODO Fields
	// ===========================================================
	private float xRate;
	private float yRate;
	private float x;
	private float y;

	// ===========================================================
	// TODO Constructors
	// ===========================================================
	public ScaleAction(float xRate, float yRate, float x, float y, int animationType, float duration, int acceleration, boolean playOrder, int playMode, int playLooper, int playType)
	{
		super(animationType, duration, acceleration, playOrder, playMode, playLooper, playType);
		this.xRate = xRate;
		this.yRate = yRate;
		this.x = x;
		this.y = y;
	}

	/**
	 * Scaled by the specified type.
	 * 
	 * @param xRate
	 * @param yRate
	 * @param animationType
	 * @param duration
	 * @param acceleration
	 * @param playType
	 * @param playOrder
	 */
	public ScaleAction(float xRate, float yRate, int animationType, float duration, int acceleration, boolean playOrder)
	{
		this(xRate, yRate, 0, 0, animationType, duration, acceleration, playOrder, TYPE_SPEED_CHANGED, TYPE_LOOP_ONCE, TYPE_PATTERN_TO);
	}

	/**
	 * Scaled by any point.
	 * 
	 * @param xRate
	 * @param yRate
	 * @param x
	 * @param y
	 * @param duration
	 * @param acceleration
	 * @param playType
	 * @param playOrder
	 */
	public ScaleAction(float xRate, float yRate, float x, float y, float duration, int acceleration,  boolean playOrder)
	{
		this(xRate, yRate, x, y, 6, duration, acceleration, playOrder, TYPE_SPEED_CHANGED, TYPE_LOOP_ONCE, TYPE_PATTERN_TO);
	}

	// ===========================================================
	// TODO Getter & Setter
	// ===========================================================

	public float getxRate()
	{
		return xRate;
	}

	public float getyRate()
	{
		return yRate;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	// ===========================================================
	// TODO Methods
	// ===========================================================
	@Override
	protected Type getType()
	{
		return Type.SCALE;
	}
	// ===========================================================
	// TODO Inner and Anonymous Classes
	// ===========================================================

}
