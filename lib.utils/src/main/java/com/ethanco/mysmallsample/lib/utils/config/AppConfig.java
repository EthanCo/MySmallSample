package com.ethanco.mysmallsample.lib.utils.config;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Description App 配置存储
 * Created by EthanCo on 2016/6/14.
 */
public class AppConfig {

    private static final String SP_APPCONFIG = "spAppConfig";
    private static final String SP_TIME = "spTime";

    private AppConfig() {
    }

    private static class SingleTonHolder {
        private static AppConfig sInstance = new AppConfig();
    }

    public static AppConfig getInstance() {
        return SingleTonHolder.sInstance;
    }

    private static Application application;

    public static void init(Application _application) {
        if (application == null) {
            application = _application;
        }
    }

    private SharedPreferences getPreferences() {
        if (application == null) {
            throw new IllegalStateException("context is null,please init() first");
        }
        return application.getSharedPreferences(SP_APPCONFIG, Context.MODE_PRIVATE);
    }

    public void setTime(String time) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(SP_TIME, time);
        editor.commit();
    }

    public String getTime() {
        return getPreferences().getString(SP_TIME, null);
    }
}
