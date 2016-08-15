package com.lib.network.sbscribe;

import android.util.Log;

import com.lib.network.sbscribe.handle_chain.LoadFailed;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * 一般情况下使用该Subscriber即可
 * 尽量使用该类及其子类，方面日后进行统一处理
 * Created by EthanCo on 2016/1/3.
 */
public class RxSubscriber<T> extends LogSubscriber<T> {

    private List<Method> loadFailedList = new ArrayList<>();
    private boolean haveProcessDialog;
    private Object reflectObj; //进行反射的Obj
    private Action1<? super T> onNext;

    private Action0 onCompleted;

    public RxSubscriber() {
    }

    /**
     * @param onNext 对 OnNext 进行自定义处理
     */
    public RxSubscriber(final Action1<? super T> onNext) {
        if (onNext == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }

        this.onNext = onNext;
    }

    /**
     * @param onCompleted 对onCompeled进行自定义处理
     */
    public RxSubscriber(Action0 onCompleted) {
        if (onCompleted == null) {
            throw new IllegalArgumentException("onCompleted can not be null");
        }

        this.onCompleted = onCompleted;
    }

    /**
     * @param onNext 对 OnNext 进行自定义处理
     * @param o      传入ProcessDialogView子类 自动调用 dismissProcessDialog，出现错误时自动调用有@LoadFailed注解的方法
     */
    public RxSubscriber(final Action1<? super T> onNext, Object o) {
        this(onNext);
        this.reflectObj = o;
        iterateClasses(reflectObj, o.getClass());
    }

    /**
     * @param onCompleted 对onCompeled进行自定义处理
     * @param o           传入ProcessDialogView子类 自动调用 dismissProcessDialog，出现错误时自动调用有@LoadFailed注解的方法
     */
    public RxSubscriber(Action0 onCompleted, Object o) {
        this(onCompleted);
        this.reflectObj = o;
        iterateClasses(reflectObj, o.getClass());
    }

    /**
     * @param o 传入ProcessDialogView子类 自动调用 dismissProcessDialog，出现错误时自动调用有@LoadFailed注解的方法
     */
    public RxSubscriber(Object o) {
        this.reflectObj = o;
        iterateClasses(reflectObj, o.getClass());
    }

    public void dismissProgressDialog(Object o) {
        if (haveProcessDialog) {
            Method method = null;
            try {
                method = o.getClass().getMethod("dismissProgressDialog");
                method.invoke(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void iterateClasses(Object o, Class cls) {
        handleIfMatching(o, cls);

        Class superClass = cls.getSuperclass();
        if (superClass != null) handleIfMatching(o, superClass);

        Class[] interfaceClasses = cls.getInterfaces();
        if (interfaceClasses == null) return;

        for (Class interfaceCls : interfaceClasses) {
            handleIfMatching(o, interfaceCls);
            iterateClasses(o, interfaceCls);
        }
    }

    private void handleIfMatching(Object o, Class cls) {
        String interfaceClassName = cls.getName();
        Log.i(TAG, "ClassName:" + interfaceClassName);
        if ("com.lib.frame.view.ProcessDialogView".equals(interfaceClassName)) {
            haveProcessDialog = true;
        } else {
            //Log.v(TAG, "未处理的Interface:" + interfaceClassName);
        }

        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            LoadFailed loadFailedAnno = method.getAnnotation(LoadFailed.class);
            if (loadFailedAnno != null) {
                loadFailedList.add(method);
            }
        }
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        if (null != reflectObj) {
            dismissProgressDialog(reflectObj);
        }
        if (null != onCompleted) {
            onCompleted.call();
        }
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
        if (null != onNext) {
            onNext.call(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (null != reflectObj) {
            dismissProgressDialog(reflectObj);
            if (loadFailedList.size() == 0) {
                throw new IllegalStateException("not found @LoadFailed Annotation,this is a must");
            }
            for (Method method : loadFailedList) {
                try {
                    method.invoke(reflectObj, e.getLocalizedMessage());
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    //============================= Z-使用示例 ==============================/

    /*
    //不使用回调，只需打印log的情况
    public void sample1() {
        Observable.just("123")
                .subscribe(new RxSubscriber<String>());
    }

    //只使用 onNext 的情况
    public void sample2() {
        Observable.just("123")
                .subscribe(new RxSubscriber<String>(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                }));
    }

    //只使用 onCompleted 的情况
    public void sample3() {
        Observable.just("123")
                .subscribe(new RxSubscriber<String>(new Action0() {
                    @Override
                    public void call() {

                    }
                }));
    }

    //使用多个回调的情况
    public void sample4() {
        Observable.just("123")
                .subscribe(new RxSubscriber<String>(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                }) {
                    //已重载的形式

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }*/

    //传入getView() 自动调用 dismissProcessDialog (需继承ProcessDialogView)，出现错误时自动调用有@LoadFailed注解的方法
    /*Observable.just(null)
            .map(new Func1<Object, CmdRequest>() {
        @Override
        public CmdRequest call(Object o) {
            return RequestModel.getInstance().generationGetServiceTimeCmd();
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
    }, getView()));*/
}
