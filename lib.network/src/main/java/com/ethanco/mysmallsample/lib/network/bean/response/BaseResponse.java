package com.ethanco.mysmallsample.lib.network.bean.response;

/**
 * Created by qiukeling on 2016/7/14.
 */
public abstract class BaseResponse<T extends BaseDataBean> {
    protected String Cmd;
    private String Result;
    private T Data;

    public void setCmd(String Cmd) {
        this.Cmd = Cmd;
    }

    public String getCmd() {
        return Cmd;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
