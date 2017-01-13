package com.molmc.core;

import android.content.Context;

import com.molmc.core.http.core.BaseRetrofit;

/**
 * features: 初始化
 * Author：  hhe on 16-9-9 17:47
 * Email：   hui@molmc.com
 */

public class CypressCore {

	private static Context sContext;
	public static void init(Context context) {
		sContext = context;
		BaseRetrofit.init(context);
	}
}
