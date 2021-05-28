package com.yanda.library_db;

import android.content.Context;
import android.util.Log;

import com.yanda.library_db.entity.MyObjectBox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import io.objectbox.BoxStore;
import io.objectbox.BoxStoreBuilder;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * 作者：caibin
 * 时间：2021/5/27 11:39
 * 类说明：
 */
public class ObjectBox {
    public static BoxStore boxStore;
    public static BoxStoreBuilder builder;

    public static void init(Context context) {
//        File baseDir = getAndroidBaseDir(context);
//        if (baseDir.exists()) {
        builder = MyObjectBox.builder();
        boxStore = builder
                    .androidContext(context.getApplicationContext())
                    .build();
//        } else {
//            File file = null;
//            try {
//                file = new File(context.getFilesDir(), "data.mdb");
//                InputStream is = context.getAssets().open("data.mdb");
//                FileOutputStream fos = null;
//
//                fos = new FileOutputStream(file);
//
//                byte buffer[] = new byte[1024];
//                int len = 0;
//                while ((len = is.read(buffer)) > 0) {
//                    fos.write(buffer, 0, len);
//                }
//                fos.flush();
//                is.close();
//
//                boxStore = MyObjectBox.builder()
//                        .androidContext(context.getApplicationContext())
//                        .initialDbFile(file)
//                        .build();
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.i("lala", e.getMessage());
//            }
//        }
        if (BuildConfig.DEBUG && boxStore != null) {
            // 开启一个浏览服务
            boolean start = new AndroidObjectBrowser(boxStore).start(context);
        }
    }

    public static BoxStoreBuilder getBuilder() {
        return builder;
    }

    public static BoxStore getBoxStore() {
        return boxStore;
    }

    static private File getAndroidBaseDir(Object context) {
        return new File(getAndroidFilesDir(context), "objectbox");
    }

    private static File getAndroidFilesDir(Object context) {
        File filesDir;
        try {
            Method getFilesDir = context.getClass().getMethod("getFilesDir");
            filesDir = (File) getFilesDir.invoke(context);
            if (filesDir == null) {
                // Race condition in Android before 4.4: https://issuetracker.google.com/issues/36918154 ?
                System.err.println("getFilesDir() returned null - retrying once...");
                filesDir = (File) getFilesDir.invoke(context);
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Could not init with given Android context (must be sub class of android.content.Context)", e);
        }
        if (filesDir == null) {
            throw new IllegalStateException("Android files dir is null");
        }
        if (!filesDir.exists()) {
            throw new IllegalStateException("Android files dir does not exist");
        }
        return filesDir;
    }
}
