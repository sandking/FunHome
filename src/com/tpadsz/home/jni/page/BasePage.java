package com.tpadsz.home.jni.page;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import com.tpadsz.home.BaseFunHome;
import com.tpadsz.home.RequestTask;
import com.tpadsz.home.jni.page.AnimationFlow.AnimationFlowCallback;
import com.tpadsz.home.jni.page.AnimationFlow.State;
import com.tpadsz.home.jni.page.Event.EventListener;
import com.tpadsz.home.jni.page.annotation.SpiritInject;
import com.tpadsz.home.view.Program;
import com.tpadsz.home.view.Spirit;
import com.tpadsz.home.view.Spirit.OnAnimationCallback;
import com.tpadsz.home.view.action.Action;
import com.tpadsz.home.view.action.Animation;
import com.tpadsz.home.view.action.RotateAction;
import com.tpadsz.home.view.action.RotateAction.Axis;
import com.tpadsz.home.view.action.TranslateAction;
import com.tpadsz.home.view.helper.AnimationManager;
import com.tpadsz.home.view.helper.GroupManager;
import com.tpadsz.home.view.helper.SpiritManager;
import com.tpadsz.home.view.helper.XMLParserHelper;
import com.tpadsz.home.widget.SpiritGroup;
import com.tpadsz.home.xmlparser.data.DataPage;

public class BasePage implements PageCondition, PageOperation, EventListener, OnAnimationCallback, AnimationFlowCallback
{
	// ===========================================================
	// TODO Constants
	// ===========================================================
	public final static int PAGE_DEFAULT = -1;
	public final static int KEYCODE_DEFAULT = -1;

	// ===========================================================
	// TODO Fields
	// ===========================================================

	private boolean _DEBUG_ = true;

	/**
	 * Contain all the call-back method name.<br>
	 * KEY[animation_id] --- VALUE[method_name].
	 */
	protected final static HashMap<Integer, String> MAPS_RUNKEY_METHODNAME = new HashMap<Integer, String>();

	/**
	 * Contains all the call-back method_params.<br>
	 * Key[animation_key] --- Value[method_params].
	 */
	protected final static HashMap<Integer, Object[]> MAPS_RUNKEY_METHODPARAMS = new HashMap<Integer, Object[]>();

	private PageChangedListener mPageChangedListener;
	private Program mProgram;
	private BaseFunHome mFunHome;

	private AnimationManager mAnimationManager;
	private SpiritManager mSpiritManager;
	private GroupManager mGroupManager;
	private XMLParserHelper mXmlParserHelper;

	protected int mPageID;
	protected int mInCommingPageID;
	protected int mOutGoingPageID;
	protected String mPageName;

	protected Event mEvent;
	protected AnimationFlow mAnimationFlow;

	// ===========================================================
	// TODO Getter & Setter
	// ===========================================================
	public void setDebug(boolean flag)
	{
		_DEBUG_ = flag;
		mAnimationFlow.setDebug(flag);
	}

	void setPageID(int pageID)
	{
		this.mPageID = pageID;
	}

	void setPageName(String pageName)
	{
		this.mPageName = pageName;
	}

	void setPageChangedListener(PageChangedListener pageChangedListener)
	{
		this.mPageChangedListener = pageChangedListener;
	}

	public void setContext(Program program)
	{
		mProgram = program;
		mFunHome = program.getFunHome();

		mXmlParserHelper = program.getParserHelper();
		mAnimationManager = program.getAnimationManager();
		mSpiritManager = program.getSpiritManager();
		mGroupManager = program.getGroupManager();
	}

	public Handler getHandler()
	{
		return mFunHome.getGestureHandler();
	}

	public Resources getResources()
	{
		return mFunHome.getResources();
	}

	public BaseFunHome getFunHome()
	{
		return mFunHome;
	}

	public XMLParserHelper getParserHelper()
	{
		return mXmlParserHelper;
	}

	public int getPageID()
	{
		return mPageID;
	}

