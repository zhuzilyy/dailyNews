package com.qianyi.dailynews.ui.Mine.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiAccount;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.ui.Mine.bean.SignBean;
import com.qianyi.dailynews.ui.invitation.activity.DailySharingAcitity;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.WhiteBgBitmapUtil;
import com.qianyi.dailynews.wxapi.WXEntryActivity;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.statistic.WBAgent;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/3.
 */

public class TaskCenterActivity extends BaseActivity implements View.OnClickListener ,WbShareCallback {
    //微信绑定
    @BindView(R.id.ll_bandwx001) public LinearLayout ll_bandwx001;
    @BindView(R.id.ll_bandwx002) public LinearLayout ll_bandwx002;
    //一元提现
    @BindView(R.id.ll_oneYuan001) public LinearLayout ll_oneYuan001;
    @BindView(R.id.ll_oneYuan002) public LinearLayout ll_oneYuan002;
    //问卷调查
    @BindView(R.id.ll_Questionnaire001) public LinearLayout ll_Questionnaire001;
    @BindView(R.id.ll_Questionnaire002) public LinearLayout ll_Questionnaire002;
    //答题奖励
    @BindView(R.id.ll_answerAward001) public LinearLayout ll_answerAward001;
    @BindView(R.id.ll_answerAward002) public LinearLayout ll_answerAward002;
    //邀请好友
    @BindView(R.id.ll_inviteFriends001) public LinearLayout ll_inviteFriends001;
    @BindView(R.id.ll_inviteFriends002) public LinearLayout ll_inviteFriends002;
    //阅读奖励
    @BindView(R.id.ll_readingAward001) public LinearLayout ll_readingAward001;
    @BindView(R.id.ll_readingAward002) public LinearLayout ll_readingAward002;
    //分享奖励
    @BindView(R.id.ll_shareRewards001) public LinearLayout ll_shareRewards001;
    @BindView(R.id.ll_shareRewards002) public LinearLayout ll_shareRewards002;
    //晒收入
    @BindView(R.id.ll_sunIncome001) public LinearLayout ll_sunIncome001;
    @BindView(R.id.ll_sunIncome002) public LinearLayout ll_sunIncome002;
    //评论奖励
    @BindView(R.id.ll_commentAward001) public LinearLayout ll_commentAward001;
    @BindView(R.id.ll_commentAward002) public LinearLayout ll_commentAward002;

    //签到第一天
    @BindView(R.id.firstDay_already_ll) public LinearLayout firstDay_already_ll;
    @BindView(R.id.firstDay_none_ll) public LinearLayout firstDay_none_ll;
    //签到第2天
    @BindView(R.id.secondDay_none_ll) public LinearLayout secondDay_none_ll;
    @BindView(R.id.secondDay_already_ll) public LinearLayout secondDay_already_ll;
    //签到第3天
    @BindView(R.id.thirdDay_already_ll) public LinearLayout thirdDay_already_ll;
    @BindView(R.id.thirdDay_none_ll) public LinearLayout thirdDay_none_ll;
    //签到第4天
    @BindView(R.id.fourthDay_already_ll) public LinearLayout fourthDay_already_ll;
    @BindView(R.id.fourthDay_none_ll) public LinearLayout fourthDay_none_ll;
    //签到第5天
    @BindView(R.id.fifthDay_already_ll) public LinearLayout fifthDay_already_ll;
    @BindView(R.id.fifthDay_none_ll) public LinearLayout fifthDay_none_ll;
    //签到第6天
    @BindView(R.id.sixthDay_already_ll) public LinearLayout sixthDay_already_ll;
    @BindView(R.id.sixthDay_none_ll) public LinearLayout sixthDay_none_ll;
    //签到第7天
    @BindView(R.id.seventhDay_already_ll) public LinearLayout seventhDay_already_ll;
    @BindView(R.id.seventhDay_none_ll) public LinearLayout seventhDay_none_ll;

