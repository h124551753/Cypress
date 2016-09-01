package com.molmc.core.api;

import android.content.Context;

import com.molmc.core.CoreApplication;
import com.molmc.core.api.core.BaseOkHttpClient;
import com.molmc.core.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * features: 设置okhttp cache
 * Author：  hhe on 16-9-1 11:50
 * Email：   hui@molmc.com
 */

public class CacheHttpClient extends BaseOkHttpClient {

	private static final long CACHE_SIZE = 1024 * 1024 * 50;

	private Context mContext;

	public void CacheHttpClient(){
		mContext = CoreApplication.getInstance();
	}

	@Override
	public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
		//设置缓存路径
		File httpCacheDirectory = new File(mContext.getCacheDir(), "cypress_repo");
		//设置缓存 50M
		Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);
		builder.cache(cache);

		builder.addNetworkInterceptor(mCacheControlInterceptor);

		return builder;
	}



	private final Interceptor mCacheControlInterceptor = new Interceptor() {
		@Override
		public Response intercept(Chain chain) throws IOException {
			Request request = chain.request();

			// Add FORCE_CACHE cache control for each request if network is not available.
			if (!NetworkUtil.isNetworkAvailable(mContext)){
				request = request.newBuilder()
						.cacheControl(CacheControl.FORCE_CACHE)
						.build();
			}

			Response originalResponse = chain.proceed(request);

			if (NetworkUtil.isNetworkAvailable(mContext)){
				String cacheControl = request.cacheControl().toString();

				// Add cache control header for response same as request's while network is available.
				return originalResponse.newBuilder()
						.header("Cache-Control", cacheControl)
						.build();
			}else{
				// Add cache control header for response to FORCE_CACHE while network is not available.
				return originalResponse.newBuilder()
						.header("Cache-Control", CacheControl.FORCE_CACHE.toString())
						.build();
			}
		}
	};

}
