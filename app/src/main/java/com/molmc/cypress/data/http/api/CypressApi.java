package com.molmc.cypress.data.http.api;

import android.support.annotation.IntDef;

import com.molmc.cypress.data.http.model.TrendingRepo;

import java.util.ArrayList;

import rx.Observable;

/**
 * features:
 * Author：  hhe on 16-9-3 15:35
 * Email：   hui@molmc.com
 */

public interface CypressApi {

	public int LANG_JAVA = 1;
	public int LANG_OC = 2;
	public int LANG_SWIFT = 3;
	public int LANG_HTML = 4;
	public int LANG_PYTHON = 5;
	public int LANG_BASH = 6;

	@IntDef({
			LANG_JAVA,
			LANG_OC,
			LANG_SWIFT,
			LANG_HTML,
			LANG_PYTHON,
			LANG_BASH
	})
	@interface LanguageType{

	}

	/**
	 * Get trending repo base on type.
	 * @param language
	 * @return
	 */
	Observable<ArrayList<TrendingRepo>> getTrendingRepo(@LanguageType int language);
}
