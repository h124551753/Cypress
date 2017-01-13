package com.molmc.core.mqtt;

import android.content.Context;
import android.text.TextUtils;

import com.molmc.core.R;
import com.molmc.core.utils.AppLog;
import com.molmc.core.utils.NetworkUtil;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * features:
 * Author：  hhe on 16-9-1 16:15
 * Email：   hui@molmc.com
 */

public class Connection {
	/**
	 * Connections status for  a connection
	 */
	public enum ConnectionStatus {

		/**
		 * Client is Connecting
		 **/
		CONNECTING,
		/**
		 * Client is Connected
		 **/
		CONNECTED,
		/**
		 * Client is Disconnecting
		 **/
		DISCONNECTING,
		/**
		 * Client is Disconnected
		 **/
		DISCONNECTED,
		/**
		 * Client has encountered an Error
		 **/
		ERROR,
		/**
		 * Status is unknown
		 **/
		NONE
	}

	/**
	 * ClientHandle for this Connection Object
	 **/
	private String clientHandle = null;
	/**
	 * The clientId of the client associated with this <code>Connection</code> object
	 **/
	private String clientId = null;
	/**
	 * The host that the {@link MqttAndroidClient} represented by this <code>Connection</code> is represented by
	 **/
	private String host = null;
	/**
	 * The port on the server this client is connecting to
	 **/
	private int port = 1883;
	/**
	 * {@link ConnectionStatus} of the {@link MqttAndroidClient} represented by this <code>Connection</code> object. Default value is {@link ConnectionStatus#NONE}
	 **/
	private ConnectionStatus status = ConnectionStatus.NONE;

	/**
	 * subscribe topic list
	 */
	private Map<String, SubscribeBean> subTopics = new HashMap<>();

	/**
	 * subscribed topic list
	 */
	private Map<String, SubscribeBean> subscribedTopics = new HashMap<>();

	/**
	 * subscribe topic failed topics
	 */
	private Map<String, PublishBean> publishFaildTopics = new HashMap<>();

	/**
	 * The {@link MqttAndroidClient} instance this class represents
	 **/
	private MqttAndroidClient client = null;

	/**
	 * Collection of {@link PropertyChangeListener}
	 **/
	private ArrayList<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

	/**
	 * The {@link Context} of the application this object is part of
	 **/
	private Context context = null;

	/**
	 * The {@link MqttConnectOptions} that were used to connect this client
	 **/
	private MqttConnectOptions conOpt = new MqttConnectOptions();

	private MqttCallbackHandler mqttCallbackHandler;

	/**
	 * True if this connection is secured using SSL
	 **/
	private boolean sslConnection = false;

	/**
	 * 指示手动断开连接
	 */
	private boolean manualDisconnect = false;

	/**
	 * Creates a connection object with the server information and the client
	 * hand which is the reference used to pass the client around activities
	 *
	 * @param clientHandle  The handle to this <code>Connection</code> object
	 * @param clientId      The Id of the client
	 * @param host          The server which the client is connecting to
	 * @param port          The port on the server which the client will attempt to connect to
	 * @param context       The application context
	 * @param client        The MqttAndroidClient which communicates with the service for this connection
	 * @param sslConnection true if the connection is secured by SSL
	 */
	public Connection(String clientHandle, String clientId, String host,
	                  int port, Context context, MqttAndroidClient client, boolean sslConnection) {
		//generate the client handle from its hash code
		this.clientHandle = clientHandle;
		this.clientId = clientId;
		this.host = host;
		this.port = port;
		this.context = context;
		this.client = client;
		this.sslConnection = sslConnection;
	}


