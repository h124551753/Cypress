package com.molmc.cypress.data.http;

import rx.Subscriber;

/**
 * features:
 * Author：  hhe on 16-9-3 15:30
 * Email：   hui@molmc.com
 */

public abstract class ResponseObserver<T> extends Subscriber<T> {
	@Override
	public void onCompleted() {

	}

	@Override
	public void onError(Throwable e) {
		onFailed(e);
	}

	@Override
	public void onNext(T t) {
		onSuccess(t);
	}

	public abstract void onSuccess(T t);

	public abstract void onFailed(Throwable e);

}
