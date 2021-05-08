package com.yanda.module_base.utils;


import com.yanda.module_base.BuildConfig;

/**
 * 作者：caibin
 * 时间：2017/10/23 10:50
 * 类说明：
 */

public class LogUtils {
    private static final String TAG = "lala";

    public static void i(String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(TAG, msg);
    }
}
