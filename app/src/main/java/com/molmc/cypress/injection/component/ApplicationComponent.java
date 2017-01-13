package com.molmc.cypress.injection.component;

import android.app.Application;
import android.content.Context;

import com.molmc.cypress.injection.ApplicationContext;
import com.molmc.cypress.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * features:
 * Author：  hhe on 16-9-2 12:11
 * Email：   hui@molmc.com
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

	@ApplicationContext
	Context context();

	Application application();
}
