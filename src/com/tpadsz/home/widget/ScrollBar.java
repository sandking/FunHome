package com.tpadsz.home.widget;

import com.tpadsz.home.jni.NativeInterface;
import com.tpadsz.home.view.Spirit;

public class ScrollBar extends Spirit
{
	private boolean isScroll;

	public ScrollBar(int id, String name, String src, float x, float y, int width, int height, int layer, boolean touchable, boolean visible, float startx, int scrollspeed)
	{
		super(id, name, src, x, y, width, height, layer, touchable, visible);
		NativeInterface.createScrollBar(id, src, x, y, width, height, layer, touchable, visible);
		setContentPositionXInScrollBar(startx);
		scroll(scrollspeed);
	}

	public ScrollBar(int id, String src, float x, float y, int width, int height, int layer, boolean touchable, boolean visible)
	{
		super(id, "", src, x, y, width, height, layer, touchable, visible);
		NativeInterface.createScrollBar(id, src, x, y, width, height, layer, touchable, visible);
	}

	@Override
	public Type getType()
	{
		return Type.SCROLLBAR;
	}

	public void setScrollBarTexture(String src)
	{
		NativeInterface.setScrollBarTexture(id, src);
	}

	public void setScrollBarTextureFromBuf(byte[] mBufData, int mLen, int mWidth, int mHeight)
	{
		NativeInterface.setScrollBarTextureFromBuf(id, mBufData, mLen, mWidth, mHeight);
	}

	public void setContentPositionXInScrollBar(float x)
	{
		NativeInterface.setContentPositionXInScrollBar(id, x);
	}

	public void scroll(float mSpeed)
	{
		if (isScroll) return;
		NativeInterface.scroll(id, mSpeed);
		isScroll = true;
	}

	public void stopScroll()
	{
		if (!isScroll) return;
		NativeInterface.stopScroll(id);
		isScroll = false;
	}

	public boolean isScroll()
	{
		return this.isScroll;
	}

	public void setScrollBarSize(int w, int h)
	{
		NativeInterface.setScrollBarSize(id, w, h);
	}
}
