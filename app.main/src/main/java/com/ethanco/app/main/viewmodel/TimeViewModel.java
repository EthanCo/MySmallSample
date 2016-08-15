package com.ethanco.app.main.viewmodel;


import android.support.annotation.NonNull;

import com.ethanco.app.main.view.abs.ITimeView;
import com.lib.frame.viewmodel.BaseViewModel;
import com.lib.network.NetFacade;
import com.lib.network.bean.request.CmdRequest;
import com.lib.network.bean.response.TimeResponse;
import com.lib.network.model.RequestModel;
import com.lib.network.sbscribe.RxHelper;
import com.lib.network.sbscribe.RxSubscriber;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by EthanCo on 2016/8/11.
 */
public class TimeViewModel extends BaseViewModel<ITimeView> {

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
                .compose(RxHelper.<TimeResponse.Entity>handleResult()) //检查返回结果是否成功，并切换线程
                .subscribe(new RxSubscriber(new Action1<TimeResponse.Entity>() {
                    @Override
                    public void call(TimeResponse.Entity entity) {
                        getView().getServiceTimeSuccess(entity.getTime());
                    }
                }, getView()));
    }

    @NonNull
    private Observable<TimeResponse> getServiceTimeFromNet(CmdRequest cmd) {
        return NetFacade.getInstance()
                .provideDefualtService()
                .getServerTime(cmd).delay(2, TimeUnit.SECONDS);
    }
}