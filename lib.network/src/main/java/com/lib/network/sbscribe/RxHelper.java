package com.lib.network.sbscribe;

import com.lib.network.AppCommandType;
import com.lib.network.bean.response.BaseBeanResponse;
import com.lib.network.bean.response.BaseDataBean;

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
    public static <T extends BaseDataBean> Observable.Transformer<BaseBeanResponse<T>, T> handleResult() {
        return new Observable.Transformer<BaseBeanResponse<T>, T>() {

            @Override
            public Observable<T> call(Observable<BaseBeanResponse<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseBeanResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseBeanResponse<T> result) {
                        if (AppCommandType.SUCCESS.equals(result.getResult())) {
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
