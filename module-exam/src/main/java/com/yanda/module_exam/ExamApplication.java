package com.yanda.module_exam;

import com.yanda.module_base.base.BaseApplication;
import com.yanda.module_base.config.ModuleLifecycleConfig;

/**
 * 作者：caibin
 * 时间：2021/4/29 14:05
 * 类说明：
 */
public class ExamApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ModuleLifecycleConfig.getInstance().initModuleAhead(this);
    }
}
