package com.ethanco.lib.rxjava.sbscribe;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * 一般情况下使用该Subscriber即可
 * 尽量使用该类及其子类，方面日后进行统一处理
 * Created by EthanCo on 2016/1/3.
 */
public class RxSubscriber<T> extends LogSubscriber<T> {

    private Action1<? super T> onNext;

    private Action0 onCompleted;

    public RxSubscriber() {
    }

    public RxSubscriber(final Action1<? super T> onNext) {
        if (onNext == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }

        this.onNext = onNext;
    }

    public RxSubscriber(Action0 onCompleted) {
        if (onCompleted == null) {
            throw new IllegalArgumentException("onCompleted can not be null");
        }

        this.onCompleted = onCompleted;
    }

    public void handler() {
        //TODO 反射
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        if (null != onCompleted) {
            onCompleted.call();
        }
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
        if (null != onNext) {
            onNext.call(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
    }

    //============================= Z-使用示例 ==============================/

    /*
    //不使用回调，只需打印log的情况
    public void sample1() {
        Observable.just("123")
                .subscribe(new RxSubscriber<String>());
    }

    //只使用 onNext 的情况
    public void sample2() {
        Observable.just("123")
                .subscribe(new RxSubscriber<String>(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                }));
    }

    //只使用 onCompleted 的情况
    public void sample3() {
        Observable.just("123")
                .subscribe(new RxSubscriber<String>(new Action0() {
                    @Override
                    public void call() {

                    }
                }));
    }

    //使用多个回调的情况
    public void sample4() {
        Observable.just("123")
                .subscribe(new RxSubscriber<String>(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                }) {
                    //已重载的形式

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }*/
}
