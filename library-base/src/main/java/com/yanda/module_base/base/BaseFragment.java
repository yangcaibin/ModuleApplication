package com.yanda.module_base.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanda.module_base.dialog.LoadingProgressDialog;
import com.yanda.module_base.utils.Constant;
import com.yanda.module_base.utils.SPKey;
import com.yanda.module_base.utils.SPUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

/**
 * 作者：caibin
 * 时间：2020/12/25 13:20
 * 类说明：
 */
public abstract class BaseFragment extends Fragment implements
        View.OnClickListener, ViewPager.OnPageChangeListener,
        OnItemClickListener, OnRefreshListener {
    private SmartRefreshLayout smartRefreshLayout;
    private Unbinder bind;
    public View rootView;
    private Disposable disposable;  //联网
    public String userId, subjectId, appType;  //用户Id,app类型
    private LoadingProgressDialog progressDialog;
    private long lastClickTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getLayoutView(inflater, container, savedInstanceState);
        bind = ButterKnife.bind(this, rootView);
        userId = getUserId();
        subjectId = getSubjectId();
        appType = getAppType();
        initView();
        //添加点击事件的方法
        addOnClick();
        return rootView;
    }

    //加载当前类的布局文件的方法
    public abstract View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    //初始化控件的方法
    public abstract void initView();

    //添加点击事件的方法
    public abstract void addOnClick();

    //获取用户Id
    public String getUserId() {
        return (String) SPUtils.get(getContext(), SPKey.USERID, "");
    }

    //获取大专业的Id
    public String getSubjectId() {
        return (String) SPUtils.get(getContext(), SPKey.SUBJECTID, Constant.SUBJECTID);
    }

    //获取app类型
    public String getAppType() {
        return (String) SPUtils.get(getContext(), SPKey.APPTYPE, Constant.APPTYPE);
    }

    public void setSmartRefreshLayout(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
    }

    //显示加载框
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new LoadingProgressDialog(getContext());
            progressDialog.setCancelable(false);
        }
        progressDialog.showDialog();
    }

    //显示加载框
    public void showProgressDialog(boolean b) {
        if (progressDialog == null) {
            progressDialog = new LoadingProgressDialog(getContext());
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
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void setRefreshTrue() {
        if (smartRefreshLayout != null) smartRefreshLayout.setEnabled(true);
    }

    public void loadPageFail() {

    }

    public void onBackActivity() {

    }

    //设置订阅者
    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

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

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    //通过Class跳转界面
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    //含有Bundle通过Class跳转界面
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    //通过Class跳转界面
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    //含有Bundle通过Class跳转界面
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        if (!isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(getContext(), cls);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivityForResult(intent, requestCode);
        }
    }

    //判断双击按钮是否小于等于300毫秒
    private boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD > 300)
            lastClickTime = time;
        return timeD <= 300;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (bind != null) {
            bind.unbind();
        }
    }
}
