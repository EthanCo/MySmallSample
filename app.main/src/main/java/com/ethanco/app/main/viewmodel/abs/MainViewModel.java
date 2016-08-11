package com.ethanco.app.main.viewmodel.abs;

import android.support.annotation.NonNull;

import com.ethanco.app.main.view.abs.MainView;
import com.ethanco.lib.rxjava.sbscribe.RxSubscriber;
import com.lib.frame.model.RequestModel;
import com.lib.frame.viewmodel.BaseViewModel;
import com.lib.network.NetFacade;
import com.lib.network.bean.request.CmdRequest;
import com.lib.network.bean.response.TimeResponse;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by EthanCo on 2016/8/11.
 */
public class MainViewModel extends BaseViewModel<MainView> {

    public void getServiceTime() {
        getView().showProgressDialog();
        Observable.just(null)
                .map(new Func1<Object, CmdRequest>() {
                    @Override
                    public CmdRequest call(Object o) {
                        return RequestModel.getInstance().generationGetServiceTimeCmd(); //生成Cmd最好也在异步中进行
                    }
                })
                .flatMap(new Func1<CmdRequest, Observable<TimeResponse>>() {
                    @Override
                    public Observable<TimeResponse> call(CmdRequest cmd) {
                        return getServiceTimeFromNet(cmd);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //表示subscribe()在主线程中执行
                .subscribeOn(Schedulers.io()) //表示subscribe()之前的代码在异步线程(IO)中执行
                .subscribe(new RxSubscriber<TimeResponse>(new Action1<TimeResponse>() {
                    @Override
                    public void call(TimeResponse timeResponse) {
                        //一般情况下，操作在此处完成即可
                    }
                }));
    }

//    new Action1<TimeResponse>() {
//        @Override
//        public void call(TimeResponse timeResponse) {
//            String time = timeResponse.getData().getTime();
//            L.i("time:" + time);
//            getView().getServiceTimeSuccess(time);
//        }
//    }, new Action1<Throwable>() {
//        @Override
//        public void call(Throwable throwable) {
//            L.i("throwable:" + throwable.getMessage());
//            getView().dismissProgressDialog();
//            getView().getServiceTimeFailed(throwable.getLocalizedMessage());
//        }
//    }, new Action0() {
//        @Override
//        public void call() {
//            L.i("complete");
//            getView().dismissProgressDialog();
//        }
//    }

    @NonNull
    private Observable<TimeResponse> getServiceTimeFromNet(CmdRequest cmd) {
        return NetFacade.getInstance()
                .provideDefualtService()
                .getServerTime(cmd).delay(2, TimeUnit.SECONDS);
    }
}
