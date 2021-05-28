//package com.yanda.library_db;
//
//import io.realm.Realm;
//import io.realm.RealmConfiguration;
//
///**
// * 作者：caibin
// * 时间：2021/5/27 21:23
// * 类说明：
// */
//public class RealmHelper {
//    private static Realm realm;
//
//    public static void init() {
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name("myrealm.realm") //文件名
//                .schemaVersion(1) //版本号
//                .build();
//        realm = Realm.getInstance(config);
//    }
//
//    public static Realm getRealm() {
//        return realm;
//    }
//}
