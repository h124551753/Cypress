package com.molmc.cypress.injection.component;

import com.molmc.cypress.injection.PerActivity;
import com.molmc.cypress.injection.module.ActivityModule;
import com.molmc.cypress.injection.module.ApiServiceModule;
import com.molmc.cypress.ui.explore.ExploreFragment;
import com.molmc.cypress.ui.main.FavoriteFragment;
import com.molmc.cypress.ui.main.ForumFragment;
import com.molmc.cypress.ui.main.NearbyFragment;

import dagger.Component;

/**
 * features:
 * Author：  hhe on 16-9-3 10:15
 * Email：   hui@molmc.com
 */

@PerActivity
@Component(
		dependencies = ApplicationComponent.class,
		modules = {ActivityModule.class, ApiServiceModule.class})
public interface MainComponent extends ActivityComponent {

	void inject(ForumFragment forumFragment);

	void inject(ExploreFragment exploreFragment);

	void inject(NearbyFragment nearbyFragment);

	void inject(FavoriteFragment favoriteFragment);

}
