package com.dabin.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Dabin on 2020/8/11 0011.
 * apk工具类，用于获取版本号，包名等
 */
public class ApkUtil {

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext m
     * @return i
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return s
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取包名
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

}
