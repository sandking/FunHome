package com.tpadsz.home.view.helper;

import java.util.HashMap;

import com.tpadsz.home.DebugLog;
import com.tpadsz.home.view.Program;
import com.tpadsz.home.view.Spirit;
import com.tpadsz.home.widget.SpiritGroup;
import com.tpadsz.home.xmlparser.spirits.DataSpiritGroup;

public class GroupManager
{

	public static final String TAG = GroupManager.class.getSimpleName();

	HashMap<Integer, SpiritGroup> MAPS_ID_GROUP = new HashMap<Integer, SpiritGroup>();
	HashMap<Integer, Spirit> MAPS_ID_SPIRIT = new HashMap<Integer, Spirit>();
	private XMLParserHelper mHelper;
	private static GroupManager manager;

	private GroupManager(Program p)
	{
		mHelper = p.getParserHelper();
		MAPS_ID_SPIRIT = p.getSpirits();
	}

	public static GroupManager getInstance(Program p)
	{
		if (manager == null) manager = new GroupManager(p);
		return manager;
	}

	public void initGroups()
	{
		for (int id : mHelper.DATA_GROUP_POOLS.keySet())
			createGroup(id);
	}

	public SpiritGroup createGroup(int id)
	{
		DataSpiritGroup mDataSpiritGroup = mHelper.DATA_GROUP_POOLS.get(id);
		SpiritGroup group = null;
		if (mDataSpiritGroup != null)
		{
			int[] contents = mDataSpiritGroup.getContents();
			Spirit[] spirits = obtainSpirits(id, contents);
			group = new SpiritGroup(id, spirits);
			MAPS_ID_GROUP.put(id, group);
		}
		else DebugLog.LogNull(TAG, String.format("DataSpiritGroup %d is null!!", id));
		return group;
	}

	private Spirit[] obtainSpirits(int id, int[] contents)
	{
		if (contents == null) return null;
		int count = contents.length;
		Spirit[] spirits = new Spirit[count];
		for (int i = 0; i < count; i++)
		{
			spirits[i] = MAPS_ID_SPIRIT.get(contents[i]);
			if (spirits[i] == null) DebugLog.LogNull(TAG, String.format("Spirit %d in group %d is null!!!", contents[i], id));
		}
		return spirits;
	}

	public HashMap<Integer, SpiritGroup> getGroups()
	{
		return MAPS_ID_GROUP;
	}
}
