package com.molmc.cypress.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * features:
 * Author：  hhe on 16-9-2 14:48
 * Email：   hui@molmc.com
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext {
}
