package com.tpadsz.home.jni;

import android.graphics.Bitmap;
import android.util.Log;

import com.tpadsz.home.widget.*;
import com.tpadsz.home.widget.ListView.*;
import com.tpadsz.home.view.Program;
import com.tpadsz.home.view.Spirit.*;

public class NativeInterface
{

	public final static String TAG = "NativeInterface";

	/**
	 * Init the program.
	 * 
	 * @param mProgramPath
	 *            path of the specified program
	 * @return program id
	 */
	native public static int initProgram(String mProgramPath);

	/**
	 * Release the program.
	 * 
	 * @param mProId
	 *            program id
	 */
	native public static void destroyProgram(int mProId);

	/**
	 * Get the spirit object by json formatter.
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @return json formatter of data
	 */
	native public static String getSpriteById(int mSpriteId);

	/**
	 * Get the spirit initial info by id
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @return
	 */
	public static native String getSpriteOriginalInfo(int mSpriteId);

	/**
	 * Get the collection of spirit-id and spirit-id matched animation-id.
	 * 
	 * @param mProgramId
	 *            program id
	 * @return json formatter of data
	 */
	native public static String getInfos(int mProgramId);

	/**
	 * Create spirit from config file by the specified spirit id.
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 */
	native public static boolean createSpriteFromFile(int mSpriteId, boolean isShow);

	native public static boolean createSpriteFromSrc(int mSpriteId, String src, float x, float y, int w, int h, int mLayer, boolean isShown, boolean isTouchable);

	/**
	 * Create spirit from buffer (need to specify the spirit id).
	 * 
	 * @param mSprited
	 *            the spirit which specified by invoker
	 * @param mData
	 *            the bitmap buffer
	 */
	native public static boolean createSpriteFromBuffer(int mSprited, byte[] mData, int mLen, int width, int height,int widths , int heights, float x, float y, int mLayer, boolean isShown, boolean isTouchable, boolean isMoveable);

	/**
	 * Release the spirit by the specified id.
	 * 
	 * @param mSpriteId
	 *            spirit id
	 */
	native public static void destroySprite(int mSpriteId);

	/**
	 * Set the touchable of the spirit.
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param isTouchable
	 *            whether can be touch
	 */
	native public static void setSpriteTouchable(int mSpriteId, boolean isTouchable);

	/**
	 * Get the touchable of the spirit by the specified spirit id.
	 * 
	 * @param mSpriteId
	 *            spirit id
	 * @return if true - the spirit can be touched
	 */
	native public static boolean getSpriteTouchable(int mSpriteId);

	/**
	 * Set the visibility of the spirit by the specified spirit id.
	 * 
	 * @param mSpriteId
	 *            spirit id
	 * @param isVisible
	 *            whether is visible
	 */
	native public static void setSpriteVisible(int mSpriteId, boolean isVisible);

	/**
	 * Get the visibility of the spirit by the specified spirit id.
	 * 
	 * @param mSpriteId
	 *            spirit id
	 * @return if true - the spirit is visible
	 */
	native public static boolean getSpriteVisible(int mSpriteId);

	/**
	 * Set the position of the spirit .
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param x
	 *            The x coordinate of the first pixel in source
	 * @param y
	 *            The y coordinate of the first pixel in source
	 * @param mLayer
	 *            The layer of the spirit
	 * 
	 */
	native public static void setSpritePostion(int mSpriteId, float x, float y);

	/**
	 * Set the size of the spirit.
	 * 
	 * @param mSpriteId
	 *            id of spirit.
	 * @param mWidth
	 *            width of spirit.
	 * @param mHeight
	 *            height of spirit.
	 */
	public static native void setSpriteContentSize(int mSpriteId, float mWidth, float mHeight);

	/**
	 * Get the size of the spirit.
	 * 
	 * @param mSpriteId
	 *            id of spirit.
	 * @return the string of the size.
	 */
	public static native String getSpriteContentSize(int mSpriteId);