	public String getPageName()
	{
		return mPageName;
	}

	public int getCurPageID()
	{
		return mProgram.getCurrentPageID();
	}

	// ===========================================================
	// TODO Implement Method
	// ===========================================================

	@Override
	public void onCreate()
	{
		if (_DEBUG_) Log.e(mPageName, String.format("onCreate running in %s", Thread.currentThread().getName()));
		mEvent = new Event(getHandler(), this);
		mAnimationFlow = new AnimationFlow(this, this);
		createSpirits();
		createGroups();
		initControls();
	}

	@Override
	public void onStart(int inComming)
	{
		if (_DEBUG_) Log.e(mPageName, String.format("onStart running in %s", Thread.currentThread().getName()));
		mInCommingPageID = inComming;
		mOutGoingPageID = PAGE_DEFAULT;
		mAnimationFlow.intoAnimationStart(inComming);
	}

	@Override
	public void onResume()
	{
		if (_DEBUG_) Log.e(mPageName, String.format("onResume running in %s", Thread.currentThread().getName()));
		// enableAnnotationSpiritTouchable();
		enableAllSpiritTouchable();
	}

	@Override
	public void onPause(int outGoing)
	{
		if (_DEBUG_) Log.e(mPageName, String.format("onPause running in %s", Thread.currentThread().getName()));
		mOutGoingPageID = outGoing;
		mInCommingPageID = PAGE_DEFAULT;
		// disableAnnotationSpiritTouchable();
		disableAnnotationSpiritTouchable();
		mAnimationFlow.outAnimationStart(outGoing);
	}

	@Override
	public void onStop()
	{
		if (_DEBUG_) Log.e(mPageName, String.format("onStop running in %s", Thread.currentThread().getName()));
		clearStatementSpirits();
	}

	@Override
	public void onDestroy()
	{
		if (_DEBUG_) Log.e(mPageName, String.format("onDestroy running in %s", Thread.currentThread().getName()));
		releasePage(mPageID, mOutGoingPageID);
	}

	@Override
	public void onPlayedBegin(int mSpriteId, int mAnimationId, String flowKey, int key)
	{

	}

	@Override
	public void onPlayedEnd(int mSpriteId, int mAnimationId, String flowKey, int key)
	{
		mAnimationFlow.onAnimationCallback(flowKey);
		invokeMethod(key);
	}

	@Override
	public void onPlayedError(int mSpriteId, int mAnimationId, String flowKey, int key)
	{

	}

	@Override
	public void onAnimationFlowOver(State state)
	{
		switch (state)
		{
		case FLOW_INTO:
			handleResume();
			break;
		case FLOW_OUT:
			handleStop();
			break;
		}
	}

	@Override
	public Spirit findSpiritByid(int id)
	{
		Spirit fileSpirit = mProgram.getSpirits().get(id);
		Spirit manualSpirit = mProgram.getManualSpirits().get(id);
		return fileSpirit == null ? manualSpirit : fileSpirit;
	}

	@Override
	public SpiritGroup findGroupByid(int id)
	{
		return mProgram.getGroups().get(id);
	}

	@Override
	public void gotoPage(int pageID)
	{
		mProgram.gotoPage(pageID);
	}

	public void gotoPage(Class<? extends BasePage> cls)
	{
		mProgram.gotoPage(cls);
	}

	@Override
	public int playAnimation(int id, Animation animation, long delay)
	{
		requestDelay(obtainTask(FLAG_TASK_SPIRIT_ANIMATION, id, animation, null), delay);
		return animation.getKey();
	}

	@Override
	public int playGroupAnimation(int id, Animation animation, long delay)
	{
		requestDelay(obtainTask(FLAG_TASK_GROUP_ANIMATION, id, animation, null), delay);
		return animation.getKey();
	}

	@Override
	public int playAction(int id, Action action, long delay)
	{
		requestDelay(obtainTask(FLAG_TASK_SPIRIT_ACTION, id, null, action), delay);
		return action.getKey();
	}

