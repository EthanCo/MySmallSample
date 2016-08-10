package com.lib.utils;

import android.app.Application;

/**
 * Created by EthanCo on 2016/8/7.
 */
public class UtilsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        T.init(this);
    }
}
