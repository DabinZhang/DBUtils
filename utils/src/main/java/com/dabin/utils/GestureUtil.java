package com.dabin.utils;

import android.content.Context;
import android.os.SystemClock;

/**
 * Created by Dabin on 2020/8/11 0011.
 * 手势工具类
 */
public class GestureUtil {

    private static long[] mHits = new long[2];

    /**
     * 再次点击确认删除
     *
     * @return boolean
     */
    public static boolean deleteEnsure(Context context) {

        boolean flag;

        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 2000)) {
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

}
