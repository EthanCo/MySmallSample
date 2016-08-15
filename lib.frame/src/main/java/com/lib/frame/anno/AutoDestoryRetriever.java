package com.lib.frame.anno;

import android.util.Log;

import com.lib.frame.viewmodel.BaseViewModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description AutoDestory 搜寻者
 * Created by EthanCo on 2016/8/15.
 */
public class AutoDestoryRetriever implements AnnoRetriever<BaseViewModel> {
    private static final String TAG = "Z-AutoDestoryRetriever";
    private List<Field> autoDestoryFiled = new ArrayList<>();
    private BaseViewModel viewModel;

    public void seek(BaseViewModel viewModel) {
        this.viewModel = viewModel;
        Class<? extends BaseViewModel> clazz = viewModel.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            AutoDestory autoDestoryAnno = field.getAnnotation(AutoDestory.class);
            if (autoDestoryAnno != null) {
                autoDestoryFiled.add(field);
            }
        }
    }

    public void clear() {
        Log.i(TAG, "clean autoDestoryFiled.size: " + autoDestoryFiled.size());
        for (Field field : autoDestoryFiled) {
            if (field != null) {
                try {
                    Method method = field.getType().getMethod("unsubscribe");
                    Object subscription = field.get(viewModel);
                    method.invoke(subscription);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
