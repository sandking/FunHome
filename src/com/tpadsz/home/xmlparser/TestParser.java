package com.tpadsz.home.xmlparser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import android.test.AndroidTestCase;
import android.util.Log;

import com.tpadsz.home.xmlparser.data.DataAnimationFlow;
import com.tpadsz.home.xmlparser.data.DataPage;

public class TestParser extends AndroidTestCase
{
	public void testReadXml() throws Exception
	{

		Log.e(getName(), "begin read Xml!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		InputStream inputStream = TestParser.class.getClassLoader().getResourceAsStream("animation_8.xml");

		ProgramParser parser = ProgramParser.getInstance(inputStream);
		HashMap<Integer, DataPage> pages = parser.parsePages();
//
//		for (Integer id : pages.keySet())
//		{
//			DataPage dataPage = pages.get(id);
//			StringBuffer sb = new StringBuffer();
//			int pgid = dataPage.getId();
//			sb.append("id:").append(pgid).append(" ").append(" spirits{");
//
//			int[] sps = dataPage.getSpirits();
//			for (int i = 0; i < sps.length; i++)
//			{
//				int sp = sps[i];
//				sb.append(sp).append(",");
//			}
//			// sb.append("}").append(" ").append(" groups{");
//			// int[] gps = dataPage.getGroups();
//			// for (int i = 0; i < gps.length; i++)
//			// {
//			// int gp = gps[i];
//			// sb.append(gp).append(",");
//			// }
//			// sb.append("}").append(" ");
//			Log.e(getName(), sb.toString());
//			HashMap<Integer, List<DataAnimationFlow>> into = dataPage.getIntoAnimationFlow();
//			HashMap<Integer, List<DataAnimationFlow>> out = dataPage.getOutAnimationFlow();
//
//			StringBuffer sb1 = new StringBuffer();
//
//			for (int key : into.keySet())
//			{
//				sb1.append("from:").append(key).append("\n");
//				List<DataAnimationFlow> data = into.get(key);
//				int len = data.size();
//				for (int i = 0; i < len; i++)
//				{
//					DataAnimationFlow animationFlow = data.get(i);
//
//					String before = animationFlow.getBeforeIndex();
//					String now = animationFlow.getNowIndex();
//					int sp = animationFlow.getSpiritId();
//					int anim = animationFlow.getAnimationId();
//					int delay = animationFlow.getDelayTime();
//					boolean order = animationFlow.isAnimationOrder();
//					boolean visible = animationFlow.isAfterAnimationVisible();
//					sb1.append("before:").append(before).append("now:").append(now).append("sp:").append(sp).append("anim:").append(anim).append("delay:").append(delay).append("order:").append(order).append("visible:").append(visible);
//					Log.e(getName(), sb1.toString());
//				}
//			}
//			StringBuffer sb2 = new StringBuffer();
//
//			for (int key : out.keySet())
//			{
//				List<DataAnimationFlow> data = out.get(key);
//				int len = data.size();
//				for (int i = 0; i < len; i++)
//				{
//					DataAnimationFlow animationFlow = data.get(i);
//
//					String before = animationFlow.getBeforeIndex();
//					String now = animationFlow.getNowIndex();
//					int sp = animationFlow.getSpiritId();
//					int anim = animationFlow.getAnimationId();
//					int delay = animationFlow.getDelayTime();
//					boolean order = animationFlow.isAnimationOrder();
//					boolean visible = animationFlow.isAfterAnimationVisible();
//					sb2.append("before:").append(before).append("now:").append(now).append("sp:").append(sp).append("anim:").append(anim).append("delay:").append(delay).append("order:").append(order).append("visible:").append(visible);
//					Log.e(getName(), sb2.toString());
//				}
//			}
//		}

		Log.e(getName(), "begin read Xml!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

	}
}