	@Override
	public int playGroupAction(int id, Action action, long delay)
	{
		requestDelay(obtainTask(FLAG_TASK_GROUP_ACTION, id, null, action), delay);
		return action.getKey();
	}

	@Override
	public boolean onDown(int id, MotionEvent e)
	{
		return false;
	}

	@Override
	public void onUp(int id, MotionEvent e)
	{

	}

	@Override
	public void onShowPress(int id, MotionEvent e)
	{

	}

	@Override
	public boolean onSingleTapUp(int id, MotionEvent e)
	{
		return false;
	}

	@Override
	public boolean onScroll(int id, MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		return false;
	}

	@Override
	public void onLongPress(int id, MotionEvent e)
	{

	}

	@Override
	public boolean onFling(int id, MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		return false;
	}

	// ===========================================================
	// TODO (Private | Protected) Method
	// ===========================================================

	final static int FLAG_TASK_SPIRIT_ACTION = 0x1000;
	final static int FLAG_TASK_SPIRIT_ANIMATION = 0x2000;
	final static int FLAG_TASK_GROUP_ACTION = 0x3000;
	final static int FLAG_TASK_GROUP_ANIMATION = 0x4000;

	final static int FLAG_REGISTER_ANIMATIONCALLBACK = 0x0100;
	final static int FLAG_REGISTER_TOUCHLISTENER = 0x0200;

	private RequestTask obtainTask(final int flag, final int id, final Animation animation, final Action action)
	{

		RequestTask mRequestTask = new RequestTask()
		{
			Spirit mSpirit = null;

			SpiritGroup mSpiritGroup = null;

			@Override
			public void run()
			{
				switch (flag)
				{
				case FLAG_TASK_SPIRIT_ACTION:
					mSpirit = findSpiritByid(id);
					if (mSpirit == null) Log.e(mPageName, String.format("mSpirit is null ! id is %d", id));
					if (action == null) Log.e(mPageName, "action is null");
					mSpirit.playAction(action);
					break;
				case FLAG_TASK_SPIRIT_ANIMATION:
					mSpirit = findSpiritByid(id);
					if (mSpirit == null) Log.e(mPageName, String.format("mSpirit is null ! id is %d", id));
					if (animation == null) Log.e(mPageName, "animation is null");
					mSpirit.playAnimation(animation);
					break;
				case FLAG_TASK_GROUP_ACTION:
					mSpiritGroup = findGroupByid(id);
					if (mSpiritGroup == null) Log.e(mPageName, String.format("mSpiritGroup is null ! id is %d", id));
					if (action == null) Log.e(mPageName, "action is null");
					mSpiritGroup.playAction(action);
					break;
				case FLAG_TASK_GROUP_ANIMATION:

					mSpiritGroup = findGroupByid(id);
					if (mSpiritGroup == null) Log.e(mPageName, String.format("mSpiritGroup is null ! id is %d", id));
					if (animation == null) Log.e(mPageName, "animation is null");
					mSpiritGroup.playAnimation(animation);
					break;
				}
			}
		};
		mRequestTask.setType(RequestTask.TYPE_RUNNING_IN_GLQUEUE);
		return mRequestTask;
	}

	public void startActivity(Intent intent)
	{
		mFunHome.startActivity(intent);
	}

	/**
	 * Request a task to run. It can be running in different thread.
	 * 
	 * @see RequestTask
	 * 
	 * @param task
	 * @param delay
	 */
	public void requestDelay(RequestTask task, long delay)
	{
		mFunHome.requestDelay(task, delay);
	}

	public void request(RequestTask task)
	{
		requestDelay(task, 0);
	}

	public int playAnimation(int id, Animation animation)
	{
		return playAnimation(id, animation, 0);
	}

	public int playGroupAnimation(int id, Animation animation)
	{
		return playGroupAnimation(id, animation, 0);
	}

	public int playAction(int id, Action action)
	{
		return playAction(id, action, 0);
	}

	public int playGroupAction(int id, Action action)
	{
		return playGroupAction(id, action, 0);
	}

