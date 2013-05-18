package com.tpadsz.home.view.action;

public class RotateAction extends Action
{

	// ===========================================================
	// TODO Fields
	// ===========================================================
	private float startX;
	private float startY;
	private float startZ;
	private float endX;
	private float endY;
	private float endZ;
	private float degree;

	// ===========================================================
	// TODO Constructors
	// ===========================================================
	public RotateAction(float x1, float y1, float z1, float x2, float y2, float z2, float degree, int animationType, float duration, int acceleration, boolean playOrder, int playMode, int playLooper, int playType)
	{
		super(animationType, duration, acceleration, playOrder, playMode, playLooper, playType);
		this.startX = x1;
		this.startY = y1;
		this.startZ = z1;
		this.endX = x2;
		this.endY = y2;
		this.endZ = z2;
		this.degree = degree;
	}

	public RotateAction(Axis axis, float degree, float duration, int acceleration, boolean playOrder)
	{
		this(0, 0, 0, 0, 0, 0, degree, axis.value(), duration, acceleration, playOrder, TYPE_SPEED_CHANGED, TYPE_LOOP_ONCE, TYPE_PATTERN_TO);
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

	public float getStartZ()
	{
		return startZ;
	}

	public float getEndX()
	{
		return endX;
	}

	public float getEndY()
	{
		return endY;
	}

	public float getEndZ()
	{
		return endZ;
	}

	public float getDegree()
	{
		return degree;
	}

	// ===========================================================
	// TODO Methods
	// ===========================================================
	@Override
	protected Type getType()
	{
		return Type.ROTATE;
	}

	// ===========================================================
	// TODO Inner and Anonymous Classes
	// ===========================================================

	public enum Axis
	{
		X(2), Y(3), Z(4);

		final int value;

		Axis(int value)
		{
			this.value = value;
		}

		public int value()
		{
			return value;
		}
	}
}
