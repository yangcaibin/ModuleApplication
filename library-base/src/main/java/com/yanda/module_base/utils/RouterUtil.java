package com.yanda.module_base.utils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yanda.module_base.entity.PosterEntity;
import com.yanda.module_base.router.RouterActivityPath;

public class RouterUtil {

    //跳转广告页
    public static void launchPosterActivity(PosterEntity posterEntity) {
        ARouter.getInstance().build(RouterActivityPath.Main.PAGER_POSTER)
                .withSerializable("entity",posterEntity).navigation();
    }

    //跳主页
    public static void launchMainActivity() {
        ARouter.getInstance().build(RouterActivityPath.Main.PAGER_MAIN).navigation();
    }

    //跳登录
    public static void launchLogin() {
        ARouter.getInstance().build(RouterActivityPath.Login.PAGER_LOGIN).navigation();
    }

    //跳课程
    public static void launchCourse() {
        ARouter.getInstance().build(RouterActivityPath.Course.PAGER_COURSE).navigation();
    }

    //跳考试
    public static void launchExam() {
        ARouter.getInstance().build(RouterActivityPath.Exam.PAGER_BEGIN).navigation();
    }

    public static void launchWeb(String webUrl) {
        ARouter.getInstance().build(RouterActivityPath.Web.PAGER_WEB).withString("webUrl", webUrl).navigation();
    }

    public static void launchArticleList(String id, String title) {
        ARouter.getInstance().build(RouterActivityPath.Square.PAGER_SQUARE_LIST).withString("id", id).withString("title", title).navigation();
    }

}
