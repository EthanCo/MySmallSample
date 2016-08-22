package com.ethanco.mysmallsample.lib.utils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Toast
 */
public class T {

    private T() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    private static Application context;

    public static boolean isAvailable() {
        return context == null;
    }

    public static void init(Application application) {
        if (context == null) {
            context = application;
        }
    }

    public static void show(int resId) {
        show(context, resId, Toast.LENGTH_SHORT);
    }

    public static void show(CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        Toast.makeText(context, resId, duration).show();
    }

    public static void show(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }
}