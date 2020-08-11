package com.dabin.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘的控制类
 * 
 * @author Dabin
 * 
 */
public class SoftInputUtil {

	/**
	 * 隐藏软键盘
	 * 
	 * @param activity
	 */
	public static void softInputDismiss(Activity activity) {
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	/**
	 * 强制隐藏已经打开的软键盘
	 * 
	 * @param activity
	 * @param view
	 *            接受软键盘输入的view
	 */
	public static void softInputDismiss(Activity activity, View view) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 软键盘的开关，原来开着的就会关闭，原来是关闭，就会打开
	 * 
	 * @param activity
	 */
	public static void softInputToggle(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

}
