package com.yanda.module_base.base;

import com.uber.autodispose.AutoDisposeConverter;

/**
 * 作者：caibin
 * 时间：2018/3/9 17:19
 * 类说明：接口类  这个类定义公共的联网的一些抽象的方法
 */

public interface BaseView {
    //联网开始显示加载框的方法
    void showProgressDialog();

    void dismissProgressDialog();

    void showRefreshDialog();

    void dismissRefreshDialog();

    void dismissSmartRefreshDialog();

    void showLoading();

    void showContent();

    void showEmpty();

    void showRetry();

    void showToast(String message);

    //refreshLayout 设置可以下拉刷新
    void setRefreshTrue();

    //分页加载失败
    void loadPageFail();

    //关闭当前页面
    void onBackActivity();

    /**
     *绑定Android生命周期 防止RxJava内存泄漏
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();
}
