package com.yanda.module_base.http;

import androidx.annotation.NonNull;

import com.yanda.module_base.entity.ExamEntity;
import com.yanda.module_base.entity.PosterEntity;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 作者：caibin
 * 时间：2017/11/6 16:29
 * 类说明：接口地址的类
 */

public interface ApiService {
    //主域名
    String HEADMAIN = "domain:main";
    //用户
    String HEADUSER = "domain:user";
    //商城
    String HEADSHOP = "domain:shop";
    //考试
    String HEADEXAM = "domain:exam";

    //下载文件
    @Streaming
    @GET
    Observable<ResponseBody> downLoadFile(@NonNull @Url String url);

    //获取启动广告页
    @Headers(HEADMAIN)
    @POST("website/startImage{appType}")
    Observable<HttpResult<PosterEntity>> getPosterEntity(@Path("appType") String appType);

    //根据试题Id获取试题用户信息
    @Headers(HEADEXAM)
    @FormUrlEncoded
    @POST("question/user/data")
    Observable<HttpResult<Map<Long, ExamEntity>>> getQuestionIdsDataMethod(@FieldMap(encoded = true) Map<String, Object> map);

//    //获取专业
//    @Headers(HEADMAIN)
//    @FormUrlEncoded
//    @POST("profession/child/list")
//    Observable<HttpResult<Map<String, List<MajorEntity>>>> getMajorDataMethod(@FieldMap(encoded = true) Map<String, Object> map);
//
//    //获取数据库版本实体
//    @Headers(HEADMAIN)
//    @FormUrlEncoded
//    @POST("website/sql")
//    Observable<HttpResult<Map<String, String>>> getSqlVersionMapEntity(@FieldMap(encoded = true) Map<String, Object> map);
//
//    //提交用户离线做题的信息
//    @Headers(HEADEXAM)
//    @FormUrlEncoded
//    @POST("user/data/submit")
//    Observable<HttpResult<String>> submitUserDataMethod(@FieldMap(encoded = true) Map<String, Object> map);
//
//    //切换身份获取要更新的数据
//    @Headers(HEADEXAM)
//    @FormUrlEncoded
//    @POST("get/user/data")
//    Observable<HttpResult<LoginRegisterEntity>> getIdentityDataMethod(@FieldMap(encoded = true) Map<String, Object> map);
//
//    //检查app版本
//    @Headers(HEADMAIN)
//    @FormUrlEncoded
//    @POST("website/app{appType}")
//    Observable<HttpResult<AppVersionEntity>> getAppVersionEntity(@Path("appType") String appType,
//                                                                 @FieldMap(encoded = true) Map<String, Object> map);
//    //未读消息
//    @Headers(HEADUSER)
//    @FormUrlEncoded
//    @POST("notice/num")
//    Observable<HttpResult<MessageEntity>> getMessageEntity(@FieldMap(encoded = true) Map<String, Object> map);
//
//    //获取今日是否有课
//    @Headers(HEADSHOP)
//    @FormUrlEncoded
//    @POST("course/today/have")
//    Observable<HttpResult<Boolean>> getIsTodayCourseMethod(@FieldMap(encoded = true) Map<String, Object> map);
//
//    //获取分享锁
//    @Headers(HEADEXAM)
//    @FormUrlEncoded
//    @POST("exam/subject/lock/list")
//    Observable<HttpResult<Object>> getShareLockMethod(@FieldMap(encoded = true) Map<String, Object> map);
//
//    //获取题单详情
//    @Headers(HEADEXAM)
//    @FormUrlEncoded
//    @POST("check/question/form")
//    Observable<HttpResult<TiDanEntity>> getTiDanDetailsMethod(@FieldMap(encoded = true) Map<String, Object> map);
//
//    //获取首页提示图
//    @Headers(HEADMAIN)
//    @FormUrlEncoded
//    @POST("website/promptImage{appType}")
//    Observable<HttpResult<PosterEntity>> getPromptImageMethod(@Path("appType") String appType,
//                                                              @FieldMap(encoded = true) Map<String, Object> map);
//
//    //获取导航广告图
//    @Headers(HEADMAIN)
//    @FormUrlEncoded
//    @POST("activityNav/query")
//    Observable<HttpResult<List<PosterEntity>>> getNavigationBanner(@FieldMap(encoded = true) Map<String, Object> map);
//
//    //获取题库导航
//    @Headers(HEADEXAM)
//    @FormUrlEncoded
//    @POST("examNavigate/query")
//    Observable<HttpResult<List<QuestionNavigationEntity>>> getQuestionNavigationMethod(@FieldMap(encoded = true) Map<String, Object> map);
}