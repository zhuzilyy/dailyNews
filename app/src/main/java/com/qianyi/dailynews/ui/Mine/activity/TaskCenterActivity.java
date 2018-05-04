package com.qianyi.dailynews.ui.Mine.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

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

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

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
            R.id.ll_inviteFriends001,R.id.ll_readingAward001,R.id.ll_shareRewards001,R.id.ll_sunIncome001,R.id.ll_commentAward001})
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ll_bandwx001:
                //绑定微信
                if(ll_bandwx002.isShown()){
                    ll_bandwx002.setVisibility(View.GONE);
                }else {
                    ll_bandwx002.setVisibility(View.VISIBLE);
                }

            break;
            case R.id.ll_oneYuan001:
                //一元提现
                if(ll_oneYuan002.isShown()){
                    ll_oneYuan002.setVisibility(View.GONE);
                }else {
                    ll_oneYuan002.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.ll_Questionnaire001:
                //问卷调查
                if(ll_Questionnaire002.isShown()){
                    ll_Questionnaire002.setVisibility(View.GONE);
                }else {
                    ll_Questionnaire002.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_answerAward001:
                //答题奖励
                if(ll_answerAward002.isShown()){
                    ll_answerAward002.setVisibility(View.GONE);
                }else {
                    ll_answerAward002.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_inviteFriends001:
                //邀请好友
                if(ll_inviteFriends002.isShown()){
                    ll_inviteFriends002.setVisibility(View.GONE);
                }else {
                    ll_inviteFriends002.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_readingAward001:
                //阅读奖励
                if(ll_readingAward002.isShown()){
                    ll_readingAward002.setVisibility(View.GONE);
                }else {
                    ll_readingAward002.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_shareRewards001:
                //分享奖励
                if(ll_shareRewards002.isShown()){
                    ll_shareRewards002.setVisibility(View.GONE);
                }else {
                    ll_shareRewards002.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_sunIncome001:
                //晒收入
                if(ll_sunIncome002.isShown()){
                    ll_sunIncome002.setVisibility(View.GONE);
                }else {
                    ll_sunIncome002.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_commentAward001:
                //评论奖励
                if(ll_commentAward002.isShown()){
                    ll_commentAward002.setVisibility(View.GONE);
                }else {
                    ll_commentAward002.setVisibility(View.VISIBLE);
                }
                break;

            default:
            break;


        }

    }
}
