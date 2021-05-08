package com.yanda.module_base.base;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

/**
 * 作者：caibin
 * 时间：2021/4/16 15:05
 * 类说明：
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication mInstance;

    // 单例模式中获取唯一的BaseApplication实例
    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this);
    }

    /**
     * 当宿主没有继承自该Application时,可以使用set方法进行初始化baseApplication
     */
    private void setApplication(@NonNull BaseApplication application) {
        mInstance = application;
//        application
//                .registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//                    @Override
//                    public void onActivityCreated(@NonNull Activity activity,
//                                                  @Nullable Bundle savedInstanceState) {
//                        ActivityUtil.addActivity(activity);
//                    }
//
//                    @Override
//                    public void onActivityStarted(@NonNull Activity activity) {
//
//                    }
//
//                    @Override
//                    public void onActivityResumed(@NonNull Activity activity) {
//
//                    }
//
//                    @Override
//                    public void onActivityPaused(@NonNull Activity activity) {
//
//                    }
//
//                    @Override
//                    public void onActivityStopped(@NonNull Activity activity) {
//
//                    }
//
//                    @Override
//                    public void onActivitySaveInstanceState(
//                            @NonNull Activity activity, @NonNull Bundle outState) {
//
//                    }
//
//                    @Override
//                    public void onActivityDestroyed(@NonNull Activity activity) {
//                        ActivityUtil.removeActivity(activity);
//                    }
//                });
    }
}
