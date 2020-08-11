package com.dabin.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;

import java.io.IOException;
import java.io.InputStream;

/**
 * 图片的工具类
 * 
 * @author Dabin
 * 
 */
public class BitmapUtil {

	/**
	 * 加载动图，使用此方法的图片需要放在drawable-nodpi文件夹中
	 * 
	 * @param anim_resource
	 *            动图的帧图资源id数组
	 * @param duration
	 *            持续时间,毫秒值
	 * @param oneShot
	 *            是否只运行一次
	 * @return 返回动图的对象
	 */
	public static AnimationDrawable loadAnimationDrawable(int[] anim_resource, int duration, boolean oneShot,
                                                          Context context) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565; // 设置图像的像素信息
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inJustDecodeBounds = false; // 设置该属性可获得图片的长宽等信息，但是避免了不必要的提前加载动画，此方法如果为true则用于只加载边缘
		InputStream is = null;
		// 创建图片对象
		Bitmap[] mbitMap = new Bitmap[anim_resource.length];
		for (int i = 0; i < anim_resource.length; i++) {
			is = context.getResources().openRawResource(anim_resource[i]);
			Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
			mbitMap[i] = bitmap;
		}
		// 释放资源
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 创建动图对象
		AnimationDrawable ad = new AnimationDrawable();
		for (int i = 0; i < anim_resource.length; i++) {
			ad.addFrame(new BitmapDrawable(context.getResources(), mbitMap[i]), duration);

		}
		ad.setOneShot(oneShot);
		return ad;
	}

	/**
	 * 加载图片资源
	 * 
	 * @param bitmap_resource
	 *            图片资源id
	 * @return
	 */
	public static Bitmap loadBitmap(int bitmap_resource, Context context) {
		// 创建图片对象
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565; // 设置图像的像素信息
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inJustDecodeBounds = false; // 设置该属性可获得图片的长宽等信息，但是避免了不必要的提前加载，此方法如果为true用于只加载边缘
		InputStream is = context.getResources().openRawResource(bitmap_resource);
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
		// 释放资源
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

}
