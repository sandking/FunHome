package com.tpadsz.home.xmlparser.spirits;

/**
 * Used to show the text and scroll the text.
 */
public class DataScrollbar extends DataSpirit
{
	protected int mOrientation; // The orientation of the scroll bar.

	protected float mStartX; //

	protected float mStartY; //

	protected int mScrollSpeed; //

	public int getOrientation()
	{
		return mOrientation;
	}

	public void setOrientation(int orientation)
	{
		this.mOrientation = orientation;
	}

	public float getStartX()
	{
		return mStartX;
	}

	public void setStartX(float startX)
	{
		this.mStartX = startX;
	}

	public float getStartY()
	{
		return mStartY;
	}

	public void setStartY(float startY)
	{
		this.mStartY = startY;
	}

	public int getScrollSpeed()
	{
		return mScrollSpeed;
	}

	public void setScrollSpeed(int scrollSpeed)
	{
		this.mScrollSpeed = scrollSpeed;
	}

}
