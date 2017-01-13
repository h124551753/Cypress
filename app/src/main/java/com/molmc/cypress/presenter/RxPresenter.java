package com.molmc.cypress.presenter;


import com.molmc.mvp.BasePresenter;
import com.molmc.mvp.MvpView;

import rx.subscriptions.CompositeSubscription;

/**
 * features:
 * Author：  hhe on 16-9-3 15:53
 * Email：   hui@molmc.com
 */

public class RxPresenter<V extends MvpView> extends BasePresenter<V> {

	protected CompositeSubscription mCompositeSubscription;

	@Override
	public void attachView(V mvpView) {
		super.attachView(mvpView);
		mCompositeSubscription = new CompositeSubscription();
	}

	@Override
	public void detachView() {
		super.detachView();
		mCompositeSubscription.clear();
		mCompositeSubscription = null;
	}
}
