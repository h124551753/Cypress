/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 * <p/>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * <p/>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */
package com.molmc.core.mqtt;


import com.molmc.core.utils.AppLog;

import org.eclipse.paho.android.service.MqttTraceHandler;

public class MqttTraceCallback implements MqttTraceHandler {

	public void traceDebug(String arg0, String arg1) {
		AppLog.i(formateString(arg0, arg1));
	}

	public void traceError(String arg0, String arg1) {
		AppLog.e(formateString(arg0, arg1));
	}

	public void traceException(String arg0, String arg1,
	                           Exception arg2) {
		AppLog.e(arg2, formateString(arg0, arg1));
	}

	private String formateString(String arg0, String arg1) {
		return String.format("%s: %s", arg0, arg1);
	}
}
