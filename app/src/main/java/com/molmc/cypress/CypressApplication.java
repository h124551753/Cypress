package com.molmc.cypress;


import com.crashlytics.android.Crashlytics;
import com.molmc.core.CoreApplication;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;



/**
 * features:
 * Author：  hhe on 16-8-30 16:15
 * Email：   hhe@molmc.com
 */

public class CypressApplication extends CoreApplication {



	@Override
	public void onCreate() {
		super.onCreate();
		if (!BuildConfig.DEBUG) {
			//初始化数据统计
			Fabric.with(this, new Crashlytics());
		}else{
			//初始化内存泄露检查
			LeakCanary.install(this);
		}
	}

}
