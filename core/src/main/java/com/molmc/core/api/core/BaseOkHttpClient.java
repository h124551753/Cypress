package com.molmc.core.api.core;

import com.molmc.core.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * features: 配置okhttp client
 * Author：  hhe on 16-9-1 11:27
 * Email：   hui@molmc.com
 */

public abstract class BaseOkHttpClient {

	private static final long TIMEOUT_CONNECT = 5 * 1000;
	private static final long TIMEOUT_WRITE = 10 * 1000;
	private static final long TIMEOUT_READ = 10 * 1000;

	public OkHttpClient get() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();

		builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
				.addInterceptor(new HttpLoggingInterceptor()
						.setLevel(BuildConfig.DEBUG ?
								HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
		.retryOnConnectionFailure(true)
		.writeTimeout(TIMEOUT_WRITE, TimeUnit.MILLISECONDS)
		.readTimeout(TIMEOUT_READ, TimeUnit.MILLISECONDS);

		builder = customize(builder);

		return builder.build();
	}

	public abstract OkHttpClient.Builder customize(OkHttpClient.Builder builder);
}
