package com.lib.network.sbscribe;

import android.util.Log;

import com.lib.network.sbscribe.anno.LoadFailed;
import com.lib.network.sbscribe.base.BaseSubscriber;
import com.lib.network.sbscribe.matcher.ProcessDialogMatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * @Description 策略生成者
 * Created by EthanCo on 2016/8/12.
 */
class StrategyMaker<T> {

    private static final String TAG = "Z-StrategyMarker";
    private final StrategyMacther.MatchListener<T> m;
    private Object reflectObj; //进行反射的Obj

    private List<StrategyMacther> matchers = new ArrayList<>();

    StrategyMaker(StrategyMacther.MatchListener<T> matchListener) {
        this.m = matchListener;
        //init matchers
        matchers.add(new ProcessDialogMatcher(matchListener));
    }

//    static class SingleTon {
//        private static StrategyHandler sInstance = new StrategyHandler();
//    }
//
//    public static StrategyHandler getInstance() {
//        return SingleTon.sInstance;
//    }

    public void handle(Object reflectObj, String className) {
        for (StrategyMacther matcher : matchers) {
            matcher.handle(reflectObj, className);
        }
    }

    void recordAction(Object o) {
        if (o == null) return;
        this.reflectObj = o;
        iterateClasses(reflectObj, o.getClass());
    }

    private void iterateClasses(Object o, Class cls) {
        handleIfMatching(o, cls);

        Class superClass = cls.getSuperclass();
        if (superClass != null) handleIfMatching(o, superClass);

        Class[] interfaceClasses = cls.getInterfaces();
        if (interfaceClasses == null) return;

        for (Class interfaceCls : interfaceClasses) {
            iterateClasses(o, interfaceCls);
        }
    }

    void handleIfMatching(Object o, Class cls) {
        String className = cls.getName();
        Log.i(TAG, "ClassName:" + className);
        for (StrategyMacther matcher : matchers) {
            matcher.handle(o, cls.getName());
        }
//        if ("".equals(className)) {
//
//        } else {
//            //Log.v(TAG, "未处理:" + className);
//        }

        final List<Method> loadFailedList = new ArrayList<>();
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            LoadFailed loadFailedAnno = method.getAnnotation(LoadFailed.class);
            if (loadFailedAnno != null) {
                loadFailedList.add(method);
            }
        }
        if (loadFailedList.size() <= 0) return;

        Subscriber<T> loadFailedSubsriber = new BaseSubscriber<T>() {

            @Override
            public void onError(Throwable e) {
                if (null != reflectObj) {
                    if (loadFailedList.size() == 0) {
                        throw new IllegalStateException("not found @LoadFailed Annotation,this is a must");
                    }
                    for (Method method : loadFailedList) {
                        try {
                            method.invoke(reflectObj, e.getLocalizedMessage());
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        } catch (InvocationTargetException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        };
        m.matchSuccess(loadFailedSubsriber);
    }
}
