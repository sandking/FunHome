package com.tpadsz.home.xmlparser.spirits;

public class DataListView extends DataSpirit
{

	protected int mOrientation; // Orientation of list-view.
	protected int mItemTouchable;//
	protected int mSelection;
	protected int mItemWidth;
	protected int mItemHeight;
	protected int mScrollSpeed; // The speed of scroll.

	public int getOrientation()
	{
		return mOrientation;
	}

	public void setOrientation(int orientation)
	{
		this.mOrientation = orientation;
	}

	public int getItemTouchable()
	{
		return mItemTouchable;
	}

	public void setItemTouchable(int itemTouchable)
	{
		this.mItemTouchable = itemTouchable;
	}

	public int getSelection()
	{
		return mSelection;
	}

	public void setSelection(int selection)
	{
		this.mSelection = selection;
	}

	public int getItemWidth()
	{
		return mItemWidth;
	}

	public void setItemWidth(int itemWidth)
	{
		this.mItemWidth = itemWidth;
	}

	public int getItemHeight()
	{
		return mItemHeight;
	}

	public void setItemHeight(int itemHeight)
	{
		this.mItemHeight = itemHeight;
	}

	public int getScrollSpeed()
	{
		return mScrollSpeed;
	}

	public void setScrollSpeed(int itemScrollSpeed)
	{
		this.mScrollSpeed = itemScrollSpeed;
	}

}
