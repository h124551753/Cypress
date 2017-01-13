package com.molmc.cypress.injection.module;

import com.molmc.cypress.data.http.api.CypressApi;
import com.molmc.cypress.data.http.api.CypressApiImpl;

import dagger.Module;
import dagger.Provides;

/**
 * features:
 * Author：  hhe on 16-9-3 15:39
 * Email：   hui@molmc.com
 */

@Module
public class ApiServiceModule {

	@Provides
	CypressApi provideCypressApi(CypressApiImpl dataSource){
		return dataSource;
	}
}
