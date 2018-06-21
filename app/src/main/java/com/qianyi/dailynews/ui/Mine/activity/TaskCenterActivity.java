package com.qianyi.dailynews.ui.Mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.ui.Mine.bean.SignBean;
import com.qianyi.dailynews.ui.WebviewActivity;
import com.qianyi.dailynews.ui.account.activity.LoginActivity;
import com.qianyi.dailynews.ui.invitation.activity.IncomeShowActivity;
import com.qianyi.dailynews.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/3.
 */

public class TaskCenterActivity extends BaseActivity implements View.OnClickListener {
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


    //赚钱攻略
    @BindView(R.id.tv_ProfitMakingStrategy) public TextView tv_ProfitMakingStrategy;
    //签到规则
    @BindView(R.id.tv_SignInRules) public TextView tv_SignInRules;
    @BindView(R.id.btn_sign) public Button btn_sign;

    @BindView(R.id.back) public ImageView back;
    private List<LinearLayout> OtherLineralayout=new ArrayList<>();
    private String userId;
    private CustomLoadingDialog customLoadingDialog;
    private List<LinearLayout> signedDays,unsignDays;
    private String user_id;
    private boolean signed;
    @Override
    protected void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userId= (String) SPUtils.get(TaskCenterActivity.this,"user_id","");
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
                    btn_sign.setText("明天签到可领取100金币");
                }
                int signDay = Integer.parseInt(countinuous);
                for (int i = 0; i <signDay; i++) {
                    if (i<signDay){
                        signedDays.get(i).setVisibility(View.VISIBLE);
                        unsignDays.get(i).setVisibility(View.GONE);
                    }else{
                        signedDays.get(i).setVisibility(View.GONE);
                        unsignDays.get(i).setVisibility(View.VISIBLE);
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
                Intent intent_profitMakeingStrategy  = new Intent(TaskCenterActivity.this, WebviewActivity.class);
                intent_profitMakeingStrategy.putExtra("title","赚钱攻略");
                intent_profitMakeingStrategy.putExtra("utl","http://www.baidu.com");
                startActivity(intent_profitMakeingStrategy);
                break;
            case R.id.tv_SignInRules:
                //签到规则
                Intent intent_SignInRules  = new Intent(TaskCenterActivity.this, WebviewActivity.class);
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
                //去绑定微信
                Toast.makeText(this, "去绑定微信", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_oneyuan:
                //去完成提现
                jumpActivity(this,ActivityZoneActivity.class);
                break;
            case R.id.btn_Questionnaire:
                jumpActivity(this,QuestionSurveyActivity.class);
                //去调查
                break;
            case R.id.btn_answerAward:
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
                //晒收入
               jumpActivity(TaskCenterActivity.this, IncomeShowActivity.class);
                break;
            case R.id.btn_commentAward:
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

        }

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
                        Gson gson=new Gson();
                        SignBean signBean = gson.fromJson(s, SignBean.class);
                        String message = signBean.getData().getMessage();
                        Toast.makeText(TaskCenterActivity.this, message, Toast.LENGTH_SHORT).show();
                        btn_sign.setText("明天签到可领取100金币");
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


}
