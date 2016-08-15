package com.lib.network.sbscribe;

import rx.Subscriber;

/**
 * @Description 匹配器
 * Created by EthanCo on 2016/8/15.
 */
public abstract class StrategyMacther<T> {
    public abstract boolean handle(final Object reflectObj, String className);

    public abstract boolean matching(Object o, String className);

    protected MatchListener<T> matchListener;

    public StrategyMacther(MatchListener<T> matchListener) {
        this.matchListener = matchListener;
    }

    public interface MatchListener<T> {
        void matchSuccess(Subscriber<T> subscriber);
    }
}
