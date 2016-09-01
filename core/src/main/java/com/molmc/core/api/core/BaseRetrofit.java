package com.molmc.core.api.core;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * features: 实例化 retrofit
 * Author：  hhe on 16-9-1 11:40
 * Email：   hui@molmc.com
 */

public abstract class BaseRetrofit {

	public Retrofit get(){
		Retrofit.Builder builder = new Retrofit.Builder();

		builder.baseUrl(getApiEndpoint().getEndpoint())
				.client(getHttpClient())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create());

		return builder.build();
	}

	public abstract ApiEndpoint getApiEndpoint();
	public abstract OkHttpClient getHttpClient();
}
