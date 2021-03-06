package com.qianyi.dailynews.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.SelfDialog;
import com.qianyi.dailynews.ui.Mine.activity.AccountDetailsActivity;
import com.qianyi.dailynews.ui.Mine.activity.HelpAndFeedBackActivity;
import com.qianyi.dailynews.ui.Mine.activity.MakeMoneyCenterActivity;
import com.qianyi.dailynews.ui.Mine.activity.MessageActivity;
import com.qianyi.dailynews.ui.Mine.activity.SettingsActivity;
import com.qianyi.dailynews.ui.Mine.activity.TaskCenterActivity;
import com.qianyi.dailynews.ui.Mine.activity.WithdrawalsActivity;
import com.qianyi.dailynews.ui.Mine.activity.WriteInvitationActivity;
import com.qianyi.dailynews.ui.account.activity.LoginActivity;
import com.qianyi.dailynews.ui.account.activity.RegisterActivity;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.Utils;
import com.qianyi.dailynews.views.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

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
    //邀请码的线
    @BindView(R.id.view_inviteCode)
    public View view_inviteCode;
    //提现
    @BindView(R.id.ll_tixian)
    public LinearLayout ll_tixian;
    @BindView(R.id.rl_login)
    public RelativeLayout rl_login;
    @BindView(R.id.rl_noLogin)
    public RelativeLayout rl_noLogin;
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
    @BindView(R.id.tv_wechatName)
    public TextView tv_wechatName;
    @BindView(R.id.mine_head)
    public CircleImageView mine_head;
    @BindView(R.id.view_message)
    public View view_message;
    private String phone,balance,earnings,gold,my_invite_code,user_id,headimgurl,invite_code,name;
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

        IntentFilter intentFilterSign=new IntentFilter();
        intentFilterSign.addAction("com.action.sign.success");
        getActivity().registerReceiver(myReceiver,intentFilterSign);

        IntentFilter intentFilterWithdrawal=new IntentFilter();
        intentFilterWithdrawal.addAction("com.action.withdrawal.success");
        getActivity().registerReceiver(myReceiver,intentFilterWithdrawal);

        IntentFilter intentFilterBindWx=new IntentFilter();
        intentFilterBindWx.addAction("com.action.bindWx.success");
        getActivity().registerReceiver(myReceiver,intentFilterBindWx);
    }
    private void setValue() {
        phone= (String) SPUtils.get(getActivity(),"phone","");
        gold=(String)SPUtils.get(getActivity(),"gold","0");
        my_invite_code=(String)SPUtils.get(getActivity(),"my_invite_code","");
        balance=(String)SPUtils.get(getActivity(),"balance","0");
        earnings=(String)SPUtils.get(getActivity(),"earnings","0");
        headimgurl=(String)SPUtils.get(getActivity(),"head_portrait","");
        invite_code=(String)SPUtils.get(getActivity(),"invite_code","");
        name=(String)SPUtils.get(getActivity(),"name","");
        if (TextUtils.isEmpty(invite_code)){
            re_WriteCode.setVisibility(View.VISIBLE);
            view_inviteCode.setVisibility(View.VISIBLE);
        }else{
            re_WriteCode.setVisibility(View.GONE);
            view_inviteCode.setVisibility(View.GONE);
        }
        Glide.with(getActivity()).load(headimgurl).error(R.mipmap.headportrait_icon).into(mine_head);
        tv_phone.setText("电话号:"+phone);
        tv_balance.setText(balance);
        tv_InvitationCode.setText(my_invite_code);
        tv_earning.setText(earnings);
        tv_gold.setText(gold);
        tv_wechatName.setText("微信号:"+name);
    }




    @Override
    protected void initData() {
        getMessageState();
    }
    //获取消息状态
    private void getMessageState() {
        user_id= (String) SPUtils.get(getActivity(),"user_id","");
        if (TextUtils.isEmpty(user_id)){
            return;
        }
        ApiMine.messageDot(ApiConstant.MESSAGE_DOT, user_id, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    boolean showDot = jsonObject.getBoolean("data");
                    if (showDot){
                        view_message.setVisibility(View.VISIBLE);
                    }else{
                        view_message.setVisibility(View.GONE);
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
    @Override
    protected void initListener() {

    }
    @OnClick({R.id.re_WriteCode, R.id.re_MoneyCenter, R.id.re_MissionCentre, R.id.re_AccountDetails,
            R.id.re_MessageCentre, R.id.re_HelpBack, R.id.re_Settings, R.id.ll_tixian, R.id.tv_copy,R.id.tv_login,R.id.tv_register,R.id.ll_noLoginTiXian
    })
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_WriteCode:
                user_id= (String) SPUtils.get(getActivity(),"user_id","");
                if (TextUtils.isEmpty(user_id)){
                    showLogin();
                    return;
                }
                //填写邀请码
                Intent intent_writecode = new Intent(getActivity(), WriteInvitationActivity.class);
                startActivityForResult(intent_writecode,100);
                break;
            case R.id.re_MoneyCenter:
                user_id= (String) SPUtils.get(getActivity(),"user_id","");
                if (TextUtils.isEmpty(user_id)){
                    showLogin();
                    return;
                }
                //赚钱中心
                Intent intent_makemoney = new Intent(getActivity(), MakeMoneyCenterActivity.class);
                startActivity(intent_makemoney);
                break;
            case R.id.re_MissionCentre:
                user_id= (String) SPUtils.get(getActivity(),"user_id","");
                if (TextUtils.isEmpty(user_id)){
                    showLogin();
                    return;
                }
                //任务中心
                Intent intent_taskcenter = new Intent(getActivity(), TaskCenterActivity.class);
                startActivity(intent_taskcenter);
                break;
            case R.id.re_AccountDetails:
                user_id= (String) SPUtils.get(getActivity(),"user_id","");
                if (TextUtils.isEmpty(user_id)){
                    showLogin();
                    return;
                }
                //账户明细
                Intent intent_accountdetails = new Intent(getActivity(), AccountDetailsActivity.class);
                startActivity(intent_accountdetails);
                break;
            case R.id.re_MessageCentre:
                user_id= (String) SPUtils.get(getActivity(),"user_id","");
                if (TextUtils.isEmpty(user_id)){
                    showLogin();
                    return;
                }
                //消息中心
                Intent intent_msg = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent_msg);
                break;
            case R.id.re_HelpBack:
                //帮助与反馈
                Intent intent_helpback = new Intent(getActivity(), HelpAndFeedBackActivity.class);
                intent_helpback.putExtra("title","帮助与反馈");
                intent_helpback.putExtra("url","http://www.baidu.com");
                startActivity(intent_helpback);
                break;
            case R.id.re_Settings:
                user_id= (String) SPUtils.get(getActivity(),"user_id","");
                if (TextUtils.isEmpty(user_id)){
                    showLogin();
                    return;
                }
                //设置
                Intent intent_settings = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent_settings);
                break;
            case R.id.ll_tixian:
                user_id= (String) SPUtils.get(getActivity(),"user_id","");
                if (TextUtils.isEmpty(user_id)){
                    showLogin();
                    return;
                }
                //提现
                Intent intent_withdrawals = new Intent(getActivity(), WithdrawalsActivity.class);
                startActivity(intent_withdrawals);
                break;
            case R.id.tv_copy:
                user_id= (String) SPUtils.get(getActivity(),"user_id","");
                if (TextUtils.isEmpty(user_id)){
                    showLogin();
                    return;
                }
                String code = tv_InvitationCode.getText().toString().trim();
                try {
                    Utils.copy(code, getActivity());
                    Toast.makeText(mActivity, "已复制", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Logger.i(e.getMessage());
                }
                break;
            case R.id.tv_login:
                Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
                startActivity(intentLogin);
                break;
            case R.id.tv_register:
                Intent intentRegister = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.ll_noLoginTiXian:
                Intent intentTiXian = new Intent(getActivity(), LoginActivity.class);
                startActivity(intentTiXian);
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        String userid = (String) SPUtils.get(getActivity(),"user_id","");
        if (TextUtils.isEmpty(userid)){
            view_message.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(userid)){
            rl_noLogin.setVisibility(View.VISIBLE);
            rl_login.setVisibility(View.GONE);
        }else{
            rl_noLogin.setVisibility(View.GONE);
            rl_login.setVisibility(View.VISIBLE);
        }
        if(!TextUtils.isEmpty(userid)){
            getUserInfo(userid);
            getMessageState();
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
                setValue();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myReceiver!=null){
            getActivity().unregisterReceiver(myReceiver);
        }
    }
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.action.login.success") || action.equals("com.action.quit")) {
                setValue();
            } else if (action.equals("com.action.sign.success") || action.equals("com.action.withdrawal.success") || action.equals("com.action.bindWx.success")) {
                getUserInfo(user_id);
            }
        }
    }
        private void getUserInfo(String user_id) {
            user_id= (String) SPUtils.get(getActivity(),"user_id","");
            ApiMine.getUserInfo(ApiConstant.GET_USERINFO, user_id, new RequestCallBack<String>() {
                @Override
                public void onSuccess(Call call, Response response, String s) {
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        String code = jsonObject.getString("code");
                        if (code.equals(ApiConstant.SUCCESS_CODE)){
                            JSONObject data = jsonObject.getJSONObject("data");
                            String user_id=data.getString("user_id");
                            String phone=data.getString("phone");
                            String head_portrait=data.getString("head_portrait");
                            String gold=data.getString("gold");
                            String my_invite_code=data.getString("my_invite_code");
                            String balance=data.getString("balance");
                            String earnings=data.getString("earnings");
                            String invite_code=data.getString("invite_code");
                            String name=data.getString("name");
                            boolean oneyuan=data.getBoolean("oneyuan");
                            SPUtils.put(getActivity(),"user_id",user_id);
                            SPUtils.put(getActivity(),"phone",phone);
                            SPUtils.put(getActivity(),"head_portrait",head_portrait);
                            SPUtils.put(getActivity(),"gold",gold);
                            SPUtils.put(getActivity(),"my_invite_code",my_invite_code);
                            SPUtils.put(getActivity(),"balance",balance);
                            SPUtils.put(getActivity(),"earnings",earnings);
                            SPUtils.put(getActivity(),"invite_code",invite_code);
                            SPUtils.put(getActivity(),"oneyuan",oneyuan);
                            SPUtils.put(getActivity(),"name",name);
                            tv_gold.setText(gold);
                            setValue();
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

}
