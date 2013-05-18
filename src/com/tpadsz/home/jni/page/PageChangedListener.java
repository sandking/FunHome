package com.tpadsz.home.jni.page;

public interface PageChangedListener
{
	/**
	 * The into-animation is played over.
	 * 
	 * @param pageID
	 *            the id of the page where come from.
	 */
	void onPageInto(int pageID,int prePageID);

	/**
	 * The out-animation is played over.
	 * 
	 * @param pageID
	 *            the id of the page where out going.
	 */
	void onPageOut(int pageID,int nextPageID);
}
