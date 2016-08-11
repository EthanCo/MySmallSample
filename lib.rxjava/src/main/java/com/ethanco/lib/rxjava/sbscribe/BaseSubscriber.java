package com.ethanco.lib.rxjava.sbscribe;

import rx.Subscriber;

/**
 * Created by EthanCo on 2016/1/3.
 */
abstract class BaseSubscriber<T> extends Subscriber<T> {
    protected static final String TAG = "Z-Subscriber";
    protected static boolean isDebug = true;

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setIsDebug(boolean isDebug) {
        BaseSubscriber.isDebug = isDebug;
    }
}
