package com.molmc.core.imgloader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.lang.ref.WeakReference;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * features: 图片加载类
 * Author：  hhe on 16-8-31 22:12
 * Email：   hhe@molmc.com
 */

public class ImageLoader {

	private static final String HTTP_PREFIX = "http://";
	private static final String HTTPS_PREFIX = "https://";
	private static final String FILE_PREFIX = "file://";
	private static final String CONTENT_PREFEIX = "content://";
	private static final String ASSETS_PREFIX = "assets://";
	private static final String DRAWABLE_PREFIX = "drawable://";

	private static ImageLoader sImageLoader;
	private static WeakReference<Context> sContextWeak;

	public ImageLoader() {

	}

	public static void init(Context context) {
		if (sImageLoader == null) {
			synchronized (ImageLoader.class) {
				if (sImageLoader == null) {
					sImageLoader = new ImageLoader();
				}
			}
		}
		sContextWeak = new WeakReference<Context>(context);
	}

	/**
	 * 加载图片
	 * @param imageView
	 * @param imageUrl
	 */
	public static void loadImage(ImageView imageView, String imageUrl) {
		loadImage(imageView, imageUrl, 0, 0, 0);
	}

	public static void loadImage(ImageView imageView, String imageUrl, int defaultImageId) {
		loadImage(imageView, imageUrl, defaultImageId, 0, 0);
	}

	public static void loadImage(ImageView imageView, String imageUrl, int defaultImageId, int maxWidth, int maxHeight) {
		loadImage(imageView, imageUrl, defaultImageId, maxWidth, maxHeight, ImageView.ScaleType.CENTER_INSIDE);
	}

	public static void loadImage(ImageView imageView, String imageUrl, int defaultImageId, int maxWidth, int maxHeight, ImageView.ScaleType scaleType) {
		isInitedCheck();
		loadImage((Context) sContextWeak.get(), imageView, imageUrl, defaultImageId, maxWidth, maxHeight, scaleType);
	}

	public static void loadImage(Context context, ImageView imageView, String imageUrl, int defaultImageId, int maxWidth, int maxHeight, ImageView.ScaleType scaleType) {
		if (imageUrl != null && imageUrl.length() >= 7) {
			if (!imageUrl.startsWith(HTTP_PREFIX) && !imageUrl.startsWith(HTTPS_PREFIX)) {
				if (imageUrl.startsWith(FILE_PREFIX)) {
					if (maxWidth != 0 && maxHeight != 0) {
						Glide.with(context).load(new File(imageUrl.substring("file://".length()))).placeholder(defaultImageId).override(maxWidth, maxHeight).into(imageView);
					} else {
						Glide.with(context).load(new File(imageUrl.substring("file://".length()))).placeholder(defaultImageId).into(imageView);
					}
				} else if (imageUrl.startsWith(CONTENT_PREFEIX)) {
					if (maxWidth != 0 && maxHeight != 0) {
						Glide.with(context).load(Uri.parse(imageUrl)).placeholder(defaultImageId).placeholder(defaultImageId).override(maxWidth, maxHeight).into(imageView);
					} else {
						Glide.with(context).load(Uri.parse(imageUrl)).placeholder(defaultImageId).into(imageView);
					}
				} else if (imageUrl.startsWith(ASSETS_PREFIX)) {
					if (maxWidth != 0 && maxHeight != 0) {
						Glide.with(context).load(Uri.parse("file:///android_asset/" + imageUrl.substring("assets://".length()))).placeholder(defaultImageId).override(maxWidth, maxHeight).into(imageView);
					} else {
						Glide.with(context).load(Uri.parse("file:///android_asset/" + imageUrl.substring("assets://".length()))).placeholder(defaultImageId).into(imageView);
					}
				} else {
					if (!imageUrl.startsWith(DRAWABLE_PREFIX)) {
						throw new IllegalArgumentException("imageUrl not support");
					}
					if (maxWidth != 0 && maxHeight != 0) {
						Glide.with(context).load(Integer.valueOf(Integer.parseInt(imageUrl.substring("drawable://".length())))).placeholder(defaultImageId).override(maxWidth, maxHeight).into(imageView);
					} else {
						Glide.with(context).load(Integer.valueOf(Integer.parseInt(imageUrl.substring("drawable://".length())))).placeholder(defaultImageId).into(imageView);
					}
				}
			} else if (maxWidth != 0 && maxHeight != 0) {
				Glide.with(context).load(imageUrl).placeholder(defaultImageId).override(maxWidth, maxHeight).crossFade().into(imageView);
			} else {
				Glide.with(context).load(imageUrl).placeholder(defaultImageId).crossFade().into(imageView);
			}
		} else if (defaultImageId > 0) {
			imageView.setImageResource(defaultImageId);
		} else {
			throw new IllegalArgumentException("imageUrl invalid");
		}
	}

	/**
	 * 加载圆角图片
	 * @param imageView
	 * @param imageUrl
	 */
	public static void loadRoundedCorners(ImageView imageView, String imageUrl) {
		loadRoundedCorners(imageView, imageUrl, 0, 0, 0);
	}

