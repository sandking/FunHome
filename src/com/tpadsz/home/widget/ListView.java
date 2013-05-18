package com.tpadsz.home.widget;

import java.nio.ByteBuffer;

import android.graphics.Bitmap;

import com.tpadsz.home.jni.NativeInterface;
import com.tpadsz.home.view.Spirit;
import com.tpadsz.home.view.action.Action;
import com.tpadsz.home.view.action.Animation;
import com.tpadsz.home.view.action.FrameAction;

public class ListView extends Spirit
{

	private Adapter mAdapter;
	private int mChildCount;
	private int mOrientation; // 1 - horizontal , 0 - vertical

	public ListView(int id, String name, String src, float x, float y, int width, int height, int layer, boolean touchable, boolean visible, int orientation, int item_touchable, int selection, int item_w, int item_h, int scrollspeed)
	{
		super(id, name, src, x, y, width, height, layer, touchable, visible);
		NativeInterface.createListView(id, x, y, width, height, layer, orientation);
		setCellMovable(item_touchable != 0);
		setStartIndex(selection);
	}

	public ListView(int id, float x, float y, int width, int height, int layer, int orientation)
	{
		super(id, "", "", x, y, width, height, layer, false, false);
		NativeInterface.createListView(id, x, y, width, height, layer, orientation);
	}

	@Override
	public Type getType()
	{
		return Type.LISTVIEW;
	}

	public boolean setCellBitmap(int index, byte[] mData, int mLen, int mWidth, int mHeight)
	{
		return NativeInterface.setCellBitmap(id, index, mData, mLen, mWidth, mHeight);
	}

	public boolean setCellBitmap(int index, Bitmap bmp)
	{
		if (bmp == null) return false;
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int size = width * height * 4;
		ByteBuffer buffer = ByteBuffer.allocate(size);
		bmp.copyPixelsToBuffer(buffer);
		return setCellBitmap(index, buffer.array(), size, width, height);
	}

	public boolean setCellBitmap(int index, String file)
	{
		return true;
	}

	public void setCellSize(int width, int height)
	{
		NativeInterface.setCellSize(id, width, height);
	}

	public void setCount(int count)
	{
		mChildCount = count < 0 ? 0 : count;
		NativeInterface.setListViewCount(id, mChildCount);
	}

	public int getFirstChildIndex()
	{
		return NativeInterface.getListViewStartIdx(id);
	}

	public boolean isEmpty()
	{
		return mChildCount == 0;
	}

	public int getOrientation()
	{
		return mOrientation;
	}

	/**
	 * Sets the data behind this ListView.
	 * 
	 * @param adapter
	 *            The ListAdapter which is responsible for maintaining the data
	 *            backing this list and for producing a view to represent an
	 *            item in that data set.
	 * 
	 * @see #getAdapter()
	 */
	public void setAdapter(BaseAdapter adapter)
	{
		this.mAdapter = adapter;
		adapter.setID(id);
		NativeInterface.setAdapter(id, adapter);
		NativeInterface.notifyListViewLoadData(id);
	}

	/**
	 * Returns the adapter currently in use in this ListView.
	 * 
	 * @return The adapter currently used to display data in this ListView.
	 * 
	 * @see #setAdapter(Adapter)
	 */
	public Adapter getAdater()
	{
		return mAdapter;
	}

	/**
	 * Register a call back to be invoked when an item in this listView has been
	 * clicked.
	 * 
	 * @param listener
	 *            The call back that will be invoked.
	 */
	public void setOnItemClickListener(OnItemClickListener listener)
	{
		NativeInterface.setOnItemClickListener(id, listener);
	}

	/**
	 * Smoothly scroll by distance pixels over duration milliseconds.
	 * 
	 * @param distance
	 *            Distance to scroll in pixels.
	 * @param duration
	 *            Duration of the scroll animation in milliseconds.
	 */
	public void smoothScrollBy(float distance, int duration)
	{
		NativeInterface.scrollListView(id, duration, distance);
	}

	/**
	 * Scroll by distance pixels
	 * 
	 * @param distance
	 *            Distance to scroll in pixels.
	 */
	public void scrollBy(int distance)
	{
		NativeInterface.manualMoveListView(id, distance);
	}

	public void setDefCellBitmap(byte[] mData, int mLen, int mWidth, int mHeight)
	{
		NativeInterface.setDefCell(id, mData, mLen, mWidth, mHeight);
	}

	public void setOnCellMoveListener(OnCellMoveListener mListener)
	{
		NativeInterface.setTheCellMoveListener(id, mListener);
	}

	public void setCellMovable(boolean flag)
	{
		NativeInterface.setCellMovable(id, flag);
	}

	/**
	 * Interface definition for a call back to be invoked when an item in this
	 * listView has been clicked.
	 */
	public interface OnItemClickListener
	{
		/**
		 * Called when a item be touched.
		 * 
		 * @param listid
		 *            id of list-view.
		 * @param index
		 *            the item index.
		 */
		public void onItemClick(int listid, int index, float x, float y);
	}

	public interface OnCellMoveListener
	{
		public void theCellMovedOverCallBack(int listViewId);
	}

	public void setStartIndex(int mStartIndex)
	{
		NativeInterface.setListViewStartIndex(id, mStartIndex);
	}

	public void setItemFrameAnimation(int index, Animation animation)
	{
		Action[] actions = animation.getActions();
		if (actions != null)
		{
			Action action = actions[0];
			if (action != null && action instanceof FrameAction)
			{
				FrameAction frameAction = (FrameAction) action;
				setItemFrameAnimation(index, frameAction);
			}
		}
	}
    
	public void setItemFrameAnimation(int index, FrameAction frameAction)
	{
		NativeInterface.runListViewFrames(id, index, frameAction.getNumber(), frameAction.getSrc(), frameAction.getRate(), frameAction.getPlayLooper(), frameAction.getKey(), frameAction.isPlayOrder());
	}
}
