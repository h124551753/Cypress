package com.molmc.core.http.core;

import android.content.Context;

import com.molmc.core.BuildConfig;
import com.molmc.core.http.CacheInterceptor;
import com.molmc.core.http.HeaderInterceptor;
import com.molmc.core.http.cookie.PersistentCookieStore;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * features: 实例化 retrofit
 * Author：  hhe on 16-9-1 11:40
 * Email：   hui@molmc.com
 */

public abstract class BaseRetrofit {

	private HttpConfig mHttpConfig;
	private static Context mContext;

	public BaseRetrofit() {
		this.mHttpConfig = setHttpConfig();
	}

	public static void init(Context context) {
		mContext = context;
	}

	public OkHttpClient getHttpClient() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();

		// 设置http Header
		builder.addInterceptor(new HeaderInterceptor());

		//设置是否支持https
		if (mHttpConfig.mBuilder.getEnableHttps()) {
			HttpsUtils httpsUtils = new HttpsUtils();
			httpsUtils.init(mContext, mHttpConfig.mBuilder.cer_server, mHttpConfig.mBuilder.cer_client, mHttpConfig.mBuilder.bks_file, mHttpConfig.mBuilder.sslType);
			//其他配置
			builder.sslSocketFactory(httpsUtils.getSSLParams().sSLSocketFactory, httpsUtils.getSSLParams().trustManager);
		}

		//设置超时时间
		if (mHttpConfig.mBuilder.getTimeOut() != -1) {
			builder.readTimeout(mHttpConfig.mBuilder.getTimeOut(), TimeUnit.SECONDS);
			builder.writeTimeout(mHttpConfig.mBuilder.getTimeOut(), TimeUnit.SECONDS);
			builder.connectTimeout(mHttpConfig.mBuilder.getTimeOut(), TimeUnit.SECONDS);
		}

		//设置超时重试
		if (!mHttpConfig.mBuilder.isRetry()) {
			builder.retryOnConnectionFailure(false);
		}

		//设置缓存
		if (mHttpConfig.mBuilder.isCache()) {
			builder.addNetworkInterceptor(CacheInterceptor.buildInterceptor(mContext));
			builder.cache(CacheInterceptor.buildCache(mContext, "retrofit_cache"));
		}

		//设置cookie
		if (mHttpConfig.mBuilder.isCookieEnable()) {
			builder.cookieJar(new CookiesManager());
		}

		//设置log拦截器
		builder.addInterceptor(new HttpLoggingInterceptor()
				.setLevel(BuildConfig.DEBUG ?
						HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE));

		builder = customize(builder);

		return builder.build();
	}

	/**
	 * 自动管理Cookies
	 */
	private class CookiesManager implements CookieJar {
		private final PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);

		@Override
		public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
			if (cookies != null && cookies.size() > 0) {
				for (Cookie item : cookies) {
					cookieStore.add(url, item);
				}
			}
		}

		@Override
		public List<Cookie> loadForRequest(HttpUrl url) {
			List<Cookie> cookies = cookieStore.get(url);
			return cookies;
		}
	}

	public abstract OkHttpClient.Builder customize(OkHttpClient.Builder builder);

	public abstract HttpConfig setHttpConfig();

	/**
	 * 获得retrofit
	 */
	public Retrofit getRetrofit() {
		Retrofit.Builder builder = new Retrofit.Builder();

		builder.baseUrl(mHttpConfig.mBuilder.baseUrl)
				.client(getHttpClient())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create());

		return builder.build();
	}

}
