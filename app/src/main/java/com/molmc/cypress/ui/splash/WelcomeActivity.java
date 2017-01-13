package com.molmc.cypress.ui.splash;

import android.os.Bundle;

import com.molmc.cypress.common.config.AppPref;
import com.molmc.cypress.ui.base.BaseActivity;
import com.molmc.cypress.ui.main.MainActivity;

/**
 * features:
 * Author：  hhe on 16-9-2 17:53
 * Email：   hui@molmc.com
 */

public class WelcomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (AppPref.isFirstRunning(this)){
			IntroduceActivity.launch(this);
		} else {
			MainActivity.launch(this);
		}
		finish();
	}
}
