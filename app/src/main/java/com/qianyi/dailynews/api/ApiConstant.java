package com.qianyi.dailynews.api;

/**
 * Created by Administrator on 2018/5/13.
 */

public class ApiConstant {
    public static final String APP_ID="wx263e7ba709980bc0";
    public static final String APP_SECRET="APP_SECRET";
    public static final String APP_KEY_WEIBO="2664184092";
    public static final String REDIRECT_URL = "http://www.sina.com";
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
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
    //召回徒弟列表
    public static final String CALL_BACK_FRIEND_LIST = BASE_URL+"/api/news/recallList";
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
    //发表热门评论
    public static final String  PUBLISH_NEWS_COMMENT= BASE_URL+"/api/news/setComment";
    //评论点赞
    public static final String  COMMENT_LIKE= BASE_URL+"/api/news/likeComment";
    //获取相关推荐
    public static final String  NEWS_WONDERFUL_REMMOND= BASE_URL+"/api/news/getWonderfulNews";
    //获取相一条新闻的详情
    public static final String  NEWS_ONE= BASE_URL+"/api/news/getCommentDetail";
    //获取新闻奖励数量
    public static final String  NEWS_REWARD= BASE_URL+"/api/news/getRewardsCnt";
    //阅读新闻
    public static final String  READ_NEWS= BASE_URL+"/api/news/readNews";
    //阅读新闻后获得奖励
    public static final String  GET_REWARD_AFTER_READ_NEWS= BASE_URL+"/api/news/getReward";
    //活动专区
    public static final String  ACTIVITY_ZONE= BASE_URL+"/api/newer/mission";
    //新手活动提交答案
    public static final String  GREEN_HAND_QUESTION= BASE_URL+"/api/answer/mission";
    //签到状态查询
    public static final String  SIGN_STATE= BASE_URL+"/api/sign/init";
    //==========辛振宇==========================================






































    //阅读新闻后获得奖励g
    public static final String  WRITE_CODE= BASE_URL+"/api/news/setInviteCode";
    //更新个人信息
    public static final String  GET_USER_INFO= BASE_URL+"/api/user/getUserInfo";




















































































































































































































































































}
