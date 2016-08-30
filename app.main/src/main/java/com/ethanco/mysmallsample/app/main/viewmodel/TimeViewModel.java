package com.ethanco.mysmallsample.app.main.viewmodel;


import android.support.annotation.NonNull;

import com.ethanco.mysmallsample.app.main.model.TimeModel;
import com.ethanco.mysmallsample.app.main.model.abs.ITimeModel;
import com.ethanco.mysmallsample.app.main.view.abs.ITimeView;
import com.ethanco.mysmallsample.lib.frame.viewmodel.BaseViewModel;
import com.ethanco.mysmallsample.lib.network.NetFacade;
import com.ethanco.mysmallsample.lib.network.bean.request.CmdRequest;
import com.ethanco.mysmallsample.lib.network.bean.response.TimeResponse;
import com.ethanco.mysmallsample.lib.network.model.RequestModel;
import com.ethanco.mysmallsample.lib.network.sbscribe.RxHelper;
import com.ethanco.mysmallsample.lib.network.sbscribe.RxSubscriber;
import com.ethanco.zanno.AutoDestory;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by EthanCo on 2016/8/11.
 */
public class TimeViewModel extends BaseViewModel<ITimeView> {

    @AutoDestory
    Subscription subscription; //有该注解的Subscription，在detachView的时候将自动unsubscribe

    private ITimeModel timeModel;

    public TimeViewModel() {
        timeModel = new TimeModel();
    }

    public void getServiceTime() {
        getView().showProgressDialog();
        subscription = Observable.just(null)
                .map(new Func1<Object, CmdRequest>() {
                    @Override
                    public CmdRequest call(Object o) {
                        return RequestModel.getInstance().generationGetServiceTimeCmd(); //生成Cmd最好也在异步中进行
                    }
                })
                .flatMap(new Func1<CmdRequest, Observable<TimeResponse>>() {
                    @Override
                    public Observable<TimeResponse> call(CmdRequest cmd) {
                        return getServiceTimeFromNet(cmd); //访问网络获取数据 并转换为Observable<TimeResponse>
                    }
                })
                ////检查返回结果是否成功，并将subscribe设置为主线程执行，之前的代码在异步线程执行，在网络加载失败时进行重新尝试连接
                .compose(RxHelper.<TimeResponse.Entity>handleResult())
                .doOnNext(new Action1<TimeResponse.Entity>() {
                    @Override
                    public void call(TimeResponse.Entity entity) {
                        timeModel.saveTime(entity.getTime()); //存储time到本地
                    }
                })
                .subscribe(new RxSubscriber(new Action1<TimeResponse.Entity>() {
                    @Override
                    public void call(TimeResponse.Entity entity) {
                        getView().getServiceTimeSuccess(entity.getTime()); //获取成功，调用view层方法
                    }
                }, getView()));
                /*
                传入getView() (返回的类为ProcessDialogView子类)，
                自动调用 dismissProcessDialog，出现错误时自动调用有@LoadFailed注解的方法

                此处为当调用onError和onCompleted时，自动调用dissmissProcessDialog，
                当onError时自动调用有@LoadFailed注解的方法

                参数3为flag标记，用于识别
                当该接口及其父接口中，只有一个@LoadFailed注解，则不需要传入flag
                当该接口及其父接口中，有多个@LoadFailed注解时，需传入flag以区分不同的@LoadFailed，
                否则多个有@loadFailed的方法都会被调用
                */
    }

    @NonNull
    private Observable<TimeResponse> getServiceTimeFromNet(CmdRequest cmd) {
        return NetFacade.getInstance()
                .provideDefualtService()
                .getServerTime(cmd)
                .delay(2, TimeUnit.SECONDS);
    }
}
