package com.tpadsz.home.widget;

public interface Adapter
{
	/**
	 * Get the data at the specified position in the data set .
	 * 
	 * @param listViewId
	 *            id of the listView .
	 * @param position
	 *            The position of the item within the adapter's data set of the
	 *            item whose view we want.
	 * @return The data to display on the specified item .
	 */
	public boolean getData(int listid, int position);

}