	/**
	 * Creates a connection from persisted information in the database store, attempting
	 * to create a {@link MqttAndroidClient} and the client handle.
	 *
	 * @param clientId      The id of the client
	 * @param host          the server which the client is connecting to
	 * @param port          the port on the server which the client will attempt to connect to
	 * @param context       the application context
	 * @param sslConnection true if the connection is secured by SSL
	 *
	 * @return a new instance of <code>Connection</code>
	 */
	public static Connection createConnection(String clientId, String host,
	                                          int port, Context context, boolean sslConnection) {
		String handle = null;
		String uri = null;
		if (TextUtils.isEmpty(clientId)) {
			clientId = genClientId(13);
		}
		if (sslConnection) {
			uri = "ssl://" + host + ":" + port;
			handle = uri + clientId;
		} else {
			uri = "tcp://" + host + ":" + port;
			handle = uri + clientId;
		}
		AppLog.i("mqtt uri = " + uri + "; clientid = " + clientId);
		MqttAndroidClient client = new MqttAndroidClient(context, uri, clientId);
		return new Connection(handle, clientId, host, port, context, client, sslConnection);
	}


	/**
	 * 用于生成clientid
	 *
	 * @param num 截取位数
	 *
	 * @return
	 */
	public static String genClientId(int num) {
		String seed = "0123456789qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		String timeStamp = String.valueOf(System.currentTimeMillis());
		String temp = "";
		for (int i = 0; i < num; i++) {
			temp += seed.charAt((int) Math.ceil(Math.random() * 100000000L) % seed.length());
		}
		return timeStamp.concat(temp);
	}


	/**
	 * Gets the client handle for this connection
	 *
	 * @return client Handle for this connection
	 */
	public String getHandle() {
		return clientHandle;
	}


	public void setManualDisconnect(boolean manualDisconnect) {
		this.manualDisconnect = manualDisconnect;
	}

	public boolean isManualDisconnect() {
		return this.manualDisconnect;
	}

	/**
	 * Determines if the client is connected
	 *
	 * @return is the client connected
	 */
	public boolean isConnected() {
		return status == ConnectionStatus.CONNECTED;
	}

	/**
	 * Determines if the client is in a state of connecting or connected.
	 *
	 * @return if the client is connecting or connected
	 */
	public boolean isConnectedOrConnecting() {
		return (status == ConnectionStatus.CONNECTED) || (status == ConnectionStatus.CONNECTING);
	}


	/**
	 * Client is currently not in an error state
	 *
	 * @return true if the client is in not an error state
	 */
	public boolean noError() {
		return status != ConnectionStatus.ERROR;
	}


	/**
	 * Changes the connection status of the client
	 *
	 * @param connectionStatus The connection status of this connection
	 */
	public void changeConnectionStatus(ConnectionStatus connectionStatus) {
		status = connectionStatus;
	}

	/**
	 * A string representing the state of the client this connection
	 * object represents
	 *
	 * @return A string representing the state of the client
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(clientId);
		sb.append("\n ");

		switch (status) {

			case CONNECTED:
				sb.append(context.getString(R.string.connectedto));
				break;
			case DISCONNECTED:
				sb.append(context.getString(R.string.disconnected));
				break;
			case NONE:
				sb.append(context.getString(R.string.no_status));
				break;
			case CONNECTING:
				sb.append(context.getString(R.string.connecting));
				break;
			case DISCONNECTING:
				sb.append(context.getString(R.string.disconnecting));
				break;
			case ERROR:
				sb.append(context.getString(R.string.connectionError));
		}
		sb.append(" ");
		sb.append(host);

		return sb.toString();
	}

	/**
	 * Compares two connection objects for equality
	 * this only takes account of the client handle
	 *
	 * @param o The object to compare to
	 *
	 * @return true if the client handles match
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Connection)) {
			return false;
		}

		Connection c = (Connection) o;

		return clientHandle.equals(c.clientHandle);

	}

	/**
	 * 获取clientid
	 *
	 * @return
	 */
	public String getClientId() {
		return this.clientId;
	}

	/**
	 * Gets the client which communicates with the android service.
	 *
	 * @return the client which communicates with the android service
	 */
	public MqttAndroidClient getClient() {
		return client;
	}

