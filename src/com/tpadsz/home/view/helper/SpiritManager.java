package com.tpadsz.home.view.helper;

import java.util.HashMap;

import android.graphics.Bitmap;

import com.tpadsz.home.jni.NativeInterface;
import com.tpadsz.home.view.Program;
import com.tpadsz.home.view.Spirit;
import com.tpadsz.home.widget.ListView;
import com.tpadsz.home.widget.Palette;
import com.tpadsz.home.widget.ScrollBar;
import com.tpadsz.home.xmlparser.spirits.DataListView;
import com.tpadsz.home.xmlparser.spirits.DataScrollbar;
import com.tpadsz.home.xmlparser.spirits.DataSpirit;
import com.tpadsz.home.xmlparser.spirits.DataTouchpad;

public class SpiritManager
{

	HashMap<Integer, Spirit> MAPS_ID_SPIRIT = new HashMap<Integer, Spirit>();

	HashMap<Integer, Spirit> MAPS_MANUAL_ID_SPIRIT = new HashMap<Integer, Spirit>();

	private static SpiritManager manager;
	private Program mProgram;
	private XMLParserHelper mHelper;

	private SpiritManager(Program program)
	{
		mProgram = program;
		mHelper = mProgram.getParserHelper();
	}

	public static SpiritManager getInstance(Program program)
	{
		if (manager == null) manager = new SpiritManager(program);
		return manager;
	}

	public void initSpirits()
	{
		createSpirits();
	}

	public Spirit createSpirit(int id)
	{
		if (MAPS_ID_SPIRIT.containsKey(id)) return MAPS_ID_SPIRIT.get(id);

		DataSpirit dataSpirit = mHelper.DATA_SPIRIT_POOLS.get(id);
		if (dataSpirit == null) return null;
		String name = dataSpirit.getName();
		String src = dataSpirit.getSrc();
		float x = dataSpirit.getX();
		float y = dataSpirit.getY();
		int width = dataSpirit.getWidth();
		int height = dataSpirit.getHeight();
		int layer = dataSpirit.getLayer();
		boolean touchable = dataSpirit.isTouchable();
		boolean visible = dataSpirit.isVisible();

		Spirit mSpirit = null;
		if (dataSpirit instanceof DataListView)
		{
			DataListView dataListView = (DataListView) dataSpirit;
			mSpirit = createListViewByXML(dataListView, id, name, src, x, y, width, height, layer, touchable, visible);
		}
		else if (dataSpirit instanceof DataScrollbar)
		{
			DataScrollbar dataScrollBar = (DataScrollbar) dataSpirit;
			mSpirit = createScrollbarByXML(dataScrollBar, id, name, src, x, y, width, height, layer, touchable, visible);
		}
		else if (dataSpirit instanceof DataTouchpad)
		{
			DataTouchpad dataTouchpad = (DataTouchpad) dataSpirit;
			mSpirit = createPaletteByXML(dataTouchpad, id, name, src, x, y, width, height, layer, touchable, visible);
		}
		else if (dataSpirit instanceof DataSpirit)
		{
			mSpirit = createSpiritByXML(id, name, src, x, y, width, height, layer, touchable, visible);
		}
		MAPS_ID_SPIRIT.put(id, mSpirit);
		return mSpirit;
	}

	private void createSpirits()
	{
		for (int id : mHelper.DATA_SPIRIT_POOLS.keySet())
			createSpirit(id);
	}

	public void preloadSrcMemory(String path)
	{
		NativeInterface.readPicToMemory(path);
	}

	public HashMap<Integer, Spirit> getFileSpiritMap()
	{
		return MAPS_ID_SPIRIT;
	}

	public HashMap<Integer, Spirit> getManualSpiritMap()
	{
		return MAPS_MANUAL_ID_SPIRIT;
	}

	protected Spirit createSpiritByXML(int id, String name, String src, float x, float y, int width, int height, int layer, boolean touchable, boolean visible)
	{
		return new Spirit(id, name, src, x, y, width, height, layer, touchable, visible);
	}

	protected ListView createListViewByXML(DataListView dataListView, int id, String name, String src, float x, float y, int width, int height, int layer, boolean touchable, boolean visible)
	{
		int orientation = dataListView.getOrientation();
		int item_touchable = dataListView.getItemTouchable();
		int selection = dataListView.getSelection();
		int item_w = dataListView.getItemWidth();
		int item_h = dataListView.getItemHeight();
		int scrollSpeed = dataListView.getScrollSpeed();
		return new ListView(id, name, src, x, y, width, height, layer, touchable, visible, orientation, item_touchable, selection, item_w, item_h, scrollSpeed);
	}

	protected ScrollBar createScrollbarByXML(DataScrollbar dataScrollbar, int id, String name, String src, float x, float y, int width, int height, int layer, boolean touchable, boolean visible)
	{
		float startx = dataScrollbar.getStartX();
		int scrollspeed = dataScrollbar.getScrollSpeed();
		return new ScrollBar(id, name, src, x, y, width, height, layer, touchable, visible, startx, scrollspeed);
	}

	protected Palette createPaletteByXML(DataTouchpad dataTouchpad, int id, String name, String src, float x, float y, int width, int height, int layer, boolean touchable, boolean visible)
	{
		boolean editable = dataTouchpad.getEditable();
		return new Palette(id, name, src, x, y, width, height, layer, visible, editable);
	}

	protected boolean isExist(int id)
	{
		return MAPS_ID_SPIRIT.containsKey(id) | MAPS_MANUAL_ID_SPIRIT.containsKey(id);
	}

	protected void judgeOnly(int id)
	{
		if (isExist(id)) throw new RuntimeException("The spirit id had been used!!!");
	}

	public Spirit obtainSpiritById(int id)
	{
		return MAPS_ID_SPIRIT.get(id);
	}

	public Spirit manualCreateSpirit(int id, Bitmap bmp,int widths,int heights, float x, float y, int layer, boolean isVisible, boolean isTouchable)
	{
		judgeOnly(id);
		Spirit spirit = new Spirit(id, bmp,widths,heights, x, y, layer, isVisible, isTouchable);
		MAPS_MANUAL_ID_SPIRIT.put(id, spirit);
		return spirit;
	}

	public Palette manualCreateTouchpad(int id, int width, int height, float x, float y, int layer, boolean visible)
	{
		judgeOnly(id);
		Palette palette = new Palette(id, width, height, x, y, layer, visible);
		MAPS_MANUAL_ID_SPIRIT.put(id, palette);
		return palette;
	}

	public ListView manualCreateListView(int id, int width, int height, float x, float y, int layer, int orientation)
	{
		judgeOnly(id);
		ListView listView = new ListView(id, x, y, width, height, layer, orientation);
		MAPS_MANUAL_ID_SPIRIT.put(id, listView);
		return listView;
	}

	public ScrollBar manualCreateScrollbar(int id, String src, int width, int height, float x, float y, int layer, boolean touchable, boolean visible)
	{
		judgeOnly(id);
		ScrollBar scrollBar = new ScrollBar(id, src, x, y, width, height, layer, touchable, visible);
		MAPS_MANUAL_ID_SPIRIT.put(id, scrollBar);
		return scrollBar;
	}
}
