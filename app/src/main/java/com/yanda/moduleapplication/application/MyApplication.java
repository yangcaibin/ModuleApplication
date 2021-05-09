package com.yanda.moduleapplication.application;

import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.yanda.module_base.base.BaseApplication;
import com.yanda.module_base.config.ModuleLifecycleConfig;
import com.yanda.module_base.utils.Constant;
import com.yanda.moduleapplication.BuildConfig;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.vivo.VivoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

/**
 * 作者：caibin
 * 时间：2021/3/12 14:03
 * 类说明：
 */
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ModuleLifecycleConfig.getInstance().initModuleAhead(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // android 7.0系统解决拍照的问题
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //初始化友盟
        initUmeng();
    }

    //初始化友盟
    private void initUmeng() {
        //预初始化
        UMConfigure.preInit(this, Constant.YMKEY, Constant.CHANNEL);
        //可以加上是否是第一次启动(目的是是否同意了隐私权限)
        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
         * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）。
         */
        UMConfigure.init(this, Constant.YMKEY, Constant.CHANNEL,
                UMConfigure.DEVICE_TYPE_PHONE, Constant.YMSECRET);
        /**
         *设置组件化的Log开关
         *参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true);
        //正式初始化
        initPushSDK();
        // 微信设置
        PlatformConfig.setWeixin(Constant.WXAPPID, Constant.WXSECRET);
        PlatformConfig.setWXFileProvider(getPackageName() + ".provider");
        // QQ设置
        PlatformConfig.setQQZone(Constant.QQID, Constant.QQKEY);
        PlatformConfig.setQQFileProvider(getPackageName() + ".provider");
        // 新浪微博设置
//        PlatformConfig.setSinaWeibo("3921700954","04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
//        PlatformConfig.setSinaFileProvider("com.yanda.moduleapplication.provider");
    }

    //初始化推送
    private void initPushSDK() {
        //建议在线程中执行初始化
        new Thread(() -> {
            //获取消息推送实例
            PushAgent pushAgent = PushAgent.getInstance(this);
            //检查集成配置文件
            pushAgent.setPushCheck(BuildConfig.DEBUG);
            //设置通知栏显示数量
            pushAgent.setDisplayNotificationNumber(0);
            //sdk开启通知声音
            pushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
            Handler handler = new Handler(getMainLooper());
            UmengMessageHandler messageHandler = new UmengMessageHandler() {

                /**
                 * 通知的回调方法（通知送达时会回调）
                 */
                @Override
                public void dealWithNotificationMessage(Context context, UMessage msg) {
                    //调用super，会展示通知，不调用super，则不展示通知。
                    super.dealWithNotificationMessage(context, msg);
                }

                /**
                 * 自定义消息的回调方法
                 */
                @Override
                public void dealWithCustomMessage(final Context context, final UMessage msg) {
                }

                /**
                 * 自定义通知栏样式的回调方法
                 */
                @Override
                public Notification getNotification(Context context, UMessage msg) {
                    return super.getNotification(context, msg);
                }
            };
            //自定义通知栏样式
            pushAgent.setMessageHandler(messageHandler);
            /**
             * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
             * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
             * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
             * */
            UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

                @Override
                public void launchApp(Context context, UMessage msg) {
                    super.launchApp(context, msg);
                }

                @Override
                public void openUrl(Context context, UMessage msg) {
                    super.openUrl(context, msg);
                }

                @Override
                public void openActivity(Context context, UMessage msg) {
                    super.openActivity(context, msg);
                }

                @Override
                public void dealWithCustomAction(Context context, UMessage msg) {
                    Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                }
            };
            //自定义通知栏打开动作
            pushAgent.setNotificationClickHandler(notificationClickHandler);
            //注册推送服务，每次调用register方法都会回调该接口
            pushAgent.register(new IUmengRegisterCallback() {

                @Override
                public void onSuccess(String deviceToken) {
                    //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                }

                @Override
                public void onFailure(String s, String s1) {
                }
            });
            //小米通道
            MiPushRegistar.register(this, Constant.XMID, Constant.XMKEY);
            //华为推送
            HuaWeiRegister.register(this);
            //魅族推送
//            MeizuRegister.register(this, Constant.FLYMEID, Constant.FLYMESECRET);
            //OPPO通道，参数1为app key，参数2为app secret
            OppoRegister.register(this, Constant.OPPOAPPKEY, Constant.OPPOAPPSECRET);
            //vivo 通道
            VivoRegister.register(this);
        }).start();
    }

}
