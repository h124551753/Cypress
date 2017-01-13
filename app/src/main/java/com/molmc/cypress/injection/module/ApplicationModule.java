package com.molmc.cypress.injection.module;

import android.app.Application;
import android.content.Context;

import com.molmc.cypress.injection.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * features:
 * Author：  hhe on 16-9-2 14:50
 * Email：   hui@molmc.com
 */

@Module
public class ApplicationModule {
	protected final Application mApplication;

	public ApplicationModule(Application application){
		mApplication = application;
	}

	@Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }
}
