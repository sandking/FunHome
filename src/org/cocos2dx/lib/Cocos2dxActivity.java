/****************************************************************************
Copyright (c) 2010-2011 cocos2d-x.org

http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 ****************************************************************************/
package org.cocos2dx.lib;

import org.cocos2dx.lib.Cocos2dxHelper.Cocos2dxHelperListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.ViewGroup;

public class Cocos2dxActivity extends Activity implements Cocos2dxHelperListener
{
	// ===========================================================
	// Constants
	// ===========================================================
	public static final String TAG = Cocos2dxActivity.class.getSimpleName();

	// ===========================================================
	// Fields
	// ===========================================================
	protected Cocos2dxGLSurfaceView mGLSurefaceView;

	protected MainView mMainView;
	private Cocos2dxHandler mHandler;

	// ===========================================================
	// Constructors
	// ===========================================================

	public void addQueue(Runnable r)
	{
		mGLSurefaceView.queueEvent(r);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.init();
		Cocos2dxHelper.init(this, this);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

//	@Override
//	protected void onResume()
//	{
//		super.onResume();
//		resume();
//	}
//
//	@Override
//	protected void onPause()
//	{
//		super.onPause();
//		pause();
//	}

	public void pause()
	{
		Cocos2dxHelper.onPause();
		this.mGLSurefaceView.onPause();
	}

	public void resume()
	{
		Cocos2dxHelper.onResume();
		this.mGLSurefaceView.onResume();
	}

	@Override
	public void runOnGLThread(final Runnable pRunnable)
	{
		this.mGLSurefaceView.queueEvent(pRunnable);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void init()
	{
		this.mHandler = new Cocos2dxHandler(this);
		// FrameLayout
		ViewGroup.LayoutParams framelayout_params = new ViewGroup.LayoutParams(480, 800);
		mMainView = new MainView(this);
		mMainView.setLayoutParams(framelayout_params);

		// Cocos2dxGLSurfaceView
		this.mGLSurefaceView = this.onCreateGLSurfaceView();

		// ...add to FrameLayout
		// framelayout.addView(mGLSurefaceView);\
		mMainView.setGlView(mGLSurefaceView);
		setCocos2dxRenderer();
		// Set framelayout as the content view
		// setContentView(mMainView);
	}

	public void setCocos2dxRenderer()
	{
		mGLSurefaceView.setCocos2dxRenderer(new Cocos2dxRenderer());
	}

	public Cocos2dxGLSurfaceView onCreateGLSurfaceView()
	{
		return new Cocos2dxGLSurfaceView(this);
	}

	@Override
	public void showDialog(final String pTitle, final String pMessage)
	{
		Message msg = new Message();
		msg.what = Cocos2dxHandler.HANDLER_SHOW_DIALOG;
		msg.obj = new Cocos2dxHandler.DialogMessage(pTitle, pMessage);
		this.mHandler.sendMessage(msg);
	}

	@Override
	public void showEditTextDialog(final String pTitle, final String pContent, final int pInputMode, final int pInputFlag, final int pReturnType, final int pMaxLength)
	{
		Message msg = new Message();
		msg.what = Cocos2dxHandler.HANDLER_SHOW_EDITBOX_DIALOG;
		msg.obj = new Cocos2dxHandler.EditBoxMessage(pTitle, pContent, pInputMode, pInputFlag, pReturnType, pMaxLength);
		this.mHandler.sendMessage(msg);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
