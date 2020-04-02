package com.dabin.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Dabin on 2020/3/31 0031.
 */
public class ToastUtils {

    /**
     * 调用短吐司，此方法可在activity非主线程调用
     *
     * @param context 上下文对象
     * @param text    显示的内容
     */
    public static void showShortToast(final Context context, final String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    private static void showToast(final Context context, final String text, final int duration) {

        if (Thread.currentThread().getName().endsWith("main")) {
            show(context, text, duration);
        } else {
            if (context instanceof Activity) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        show(context, text, duration);
                    }
                });
            } else {
                throw new RuntimeException("请在主线程调用吐司");
            }
        }

    }

    private static void show(final Context context, final String text, final int duration) {
        Toast.makeText(context, text, duration).show();
    }

    /**
     * 调用长吐司，此方法可在activity非主线程调用
     *
     * @param context 上下文对象
     * @param text    显示的内容
     */
    public static void showLongToast(final Context context, final String text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }


//    /**
//     * 退出程序前取消
//     */
//    public static void cancel() {
//        if (mToast != null) {
//            mToast.cancel();
//            mToast = null;
//        }
//    }
}
