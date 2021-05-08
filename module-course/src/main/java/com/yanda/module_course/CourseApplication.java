package com.yanda.module_course;

import com.yanda.module_base.base.BaseApplication;
import com.yanda.module_base.config.ModuleLifecycleConfig;

/**
 * 作者：caibin
 * 时间：2021/4/16 15:04
 * 类说明：
 */
public class CourseApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ModuleLifecycleConfig.getInstance().initModuleAhead(this);
    }
}
