package com.molmc.cypress.ui.base;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.molmc.cypress.CypressApplication;
import com.molmc.cypress.BuildConfig;
import com.squareup.leakcanary.RefWatcher;

/**
 * features: Activity 基类
 * Author：  hhe on 16-9-2 00:23
 * Email：   hui@molmc.com
 */

public class BaseActivity extends AppCompatActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initStrictMode();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		RefWatcher refWatcher = CypressApplication.getRefWatcher(this);
		refWatcher.watch(this);
	}

	@TargetApi(Build.VERSION_CODES.M)
	public void requestPermissionsSafely(String[] permissions, int requestCode) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			requestPermissions(permissions, requestCode);
		}
	}

	@TargetApi(Build.VERSION_CODES.M)
	public boolean hasPermission(String permission) {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
				checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
	}

	private void initStrictMode() {
		if (BuildConfig.DEBUG) {
			StrictMode.setThreadPolicy(
					new StrictMode.ThreadPolicy.Builder()
							.detectAll()
//                            .penaltyDialog() // 弹出违规提示对话框
							.penaltyLog() // 在logcat中打印违规异常信息
							.build());
			StrictMode.setVmPolicy(
					new StrictMode.VmPolicy.Builder()
							.detectAll()
							.penaltyLog()
							.build());
		}
	}

	protected void showToast(int res){
		Snackbar.make(getWindow().getDecorView(), res, Snackbar.LENGTH_LONG).setAction("Action", null).show();
	}

	protected void showToast(String msg){
		Snackbar.make(getWindow().getDecorView(), msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
	}
}
