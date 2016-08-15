package com.lib.network.sbscribe.matcher;

import com.lib.network.sbscribe.StrategyMacther;
import com.lib.network.sbscribe.base.BaseSubscriber;

import java.lang.reflect.Method;

import rx.Subscriber;

/**
 * Created by EthanCo on 2016/8/15.
 */
public class ProcessDialogMatcher<T> extends StrategyMacther<T> {

    public ProcessDialogMatcher(MatchListener<T> matchListener) {
        super(matchListener);
    }

    @Override
    protected Subscriber<T> generateSubscriber(final Object o, Class cls) {
        return new BaseSubscriber<T>() {

            public void dismissProgressDialog(Object o) {
                if (o == null) return;

                Method method = null;
                try {
                    method = o.getClass().getMethod("dismissProgressDialog");
                    method.invoke(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                dismissProgressDialog(o);
            }

            @Override
            public void onNext(T t) {
                dismissProgressDialog(o);
            }
        };
    }

    public boolean matching(Object o, String className) {
        return "com.lib.frame.view.ProcessDialogView".equals(className);
    }
}
