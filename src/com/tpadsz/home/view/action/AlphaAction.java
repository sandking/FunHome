package com.tpadsz.home.view.action;

public class AlphaAction extends Action
{
	// ===========================================================
	// TODO Fields
	// ===========================================================
	private int alphaStart;
	private int alphaEnd;

	// ===========================================================
	// TODO Constructors
	// ===========================================================
	public AlphaAction(int alphaStart, int alphaEnd, int animationType, float duration, int acceleration, boolean playOrder, int playMode, int playLooper, int playType)
	{
		super(animationType, duration, acceleration, playOrder, playMode, playLooper, playType);
		this.alphaStart = alphaStart;
		this.alphaEnd = alphaEnd;
	}

	/**
	 * Play animation , PlayType is {@link Action#TYPE_PATTERN_TO}
	 * 
	 * @param from
	 * @param to
	 * @param duration
	 * @param acceleration
	 */
	public AlphaAction(int from, int to, float duration, int acceleration)
	{
		this(from, to, 1, duration, acceleration, TYPE_ORDER_POSITIVE, TYPE_SPEED_CHANGED, TYPE_LOOP_ONCE, TYPE_PATTERN_TO);
	}

	/**
	 * Play animation , PlayType is {@link Action#TYPE_PATTERN_BY}
	 * 
	 * @param by
	 * @param duration
	 * @param acceleration
	 */
	public AlphaAction(int by, float duration, int acceleration)
	{
		this(by, 0, 1, duration, acceleration, TYPE_ORDER_POSITIVE, TYPE_SPEED_CHANGED, TYPE_LOOP_ONCE, TYPE_PATTERN_BY);
	}

	// ===========================================================
	// TODO Getter & Setter
	// ===========================================================
	public int getAlphaStart()
	{
		return alphaStart;
	}

	public int getAlphaEnd()
	{
		return alphaEnd;
	}

	// ===========================================================
	// TODO Methods
	// ===========================================================
	@Override
	protected Type getType()
	{
		return Type.ALPHA;
	}
}
