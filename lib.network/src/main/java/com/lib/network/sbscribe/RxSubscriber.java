package com.lib.network.sbscribe;

import android.util.Log;

import com.lib.network.sbscribe.handle_chain.LoadFailed;

import java.lang.annotation.Annotation;
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
    private Object reflectObj;
    private Action1<? super T> onNext;

    private Action0 onCompleted;

    public RxSubscriber() {
    }

    public RxSubscriber(final Action1<? super T> onNext) {
        if (onNext == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }

        this.onNext = onNext;
    }

    public RxSubscriber(Action0 onCompleted) {
        if (onCompleted == null) {
            throw new IllegalArgumentException("onCompleted can not be null");
        }

        this.onCompleted = onCompleted;
    }

    public RxSubscriber(final Action1<? super T> onNext, Object o) {
        this(onNext);
        this.reflectObj = o;
        iterateClasses(reflectObj, o.getClass());
    }

    public RxSubscriber(Action0 onCompleted, Object o) {
        this(onCompleted);
        this.reflectObj = o;
        iterateClasses(reflectObj, o.getClass());
    }

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

    public void invoke(Object o, String methodName) {
        String className = o.getClass().getName();
        Log.i(TAG, "className: " + className);

        //Class[] classes = o.getClass().getInterfaces();
        iterateClasses(o, o.getClass());

        /*String interfaceClassName;
        for (Class interfaceClass1 : classes) {
            Class[] classArr2 = interfaceClass1.getInterfaces();
            for (Class interfaceClass : classArr2) {
                interfaceClassName = interfaceClass.getName();
                Log.i(TAG, "interfaceClassName:" + interfaceClassName);
                if ("com.lib.frame.view.ProcessDialogView".equals(interfaceClassName)) {
                    Method method = null;
                    try {
                        method = o.getClass().getMethod(methodName);
                        method.invoke(o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v(TAG, "未处理的Interface:" + interfaceClassName);
                }
            }
        }*/
    }

    /*public void handleClasses(Object o, Class[] classes) {
        String interfaceClassName;
        for (Class interfaceCls : classes) {
            interfaceClassName = interfaceCls.getName();
            Log.i(TAG, "interfaceClassName:" + interfaceClassName);
            //TODO Handler
            if ("com.lib.frame.view.ProcessDialogView".equals(interfaceClassName)) {
                Method method = null;
                try {
                    method = o.getClass().getMethod("dismissProgressDialog");
                    method.invoke(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.v(TAG, "未处理的Interface:" + interfaceClassName);
            }

            Class superClass = interfaceCls.getSuperclass();
            Log.i(TAG, "handleClasses : ");
            if (superClass != null) {
                Class[] superInterfaceClasses = superClass.getInterfaces();
                if (null != superInterfaceClasses && superInterfaceClasses.length > 0) {
                    Log.i(TAG, "handleClasses : ");
                    handleClasses(o, superInterfaceClasses);
                }
            }
        }
    }*/

    public void iterateClasses(Object o, Class cls) {
        handleIfMatching(o, cls);

//        Class superClass = cls.getSuperclass();
//        if (superClass != null) handleIfMatching(o, superClass);

        Class[] interfaceClasses = cls.getInterfaces();
        if (interfaceClasses == null) return;

        for (Class interfaceCls : interfaceClasses) {
            handleIfMatching(o, interfaceCls);
            iterateClasses(o, interfaceCls);
        }
    }

    private void handleIfMatching(Object o, Class interfaceCls) {
        String interfaceClassName = interfaceCls.getName();
        Log.i(TAG, "interfaceClassName:" + interfaceClassName);
        if ("com.lib.frame.view.ProcessDialogView".equals(interfaceClassName)) {
            haveProcessDialog = true;
        } else {
            Log.v(TAG, "未处理的Interface:" + interfaceClassName);
        }

        Method[] methods = interfaceCls.getMethods();
        for (Method method : methods) {
            Log.i(TAG, "handleIfMatching method: " + method.getName());
            Annotation[] annotations = method.getAnnotations();
            Log.i(TAG, "handleIfMatching method: " + method + " annotationArr.len:" + annotations.length);
            LoadFailed loadFailedAnno = method.getAnnotation(LoadFailed.class);
            if (loadFailedAnno != null) {
                Log.i(TAG, "handleIfMatching  add: ");
                loadFailedList.add(method);
            }
//            for (Annotation annotation : annotations) {
//                Log.i(TAG, "handleIfMatching method: " + method + " annotation:" + annotation);
//                annotation.is
////                Class<? extends Annotation> annotationType = annotation.annotationType();
////                LoadFailed loadFailedAnnotation = annotationType.getAnnotation(LoadFailed.class);
//                if (annotation != null) {
//
//                }
//            }
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
        throw new RuntimeException("gsdfsdfsdf");
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (null != reflectObj) {
            dismissProgressDialog(reflectObj);
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
}
