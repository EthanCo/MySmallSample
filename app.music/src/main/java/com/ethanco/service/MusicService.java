package com.ethanco.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lib.utils.L;
import com.lib.utils.T;

/**
 * Created by EthanCo on 2016/8/10.
 */
public class MusicService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        T.show("MusicService onCreate");
        L.i("MusicService onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        T.show("MusicService onDestroy");
        L.i("MusicService destroy");
    }
}
