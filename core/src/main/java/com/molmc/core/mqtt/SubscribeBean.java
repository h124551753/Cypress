package com.molmc.core.mqtt;

import java.io.Serializable;

/**
 * Created by hhe on 15-12-10.
 */
public class SubscribeBean implements Serializable {
    private static final long serialVersionUID = 7177717036113979094L;
    private String topic;
    private int qos;
    private ReceiveListener receiveListner;
    private SubscribeListener successListener;

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

    public ReceiveListener getReceiveListner() {
        return receiveListner;
    }

    public void setReceiveListner(ReceiveListener receiveListner) {
        this.receiveListner = receiveListner;
    }

    public SubscribeListener getSuccessListener() {
        return successListener;
    }

    public void setSuccessListener(SubscribeListener successListener) {
        this.successListener = successListener;
    }
}
