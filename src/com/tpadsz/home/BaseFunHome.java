package com.tpadsz.home;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.tpadsz.home.view.Program;
import com.tpadsz.home.view.Program.LoadFileCallback;

public abstract class BaseFunHome extends Cocos2dxActivity implements LoadFileCallback
{
	static
	{
		System.loadLibrary("game");
	}

	// ===========================================================
	// TODO Constants
	// ===========================================================
	public final static String TAG = BaseFunHome.class.getSimpleName();
	public final static boolean _DEBUG_ = true;
	public final static int FLAG_MAIN_HANDLER_UPDATEGL_INMAIN = 0;

	// ===========================================================
	// TODO Fields
	// ===========================================================
	protected InputMethodManager mInputMethodManager;
	protected List<RequestTask> mReserveTasks = new ArrayList<RequestTask>();

	protected Handler mainHandler = new MainHandler();
	protected Handler mGestureHandler = new Handler();
	protected DoRequestHandler mDoRequestHandler;
	protected DoRequestThread mDoRequestThread;
	protected Program mProgram;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView();
		initThread();
		initData();
	}

	void setContentView()
	{
		setContentView(initView());
	}

	abstract public View initView();

	abstract public String getConfigPath();

	// ===========================================================
	// TODO Getter & Setter
	// ===========================================================
	public Handler getGestureHandler()
	{
		return mGestureHandler;
	}

	// ===========================================================
	// TODO Methods
	// ===========================================================
	protected void initData()
	{
		try
		{
			mProgram = new Program(this, new FileInputStream(getConfigPath()));
			mProgram.setLoadFileCallback(this);
			mProgram.initProgram();
			mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	public void show()
	{
		mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private void initThread()
	{
		mDoRequestThread = new DoRequestThread();
		mDoRequestThread.start();
	}

	public void request(RequestTask mTask)
	{
		requestDelay(mTask, 0);
	}

	public void requestDelay(RequestTask mTask, long delayTime)
	{
		if (mDoRequestHandler == null)
		{
			mReserveTasks.add(mTask);
		}
		else mDoRequestHandler.sendMessageDelayed(mDoRequestHandler.obtainMessage(mTask.getId(), mTask), delayTime);
	}

	// ===========================================================
	// TODO Inner and Anonymous Classes
	// ===========================================================
	class MainHandler extends Handler
	{

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{
			case FLAG_MAIN_HANDLER_UPDATEGL_INMAIN:
				RequestTask mRequestTask = (RequestTask) msg.obj;
				mRequestTask.run();
				break;
			}
		}
	}

	class DoRequestThread extends HandlerThread
	{
		public DoRequestThread()
		{
			super(DoRequestThread.class.getName());
		}

		@Override
		protected void onLooperPrepared()
		{
			super.onLooperPrepared();
			mDoRequestHandler = new DoRequestHandler(getLooper());
			while (!mReserveTasks.isEmpty())
			{
				RequestTask mTask = mReserveTasks.remove(0);
				mDoRequestHandler.sendMessage(mDoRequestHandler.obtainMessage(mTask.getId(), mTask));
			}
		}
	}

	class DoRequestHandler extends Handler
	{

		public DoRequestHandler()
		{
			super();
		}

		public DoRequestHandler(Looper looper)
		{
			super(looper);
		}

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			RequestTask mRequestTask = (RequestTask) msg.obj;
			int request_type = mRequestTask.getType();
			switch (request_type)
			{
			case RequestTask.TYPE_RUNNING_IN_MAIN:
				mainHandler.sendMessage(mainHandler.obtainMessage(FLAG_MAIN_HANDLER_UPDATEGL_INMAIN, mRequestTask));
				break;
			case RequestTask.TYPE_RUNNING_IN_NORMAL:
				mRequestTask.run();
				break;
			case RequestTask.TYPE_RUNNING_IN_GLQUEUE:
				addQueue(mRequestTask);
				break;
			case RequestTask.TYPE_RUNNING_IN_NEW_THREAD:
				new Thread(mRequestTask).start();
				break;
			}
		}
	}

}
