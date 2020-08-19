package com.dabin.widget.utils;

import android.content.Context;

/**
 * Created by Dabin on 2020/8/19 0019.
 * 屏幕单位转换工具
 */
public class DensityUtil {
    public static int dip2px(Context context, float dpValue) {
        return (int) (dpValue * getDensity(context) + 0.5F);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / getDensity(context) + 0.5F);
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
}
