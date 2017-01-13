package com.molmc.cypress.ui.explore;

import com.molmc.cypress.data.http.ResponseObserver;
import com.molmc.cypress.data.http.api.CypressApi;
import com.molmc.cypress.data.http.model.TrendingRepo;
import com.molmc.cypress.presenter.RxPresenter;
import com.molmc.cypress.mvp.lce.LceView;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * features:
 * Author：  hhe on 16-9-3 16:04
 * Email：   hui@molmc.com
 */

public class ExplorePresenter extends RxPresenter<LceView<ArrayList<TrendingRepo>>>{

	private final CypressApi mCypressApi;

	public ExplorePresenter(CypressApi cypressApi){
		mCypressApi = cypressApi;
	}

	public void loadTrendingRepo(@CypressApi.LanguageType int languageType){
		mCompositeSubscription.add(mCypressApi.getTrendingRepo(languageType)
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
		.doOnSubscribe(new Action0() {
			@Override
			public void call() {
				getMvpView().showLoading();
			}
		})
		.doOnTerminate(new Action0() {
			@Override
			public void call() {
				getMvpView().dismissLoading();
			}
		})
		.subscribe(new ResponseObserver<ArrayList<TrendingRepo>>() {
			@Override
			public void onSuccess(ArrayList<TrendingRepo> results) {
				getMvpView().showContent(results);
			}

			@Override
			public void onFailed(Throwable e) {
				super.onError(e);
				getMvpView().showError(e);
			}
		}));
	}
}
