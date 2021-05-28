package com.yanda.module_base.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.umeng.message.PushAgent;
import com.yanda.module_base.dialog.LoadingProgressDialog;
import com.yanda.module_base.utils.Constant;
import com.yanda.module_base.utils.NetWorkUtils;
import com.yanda.module_base.utils.SPKey;
import com.yanda.module_base.utils.SPUtils;
import com.yanda.module_base.utils.StatusBarUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：caibin
 * 时间：2020/12/18 14:34
 * 类说明：所有activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity implements
        View.OnClickListener, OnItemClickListener, ViewPager.OnPageChangeListener,
        SwipeRefreshLayout.OnRefreshListener, OnRefreshListener, OnLoadMoreListener {
    private SmartRefreshLayout smartRefreshLayout;
    private Unbinder bind;
    public String userId, subjectId, appType;  //用户Id,app类型
    private LoadingProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initContentView());
        AppManager.getInstance().addActivity(this); //添加到栈中
        registerSlideBack();
        PushAgent.getInstance(this).onAppStart();
        //绑定控件
        bind = ButterKnife.bind(this);
        userId = getUserId();  //得到用户Id
        subjectId = getSubjectId();  //得到当前所选专业的Id
        appType = getAppType();  //得到所选专业的类型
        // 初始化控件的方法
        initView();
        // 添加点击事件的方法
        addOnClick();
    }

    /**
     * 注册侧滑回调
     */
    protected void registerSlideBack() {
//        SlideBack.with(this)
//                .haveScroll(true)
//                .edgeMode(ResUtils.isRtl() ? SlideBack.EDGE_RIGHT : SlideBack.EDGE_LEFT)
//                .callBack(this::onBackPressed)
//                .register();
    }

    //初始化布局资源文件
    protected abstract int initContentView();

    //每个类中初始化控件的方法
    public abstract void initView();

    //每个类中添加点击事件的方法
    public abstract void addOnClick();

    //设置沉浸式状态栏
    public void setFitsSystemWindows(View view) {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.setTranslucentStatus(this);
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
        if (view != null) {
            int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            ViewGroup.MarginLayoutParams marginParams;
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
            } else {
                marginParams = new ViewGroup.MarginLayoutParams(layoutParams);
            }
            marginParams.setMargins(marginParams.getMarginStart(), statusBarHeight, marginParams.getMarginEnd(), 0);
//            layoutParams.topMargin = statusBarHeight;
            view.setLayoutParams(layoutParams);
        }
    }

    public void setSmartRefreshLayout(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
    }

    //获取用户Id
    public String getUserId() {
        return (String) SPUtils.get(this, SPKey.USERID, "");
    }

    //获取大专业的Id
    public String getSubjectId() {
        return (String) SPUtils.get(this, SPKey.SUBJECTID, Constant.SUBJECTID);
    }

    //获取app类型
    public String getAppType() {
        return (String) SPUtils.get(this, SPKey.APPTYPE, Constant.APPTYPE);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
//        if (!isFastDoubleClick()) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
//        }
    }

    //判断是否有网络连接的方法
    public boolean isNetworkMethod() {
        if (NetWorkUtils.isNetworkAvailable(this))
            return true;
        showToast("无网络连接,请先连接网络!");
        return false;
    }

    //显示加载框
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new LoadingProgressDialog(this);
            progressDialog.setCancelable(false);
        }
        progressDialog.showDialog();
    }

    //显示加载框
    public void showProgressDialog(boolean b) {
        if (progressDialog == null) {
            progressDialog = new LoadingProgressDialog(this);
            progressDialog.setCancelable(b);
        }
        progressDialog.showDialog();
    }

    //消失加载框
    public void dismissProgressDialog() {
        if (progressDialog != null) progressDialog.dismissProgressDialog();
    }

    public void showRefreshDialog() {

    }

    public void dismissRefreshDialog() {

    }

    public void dismissSmartRefreshDialog() {
        if (smartRefreshLayout != null) smartRefreshLayout.finishRefresh();
    }

    public void showLoading() {

    }

    public void showContent() {

    }

    public void showEmpty() {

    }

    public void showRetry() {

    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setRefreshTrue() {
        if (smartRefreshLayout != null) smartRefreshLayout.setEnabled(true);
    }

    public void loadPageFail() {

    }

    public void onBackActivity() {
        this.finish();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
//        SlideBack.unregister(this);
        AppManager.getInstance().finishActivity(this);
    }

    //app版本更新的方法
//    public void upDateAppVersion(AppVersionEntity versionEntity) {
//        String andtoid_flag = versionEntity.getAndtoid_flag();  //是否提示更新
//        String android_update = versionEntity.getAndroid_update();  //是否是强制更新
//        String android_url = versionEntity.getAndroid_url();  //应用宝地址
//        String android_apk = versionEntity.getAndroid_apk();  //apk地址
//        String android_desc = versionEntity.getAndroid_desc();  //说明
//        String lineCode = versionEntity.getAndroid_code();  //线上版本
//        int versionCode = ConstantUtils.getVersionCode(this);
////        if ("y".equals(andtoid_flag) && versionCode < Integer.parseInt(lineCode)) {
////            if (versionDialog == null) {
////                versionDialog = new UpDataVersionDialog(this);
////                versionDialog.setContent(android_desc);
////                //先显示  否则设置了宽度不管用
////                versionDialog.show();
////                if ("y".equals(android_update)) {
////                    versionDialog.setHintCloseLayout(true);
////                }
////                versionDialog.setVersionOnClickListener(new UpDataVersionDialog.VersionOnClickListener() {
////                    @Override
////                    public void onManualUpdateMethod() {
////                        //手动更新
////                        try {
////                            Uri uri = Uri.parse(android_url);
////                            Intent it = new Intent(Intent.ACTION_VIEW, uri);
////                            startActivity(it);
////                            if (!"y".equals(android_update)) {
////                                versionDialog.dismiss();
////                            }
////                        } catch (Exception e) {
////                            Bundle bundle = new Bundle();
////                            bundle.putString("type", "upVersion");
////                            bundle.putString("url", android_url);
////                            startActivity(WebViewActivity.class, bundle);
////                        }
////                    }
////
////                    @Override
////                    public void onAutoUpdateMethod() {
////                        //自动更新apk
////                        if (isServiceRunning(DownloadIntentService.class.getName())) {
////                            showToast(getResources().getString(R.string.app_name) + "正在下载");
////                            return;
////                        }
////                        Intent intent = new Intent(BaseActivity.this, DownloadIntentService.class);
////                        Bundle bundle = new Bundle();
////                        bundle.putString("download_url", android_apk);
////                        intent.putExtras(bundle);
////                        startService(intent);
////                        if (!"y".equals(android_update)) {
////                            versionDialog.dismiss();
////                        }
////                    }
////                });
////            } else {
////                versionDialog.setContent(android_desc);
////                versionDialog.show();
////            }
////        }
//    }

    /**
     * 用来判断服务是否运行.
     * 在线更新apk用
     *
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public boolean isServiceRunning(String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) return false;
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (serviceList == null || serviceList.isEmpty()) return false;
        for (int i = 0; i < serviceList.size(); i++) {
            if (TextUtils.equals(serviceList.get(i).service.getClassName(), className)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
