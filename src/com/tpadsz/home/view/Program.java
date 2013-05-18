package com.tpadsz.home.view;

import java.io.InputStream;
import java.util.HashMap;

import android.util.Log;
import com.tpadsz.home.BaseFunHome;
import com.tpadsz.home.jni.NativeInterface;
import com.tpadsz.home.jni.page.BasePage;
import com.tpadsz.home.jni.page.PageChangedListener;
import com.tpadsz.home.jni.page.PageManager;
import com.tpadsz.home.view.helper.AnimationManager;
import com.tpadsz.home.view.helper.GroupManager;
import com.tpadsz.home.view.helper.SpiritManager;
import com.tpadsz.home.view.helper.XMLParserHelper;
import com.tpadsz.home.widget.SpiritGroup;
import com.tpadsz.home.xmlparser.animation.DataFrame;
import com.tpadsz.home.xmlparser.data.DataPage;
import com.tpadsz.home.xmlparser.data.DataProgram;
import com.tpadsz.home.xmlparser.spirits.DataSpirit;

public class Program implements PageChangedListener
{

	// ===========================================================
	// TODO Constants
	// ===========================================================
	public final static String TAG = Program.class.getName();
	public final static int DEFAULT_PAGE_ID = 0;

	// ===========================================================
	// TODO Fields
	// ===========================================================
	private boolean _DEBUG_;
	private int INIT_PAGE_ID = 1;
	private BaseFunHome mFunHome;
	private int mProgram_ID;
	private int mCurrentPageID, mNextPageID;

	private static LoadFileCallback mLoadFileCallback;
	public static int ID_PROGRAM;
	public static String NAME_PROGRAM;
	public static String VERSION_PROGRAM;
	public static String PATH_PROGRAM;
	public static String PACKAGE_PROGRAM;

	private DataProgram mDataProgram;
	private XMLParserHelper mXmlParserHelper;
	private PageManager mPageManager;
	private SpiritManager mSpiritManager;
	private GroupManager mGroupManager;
	private AnimationManager mAnimationManager;

	private InputStream configStream;

	// ===========================================================
	// TODO Constructors
	// ===========================================================
	public Program(BaseFunHome funHome, InputStream inputStream)
	{
		mFunHome = funHome;
		configStream = inputStream;
		initObject();
		initData();
	}

	void initObject()
	{
		mXmlParserHelper = XMLParserHelper.getInstance(configStream);

		mPageManager = PageManager.getInstance(this);
		mSpiritManager = SpiritManager.getInstance(this);

		mGroupManager = GroupManager.getInstance(this);
		mAnimationManager = AnimationManager.getInstance(this);
	}

	void initData()
	{
		mDataProgram = mXmlParserHelper.DATA_PROGRAM;
		if (mDataProgram != null)
		{
			ID_PROGRAM = mDataProgram.getId();
			NAME_PROGRAM = mDataProgram.getName();
			VERSION_PROGRAM = mDataProgram.getVersion();
			PATH_PROGRAM = mDataProgram.getSrc();
			PACKAGE_PROGRAM = mDataProgram.getPkg();
		}
		mPageManager.setPageChangedListener(this);
	}

	// ===========================================================
	// TODO Getter & Setter
	// ===========================================================
	public void setInitPageId(int id)
	{
		INIT_PAGE_ID = id;
	}

	public void setDebug(boolean flag)
	{
		_DEBUG_ = flag;
	}

	public int getCurrentPageID()
	{
		return mCurrentPageID;
	}

	public BasePage getCurrentPage()
	{
		return mPageManager.getPage(mCurrentPageID);
	}

	public BaseFunHome getFunHome()
	{
		return mFunHome;
	}

	public XMLParserHelper getParserHelper()
	{
		return mXmlParserHelper;
	}

	public AnimationManager getAnimationManager()
	{
		return mAnimationManager;
	}

	public SpiritManager getSpiritManager()
	{
		return mSpiritManager;
	}

	public GroupManager getGroupManager()
	{
		return mGroupManager;
	}

	public HashMap<Integer, Spirit> getSpirits()
	{
		return mSpiritManager.getFileSpiritMap();
	}

	public HashMap<Integer, Spirit> getManualSpirits()
	{
		return mSpiritManager.getManualSpiritMap();
	}

	public HashMap<Integer, SpiritGroup> getGroups()
	{
		return mGroupManager.getGroups();
	}

	// ===========================================================
	// TODO Methods
	// ===========================================================

	public void setLoadFileCallback(LoadFileCallback mCallback)
	{
		mLoadFileCallback = mCallback;
	}

	public void initProgram()
	{
		mProgram_ID = NativeInterface.initProgram(PATH_PROGRAM);
	}

	public void destroyProgram()
	{
		NativeInterface.destroyProgram(mProgram_ID);
	}

	void preloadInitPageSpirits(int page)
	{
		DataPage mDataPage = mXmlParserHelper.getPage(page);
		if (mDataPage != null)
		{
			int[] spirits = mDataPage.getSpirits();
			if (spirits != null)
			{
				int len = spirits.length;
				for (int i = 0; i < len; i++)
				{
					DataSpirit spirit = mXmlParserHelper.DATA_SPIRIT_POOLS.get(spirits[i]);
					if (spirit != null)
					{
						String src = spirit.getSrc();
						mSpiritManager.preloadSrcMemory(src);
						if (_DEBUG_) Log.e(TAG, String.format("initPage %d , load spirit %d [%s]", page, spirit.getID(), src));
					}
				}
			}
		}
	}

	public void initPage()
	{
		mCurrentPageID = INIT_PAGE_ID;
		mPageManager.createPage(mCurrentPageID).onStart(DEFAULT_PAGE_ID);
	}

	public void preloadFrame()
	{
		for (int id : mXmlParserHelper.ANIMATION_FRAME_POOLS.keySet())
		{
			DataFrame dataFrame = mXmlParserHelper.ANIMATION_FRAME_POOLS.get(id);
			String src = dataFrame.getSrc();
			loadFrameTexture(src);
		}
	}

	public void gotoPage(int pageID)
	{
		mNextPageID = pageID;
		mPageManager.getPage(mCurrentPageID).onPause(mNextPageID);
	}

	public void gotoPage(Class<? extends BasePage> cls)
	{
		gotoPage(mXmlParserHelper.getPage(cls.getSimpleName()).getId());
	}

	protected void loadFrameTexture(String src)
	{
		NativeInterface.loadFramesTexture(src);
	}

	@Override
	public void onPageInto(int pageID, int prePageID)
	{
		mCurrentPageID = pageID;
		mPageManager.release(prePageID);
		mPageManager.getPage(mCurrentPageID).onResume();
	}

	@Override
	public void onPageOut(int pageID, int nextPageID)
	{
		mPageManager.getPage(mCurrentPageID).onStop();
		mPageManager.createPage(mNextPageID).onStart(mCurrentPageID);
	}

	public static void onLoadFileCallback(boolean result)
	{
		if (mLoadFileCallback != null) mLoadFileCallback.loadFileCallback(result);
	}

	// ===========================================================
	// TODO Inner and Anonymous Classes
	// ===========================================================
	public interface LoadFileCallback
	{
		public void loadFileCallback(boolean result);
	}
}
