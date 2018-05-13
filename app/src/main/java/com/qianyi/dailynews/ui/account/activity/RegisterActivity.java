package com.qianyi.dailynews.ui.account.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiAccount;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
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

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.register_close_tv)
    public TextView register_close_tv;
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
    private MyCountDownTimer timer;
    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_register);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.register_close_tv,R.id.btn_getCode,R.id.btn_register})
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
                    if (phoneNumber.matches("^[1][34758][0-9]{9}$")) {
                        timer = new MyCountDownTimer(60000, 1000, (Button) view);
                        timer.start();
                        // 向服务器请求验证码
                        getConfirmCode(phoneNumber);
                    } else {
                        ToastUtils.show(RegisterActivity.this, "手机号码格式不正确");
                    }
                } else {
                    ToastUtils.show(RegisterActivity.this, "手机号码不能为空");
                }
                break;
            //注册
            case R.id.btn_register:
                String account = et_account.getText().toString().trim();
                String confrimCode=et_confirmCode.getText().toString().trim();
                String pwd=et_pwd.getText().toString().trim();
                String confirmPwd=et_confirmPwd.getText().toString().trim();
                String inviteCode=et_inviteCode.getText().toString().trim();
                if (TextUtils.isEmpty(account)){
                    ToastUtils.show(RegisterActivity.this,"请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(confrimCode)){
                    ToastUtils.show(RegisterActivity.this,"请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                    ToastUtils.show(RegisterActivity.this,"密码不能为空");
                    return;
                }
                if (pwd.length()<6){
                    ToastUtils.show(RegisterActivity.this,"密码长度不能小于6位");
                    return;
                }
                if (!pwd.equals(confirmPwd)){
                    ToastUtils.show(RegisterActivity.this,"两次密码不一致");
                    return;
                }
                register(account,pwd,confrimCode,inviteCode);
                break;
        }
    }
    //注册
    private void register(String account,  String pwd, String confrimCode,String inviteCode) {
        ApiAccount.register(ApiConstant.REGISTER, account, pwd, confrimCode, inviteCode, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           JSONObject jsonObject=new JSONObject(s);
                           String code = jsonObject.getString("code");
                           String return_msg = jsonObject.getString("return_msg");
                           ToastUtils.show(RegisterActivity.this,return_msg);
                           if (code.equals(ApiConstant.SUCCESS_CODE)){
                               jumpActivity(RegisterActivity.this,LoginActivity.class);
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
                ToastUtils.show(RegisterActivity.this,"网络错误");
            }
        });
    }

    //获取验证码
    private void getConfirmCode(String phoneNumber) {
        ApiAccount.getConfirmCode(ApiConstant.CONFIRMCODE, phoneNumber, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, final Response response, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            String return_msg=jsonObject.getString("return_msg");
                            ToastUtils.show(RegisterActivity.this,return_msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                ToastUtils.show(RegisterActivity.this,"网络错误");
            }
        });
    }
}
