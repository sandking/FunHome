package com.tpadsz.home.view.action;

public class TranslateAction extends Action
{

	// ===========================================================
	// TODO Fields
	// ===========================================================
	private float startX;

	private float startY;

	private float endX;

	private float endY;

	private float[] moveLine;

	private int lineNum;

	// ===========================================================
	// TODO Constructors
	// ===========================================================
	public TranslateAction(float x1, float y1, float x2, float y2, float[] moveLine, int lineNum, int animationType, float duration, int acceleration, boolean playOrder, int playMode, int playLooper, int playType)
	{
		super(animationType, duration, acceleration, playOrder, playMode, playLooper, playType);
		this.startX = x1;
		this.startY = y1;
		this.endX = x2;
		this.endY = y2;
		this.moveLine = moveLine;
		this.lineNum = lineNum;
	}

	/**
	 * Rectilinear motion , playType is {@link Action#TYPE_PATTERN_TO} .
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param duration
	 * @param acceleration
	 */
	public TranslateAction(float x1, float y1, float x2, float y2, float duration, int acceleration)
	{
		this(x1, y1, x2, y2, new float[0], 0, 1, duration, acceleration, TYPE_ORDER_POSITIVE, TYPE_SPEED_CHANGED, TYPE_LOOP_ONCE, TYPE_PATTERN_TO);
	}

	/**
	 * Rectilinear motion , playType is {@link Action#TYPE_PATTERN_BY} .
	 * 
	 * @param xBy
	 * @param yBy
	 * @param duration
	 * @param acceleration
	 */
	public TranslateAction(float xBy, float yBy, float duration, int acceleration)
	{
		this(0, 0, xBy, yBy, new float[0], 0, 1, duration, acceleration, TYPE_ORDER_POSITIVE, TYPE_SPEED_CHANGED, TYPE_LOOP_ONCE, TYPE_PATTERN_BY);
	}

	/**
	 * Curvilinear motion.
	 * 
	 * @param moveLine
	 * @param lineNum
	 * @param duration
	 * @param acceleration
	 */
	public TranslateAction(float[] moveLine, int lineNum, float duration, int acceleration)
	{
		this(0, 0, 0, 0, moveLine, lineNum, 2, duration, acceleration, TYPE_ORDER_POSITIVE, TYPE_SPEED_CHANGED, TYPE_LOOP_ONCE, TYPE_PATTERN_TO);
	}

	// ===========================================================
	// TODO Getter & Setter
	// ===========================================================
	public float getStartX()
	{
		return startX;
	}

	public float getStartY()
	{
		return startY;
	}

	public float getEndX()
	{
		return endX;
	}

	public float getEndY()
	{
		return endY;
	}

	public float[] getMoveLine()
	{
		return moveLine;
	}

	public int getLineNum()
	{
		return lineNum;
	}

	// ===========================================================
	// TODO Methods
	// ===========================================================
	@Override
	protected Type getType()
	{
		return Type.TRANSLATE;
	}
	// ===========================================================
	// TODO Inner and Anonymous Classes
	// ===========================================================

}
