package com.molmc.cypress.ui.explore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.molmc.cypress.R;
import com.molmc.cypress.common.utils.AppLog;
import com.molmc.cypress.data.http.api.CypressApi;
import com.molmc.cypress.data.http.model.TrendingRepo;
import com.molmc.cypress.injection.component.MainComponent;
import com.molmc.cypress.ui.base.BaseLceFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * features:
 * Author：  hhe on 16-9-3 11:24
 * Email：   hui@molmc.com
 */

public class ExploreFragment extends BaseLceFragment<ArrayList<TrendingRepo>> {

	private static final String EXTRA_LANG = "extra_lang";

	public static ExploreFragment newInstance(int lang) {
		Bundle args = new Bundle();
		args.putInt(EXTRA_LANG, lang);

		ExploreFragment fragment = new ExploreFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@BindView(R.id.repo_list)
	RecyclerView repoList;
	@BindView(R.id.refresh_layout)
	SwipeRefreshLayout refreshLayout;

	@Inject
	ExplorePresenter mPresenter;

	private ExploreAdapter mAdapter;
	private int mCurrentLang;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getComponent(MainComponent.class).inject(this);
		mCurrentLang = getArguments().getInt(EXTRA_LANG, CypressApi.LANG_JAVA);
		mPresenter.attachView(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: inflate a fragment view
		View rootView = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.bind(this, rootView);
		initViews();
		return rootView;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mPresenter.loadTrendingRepo(mCurrentLang);
	}

	private void initViews() {
		refreshLayout.setOnRefreshListener(mRefreshListener);

		mAdapter = new ExploreAdapter(null);
		mAdapter.setOnRecyclerViewItemClickListener(mItemClickListener);
		mAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null));

		repoList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
		repoList.addItemDecoration(new HorizontalDividerItemDecoration
				.Builder(getActivity())
				.color(Color.TRANSPARENT)
				.size(getResources().getDimensionPixelSize(R.dimen.divider_height))
				.build());

		repoList.setAdapter(mAdapter);
	}

	private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
		@Override
		public void onItemClick(View view, int i) {
			TrendingRepo trendingRepo = mAdapter.getItem(i);

			String fullName = trendingRepo.getTitle();
			String[] array = fullName.split("\\/");
//			RepoDetailActivity.launch(getActivity(), array[0], array[1]);
		}
	};

	private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
		@Override
		public void onRefresh() {
			AppLog.d("onRefresh, mCurrentLang:" + mCurrentLang);
			mPresenter.loadTrendingRepo(mCurrentLang);
		}
	};


	@Override
	public int getContentView() {
		return R.layout.lay_pulldown_refresh;
	}

	@Override
	public View.OnClickListener getRetryListener() {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mPresenter.detachView();
	}
}
