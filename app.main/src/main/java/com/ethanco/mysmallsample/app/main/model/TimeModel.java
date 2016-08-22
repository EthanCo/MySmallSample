package com.ethanco.mysmallsample.app.main.model;

import com.ethanco.mysmallsample.app.main.model.abs.ITimeModel;
import com.ethanco.mysmallsample.lib.utils.config.AppConfig;

/**
 * Created by EthanCo on 2016/8/17.
 */
public class TimeModel implements ITimeModel {
    @Override
    public void saveTime(String time) {
        AppConfig.getInstance().setTime(time);
    }
}
