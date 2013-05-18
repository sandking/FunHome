package com.tpadsz.home.xmlparser.data;

import java.util.HashMap;

public class DataPage
{
	private int id;
	private String name;
	private int[] spirits;
	private int[] groups;
	private HashMap<Integer, HashMap<String, DataAnimationFlow>> intoAnimationFlow;
	private HashMap<Integer, HashMap<String, DataAnimationFlow>> outAnimationFlow;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int[] getSpirits()
	{
		return spirits;
	}

	public void setSpirits(int[] spirits)
	{
		this.spirits = spirits;
	}

	public int[] getGroups()
	{
		return groups;
	}

	public void setGroups(int[] groups)
	{
		this.groups = groups;
	}

	public HashMap<Integer, HashMap<String, DataAnimationFlow>>  getIntoAnimationFlow()
	{
		return intoAnimationFlow;
	}

	public void setIntoAnimationFlow(HashMap<Integer, HashMap<String, DataAnimationFlow>>  intoAnimationFlow)
	{
		this.intoAnimationFlow = intoAnimationFlow;
	}

	public HashMap<Integer, HashMap<String, DataAnimationFlow>>  getOutAnimationFlow()
	{
		return outAnimationFlow;
	}

	public void setOutAnimationFlow(HashMap<Integer, HashMap<String, DataAnimationFlow>>  outAnimationFlow)
	{
		this.outAnimationFlow = outAnimationFlow;
	}

}
