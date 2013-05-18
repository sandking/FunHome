package com.tpadsz.home.xmlparser;

import java.io.InputStream;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.tpadsz.home.xmlparser.animation.DataAlpha;
import com.tpadsz.home.xmlparser.animation.DataFrame;
import com.tpadsz.home.xmlparser.animation.DataMove;
import com.tpadsz.home.xmlparser.animation.DataRotate;
import com.tpadsz.home.xmlparser.animation.DataScale;
import com.tpadsz.home.xmlparser.data.DataAnimation;
import com.tpadsz.home.xmlparser.data.DataAnimationFlow;
import com.tpadsz.home.xmlparser.data.DataFrameFile;
import com.tpadsz.home.xmlparser.data.DataMoveline;
import com.tpadsz.home.xmlparser.data.DataPage;
import com.tpadsz.home.xmlparser.data.DataProgram;
import com.tpadsz.home.xmlparser.spirits.DataListView;
import com.tpadsz.home.xmlparser.spirits.DataScrollbar;
import com.tpadsz.home.xmlparser.spirits.DataSpirit;
import com.tpadsz.home.xmlparser.spirits.DataSpiritGroup;
import com.tpadsz.home.xmlparser.spirits.DataTouchpad;

public class ProgramParser
{
	private InputStream mSourceInput;
	private XmlPullParser xmlpull = null;
	private static ProgramParser mProgramParser;

	private ProgramParser(InputStream input) throws XmlPullParserException
	{
		mSourceInput = input;
		xmlpull = Xml.newPullParser();
		xmlpull.setInput(mSourceInput, "utf-8");
	}

	public static ProgramParser getInstance(InputStream input)
	{
		if (mProgramParser == null)
		{
			try
			{
				mProgramParser = new ProgramParser(input);
			}
			catch (XmlPullParserException e)
			{
				e.printStackTrace();
			}
		}
		return mProgramParser;
	}

	// ===========================================================
	// TODO Public Methods
	// ===========================================================

	public DataProgram parseProgram()
	{
		DataProgram dataProgram = null;
		try
		{
			int eventCode = xmlpull.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_TAG:
					String tagName = xmlpull.getName();
					if (tagName.equals("Program"))
					{
						dataProgram = new DataProgram();
						int count = getCurrAttrsCount();
						for (int i = 0; i < count; i++)
						{
							String attrName = xmlpull.getAttributeName(i).trim();
							String attrValue = xmlpull.getAttributeValue(i).trim();
							if (attrName.equals("id"))
							{
								dataProgram.setId(attrName == null ? 0 : Integer.parseInt(attrValue));
							}
							else if (attrName.equals("name"))
							{
								dataProgram.setName(attrValue);
							}
							else if (attrName.equals("src"))
							{
								dataProgram.setSrc(attrValue);
							}
							else if (attrName.equals("package"))
							{
								dataProgram.setPkg(attrValue);
							}
						}
						return dataProgram;
					}
					break;
				}
				eventCode = xmlpull.next();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataProgram;
	}

