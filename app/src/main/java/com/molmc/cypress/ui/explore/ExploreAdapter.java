package com.molmc.cypress.ui.explore;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.molmc.cypress.R;
import com.molmc.cypress.data.http.model.TrendingRepo;

import java.util.List;

/**
 * features:
 * Author：  hhe on 16-9-3 16:39
 * Email：   hui@molmc.com
 */

public class ExploreAdapter extends BaseQuickAdapter<TrendingRepo> {

	public ExploreAdapter(List<TrendingRepo> data) {
		super(R.layout.item_repo, data);
	}

	@Override
	protected void convert(BaseViewHolder baseViewHolder, TrendingRepo trendingRepo) {
		baseViewHolder.setText(R.id.name, trendingRepo.getTitle());
		baseViewHolder.setText(R.id.desc, trendingRepo.getDescription().trim());
	}
}
