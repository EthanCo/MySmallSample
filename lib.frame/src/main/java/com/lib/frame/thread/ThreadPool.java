package com.lib.frame.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description 线程池
 * Created by EthanCo on 2016/7/28.
 */
public class ThreadPool {
    private ThreadPool() {
        executorPool = Executors.newCachedThreadPool();
        //executorPool = Executors.newScheduledThreadPool(3);
    }

    private static class SingletonHolder {
        private static ThreadPool sInstance = new ThreadPool();
    }

    public static ThreadPool getInstance() {
        return SingletonHolder.sInstance;
    }

    private final ExecutorService executorPool;

    public ExecutorService getExecutorPool() {
        return executorPool;
    }
}
