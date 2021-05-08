package com.yanda.module_base.module;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yanda.module_base.base.BaseApplication;

/**
 * Created by zlx on 2020/9/22 14:28
 * Email: 1170762202@qq.com
 * Description:
 */
public class CommonModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new ClassicsHeader(application));
//        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(application));
//        MMKV.initialize(application);
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(application);
//        LoadSir.beginBuilder()
//                .addCallback(new ErrorCallback())
//                .addCallback(new LoadingCallback())
//                .addCallback(new EmptyCallback())
//                .setDefaultCallback(LoadingCallback.class)
//                .commit();


//        DbUtil.getInstance().init(application, "wanandroid");
        return false;
    }

    @Override
    public boolean onInitAfter(BaseApplication application) {
        return false;
    }
}
