package com.tpadsz.home.xmlparser.data;

public class DataProgram
{
	private int id;
	private String name;
	private String src;
	private String version;
	private String pkg;
	
	public String getPkg()
	{
		return pkg;
	}

	public void setPkg(String pkg)
	{
		this.pkg = pkg;
	}

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

	public String getSrc()
	{
		return src;
	}

	public void setSrc(String src)
	{
		this.src = src;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

}
