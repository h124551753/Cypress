package com.molmc.mvp.lce;

import android.support.annotation.UiThread;

import com.molmc.mvp.MvpView;

/**
 * features:
 * Author：  hhe on 16-9-1 23:41
 * Email：   hui@molmc.com
 */

public interface LceView<M> extends MvpView {
	@UiThread
	public void showLoading();

	@UiThread
	public void dismissLoading();

	@UiThread
	public void showContent(M data);

	@UiThread
	public void showError(Throwable e);

	@UiThread
	public void showEmpty();
}
