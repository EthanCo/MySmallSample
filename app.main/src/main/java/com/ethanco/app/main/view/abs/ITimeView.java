package com.ethanco.app.main.view.abs;

import com.lib.frame.view.ProcessDialogView;
import com.lib.network.sbscribe.anno.LoadFailed;

/**
 * Created by EthanCo on 2016/8/11.
 */
public interface ITimeView extends ProcessDialogView {

    int FLAG_GET_TIME_FAILED = 1001;

    void getServiceTimeSuccess(String time);

    //当该接口及其父接口中，只有一个@LoadFailed注解，则不需要传入flag
    //当该接口及其父接口中，有多个@LoadFailed注解时，需传入flag以区分不同的@LoadFailed，
    //否则多个有@loadFailed的方法都会被调用
    @LoadFailed
    //@LoadFailed(FLAG_GET_TIME_FAILED)
    void getServiceTimeFailed(String error);
}
