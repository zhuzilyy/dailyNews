package com.qianyi.dailynews.api;

/**
 * Created by Administrator on 2018/5/13.
 */

public class ApiConstant {
    public static final String BASE_URL="http://47.104.73.127:8080/news-0.0.1";
    public static final String PAGE_SIZE="10";
    public static final String SUCCESS_CODE="0000";
    public static final String CONFIRMCODE=BASE_URL+"/api/user/sendRegisterCode";
    public static final String REGISTER=BASE_URL+"/api/user/register";
    public static final String LOGIN=BASE_URL+"/api/user/login";
    public static final String VIDEO_LIST= BASE_URL+"/api/videos/getComment";
    //忘记密码发送验证码
    public static final String FORGETPWD_CONFIRM_CODE= BASE_URL+"/api/user/sendForgetCode";
    //修改密码
    public static final String UPDATE_PWD= BASE_URL+"/api/user/updatePasswd";
    //获取新闻title
    public static final String NEWS_TITLES = BASE_URL+"/api/news/types";
    //获取新闻条目
    public static final String NEWS_CONTENTS = BASE_URL+"/api/news/home";
    //邀请
    public static final String INVITE = BASE_URL+"/api/news/getBanner";
    //签到
    public static final String SIGN = BASE_URL+"/api/sign/do";
    //获取金币
    public static final String  GOLD_COIN= BASE_URL+"/api/account/detail/page";
    //提现
    public static final String  WITHDRAWAWAL= BASE_URL+"/api/account/withdraw/page";
    //视频详情
    public static final String  VIDEO_DETAIL= BASE_URL+"/api/videos/getWonderfulVideo";
    //邀请详情
    public static final String  INVITE_DETAIL= BASE_URL+"/api/news/getInviteInfo";
    //新闻热门评论
    public static final String  NEWS_COMMENT= BASE_URL+"/api/news/getComment";
    //==========辛振宇==========================================
























































































































































































































































































































}
