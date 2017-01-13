package com.molmc.core.utils;


import com.orhanobut.logger.Logger;

/**
 * Created by mingjun on 16/7/17.
 */
public class AppLog {

    private static final String TAG = "CypressApp";
    private static boolean DEBUG = false;


    /**
     * initialize the logger.
     */
    public static void init(boolean debug) {
        DEBUG = debug;
        Logger.init(TAG);
    }

    /**
     * log.i
     * @param msg
     */
    public static void i(String msg) {
        if (DEBUG) {
            Logger.i(msg);
        }
    }

    /**
     * log.d
     * @param msg
     */
    public static void d(String msg) {
        if (DEBUG) {
            Logger.d(msg);
        }
    }

    /**
     * log.w
     * @param msg
     */
    public static void w(String msg) {
        if (DEBUG) {
            Logger.w(msg);
        }
    }

    /**
     * log.e
     * @param msg
     */
    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void e(Throwable e) {
        Logger.e(e, "");
    }
}
