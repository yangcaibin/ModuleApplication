package com.yanda.module_base.http;

/**
 * 作者：caibin
 * 时间：2017/11/6 17:09
 * 类说明：
 */

public class Address {
    //获取heard的键
    public static String HEADKEY = "domain";
    //主域名
    public static String HEADMAIN = "main";
    //用户
    public static String HEADUSER = "user";
    //商城
    public static String HEADSHOP = "shop";
    //考试
    public static String HEADEXAM = "exam";
    //主域名
    public static String HOST = "https://main.med163.net/app/";
    //商城
    public static String SHOPHOST = "https://shop.med163.net/app/";
    //用户
    public static String USERHOST = "https://user.med163.net/app/";
    //考试
    public static String EXAMHOST = "https://exam.%s.med163.net/app/";
    //测试主域名
//    public static String HOST = "http://main.test.med163.net/app/";
//    //测试商城
//    public static String SHOPHOST = "http://shop.test.med163.net/app/";
//    //测试用户
//    public static String USERHOST = "http://user.test.med163.net/app/";
//    //测试考试
//    public static String EXAMHOST = "http://exam.%s.test.med163.net/app/";
    //head头传的参数(imageLoader)
    public static String REFERERHEAD = "https://main.med163.net";
    //阿里云上传图片地址
    public static String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";
    // 访问图片
    public static String IMAGE_NET = "https://ytk-img.oss-cn-beijing.aliyuncs.com";
    //访问音频
    public static String AUDIO_NET = "https://ytk-video.oss-cn-beijing.aliyuncs.com";
    //下载数据库
    public static String DOWNLOADDB = "http://ytk-db.oss-cn-beijing.aliyuncs.com/";
    //排行榜活动规则  h5
    public static String FREQUENCYRULE = HOST + "get/topExplain";
    //库币攻略  h5
    public static String KUSTRATEGY = HOST + "get/vmRaiders";
    //商城详情页
    public static String SHOPDETAILS = SHOPHOST + "goods/content/%s";
    //关于我们
    public static String ABOUTWE = HOST + "get/aboutUs";
    //咨询的地址
    public static String CONSULTURL = "mqqwpa://im/chat?chat_type=wpa&uin=";
    //app分享的链接
    public static String APPSHARE = HOST + "skip/share?clientType=Android&project=%s";
    //工作机会
    public static String WORKCHANCE = HOST + "get/%sWorkChance";
    //考点狂背答疑
    public static String RECITEWEBVIEW = HOST + "get/%sAnswer";
    //图文消息详情
    public static String IMAGENOTICEDETAILS = USERHOST + "user/notice/content/";
    //学习打卡页面分享链接
    public static String STUDYCLOCKINSHARE = EXAMHOST + "user/plan/share?userId=%s";
    //集训专享A详情h5
    public static String TRAININGBCONTENT = EXAMHOST + "audioVip/content/";
    //直播 课程分享
    public static String SHARELINK = SHOPHOST + "get/share/link?otherId=%s&type=%s&project=%s";
    //题库哪分享
    public static String EXAMSHARELINK = EXAMHOST + "get/share/link?otherId=%s&type=%s&project=%s";
    //查看物流
    public static String LOOKLOGISTICS = SHOPHOST + "user/order/logistics?userId=%s&requestId=%s";
    //圈子的分享链接
    public static String CIRCLESHARE = USERHOST + "forum/share/info?id=";
    //模考排名分享
    public static String MOCKRANKINGSHARE = EXAMHOST + "paper/ranking/share?userId=%s&paperId=%s";
    //做题统计分享
    public static String STATISTICESHARE = EXAMHOST + "user/answer/info/share?subjectName=%s&" +
            "pointName=%s&duNum=%s&yesNum=%s&errorNum=%s&yesRate=%s&info=%s&userId=%s&dateTime=%s";
}
