package com.molmc.core;

import android.app.Application;

import com.molmc.core.utils.AppLog;

/**
 * features:
 * Author：  hhe on 16-8-31 16:13
 * Email：   hhe@molmc.com
 */

public class CoreApplication extends Application {

	private static CoreApplication sCoreApplication;

	@Override
	public void onCreate() {
		super.onCreate();
		sCoreApplication = this;

		//初始化Log
		AppLog.init();
	}

	public static CoreApplication getInstance(){
		return sCoreApplication;
	}
}
