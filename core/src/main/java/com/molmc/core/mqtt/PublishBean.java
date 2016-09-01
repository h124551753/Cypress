package com.molmc.core.mqtt;

import java.io.Serializable;

/**
 * features: PublishBean
 * Author：  hhe on 16-7-29 16:01
 * Email：   hhe@molmc.com
 */

public class PublishBean implements Serializable {
	private static final long serialVersionUID = 5656012243701071578L;

	private String topic;
	private int qos;
	private byte[] payload;
	private boolean retained;
	private PublishListener publishListener;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getQos() {
		return qos;
	}

	public void setQos(int qos) {
		this.qos = qos;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	public PublishListener getPublishListener() {
		return publishListener;
	}

	public void setPublishListener(PublishListener publishListener) {
		this.publishListener = publishListener;
	}

	public boolean isRetained() {
		return retained;
	}

	public void setRetained(boolean retained) {
		this.retained = retained;
	}
}
