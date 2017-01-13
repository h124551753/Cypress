package com.molmc.mvp;

import android.support.annotation.UiThread;

/**
 * features:
 * Author：  hhe on 16-9-1 23:38
 * Email：   hui@molmc.com
 */

public interface Presenter<V extends MvpView> {

	/**
	 * Set or attach the view to this presenter
	 */
	@UiThread
	void attachView(V view);

	/**
	 * Will be called if the view has been destroyed. Typically this method will be invoked from
	 * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
	 */
	@UiThread
	void detachView();
}
