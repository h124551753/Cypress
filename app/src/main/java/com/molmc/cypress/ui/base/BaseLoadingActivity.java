package com.molmc.cypress.ui.base;

import android.os.Bundle;

import com.molmc.cypress.mvp.lce.LoadView;
import com.molmc.cypress.ui.widget.LoadingView;

/**
 * features: 加载基类
 * Author：  hhe on 16-9-2 16:40
 * Email：   hui@molmc.com
 */

public abstract class BaseLoadingActivity extends BaseActivity implements LoadView {

	private LoadingView mLoadingView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLoadingView = new LoadingView(this, getLoadingMessage());
	}

	@Override
	public void showLoading() {
		mLoadingView.show();
	}

	@Override
	public void dismissLoading() {
		mLoadingView.dismiss();

	}

	@Override
	public void error(Throwable e) {
		showToast(e.getMessage());
	}

	public abstract String getLoadingMessage();
}
