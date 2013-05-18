package com.tpadsz.home.xmlparser.data;

public class DataAnimationFlow
{
	private String beforeIndex;
	private String nowIndex;
	private int spiritId;
	private int groupId;
	private int animationId;
	private int delayTime;
	private boolean animationOrder;
	private boolean afterAnimationVisible;

	public String getBeforeIndex()
	{
		return beforeIndex;
	}

	public void setBeforeIndex(String beforeIndex)
	{
		this.beforeIndex = beforeIndex;
	}

	public String getNowIndex()
	{
		return nowIndex;
	}

	public void setNowIndex(String nowIndex)
	{
		this.nowIndex = nowIndex;
	}

	public int getSpiritId()
	{
		return spiritId;
	}

	public void setSpiritId(int spiritId)
	{
		this.spiritId = spiritId;
	}

	public int getAnimationId()
	{
		return animationId;
	}

	public void setAnimationId(int animationId)
	{
		this.animationId = animationId;
	}

	public int getDelayTime()
	{
		return delayTime;
	}

	public void setDelayTime(int delayTime)
	{
		this.delayTime = delayTime;
	}

	public boolean isAnimationOrder()
	{
		return animationOrder;
	}

	public void setAnimationOrder(boolean animationOrder)
	{
		this.animationOrder = animationOrder;
	}

	public boolean isAfterAnimationVisible()
	{
		return afterAnimationVisible;
	}

	public void setAfterAnimationVisible(boolean afterAnimationVisible)
	{
		this.afterAnimationVisible = afterAnimationVisible;
	}

	public int getGroupId()
	{
		return groupId;
	}

	public void setGroupId(int groupId)
	{
		this.groupId = groupId;
	}

}
