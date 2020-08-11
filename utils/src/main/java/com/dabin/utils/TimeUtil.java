package com.dabin.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dabin on 2020/8/11 0011.
 * 日期工具类
 */
public class TimeUtil {

    /**
     * 格式化时间为String类型，并把个位数前加0
     *
     * @param paramInt
     * @return
     */
    public static String int2String(int paramInt) {
        return paramInt < 10 ? "0" + paramInt : "" + paramInt;
    }

    /**
     * 把时间戳转为字符串类型，yyyy年MM月dd日 HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String stamp2String(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }

    /**
     * 获取今天的日期
     *
     * @return
     */
    public static String getToday() {
        return getToday("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static String getToday(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
     * 将时间转换为时间戳
     */
    public static long date2Stamp(String s) {
        return date2Stamp("yyyy-MM-dd HH:mm:ss", s);
    }

    /**
     * 将时间转换为时间戳
     */
    public static long date2Stamp(String pattern, String s) {
        long res = -1;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date date = simpleDateFormat.parse(s);
            res = date.getTime();
        } catch (Exception e) {
        }
        return res;
    }



}
