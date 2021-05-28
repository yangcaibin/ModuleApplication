package com.yanda.module_base.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yanda.module_base.dialog.LoadingProgressDialog;
import com.yanda.module_base.utils.Constant;
import com.yanda.module_base.utils.NetWorkUtils;
import com.yanda.module_base.utils.SPKey;
import com.yanda.module_base.utils.SPUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

/**
 * 作者：caibin
 * 时间：2020/5/9 17:25
 * 类说明：
 */
public abstract class BaseLazyFragment extends Fragment implements
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnRefreshListener,
        TabLayout.OnTabSelectedListener, ExpandableListView.OnGroupClickListener,
        ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupExpandListener,
        UMShareListener, ViewPager.OnPageChangeListener, OnLoadMoreListener, OnItemClickListener {

    private LoadingProgressDialog progressDialog;
    private SwipeRefreshLayout refreshLayout;
    private SmartRefreshLayout smartRefreshLayout;
    //    public StateView stateView;
    private View rootView;
    private Unbinder bind;
    public String userId, subjectId, appType;  //用户Id
    private long lastClickTime;
    public boolean isFirstLoad = true; // 是否第一次加载
    public boolean isVisible = false;  //是否可见
    public boolean isLoad = false;  //不是第一次加载  可见时是否加载数据
    private Disposable getTokenDisposable, verifyDispose, liveDetailDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getLayoutView(inflater, container, savedInstanceState);
        bind = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            // 将数据加载逻辑放到onResume()方法中
            userId = getUserId();
            subjectId = getSubjectId();
            appType = getAppType();
            //初始化控件的方法
            initView();
            //添加点击事件的方法
            addOnClick();
            isFirstLoad = false;
        }
        isVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isVisible = false;
    }

    //加载当前类的布局文件的方法
    public abstract View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    //初始化控件的方法
    public abstract void initView();

    //添加点击事件的方法
    public abstract void addOnClick();

    //获取app类型
    public String getAppType() {
        return (String) SPUtils.get(getContext(), SPKey.APPTYPE, Constant.APPTYPE);
    }

    //获取执业药师专业的类型
    public int getPharmacistSubjectType() {
        return (int) SPUtils.get(getContext(), SPKey.PHARMACISTSUBJECTTYPE, 1);
    }

    //获取用户Id的方法
    public String getUserId() {
        return (String) SPUtils.get(getContext(), SPKey.USERID, "");
    }

    //获取专业Id的方法
    public String getSubjectId() {
        return (String) SPUtils.get(getContext(), SPKey.SUBJECTID, Constant.SUBJECTID);
    }

    public void setRefreshLayout(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    public void setSmartRefreshLayout(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
    }

//    //数据加载失败显示的布局 b:数据为空点击是否刷新
//    public void setOnStateViewClick(StateView stateView, boolean b) {
//        this.stateView = stateView;
//        stateView.setOnRetryClickListener(() -> {
//            //点击重试响应事件
//            onRefresh();
//            onRefresh(smartRefreshLayout);
//        });
//        if (b) {
//            stateView.setOnEmptyClickListener(() -> {
//                onRefresh();
//                onRefresh(smartRefreshLayout);
//            });
//        }
//    }

    //显示加载框
    public void showProgressDialog() {
        if (progressDialog == null)
            progressDialog = new LoadingProgressDialog(getContext());
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
        if (refreshLayout != null) refreshLayout.setRefreshing(true);
    }

    public void dismissRefreshDialog() {
        if (refreshLayout != null) refreshLayout.setRefreshing(false);
    }

    public void dismissSmartRefreshDialog() {
        if (smartRefreshLayout != null) smartRefreshLayout.finishRefresh();
    }

//    public void showLoading() {
//        if (stateView != null) stateView.showLoading();
//    }
//
//    public void showContent() {
//        if (stateView != null) stateView.showContent();
//    }
//
//    public void showEmpty() {
//        if (stateView != null) stateView.showEmpty();
//    }
//
//    //失败显示重试的方法
//    public void showRetry() {
//        if (stateView != null) {
//            if (NetWorkUtils.isNetworkAvailable(getContext())) {
//                stateView.showRetry();
//            } else {
//                stateView.showNetwork();
//            }
//        }
//    }

    //refreshLayout 设置可以下拉刷新
    public void setRefreshTrue() {
        if (refreshLayout != null) refreshLayout.setEnabled(true);
    }

    //分页加载失败
    public void loadPageFail() {
    }

    //判断是否有网络连接的方法
    public boolean isNetworkMethod() {
        if (NetWorkUtils.isNetworkAvailable(getContext()))
            return true;
        showToast("无网络连接,请先连接网络!");
        return false;
    }

    //显示
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

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
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }

    @Override
    public void onGroupExpand(int groupPosition) {

    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        showToast("正在调起分享");
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        showToast("分享成功");
//        userId = (String) SPUtils.get(getContext(), SPKey.USERID, "");
//        if (!TextUtils.isEmpty(userId)) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("userId", userId);
//            RetrofitUtils.getApiService()
//                    .getShareSuccess(map)
//                    .compose(RxScheduler.Obs_io_main())
//                    .as(bindAutoDispose())
//                    .subscribe(new HttpObserver<String>() {
//
//                        @Override
//                        public void onSuccess(String result, String message) {
//
//                        }
//
//                        @Override
//                        public void onFailMessage(String message) {
//
//                        }
//                    });
//        }
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        showToast("分享失败," + throwable.getMessage());
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        showToast("分享已取消");
    }

    //关闭本类
    public void onBackActivity() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null) {
            bind.unbind();
        }
        isFirstLoad = true;
    }

    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //内存检测
