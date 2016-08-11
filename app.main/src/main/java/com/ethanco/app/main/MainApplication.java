package com.ethanco.app.main;

import android.app.Application;

import com.lib.utils.T;

/**
 * Created by EthanCo on 2016/8/11.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        T.init(this);
    }
}
