package com.yanda.module_base.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者：caibin
 * 时间：2018/3/13 15:39
 * 类说明：
 */

public class BasePresenter<V extends BaseView> {
    public V mView;
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(V view) {
        this.mView = view;
    }

    /**
     * 将Disposable添加
     */
    public void addDisposable(Disposable d) {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(d);
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    //Activity关闭把view对象置为空
    public void detach() {
        if (mView != null) {
            mView = null;
        }
        unDisposable();
    }
}
