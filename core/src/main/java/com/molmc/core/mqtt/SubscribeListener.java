package com.molmc.core.mqtt;

/** subscribe topic回调
 * features:
 * Author：  hhe on 16-7-29 14:35
 * Email：   hhe@molmc.com
 */

public interface SubscribeListener {

	/**
	 * subscribe 成功回调
	 * @param topic
	 */
	void onSuccess(String topic);

	/**
	 * subscribe 失败回调
	 * @param topic
	 */
	void onFailed(String topic, String errMsg);
}
