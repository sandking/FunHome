package com.tpadsz.home.jni.page;

import java.util.HashMap;

import com.tpadsz.home.view.Program;
import com.tpadsz.home.xmlparser.data.DataPage;

public class PageManager
{

	public final static String TAG = PageManager.class.getSimpleName();

	/**
	 * The collection of the pages. The key is the pageId.
	 */
	final HashMap<Integer, BasePage> MAPS_ID_PAGE = new HashMap<Integer, BasePage>();

	private static PageManager mPageManagerSelf;

	private Program mProgram;
	private PageChangedListener mPageChangedListener;

	public static PageManager getInstance(Program program)
	{
		return mPageManagerSelf == null ? new PageManager(program) : mPageManagerSelf;
	}

	private PageManager(Program program)
	{
		this.mProgram = program;
	}

	public void setPageChangedListener(PageChangedListener pageChangedListener)
	{
		this.mPageChangedListener = pageChangedListener;
	}

	DataPage getDataPage(int pageID)
	{
		return mProgram.getParserHelper().getPage(pageID);
	}

	BasePage createPage(int pageID, Class<? extends BasePage> pageCls)
	{
		BasePage mPage = null;
		String pageName = pageCls.getSimpleName();
		try
		{
			if (!MAPS_ID_PAGE.containsKey(pageID))
			{
				mPage = pageCls.newInstance();
				mPage.setContext(mProgram);
				mPage.setPageID(pageID);
				mPage.setPageName(pageName);
				mPage.setPageChangedListener(mPageChangedListener);
				MAPS_ID_PAGE.put(pageID, mPage);
				mPage.onCreate();
			}
			else mPage = MAPS_ID_PAGE.get(pageID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mPage;
	}

	@SuppressWarnings("unchecked")
	public BasePage createPage(int pageID)
	{
		Class<? extends BasePage> basePageCls = null;
		BasePage mBasePage = null;
		try
		{
			basePageCls = (Class<? extends BasePage>) Class.forName(Program.PACKAGE_PROGRAM + getDataPage(pageID).getName());
			mBasePage = createPage(pageID, basePageCls);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return mBasePage;
	}

	public BasePage getPage(int pageID)
	{
		return MAPS_ID_PAGE.get(pageID);
	}

	public void release(int pageID)
	{
		BasePage mPage = MAPS_ID_PAGE.remove(pageID);
		if (mPage != null) mPage.onDestroy();
	}

}
