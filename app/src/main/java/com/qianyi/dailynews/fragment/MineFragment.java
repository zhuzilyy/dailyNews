package com.qianyi.dailynews.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.dialog.SelfDialog;
import com.qianyi.dailynews.ui.Mine.activity.MessageActivity;
import com.qianyi.dailynews.ui.WebviewActivity;
import com.qianyi.dailynews.ui.Mine.activity.AccountDetailsActivity;
import com.qianyi.dailynews.ui.Mine.activity.MakeMoneyCenterActivity;
import com.qianyi.dailynews.ui.Mine.activity.SettingsActivity;
import com.qianyi.dailynews.ui.Mine.activity.TaskCenterActivity;
import com.qianyi.dailynews.ui.Mine.activity.WithdrawalsActivity;
import com.qianyi.dailynews.ui.Mine.activity.WriteInvitationActivity;
import com.qianyi.dailynews.ui.account.activity.LoginActivity;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/30.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private View newsView;
    //填写邀请码
    @BindView(R.id.re_WriteCode)
    public RelativeLayout re_WriteCode;
    //赚钱中心
    @BindView(R.id.re_MoneyCenter)
    public RelativeLayout re_MoneyCenter;
    //任务中心
    @BindView(R.id.re_MissionCentre)
    public RelativeLayout re_MissionCentre;
    //账户明细
    @BindView(R.id.re_AccountDetails)
    public RelativeLayout re_AccountDetails;
    //消息中心
    @BindView(R.id.re_MessageCentre)
    public RelativeLayout re_MessageCentre;
    //帮助与反馈
    @BindView(R.id.re_HelpBack)
    public RelativeLayout re_HelpBack;
    //设置
    @BindView(R.id.re_Settings)
    public RelativeLayout re_Settings;
    //提现
    @BindView(R.id.ll_tixian)
    public LinearLayout ll_tixian;
    @BindView(R.id.tv_copy)
    public TextView tv_copy;
    @BindView(R.id.tv_InvitationCode)
    public TextView tv_InvitationCode;
    @BindView(R.id.tv_phone)
    public TextView tv_phone;
    @BindView(R.id.tv_balance)
    public TextView tv_balance;
    @BindView(R.id.tv_earning)
    public TextView tv_earning;
    @BindView(R.id.tv_gold)
    public TextView tv_gold;
    private String phone,balance,earnings,gold,my_invite_code,user_id;
    private MyReceiver myReceiver;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        newsView = inflater.inflate(R.layout.fragment_mine, null);
        return newsView;
    }

    @Override
    protected void initViews() {
        setValue();
        myReceiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.action.login.success");
        getActivity().registerReceiver(myReceiver,intentFilter);

        IntentFilter intentFilterQuit=new IntentFilter();
        intentFilterQuit.addAction("com.action.quit");
        getActivity().registerReceiver(myReceiver,intentFilterQuit);


    }
    private void setValue() {
        phone= (String) SPUtils.get(getActivity(),"phone","");
        gold=(String)SPUtils.get(getActivity(),"gold","0");
        my_invite_code=(String)SPUtils.get(getActivity(),"my_invite_code","");
        balance=(String)SPUtils.get(getActivity(),"balance","0");
        earnings=(String)SPUtils.get(getActivity(),"earnings","0");
        tv_phone.setText("电话号:"+phone);
        tv_balance.setText(balance);
        tv_InvitationCode.setText(my_invite_code);
        tv_earning.setText(earnings);
        tv_gold.setText(gold);
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.re_WriteCode, R.id.re_MoneyCenter, R.id.re_MissionCentre, R.id.re_AccountDetails,
            R.id.re_MessageCentre, R.id.re_HelpBack, R.id.re_Settings, R.id.ll_tixian, R.id.tv_copy,
    })
    @Override
    public void onClick(View v) {
        user_id= (String) SPUtils.get(getActivity(),"user_id","");
        if (TextUtils.isEmpty(user_id)){
            showLogin();
            return;
        }
        switch (v.getId()) {
            case R.id.re_WriteCode:
                //填写邀请码
                Intent intent_writecode = new Intent(getActivity(), WriteInvitationActivity.class);
                startActivityForResult(intent_writecode,100);
                break;
            case R.id.re_MoneyCenter:
                //赚钱中心
                Intent intent_makemoney = new Intent(getActivity(), MakeMoneyCenterActivity.class);
                startActivity(intent_makemoney);
                break;
            case R.id.re_MissionCentre:
                //任务中心
                Intent intent_taskcenter = new Intent(getActivity(), TaskCenterActivity.class);
                startActivity(intent_taskcenter);
                break;
            case R.id.re_AccountDetails:
                //账户明细
                Intent intent_accountdetails = new Intent(getActivity(), AccountDetailsActivity.class);
                startActivity(intent_accountdetails);
                break;
            case R.id.re_MessageCentre:
                //消息中心
                Intent intent_msg = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent_msg);
                break;
            case R.id.re_HelpBack:
                //帮助与反馈
                Intent intent_helpback = new Intent(getActivity(), WebviewActivity.class);
                intent_helpback.putExtra("title","帮助与反馈");
                intent_helpback.putExtra("url","http://www.baidu.com");
                startActivity(intent_helpback);
                break;
            case R.id.re_Settings:
                //设置
                Intent intent_settings = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent_settings);
                break;
            case R.id.ll_tixian:
                //提现
                Intent intent_withdrawals = new Intent(getActivity(), WithdrawalsActivity.class);
                startActivity(intent_withdrawals);
                break;
            case R.id.tv_copy:
                String code = tv_InvitationCode.getText().toString().trim();
                try {
                    Utils.copy(code, getActivity());
                    Toast.makeText(mActivity, "已复制", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Logger.i(e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    private void showLogin() {
        final SelfDialog quitDialog = new SelfDialog(getActivity());
        quitDialog.setTitle("提示");
        quitDialog.setMessage("请先登录");
        quitDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                quitDialog.dismiss();
            }
        });
        quitDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                quitDialog.dismiss();
            }
        });
        quitDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 100:
                //好友邀请码写完，返回后隐藏该条目
                re_WriteCode.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
    public class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.action.login.success")||action.equals("com.action.quit")){
                setValue();
            }
        }
    }

}
