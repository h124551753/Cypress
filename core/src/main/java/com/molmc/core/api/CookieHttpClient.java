package com.molmc.core.api;

import com.molmc.core.CoreApplication;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * features: okhttp设置cookie
 * Author：  hhe on 16-9-1 12:19
 * Email：   hui@molmc.com
 */

public class CookieHttpClient extends CacheHttpClient {

	@Override
	public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
		builder = super.customize(builder);
		builder.cookieJar(new CookiesManager());
		return builder;
	}

	/**
	 * 自动管理Cookies
	 */
	private class CookiesManager implements CookieJar {
		private final PersistentCookieStore cookieStore = new PersistentCookieStore(CoreApplication.getInstance());

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
}
