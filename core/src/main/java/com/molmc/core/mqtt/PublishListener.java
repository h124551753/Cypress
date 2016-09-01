package com.molmc.core.mqtt;

/**
 * features: publish topic 回调
 * Author：  hhe on 16-7-29 14:38
 * Email：   hhe@molmc.com
 */

public interface PublishListener {

	/**
	 * 发布消息成功
	 * @param topic
	 */
	void onSuccess(String topic);

	/**
	 * 发布消息失败
	 * @param topic
	 * @param errMsg
	 */
	void onFailed(String topic, String errMsg);
}
