package com.molmc.cypress.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.molmc.cypress.R;
import com.molmc.cypress.common.config.AppPref;
import com.molmc.cypress.ui.main.MainActivity;

/**
 * features: span展示界面
 * Author：  hhe on 16-9-2 18:01
 * Email：   hui@molmc.com
 */

public class IntroduceActivity extends AppIntro {

	public static void launch(Context from) {
		from.startActivity(new Intent(from, IntroduceActivity.class));
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addSlide(AppIntroFragment.newInstance(
				"Code is everything",
				"Read the fucking source code.",
				R.drawable.ic_cypress,
				getResources().getColor(R.color.md_teal_400),
				getResources().getColor(R.color.white),
				getResources().getColor(R.color.white)
		));

		addSlide(AppIntroFragment.newInstance(
				"Let's coding",
				"Talk is cheap, show me the code",
				R.drawable.ic_cypress,
				getResources().getColor(R.color.md_indigo_400),
				getResources().getColor(R.color.white),
				getResources().getColor(R.color.white)
		));
		addSlide(AppIntroFragment.newInstance(
				"Well, well",
				"GET STARTED",
				R.drawable.ic_cypress,
				getResources().getColor(R.color.md_green_400),
				getResources().getColor(R.color.white),
				getResources().getColor(R.color.white)
		));

		showSkipButton(false);
	}

	@Override
	public void onDonePressed(Fragment currentFragment) {
		super.onDonePressed(currentFragment);

		AppPref.setAlreadyRun(this);

		MainActivity.launch(this);
		finish();
	}

}
