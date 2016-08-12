package com.lib.network.model;

import com.lib.network.AppCommandType;
import com.lib.network.bean.request.CmdRequest;

/**
 * @Description 生成 请求 的 Model
 * Created by EthanCo on 2016/7/4.
 */
public class RequestModel {

    public CmdRequest generationGetServiceTimeCmd() {
        CmdRequest cmd = new CmdRequest();
        cmd.setCmd(AppCommandType.GET_SERVICE_TIME);
        return cmd;
    }

    private static class SingleTon {
        public static RequestModel sInstance = new RequestModel();
    }

    public static RequestModel getInstance() {
        return SingleTon.sInstance;
    }
}
