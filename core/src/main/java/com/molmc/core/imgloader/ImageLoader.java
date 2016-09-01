package com.molmc.core.imgloader;

import android.content.Context;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.molmc.core.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * features: 图片加载类
 * Author：  hhe on 16-8-31 22:12
 * Email：   hhe@molmc.com
 */

public class ImageLoader {

	// 加载网络图片
	public static void load(Context context, String url, ImageView imageView) {
		Glide.with(context)
				.load(url)
				.placeholder(R.color.abc_tab_text_normal)
				.error(R.color.abc_tab_text_normal)
				.crossFade()
				.into(imageView);
	}

	// 加载圆角网络图片
	public static void loadRoundedCorners(Context context, String url, ImageView imageView) {
		Glide.with(context)
				.load(url)
				.placeholder(R.color.abc_tab_text_normal)
				.error(R.color.abc_tab_text_normal)
				.bitmapTransform(new RoundedCornersTransformation(context, 2, 0))
				.crossFade()
				.into(imageView);
	}

	// 加载圆型网络图片
	public static void loadWithCircle(Context context, String url, ImageView imageView) {
		Glide.with(context)
				.load(url)
				.placeholder(R.color.abc_tab_text_normal)
				.error(R.color.abc_tab_text_normal)
				.bitmapTransform(new CropCircleTransformation(context))
				.crossFade()
				.into(imageView);
	}

	// 加载gif网络图片
	public static void loadGif(Context context, String url, ImageView imageView) {
		Glide.with(context)
				.load(url)
				.asGif()
				.placeholder(R.color.abc_tab_text_normal)
				.error(R.color.abc_tab_text_normal)
				.crossFade()
				.into(imageView);
	}

	// 加载本地图片
	public static void load(Context context, int resid, ImageView imageView) {
		Glide.with(context)
				.load(resid)
				.placeholder(R.color.abc_tab_text_normal)
				.error(R.color.abc_tab_text_normal)
				.crossFade()
				.into(imageView);
	}

	// 加载圆角本地图片
	public static void loadRoundedCorners(Context context, int resId, ImageView imageView) {
		Glide.with(context)
				.load(resId)
				.placeholder(R.color.abc_tab_text_normal)
				.error(R.color.abc_tab_text_normal)
				.bitmapTransform(new RoundedCornersTransformation(context, 2, 0))
				.crossFade()
				.into(imageView);
	}

	// 加载圆型本地图片
	public static void loadWithCircle(Context context, int resId, ImageView imageView) {
		Glide.with(context)
				.load(resId)
				.placeholder(R.color.abc_tab_text_normal)
				.error(R.color.abc_tab_text_normal)
				.bitmapTransform(new CropCircleTransformation(context))
				.crossFade()
				.into(imageView);
	}

	// 加载gif网络图片
	public static void loadGif(Context context, int resId, ImageView imageView) {
		Glide.with(context)
				.load(resId)
				.asGif()
				.placeholder(R.color.abc_tab_text_normal)
				.error(R.color.abc_tab_text_normal)
				.crossFade()
				.into(imageView);
	}


	// 加载网络图片动画
	public static void loadAnima(Context context, String url, Animation animation, ImageView imageView) {
		Glide.with(context)
				.load(url)
				.placeholder(R.color.abc_tab_text_normal)
				.error(R.color.abc_tab_text_normal)
				.animate(animation)
				.crossFade()
				.into(imageView);
	}

	// 加载网络图片动画
	public static void loadAnima(Context context, String url, int animationId, ImageView imageView) {
		Glide.with(context)
				.load(url)
				.placeholder(R.color.abc_tab_text_normal)
				.error(R.color.abc_tab_text_normal)
				.animate(animationId)
				.crossFade()
				.into(imageView);
	}

	// 加载本地图片动画
	public static void loadAnima(Context context, int resId, Animation animation, ImageView imageView) {
		Glide.with(context)
				.load(resId)
				.placeholder(R.color.abc_tab_text_normal)
				.error(R.color.abc_tab_text_normal)
				.animate(animation)
				.crossFade()
				.into(imageView);
	}


	// 加载drawable图片
	public static void loadAnima(Context context, int resId, int animationId, ImageView imageView) {
		Glide.with(context)
				.load(resId)
				.placeholder(R.color.abc_tab_text_normal)
				.error(R.color.abc_tab_text_normal)
				.animate(animationId)
				.crossFade()
				.into(imageView);
	}

}
