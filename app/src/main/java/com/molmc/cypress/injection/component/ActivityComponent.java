package com.molmc.cypress.injection.component;

import android.app.Activity;

import com.molmc.cypress.injection.PerActivity;
import com.molmc.cypress.injection.module.ActivityModule;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();
}