	public static void loadRoundedCorners(ImageView imageView, String imageUrl, int defaultImageId) {
		loadRoundedCorners(imageView, imageUrl, defaultImageId, 0, 0);
	}

	public static void loadRoundedCorners(ImageView imageView, String imageUrl, int defaultImageId, int maxWidth, int maxHeight) {
		loadRoundedCorners(imageView, imageUrl, defaultImageId, maxWidth, maxHeight, ImageView.ScaleType.CENTER_INSIDE);
	}

	public static void loadRoundedCorners(ImageView imageView, String imageUrl, int defaultImageId, int maxWidth, int maxHeight, ImageView.ScaleType scaleType) {
		isInitedCheck();
		loadRoundedCorners((Context) sContextWeak.get(), imageView, imageUrl, defaultImageId, maxWidth, maxHeight, scaleType);
	}

	public static void loadRoundedCorners(Context context, ImageView imageView, String imageUrl, int defaultImageId, int maxWidth, int maxHeight, ImageView.ScaleType scaleType) {
		if (imageUrl != null && imageUrl.length() >= 7) {
			if (!imageUrl.startsWith(HTTP_PREFIX) && !imageUrl.startsWith(HTTPS_PREFIX)) {
				if (imageUrl.startsWith(FILE_PREFIX)) {
					if (maxWidth != 0 && maxHeight != 0) {
						Glide.with(context)
								.load(new File(imageUrl.substring("file://".length())))
								.placeholder(defaultImageId).override(maxWidth, maxHeight)
								.bitmapTransform(new RoundedCornersTransformation(context, 2, 0))
								.into(imageView);
					} else {
						Glide.with(context)
								.load(new File(imageUrl.substring("file://".length())))
								.placeholder(defaultImageId)
								.bitmapTransform(new RoundedCornersTransformation(context, 2, 0))
								.into(imageView);
					}
				} else if (imageUrl.startsWith(CONTENT_PREFEIX)) {
					if (maxWidth != 0 && maxHeight != 0) {
						Glide.with(context)
								.load(Uri.parse(imageUrl))
								.placeholder(defaultImageId)
								.bitmapTransform(new RoundedCornersTransformation(context, 2, 0))
								.override(maxWidth, maxHeight)
								.into(imageView);
					} else {
						Glide.with(context)
								.load(Uri.parse(imageUrl))
								.placeholder(defaultImageId)
								.bitmapTransform(new RoundedCornersTransformation(context, 2, 0))
								.into(imageView);
					}
				} else if (imageUrl.startsWith(ASSETS_PREFIX)) {
					if (maxWidth != 0 && maxHeight != 0) {
						Glide.with(context)
								.load(Uri.parse("file:///android_asset/" + imageUrl.substring("assets://".length())))
								.placeholder(defaultImageId)
								.override(maxWidth, maxHeight)
								.bitmapTransform(new RoundedCornersTransformation(context, 2, 0))
								.into(imageView);
					} else {
						Glide.with(context)
								.load(Uri.parse("file:///android_asset/" + imageUrl.substring("assets://".length())))
								.placeholder(defaultImageId)
								.bitmapTransform(new RoundedCornersTransformation(context, 2, 0))
								.into(imageView);
					}
				} else {
					if (!imageUrl.startsWith(DRAWABLE_PREFIX)) {
						throw new IllegalArgumentException("imageUrl not support");
					}
					if (maxWidth != 0 && maxHeight != 0) {
						Glide.with(context)
								.load(Integer.valueOf(Integer.parseInt(imageUrl.substring("drawable://".length()))))
								.placeholder(defaultImageId)
								.override(maxWidth, maxHeight)
								.bitmapTransform(new RoundedCornersTransformation(context, 2, 0))
								.into(imageView);
					} else {
						Glide.with(context)
								.load(Integer.valueOf(Integer.parseInt(imageUrl.substring("drawable://".length()))))
								.placeholder(defaultImageId)
								.bitmapTransform(new RoundedCornersTransformation(context, 2, 0))
								.into(imageView);
					}
				}
			} else if (maxWidth != 0 && maxHeight != 0) {
				Glide.with(context)
						.load(imageUrl)
						.placeholder(defaultImageId)
						.override(maxWidth, maxHeight)
						.bitmapTransform(new RoundedCornersTransformation(context, 2, 0))
						.crossFade()
						.into(imageView);
			} else {
				Glide.with(context)
						.load(imageUrl)
						.placeholder(defaultImageId)
						.bitmapTransform(new RoundedCornersTransformation(context, 2, 0))
						.crossFade()
						.into(imageView);
			}
		} else if (defaultImageId > 0) {
			imageView.setImageResource(defaultImageId);
		} else {
			throw new IllegalArgumentException("imageUrl invalid");
		}
	}

