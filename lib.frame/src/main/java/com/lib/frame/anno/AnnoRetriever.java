package com.lib.frame.anno;

import com.lib.frame.viewmodel.BaseViewModel;

/**
 * @Description 注解搜寻者
 * Created by EthanCo on 2016/8/15.
 */
public interface AnnoRetriever<T> {
    /**
     * 搜寻注解
     *
     * @param viewModel
     */
    void seek(BaseViewModel viewModel);

    /**
     * 清除搜寻到的Filed
     */
    void clear();


}
