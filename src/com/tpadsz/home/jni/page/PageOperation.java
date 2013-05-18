package com.tpadsz.home.jni.page;

import com.tpadsz.home.view.Spirit;
import com.tpadsz.home.view.action.Action;
import com.tpadsz.home.view.action.Animation;
import com.tpadsz.home.widget.SpiritGroup;

public interface PageOperation
{
	Spirit findSpiritByid(int id);

	SpiritGroup findGroupByid(int id);

	void gotoPage(int id);

	int playAnimation(int id, Animation animation, long delay);

	int playGroupAnimation(int id, Animation animation, long delay);

	int playAction(int id, Action action, long delay);

	int playGroupAction(int id, Action action, long delay);
}
