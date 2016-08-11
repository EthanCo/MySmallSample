package com.lib.frame.viewmodel;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Description ViewModel基类
 * Created by EthanCo on 2016/6/13.
 */
public abstract class BaseViewModel<T> {
    protected Reference<T> mViewRef; //View接口类型的弱引用

    @CallSuper
    public void attachView(T view) { //建立关联
        mViewRef = new WeakReference<T>(view);
    }

    @NonNull
    protected T getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @CallSuper
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

//    /**
//     * 检查Response是否成功
//     *
//     * @param response
//     * @return true 成功
//     */
//    protected boolean checkResponseResult(BaseBeanResponse response) {
//        if (AppCommandType.SUCCESS.equals(response.getResult())) {
//            return true;
//        } else {
//            throw new IllegalStateException(response.getData().getMessage());
//        }
//    }
}