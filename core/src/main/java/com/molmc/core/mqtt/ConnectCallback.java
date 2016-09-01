package com.molmc.core.mqtt;

/**
 * features: mqtt 连接回调
 * Author：  hhe on 16-7-29 16:41
 * Email：   hhe@molmc.com
 */

public interface ConnectCallback {

	void onConnectSuccess();

	void onConnectFailure();
}
