package com.qianyi.dailynews.ui.account.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.MainActivity;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiAccount;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.utils.ListActivity;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.views.ClearEditText;
import com.sina.weibo.sdk.constant.WBConstants;
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

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.login_close_iv)
    public ImageView login_close_iv;
    @BindView(R.id.login_account_cet)
    public ClearEditText login_account_cet;
    @BindView(R.id.login_pwd_cet)
    public ClearEditText login_pwd_cet;
    @BindView(R.id.login_login_btn)
    public Button login_login_btn;
    @BindView(R.id.login_forgetpwd_tv)
    public TextView login_forgetpwd_tv;
    @BindView(R.id.login_register_tv)
    public TextView login_register_tv;
    @BindView(R.id.login_wxlogin_iv)
    public ImageView login_wxlogin_iv;
    private MyReceiver myReceiver;

    private boolean account_b = false;
    private boolean pwd_b = false;
    private CustomLoadingDialog customLoadingDialog;
    private String openid, unionid, nickname, headimgurl,language,city,province,country;
    private int sex;
    @Override
    protected void initViews() {
        ListActivity.list2.add(this);
        ListActivity.list3.add(this);
        BaseActivity.addActivity2(this);
        String account =login_account_cet.getText().toString().trim();
        String pwd = login_pwd_cet.getText().toString().trim();
        if(!TextUtils.isEmpty(account)&&!TextUtils.isEmpty(pwd)){
            login_login_btn.setEnabled(true);
        }else {
            login_login_btn.setEnabled(false);
        }
        customLoadingDialog=new CustomLoadingDialog(this);

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.action.wechat");
        registerReceiver(myReceiver, intentFilter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void initListener() {
        //账号监听
        login_account_cet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable.toString())){
                    if(TextUtils.isEmpty(login_pwd_cet.getText().toString().trim())){
                        pwd_b=false;
                    }else {
                        pwd_b=true;
                    }
                    account_b = true;
                    isLoginEnable(account_b,pwd_b);
                }else if (TextUtils.isEmpty(editable.toString())){
                    if(TextUtils.isEmpty(login_pwd_cet.getText().toString().trim())){
                        pwd_b=false;
                    }else {
                        pwd_b=true;
                    }
                    account_b = false;
                    isLoginEnable(account_b,pwd_b);
                }
            }
        });
        //密码监听
        login_pwd_cet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable.toString())){
                    if(TextUtils.isEmpty(login_account_cet.getText().toString().trim())){
                        account_b=false;
                    }else {
                        account_b=true;
                    }
                    pwd_b = true;
                    isLoginEnable(account_b,pwd_b);
                }else if(TextUtils.isEmpty(editable.toString())) {
                    if(TextUtils.isEmpty(login_account_cet.getText().toString().trim())){
                        account_b=false;
                    }else {
                        account_b=true;
                    }
                    pwd_b = false;
                    isLoginEnable(account_b,pwd_b);
                }
            }
        });

    }


    private void isLoginEnable(boolean accoutStr,boolean pwdStr){
        if(accoutStr&&pwdStr){
            login_login_btn.setEnabled(true);
        }else {
            login_login_btn.setEnabled(false);
        }
    }
    @Override
    protected void setStatusBarColor() {
    }
    @OnClick({R.id.login_close_iv,R.id.login_login_btn,R.id.login_forgetpwd_tv,
            R.id.login_register_tv,R.id.login_wxlogin_iv})
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_close_iv:
                finish();
                //关闭当前页面
            break;
            case R.id.login_login_btn:
                //登录
                String account = login_account_cet.getText().toString().trim();
                String pwd = login_pwd_cet.getText().toString().trim();
                if(TextUtils.isEmpty(account)){
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                login(account,pwd);
                break;
            case R.id.login_forgetpwd_tv:
                //忘记密码
                Intent intent_forget = new Intent(LoginActivity.this,ForgetPwdActivity.class);
                startActivity(intent_forget);
                break;
            case R.id.login_register_tv:
                //注册
                Intent intent_register = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent_register);
                break;
            case R.id.login_wxlogin_iv:
                //微信登录
                loginWx();
                //jumpActivity(this,BindWxActivity.class);
                break;
            default:
            break;
        }
    }
    /***
     * 微信登录
     */
    private void loginWx() {
        IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, ApiConstant.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(ApiConstant.APP_ID);
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        mWxApi.sendReq(req);
    }
    //登录的方法
    private void login(String account, String pwd) {
        customLoadingDialog.show();
        ApiAccount.login(ApiConstant.LOGIN, account, pwd, new RequestCallBack<String>() {
            @Override
            public void onSuccess(final Call call, Response response, final String s) {
                Log.i("tag",s);
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                                JSONObject jsonObject=new JSONObject(s);
                                String code = jsonObject.getString("code");
                                String return_msg = jsonObject.getString("return_msg");
                                Toast.makeText(LoginActivity.this, return_msg, Toast.LENGTH_SHORT).show();
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
                                    SPUtils.put(LoginActivity.this,"user_id",user_id);
                                    SPUtils.put(LoginActivity.this,"phone",phone);
                                    SPUtils.put(LoginActivity.this,"head_portrait",head_portrait);
                                    SPUtils.put(LoginActivity.this,"gold",gold);
                                    SPUtils.put(LoginActivity.this,"my_invite_code",my_invite_code);
                                    SPUtils.put(LoginActivity.this,"balance",balance);
                                    SPUtils.put(LoginActivity.this,"earnings",earnings);
                                    SPUtils.put(LoginActivity.this,"invite_code",invite_code);
                                    SPUtils.put(LoginActivity.this,"oneyuan",oneyuan);
                                    SPUtils.put(LoginActivity.this,"name",name);
                                    Intent intent=new Intent();
                                    intent.setAction("com.action.login.success");
                                    sendBroadcast(intent);
                                    sendBroadcast(new Intent("loginOk"));
                                    finish();
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("tag",e.getMessage());
                customLoadingDialog.dismiss();
            }
        });
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
                //SPUtils.put(LoginActivity.this,"head_portrait",headimgurl);
                wechatLogin();
            }
        }
    }
    private void wechatLogin() {
        customLoadingDialog.show();
        ApiAccount.wechatLogin(ApiConstant.WECHAT_LOGIN, openid, nickname, sex+"", language, city, province,country,headimgurl,unionid,new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                Log.i("tag", s);
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            String code = jsonObject.getString("code");
                            String return_msg = jsonObject.getString("return_msg");
                            JSONObject data = jsonObject.getJSONObject("data");
                            String phone=data.getString("phone");
                            String user_id=data.getString("user_id");
                            Toast.makeText(LoginActivity.this, return_msg, Toast.LENGTH_SHORT).show();
                            if (code.equals(ApiConstant.SUCCESS_CODE)){
                                //绑定过手机号
                                if (!TextUtils.isEmpty(phone)){
                                    String head_portrait=data.getString("head_portrait");
                                    String gold=data.getString("gold");
                                    String my_invite_code=data.getString("my_invite_code");
                                    String balance=data.getString("balance");
                                    String earnings=data.getString("earnings");
                                    String invite_code=data.getString("invite_code");
                                    SPUtils.put(LoginActivity.this,"head_portrait",head_portrait);
                                    SPUtils.put(LoginActivity.this,"gold",gold);
                                    SPUtils.put(LoginActivity.this,"my_invite_code",my_invite_code);
                                    SPUtils.put(LoginActivity.this,"balance",balance);
                                    SPUtils.put(LoginActivity.this,"earnings",earnings);
                                    SPUtils.put(LoginActivity.this,"invite_code",invite_code);
                                    SPUtils.put(LoginActivity.this,"user_id",user_id);
                                    Intent intent=new Intent();
                                    intent.setAction("com.action.login.success");
                                    sendBroadcast(intent);
                                    finish();
                                    //没绑定过手机跳转到绑定手机的界面
                                }else{
                                    Intent intent=new Intent(LoginActivity.this,BindWxActivity.class);
                                    intent.putExtra("openid",openid);
                                    intent.putExtra("nickname",nickname);
                                    intent.putExtra("sex",sex);
                                    intent.putExtra("language",language);
                                    intent.putExtra("city",city);
                                    intent.putExtra("province",province);
                                    intent.putExtra("country",country);
                                    intent.putExtra("headimgurl",headimgurl);
                                    intent.putExtra("unionid",unionid);
                                    intent.putExtra("userId",user_id);
                                    startActivity(intent);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myReceiver!=null){
            unregisterReceiver(myReceiver);
        }
    }




}
