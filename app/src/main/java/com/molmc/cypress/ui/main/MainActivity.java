package com.molmc.cypress.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.molmc.cypress.CypressApplication;
import com.molmc.cypress.R;
import com.molmc.cypress.common.utils.AppLog;
import com.molmc.cypress.injection.HasComponent;
import com.molmc.cypress.injection.component.MainComponent;
import com.molmc.cypress.injection.module.ActivityModule;
import com.molmc.cypress.ui.base.BaseActivity;
import com.molmc.cypress.ui.explore.ExploreFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements HasComponent<MainComponent>{

	public static void launch(Context from) {
		from.startActivity(new Intent(from, MainActivity.class));
	}

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.frameContent)
	FrameLayout frameContent;
	@BindView(R.id.bottomBar)
	BottomBar bottomBar;

	private FragmentManager mFragmentManager = getSupportFragmentManager();
	private Fragment mCurrentFragment;
	private long mLastBackTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		bottomBar.setOnTabSelectListener(mTabSelectListener);
	}

	private OnTabSelectListener mTabSelectListener = new OnTabSelectListener() {
		@Override
		public void onTabSelected(@IdRes int tabId) {
			switchMenu(getFragmentName(tabId));
			if (tabId == R.id.tab_favorites){
				AppLog.i("fragment favorites");
			}else if (tabId == R.id.tab_explore){
				AppLog.i("fragment explore");
			}else if (tabId == R.id.tab_nearby){
				AppLog.i("fragment nearby");
				BottomBarTab nearby = bottomBar.getTabWithId(R.id.tab_nearby);
				nearby.setBadgeCount(5);
			}else if(tabId == R.id.tab_friends){
				AppLog.i("fragment friends");
			}
		}
	};

	private void switchMenu(String fragmentName) {

		Fragment fragment = mFragmentManager.findFragmentByTag(fragmentName);

		if (fragment != null) {
			if (fragment == mCurrentFragment) return;

			mFragmentManager.beginTransaction().show(fragment).commit();
		} else {
			fragment = Fragment.instantiate(this, fragmentName);
			mFragmentManager.beginTransaction().add(R.id.content_frame, fragment, fragmentName).commit();
		}

		if (mCurrentFragment != null) {
			mFragmentManager.beginTransaction().hide(mCurrentFragment).commit();
		}

		mCurrentFragment = fragment;
	}

	private String getFragmentName(int tabId) {
		switch (tabId) {
			case R.id.tab_forum:
				return ForumFragment.class.getName();
			case R.id.tab_favorites:
				return FavoriteFragment.class.getName();
			case R.id.tab_explore:
				return ExploreFragment.class.getName();
			case R.id.tab_nearby:
				return NearbyFragment.class.getName();
			case R.id.tab_friends:
				return MineFragment.class.getName();

			default:
				return null;
		}
	}

	@Override
	public void onBackPressed() {
		if ((System.currentTimeMillis() - mLastBackTime) < 1000) {
			finish();
		} else {
			mLastBackTime = System.currentTimeMillis();
			showToast(R.string.comm_hint_exit);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public MainComponent getComponent() {
		return DaggerMainComponent.builder()
				.applicationComponent(CypressApplication.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
	}
}
