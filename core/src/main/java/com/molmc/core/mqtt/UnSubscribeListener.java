package com.molmc.core.mqtt;

/** subscribe topic回调
 * features:
 * Author：  hhe on 16-7-29 14:35
 * Email：   hhe@molmc.com
 */

public interface UnSubscribeListener {
	void onSuccess(String topic);

	void onFailed(String topic, String errMsg);
}
