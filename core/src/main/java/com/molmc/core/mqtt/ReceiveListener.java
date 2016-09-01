package com.molmc.core.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * features: 接收到sub的数据回调
 * Author：  hhe on 16-7-29 14:36
 * Email：   hhe@molmc.com
 */

public interface ReceiveListener {

	/**
	 * 接收到订阅的消息
	 * @param topic
	 * @param message
	 */
	void onReceive(String topic, MqttMessage message);
}
