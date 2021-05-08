package com.yanda.module_base.http;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：caibin
 * 时间：2020/12/25 14:15
 * 类说明：统一线程处理
 */
public class RxScheduler {
    /**
     * 统一线程处理
     *
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> Obs_io_main() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
