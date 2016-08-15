package com.lib.frame.viewmodel;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.lib.frame.anno.AnnoRetriever;
import com.lib.frame.anno.AutoDestoryRetriever;
import com.lib.frame.thread.ThreadPool;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;

/**
 * @Description ViewModel基类
 * Created by EthanCo on 2016/6/13.
 */
public abstract class BaseViewModel<T> {
    private static final String TAG = "Z-BaseViewModel";
    protected Reference<T> mViewRef; //View接口类型的弱引用
    private AnnoRetriever retriever = new AutoDestoryRetriever();
    protected ExecutorService executorPool = ThreadPool.getInstance().getExecutorPool();

    public BaseViewModel() {
        executorPool.execute(new Runnable() {
            @Override
            public void run() {
                seek();
            }
        });
    }

    private void seek() {
        retriever.seek(this);
    }

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
        executorPool.execute(new Runnable() {
            @Override
            public void run() {
                retriever.clear();
            }
        });

        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