    @BindView(R.id.ll_taskCenter) public LinearLayout ll_taskCenter;


    //赚钱攻略
    @BindView(R.id.tv_ProfitMakingStrategy) public TextView tv_ProfitMakingStrategy;
    //签到规则
    @BindView(R.id.tv_SignInRules) public TextView tv_SignInRules;
    @BindView(R.id.btn_sign) public Button btn_sign;
    @BindView(R.id.btn_readingAward) public Button btn_readingAward;
    @BindView(R.id.btn_sunincome) public TextView btn_sunincome;
    @BindView(R.id.btn_commentAward) public TextView btn_commentAward;
    //日常任务
    @BindView(R.id.btn_inviteFriends) public TextView btn_inviteFriends;
    @BindView(R.id.btn_share) public TextView btn_share;


    @BindView(R.id.back) public ImageView back;
    private List<LinearLayout> OtherLineralayout=new ArrayList<>();
    private String userId;
    private List<LinearLayout> signedDays,unsignDays;
    private boolean signed;

    private MyReceiver myReceiver;

    private CustomLoadingDialog customLoadingDialog;
    private String openid, unionid, nickname, headimgurl,language,city,province,country,user_id;
    private int sex;
    //新手任务
    @BindView(R.id.btn_bandwx) public Button btn_bandwx;
    @BindView(R.id.btn_oneyuan) public TextView btn_oneyuan;
    @BindView(R.id.btn_answerAward) public TextView btn_answerAward;
    @BindView(R.id.btn_Questionnaire) public TextView btn_Questionnaire;
    private String mission1,mission2,mission3,mission4,dailyMission1,dailyMission2,dailyMission3,dailyMission4,dailyMission5;
    private PopupWindow pw_share;
    private View view_share;
    public LinearLayout ll_friendCircle;//分享到盆友圈
    public LinearLayout ll_QQ;//分享到QQ
    public LinearLayout ll_wechat;//分享到微信
    public LinearLayout ll_weibo;//分享到微博
    private IWXAPI mWxApi;
    private WbShareHandler shareHandler;
    private boolean oneYuan;
    private static final String APP_ID = "101488066"; //获取的APPID
    private Tencent mTencent;
    @Override
    protected void initViews() {
        //传入参数APPID
        mTencent = Tencent.createInstance(APP_ID, getApplicationContext());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userId= (String) SPUtils.get(TaskCenterActivity.this,"user_id","");
        oneYuan= (boolean) SPUtils.get(TaskCenterActivity.this,"oneyuan",false);
        customLoadingDialog=new CustomLoadingDialog(this);
        signedDays=new ArrayList<>();
        unsignDays=new ArrayList<>();
        signedDays.add(firstDay_already_ll);
        signedDays.add(secondDay_already_ll);
        signedDays.add(thirdDay_already_ll);
        signedDays.add(fourthDay_already_ll);
        signedDays.add(fifthDay_already_ll);
        signedDays.add(sixthDay_already_ll);
        signedDays.add(secondDay_already_ll);

        unsignDays.add(firstDay_none_ll);
        unsignDays.add(secondDay_none_ll);
        unsignDays.add(thirdDay_none_ll);
        unsignDays.add(fourthDay_none_ll);
        unsignDays.add(fifthDay_none_ll);
        unsignDays.add(sixthDay_none_ll);
        unsignDays.add(seventhDay_none_ll);

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.action.wechat");
        registerReceiver(myReceiver, intentFilter);

        IntentFilter intentFilterUpdateMission = new IntentFilter();
        intentFilterUpdateMission.addAction("com.action.update.mission");
        registerReceiver(myReceiver, intentFilterUpdateMission);

        IntentFilter intentFilterDailyShare= new IntentFilter();
        intentFilterDailyShare.addAction("com.action.share.success");
        registerReceiver(myReceiver, intentFilterDailyShare);


        view_share = LayoutInflater.from(TaskCenterActivity.this).inflate(R.layout.pw_share, null);
        ll_friendCircle=view_share.findViewById(R.id.ll_friendCircle);
        ll_QQ=view_share.findViewById(R.id.ll_QQ);
        ll_wechat=view_share.findViewById(R.id.ll_wechat);
        ll_weibo=view_share.findViewById(R.id.ll_weibo);
        ll_friendCircle.setOnClickListener(this);
        ll_QQ.setOnClickListener(this);
        ll_wechat.setOnClickListener(this);
        ll_weibo.setOnClickListener(this);

        mWxApi = WXAPIFactory.createWXAPI(this, ApiConstant.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(ApiConstant.APP_ID);

    }
    @Override
    protected void initData() {
        //将点击显示的条目添加进集合
        OtherLineralayout.add(ll_bandwx002);
        OtherLineralayout.add(ll_oneYuan002);
        OtherLineralayout.add(ll_Questionnaire002);
        OtherLineralayout.add(ll_answerAward002);
        OtherLineralayout.add(ll_inviteFriends002);
        OtherLineralayout.add(ll_readingAward002);
        OtherLineralayout.add(ll_shareRewards002);
        OtherLineralayout.add(ll_sunIncome002);
        OtherLineralayout.add(ll_commentAward002);
        customLoadingDialog.show();
        getSignState();
        getDailyMissionState();
        getUesrInfo();
    }
    //获取日常任务的状态
    private void getDailyMissionState() {
        ApiMine.dailyMission(ApiConstant.DAILY_MISSION, user_id, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String newer_mission = jsonObject.getString("data");
                    String[] missionArr=newer_mission.split("\\|");
                    dailyMission1 = missionArr[0];
                    dailyMission2 = missionArr[1];
                    dailyMission3 = missionArr[2];
                    dailyMission4 = missionArr[3];
                    dailyMission5 = missionArr[4];
                    if (!dailyMission1.equals("0")){
                        btn_inviteFriends.setText("已完成");
                        btn_inviteFriends.setBackgroundResource(R.drawable.new_mission_finish);
                    }
                    if (!dailyMission2.equals("0")){
                        btn_readingAward.setText("已完成");
                        btn_readingAward.setBackgroundResource(R.drawable.new_mission_finish);
                    }
                    if (!dailyMission3.equals("0")){
                        btn_share.setText("已完成");
                        btn_share.setBackgroundResource(R.drawable.new_mission_finish);
                    }
                    if (!dailyMission4.equals("0")){
                        btn_sunincome.setText("已完成");
                        btn_sunincome.setBackgroundResource(R.drawable.new_mission_finish);
                    }
                    if (!dailyMission5.equals("0")){
                        btn_commentAward.setText("已完成");
                        btn_commentAward.setBackgroundResource(R.drawable.new_mission_finish);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }
    //获取新手任务状态
    private void getUesrInfo() {
        user_id= (String) SPUtils.get(TaskCenterActivity.this,"user_id","");
        ApiMine.getUserInfo(ApiConstant.GET_USERINFO, user_id, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String newer_mission = data.getString("newer_mission");
                    String[] missionArr=newer_mission.split("\\|");
                    mission1 = missionArr[0];
                    mission2 = missionArr[1];
                    mission3 = missionArr[2];
                    mission4 = missionArr[3];
                    if (!mission1.equals("0")){
                        btn_bandwx.setText("已完成");
                        btn_bandwx.setBackgroundResource(R.drawable.new_mission_finish);
                    }
                    if (!mission2.equals("0")||oneYuan){
                        btn_oneyuan.setText("已完成");
                        btn_oneyuan.setBackgroundResource(R.drawable.new_mission_finish);
                    }
                    if (!mission3.equals("0")){
                        btn_Questionnaire.setText("已完成");
                        btn_Questionnaire.setBackgroundResource(R.drawable.new_mission_finish);
                    }
                    if (!mission4.equals("0")){
                        btn_answerAward.setText("已完成");
                        btn_answerAward.setBackgroundResource(R.drawable.new_mission_finish);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }
    private void getSignState() {
        user_id= (String) SPUtils.get(TaskCenterActivity.this,"user_id","");
        ApiMine.signState(ApiConstant.SIGN_STATE, user_id, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response,final String s) {
                customLoadingDialog.dismiss();
                Gson gson=new Gson();
                SignBean signBean = gson.fromJson(s, SignBean.class);
                String countinuous = signBean.getData().getCountinuous();
                signed= signBean.getData().isSigned();
                if (signed){
                    btn_sign.setText("明天再来领取金币吧");
                }
                int signDay = Integer.parseInt(countinuous);
                for (int i = 0; i <signDay; i++) {
                    if (i<signDay){
                        signedDays.get(i).setVisibility(View.GONE);
                        unsignDays.get(i).setVisibility(View.VISIBLE);
                    }else{
                        signedDays.get(i).setVisibility(View.VISIBLE);
                        unsignDays.get(i).setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
            }
        });
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_taskcenter);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.ll_bandwx001,R.id.ll_oneYuan001,R.id.ll_Questionnaire001,R.id.ll_answerAward001,
            R.id.ll_inviteFriends001,R.id.ll_readingAward001,R.id.ll_shareRewards001,R.id.ll_sunIncome001,R.id.ll_commentAward001,
    R.id.btn_bandwx,R.id.btn_oneyuan,R.id.btn_Questionnaire,R.id.btn_answerAward,R.id.btn_inviteFriends,
            R.id.btn_readingAward,R.id.btn_share,R.id.btn_sunincome,R.id.btn_commentAward,
    R.id.tv_ProfitMakingStrategy,R.id.tv_SignInRules,R.id.btn_sign})
    @Override
    public void onClick(View view) {
        Intent intent=null;
        switch(view.getId()){
            case R.id.tv_ProfitMakingStrategy:
                //赚钱攻略
                Intent intent_profitMakeingStrategy  = new Intent(TaskCenterActivity.this, MakeMoneyActivity.class);
                /*intent_profitMakeingStrategy.putExtra("title","赚钱攻略");
                intent_profitMakeingStrategy.putExtra("utl","http://www.baidu.com");*/
                startActivity(intent_profitMakeingStrategy);
                break;
            case R.id.tv_SignInRules:
                //签到规则
                Intent intent_SignInRules  = new Intent(TaskCenterActivity.this, SignRuleActivity.class);
                intent_SignInRules.putExtra("title","签到规则");
                intent_SignInRules.putExtra("utl","http://www.qq.com");
                startActivity(intent_SignInRules);
                break;
            case R.id.ll_bandwx001:
                //绑定微信
                if(ll_bandwx002.isShown()){
                    ll_bandwx002.setVisibility(View.GONE);
                }else {
                    ll_bandwx002.setVisibility(View.VISIBLE);
                }
                closeOtherAll(ll_bandwx002);
            break;
            case R.id.ll_oneYuan001:
                //一元提现
                if(ll_oneYuan002.isShown()){
                    ll_oneYuan002.setVisibility(View.GONE);
                }else {
                    ll_oneYuan002.setVisibility(View.VISIBLE);
                }
                closeOtherAll(ll_oneYuan002);
                break;
            case R.id.ll_Questionnaire001:
                //问卷调查
                if(ll_Questionnaire002.isShown()){
                    ll_Questionnaire002.setVisibility(View.GONE);
                }else {
                    ll_Questionnaire002.setVisibility(View.VISIBLE);
                }
                closeOtherAll(ll_Questionnaire002);
                break;
            case R.id.ll_answerAward001:
                //答题奖励
                if(ll_answerAward002.isShown()){
                    ll_answerAward002.setVisibility(View.GONE);
                }else {
                    ll_answerAward002.setVisibility(View.VISIBLE);
                }
                closeOtherAll(ll_answerAward002);
                break;
            case R.id.ll_inviteFriends001:
                //邀请好友
                if(ll_inviteFriends002.isShown()){
                    ll_inviteFriends002.setVisibility(View.GONE);
                }else {
                    ll_inviteFriends002.setVisibility(View.VISIBLE);
                }
                closeOtherAll(ll_inviteFriends002);
                break;
            case R.id.ll_readingAward001:
                //阅读奖励
                if(ll_readingAward002.isShown()){
                    ll_readingAward002.setVisibility(View.GONE);
                }else {
                    ll_readingAward002.setVisibility(View.VISIBLE);
                }
                closeOtherAll(ll_readingAward002);
                break;
            case R.id.ll_shareRewards001:
                //分享奖励
                if(ll_shareRewards002.isShown()){
                    ll_shareRewards002.setVisibility(View.GONE);
                }else {
                    ll_shareRewards002.setVisibility(View.VISIBLE);
                }
                closeOtherAll(ll_shareRewards002);
                break;
            case R.id.ll_sunIncome001:
                //晒收入
                if(ll_sunIncome002.isShown()){
                    ll_sunIncome002.setVisibility(View.GONE);
                }else {
                    ll_sunIncome002.setVisibility(View.VISIBLE);
                }
                closeOtherAll(ll_sunIncome002);
                break;
            case R.id.ll_commentAward001:
                //评论奖励
                if(ll_commentAward002.isShown()){
                    ll_commentAward002.setVisibility(View.GONE);
                }else {
                    ll_commentAward002.setVisibility(View.VISIBLE);
                }
                closeOtherAll(ll_commentAward002);
                break;
            case R.id.btn_bandwx:
                if (!mission1.equals("0")){
                    return;
                }
                //去绑定微信
                initWx();
                break;
            case R.id.btn_oneyuan:
                if (!mission2.equals("0")||oneYuan){
                    return;
                }
                //去完成提现
                jumpActivity(this,ActivityZoneActivity.class);
                break;
            case R.id.btn_Questionnaire:
                if (!mission3.equals("0")){
                    return;
                }
                jumpActivity(this,QuestionSurveyActivity.class);
                //去调查
                break;
            case R.id.btn_answerAward:
                if (!mission4.equals("0")){
                    return;
                }
                //去答题
                jumpActivity(this,GreenHandsGuideActivity.class);
                break;
            case R.id.btn_inviteFriends:
                //去邀请
                intent=new Intent();
                intent.setAction("com.action.invite");
                sendBroadcast(intent);
                finish();
                break;
            case R.id.btn_readingAward:
                if (dailyMission2.equalsIgnoreCase("200")){
                    return;
                }
                //去阅读
                intent=new Intent();
                intent.setAction("com.action.read");
                sendBroadcast(intent);
                finish();
                break;
            case R.id.btn_share:
                //去分享
                intent=new Intent();
                intent.setAction("com.action.read");
                sendBroadcast(intent);
                finish();
                break;
            case R.id.btn_sunincome:
                if (!dailyMission4.equals("0")){
                    return;
                }
                shwoSharePw();

                //晒收入
               //jumpActivity(TaskCenterActivity.this, IncomeShowActivity.class);
                break;
            case R.id.btn_commentAward:
                if (!dailyMission5.equals("0")){
                    return;
                }
                //去评论
                //去邀请
                intent=new Intent();
                intent.setAction("com.action.comment");
                sendBroadcast(intent);
                finish();
                //Toast.makeText(this, "去评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_sign:
                if (signed){
                    Toast.makeText(this, "已签到", Toast.LENGTH_SHORT).show();
                    return;
                }
                sign();
                break;
            case R.id.ll_wechat:
                //微信分享
                //Toast.makeText(this, "微信分享", Toast.LENGTH_SHORT).show();
                ApiConstant.SHARE_TAG="taskCenterShare";
                shareFriends();
                pw_share.dismiss();
                break;
            case R.id.ll_friendCircle:
                ApiConstant.SHARE_TAG="taskCenterShare";
                shareFriendCircle();
                pw_share.dismiss();
                //朋友圈分享
                //Toast.makeText(this, "朋友圈分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_QQ:
                shareQQ();
                pw_share.dismiss();
                //QQ分享
                //Toast.makeText(this, "QQ分享", Toast.LENGTH_SHORT).show();
              /*  shareFriends();
                pw_share.dismiss();*/
                break;
            case R.id.ll_weibo:
                //微博分享
                //Toast.makeText(this, "微博分享", Toast.LENGTH_SHORT).show();
                shareWeiBo();
                pw_share.dismiss();
                break;
        }
    }
    //QQ分享
    private void shareQQ() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "每日速报");//分享标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,"任务中心的晒收入分享");//要分享的内容摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,ApiConstant.DAILY_SHARE_URL+userId);//内容地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,ApiConstant.QQ_SHARE_LOGO);//分享的图片URL
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "每日速报");//应用名称
        mTencent.shareToQQ(this, params, new ShareUiListener());
    }
    //微博分享的回调
    @Override
    public void onWbShareSuccess() {
        dialyShareSuccess();
    }
    @Override
    public void onWbShareCancel() {

    }

