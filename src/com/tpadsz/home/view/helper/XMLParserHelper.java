package com.tpadsz.home.view.helper;

import java.io.InputStream;
import java.util.HashMap;
import com.tpadsz.home.xmlparser.ProgramParser;
import com.tpadsz.home.xmlparser.animation.DataAlpha;
import com.tpadsz.home.xmlparser.animation.DataFrame;
import com.tpadsz.home.xmlparser.animation.DataMove;
import com.tpadsz.home.xmlparser.animation.DataRotate;
import com.tpadsz.home.xmlparser.animation.DataScale;
import com.tpadsz.home.xmlparser.data.DataAnimation;
import com.tpadsz.home.xmlparser.data.DataFrameFile;
import com.tpadsz.home.xmlparser.data.DataMoveline;
import com.tpadsz.home.xmlparser.data.DataPage;
import com.tpadsz.home.xmlparser.data.DataProgram;
import com.tpadsz.home.xmlparser.spirits.DataSpirit;
import com.tpadsz.home.xmlparser.spirits.DataSpiritGroup;

public class XMLParserHelper
{
	public final HashMap<Integer, DataAlpha> ANIMATION_ALPHA_POOLS;
	public final HashMap<Integer, DataFrame> ANIMATION_FRAME_POOLS;
	public final HashMap<Integer, DataMove> ANIMATION_MOVE_POOLS;
	public final HashMap<Integer, DataRotate> ANIMATION_ROTATE_POOLS;
	public final HashMap<Integer, DataScale> ANIMATION_SCALE_POOLS;
	public final HashMap<Integer, DataSpirit> DATA_SPIRIT_POOLS;
	public final HashMap<Integer, DataSpiritGroup> DATA_GROUP_POOLS;
	public final HashMap<Integer, DataPage> DATA_PAGE_POOLS;
	public final HashMap<Integer, DataAnimation> DATA_ANIMATION_POOLS;
	public final HashMap<String, DataFrameFile> DATA_FRAMEFILE_POOLS;
	public final HashMap<String, DataMoveline> DATA_MOVELINE_POOLS;
	public final DataProgram DATA_PROGRAM;
	public final ProgramParser xmlParser;

	private XMLParserHelper(InputStream inputStream)
	{
		xmlParser = ProgramParser.getInstance(inputStream);
		DATA_PROGRAM = xmlParser.parseProgram();
		DATA_SPIRIT_POOLS = xmlParser.parseSpirits();
		DATA_GROUP_POOLS = xmlParser.parseGroup();
		ANIMATION_FRAME_POOLS = xmlParser.parseFrameAnim();
		ANIMATION_MOVE_POOLS = xmlParser.parseMoveAnim();
		ANIMATION_SCALE_POOLS = xmlParser.parseScaleAnim();
		ANIMATION_ROTATE_POOLS = xmlParser.parseRotateAnim();
		ANIMATION_ALPHA_POOLS = xmlParser.parseAlphaAnim();
		DATA_MOVELINE_POOLS = xmlParser.parseMoveLine();
		DATA_FRAMEFILE_POOLS = xmlParser.parseFrameFile();
		DATA_ANIMATION_POOLS = xmlParser.parseAnimations();
		DATA_PAGE_POOLS = xmlParser.parsePages();
	}

	private static XMLParserHelper parserHelper;

	public static XMLParserHelper getInstance(InputStream inputStream)
	{
		if (parserHelper == null) parserHelper = new XMLParserHelper(inputStream);
		return parserHelper;
	}

	public DataPage getPage(int id)
	{
		return DATA_PAGE_POOLS.get(id);
	}

	public DataPage getPage(String name)
	{
		DataPage dataPage = null;
		for (int id : DATA_PAGE_POOLS.keySet())
		{
			DataPage mDataPage = DATA_PAGE_POOLS.get(id);
			if (name.equals(mDataPage.getName()))
			{
				dataPage = mDataPage;
				break;
			}
		}
		return dataPage;
	}

}
