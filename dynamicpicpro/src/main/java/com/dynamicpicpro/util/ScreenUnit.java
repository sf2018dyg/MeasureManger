package com.dynamicpicpro.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.util.HashMap;

public class ScreenUnit {

	public static HashMap<String, Integer> ScreenMap;
	public static int VersionCode = 0;

	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}


	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕尺寸
	 * @param context
	 * @return
	 */
	public static HashMap<String, Integer> getScreenSize(Activity activity,
														 Context context) {
		int width, height;
		if (VersionCode > 13) {
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			width = size.x;
			height = size.y;
		} else {
			Display display = activity.getWindowManager().getDefaultDisplay();
			width = display.getWidth();
			height = display.getHeight();
		}
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		hashMap.put("width", width);
		hashMap.put("height", height);
		return hashMap;
	}
}
