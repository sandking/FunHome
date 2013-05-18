package com.tpadsz.home.view.action;

public class FrameAction extends Action
{

	private int number;
	private String BigBmpsrc;
	private String[] src;
	private float rate;

	public FrameAction(int number, String bgSrc, String[] src, float rate, int looper, boolean playOrder)
	{
		super(0, 0, 0, playOrder, 0, looper, 0);
		this.number = number;
		this.BigBmpsrc = bgSrc;
		this.src = src;
		this.rate = rate;
	}

	@Override
	protected Type getType()
	{
		return Type.FRAME;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public String getBigBmpsrc()
	{
		return BigBmpsrc;
	}

	public void setBigBmpsrc(String bigBmpsrc)
	{
		BigBmpsrc = bigBmpsrc;
	}

	public float getRate()
	{
		return rate;
	}

	public void setRate(float rate)
	{
		this.rate = rate;
	}

	public String[] getSrc()
	{
		return src;
	}

	public void setSrc(String[] src)
	{
		this.src = src;
	}

}