	/**
	 * Add the connectOptions used to connect the client to the server
	 *
	 * @param connectOptions the connectOptions used to connect to the server
	 */
	public void addConnectionOptions(MqttConnectOptions connectOptions) {
		conOpt = connectOptions;

	}

	/**
	 * Get the connectOptions used to connect this client to the server
	 *
	 * @return The connectOptions used to connect the client to the server
	 */
	public MqttConnectOptions getConnectionOptions() {
		return conOpt;
	}

	/**
	 * Determines if the connection is secured using SSL, returning a C style integer value
	 *
	 * @return 1 if SSL secured 0 if plain text
	 */
	public int isSSL() {
		return sslConnection ? 1 : 0;
	}


	/**
	 * 检查网络
	 */
	public boolean checkNetwork() {
		// 是否有网络连接
		if (NetworkUtil.checkNetwork(context) == NetworkUtil.NO_NETWORK) {
			return false;
		}
		return true;
	}

	/**
	 * 获取sub未成功的topic
	 *
	 * @return
	 */
	public Map<String, SubscribeBean> getSubTopics() {
		return this.subTopics;
	}

	/**
	 * 获取publish未成功的topic
	 *
	 * @return
	 */
	public Map<String, PublishBean> getPublicFailedTopic() {
		return this.publishFaildTopics;
	}

	/**
	 * 连接mqtt
	 *
	 * @param username
	 * @param password
	 */
	public void connectToServer(String username, String password) {
		String[] actionArgs = new String[1];
		actionArgs[0] = clientId;
		IMqttActionListener callback = new ActionListener(context, ActionListener.Action.CONNECT, this, actionArgs);
		connectToServer(username, password, callback, true);
	}

	public void connectToServer(String username, String password, IMqttActionListener actionListener, boolean cleanSession) {
		if (this.client == null) {
			return;
		}
		if (this.client.isConnected()) {
			return;
		}
		AppLog.i("mqtt username: " + username + "; password: "+ password);
		this.conOpt.setUserName(username);
		this.conOpt.setPassword(password.toCharArray());
		this.conOpt.setCleanSession(cleanSession);
		this.conOpt.setConnectionTimeout(30);
		this.conOpt.setKeepAliveInterval(60);
		status = ConnectionStatus.CONNECTING;
		mqttCallbackHandler = new MqttCallbackHandler(context, this);
		this.client.setCallback(mqttCallbackHandler);
		this.client.setTraceCallback(new MqttTraceCallback());
		try {
			this.client.connect(this.conOpt, null, actionListener);
		} catch (MqttException e) {
			AppLog.e(e.getMessage());
		}
	}

	/**
	 * 重新连接mqtt
	 */
	public void reconnection() {
		if (!checkNetwork()) {
			return;
		}
		if (this.client == null) {
			return;
		}
		if (isManualDisconnect()) {
			return;
		}
		if (status == ConnectionStatus.CONNECTED || status == ConnectionStatus.CONNECTING) {
			return;
		}
		String[] actionArgs = new String[1];
		actionArgs[0] = clientId;
		final ActionListener callback = new ActionListener(context,
				ActionListener.Action.RECONNECT, this, actionArgs);
		try {
			this.client.connect(this.conOpt, null, callback);
		} catch (MqttException e) {
			AppLog.e(e.getMessage());
		}
	}

	/**
	 * 重新连接mqtt
	 */
	public void reconnection(String user, String password) {
		reconnection(user, password, false);
	}

	/**
	 * 重新连接mqtt
	 */
	public void reconnection(String user, String password, boolean forceReconnect) {
		reconnection(user, password, forceReconnect, null);
	}


	/**
	 * 重新连接mqtt
	 */
	public void reconnection(String user, String password, boolean forceReconnect, IMqttActionListener callback) {
		if (!checkNetwork()) {
			return;
		}
		if (this.client == null) {
			return;
		}
		if (!forceReconnect && (status == ConnectionStatus.CONNECTED || status == ConnectionStatus.CONNECTING)) {
			return;
		}
		this.conOpt.setUserName(user);
		this.conOpt.setPassword(password.toCharArray());
		String[] actionArgs = new String[1];
		actionArgs[0] = clientId;
		final IMqttActionListener mCallback;
		if (callback == null) {
			mCallback = new ActionListener(context,
					ActionListener.Action.RECONNECT, this, actionArgs);
		} else {
			mCallback = callback;
		}
		try {
			this.client.connect(this.conOpt, null, mCallback);
		} catch (MqttException e) {
			AppLog.e(e.getMessage());
		}
	}


