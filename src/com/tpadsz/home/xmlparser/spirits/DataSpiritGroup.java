package com.tpadsz.home.xmlparser.spirits;

public class DataSpiritGroup
{
	protected int mID; // The id of group.

	protected int[] mContents; // The reference of the spirits.

	public int getID()
	{
		return mID;
	}

	public void setID(int id)
	{
		this.mID = id;
	}

	public int[] getContents()
	{
		return mContents;
	}

	public void setContents(int[] contents)
	{
		this.mContents = contents;
	}

}
