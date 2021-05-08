package com.yanda.module_base.http;

import com.yanda.module_base.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 作者：caibin
 * 时间：2017/11/6 16:35
 * 类说明：联网的工具类
 */

public class RetrofitUtils {
    //请求地址的总的接口类
    private static ApiService apiService = null;

    //得到接口类 配置解析为gson解析
    public static ApiService getApiService() {
        synchronized (RetrofitUtils.class) {
            if (apiService == null) {
                //配置OkHttp
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    builder.addInterceptor(interceptor);
                }
                OkHttpClient client = builder
                        //设置连接超时
                        .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                        //设置读取超时
                        .readTimeout(10000L, TimeUnit.MILLISECONDS)
                        //设置写入超时
                        .writeTimeout(10000L, TimeUnit.MILLISECONDS)
                        //设置缓存目录和10M缓存
//                        .cache(new Cache(getCacheDir(),10 * 1024 * 1024))
                        .addInterceptor(new UserAgentInterceptor())
//                        .proxy(Proxy.NO_PROXY)
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .client(client)        //设置OkHttp
                        .baseUrl(Address.HOST)
                        //增加返回值为String的支持
                        .addConverterFactory(ScalarsConverterFactory.create())
                        //增加返回值为Gson的支持(以实体类返回)
                        .addConverterFactory(GsonConverterFactory.create())
                        //添加RxJava 增加返回值为Oservable<T>的支持
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                apiService = retrofit.create(ApiService.class);
            }
        }
        return apiService;
    }
}
