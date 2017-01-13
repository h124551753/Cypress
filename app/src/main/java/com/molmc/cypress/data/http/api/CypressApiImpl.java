package com.molmc.cypress.data.http.api;

import com.molmc.core.utils.AppLog;
import com.molmc.cypress.data.http.model.TrendingRepo;
import com.molmc.cypress.data.http.response.TrendingResultResp;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * features:
 * Author：  hhe on 16-9-2 12:08
 * Email：   hui@molmc.com
 */

public class CypressApiImpl implements CypressApi {

	ApiService mApiService;

	@Inject
	public CypressApiImpl(ApiService service) {
		mApiService = service;
	}

	@Override
	public Observable<ArrayList<TrendingRepo>> getTrendingRepo(@LanguageType final int language) {
		return mApiService.getTrendingRepos()
				.map(new Func1<TrendingResultResp, ArrayList<TrendingRepo>>() {
					@Override
					public ArrayList<TrendingRepo> call(TrendingResultResp resp) {
						switch (language) {
							case LANG_JAVA:
								return resp.getJava();

							case LANG_OC:
								return resp.getOc();

							case LANG_SWIFT:
								return resp.getSwift();

							case LANG_HTML:
								return resp.getHtml();

							case LANG_PYTHON:
								return resp.getPython();

							case LANG_BASH:
								return resp.getBash();

							default:
								AppLog.w("unknown language");
								break;
						}
						return null;
					}
				});
	}
}
