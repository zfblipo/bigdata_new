package com.xingnext.bigdata.utils;

/**
 * Created by lipo on 2017/4/8.
 */
public class MyUrl {

//    public static String key = "https://api.shaobing.jikegouwu.com/";
    public static String key = "https://api.jumi.xingnext.com/";

    public static String getVersion = key+"startup";//系统信息
    public static String refreshToken = key+"token/refresh";//系统信息

    /**
     *  比赛
     */
    public static String getMatchList = key+"match";//比赛预测列表
    public static String getHotList = key+"market";//红单推荐列表
    public static String getHotDetail = key+"market/detail/";//红单推荐详情
    public static String getplanDetail = key+"match/detail/";//比赛预测详情
    public static String getHistoryList = key+"history/date/";//历史战绩列表
    public static String getSampleList = key+"match/sample/";//分析样本

    public static String noticeList = key+"notice/market";//公告接口
    public static String favoriteList = key+"match/favorite";//公告接口

    /**
     * 注册登录
     */
    public static String smsUrl = key+"sms/send";//发送短信验证码
    public static String loginUrl = key+"user/login";//登录
    public static String registUrl = key+"user/register";//注册
    public static String forgetPswUrl = key+"user/forget";//忘记密码
    public static String userUrl = key+"user/profile";//用户个人资料

    /**
     * 我的
     */
    public static String orderUrl = key+"order";//我的订阅
    public static String orderDetailUrl = key+"order/detail/";//订单详情
    public static String billUrl = key+"user/bill";//账户明细
    public static String chargeUrl = key+"charge";//账户明细

    public static String orderPay = key+"order/check";//支付

    /**
     * web
     */
    public static String key_web = "https://jumi.xingnext.com/";
    public static String chargeWebUrl = key_web+"charge";//兑换
    public static String aboutWebUrl = key_web+"help/index.html";//帮助
    public static String gifWebUrl = key_web+"app/luckywheel";//抽奖
    public static String checkWebUrl = key_web+"order/check";//支付
    public static String couponWebUrl = key_web+"coupon";//优惠券
    public static String profitWebUrl = key_web+"plan";


}
