package com.tpadsz.home;

public abstract class RequestTask implements Runnable
{
	public static final int TYPE_RUNNING_IN_MAIN = 0x0000;
	
	public static final int TYPE_RUNNING_IN_NORMAL = 0x0010;

	public static final int TYPE_RUNNING_IN_GLQUEUE = 0x0020;

	public static final int TYPE_RUNNING_IN_NEW_THREAD = 0x0030;

	private int mTaskId;
	private int mTaskType;
	private int mTaskFlag;

	public RequestTask()
	{
		this.mTaskType = TYPE_RUNNING_IN_GLQUEUE;
	}

	public void setId(int mTaskId)
	{
		this.mTaskId = mTaskId;
	}

	public void setType(int mTaskType)
	{
		this.mTaskType = mTaskType;
	}

	public void setFlag(int mTaskFlag)
	{
		this.mTaskFlag = mTaskFlag;
	}

	public int getId()
	{
		return mTaskId;
	}

	public int getType()
	{
		return mTaskType;
	}

	public int getFlag()
	{
		return mTaskFlag;
	}

}
