package com.qianyi.dailynews.ui.Mine.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiAccount;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.dialog.SelfDialog;
import com.qianyi.dailynews.ui.account.activity.LoginActivity;
import com.qianyi.dailynews.utils.SPUtils;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/1.
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.tv_phone) public TextView tv_phone;
    @BindView(R.id.btn_bind) public Button btn_bind;
    @BindView(R.id.re_ModifyPassword) public RelativeLayout re_ModifyPassword;

    private MyReceiver myReceiver;

    private CustomLoadingDialog customLoadingDialog;
    private String openid, unionid, nickname, headimgurl,language,city,province,country,user_id,missionWxBind;
    private int sex;

    @Override
    protected void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("设置");

        String phone= (String) SPUtils.get(SettingsActivity.this,"phone","");
        user_id= (String) SPUtils.get(SettingsActivity.this,"user_id","");
        tv_phone.setText(phone);
        customLoadingDialog=new CustomLoadingDialog(this);
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.action.wechat");
        registerReceiver(myReceiver, intentFilter);
        getUserInfo();
    }

    private void getUserInfo() {
        customLoadingDialog.show();
        ApiMine.getUserInfo(ApiConstant.GET_USERINFO, user_id, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String newer_mission = data.getString("newer_mission");
                    String[] missionArr=newer_mission.split("\\|");
                    missionWxBind = missionArr[0];
                    if (missionWxBind.equals("1")){
                        btn_bind.setText("已绑定");
                        btn_bind.setTextColor(Color.parseColor("#ffffff"));
                        btn_bind.setBackgroundResource(R.drawable.bg_wx_bind_finish);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
            }
        });
    }

    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_settings);


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.re_ModifyPassword,R.id.btn_quit,R.id.btn_bind})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.re_ModifyPassword:
               Intent intent_modifypwd = new Intent(SettingsActivity.this,ModifyPasswordActivity.class);
               startActivity(intent_modifypwd);
                break;
            case R.id.btn_quit:
                quitAccount();
                break;
            //微信绑定
            case R.id.btn_bind:
                if (missionWxBind.equals("1")){
                    return;
                }
                initWx();
                break;
        }
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
            }
        }
    }
    private void bindWx() {
        customLoadingDialog.show();
        ApiAccount.wechatBind(ApiConstant.WX_BIND, user_id,openid, nickname, sex+"", language, city, province,country,headimgurl,unionid,new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                Log.i("tag", s);
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String return_msg = jsonObject.getString("return_msg");
                    Toast.makeText(SettingsActivity.this, return_msg, Toast.LENGTH_SHORT).show();
                    getUserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
            }
        });

    }


    private void quitAccount() {
        final SelfDialog quitDialog = new SelfDialog(this);
        quitDialog.setTitle("提示");
        quitDialog.setMessage("确定要退出登录吗");
        quitDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                SPUtils.put(SettingsActivity.this,"user_id","");
                jumpActivity(SettingsActivity.this,LoginActivity.class);
                quitDialog.dismiss();
                Intent intent=new Intent();
                intent.setAction("com.action.quit");
                sendBroadcast(intent);
                SPUtils.put(SettingsActivity.this,"user_id","");
                SPUtils.put(SettingsActivity.this,"phone","");
                SPUtils.put(SettingsActivity.this,"head_portrait","");
                SPUtils.put(SettingsActivity.this,"gold","");
                SPUtils.put(SettingsActivity.this,"my_invite_code","");
                SPUtils.put(SettingsActivity.this,"balance","");
                SPUtils.put(SettingsActivity.this,"earnings","");
                SPUtils.put(SettingsActivity.this,"invite_code","");
                finish();
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
    protected void onDestroy() {
        super.onDestroy();
        if(myReceiver!=null){
            unregisterReceiver(myReceiver);
        }
    }
}
