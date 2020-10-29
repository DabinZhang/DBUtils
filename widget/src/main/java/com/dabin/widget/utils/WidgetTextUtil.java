package com.dabin.widget.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.text.DecimalFormat;

/**
 * 文本工具类
 */
public class WidgetTextUtil {

    /**
     * 获取字符串左下角的y坐标
     *
     * @param viewHeight 总体高度
     * @param paint      画笔
     * @return Y坐标
     */
    public static int getLeftDownY(int viewHeight, Paint paint) {
        int result = 0;
        if (viewHeight > 0 && paint != null) {
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            result = (viewHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        }
        return result;
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

}
