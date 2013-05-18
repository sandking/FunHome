package com.tpadsz.home.jni.page;

public interface PageCondition
{
	/**
	 * Called when the page is created.<br>
	 * The spirits will be created in the method.
	 */
	void onCreate();

	/**
	 * Called when the page is started by pre-page. <br>
	 * The page is visible. <br>
	 * The into-animation start to play.
	 * 
	 * @param inComming
	 *            id of the pre-page.
	 */
	void onStart(int inComming);

	/**
	 * Called when the page is active. All the spirit can be touched.<br>
	 * The into-animation is played over.
	 * 
	 */
	void onResume();

	/**
	 * Called when the page is going to next-page . <br>
	 * The out-animation start to play.
	 * 
	 * @param outGoing
	 */
	void onPause(int outGoing);

	/**
	 * Called when the page is be over by next-page.<br>
	 * The page is invisible.<br>
	 * The out-animation is played over.<br>
	 * 
	 */
	void onStop();

	/**
	 * Called when this page be destroy.<br>
	 * All the spirits will be released.
	 */
	void onDestroy();
}