	/**
	 * 加载圆形图片
	 * @param imageView
	 * @param imageUrl
	 */
	public static void loadWithCircle(ImageView imageView, String imageUrl) {
		loadWithCircle(imageView, imageUrl, 0, 0, 0);
	}

	public static void loadWithCircle(ImageView imageView, String imageUrl, int defaultImageId) {
		loadWithCircle(imageView, imageUrl, defaultImageId, 0, 0);
	}

	public static void loadWithCircle(ImageView imageView, String imageUrl, int defaultImageId, int maxWidth, int maxHeight) {
		loadWithCircle(imageView, imageUrl, defaultImageId, maxWidth, maxHeight, ImageView.ScaleType.CENTER_INSIDE);
	}

	public static void loadWithCircle(ImageView imageView, String imageUrl, int defaultImageId, int maxWidth, int maxHeight, ImageView.ScaleType scaleType) {
		isInitedCheck();
		loadWithCircle((Context) sContextWeak.get(), imageView, imageUrl, defaultImageId, maxWidth, maxHeight, scaleType);
	}

	public static void loadWithCircle(Context context, ImageView imageView, String imageUrl, int defaultImageId, int maxWidth, int maxHeight, ImageView.ScaleType scaleType) {
		if (imageUrl != null && imageUrl.length() >= 7) {
			if (!imageUrl.startsWith(HTTP_PREFIX) && !imageUrl.startsWith(HTTPS_PREFIX)) {
				if (imageUrl.startsWith(FILE_PREFIX)) {
					if (maxWidth != 0 && maxHeight != 0) {
						Glide.with(context)
								.load(new File(imageUrl.substring("file://".length())))
								.placeholder(defaultImageId).override(maxWidth, maxHeight)
								.bitmapTransform(new CropCircleTransformation(context))
								.into(imageView);
					} else {
						Glide.with(context)
								.load(new File(imageUrl.substring("file://".length())))
								.placeholder(defaultImageId)
								.bitmapTransform(new CropCircleTransformation(context))
								.into(imageView);
					}
				} else if (imageUrl.startsWith(CONTENT_PREFEIX)) {
					if (maxWidth != 0 && maxHeight != 0) {
						Glide.with(context)
								.load(Uri.parse(imageUrl))
								.placeholder(defaultImageId)
								.bitmapTransform(new CropCircleTransformation(context))
								.override(maxWidth, maxHeight)
								.into(imageView);
					} else {
						Glide.with(context)
								.load(Uri.parse(imageUrl))
								.placeholder(defaultImageId)
								.bitmapTransform(new CropCircleTransformation(context))
								.into(imageView);
					}
				} else if (imageUrl.startsWith(ASSETS_PREFIX)) {
					if (maxWidth != 0 && maxHeight != 0) {
						Glide.with(context)
								.load(Uri.parse("file:///android_asset/" + imageUrl.substring("assets://".length())))
								.placeholder(defaultImageId)
								.override(maxWidth, maxHeight)
								.bitmapTransform(new CropCircleTransformation(context))
								.into(imageView);
					} else {
						Glide.with(context)
								.load(Uri.parse("file:///android_asset/" + imageUrl.substring("assets://".length())))
								.placeholder(defaultImageId)
								.bitmapTransform(new CropCircleTransformation(context))
								.into(imageView);
					}
				} else {
					if (!imageUrl.startsWith(DRAWABLE_PREFIX)) {
						throw new IllegalArgumentException("imageUrl not support");
					}
					if (maxWidth != 0 && maxHeight != 0) {
						Glide.with(context)
								.load(Integer.valueOf(Integer.parseInt(imageUrl.substring("drawable://".length()))))
								.placeholder(defaultImageId)
								.override(maxWidth, maxHeight)
								.bitmapTransform(new CropCircleTransformation(context))
								.into(imageView);
					} else {
						Glide.with(context)
								.load(Integer.valueOf(Integer.parseInt(imageUrl.substring("drawable://".length()))))
								.placeholder(defaultImageId)
								.bitmapTransform(new CropCircleTransformation(context))
								.into(imageView);
					}
				}
			} else if (maxWidth != 0 && maxHeight != 0) {
				Glide.with(context)
						.load(imageUrl)
						.placeholder(defaultImageId)
						.override(maxWidth, maxHeight)
						.bitmapTransform(new CropCircleTransformation(context))
						.crossFade()
						.into(imageView);
			} else {
				Glide.with(context)
						.load(imageUrl)
						.placeholder(defaultImageId)
						.bitmapTransform(new CropCircleTransformation(context))
						.crossFade()
						.into(imageView);
			}
		} else if (defaultImageId > 0) {
			imageView.setImageResource(defaultImageId);
		} else {
			throw new IllegalArgumentException("imageUrl invalid");
		}
	}

	public static void clear() {
		isInitedCheck();
		Glide.get((Context) sContextWeak.get()).clearMemory();
	}

	private static void isInitedCheck() {
		if (sContextWeak == null || sContextWeak.get() == null) {
			throw new IllegalStateException("ImageLoader not initialized");
		}
	}

}
