package com.molmc.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * features: check network
 * Author：  hhe on 16-7-22 09:58
 * Email：   hhe@molmc.com
 */
public class NetworkUtil {
    public final static int NO_NETWORK = 0;
    public final static int NETWORK_WIFI = 1;
    public final static int NETWORK_MOBILE = 2;

    public static int checkNetwork(Context context) {
        if (context == null) {
            return NETWORK_WIFI;
        }
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi != null && wifi.isConnected()) {
            return NETWORK_WIFI;
        }
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobile != null && mobile.isConnected()) {
            return NETWORK_MOBILE;
        }
        return NO_NETWORK;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }
}

