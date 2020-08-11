package com.dabin.utils;

import android.database.Cursor;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dabin on 2020/8/11 0011.
 */
public class TextUtil {

    /**
     * 判断是否text是否为空
     *
     * @param text 文字
     * @return true表示为空
     */
    public static boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    /**
     * 判断名称是否和数据库的已有名称重复
     *
     * @return false 表示不重复，反之为重复
     */
    public static boolean isDuplicate(String text, Cursor cursor) {
        boolean flag = false;

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                if (text.equals(name)) {
                    flag = true;
                    break;
                }
            }
            cursor.close();
        }

        return flag;
    }


    /**
     * 把小数类型的数据格式化成标准的类型，如果类型有误，返回0
     *
     * @param digital d
     * @return s
     */
    public static String formatDigital(String digital) {
        if (isNumber(digital)) {
            return Float.parseFloat(digital) + "";
        } else {
            return "0";
        }
    }

    /**
     * 把整数类型的数据格式化成标准的类型，如果类型有误，返回0
     *
     * @param numeric n
     * @return s
     */
    public static String formatNumeric(String numeric) {
        if (isNumeric(numeric)) {
            return Integer.parseInt(numeric) + "";
        } else {
            return "0";
        }
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str s
     * @return b
     */
    public static boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 判断字符串是否为小数
     *
     * @param str s
     * @return b
     */
    public static boolean isDecimal(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 判断是否为数字（小数和整数）
     *
     * @param str s
     * @return b
     */
    public static boolean isNumber(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
}
