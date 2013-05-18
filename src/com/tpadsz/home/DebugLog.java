package com.tpadsz.home;

import android.util.Log;

public class DebugLog
{

	public static void LogNull(String tag, String str)
	{
		Log.e(tag, String.format("----------------------%s-------------------------", str));
	}

	public static void LogInfo(String tag, String str)
	{
		Log.e(tag, String.format("~~~~~~~~~~~~~%s~~~~~~~~~~~~", str));
	}

}
