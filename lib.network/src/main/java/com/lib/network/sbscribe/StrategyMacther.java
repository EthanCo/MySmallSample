package com.lib.network.sbscribe;

import rx.Subscriber;

/**
 * @Description 匹配器
 * Created by EthanCo on 2016/8/15.
 */
public abstract class StrategyMacther<T> {
    public boolean handle(final Object reflectObj, Class cls) {
        String className = cls.getName();
        if (!matching(reflectObj, className)) return false;

        Subscriber<T> subscriber = generateSubscriber(reflectObj, cls);
        if (subscriber != null) {
            if (matchListener != null) {
                matchListener.matchSuccess(subscriber);
            }
            return true;
        } else {
            return false;
        }
    }

    protected abstract Subscriber<T> generateSubscriber(final Object reflectObj, Class cls);

    protected abstract boolean matching(Object o, String className);

    protected MatchListener<T> matchListener;

    public StrategyMacther(MatchListener<T> matchListener) {
        this.matchListener = matchListener;
    }

    public interface MatchListener<T> {
        void matchSuccess(Subscriber<T> subscriber);
    }
}
