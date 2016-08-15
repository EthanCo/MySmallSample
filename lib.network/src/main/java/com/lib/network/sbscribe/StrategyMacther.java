package com.lib.network.sbscribe;

import rx.Subscriber;

/**
 * @Description 策略匹配器
 * Created by EthanCo on 2016/8/15.
 */
public abstract class StrategyMacther<T> {
    public boolean handle(final Object o, Class cls) {
        String className = cls.getName();
        if (!matching(o, className)) return false;

        Subscriber<T> subscriber = generateSubscriber(o, cls);
        if (subscriber != null) {
            if (matchListener != null) {
                matchListener.matchSuccess(subscriber);
            }
            return true;
        } else {
            return false;
        }
    }

    protected abstract Subscriber<T> generateSubscriber(final Object o, Class cls);

    public abstract boolean matching(Object o, String className);

    protected MatchListener<T> matchListener;

    public StrategyMacther(MatchListener<T> matchListener) {
        this.matchListener = matchListener;
    }

    public interface MatchListener<T> {
        void matchSuccess(Subscriber<T> subscriber);
    }
}
