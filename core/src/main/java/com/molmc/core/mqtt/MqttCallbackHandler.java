/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */
package com.molmc.core.mqtt;

import android.content.Context;

import com.molmc.core.R;
import com.molmc.core.utils.AppLog;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Handles call backs from the MQTT Client
 *
 */
public class MqttCallbackHandler implements MqttCallback {

    /** {@link Context} for the application used to format and import external strings**/
    private Context context;
    /** Client handle to reference the connection that this handler is attached to**/
    private Connection mConnection;

    private HashMap<String, ReceiveListener> receiveListnerHashMap = new HashMap<>();

    private HashMap<String, SubscribeListener> publishListnerHashMap = new HashMap<>();

    /**
     * Creates an <code>MqttCallbackHandler</code> object
     * @param context The application's context
     */
    public MqttCallbackHandler(Context context, Connection connection) {
        this.context = context;
        this.mConnection = connection;
    }

    /**
     * 注册一个接收监听回调
     * @param topic
     * @param iMqttReceiveListner
     */
    public void registerReceiveListner(String topic, ReceiveListener iMqttReceiveListner){
        receiveListnerHashMap.put(topic, iMqttReceiveListner);
    }

    /**
     * 注册一个接收监听回调
     * @param topic
     * @param iMqttSuccessListener
     */
    public void registerPublishListner(String topic, SubscribeListener iMqttSuccessListener){
        publishListnerHashMap.put(topic, iMqttSuccessListener);
    }
    /**
     * 注销一个接收监听回调
     * @param topic
     */
    public void unregisterReceiveListner(String topic){
        receiveListnerHashMap.remove(topic);
    }

    /**
     * 注销所有监听
     */
    public void unregisterAllListner(){
        receiveListnerHashMap.clear();
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    @Override
    public void connectionLost(Throwable cause) {
        if (cause != null) {
            mConnection.changeConnectionStatus(Connection.ConnectionStatus.DISCONNECTED);
            if (!mConnection.isManualDisconnect()){
                AppLog.i(context.getString(R.string.notifyTitle_connectionLost));
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!mConnection.isManualDisconnect()){
                            this.cancel();
                        }
                        mConnection.reconnection();
                    }
                }, 5000);
            }
        }
    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if(receiveListnerHashMap.containsKey(topic)){
            receiveListnerHashMap.get(topic).onReceive(topic, message);
        }else{
            String tempTopic = topic.replaceAll("v1/(\\w+)/", "v1/+/");
            if (receiveListnerHashMap.containsKey(tempTopic)){
                receiveListnerHashMap.get(tempTopic).onReceive(topic, message);
            }
        }
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Do nothing
        AppLog.i("publish: deliveryComplete");
    }

}
