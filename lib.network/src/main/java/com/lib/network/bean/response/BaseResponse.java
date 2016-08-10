package com.lib.network.bean.response;

/**
 * @Description 网络响应基类
 * Created by EthanCo on 2016/6/14.
 */
public abstract class BaseResponse {
    protected String Cmd;

    public void setCmd(String Cmd) {
        this.Cmd = Cmd;
    }

    public String getCmd() {
        return Cmd;
    }
}
