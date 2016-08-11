package com.lib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast
 */
public class T {

    private T() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    public static void show(Context context, int resId) {
        show(context, resId, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        Toast.makeText(context, resId, duration).show();
    }

    public static void show(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }
}