//        RefWatcher refWatcher = DMApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }

//    //登录直播间
//    public void loginLiveMethod(boolean isLive, String polyvUserId, String roomId, String secret, String vid) {
//        showProgressDialog();
//        //请求token接口
//        PolyvLoginManager.checkLoginToken(polyvUserId, isLive ? secret : null, Constant.LIVEAPPID,
//                roomId, vid,
//                new PolyvrResponseCallback<PolyvChatDomain>() {
//                    @Override
//                    public void onSuccess(PolyvChatDomain responseBean) {
//                        PolyvLinkMicClient.getInstance().setAppIdSecret(Constant.LIVEAPPID, secret);
//                        PolyvLiveSDKClient.getInstance().setAppIdSecret(Constant.LIVEAPPID, secret);
//                        PolyvVodSDKClient.getInstance().initConfig(Constant.LIVEAPPID, secret);
//                        if (isLive) {
//                            requestLiveStatus(roomId);
//                            PolyvChatDomainManager.getInstance().setChatDomain(responseBean);
//                        } else {
//                            requestPlayBackStatus(roomId, vid);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(PolyvResponseBean<PolyvChatDomain> responseBean) {
//                        super.onFailure(responseBean);
//                        ToastUtils.showLong(responseBean.getMessage());
//                        progressDialog.dismissProgressDialog();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        errorStatus(e);
//                    }
//                });
//    }
//
//    //请求回放状态
//    private void requestPlayBackStatus(String roomId, String vid) {
//        if (TextUtils.isEmpty(vid)) {
//            return;
//        }
//        verifyDispose = PolyvLoginManager.getPlayBackType(vid, new PolyvrResponseCallback<PolyvPlayBackVO>() {
//            @Override
//            public void onSuccess(PolyvPlayBackVO playBack) {
//                if (playBack.getLiveType() == 0 || playBack.getLiveType() == 1) {
//                    boolean isLivePlayBack = playBack.getLiveType() == 0;
//                    PolyvVClassGlobalConfig.viewerId = userId;
//                    PolyvVClassGlobalConfig.username = (String) SPUtils.get(getContext(), SPKey.NICKNAME, "");
//                    String avatar = (String) SPUtils.get(getContext(), SPKey.AVATAR, "");
//                    PolyvCloudClassHomeActivity.startActivityForPlayBack(getActivity(),
//                            vid, roomId, Constant.BACKUSERID, isLivePlayBack,
//                            Address.IMAGE_NET + avatar, "", "");
//                } else {
//                    showToast("只支持云课堂类型频道或普通直播类型频道");
//                }
//                dismissProgressDialog();
//            }
//
//            @Override
//            public void onFailure(PolyvResponseBean<PolyvPlayBackVO> responseBean) {
//                super.onFailure(responseBean);
//                ToastUtils.showLong(responseBean.getMessage());
//                dismissProgressDialog();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                errorStatus(e);
//            }
//        });
//    }
//
//    //请求直播状态
//    private void requestLiveStatus(String roomId) {
//        verifyDispose = PolyvResponseExcutor.excuteUndefinData(PolyvApiManager.getPolyvLiveStatusApi()
//                        .geLiveStatusJson(roomId)
//                , new PolyvrResponseCallback<PolyvLiveStatusVO>() {
//                    @Override
//                    public void onSuccess(PolyvLiveStatusVO statusVO) {
//                        PolyvLiveChannelType channelType = null;
//                        try {
//                            channelType = PolyvLiveChannelType.mapFromServerString(statusVO.getChannelType());
//                        } catch (PolyvLiveChannelType.UnknownChannelTypeException e) {
//                            dismissProgressDialog();
//                            ToastUtils.showShort("未知的频道类型");
//                            e.printStackTrace();
//                            return;
//                        }
//                        if (channelType != PolyvLiveChannelType.CLOUD_CLASS && channelType != PolyvLiveChannelType.NORMAL) {
//                            dismissProgressDialog();
//                            ToastUtils.showShort("只支持云课堂类型频道或普通直播类型频道");
//                            return;
//                        }
//                        final boolean isAlone = channelType == PolyvLiveChannelType.NORMAL;//是否有ppt
//                        requestLiveDetail(new Consumer<String>() {
//                            @Override
//                            public void accept(String rtcType) throws Exception {
//                                dismissProgressDialog();
//                                if (false) {
//                                    if ("urtc".equals(rtcType) || TextUtils.isEmpty(rtcType)) {
//                                        ToastUtils.showShort("暂不支持该频道观看");
//                                        return;
//                                    }
//                                }
//                                PolyvVClassGlobalConfig.viewerId = userId;
//                                PolyvVClassGlobalConfig.username = (String) SPUtils.get(getContext(), SPKey.NICKNAME, "");
//                                String avatar = (String) SPUtils.get(getContext(), SPKey.AVATAR, "");
//                                PolyvCloudClassHomeActivity.startActivityForLiveWithParticipant(getActivity(),
//                                        roomId, Constant.LIVEUSERID, isAlone, false, rtcType,
//                                        Address.IMAGE_NET + avatar, "", "");
//                            }
//                        }, roomId);
//                    }
//
//                    @Override
//                    public void onFailure(PolyvResponseBean<PolyvLiveStatusVO> responseBean) {
//                        super.onFailure(responseBean);
//                        ToastUtils.showLong(responseBean.getMessage());
//                        dismissProgressDialog();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        errorStatus(e);
//                    }
//                });
//    }
//
//    //请求直播详情
//    private void requestLiveDetail(Consumer<String> onSuccess, String roomId) {
//        if (liveDetailDisposable != null) {
//            liveDetailDisposable.dispose();
//        }
//        liveDetailDisposable = PolyvResponseExcutor.excuteUndefinData(PolyvChatApiRequestHelper.getInstance()
//                .requestLiveClassDetailApi(roomId), new PolyvrResponseCallback<PolyvLiveClassDetailVO>() {
//            @Override
//            public void onSuccess(PolyvLiveClassDetailVO polyvLiveClassDetailVO) {
//                try {
//                    onSuccess.accept(polyvLiveClassDetailVO.getData().getRtcType());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                errorStatus(e);
//            }
//        });
//    }
//
//    //错误的方法
//    public void errorStatus(Throwable e) {
//        PolyvCommonLog.exception(e);
//        dismissProgressDialog();
//        if (e instanceof HttpException) {
//            try {
//                ToastUtils.showLong(((HttpException) e).response().errorBody().string());
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        } else {
//            ToastUtils.showLong(e.getMessage());
//        }
//    }
}
