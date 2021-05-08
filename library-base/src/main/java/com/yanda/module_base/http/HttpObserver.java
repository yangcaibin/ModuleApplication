package com.yanda.module_base.http;

import android.text.TextUtils;
import android.widget.Toast;

import com.yanda.module_base.base.AppManager;
import com.yanda.module_base.base.BaseApplication;
import com.yanda.module_base.utils.RouterUtil;

import io.reactivex.observers.DisposableObserver;

/**
 * 作者：caibin
 * 时间：2017/11/14 16:12
 * 类说明：
 */

public abstract class HttpObserver<T> extends DisposableObserver<HttpResult<T>> {

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(HttpResult<T> result) {
        if (result == null) {
            onError(new NullPointerException());
        } else {
            if (result.isSuccess()) {
                onSuccess(result.getEntity(), result.getMessage());
            } else {
                String message = result.getMessage();
                if (TextUtils.equals(message, "timeOut") || TextUtils.equals(message, "limitOut")) {
                    if (TextUtils.equals(message, "timeOut")) {
                        Toast.makeText(BaseApplication.getInstance(), "登录超时", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BaseApplication.getInstance(), "账号在其他设备登录", Toast.LENGTH_SHORT).show();
                    }
                    //同步数据
//                    BaseApplication.getInstance().synchronousDataMethod();
                    //登录超时
//                    AppManager.getInstance().returnToActivity(MainActivity.class);
                    RouterUtil.launchLogin();
//                    Intent intent = new Intent(BaseApplication.getInstance(), LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    BaseApplication.getInstance().startActivity(intent);
                } else {
                    onFailMessage(message);
                }
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        onComplete();
    }

    @Override
    public void onComplete() {

    }

    public void onSuccess(T result, String message) {
    }

    public void onFailMessage(String message) {
    }
}
