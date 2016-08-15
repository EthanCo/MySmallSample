package com.ethanco.app.main.view.abs;

import com.lib.frame.view.ProcessDialogView;
import com.lib.network.sbscribe.anno.LoadFailed;

/**
 * Created by EthanCo on 2016/8/11.
 */
public interface ITimeView extends ProcessDialogView {

    void getServiceTimeSuccess(String time);

    @LoadFailed
    void getServiceTimeFailed(String error);
}
