package com.ethanco.mysmallsample.lib.utils;

import android.app.Application;

import com.ethanco.mysmallsample.lib.utils.config.AppConfig;

/**
 * Created by EthanCo on 2016/8/19.
 */
public class UtilsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        T.init(this);
        AppConfig.init(this);
    }
}
