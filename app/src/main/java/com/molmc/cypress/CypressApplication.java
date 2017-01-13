package com.molmc.cypress;


import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.molmc.core.imgloader.ImageLoader;
import com.molmc.core.utils.AppLog;
import com.molmc.cypress.data.http.RetrofitManager;
import com.molmc.cypress.injection.component.ApplicationComponent;
import com.molmc.cypress.injection.component.DaggerApplicationComponent;
import com.molmc.cypress.injection.module.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import io.fabric.sdk.android.Fabric;


/**
 * features:
 * Author：  hhe on 16-8-30 16:15
 * Email：   hhe@molmc.com
 */

public class CypressApplication extends Application {

	ApplicationComponent mApplicationComponent;
	private RefWatcher refWatcher;

	public static RefWatcher getRefWatcher(Context context) {
		CypressApplication application = (CypressApplication) context.getApplicationContext();
		return application.refWatcher;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initCrashlytics();
		initLeakCanary();

		//初始化Log
		AppLog.init(BuildConfig.DEBUG);
		//初始化http模块
		RetrofitManager.init(this);
		//初始化图片加载模块
		ImageLoader.init(this);
	}


	//初始化数据统计
	private void initCrashlytics(){
		if (!BuildConfig.DEBUG) {
			Fabric.with(this, new Crashlytics());
		}
	}

	private void initLeakCanary() {
		if (BuildConfig.DEBUG) {
			refWatcher = LeakCanary.install(this);
		} else {
			refWatcher = installLeakCanary();
		}
	}

	/**
	 * release版本使用此方法
	 */
	protected RefWatcher installLeakCanary() {
		return RefWatcher.DISABLED;
	}

	public static CypressApplication get(Context context) {
		return (CypressApplication) context.getApplicationContext();
	}

	public ApplicationComponent getComponent() {
		if (mApplicationComponent == null) {
			mApplicationComponent = DaggerApplicationComponent.builder()
					.applicationModule(new ApplicationModule(this))
					.build();
		}
		return mApplicationComponent;
	}

	// Needed to replace the component with a test specific one
	public void setComponent(ApplicationComponent applicationComponent) {
		mApplicationComponent = applicationComponent;
	}

}
