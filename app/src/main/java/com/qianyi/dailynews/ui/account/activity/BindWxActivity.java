package com.qianyi.dailynews.ui.account.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiAccount;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.utils.ListActivity;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.ToastUtils;
import com.qianyi.dailynews.views.ClearEditText;
import com.qianyi.dailynews.views.MyCountDownTimer;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/4.
 */

public class BindWxActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.register_close_tv)
    public TextView register_close_tv;
    @BindView(R.id.tv_title)
    public TextView tv_title;
    @BindView(R.id.et_account)

    ClearEditText et_account;
    @BindView(R.id.register_code_cet)
    ClearEditText et_confirmCode;
    @BindView(R.id.register_pwd_cet)
    ClearEditText et_pwd;
    @BindView(R.id.register_confirmpwd_cet)
    ClearEditText et_confirmPwd;
    @BindView(R.id.et_inviteCode)
    ClearEditText et_inviteCode;
    @BindView(R.id.ll_doublePwd)
    LinearLayout ll_doublePwd;
    private MyCountDownTimer timer;
    private CustomLoadingDialog customLoadingDialog;
    private String user_id,openid, nickname,language, city, province, country, headimgurl, unionid,gender;
    private Intent intent;
    private int sex;
    @Override
    protected void initViews() {
        ListActivity.list3.add(this);
        customLoadingDialog=new CustomLoadingDialog(this);
        tv_title.setText("绑定手机");
        intent=getIntent();
        if (intent!=null){
            openid = intent.getStringExtra("openid");
            nickname = intent.getStringExtra("nickname");
            sex = intent.getIntExtra("sex",0);
            language = intent.getStringExtra("language");
            city = intent.getStringExtra("city");
            province = intent.getStringExtra("province");
            country = intent.getStringExtra("country");
            headimgurl = intent.getStringExtra("headimgurl");
            unionid = intent.getStringExtra("unionid");
            user_id = intent.getStringExtra("userId");
            if (sex==0){
                gender="女";
            }else{
                gender="男";
            }
        }
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_bind_wx);
    }
    @Override
    protected void initListener() {

    }
    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.register_close_tv,R.id.btn_getCode,R.id.btn_bind,R.id.iv_back})
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register_close_tv:
                finish();
            break;
            //发送验证码
            case R.id.btn_getCode:
                String phoneNumber = et_account.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNumber)) {
                    if (phoneNumber.matches("^[1][3467589][0-9]{9}$")) {
                        timer = new MyCountDownTimer(60000, 1000, (Button) view);
                        timer.start();
                        // 向服务器请求验证码
                        getConfirmCode(phoneNumber);
                    } else {
                        ToastUtils.show(BindWxActivity.this, "手机号码格式不正确");
                    }
                } else {
                    ToastUtils.show(BindWxActivity.this, "手机号码不能为空");
                }
                break;
            //注册
            case R.id.btn_bind:
                String account = et_account.getText().toString().trim();
                String confrimCode=et_confirmCode.getText().toString().trim();
                String pwd=et_pwd.getText().toString().trim();
                String confirmPwd=et_confirmPwd.getText().toString().trim();
                String inviteCode=et_inviteCode.getText().toString().trim();
                if (TextUtils.isEmpty(account)){
                    ToastUtils.show(BindWxActivity.this,"请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(confrimCode)){
                    ToastUtils.show(BindWxActivity.this,"请输入验证码");
                    return;
                }
                if (ll_doublePwd.getVisibility()==View.VISIBLE){
                    if (TextUtils.isEmpty(pwd)){
                        ToastUtils.show(BindWxActivity.this,"密码不能为空");
                        return;
                    }
                    if (pwd.length()<6){
                        ToastUtils.show(BindWxActivity.this,"密码长度不能小于6位");
                        return;
                    }
                    if (!pwd.equals(confirmPwd)){
                        ToastUtils.show(BindWxActivity.this,"两次密码不一致");
                        return;
                    }
                }
                bindPhone(account,pwd,confrimCode,inviteCode);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
    //注册

    private void bindPhone(final String account, final String pwd, String confrimCode, String inviteCode) {
       customLoadingDialog.show();
       ApiAccount.bindPhone(ApiConstant.BIND_PHONE, user_id, account, pwd, confrimCode, inviteCode, openid, nickname, gender, language, city, province, country, headimgurl, unionid, new RequestCallBack<String>() {
           @Override
           public void onSuccess(Call call, Response response, final String s) {
               customLoadingDialog.dismiss();
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           JSONObject jsonObject=new JSONObject(s);
                           String code = jsonObject.getString("return_code");
                           String return_msg = jsonObject.getString("return_msg");
                           if (code.equals("SUCCESS")){
                               ToastUtils.show(BindWxActivity.this,"绑定成功");
                           }else{
                               Toast.makeText(BindWxActivity.this, return_msg, Toast.LENGTH_SHORT).show();
                           }
                           if (code.equals(ApiConstant.SUCCESS)){
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
                               SPUtils.put(BindWxActivity.this,"user_id",user_id);
                               SPUtils.put(BindWxActivity.this,"phone",phone);
                               SPUtils.put(BindWxActivity.this,"head_portrait",head_portrait);
                               SPUtils.put(BindWxActivity.this,"gold",gold);
                               SPUtils.put(BindWxActivity.this,"my_invite_code",my_invite_code);
                               SPUtils.put(BindWxActivity.this,"balance",balance);
                               SPUtils.put(BindWxActivity.this,"earnings",earnings);
                               SPUtils.put(BindWxActivity.this,"invite_code",invite_code);
                               SPUtils.put(BindWxActivity.this,"oneyuan",oneyuan);
                               SPUtils.put(BindWxActivity.this,"name",name);
                               Intent intent=new Intent();
                               intent.setAction("com.action.login.success");
                               ListActivity.close3();
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
               ToastUtils.show(BindWxActivity.this,"网络错误");
           }
       });



    }

    /****
     * 注册完成后自动登录
     * @param account
     * @param pwd
     */
    //登录的方法
    private void AutoLogin(String account, String pwd) {
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
                            Toast.makeText(BindWxActivity.this, return_msg, Toast.LENGTH_SHORT).show();
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
                                SPUtils.put(BindWxActivity.this,"user_id",user_id);
                                SPUtils.put(BindWxActivity.this,"phone",phone);
                                SPUtils.put(BindWxActivity.this,"head_portrait",head_portrait);
                                SPUtils.put(BindWxActivity.this,"gold",gold);
                                SPUtils.put(BindWxActivity.this,"my_invite_code",my_invite_code);
                                SPUtils.put(BindWxActivity.this,"balance",balance);
                                SPUtils.put(BindWxActivity.this,"earnings",earnings);
                                SPUtils.put(BindWxActivity.this,"invite_code",invite_code);
                                SPUtils.put(BindWxActivity.this,"oneyuan",oneyuan);
                                SPUtils.put(BindWxActivity.this,"name",name);
                                Intent intent=new Intent();
                                intent.setAction("com.action.login.success");
                                sendBroadcast(intent);
                                sendBroadcast(new Intent("loginOk"));
                                //关闭登录
                                BaseActivity.removeActivity2();
                              //startActivity(new Intent(RegisterActivity.this,MainActivity.class));
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
                customLoadingDialog.dismiss();
            }
        });
    }

    //获取验证码
    private void getConfirmCode(String phoneNumber) {
        customLoadingDialog.show();
        ApiAccount.getConfirmCode(ApiConstant.WX_CONFIRM_CODE, phoneNumber, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, final Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            String return_msg=jsonObject.getString("return_msg");
                            boolean isBindWx=jsonObject.getBoolean("data");
                            Toast.makeText(BindWxActivity.this, return_msg, Toast.LENGTH_SHORT).show();
                            if (isBindWx){
                                ll_doublePwd.setVisibility(View.GONE);
                            }else{
                                ll_doublePwd.setVisibility(View.VISIBLE);
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
                ToastUtils.show(BindWxActivity.this,"网络错误");
            }
        });
    }
}
