package com.yanda.module_base.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

/**
 * 作者：caibin
 * 时间：2021/1/21 14:44
 * 类说明：mvp模式的基类
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView {
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = getPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * 在子类中初始化对应的presenter
     *
     * @return 相应的presenter
     */
    public abstract T getPresenter();

    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
        super.onDestroy();
    }
}
