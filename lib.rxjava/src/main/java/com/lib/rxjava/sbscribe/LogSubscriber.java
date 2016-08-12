package com.lib.rxjava.sbscribe;

import android.util.Log;

/**
 * Created by EthanCo on 2016/1/3.
 */
class LogSubscriber<T> extends BaseSubscriber<T> {
    @Override
    public void onCompleted() {
        Log.i(TAG, "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Log.i(TAG, "onError: " + e.getMessage());
    }

    @Override
    public void onNext(T t) {
        if (isDebug) {
            Log.i(TAG, "onNext");
        }
    }
}