	public Animation obtainAnimation(int id, boolean order, String flowKey)
	{
		return mAnimationManager.obtainAnimation(id, order, flowKey);
	}

	public Animation obtainAnimation(int id, boolean order)
	{
		return obtainAnimation(id, order, null);
	}

	public Animation obtainAnimation(int id)
	{
		return obtainAnimation(id, true, null);
	}

	private int[] getAllSpiritId()
	{
		return mXmlParserHelper.getPage(mPageID).getSpirits();
	}

	private int[] getAllGroupId()
	{
		return mXmlParserHelper.getPage(mPageID).getGroups();
	}

	/**
	 * Create the spirits in this page.
	 */
	private void createSpirits()
	{
		int[] spiritIDs = getAllSpiritId();
		if (spiritIDs == null) return;
		int count = spiritIDs.length;
		for (int i = 0; i < count; i++)
		{
			Spirit mSpirit = mSpiritManager.createSpirit(spiritIDs[i]);
			mSpirit.setOnAnimationCallback(BasePage.this);
			mSpirit.setVisible(true);
		}
	}

	/**
	 * Create the groups in this page.
	 */
	private void createGroups()
	{
		int[] groups = getAllGroupId();
		if (groups != null)
		{
			int groupCount = groups.length;
			for (int i = 0; i < groupCount; i++)
			{
				mGroupManager.createGroup(groups[i]);
			}
		}
	}

	private Spirit[] idToSpirit(int[] spiritIDs)
	{
		Spirit[] spirits = null;
		if (spiritIDs != null)
		{
			int spiritsCount = spiritIDs.length;
			spirits = new Spirit[spiritsCount];
			for (int index = 0; index < spiritsCount; index++)
			{
				spirits[index] = findSpiritByid(spiritIDs[index]);
			}
		}
		return spirits;
	}

	/**
	 * Get all the spirits in this page.
	 * 
	 * @return spirits.
	 */
	protected Spirit[] getAllSpirits()
	{
		return idToSpirit(getAllSpiritId());
	}

	protected void handleResume()
	{
		mPageChangedListener.onPageInto(mPageID, mInCommingPageID);
	}

	protected void handleStop()
	{
		mPageChangedListener.onPageOut(mPageID, mOutGoingPageID);
	}

	/**
	 * Set the touchable for spirits.
	 * 
	 * @param touchable
	 * @param spirits
	 */
	protected void setTouchable(boolean touchable, Spirit... spirits)
	{
		for (Spirit spirit : spirits)
		{
			if (spirit == null) continue;
			spirit.setTouchable(touchable);
		}
	}

	protected void setTouchable(boolean touchable, int... spirits)
	{
		setTouchable(touchable, idToSpirit(spirits));
	}

	protected void setTouchListener(Spirit... spirits)
	{
		for (Spirit spirit : spirits)
		{
			if (spirit == null) continue;
			spirit.setOnTouchListener(mEvent);
		}
	}

	protected void setTouchListener(int... spirits)
	{
		setTouchListener(idToSpirit(spirits));
	}

	protected void showAll()
	{
		for (Integer id : getAllSpiritId())
		{
			Spirit sp = findSpiritByid(id);
			if (sp == null) continue;
			sp.setVisible(true);
		}
	}

	protected void disappearAll()
	{
		for (Integer id : getAllSpiritId())
		{
			Spirit sp = findSpiritByid(id);
			if (sp == null) continue;
			sp.setVisible(false);
		}
	}

	/**
	 * Set the visible for spirits.
	 * 
	 * @param visible
	 * @param spirits
	 */
	protected void handleShowSprites(boolean visible, Spirit... spirits)
	{
		for (Spirit spirit : spirits)
		{
			if (spirit == null) continue;
			spirit.setVisible(visible);
		}
	}

	protected void handleShowSprites(boolean visible, int... spirits)
	{
		handleShowSprites(visible, idToSpirit(spirits));
	}

