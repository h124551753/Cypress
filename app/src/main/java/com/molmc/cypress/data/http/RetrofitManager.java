package com.molmc.cypress.data.http;


import com.molmc.core.http.core.BaseRetrofit;
import com.molmc.core.http.core.CertType;
import com.molmc.core.http.core.HttpConfig;
import com.molmc.cypress.constants.Constant;

import okhttp3.OkHttpClient;

/**
 * features:
 * Author：  hhe on 16-9-9 15:01
 * Email：   hui@molmc.com
 */

public class RetrofitManager extends BaseRetrofit {

	private static final long TIMEOUT_CONNECT = 5 * 1000;

	@Override
	public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
		return builder;
	}

	@Override
	public HttpConfig setHttpConfig() {
		return buildNetConfig();
	}

	private HttpConfig buildNetConfig() {
		HttpConfig httpConfig = new HttpConfig.Builder()
				.setBaseUrl(Constant.BASE_URL)
				.setCookieEnable(true)
				.setCache(true)
				.setTimeOut(TIMEOUT_CONNECT)
				.setRetry(false)
				.setEnableHttps(true, CertType.CER_SEUIC_SERVER, "security_client.bks", "", 0)
				.build();
		return httpConfig;
	}
}
