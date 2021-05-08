package com.yanda.module_base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;

/**
 * @author bin 修改人: 时间:2015-9-28 下午2:22:55 类说明:网络的工具类
 */
public class NetWorkUtils {
    /**
     * 没有连接网络
     */
    public static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    public static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    public static final int NETWORK_WIFI = 1;
    private static ConnectivityManager connectivityManager; // 判断网络的对象

    // 实例话判断网络的类
    private static void initConnectivityManager(Context context) {
        if (connectivityManager == null) {
            connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        }
    }

    // 判断网络是否可用
    public static boolean isNetworkAvailable(Context context) {
        initConnectivityManager(context);
        //得到网络的信息
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    // 判断是否是WIFI连接
    public static boolean isWIFI(Context context) {
        initConnectivityManager(context);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    //返回网络连接的类型
    public static int getNetWorkType(Context context) {
        initConnectivityManager(context);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            switch (activeNetworkInfo.getType()) {
                case ConnectivityManager.TYPE_MOBILE:
                    return NETWORK_MOBILE;
                case ConnectivityManager.TYPE_WIFI:  //wifi连接
                    return NETWORK_WIFI;
                default:
                    break;
            }
        } else {
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }

    /*
     * 判断设备 是否使用代理上网
     * */
    private boolean isWifiProxy(Context context) {
        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            proxyAddress = android.net.Proxy.getHost(context);
            proxyPort = android.net.Proxy.getPort(context);
        }
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }
}