	/**
	 * publish topic
	 *
	 * @param topic
	 * @param payload
	 * @param qos
	 * @param retained
	 * @param publishListener
	 */
	public void publish(final String topic, byte[] payload, int qos, boolean retained, final PublishListener publishListener) {
		if (!checkNetwork()) {
			return;
		}
		if (this.client == null) {
			return;
		}
		if (status == ConnectionStatus.DISCONNECTED) {
			reconnection();
			return;
		}
		if (status != ConnectionStatus.CONNECTED) {
			return;
		}
		try {
			if (status == ConnectionStatus.CONNECTED) {
				final PublishBean publishBean = new PublishBean();
				publishBean.setTopic(topic);
				publishBean.setPayload(payload);
				publishBean.setQos(qos);
				publishBean.setRetained(retained);
				publishBean.setPublishListener(publishListener);
				this.client.publish(topic, payload, qos, retained, context, new IMqttActionListener() {
					@Override
					public void onSuccess(IMqttToken iMqttToken) {
						AppLog.i("success to publish a message: "+topic);
						if (publishListener != null) {
							publishListener.onSuccess(topic);
						}
					}

					@Override
					public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
						AppLog.i("Failed to publish a message");
						publishFaildTopics.put(publishBean.getTopic(), publishBean);
						if (publishListener != null) {
							publishListener.onFailed(topic, throwable.getMessage());
						}
					}
				});
			} else {
				AppLog.i("mqtt 未连接成功");
				publishListener.onFailed(topic, context.getString(R.string.no_status));
			}
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
			publishListener.onFailed(topic, e.getMessage());
			AppLog.e("Failed to publish a messged from the client with the handle " + clientHandle + ": " + e);
		} catch (MqttException e) {
			e.printStackTrace();
			publishListener.onFailed(topic, e.getMessage());
			AppLog.e("Failed to publish a messged from the client with the handle " + clientHandle + ": " + e);
		}
	}

	/**
	 * publish topic
	 *
	 * @param topic
	 * @param payload
	 * @param publishListener
	 */
	public void publish(final String topic, MqttMessage payload, final PublishListener publishListener) {
		if (!checkNetwork()) {
			return;
		}
		if (this.client == null) {
			return;
		}
		if (status == ConnectionStatus.DISCONNECTED) {
			reconnection();
			return;
		}
		if (status != ConnectionStatus.CONNECTED) {
			return;
		}
		try {
			if (status == ConnectionStatus.CONNECTED) {
				this.client.publish(topic, payload, context, new IMqttActionListener() {
					@Override
					public void onSuccess(IMqttToken iMqttToken) {
						AppLog.i("Success to publish a message: "+ topic);
						if (publishListener != null) {
							publishListener.onSuccess(topic);
						}

					}

					@Override
					public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
						AppLog.i("Failed to publish a messged");
						if (publishListener != null) {
							publishListener.onFailed(topic, throwable.toString());
						}
					}
				});
			} else {
				AppLog.i("mqtt 未连接成功");
				publishListener.onFailed(topic, context.getString(R.string.no_status));
			}
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
			publishListener.onFailed(topic, e.getMessage());
			AppLog.e("Failed to publish a messged from the client with the handle " + clientHandle + ": " + e);
		} catch (MqttException e) {
			e.printStackTrace();
			publishListener.onFailed(topic, e.getMessage());
			AppLog.e("Failed to publish a messged from the client with the handle " + clientHandle + ": " + e);
		}
	}

	/**
	 * subscribe topic
	 *
	 * @param topic
	 * @param qos
	 * @param receiveCallback
	 */
	public void subscribe(final String topic, int qos, final ReceiveListener receiveCallback, final SubscribeListener subscribeListener) {
		subscribe(topic, qos, receiveCallback, subscribeListener, false);
	}

	public void subscribe(final String topic, int qos, final ReceiveListener receiveCallback, final SubscribeListener subscribeListener, boolean forceSub) {
		if (!checkNetwork()) {
			return;
		}
		if (this.client == null) {
			return;
		}
		if (status == ConnectionStatus.DISCONNECTED) {
			reconnection();
			return;
		}
		if (status != ConnectionStatus.CONNECTED) {
			return;
		}
		if (!client.isConnected()) {
			return;
		}

		if (!subTopics.containsKey(topic)) {
			SubscribeBean subscribeBean = new SubscribeBean();
			subscribeBean.setTopic(topic);
			subscribeBean.setQos(qos);
			subscribeBean.setReceiveListner(receiveCallback);
			subscribeBean.setSuccessListener(subscribeListener);
			subTopics.put(topic, subscribeBean);
		}

		try {
			if (!subscribedTopics.keySet().contains(topic) || forceSub) {
				AppLog.i("subscribe topic: " + topic);
				this.client.subscribe(topic, qos, context, new IMqttActionListener() {
					@Override
					public void onSuccess(IMqttToken iMqttToken) {
						AppLog.i("Success to subscribe a message: "+ topic);
						subscribedTopics.put(topic, subTopics.get(topic));
						subTopics.remove(topic);
						if (subscribeListener != null) {
							subscribeListener.onSuccess(topic);
						}
					}

					@Override
					public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
						AppLog.i("Failed to subscribe a message: "+ topic);
						if (status == ConnectionStatus.DISCONNECTED) {
							reconnection();
							return;
						} else if (status == ConnectionStatus.CONNECTED) {
							subscribeAll();
						}
						if (subscribeListener != null) {
							subscribeListener.onFailed(topic, throwable.getMessage());
						}
					}
				});
				mqttCallbackHandler.registerReceiveListner(topic, receiveCallback);
			}
		} catch (MqttSecurityException e) {
			e.printStackTrace();
			subscribeListener.onFailed(topic, e.getMessage());
			AppLog.e("Failed to subscribe to" + topic + " the client with the handle " + clientHandle + ": " + e);
		} catch (MqttException e) {
			e.printStackTrace();
			subscribeListener.onFailed(topic, e.getMessage());
			AppLog.e("Failed to subscribe to" + topic + " the client with the handle " + clientHandle + ": " + e);
		}
	}

	/**
	 * topic是否已经subscribe过
	 *
	 * @param topic
	 *
	 * @return
	 */
	public boolean isSubscribed(String topic) {
		return subscribedTopics.keySet().contains(topic);
	}

	/**
	 * sub剩下的topic
	 */
	public void subscribeAll() {
		if (!checkNetwork()) {
			return;
		}
		if (!subTopics.isEmpty() & subTopics.size() > 0) {
			for (Map.Entry<String, SubscribeBean> topic : subTopics.entrySet()) {
				this.subscribe(topic.getValue().getTopic(), topic.getValue().getQos(), topic.getValue().getReceiveListner(), topic.getValue().getSuccessListener());
			}
		}
	}

	/**
	 * sub剩下的topic
	 */
	public void reSubscribeAll() {
		if (!checkNetwork()) {
			return;
		}
		if (!subscribedTopics.isEmpty() & subscribedTopics.size() > 0) {
			for (final Map.Entry<String, SubscribeBean> topic : subscribedTopics.entrySet()) {
				if (topic != null && topic.getValue() != null) {
					final String mTopic = topic.getValue().getTopic();
					int mQos = topic.getValue().getQos();
					ReceiveListener mReceiveListener = topic.getValue().getReceiveListner();
					final SubscribeListener mSuccessListener = topic.getValue().getSuccessListener();
					try {
						this.client.subscribe(mTopic, mQos, context, new IMqttActionListener() {
							@Override
							public void onSuccess(IMqttToken iMqttToken) {
								AppLog.i("Success to subscribe a message");
								if (mSuccessListener != null) {
									mSuccessListener.onSuccess(mTopic);
								}
							}

							@Override
							public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
								AppLog.i("Failed to subscribe a message");
								if (status == ConnectionStatus.DISCONNECTED) {
									reconnection();
									return;
								} else if (status == ConnectionStatus.CONNECTED) {
									subscribeAll();
								}
								if (mSuccessListener != null) {
									mSuccessListener.onFailed(mTopic, throwable.getMessage());
								}
							}
						});
					} catch (MqttException e) {
						e.printStackTrace();
					}
					mqttCallbackHandler.registerReceiveListner(mTopic, mReceiveListener);
				}
			}
		}
	}

	/**
	 * unsubscribe
	 *
	 * @param topic
	 */
	public void unsubscribe(final String topic) {
		unsubscribe(topic, null);
	}

	/**
	 * unsubscribe
	 *
	 * @param topic
	 * @param callback
	 */
	public void unsubscribe(final String topic, final UnSubscribeListener callback) {
		if (!checkNetwork()) {
			return;
		}
		if (this.client == null) {
			return;
		}
		if (status == ConnectionStatus.DISCONNECTED) {
			reconnection();
			return;
		}
		if (status != ConnectionStatus.CONNECTED) {
			return;
		}
		try {
			if (subscribedTopics.keySet().contains(topic)) {
				this.client.unsubscribe(topic, context, new IMqttActionListener() {
					@Override
					public void onSuccess(IMqttToken iMqttToken) {
						AppLog.i("unsubscribe success..." + topic);
						subscribedTopics.remove(topic);
						if (callback != null) {
							callback.onSuccess(topic);
						}
					}

					@Override
					public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
						AppLog.i("unsubscribe failed..." + topic);
						if (callback != null) {
							callback.onFailed(topic, throwable.getMessage());
						}
					}
				});
			}
		} catch (MqttException e) {
			e.printStackTrace();
			AppLog.e("Failed to unsubscribe a messged from the client with the handle " + clientHandle + ": " + e);
		}
		mqttCallbackHandler.unregisterReceiveListner(topic);
	}


	/**
	 * unsubsribe all topics
	 */
	public void unSubscribeAll(final UnSubscribeListener unSubscribeListener) {
		if (!checkNetwork()) {
			return;
		}
		if (this.client == null) {
			return;
		}
		if (status == ConnectionStatus.DISCONNECTED) {
			reconnection();
			return;
		}
		if (status != ConnectionStatus.CONNECTED) {
			return;
		}
		for (final String topic : subscribedTopics.keySet()) {
			try {
				this.client.unsubscribe(topic, context, new IMqttActionListener() {
					@Override
					public void onSuccess(IMqttToken iMqttToken) {
						AppLog.i("Success to unsubscribe a topice : " + topic);
						unSubscribeListener.onSuccess(topic);
						subscribedTopics.remove(topic);
						mqttCallbackHandler.unregisterReceiveListner(topic);
					}

					@Override
					public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
						AppLog.i("Failed to unsubscribe a topice : " + topic);
						unSubscribeListener.onFailed(topic, throwable.getMessage());
					}
				});
			} catch (MqttException e) {
				e.printStackTrace();
				AppLog.e("Failed to unsubscribe a topice : " + topic);
			}
		}
	}


	/**
	 * 断开mqtt连接
	 */
	public void disconnect() {
		setManualDisconnect(true);
		if (!checkNetwork()) {
			return;
		}
		if (this.client == null) {
			return;
		}
		if (status != ConnectionStatus.CONNECTED) {
			return;
		}
		try {
			this.client.disconnect(context, new ActionListener(context, ActionListener.Action.DISCONNECT, this));
			changeConnectionStatus(ConnectionStatus.DISCONNECTING);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
