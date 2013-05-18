package com.tpadsz.home.xmlparser.spirits;

public class DataSpirit
{
	protected int mID;
	protected String mName;
	protected String mSrc; // The path of the background resource.
	protected float mX;
	protected float mY;
	protected int mWidth;
	protected int mHeight;
	protected int mLayer;
	protected boolean mTouchable;
	protected boolean mVisible;

	public int getID()
	{
		return mID;
	}

	public void setID(int id)
	{
		this.mID = id;
	}

	public String getName()
	{
		return mName;
	}

	public void setName(String name)
	{
		this.mName = name;
	}

	public String getSrc()
	{
		return mSrc;
	}

	public void setSrc(String src)
	{
		this.mSrc = src;
	}

	public float getX()
	{
		return mX;
	}

	public void setX(float x)
	{
		this.mX = x;
	}

	public float getY()
	{
		return mY;
	}

	public void setY(float y)
	{
		this.mY = y;
	}

	public int getWidth()
	{
		return mWidth;
	}

	public void setWidth(int width)
	{
		this.mWidth = width;
	}

	public int getHeight()
	{
		return mHeight;
	}

	public void setHeight(int height)
	{
		this.mHeight = height;
	}

	public int getLayer()
	{
		return mLayer;
	}

	public void setLayer(int layer)
	{
		this.mLayer = layer;
	}

	public boolean isTouchable()
	{
		return mTouchable;
	}

	public void setTouchable(boolean touchable)
	{
		this.mTouchable = touchable;
	}

	public boolean isVisible()
	{
		return mVisible;
	}

	public void setVisible(boolean visible)
	{
		this.mVisible = visible;
	}

}
