package com.dabin.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.text.DecimalFormat;
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

    /**
     * 生成指定格式的字符串
     *
     * @param source 字符串
     * @param start  起始位
     * @param end    结束位
     * @param color  颜色，{@link Color}，传入-1为默认值黑色
     * @param style  格式，{@link Typeface}，传入-1为默认正体
     * @return 返回字符串
     */
    public static CharSequence generateSpanString(CharSequence source, int start, int end,
                                                  int color, int style) {
        SpannableString span = new SpannableString(source);
        int flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;  // 不包含起始位置和结束位置
        //设置颜色
        span.setSpan(new ForegroundColorSpan(color != -1 ? color : Color.BLACK), start, end, flags);
        //设置字体
        span.setSpan(new StyleSpan(style != -1 ? style : Typeface.NORMAL), start, end, flags);
        return span;
    }

    /**
     * 设置密码是否可见
     *
     * @param editText       Et
     * @param isShowPassword 是否可见
     * @return
     */
    public static void setPasswordVisibility(@NonNull EditText editText, boolean isShowPassword) {
        int inputType;
        if (isShowPassword) {//显示密码
            inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
        } else {
            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        }
        editText.setInputType(inputType);
        // 定位到选择的位置
        editText.setSelection(editText.getText().toString().length());
    }

    /**
     * 解析浮点类型的数据，使得它仅显示出小数点后n位数值
     *
     * @param d d
     * @param n ndouble
     * @return n位小数点的数值
     */
    public static String keepDecimal(double d, @IntRange(from = 1) int n) {
        return (Math.round(d * Math.pow(10, n)) / Math.pow(10, n)) + "";
    }

    /**
     * 格式化千位分隔符
     * 将每三个数字加上逗号处理，小数部分取2位，四舍五入
     * 10,000,000,000.01
     * @param number 需要处理的数值
     * @return 处理完之后的字符串
     */
    public static String formatThousands(double number) {
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        return decimalFormat.format(number);
    }

    /**
     * 获取字体
     *
     * @param typeface 字体文件名称，字体放在assets目录下
     * @param isBold   是否加粗
     * @return 返回Typeface
     */
    public static Typeface getTypeface(Context context, String typeface, boolean isBold) {
        AssetManager assets = context.getResources().getAssets();
        Typeface fromAsset = Typeface.createFromAsset(assets, typeface);
        return Typeface.create(fromAsset, isBold ? Typeface.BOLD : Typeface.NORMAL);
    }

    /**
     * 测量字符串所占用的宽度
     *
     * @param paint 画笔
     * @param str   测量的字符串
     * @return 返回字符串所占用的宽度
     */
    public static float measureTextWidth(Paint paint, String str) {
        float width = 0;
        if (paint != null && str != null) {
            width = paint.measureText(str);
        }
        return width;
    }

    /**
     * 测量字符串所占用的高度
     *
     * @param paint 画笔
     * @param str   测量的字符串
     * @return 返回字符串所占用的高度
     */
    public static float measureTextHight(Paint paint, String str) {
        Rect rect = new Rect();

        if (paint != null && str != null) {
            //返回包围整个字符串的最小的一个Rect区域
            paint.getTextBounds(str, 0, 1, rect);
        }
        return rect.width();
    }
}
