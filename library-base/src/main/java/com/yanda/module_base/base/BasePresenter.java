package com.yanda.module_base.base;

/**
 * 作者：caibin
 * 时间：2018/3/13 15:39
 * 类说明：
 */

public class BasePresenter<V extends BaseView> {
    public V mView;

    public BasePresenter() {
    }

    public BasePresenter(V view) {
        this.mView = view;
    }

    public void attachView(V view) {
        this.mView = view;
    }

    //Activity关闭把view对象置为空
    public void detach() {
        if (mView != null) {
            mView = null;
        }
    }
}