	/**
	 * Set the texture to the spirit.
	 * 
	 * @param mSptiteId
	 *            id of spirit.
	 * @param mFileName
	 *            texture path.
	 */
	public static native void setSpriteTexture(int mSptiteId, String mFileName);

	/**
	 * Set the texture to the specified spirit.
	 * 
	 * @param mSptiteId
	 *            id of spirit.
	 * @param mBufData
	 *            buffer of the texture data.
	 * @param mLen
	 *            length of data.
	 * @param mWidth
	 *            width of texture.
	 * @param mHeight
	 *            height of texture.
	 */
	public static native void setSpriteTexture(int mSptiteId, byte[] buffer, int size, int width, int height);

	/**
	 * Get the x coordinate of the spirit .
	 * 
	 * @param spiritid
	 * @return
	 */
	native public static float getSpiritX(int spiritid);

	/**
	 * Get the y coordinate of the spirit .
	 * 
	 * @param spiritid
	 * @return
	 */
	native public static float getSpiritY(int spiritid);

	/**
	 * Play the animation of the specified spirit
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param mAnimationId
	 *            the animation id
	 * @param mAnimationTypeId
	 *            the animation type id
	 * @param mAnimationOrder
	 *            the type of running
	 */
	native public static void runAnimation(int mSpriteId, int mAnimationId, int mAnimationTypeId, boolean mAnimationOrder, int key);

	/**
	 * Run the move action
	 * 
	 * @param mSpriteId
	 * @param mMoveType
	 * @param duration
	 * @param x
	 * @param y
	 * @param key
	 */
	native public static void runActionMove(int mSpriteId, int move_type, float x_star, float y_star, float x_end, float y_end, int move_speed_type, float move_time, int move_loop, float move_g, int move_to_or_by, int key, boolean isNormal);

	/**
	 * Stop the animation of the specified spirit
	 * 
	 * @param mSpriteId
	 *            the spirit id
	 * @param mAnimationId
	 *            the animation id
	 * @param mAnimationTypeId
	 *            the animation type id
	 * @param mAnimationOrder
	 *            the type of running
	 */
	native public static void stopAnimation(int mSpriteId, int mAnimationId, int mAnimationTypeId, int mAnimationOrder);

	/**
	 * Notify the specified listView to call back
	 * 
	 * @param mListViewId
	 *            id of listView
	 */
	native public static void notifyListViewLoadData(int mListViewId);

	/**
	 * Manual move the listView.
	 * 
	 * @param mListViewId
	 *            id of the listView.
	 * @param mDistance
	 *            the distance to move.
	 */
	native public static void manualMoveListView(int mListViewId, float mDistance);

	/**
	 * Scroll the listView .
	 * 
	 * @param mListViewId
	 *            id of the listView.
	 * @param mDistance
	 *            the distance to move.
	 */
	public static native void scrollListView(int mListViewId, float mDuration, float mDistance);

	//
	// native public static void setOnClickListener(int spirit_id,
	// OnClickListener listener);

	//
	// native public static void setOnLongPressListener(int spirit_id,
	// OnLongPressListener listener);
	//
	native public static void setOnTouchListener(int spirit_id, OnTouchListener listener);

	native public static void setAnimationCallback(int spirit_id, OnAnimationCallback callback);

	native public static void setOnItemClickListener(int spirit_id, OnItemClickListener listener);

	native public static void setAdapter(int spirit_id, Adapter mAdapter);

	native public static void notifyDataSetChanged(int spirit_id);

	native public static void setOpacity(int mSpriteId, int opacity);

	native public static int getOpacity(int mSpriteId);

	native public static void setLayer(int spiritid, int layer);

	native public static int getLayer(int spiritid);

	native public static int getListViewStartIdx(int mListViewId);

	native public static void setDefCell(int mListViewId, byte[] mData, int mLen, int mWidth, int mHeight);

	native public static boolean setCellBitmap(int mListViewId, int indx, byte[] mData, int mLen, int mWidth, int mHeight);

