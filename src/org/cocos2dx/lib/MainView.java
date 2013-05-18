package org.cocos2dx.lib;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.EditText;
import android.widget.FrameLayout;

public class MainView extends FrameLayout
{
	private List<EditText> mEditTexts;

	private Cocos2dxGLSurfaceView mCocos2dxGLSurfaceView;

	public MainView(Context context)
	{
		super(context);
	}

	private void putEditTextToPools(EditText view)
	{
		if (mEditTexts == null) mEditTexts = new ArrayList<EditText>();
		mEditTexts.add(view);
	}

	public void setGlView(Cocos2dxGLSurfaceView view)
	{
		this.mCocos2dxGLSurfaceView = view;
		this.addView(view);
	}

	public void addEditTextView(EditText view, LayoutParams params)
	{
		putEditTextToPools(view);
		this.addView(view, params);
	}

	public Cocos2dxGLSurfaceView getGlView()
	{
		return mCocos2dxGLSurfaceView;
	}

	public EditText findEditTextByid(int id)
	{
		if (mEditTexts == null) return null;
		for (EditText mEditText : mEditTexts)
		{
			if (mEditText.getId() == id) return mEditText;
		}
		return null;
	}
}
