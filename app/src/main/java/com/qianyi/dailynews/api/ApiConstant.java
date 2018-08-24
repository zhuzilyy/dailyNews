package com.qianyi.dailynews.api;

/**
 * Created by Administrator on 2018/5/13.
 */

public class ApiConstant {
    public static final String APP_ID="wx263e7ba709980bc0";
    public static final String APP_SECRET="06f90194e860b537c6e23985894ad81c";
    public static final String APP_KEY_WEIBO="2664184092";
    public static final String REDIRECT_URL = "http://www.sina.com";
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    public static final String BASE_URL="http://mrsb.cqlianbei.com/";
    public static final String DAILY_SHARE_URL=BASE_URL+"share/everyday/info.html?id=";
    public static final String DOWN_SHARE_URL=BASE_URL+"download/download.html";
    public static final String LIANJIE_URL=BASE_URL+"download/download.html";
    public static final String INCOME_SHOW=BASE_URL+"share/index.html?id=";
    public static final String QQ_SHARE_LOGO=BASE_URL+"share/share_logo.png";
    public static String SHARE_TAG="";

    public static final String PAGE_SIZE="10";
    public static final String SUCCESS_CODE="0000";
    public static final String SUCCESS="SUCCESS";
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
    //修改密码
    public static final String  MODIFY_PWD= BASE_URL+"/api/user/updatePasswd2";
    //徒弟列表
    public static final String  TUDI_LIST= BASE_URL+"/api/news/tudiList";
    //每日分享前调用
    public static final String  SHARE_PRE= BASE_URL+"/api/share/init";
    public static final String  SHARE_DAILYSHARE_PRE= BASE_URL+"/api/dayshare/init";
    //每日分享后调用
    public static final String  SHARE_AFTER= BASE_URL+"/api/share/success";
    public static final String  SHARE_DAILYSHARE_AFTER= BASE_URL+"/api/dayshare/success";
    //晒收入
    public static final String  ISHARE_INCOME= BASE_URL+"/api/show/income";
    //高额返利列表
    public static final String  FANLI_LIST= BASE_URL+"/api/makeMoney/getList";
    //高额返利任务列表
    public static final String  FANLI_TASK_LIST= BASE_URL+"/api/makeMoney/makeMoneyTask";
    //轻松赚钱
    public static final String  MAKE_MONEY= BASE_URL+"/api/makeMoney/easyMoneyList";
    //轻松赚钱任务列表
    public static final String  MAKE_MONEY_LIST= BASE_URL+"/api/makeMoney/easyMoneyTask";
    //热词
    public static final String  HOT_WORD= BASE_URL+"/api/news/keywords";
    //更换热词
    public static final String  CHANGE_HOT_WORD= BASE_URL+"/api/news/nextkeywords";
    //绑定微信
    public static final String  WX_BIND= BASE_URL+"/api/wx/bind";
    //新手任务热词搜索
    public static final String  MISSSION_READ= BASE_URL+"/api/newer/search";
    //获取用户信息
    public static final String  GET_USERINFO= BASE_URL+"/api/user/getUserInfo";
    //找回徒弟
    public static final String  RECALL= BASE_URL+"/api/news/recall";
    //增加播放量
    public static final String  ADD_WATCH_NUM= BASE_URL+"/api/videos/addAmountOfPlay";
    //查询可提现金额
    public static final String  WITHDRAWAL_MONEY= BASE_URL+"/api/withdraw/info";
    //确认提现
    public static final String  DO_WITHDRAWAL= BASE_URL+"/api/withdraw/do";
    //提现记录
    public static final String  WITHDRAWAL_RECORD= BASE_URL+"/api/withdraw/detail/page";
    //Webview
    public static final String  WEBVIEW= BASE_URL+"/api/user/getOfficial";
    //日常任务状态
    public static final String  DAILY_MISSION= BASE_URL+"/api/daymission/state";
    //日常任务分享
    public static final String  DAILY_MISSION_SHARE= BASE_URL+"/api/daymission/show";
    //
    public static final String  BIND_PHONE= BASE_URL+"/api/wx/bindphone";
    //消息分页
    public static final String  MESSAGE_LIST= BASE_URL+"/api/message/page";
    //消息红点
    public static final String  MESSAGE_DOT= BASE_URL+"/api/message/redot";
    //微信发送验证码
    public static final String  WX_CONFIRM_CODE= BASE_URL+"/api/wx/sendCaptcha";
    /*//邀请规则
    public static final String  INVITE_WEBVIEW= BASE_URL+"/api/user/getOfficial?type=INVITE";
    //签到规则
    public static final String  SIGN_IN= BASE_URL+"/api/user/getOfficial?type=SIGN_IN";
    //赚钱攻略
    public static final String  MAKEMONEY= BASE_URL+"/api/user/getOfficial?type=MAKEMONEY";
    //帮助与反馈
    public static final String  HELP= BASE_URL+"/api/user/getOfficial?type=HELP";*/
    //==========辛振宇==========================================
























    //阅读新闻后获得奖励g
    public static final String  WRITE_CODE= BASE_URL+"/api/news/setInviteCode";
    //更新个人信息
    public static final String  GET_USER_INFO= BASE_URL+"/api/user/getUserInfo";
    //高额返利
    public static final String  HIGH_BACK_MONEY= BASE_URL+"/api/makeMoney/getList";
    //高额返利详情
    public static final String  HIGH_BACK_MONEY_DETAILS= BASE_URL+"/api/makeMoney/detail";
    //微信登录
    public static final String  WECHAT_LOGIN= BASE_URL+"/api/wx/login";

    //上传图片（file）
    public static final String  UPLOAD_PICS= BASE_URL+"/api/makeMoney/uploadImage";
    //上传图片2
    public static final String  SAVE_PICS= BASE_URL+"/api/makeMoney/saveImgs";
    //截图事例
    public static final String  getMakeMoneyExample= BASE_URL+"/api/makeMoney/getMakeMoneyExample";

    //分享之前
    public static final String  easyMoneyShare= BASE_URL+"/api/makeMoney/easyMoneyShare";
    //分享拼接
    public static final String  ShareUrl= "http://47.104.73.127:8080/share/esmy.html?token=";
    //分享拼接
    public static final String  takePartInUrl= BASE_URL+"/api/makeMoney/takePartIn";
    //删除新闻
    public static final String  deleteNews= BASE_URL+"/api/news/removeNews";
    //增加阅读次数
    public static final String  addViewCount= BASE_URL+"/api/news/addViewCount";

























































































































































































































































































}