	native public static void setCellSize(int mListViewId, int cellWidth, int cellHeight);

	native public static void setListViewCount(int mListViewId, int count);

	native public static void setGroupPostion(int mGroupId, float x, float y);

	native public static void manualRunGroupActionMove(int mGroupId, int mMoveType, float duration, float x, float y, float acceleration, int key);

	native public static void manualRunGroupAnimationRotate(int groupID, int rotate_type, float x1, float y1, float x2, float y2, float degrees, float rotate_time, int key, boolean isNormal);

	native public static void manualRunSpriteAnimationRotate(int spriteID, int rotate_type, float x1, float y1, float x2, float y2, float degrees, float rotate_time, int key, boolean isNormal);

	native public static void runGroupAnimation(int mGroupId, int mAnimationId, int mAnimationTypeId, boolean mAnimationOrder, int key);

	native public static int getSpriteWidth(int mSpriteId);

	native public static int getSpriteHeight(int mSpriteId);

	native public static boolean spIsTouched(int mSpriteId, float x, float y);

	native public static void setStartXYOfActInfo(int mAnimationId, float x, float y);

	native public static void setEndXYOfActInfo(int mAnimationId, float x, float y);

	native public static void setTheCellMoveListener(int mSpriteId, OnCellMoveListener mListener);

	native public static void setSpriteRotationX(int spriteId, float degrees);

	native public static void setSpriteRotationY(int spriteId, float degrees);

	native public static void setSpriteRotationZ(int spriteId, float degrees);

	native public static void setSpriteRotationXY(int spriteId, float x1, float y1, float x2, float y2, float degrees);

	native public static void setGroupRotationX(int groupId, float degrees);

	native public static void setGroupRotationY(int spriteId, float degrees);

	native public static void setGroupRotationXY(int groupId, float x1, float y1, float x2, float y2, float degrees);

	native public static void setListViewStartIndex(int mSpriteId, int mStartIndex);

	native public static void mamualRunSpriteScale(int spriteID, int zoom_type, float x_sale_rate, float y_sale_rate, float x, float y, int zoom_speed_type, float zoom_time, int zoom_loop, float zoom_g, int zoom_to_or_by, int key, boolean isNormal);

	native public static void mamualRunGroupScale(int groupID, int zoom_type, float x_sale_rate, float y_sale_rate, float x, float y, int zoom_speed_type, float zoom_time, int zoom_loop, float zoom_g, int zoom_to_or_by, int key, boolean isNormal);

	native public static void mamualRunSpriteAlpha(int spriteID, int alpha_type, float alpha_star, float alpha_end, int alpha_speed_type, float alpha_time, int alpha_loop, float alpha_g, int alpha_to_or_by, int key, boolean isNormal);

	native public static void mamualRunGroupAlpha(int groupID, int alpha_type, float alpha_star, float alpha_end, int alpha_speed_type, float alpha_time, int alpha_loop, float alpha_g, int alpha_to_or_by, int key, boolean isNormal);

	// pelette
	native public static void setDrawingBoardRuning(int drawingBoardID, boolean isRun);

	native public static boolean getDrawingBoardRuning(int drawingBoardID);

	native public static void setDrawingBoardDrawing(int drawingBoardID, boolean isDrawing);

	native public static boolean getDrawingBoardisDrawing(int drawingBoardID);

	native public static void setDrawingBoardClear(int drawingBoardID, boolean isClear);

	native public static boolean getDrawingBoardisClear(int drawingBoardID);

	native public static void drawingBoardClearAll(int drawingBoardID);

	native public static void setBrushColor(int drawingBoardID, float r, float g, float b, float a);

	native public static void setBrushDiameter(int drawingBoardID, float diameter);

	native public static void setClearBrushDiameter(int drawingBoardID, float diameter);

	native public static void setCellMovable(int mListViewId, boolean movable);

	native public static void setFatherSprite(int spriteID, int fatherSpriteID);

