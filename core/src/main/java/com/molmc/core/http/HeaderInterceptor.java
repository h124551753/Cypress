package com.molmc.core.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * features: 设置http头拦截器
 * Author：  hhe on 16-7-24 19:34
 * Email：   hhe@molmc.com
 */
public class HeaderInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request()
				.newBuilder()
				.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.addHeader("Accept-Encoding", "gzip, deflate")
				.addHeader("Connection", "keep-alive")
				.addHeader("Accept", "*/*")
				.build();

		return chain.proceed(request);
	}
}
