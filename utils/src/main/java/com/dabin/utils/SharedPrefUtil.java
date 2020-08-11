package com.dabin.utils;

import android.content.Context;

/**
 * sharedpreferences的工具类
 * 
 * @author Dabin
 * 
 */
public class SharedPrefUtil {

	private static final String PREFNAME = "MyPref";

	/**
	 * 把字符串数据放入Pref中
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void putString(Context context, String key, String value) {
		context.getSharedPreferences(PREFNAME, 0).edit().putString(key, value).apply();
	}

	/**
	 * 把int数据放入Pref中
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void putInt(Context context, String key, int value) {
		context.getSharedPreferences(PREFNAME, 0).edit().putInt(key, value).apply();
	}
	
	/**
	 * 把long数据放入Pref中
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void putLong(Context context, String key, long value) {
		context.getSharedPreferences(PREFNAME, 0).edit().putLong(key, value).apply();
	}

	/**
	 * 把float数据放入Pref中
	 *
	 * @param context
	 *            上下文
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void putFloat(Context context, String key, float value) {
		context.getSharedPreferences(PREFNAME, 0).edit().putFloat(key, value).apply();
	}

	/**
	 * 把boolean数据放入Pref中
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void putBoolean(Context context, String key, boolean value) {
		context.getSharedPreferences(PREFNAME, 0).edit().putBoolean(key, value).apply();
	}

	/**
	 * 获取Pref中的key对应的数据
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            键
	 * @return 值
	 */
	public static String getString(Context context, String key) {
		return context.getSharedPreferences(PREFNAME, 0).getString(key, "");
	}

	/**
	 * 获取Pref中的key对应的数据
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            键
	 * @return 值
	 */
	public static int getInt(Context context, String key) {
		return context.getSharedPreferences(PREFNAME, 0).getInt(key, 0);
	}
	
	/**
	 * 获取Pref中的key对应的数据
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            键
	 * @return 值
	 */
	public static long getLong(Context context, String key) {
		return context.getSharedPreferences(PREFNAME, 0).getLong(key, 0);
	}

	/**
	 * 获取Pref中的key对应的数据
	 *
	 * @param context
	 *            上下文
	 * @param key
	 *            键
	 * @return 值
	 */
	public static boolean getBoolean(Context context, String key) {
		return context.getSharedPreferences(PREFNAME, 0).getBoolean(key, false);
	}

	/**
	 * 获取Pref中的key对应的数据
	 *
	 * @param context
	 *            上下文
	 * @param key
	 *            键
	 * @return 值
	 */
	public static float getFloat(Context context, String key) {
		return context.getSharedPreferences(PREFNAME, 0).getFloat(key, 0);
	}

}