	native public static void removeFather(int spriteID);

	native public static boolean createListView(int listviewID, float x, float y, float w, float h, int mLayer, int isHorizontal);

	native public static void setListViewFrameAnimation(int listviewID, int idx, int mAnimationId, int key, boolean isNormal);

	native public static boolean createScrollBar(int spID, String src, float x, float y, int w, int h, int layer_num, boolean touchble, boolean isShown);

	native public static boolean createScrollBarFromBuf(int spID, byte[] mData, int mData_Len, int mData_width, int mData_height, float x, float y, int sprite_w, int sprite_h, int layer_num, boolean touchble, boolean isShown);

	native public static void setScrollBarTexture(int spID, String src);

	native public static void setScrollBarTextureFromBuf(int spID, byte[] mBufData, int mLen, int mWidth, int mHeight);

	native public static void setContentPositionXInScrollBar(int spID, float x);

	native public static void scroll(int spId, float v);

	native public static void stopScroll(int spID);

	native public static void setScrollBarSize(int spID, int w, int h);

	native public static void loadFramesTexture(String src);

	native public static void runMove(int mSpriteID, int spriteType, int move_type, float x_star, float y_star, float x_end, float y_end, float move_speed_type, float move_time, int move_loop, float move_g, float[] move_line, int line_num, int move_to_or_by, int key, String flowKey, boolean isNormal);

	native public static void runRotate(int mSpriteID, int spriteType, int rotate_type, float x1, float y1, float z1, float x2, float y2, float z2, float rotate_angle, int rotate_speed_type, float rotate_time, int rotate_loop, float rotate_g, int rotate_to_or_by, int key, String flowKey, boolean isNormal);

	native public static void runScale(int mSpriteID, int spriteType, int zoom_type, float x_sale_rate, float y_sale_rate, float x, float y, int zoom_speed_type, float zoom_time, int zoom_loop, float zoom_g, int zoom_to_or_by, int key, String flowKey, boolean isNormal);

	native public static void runAlpha(int mSpriteID, int spriteType, int alpha_type, int alpha_star, int alpha_end, int alpha_speed_type, float alpha_time, int alpha_loop, float alpha_g, int alpha_to_or_by, int key, String flowKey, boolean isNormal);

	native public static void runFrames(int mSpriteID, int spriteType, int frame_number, String[] frame_src, float frame_rate, int frame_src_loop, int key, String flowKey, boolean isNormal);

	native public static boolean createDrawingBoard(int mSpriteId, int width, int height, float x, float y, int mLayer, boolean isShown);

	native public static void runListViewFrames(int listViewID, int idx, int frame_number, String[] frame_src, float frame_rate, int frame_src_loop, int key, boolean isNormal);

	native public static void runWaves2D(int mSpriteID, int spriteType, float amplitude, float cycle_time, boolean isVertical, int gridNum, int key);

	native public static void setSpriteScale(int spriteId, float x, float y);

	native public static void setSpriteWidth(int spriteId, int w);

	native public static void setSpriteHeight(int spriteId, int h);

	native public static void readPicToMemory(String picPath);

	native public static boolean searchForMemoryBuf(String picPath);

	native public static boolean createSpriteFromMemoryBuf(int mSpriteId, String src, float x, float y, int w, int h, int mLayer, boolean isShown, boolean isTouchable);

	// ///////////////////////////////////////////////////////////////////
	// /////////////////////////// CallBack //////////////////////////////
	// ///////////////////////////////////////////////////////////////////

	public static void onLoadFileCallback(boolean result)
	{
		Log.e(TAG, "onLoadFileCallback:" + result);
		Program.onLoadFileCallback(result);
	}

	public static void stopAnimationCallback(int i, int j, int k, boolean m)
	{
	}

	public static void onCreateSpritCallback(int spiritid, boolean result)
	{

	}

	public static void OnDeleteBitmap(Bitmap mBitmap)
	{
	}
}
