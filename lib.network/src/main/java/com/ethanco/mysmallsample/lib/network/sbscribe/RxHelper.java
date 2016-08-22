package com.ethanco.mysmallsample.lib.network.sbscribe;

import android.text.TextUtils;

import com.ethanco.mysmallsample.lib.network.AppCommandType;
import com.ethanco.mysmallsample.lib.network.bean.response.BaseResponse;
import com.ethanco.mysmallsample.lib.network.bean.response.BaseDataBean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description RxJava 帮助类
 * Created by EthanCo on 2016/8/12.
 */
public class RxHelper {
    /**
     * 处理返回结果，如果错误则会执行onError
     * 并且进行线程的切换 subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
     *
     * @param <T>
     * @return
     */
    public static <T extends BaseDataBean> Observable.Transformer<BaseResponse<T>, T> handleResult() {
        return new Observable.Transformer<BaseResponse<T>, T>() {

            @Override
            public Observable<T> call(Observable<BaseResponse<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseResponse<T> result) {
                        if (AppCommandType.SUCCESS.equals(result.getResult()) || TextUtils.isEmpty(result.getResult())) {
                            return createData(result.getData());
                        } else {
                            return Observable.error(new RuntimeException(result.getData().getMessage()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
