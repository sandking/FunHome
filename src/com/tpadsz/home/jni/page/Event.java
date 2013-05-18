package com.tpadsz.home.jni.page;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.tpadsz.home.DebugLog;
import com.tpadsz.home.view.Spirit.OnTouchListener;

public class Event implements OnTouchListener, OnGestureListener
{
	public final static String TAG = Event.class.getSimpleName();

	private GestureDetector mGestureDetector;
	private EventListener mEventListener;
	private int mTouchedSpiritId;

	public Event(Handler handler, EventListener listener)
	{
		if (handler == null || listener == null) DebugLog.LogNull(TAG, "Constructor Event  --->  Handler or EventListener is null!!");
		mEventListener = listener;
		mGestureDetector = new GestureDetector(this, handler);
	}

	@Override
	public void onTouch(int spiritid, float x, float y, int action)
	{
		mTouchedSpiritId = spiritid;
		mGestureDetector.onTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), action, x, y, 0));
		if (action == 1) mEventListener.onUp(mTouchedSpiritId, MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), action, x, y, 0));
	}

	@Override
	public boolean onDown(MotionEvent e)
	{
		return mEventListener.onDown(mTouchedSpiritId, e);
	}

	@Override
	public void onShowPress(MotionEvent e)
	{
		mEventListener.onShowPress(mTouchedSpiritId, e);
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		return mEventListener.onSingleTapUp(mTouchedSpiritId, e);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		return mEventListener.onScroll(mTouchedSpiritId, e1, e2, distanceX, distanceY);
	}

	@Override
	public void onLongPress(MotionEvent e)
	{
		mEventListener.onLongPress(mTouchedSpiritId, e);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		return mEventListener.onFling(mTouchedSpiritId, e1, e2, velocityX, velocityY);
	}

	public interface EventListener
	{
		public boolean onDown(int id, MotionEvent e);

		public void onUp(int id, MotionEvent e);

		public void onShowPress(int id, MotionEvent e);

		public boolean onSingleTapUp(int id, MotionEvent e);

		public boolean onScroll(int id, MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);

		public void onLongPress(int id, MotionEvent e);

		public boolean onFling(int id, MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);

	}

}
