package com.qianyi.dailynews.ui.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.ui.WebviewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/3.
 */

public class TaskCenterActivity extends BaseActivity implements View.OnClickListener {
    //微信绑定
    @BindView(R.id.ll_bandwx001) public LinearLayout ll_bandwx001;
    @BindView(R.id.ll_bandwx002) public LinearLayout ll_bandwx002;
    @BindView(R.id.btn_bandwx) public Button btn_bandwx;
    //一元提现
    @BindView(R.id.ll_oneYuan001) public LinearLayout ll_oneYuan001;
    @BindView(R.id.ll_oneYuan002) public LinearLayout ll_oneYuan002;
    @BindView(R.id.btn_oneyuan) public Button btn_oneyuan;
    //问卷调查
    @BindView(R.id.ll_Questionnaire001) public LinearLayout ll_Questionnaire001;
    @BindView(R.id.ll_Questionnaire002) public LinearLayout ll_Questionnaire002;
    @BindView(R.id.btn_Questionnaire) public Button btn_Questionnaire;
    //答题奖励
    @BindView(R.id.ll_answerAward001) public LinearLayout ll_answerAward001;
    @BindView(R.id.ll_answerAward002) public LinearLayout ll_answerAward002;
    @BindView(R.id.btn_answerAward) public Button btn_answerAward;
    //邀请好友
    @BindView(R.id.ll_inviteFriends001) public LinearLayout ll_inviteFriends001;
    @BindView(R.id.ll_inviteFriends002) public LinearLayout ll_inviteFriends002;
    @BindView(R.id.btn_inviteFriends) public Button btn_inviteFriends;
    //阅读奖励
    @BindView(R.id.ll_readingAward001) public LinearLayout ll_readingAward001;
    @BindView(R.id.ll_readingAward002) public LinearLayout ll_readingAward002;
    @BindView(R.id.btn_readingAward) public Button btn_readingAward;
    //分享奖励
    @BindView(R.id.ll_shareRewards001) public LinearLayout ll_shareRewards001;
    @BindView(R.id.ll_shareRewards002) public LinearLayout ll_shareRewards002;
    @BindView(R.id.btn_share) public Button btn_share;
    //晒收入
    @BindView(R.id.ll_sunIncome001) public LinearLayout ll_sunIncome001;
    @BindView(R.id.ll_sunIncome002) public LinearLayout ll_sunIncome002;
    @BindView(R.id.btn_sunincome) public Button btn_sunincome;
    //评论奖励
    @BindView(R.id.ll_commentAward001) public LinearLayout ll_commentAward001;
    @BindView(R.id.ll_commentAward002) public LinearLayout ll_commentAward002;
    @BindView(R.id.btn_commentAward) public Button btn_commentAward;
    //赚钱攻略
    @BindView(R.id.tv_ProfitMakingStrategy) public TextView tv_ProfitMakingStrategy;
    //签到规则
    @BindView(R.id.tv_SignInRules) public TextView tv_SignInRules;

    @BindView(R.id.back) public ImageView back;



    private List<LinearLayout> OtherLineralayout=new ArrayList<>();
    @Override
    protected void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
    R.id.tv_ProfitMakingStrategy,R.id.tv_SignInRules})
    @Override
    public void onClick(View view) {
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
                Toast.makeText(this, "去完成提现", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_Questionnaire:
                //去调查
                Toast.makeText(this, "去调查", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_answerAward:
                //去答题
                Toast.makeText(this, "去答题", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_inviteFriends:
                //去邀请
                Toast.makeText(this, "去邀请", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_readingAward:
                //去阅读
                Toast.makeText(this, "去阅读", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_share:
                //去分享
                Toast.makeText(this, "去分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_sunincome:
                //晒收入
                Toast.makeText(this, "晒收入", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_commentAward:
                //去评论
                Toast.makeText(this, "去评论", Toast.LENGTH_SHORT).show();
                break;

            default:
            break;



        }

    }

    private void closeOtherAll(LinearLayout ll){

        for (int i = 0; i <OtherLineralayout.size() ; i++) {
            if(ll!=OtherLineralayout.get(i)){
                OtherLineralayout.get(i).setVisibility(View.GONE);
            }
        }


    }


}
