package com.lib.network.sbscribe;

import android.util.Log;

import com.lib.network.sbscribe.matcher.ActionMatcher;
import com.lib.network.sbscribe.matcher.LoadFailedMatcher;
import com.lib.network.sbscribe.matcher.ProcessDialogMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 策略生成者
 * Created by EthanCo on 2016/8/12.
 */
class StrategyMaker<T> {
    private static final String TAG = "Z-StrategyMarker";
    private List<StrategyMacther> matchers = new ArrayList<>();

    StrategyMaker(StrategyMacther.MatchListener<T> matchListener) {
        //init matchers
        matchers.add(new ProcessDialogMatcher(matchListener));
        matchers.add(new LoadFailedMatcher(matchListener));
        matchers.add(new ActionMatcher(matchListener));
    }

//    static class SingleTon {
//        private static StrategyHandler sInstance = new StrategyHandler();
//    }
//
//    public static StrategyHandler getInstance() {
//        return SingleTon.sInstance;
//    }

    /**
     *
     * @param o
     * @param iterate 是否轮询其父接口获取更多的 StrategyMacther
     */
    void recordAction(Object o, boolean iterate) {
        if (o == null) return;
        if (iterate) {
            iterateClasses(o, o.getClass());
        } else {
            handleIfMatching(o, o.getClass());
        }
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

    private void handleIfMatching(Object o, Class cls) {
        String className = cls.getName();
        Log.i(TAG, "ClassName:" + className);
        for (StrategyMacther matcher : matchers) {
            matcher.handle(o, cls);
        }
    }
}
