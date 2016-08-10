package com.lib.network;

import android.app.Application;

/**
 * Created by EthanCo on 2016/8/8.
 */
public class NetWorkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitFactory.init(this);
        //RetrofitFactory.register(new ErrorObserver());
    }
}
