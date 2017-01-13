package com.molmc.cypress.data.http.api;

import com.molmc.cypress.data.http.response.TrendingResultResp;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

/**
 * features:
 * Author：  hhe on 16-9-3 14:12
 * Email：   hui@molmc.com
 */

public interface ApiService {

	@Headers("Cache-Control: public, max-age=600")
	@GET("trending?languages[]=java&languages[]=swift&languages[]=objective-c&languages[]=bash&languages[]=python&languages[]=html")
	Observable<TrendingResultResp> getTrendingRepos();
}
