package com.lib.network.sbscribe.matcher;

import com.lib.network.sbscribe.StrategyMacther;
import com.lib.network.sbscribe.anno.LoadFailed;
import com.lib.network.sbscribe.base.BaseSubscriber;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * @Description 加载失败Matcher
 * Created by EthanCo on 2016/8/15.
 */
public class LoadFailedMatcher<T> extends StrategyMacther<T> {

    public LoadFailedMatcher(MatchListener<T> matchListener) {
        super(matchListener);
    }

    @Override
    protected Subscriber<T> generateSubscriber(final Object o, Class cls) {
        final List<Method> loadFailedList = new ArrayList<>();
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            LoadFailed loadFailedAnno = method.getAnnotation(LoadFailed.class);
            if (loadFailedAnno != null) {
                loadFailedList.add(method);
            }
        }
        if (loadFailedList.size() <= 0) return null;

        return new BaseSubscriber<T>() {

            @Override
            public void onError(Throwable e) {
                if (null != o) {
                    if (loadFailedList.size() == 0) {
                        throw new IllegalStateException("not found @LoadFailed Annotation,this is a must");
                    }
                    for (Method method : loadFailedList) {
                        try {
                            method.invoke(o, e.getLocalizedMessage());
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        } catch (InvocationTargetException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    @Override
    public boolean matching(Object o, String className) {
        return true;
    }
}