	public HashMap<String, DataFrameFile> parseFrameFile()
	{
		HashMap<String, DataFrameFile> maps_ID_FrameFile = null;
		try
		{
			int eventCode = xmlpull.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_TAG:
					String tagName = xmlpull.getName();
					if (tagName.equals("frame_animation_file_name"))
					{
						maps_ID_FrameFile = new HashMap<String, DataFrameFile>();
					}
					else
					{
						DataFrameFile dataFrameFile = null;
						if (tagName.equals("frame_file"))
						{
							dataFrameFile = new DataFrameFile();
							parseFrameFileAttr(dataFrameFile);
							maps_ID_FrameFile.put(dataFrameFile.getName(), dataFrameFile);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					String endTagName = xmlpull.getName();
					if (endTagName.equals("frame_animation_file_name")) return maps_ID_FrameFile;
					break;
				}
				eventCode = xmlpull.next();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return maps_ID_FrameFile;
	}

	public HashMap<String, DataMoveline> parseMoveLine()
	{
		HashMap<String, DataMoveline> maps_ID_MoveLine = null;
		try
		{
			int eventCode = xmlpull.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_TAG:
					String tagName = xmlpull.getName();
					if (tagName.equals("move_lines"))
					{
						maps_ID_MoveLine = new HashMap<String, DataMoveline>();
					}
					else
					{
						DataMoveline dataMoveline = null;
						if (tagName.equals("move_line"))
						{
							dataMoveline = new DataMoveline();
							parseMoveLineAttr(dataMoveline);
							maps_ID_MoveLine.put(dataMoveline.getName(), dataMoveline);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					String endTagName = xmlpull.getName();
					if (endTagName.equals("move_lines")) return maps_ID_MoveLine;
					break;
				}
				eventCode = xmlpull.next();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return maps_ID_MoveLine;
	}

	public HashMap<Integer, DataAnimation> parseAnimations()
	{
		HashMap<Integer, DataAnimation> maps_ID_Animation = null;
		try
		{
			int eventCode = xmlpull.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_TAG:
					String tagName = xmlpull.getName();
					if (tagName.equals("play_animation_IDS"))
					{
						maps_ID_Animation = new HashMap<Integer, DataAnimation>();
					}
					else
					{
						DataAnimation dataAnimation = null;
						if (tagName.equals("play_animation_ID"))
						{
							dataAnimation = new DataAnimation();
							parseAnimationAttr(dataAnimation);
							maps_ID_Animation.put(dataAnimation.getId(), dataAnimation);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					String endTagName = xmlpull.getName();
					if (endTagName.equals("play_animation_IDS")) return maps_ID_Animation;
					break;
				}
				eventCode = xmlpull.next();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return maps_ID_Animation;
	}

	public HashMap<Integer, DataPage> parsePages()
	{
		HashMap<Integer, DataPage> maps_ID_Page = null;
		HashMap<Integer, HashMap<String, DataAnimationFlow>> intoAnimationFlow = null;
		HashMap<Integer, HashMap<String, DataAnimationFlow>> outAnimationFlow = null;
		// List<DataAnimationFlow> dataAnimationFlows = null;
		HashMap<String, DataAnimationFlow> dataAnimationFlows = null;
		DataPage dataPage = null;
		DataAnimationFlow dataAnimationFlow = null;
		String from = null, to = null;
		try
		{
			int eventCode = xmlpull.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_TAG:
					String tagName = xmlpull.getName();
					if (tagName.equals("page_element_IDS"))
					{
						maps_ID_Page = new HashMap<Integer, DataPage>();
					}
					else if (tagName.equals("page_sprites_ID"))
					{
						intoAnimationFlow = new HashMap<Integer, HashMap<String, DataAnimationFlow>>();
						outAnimationFlow = new HashMap<Integer, HashMap<String, DataAnimationFlow>>();
						dataPage = new DataPage();
						parsePageAttr(dataPage);
					}
					else if (tagName.equals("page_in"))
					{
						dataAnimationFlows = new HashMap<String, DataAnimationFlow>();
						from = xmlpull.getAttributeValue(0).trim();
					}
					else if (tagName.equals("page_out"))
					{
						dataAnimationFlows = new HashMap<String, DataAnimationFlow>();
						to = xmlpull.getAttributeValue(0).trim();
					}
					else if (tagName.equals("play_anim"))
					{
						dataAnimationFlow = new DataAnimationFlow();
						parseAnimationFlowAttr(dataAnimationFlow);
					}
					break;
				case XmlPullParser.END_TAG:
					String endTagName = xmlpull.getName();
					if (endTagName.equals("page_element_IDS"))
					{
						return maps_ID_Page;
					}
					else if (endTagName.equals("page_sprites_ID"))
					{

						if (dataPage != null)
						{
							dataPage.setIntoAnimationFlow(intoAnimationFlow);
							dataPage.setOutAnimationFlow(outAnimationFlow);
							maps_ID_Page.put(dataPage.getId(), dataPage);
						}
					}
					else if (endTagName.equals("page_in"))
					{
						if (from.contains(","))
						{
							String[] froms = from.split(",");
							for (String str : froms)
							{
								int key = Integer.parseInt(str);
								intoAnimationFlow.put(key, dataAnimationFlows);
							}
						}
						else intoAnimationFlow.put(Integer.parseInt(from), dataAnimationFlows);
					}
					else if (endTagName.equals("page_out"))
					{
						// outAnimationFlow.put(to, dataAnimationFlows);
						if (to.contains(","))
						{
							String[] tos = to.split(",");
							for (String str : tos)
							{
								int key = Integer.parseInt(str);
								outAnimationFlow.put(key, dataAnimationFlows);
							}
						}
						else outAnimationFlow.put(Integer.parseInt(to), dataAnimationFlows);
					}
					else if (endTagName.equals("play_anim"))
					{
						dataAnimationFlows.put(dataAnimationFlow.getNowIndex(), dataAnimationFlow);
					}
					break;
				}
				eventCode = xmlpull.next();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return maps_ID_Page;
	}

	// public HashMap<Integer, DataPage> parsePages()
	// {
	// HashMap<Integer, DataPage> maps_ID_Page = null;
	// HashMap<Integer, List<DataAnimationFlow>> intoAnimationFlow = null;
	// HashMap<Integer, List<DataAnimationFlow>> outAnimationFlow = null;
	// List<DataAnimationFlow> dataAnimationFlows = null;
	// int from, to;
	// try
	// {
	// int eventCode = xmlpull.getEventType();
	// while (eventCode != XmlPullParser.END_DOCUMENT)
	// {
	// switch (eventCode)
	// {
	// case XmlPullParser.START_TAG:
	// String tagName = xmlpull.getName();
	// if (tagName.equals("page_element_IDS"))
	// {
	// maps_ID_Page = new HashMap<Integer, DataPage>();
	// }
	// else
	// {
	// DataPage dataPage = null;
	// if (tagName.equals("page_sprites_ID"))
	// {
	// intoAnimationFlow = new HashMap<Integer, List<DataAnimationFlow>>();
	// dataPage = new DataPage();
	// parsePageAttr(dataPage);
	// maps_ID_Page.put(dataPage.getId(), dataPage);
	// }
	// else
	// {
	// if (tagName.equals("page_in"))
	// {
	// dataAnimationFlows = new ArrayList<DataAnimationFlow>();
	// }
	// else
	// {
	// DataAnimationFlow animationFlow = null;
	// if (tagName.equals("play_anim"))
	// {
	// animationFlow = new DataAnimationFlow();
	// parseAnimationFlowAttr(animationFlow);
	// dataAnimationFlows.add(animationFlow);
	// }
	// }
	//
	// // if (tagName.equals("page_in"))
	// // {
	// // intoAnimationFlow = new HashMap<Integer,
	// // List<DataAnimationFlow>>();
	// // String attrValue =
	// // xmlpull.getAttributeValue(0).trim();
	// // }
	// // else if (tagName.equals("page_out"))
	// // {
	// // outAnimationFlow = new HashMap<Integer,
	// // List<DataAnimationFlow>>();
	// // }
	// // else if (tagName.equals("play_anim"))
	// // {
	// //
	// // }
	// // dataPage.setIntoAnimationFlow(intoAnimationFlow);
	// // dataPage.setOutAnimationFlow(outAnimationFlow);
	// }
	// }
	// break;
	// case XmlPullParser.END_TAG:
	// String endTagName = xmlpull.getName();
	// if (endTagName.equals("page_element_IDS")) return maps_ID_Page;
	// break;
	// }
	// eventCode = xmlpull.next();
	// }
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// return maps_ID_Page;
	// }

	public HashMap<Integer, DataAlpha> parseAlphaAnim()
	{
		HashMap<Integer, DataAlpha> maps_ID_AlphaAnim = null;
		try
		{
			int eventCode = xmlpull.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_TAG:
					String tagName = xmlpull.getName();
					if (tagName.equals("alpha_animations"))
					{
						maps_ID_AlphaAnim = new HashMap<Integer, DataAlpha>();
					}
					else
					{
						DataAlpha alphaAnim = null;
						if (tagName.equals("alpha_animation"))
						{
							alphaAnim = new DataAlpha();
							parseAlphaAnimAttr(alphaAnim);
							maps_ID_AlphaAnim.put(alphaAnim.getId(), alphaAnim);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					String endTagName = xmlpull.getName();
					if (endTagName.equals("alpha_animations")) return maps_ID_AlphaAnim;
					break;
				}
				eventCode = xmlpull.next();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return maps_ID_AlphaAnim;
	}

	public HashMap<Integer, DataRotate> parseRotateAnim()
	{
		HashMap<Integer, DataRotate> maps_ID_RotateAnim = null;
		try
		{
			int eventCode = xmlpull.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_TAG:
					String tagName = xmlpull.getName();
					if (tagName.equals("rotate_animations"))
					{
						maps_ID_RotateAnim = new HashMap<Integer, DataRotate>();
					}
					else
					{
						DataRotate rotateAnim = null;
						if (tagName.equals("rotate_animation"))
						{
							rotateAnim = new DataRotate();
							parseRotateAnimAttr(rotateAnim);
							maps_ID_RotateAnim.put(rotateAnim.getId(), rotateAnim);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					String endTagName = xmlpull.getName();
					if (endTagName.equals("rotate_animations")) return maps_ID_RotateAnim;
					break;
				}
				eventCode = xmlpull.next();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return maps_ID_RotateAnim;
	}

	public HashMap<Integer, DataScale> parseScaleAnim()
	{
		HashMap<Integer, DataScale> maps_ID_ScaleAnim = null;
		try
		{
			int eventCode = xmlpull.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_TAG:
					String tagName = xmlpull.getName();
					if (tagName.equals("zoom_animations"))
					{
						maps_ID_ScaleAnim = new HashMap<Integer, DataScale>();
					}
					else
					{
						DataScale scaleAnim = null;
						if (tagName.equals("zoom_animation"))
						{
							scaleAnim = new DataScale();
							parseScaleAnimAttr(scaleAnim);
							maps_ID_ScaleAnim.put(scaleAnim.getId(), scaleAnim);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					String endTagName = xmlpull.getName();
					if (endTagName.equals("zoom_animations")) return maps_ID_ScaleAnim;
					break;
				}
				eventCode = xmlpull.next();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return maps_ID_ScaleAnim;
	}

	public HashMap<Integer, DataMove> parseMoveAnim()
	{
		HashMap<Integer, DataMove> maps_ID_MoveAnim = null;
		try
		{
			int eventCode = xmlpull.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_TAG:
					String tagName = xmlpull.getName();
					if (tagName.equals("move_animations")) maps_ID_MoveAnim = new HashMap<Integer, DataMove>();
					else
					{
						DataMove moveAnim = null;
						if (tagName.equals("move_animation"))
						{
							moveAnim = new DataMove();
							parseMoveAnimAttr(moveAnim);
							maps_ID_MoveAnim.put(moveAnim.getId(), moveAnim);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					String endTagName = xmlpull.getName();
					if (endTagName.equals("move_animations")) return maps_ID_MoveAnim;
					break;
				}
				eventCode = xmlpull.next();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return maps_ID_MoveAnim;
	}

	public HashMap<Integer, DataFrame> parseFrameAnim()
	{
		HashMap<Integer, DataFrame> maps_ID_FrameAnim = null;
		try
		{
			int eventCode = xmlpull.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_TAG:
					String tagName = xmlpull.getName();
					if (tagName.equals("frame_animations")) maps_ID_FrameAnim = new HashMap<Integer, DataFrame>();

					DataFrame frameAnim = null;
					if (tagName.equals("frame_animation"))
					{
						frameAnim = new DataFrame();
						parseFrameAnimAttr(frameAnim);
						maps_ID_FrameAnim.put(frameAnim.getId(), frameAnim);
					}
					break;
				case XmlPullParser.END_TAG:
					String endTagName = xmlpull.getName();
					if (endTagName.equals("frame_animations")) return maps_ID_FrameAnim;
					break;
				}
				eventCode = xmlpull.next();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return maps_ID_FrameAnim;
	}

	public HashMap<Integer, DataSpiritGroup> parseGroup()
	{
		HashMap<Integer, DataSpiritGroup> maps_ID_SpiritGroup = null;
		try
		{
			int eventCode = xmlpull.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_DOCUMENT:
					maps_ID_SpiritGroup = new HashMap<Integer, DataSpiritGroup>();
					break;
				case XmlPullParser.START_TAG:
					String tagName = xmlpull.getName();
					if (tagName.equals("groups")) maps_ID_SpiritGroup = new HashMap<Integer, DataSpiritGroup>();
					else
					{
						DataSpiritGroup group = null;
						if (tagName.equals("group"))
						{
							group = new DataSpiritGroup();
							parseGroupAttr(group);
							maps_ID_SpiritGroup.put(group.getID(), group);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					String endTagName = xmlpull.getName();
					if (endTagName.equals("groups")) return maps_ID_SpiritGroup;
					break;
				}
				eventCode = xmlpull.next();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return maps_ID_SpiritGroup;
	}

	public HashMap<Integer, DataSpirit> parseSpirits()
	{
		HashMap<Integer, DataSpirit> maps_ID_Spirit = null;
		try
		{
			int eventCode = xmlpull.getEventType();
			while (eventCode != XmlPullParser.END_DOCUMENT)
			{
				switch (eventCode)
				{
				case XmlPullParser.START_TAG:
					String tagName = xmlpull.getName();
					if (tagName.equals("element"))
					{
						maps_ID_Spirit = new HashMap<Integer, DataSpirit>();
					}
					else
					{
						DataSpirit mSpirit = null;
						if (tagName.equals("sprite"))
						{
							mSpirit = new DataSpirit();
							parseSpiritAttr(mSpirit);
						}
						else if (tagName.equals("listview"))
						{
							mSpirit = new DataListView();
							parseListViewAttr((DataListView) mSpirit);
						}
						else if (tagName.equals("touch_board"))
						{
							mSpirit = new DataTouchpad();
							parseTouchpadAttr((DataTouchpad) mSpirit);
						}
						else if (tagName.equals("scrollbar"))
						{
							mSpirit = new DataScrollbar();
							parseScrollbarAttr((DataScrollbar) mSpirit);
						}
						if (mSpirit != null)
						{
							maps_ID_Spirit.put(mSpirit.getID(), mSpirit);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					String endTagName = xmlpull.getName();
					if (endTagName.equals("element")) { return maps_ID_Spirit; }
					break;
				}
				eventCode = xmlpull.next();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return maps_ID_Spirit;
	}

	// ===========================================================
	// TODO Private Methods
	// ===========================================================

	private void parseFrameFileAttr(DataFrameFile dataFrameFile)
	{
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String fileAttrName = xmlpull.getAttributeName(i).trim();
			String fileAttrValue = xmlpull.getAttributeValue(i).trim();
			if (fileAttrName.equals("id"))
			{
				dataFrameFile.setId(Integer.parseInt(fileAttrValue));
			}
			else if (fileAttrName.equals("name"))
			{
				dataFrameFile.setName(fileAttrValue);
			}
			else if (fileAttrName.equals("file_data"))
			{
				dataFrameFile.setDatas(fileAttrValue);
			}
		}
	}

	private void parseMoveLineAttr(DataMoveline dataMoveline)
	{
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String lineAttrName = xmlpull.getAttributeName(i).trim();
			String lineAttrValue = xmlpull.getAttributeValue(i).trim();
			if (lineAttrName.equals("id"))
			{
				dataMoveline.setId(Integer.parseInt(lineAttrValue));
			}
			else if (lineAttrName.equals("name"))
			{
				dataMoveline.setName(lineAttrValue);
			}
			else if (lineAttrName.equals("line_data"))
			{
				String[] strs = lineAttrValue == null ? null : lineAttrValue.split(",");
				float[] contents = strs == null ? null : new float[strs.length];
				for (int j = 0; j < strs.length; j++)
				{
					contents[j] = Float.parseFloat(strs[j]);
				}
				dataMoveline.setDatas(contents);
			}
		}
	}

	private void parseAnimationAttr(DataAnimation animation)
	{
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String animAttrName = xmlpull.getAttributeName(i).trim();
			String animAttrValue = xmlpull.getAttributeValue(i).trim();
			int value = animAttrValue == null ? 0 : Integer.parseInt(animAttrValue);
			if (animAttrName.equals("animation_ID"))
			{
				animation.setId(value);
			}
			else if (animAttrName.equals("frame_ID"))
			{
				animation.setFrameID(value);
			}
			else if (animAttrName.equals("move_ID"))
			{
				animation.setMoveID(value);
			}
			else if (animAttrName.equals("zoom_ID"))
			{
				animation.setZoomID(value);
			}
			else if (animAttrName.equals("rotate_ID"))
			{
				animation.setRotateID(value);
			}
			else if (animAttrName.equals("alpha_ID"))
			{
				animation.setAlphaID(value);
			}
			else if (animAttrName.equals("colour_ID"))
			{
				animation.setColorID(value);
			}
		}
	}

	private void parseAnimationFlowAttr(DataAnimationFlow animationFlow)
	{
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String pageAttrName = xmlpull.getAttributeName(i).trim();
			String pageAttrValue = xmlpull.getAttributeValue(i).trim();
			if (pageAttrName.equals("before_index"))
			{
				animationFlow.setBeforeIndex(pageAttrValue);
			}
			else if (pageAttrName.equals("now_index"))
			{
				animationFlow.setNowIndex(pageAttrValue);
			}
			else if (pageAttrName.equals("element"))
			{
				animationFlow.setSpiritId(Integer.parseInt(pageAttrValue));
			}
			else if (pageAttrName.equals("element_group"))
			{
				animationFlow.setGroupId(Integer.parseInt(pageAttrValue));
			}
			else if (pageAttrName.equals("anim_ID"))
			{
				animationFlow.setAnimationId(Integer.parseInt(pageAttrValue));
			}
			else if (pageAttrName.equals("duration"))
			{
				animationFlow.setDelayTime(Integer.parseInt(pageAttrValue));
			}
			else if (pageAttrName.equals("order"))
			{
				animationFlow.setAnimationOrder(Integer.parseInt(pageAttrValue) == 0);
			}
			else if (pageAttrName.equals("visiable"))
			{
				animationFlow.setAfterAnimationVisible(Integer.parseInt(pageAttrValue) == 1);
			}
		}
	}

	private void parsePageAttr(DataPage dataPage)
	{
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String pageAttrName = xmlpull.getAttributeName(i).trim();
			String pageAttrValue = xmlpull.getAttributeValue(i).trim();
			if (pageAttrName.equals("ID"))
			{
				dataPage.setId(Integer.parseInt(pageAttrValue));
			}
			else if (pageAttrName.equals("name"))
			{
				dataPage.setName(pageAttrValue);
			}
			else if (pageAttrName.equals("element"))
			{
				String[] strs = pageAttrValue == null ? null : pageAttrValue.split(",");
				int[] contents = strs == null ? null : new int[strs.length];
				for (int j = 0; j < strs.length; j++)
				{
					contents[j] = Integer.parseInt(strs[j]);
				}
				dataPage.setSpirits(contents);
			}
			else if (pageAttrName.equals("page_group"))
			{
				String[] strs = pageAttrValue == null ? null : pageAttrValue.split(",");
				int[] contents = strs == null ? null : new int[strs.length];
				for (int j = 0; j < strs.length; j++)
				{
					contents[j] = Integer.parseInt(strs[j]);
				}
				dataPage.setGroups(contents);
			}
		}
	}

	private void parseAlphaAnimAttr(DataAlpha alphaAnim)
	{
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String alphaAttrName = xmlpull.getAttributeName(i).trim();
			String alphaAttrValue = xmlpull.getAttributeValue(i).trim();
			if (alphaAttrName.equals("ID"))
			{
				alphaAnim.setId(Integer.parseInt(alphaAttrValue));
			}
			else if (alphaAttrName.equals("type"))
			{
				alphaAnim.setType(Integer.parseInt(alphaAttrValue));
			}
			else if (alphaAttrName.equals("alpha_star"))
			{
				alphaAnim.setAlphaStart(Integer.parseInt(alphaAttrValue));
			}
			else if (alphaAttrName.equals("alpha_end"))
			{
				alphaAnim.setAlphaEnd(Integer.parseInt(alphaAttrValue));
			}
			else if (alphaAttrName.equals("speed_type"))
			{
				alphaAnim.setSpeedType(Integer.parseInt(alphaAttrValue));
			}
			else if (alphaAttrName.equals("time"))
			{
				alphaAnim.setDuration(Integer.parseInt(alphaAttrValue));
			}
			else if (alphaAttrName.equals("loop"))
			{
				alphaAnim.setLoop(Integer.parseInt(alphaAttrValue));
			}
			else if (alphaAttrName.equals("alpha_g"))
			{
				alphaAnim.setAlphaG(Integer.parseInt(alphaAttrValue));
			}
			else if (alphaAttrName.equals("to_or_by"))
			{
				alphaAnim.setToOrBy(Integer.parseInt(alphaAttrValue));
			}
		}
	}

	private void parseRotateAnimAttr(DataRotate rotateAnim)
	{
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String rotateAttrName = xmlpull.getAttributeName(i).trim();
			String rotateAttrValue = xmlpull.getAttributeValue(i).trim();
			if (rotateAttrName.equals("ID"))
			{
				rotateAnim.setId(Integer.parseInt(rotateAttrValue));
			}
			else if (rotateAttrName.equals("type"))
			{
				rotateAnim.setType(Integer.parseInt(rotateAttrValue));
			}
			else if (rotateAttrName.equals("x1"))
			{
				rotateAnim.setStartX(Float.parseFloat(rotateAttrValue));
			}
			else if (rotateAttrName.equals("y1"))
			{
				rotateAnim.setStartY(Float.parseFloat(rotateAttrValue));
			}
			else if (rotateAttrName.equals("z1"))
			{
				rotateAnim.setStartZ(Float.parseFloat(rotateAttrValue));
			}
			else if (rotateAttrName.equals("x2"))
			{
				rotateAnim.setEndX(Float.parseFloat(rotateAttrValue));
			}
			else if (rotateAttrName.equals("y2"))
			{
				rotateAnim.setEndY(Float.parseFloat(rotateAttrValue));
			}
			else if (rotateAttrName.equals("z2"))
			{
				rotateAnim.setEndZ(Float.parseFloat(rotateAttrValue));
			}
			else if (rotateAttrName.equals("angle"))
			{
				rotateAnim.setAngle(Integer.parseInt(rotateAttrValue));
			}
			else if (rotateAttrName.equals("speed_type"))
			{
				rotateAnim.setSpeedType(Integer.parseInt(rotateAttrValue));
			}
			else if (rotateAttrName.equals("time"))
			{
				rotateAnim.setDuration(Integer.parseInt(rotateAttrValue));
			}
			else if (rotateAttrName.equals("loop"))
			{
				rotateAnim.setLoop(Integer.parseInt(rotateAttrValue));
			}
			else if (rotateAttrName.equals("rotate_g"))
			{
				rotateAnim.setRotateG(Integer.parseInt(rotateAttrValue));
			}
			else if (rotateAttrName.equals("to_or_by"))
			{
				rotateAnim.setToOrBy(Integer.parseInt(rotateAttrValue));
			}
		}
	}

	private void parseScaleAnimAttr(DataScale scaleAnim)
	{
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String scaleAttrName = xmlpull.getAttributeName(i).trim();
			String scaleAttrValue = xmlpull.getAttributeValue(i).trim();
			if (scaleAttrName.equals("ID"))
			{
				scaleAnim.setId(Integer.parseInt(scaleAttrValue));
			}
			else if (scaleAttrName.equals("type"))
			{
				scaleAnim.setType(Integer.parseInt(scaleAttrValue));
			}
			else if (scaleAttrName.equals("x_rate"))
			{
				scaleAnim.setX_rate(Float.parseFloat(scaleAttrValue));
			}
			else if (scaleAttrName.equals("y_rate"))
			{
				scaleAnim.setY_rate(Float.parseFloat(scaleAttrValue));
			}
			else if (scaleAttrName.equals("x"))
			{
				scaleAnim.setX(Integer.parseInt(scaleAttrValue));
			}
			else if (scaleAttrName.equals("y"))
			{
				scaleAnim.setY(Integer.parseInt(scaleAttrValue));
			}
			else if (scaleAttrName.equals("speed_type"))
			{
				scaleAnim.setSpeed_type(Integer.parseInt(scaleAttrValue));
			}
			else if (scaleAttrName.equals("time"))
			{
				scaleAnim.setDuration(Integer.parseInt(scaleAttrValue));
			}
			else if (scaleAttrName.equals("loop"))
			{
				scaleAnim.setLoop(Integer.parseInt(scaleAttrValue));
			}
			else if (scaleAttrName.equals("zoom_g"))
			{
				scaleAnim.setZoomG(Integer.parseInt(scaleAttrValue));
			}
			else if (scaleAttrName.equals("to_or_by"))
			{
				scaleAnim.setToOrBy(Integer.parseInt(scaleAttrValue));
			}
		}
	}

	private void parseMoveAnimAttr(DataMove moveAnim)
	{
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String moveAttrName = xmlpull.getAttributeName(i).trim();
			String moveAttrValue = xmlpull.getAttributeValue(i).trim();
			if (moveAttrName.equals("ID"))
			{
				moveAnim.setId(Integer.parseInt(moveAttrValue));
			}
			else if (moveAttrName.equals("type"))
			{
				moveAnim.setType(Integer.parseInt(moveAttrValue));
			}
			else if (moveAttrName.equals("x_star"))
			{
				moveAnim.setStart_X(Float.parseFloat(moveAttrValue));
			}
			else if (moveAttrName.equals("y_star"))
			{
				moveAnim.setStart_Y(Float.parseFloat(moveAttrValue));
			}
			else if (moveAttrName.equals("x_end"))
			{
				moveAnim.setEnd_X(Float.parseFloat(moveAttrValue));
			}
			else if (moveAttrName.equals("y_end"))
			{
				moveAnim.setEnd_Y(Float.parseFloat(moveAttrValue));
			}
			else if (moveAttrName.equals("speed_type"))
			{
				moveAnim.setSpeedType(Integer.parseInt(moveAttrValue));
			}
			else if (moveAttrName.equals("time"))
			{
				moveAnim.setDuration(Integer.parseInt(moveAttrValue));
			}
			else if (moveAttrName.equals("loop"))
			{
				moveAnim.setLoop(Integer.parseInt(moveAttrValue));
			}
			else if (moveAttrName.equals("move_g"))
			{
				moveAnim.setMove_G(Integer.parseInt(moveAttrValue));
			}
			else if (moveAttrName.equals("move_line"))
			{

				// String[] strs = moveAttrValue == null ? null :
				// moveAttrValue.split(",");
				// float[] contents = strs == null ? null : new
				// float[strs.length];
				// for (int j = 0; j < strs.length; j++)
				// {
				// contents[j] = Float.parseFloat(strs[j]);
				// }
				moveAnim.setMove_Line(moveAttrValue);
			}
			else if (moveAttrName.equals("line_num"))
			{
				moveAnim.setLine_Num(Integer.parseInt(moveAttrValue));
			}
			else if (moveAttrName.equals("to_or_by"))
			{
				moveAnim.setTo_or_by(Integer.parseInt(moveAttrValue));
			}
		}
	}

	private void parseFrameAnimAttr(DataFrame frameAnim)
	{
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String frameAttrName = xmlpull.getAttributeName(i).trim();
			String frameAttrValue = xmlpull.getAttributeValue(i).trim();
			if (frameAttrName.equals("ID"))
			{
				frameAnim.setId(Integer.parseInt(frameAttrValue));
			}
			else if (frameAttrName.equals("number"))
			{
				frameAnim.setNum(Integer.parseInt(frameAttrValue));
			}
			else if (frameAttrName.equals("texture_src"))
			{
				frameAnim.setSrc(frameAttrValue);
			}
			else if (frameAttrName.equals("file_name"))
			{
				frameAnim.setFileName(frameAttrValue);
			}
			else if (frameAttrName.equals("frame_rate"))
			{
				frameAnim.setRate(Integer.parseInt(frameAttrValue));
			}
			else if (frameAttrName.equals("loop"))
			{
				frameAnim.setLoop(Integer.parseInt(frameAttrValue));
			}
		}
	}

	private void parseGroupAttr(DataSpiritGroup group)
	{
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String gpAttrName = xmlpull.getAttributeName(i).trim();
			String gpAttrValue = xmlpull.getAttributeValue(i).trim();
			if (gpAttrName.equals("group_ID"))
			{
				group.setID(Integer.parseInt(gpAttrValue));
			}
			else if (gpAttrName.equals("content"))
			{
				String[] strs = gpAttrValue == null ? null : gpAttrValue.split(",");
				int[] contents = strs == null ? null : new int[strs.length];
				for (int j = 0; j < strs.length; j++)
				{
					contents[j] = Integer.parseInt(strs[j]);
				}
				group.setContents(contents);
			}
		}
	}

	// <sprite id="1" name="fedfsf" src="fedfsf" x="204" y="20" w="120"
	// h="190" layer="1" touchable="0" visible="0" />
	private void parseSpiritAttr(DataSpirit spirit)
	{
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String spAttrName = xmlpull.getAttributeName(i).trim();
			String spAttrValue = xmlpull.getAttributeValue(i).trim();

			if (spAttrName.equals("id"))
			{
				spirit.setID(Integer.parseInt(spAttrValue));
			}
			else if (spAttrName.equals("name"))
			{
				spirit.setName(spAttrValue);
			}
			else if (spAttrName.equals("src"))
			{
				spirit.setSrc(spAttrValue);
			}
			else if (spAttrName.equals("x"))
			{
				spirit.setX(Float.parseFloat(spAttrValue));
			}
			else if (spAttrName.equals("y"))
			{
				spirit.setY(Float.parseFloat(spAttrValue));
			}
			else if (spAttrName.equals("w"))
			{
				spirit.setWidth(Integer.parseInt(spAttrValue));
			}
			else if (spAttrName.equals("h"))
			{
				spirit.setHeight(Integer.parseInt(spAttrValue));
			}
			else if (spAttrName.equals("layer"))
			{
				spirit.setLayer(Integer.parseInt(spAttrValue));
			}
			else if (spAttrName.equals("touchable"))
			{
				spirit.setTouchable(Integer.parseInt(spAttrValue) != 0);
			}
			else if (spAttrName.equals("visible"))
			{
				spirit.setVisible(Integer.parseInt(spAttrValue) != 0);
			}
		}
	}

	// <listview id="3" name="fedfsf" src="fedfsf" x="204" y="20"
	// w="120" h="190" layer="1" touchable="0" visible="0" orientation="0"
	// item_touchable="0" selection="1" iterm_w="200" iterm_h="400"
	// scroll_speed="2" />
	private void parseListViewAttr(DataListView listview)
	{
		parseSpiritAttr(listview);
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String listAttrName = xmlpull.getAttributeName(i).trim();
			String listAttrValue = xmlpull.getAttributeValue(i).trim();

			if (listAttrName.equals("orientation"))
			{
				listview.setOrientation(Integer.parseInt(listAttrValue));
			}
			else if (listAttrName.equals("item_touchable"))
			{
				listview.setItemTouchable(Integer.parseInt(listAttrValue));
			}
			else if (listAttrName.equals("selection"))
			{
				listview.setSelection(Integer.parseInt(listAttrValue));
			}
			else if (listAttrName.equals("iterm_w"))
			{
				listview.setItemWidth(Integer.parseInt(listAttrValue));
			}
			else if (listAttrName.equals("iterm_h"))
			{
				listview.setItemHeight(Integer.parseInt(listAttrValue));
			}
			else if (listAttrName.equals("scroll_speed"))
			{
				listview.setScrollSpeed(Integer.parseInt(listAttrValue));
			}
		}

	}

	// <touch_board id="3" name="fedfsf" src="fedfsf" x="204" y="20"
	// w="120" h="190" layer="1" touchable="0" visible="0" editable="0" />
	private void parseTouchpadAttr(DataTouchpad touchpad)
	{
		parseSpiritAttr(touchpad);
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String padAttrName = xmlpull.getAttributeName(i).trim();
			String padAttrValue = xmlpull.getAttributeValue(i).trim();
			if (padAttrName.equals("editable"))
			{
				touchpad.setEditable(Integer.parseInt(padAttrValue) != 0);
			}
		}
	}

	// <scrollbar id="3" name="fedfsf" src="fedfsf" x="204" y="20"
	// w="120" h="190" layer="1" orientation="0" touchable="0" start_x="25"
	// start_y="45" scroll_speed="45" />
	private void parseScrollbarAttr(DataScrollbar scrollbar)
	{
		parseSpiritAttr(scrollbar);
		int count = getCurrAttrsCount();
		for (int i = 0; i < count; i++)
		{
			String scrollAttrName = xmlpull.getAttributeName(i).trim();
			String scrollAttrValue = xmlpull.getAttributeValue(i).trim();
			if (scrollAttrName.equals("orientation"))
			{
				scrollbar.setOrientation(Integer.parseInt(scrollAttrValue));
			}
			else if (scrollAttrName.equals("start_x"))
			{
				scrollbar.setStartX(Float.parseFloat(scrollAttrValue));
			}
			else if (scrollAttrName.equals("start_y"))
			{
				scrollbar.setStartY(Float.parseFloat(scrollAttrValue));
			}
			else if (scrollAttrName.equals("scroll_speed"))
			{
				scrollbar.setScrollSpeed(Integer.parseInt(scrollAttrValue));
			}
		}
	}

	private int getCurrAttrsCount()
	{
		return xmlpull.getAttributeCount();
	}
}
