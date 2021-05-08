package com.yanda.module_base.http;

import com.yanda.module_base.base.BaseApplication;
import com.yanda.module_base.utils.Constant;
import com.yanda.module_base.utils.LogUtils;
import com.yanda.module_base.utils.SPKey;
import com.yanda.module_base.utils.SPUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：caibin
 * 时间：2017/11/6 17:00
 * 类说明：okHttp拦截器
 */

public class UserAgentInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取request
        Request request = chain.request();
        //获取request的创建者builder
        Request.Builder builder = request.newBuilder();
        //从request中获取headers，通过给定的键url_name
        List<String> headerValues = request.headers(Address.HEADKEY);
        if (headerValues.size() > 0) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader(Address.HEADKEY);
            //添加验证header
            String nonce = getNonceStr();  //获取随机数
            long mills = System.currentTimeMillis();  //获取系统时间毫秒数
            HttpUrl.Builder newBuilder = request.url().newBuilder();
            newBuilder.addQueryParameter("nonce_str", nonce)
                    .addQueryParameter("timestamp", String.valueOf(mills))
                    .addQueryParameter("terminal", "android")
                    .addQueryParameter("currentTime", getCurrentTime())
                    .addQueryParameter("app_keyword", (String)SPUtils.get(BaseApplication.getInstance(), SPKey.APPTYPE, Constant.APPTYPE))
                    .addQueryParameter("app_professionId", (String)SPUtils.get(BaseApplication.getInstance(), SPKey.SUBJECTID, Constant.SUBJECTID));
            //匹配获得新的BaseUrl
            String headerValue = headerValues.get(0);
            HttpUrl newBaseUrl;
            /**
             * 这里拦截到对应的header
             * 重新设置新的域名
             * **/
            if (Address.HEADMAIN.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(Address.HOST);
                newBuilder.addQueryParameter("secret", getMainSecret(nonce, mills));
            } else if (Address.HEADUSER.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(Address.USERHOST);
                newBuilder.addQueryParameter("secret", getUserSecret(nonce, mills));
            } else if (Address.HEADSHOP.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(Address.SHOPHOST);
                newBuilder.addQueryParameter("secret", getShopSecret(nonce, mills));
            } else if (Address.HEADEXAM.equals(headerValue)) {
                String appType = (String) SPUtils.get(BaseApplication.getInstance(),
                        SPKey.APPTYPE, Constant.APPTYPE);
                String url = String.format(Address.EXAMHOST, appType);
                newBaseUrl = HttpUrl.parse(url);
                newBuilder.addQueryParameter("secret", getExamSecret(nonce, mills));
            } else {
                newBaseUrl = HttpUrl.parse(Address.HOST);
                newBuilder.addQueryParameter("secret", getMainSecret(nonce, mills));
            }
            HttpUrl oldHttpUrl = newBuilder.build();
            //从request中获取原有的HttpUrl实例oldHttpUrl
//            HttpUrl oldHttpUrl = request.url();
            //重建新的HttpUrl，修改需要修改的url部分
            HttpUrl newHttpUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .build();

            //重建这个request，通过builder.url(newHttpUrl).build()；
            //然后返回一个response至此结束修改
            return chain.proceed(builder.url(newHttpUrl).build());
        } else {
            return chain.proceed(request);
        }
    }

    //获取随机字符串
    public String getNonceStr() {
        Random random = new Random();
        return MD5
                .getMD5(String.valueOf(random.nextInt(10000)));
    }

    public String getCurrentTime() {
        return (String)SPUtils.get(BaseApplication.getInstance(), SPKey.CURRENTTIME, "");
    }

    //获取主站secret
    private String getMainSecret(String nonce_str, long mills) {
        //主站
        String productId = "product_web";
        String token = "fxQipHk3pZa0q5i9MxBlwO1bhM+6lqQW";
        return MD5.getMD5("productId=" + productId + "&token=" + token +
                "&nonce_str=" + nonce_str + "&timestamp=" + mills);
    }

    //获取shopsecret
    private String getShopSecret(String nonce_str, long mills) {
        //商城
        String shopProductId = "product_shop";
        String shopToken = "cum4VNfgjRDcvSPJSDCvRhb9172Lieax";
        return MD5.getMD5("productId=" + shopProductId + "&token=" + shopToken +
                "&nonce_str=" + nonce_str + "&timestamp=" + mills);
    }

    //获取examsecret
    private String getExamSecret(String nonce_str, long mills) {
        //考试
        String examProductId = "product_exam";
        String examToken = "8E83ZjJj8F8CPd1YHWgr335AjZdVk7EF";
        return MD5.getMD5("productId=" + examProductId + "&token=" + examToken +
                "&nonce_str=" + nonce_str + "&timestamp=" + mills);
    }

    //获取usersecret
    private String getUserSecret(String nonce_str, long mills) {
        //用户
        String userProductId = "product_user";
        String userToken = "rbGAVcJIABFft0FsIQL4wJtmY2LbQqKV";
        return MD5.getMD5("productId=" + userProductId + "&token=" + userToken +
                "&nonce_str=" + nonce_str + "&timestamp=" + mills);
    }
}