	protected boolean register(int key, String mName, Object[] objects)
	{
		boolean register_state = MAPS_RUNKEY_METHODNAME.put(key, mName) == null;
		if (objects != null) MAPS_RUNKEY_METHODPARAMS.put(key, objects);
		return register_state;
	}

	/**
	 * Take care of this key , invoked the method at the key be received.
	 * 
	 * @param key
	 *            the animation key.
	 * @param mName
	 *            the method name.
	 * @return if true - register successfully ,otherwise the key has been
	 *         exists.
	 */
	protected boolean register(int key, String mName)
	{
		return register(key, mName, null);
	}

	/**
	 * Don't need to care about this key . It's usually been invoked.
	 * 
	 * @param key
	 *            the key will be unregistered.
	 * @return if true - unregister successfully , otherwise there is no key.
	 */
	protected boolean unregister(int key)
	{
		return MAPS_RUNKEY_METHODNAME.remove(key) != null || MAPS_RUNKEY_METHODPARAMS.remove(key) != null;
	}

	/**
	 * Invoked the specified method by name.
	 * 
	 * @param name
	 *            the method name
	 * @param objects
	 *            params.
	 * @return if true - invoked correctly.
	 */
	private boolean invokeMethod(String name, Object[] objects)
	{
		if (name == null) return false;
		Class<?> cls = null;
		try
		{
			cls = getClass();
			Method method = cls.getDeclaredMethod(name, new Class[] {});
			method.setAccessible(true);
			method.invoke(this, objects == null ? new Object[] {} : objects);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Invoked the specified method by key and removed the key.
	 * 
	 * @param key
	 *            the animation key.
	 * @return if true - invoked correctly.
	 */
	private boolean invokeMethod(int key)
	{
		return MAPS_RUNKEY_METHODNAME.containsKey(key) ? invokeMethod(MAPS_RUNKEY_METHODNAME.remove(key), MAPS_RUNKEY_METHODPARAMS.remove(key)) : false;
	}

	// release spirits
	int releasePage(int curr, int next)
	{
		int num = 0;
		DataPage nextPage = mXmlParserHelper.getPage(next);
		DataPage currPage = mXmlParserHelper.getPage(curr);
		if (nextPage == null || currPage == null) return num;
		int[] nextS = nextPage.getSpirits();
		int[] currS = currPage.getSpirits();

		int[] nextG = nextPage.getGroups();
		int[] currG = currPage.getGroups();
		if (currS != null)
		{
			int len = currS.length;
			for (int i = 0; i < len; i++)
			{
				int id = currS[i];
				if (!contain(nextS, id))
				{
					String.format(mPageName, String.format("Spirit %d released!!", id));
					mSpiritManager.getFileSpiritMap().remove(id).release();
					num++;
				}
			}
		}

		if (currG != null)
		{
			int len = currG.length;
			for (int i = 0; i < len; i++)
			{
				int id = currG[i];
				if (!contain(nextG, id))
				{
					mGroupManager.getGroups().remove(id);
				}
			}
		}
		if (_DEBUG_) Log.e(mPageName, String.format("Release %d spirits from %d to %d", num, curr, next));

		return num;
	}

	private boolean contain(int[] s, int id)
	{
		boolean isContain = false;
		if (s == null) return isContain;
		int len = s.length;
		for (int i = 0; i < len; i++)
		{
			if (s[i] == id)
			{
				isContain = true;
				break;
			}
		}
		return isContain;
	}

	private List<Spirit> statementSpirits = new ArrayList<Spirit>();

	private void initControls()
	{
		Field[] fields = getClass().getDeclaredFields();
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				SpiritInject viewInject = field.getAnnotation(SpiritInject.class);
				if (viewInject != null)
				{
					int viewId = viewInject.id();
					try
					{
						field.setAccessible(true);
						field.set(this, findSpiritByid(viewId));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					addStatementSpirits(field);
					setSpiritTouchListener(field);
				}
			}
		}
	}

	private void addStatementSpirits(Field field)
	{
		Object obj = null;
		try
		{
			obj = field.get(this);
			if (obj == null) return;
			if (obj instanceof Spirit) statementSpirits.add((Spirit) obj);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void clearStatementSpirits()
	{
		statementSpirits.clear();
	}

	private void setSpiritTouchListener(Field field)
	{
		try
		{
			Object obj = field.get(this);
			if (obj instanceof Spirit) ((Spirit) obj).setOnTouchListener(mEvent);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// enable annotation spirits' touchable.
	protected void enableAnnotationSpiritTouchable()
	{
		setAnnotationSpiritTouchable(true);
	}

	// disable annotation spirits' touchable.
	protected void disableAnnotationSpiritTouchable()
	{
		setAnnotationSpiritTouchable(false);
	}

	// enable all spirits' touchable.
	protected void enableAllSpiritTouchable()
	{
		setAllSpiritTouchable(true);
	}

	// disable all spirits' touchable.
	protected void disableAllSpiritTouchable()
	{
		setAllSpiritTouchable(false);
	}

	private void setAllSpiritTouchable(boolean touchable)
	{
		Spirit[] spirits = getAllSpirits();
		if (spirits != null)
		{
			for (Spirit spirit : spirits)
			{
				if (spirit == null) continue;
				spirit.setTouchable(touchable);
			}
		}
	}

	private void setAnnotationSpiritTouchable(boolean touchable)
	{
		if (statementSpirits == null) return;
		for (Spirit spirit : statementSpirits)
		{
			if (spirit == null) continue;
			Log.e(mPageName, String.format("Spirit %d set touchable %s", spirit.id, touchable));
			spirit.setTouchable(touchable);
		}
	}

	public class HeartPlayer
	{
		private boolean _DEBUG_ = true;

		private final static int PLAY_HEART_ROTATE_DURATION = 500;
		private final static int PLAY_HEART_ROTATE_DEGREE = -360;
		private final static int PLAY_HEART_TRANSLATE_DISTANCE = 10;
		private final static int PLAY_HEART_TRANSLATE_MOVE_DURATION = 300;
		private final static int PLAY_HEART_DURATION = 400;

		private Timer mHeartTimer;
		private TimerTask mHeartTask;
		private boolean playedHeart;
		private int index;
		private boolean order;
		final Action mRotateAction, mPositiveTranslateAction, mInversaTranslateAction;
		final int mHeart;

		HeartPlayer(int heart)
		{
			index = 0;
			mHeart = heart;
			order = true;
			mRotateAction = new RotateAction(Axis.Y, PLAY_HEART_ROTATE_DEGREE, PLAY_HEART_ROTATE_DURATION, 0, Action.TYPE_ORDER_POSITIVE);
			mPositiveTranslateAction = new TranslateAction(0, PLAY_HEART_TRANSLATE_DISTANCE, PLAY_HEART_TRANSLATE_MOVE_DURATION, -2);
			mInversaTranslateAction = mPositiveTranslateAction.cloneOpposeAction();
		}

		public void play()
		{
			if (_DEBUG_) return;
			if (playedHeart) return;
			playedHeart = true;
			mHeartTimer = new Timer();
			mHeartTask = new TimerTask()
			{
				@Override
				public void run()
				{
					int k = index++;
					if (k == 15)
					{
						index = 0;
						playAction(mHeart, mRotateAction);
					}
					playAction(mHeart, order ? mPositiveTranslateAction : mInversaTranslateAction);
					order = !order;
				}
			};
			mHeartTimer.schedule(mHeartTask, 0, PLAY_HEART_DURATION);
		}

		public void stop()
		{
			if (_DEBUG_) return;
			if (!playedHeart) return;
			findSpiritByid(mHeart).setRotationY(0);
			playedHeart = false;
			mHeartTask.cancel();
			mHeartTimer.cancel();
			mHeartTask = null;
			mHeartTimer = null;
			findSpiritByid(mHeart).stopAnimation();
		}
	}
}
