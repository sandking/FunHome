package com.tpadsz.home.widget;

import com.tpadsz.home.jni.NativeInterface;
import com.tpadsz.home.view.Spirit;

public class Palette extends Spirit
{

	private boolean editable;

	public Palette(int id, String name, String src, float x, float y, int width, int height, int layer, boolean visible, boolean editable)
	{
		super(id, name, src, x, y, width, height, layer, false, visible);
		NativeInterface.createDrawingBoard(id, width, height, x, y, layer, visible);
		setDrawingBoardDrawing(editable);
	}

	public Palette(int id, int width, int height, float x, float y, int layer, boolean visible)
	{
		this(id, "", "", x, y, width, height, layer, visible, true);
	}

	@Override
	public Type getType()
	{
		return Type.TOUCHPAD;
	}

	public boolean isEditable()
	{
		return this.editable;
	}

	public void setDrawingBoardRuning(boolean isRun)
	{
		NativeInterface.setDrawingBoardRuning(id, isRun);
	}

	public boolean getDrawingBoardRuning()
	{
		return NativeInterface.getDrawingBoardRuning(id);
	}

	public void setDrawingBoardDrawing(boolean isDrawing)
	{
		this.editable = isDrawing;
		NativeInterface.setDrawingBoardDrawing(id, isDrawing);
	}

	public boolean getDrawingBoardisDrawing()
	{
		return NativeInterface.getDrawingBoardisDrawing(id);
	}

	public void setDrawingBoardClear(boolean isClear)
	{
		NativeInterface.setDrawingBoardClear(id, isClear);
	}

	public boolean getDrawingBoardisClear()
	{
		return NativeInterface.getDrawingBoardisClear(id);
	}

	public void drawingBoardClearAll()
	{
		NativeInterface.drawingBoardClearAll(id);
	}

	public void setBrushColor(float r, float g, float b, float a)
	{
		NativeInterface.setBrushColor(id, r, g, b, a);
	}

	public void setBrushDiameter(float diameter)
	{
		NativeInterface.setBrushDiameter(id, diameter);
	}

	public void setClearBrushDiameter(float diameter)
	{
		NativeInterface.setClearBrushDiameter(id, diameter);
	}
}
