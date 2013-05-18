package com.tpadsz.home.widget;

import com.tpadsz.home.jni.NativeInterface;

public abstract class BaseAdapter implements Adapter
{
	private int mListViewId;

	/** 
	 * Notifies the attached View that the underlying data has been changed and
	 * it should refresh itself.
	 */
	public void notifyDataSetChanged()
	{
		// TODO report the view to refresh itself.
		NativeInterface.notifyListViewLoadData(mListViewId);
	}

	/**
	 * Set the SpiritID
	 * 
	 * @param id
	 */
	void setID(int id)
	{
		this.mListViewId = id;
	}

	protected int getListViewID()
	{
		return this.mListViewId;
	}

}
