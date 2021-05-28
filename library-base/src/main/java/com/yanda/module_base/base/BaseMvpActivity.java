package com.yanda.module_base.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * 作者：caibin
 * 时间：2021/1/21 14:44
 * 类说明：mvp模式的基类
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView {
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * 在子类中初始化对应的presenter
     */
    public abstract void initPresenter();

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
        super.onDestroy();
    }
}
