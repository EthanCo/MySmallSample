package com.ethanco.lib.utils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by EthanCo on 2016/8/7.
 */
public class T {
    private static Application context;

    public static void init(Application _context) {
        context = _context;
    }

    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
