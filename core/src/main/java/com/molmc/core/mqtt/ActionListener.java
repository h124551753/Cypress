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

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/**
 * This Class handles receiving information from the
 * {@link MqttAndroidClient} and updating the {@link Connection} associated with
 * the action
 */
class ActionListener implements IMqttActionListener {

    /**
     * Actions that can be performed Asynchronously <strong>and</strong> associated with a
     * {@link ActionListener} object
     *
     */
    enum Action {
        /** Connect Action **/
        CONNECT,
        /** ReConnect Action **/
        RECONNECT,
        /** Disconnect Action **/
        DISCONNECT,
        /** Subscribe Action **/
        SUBSCRIBE,
        /** Publish Action **/
        PUBLISH
    }

    /**
     * The {@link Action} that is associated with this instance of
     * <code>ActionListener</code>
     **/
    private Action action;
    /** The arguments passed to be used for formatting strings**/
    private String[] additionalArgs;
    /** {@link Context} for performing various operations **/
    private Context context;

    /**   Mqtt连接实例   */
    private Connection mConnection;

    /**
     * Creates a generic action listener for actions performed form any activity
     *
     * @param context
     *            The application context
     * @param action
     *            The action that is being performed
     * @param connection
     *            The handle for the client which the action is being performed
     *            on
     * @param additionalArgs
     *            Used for as arguments for string formating
     */

    public ActionListener(Context context, Action action,
                          Connection connection, String... additionalArgs) {
        this.context = context;
        this.action = action;
        this.mConnection = connection;
        this.additionalArgs = additionalArgs;
    }

    /**
     * Creates a generic action listener for actions performed form any activity
     *
     * @param context
     *            The application context
     * @param action
     *            The action that is being performed
     * @param connection
     *            The handle for the client which the action is being performed
     *            on
     */
    public ActionListener(Context context, Action action, Connection connection) {
        this.context = context;
        this.action = action;
        this.mConnection = connection;
    }
    /**
     * The action associated with this listener has been successful.
     *
     * @param asyncActionToken
     *            This argument is not used
     */
    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        switch (action) {
            case CONNECT:
                connect();
                break;
            case RECONNECT:
                reSubscribeTopic();
                break;
            case DISCONNECT:
                disconnect();
                break;
            case SUBSCRIBE:
                subscribe();
                break;
            case PUBLISH:
                publish();
                break;
        }

    }

    /**
     * A publish action has been successfully completed, update connection
     * object associated with the client this action belongs to, then notify the
     * user of success
     */
    private void publish() {
        String actionTaken = context.getString(R.string.toast_pub_success,
                (Object[]) additionalArgs);
        AppLog.i(actionTaken);
    }

    /**
     * A subscribe action has been successfully completed, update the connection
     * object associated with the client this action belongs to and then notify
     * the user of success
     */
    private void subscribe() {
        String actionTaken = context.getString(R.string.toast_sub_success,
                (Object[]) additionalArgs);
        AppLog.i(actionTaken);
    }

    /**
     * A disconnection action has been successfully completed, update the
     * connection object associated with the client this action belongs to and
     * then notify the user of success.
     */
    private void disconnect() {
        mConnection.changeConnectionStatus(Connection.ConnectionStatus.DISCONNECTED);
        String actionTaken = context.getString(R.string.toast_disconnected);
        AppLog.i(actionTaken);
    }

    /**
     * A connection action has been successfully completed, update the
     * connection object associated with the client this action belongs to and
     * then notify the user of success.
     */
    private void connect() {
        mConnection.setManualDisconnect(false);
        mConnection.changeConnectionStatus(Connection.ConnectionStatus.CONNECTED);
        mConnection.subscribeAll();
        AppLog.i(context.getString(R.string.client_connected));
    }


    private void reSubscribeTopic(){
        mConnection.setManualDisconnect(false);
        mConnection.changeConnectionStatus(Connection.ConnectionStatus.CONNECTED);
        mConnection.reSubscribeAll();
        AppLog.i(context.getString(R.string.client_reconnected));
    }

    /**
     * The action associated with the object was a failure
     *
     * @param token
     *            This argument is not used
     * @param exception
     *            The exception which indicates why the action failed
     */
    @Override
    public void onFailure(IMqttToken token, Throwable exception) {
        switch (action) {
            case CONNECT:
                connect(exception);
                break;
            case DISCONNECT:
                disconnect(exception);
                break;
            case SUBSCRIBE:
                subscribe(exception);
                break;
            case PUBLISH:
                publish(exception);
                break;
        }

    }

    /**
     * A publish action was unsuccessful, notify user and update client history
     *
     * @param exception
     *            This argument is not used
     */
    private void publish(Throwable exception) {
        String action = context.getString(R.string.toast_pub_failed,
                (Object[]) additionalArgs);
        AppLog.i(action);

    }

    /**
     * A subscribe action was unsuccessful, notify user and update client history
     * @param exception This argument is not used
     */
    private void subscribe(Throwable exception) {
        String action = context.getString(R.string.toast_sub_failed,
                (Object[]) additionalArgs);
        AppLog.i( action);
    }

    /**
     * A disconnect action was unsuccessful, notify user and update client history
     * @param exception This argument is not used
     */
    private void disconnect(Throwable exception) {
        mConnection.changeConnectionStatus(Connection.ConnectionStatus.DISCONNECTED);
    }

    /**
     * A connect action was unsuccessful, notify the user and update client history
     * @param exception This argument is not used
     */
    private void connect(Throwable exception) {
        mConnection.changeConnectionStatus(Connection.ConnectionStatus.ERROR);
        AppLog.i(context.getString(R.string.connectionError));
    }

}
