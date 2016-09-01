package com.molmc.core.utils;

import com.molmc.core.BuildConfig;

import timber.log.Timber;

/**
 * Created by mingjun on 16/7/17.
 */
public class AppLog {

    private static final String TAG = "CypressApp";

    /**
     * initialize the logger.
     */
    public static void init() {
        Timber.plant(new Timber.DebugTree());
    }

    /**
     * log.i
     * @param msg
     */
    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            Timber.i(msg);
        }
    }

	/**
     * log.i
     * @param msg
     * @param args
     */
    public static void i(String msg, Object... args){
        if (BuildConfig.DEBUG) {
            Timber.i(msg, args);
        }
    }

    /**
     * log.d
     * @param msg
     */
    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Timber.d(msg);
        }
    }

    /**
     * log.w
     * @param msg
     */
    public static void w(String msg) {
        if (BuildConfig.DEBUG) {
            Timber.w(msg);
        }
    }

    /**
     * log.e
     * @param msg
     */
    public static void e(String msg) {
        Timber.e(msg);
    }

    /**
     * log.e
     * @param msg
     */
    public static void e(String msg, Object... args) {
        Timber.e(msg, args);
    }

    public static void e(Throwable e) {
        Timber.e(e, "");
    }

    public static void e(Throwable e, Object... args) {
        Timber.e(e, "", args);
    }
}