    @Override
    public void onWbShareFail() {

    }

    /**
     * 自定义监听器实现IUiListener，需要3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class ShareUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            //分享成功
            dialyShareSuccess();
        }
        @Override
        public void onError(UiError uiError) {
            //分享失败

        }
        @Override
        public void onCancel() {
            //分享取消

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode,resultCode,data,new ShareUiListener());
    }
    private void dialyShareSuccess() {
        userId= (String) SPUtils.get(TaskCenterActivity.this,"user_id","");
        ApiMine.dailyMissionShare(ApiConstant.DAILY_MISSION_SHARE, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String code = jsonObject.getString("code");
                    if (code.equals(ApiConstant.SUCCESS_CODE)){
                        Intent intent=new Intent();
                        intent.setAction("com.action.share.success");
                        sendBroadcast(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }


    private void shareFriendCircle() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ApiConstant.DAILY_SHARE_URL+userId;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "每日速报";
        msg.description = "任务中心的晒收入分享";
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(this, bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        msg.setThumbImage(thumbBmp);
        bmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        // req.scene = sendtype==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        mWxApi.sendReq(req);
    }
    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    //分享到微博
    private void shareWeiBo() {
        initLog();
        //startActivity(new Intent(getActivity(), WBAuthActivity.class));
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();
        // sendMessage(true, true);
        shareWebPage();
    }
    private void shareWebPage() {
      /*  WebpageObject mediaObj =new WebpageObject();
        //创建文本消息对象
        TextObject textObject =new TextObject();
        textObject.text= "你分享内容的描述"+"分享网页的话加上网络地址";

        textObject.title= "哈哈哈哈哈哈";

        //创建图片消息对象，如果只分享文字和网页就不用加图片

        WeiboMultiMessage message =new WeiboMultiMessage();

        ImageObject imageObject =new ImageObject();

        // 设置 Bitmap 类型的图片到视频对象里        设置缩略图。 注意：最终压缩过的缩略图大小 不得超过 32kb。

        Bitmap bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.test);

        imageObject.setImageObject(bitmap);

        message.textObject= textObject;

        message.imageObject= imageObject;

        message.mediaObject= mediaObj;*/


        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = "每日速报";
        mediaObject.description = "任务中心的晒收入分享";
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);

        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = ApiConstant.DAILY_SHARE_URL+userId;
        mediaObject.defaultText = "任务中心的晒收入分享";
        WeiboMultiMessage message = new WeiboMultiMessage();
        message.mediaObject = mediaObject;
        shareHandler.shareMessage(message, false);
    }
    private void initLog() {
        WBAgent.setAppKey(ApiConstant.APP_KEY_WEIBO);
        WBAgent.setChannel("weibo"); //这个是统计这个app 是从哪一个平台down下来的  百度手机助手
        WBAgent.openActivityDurationTrack(false);
        try {
            //设置发送时间间隔 需大于90s小于8小时
            WBAgent.setUploadInterval(91000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = "测试title";
        mediaObject.description = "测试描述";
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);

        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = ApiConstant.DAILY_SHARE_URL;
        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }

    //分享到微信
    private void shareFriends() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ApiConstant.DAILY_SHARE_URL+userId;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "每日速报";
        msg.description = "任务中心的晒收入分享";
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(this, bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        msg.setThumbImage(thumbBmp);
        bmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        // req.scene = sendtype==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        mWxApi.sendReq(req);
    }

    private void shwoSharePw() {
        pw_share = new PopupWindow(TaskCenterActivity.this);
        pw_share.setContentView(view_share);
        pw_share.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pw_share.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pw_share.setTouchable(true);
        pw_share.setFocusable(true);
        pw_share.setBackgroundDrawable(new BitmapDrawable());
        pw_share.setAnimationStyle(R.style.AnimBottom);
        pw_share.showAtLocation(ll_taskCenter, Gravity.BOTTOM, 0, 0);
        // 设置pw弹出时候的背景颜色的变化
        backgroundAlpha(0.5f);
        pw_share.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
    /***
     * 微信登录
     */
    private void initWx() {
        IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, ApiConstant.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(ApiConstant.APP_ID);
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        mWxApi.sendReq(req);
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.action.wechat")) {
                openid = intent.getStringExtra("openid");
                nickname = intent.getStringExtra("nickname");
                sex = intent.getIntExtra("sex", 0);
                language = intent.getStringExtra("language");
                city = intent.getStringExtra("city");
                province = intent.getStringExtra("province");
                country = intent.getStringExtra("country");
                headimgurl = intent.getStringExtra("headimgurl");
                unionid = intent.getStringExtra("unionid");
                bindWx();
            }else if(action.equals("com.action.update.mission")){
               getUesrInfo();
            }else if(action.equals("com.action.share.success")){
                getDailyMissionState();
            }
        }
    }
    private void bindWx() {
        customLoadingDialog.show();
        ApiAccount.wechatBind(ApiConstant.WX_BIND, user_id,openid, nickname,sex+"",language,city,province,country,headimgurl,unionid,new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String return_msg = jsonObject.getString("return_msg");
                    Toast.makeText(TaskCenterActivity.this, return_msg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("tag",e.getMessage());
                customLoadingDialog.dismiss();
            }
        });


    }
    //签到
    private void sign() {
        customLoadingDialog.show();
        ApiMine.sign(ApiConstant.SIGN, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("tag",s);
                        Gson gson=new Gson();
                        SignBean signBean = gson.fromJson(s, SignBean.class);
                        String message = signBean.getData().getMessage();
                        if (TextUtils.isEmpty(message)){
                            Toast.makeText(TaskCenterActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        btn_sign.setText("明天再来领取金币吧");
                        Intent intent=new Intent();
                        intent.setAction("com.action.sign.success");
                        sendBroadcast(intent);
                        getSignState();
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                Toast.makeText(TaskCenterActivity.this, "网络错误签到失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void closeOtherAll(LinearLayout ll){
        for (int i = 0; i <OtherLineralayout.size() ; i++) {
            if(ll!=OtherLineralayout.get(i)){
                OtherLineralayout.get(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myReceiver!=null){
            unregisterReceiver(myReceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUesrInfo();
        getDailyMissionState();
    }
}
