package com.ethanco.app.main.view.abs;

import com.lib.frame.view.ProcessDialogView;

/**
 * Created by EthanCo on 2016/8/11.
 */
public interface MainView extends ProcessDialogView {

    void getServiceTimeSuccess(String time);

    void getServiceTimeFailed(String error);
}
