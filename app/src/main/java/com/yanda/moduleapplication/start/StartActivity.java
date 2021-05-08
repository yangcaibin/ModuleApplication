package com.yanda.moduleapplication.start;

import android.os.Handler;
import android.text.TextUtils;

import com.yanda.module_base.base.BaseActivity;
import com.yanda.module_base.entity.PosterEntity;
import com.yanda.module_base.http.HttpObserver;
import com.yanda.module_base.http.RetrofitUtils;
import com.yanda.module_base.http.RxScheduler;
import com.yanda.module_base.utils.RouterUtil;
import com.yanda.module_base.utils.SPKey;
import com.yanda.module_base.utils.SPUtils;
import com.yanda.moduleapplication.R;

/**
 * 作者：caibin
 * 时间：2020/12/18 14:58
 * 类说明：启动页
 */
public class StartActivity extends BaseActivity {
    private boolean isFirst;  //判断是否是第一次进入程序
    private Handler handler;  //延时跳转
    private PosterEntity posterEntity;  //广告页的实体
    private String isShow = "false";
    //延时跳转的对象
    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (isFirst) {  //是第一次进入程序,跳入到导航页
                startActivity(GuiDeActivity.class);
            } else {  //不是第一次进入程序,跳入到主页
                if (!TextUtils.isEmpty(isShow) && isShow.equals("true")) {
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("entity", posterEntity);
//                    startActivity(PosterActivity.class, bundle);
                    RouterUtil.launchPosterActivity(posterEntity);
                } else {
                    RouterUtil.launchMainActivity();
                }
            }
            StartActivity.this.finish();
        }
    };

    @Override
    protected int initContentView() {
        return R.layout.activity_start;
    }

    @Override
    public void initView() {
        if (!isTaskRoot()) {
            finish();
            return;
        }
        handler = new Handler();
        //获取是否是第一次打开app
        isFirst = (boolean) SPUtils.get(StartActivity.this, SPKey.ISFRIST, true);
        if (!isFirst) {
            if (!TextUtils.isEmpty(appType)) {
                //获取广告页的数据
                getPosterData(appType);
            }
        }
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void addOnClick() {

    }

    //获取广告页的数据
    private void getPosterData(String appType) {
        RetrofitUtils.getApiService()
                .getPosterEntity(appType)
                .compose(RxScheduler.Obs_io_main())
                .as(bindAutoDispose())
                .subscribe(new HttpObserver<PosterEntity>() {
                    @Override
                    public void onSuccess(PosterEntity result, String message) {
                        try {
                            posterEntity = result;
                            String img_url = result.getImg_url();
                            if (!TextUtils.isEmpty(img_url)) {
                                isShow = result.getIs_show();
                            }
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
