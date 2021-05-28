package com.yanda.module_base.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 作者：caibin
 * 时间：2021/1/21 16:36
 * 类说明：
 */
public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView {
    protected T presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 在子类中初始化对应的presenter
     */
    public abstract void initPresenter();

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
        super.onDestroyView();
    }
}
