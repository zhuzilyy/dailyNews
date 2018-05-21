package com.qianyi.dailynews.ui.account.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qianyi.dailynews.MainActivity;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiAccount;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
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

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.forgetpwd_close_tv)
    TextView forgetpwd_close_tv;
    @BindView(R.id.forgetpwd_account_cet)
    ClearEditText et_account;
    @BindView(R.id.forgetpwd_pwd_cet)
    ClearEditText et_pwd;
    @BindView(R.id.register_code_cet)
    ClearEditText et_confrimCode;
    private MyCountDownTimer timer;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected void initViews() {
        customLoadingDialog=new CustomLoadingDialog(this);
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_forgetpwd);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.forgetpwd_close_tv,R.id.forgetpwd_forgetpwd_btn,R.id.btn_getCode})
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.forgetpwd_close_tv:
                finish();
            break;
            case R.id.btn_getCode:
                String phoneNumber = et_account.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNumber)) {
                    if (phoneNumber.matches("^[1][34758][0-9]{9}$")) {
                        timer = new MyCountDownTimer(60000, 1000, (Button) view);
                        timer.start();
                        // 向服务器请求验证码
                        getConfirmCode(phoneNumber);
                    } else {
                        ToastUtils.show(ForgetPwdActivity.this, "手机号码格式不正确");
                    }
                } else {
                    ToastUtils.show(ForgetPwdActivity.this, "手机号码不能为空");
                }
                break;
            case R.id.forgetpwd_forgetpwd_btn:
                String account = et_account.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                String confirmCode = et_confrimCode.getText().toString().trim();
                Log.i("tag",account);
                Log.i("tag",pwd);
                Log.i("tag",confirmCode);
                if (TextUtils.isEmpty(account)){
                    ToastUtils.show(ForgetPwdActivity.this,"用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                    ToastUtils.show(ForgetPwdActivity.this,"密码不能为空");
                    return;
                }
                if (pwd.length()<6){
                    ToastUtils.show(ForgetPwdActivity.this,"密码长度不能小于6");
                    return;
                }
                if (TextUtils.isEmpty(confirmCode)){
                    ToastUtils.show(ForgetPwdActivity.this,"验证码不能为空");
                    return;
                }
                findPwd(account,pwd,confirmCode);
                break;
        }
    }
    private void findPwd(String account,String pwd,String confirmCode) {
        customLoadingDialog.show();
        ApiAccount.updatePwd(ApiConstant.UPDATE_PWD, account, pwd, confirmCode, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            String return_msg=jsonObject.getString("return_msg");
                            String code=jsonObject.getString("code");
                            if (code.equals(ApiConstant.SUCCESS_CODE)){
                                jumpActivity(ForgetPwdActivity.this, MainActivity.class);
                                finish();
                            }
                            ToastUtils.show(ForgetPwdActivity.this,return_msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                ToastUtils.show(ForgetPwdActivity.this,"网络错误");
            }
        });
    }
    //获取验证码
    private void getConfirmCode(String phoneNumber) {
        customLoadingDialog.show();
        ApiAccount.getForgetPwdConfirmCode(ApiConstant.FORGETPWD_CONFIRM_CODE, phoneNumber, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, final Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            String return_msg=jsonObject.getString("return_msg");
                            ToastUtils.show(ForgetPwdActivity.this,return_msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                ToastUtils.show(ForgetPwdActivity.this,"网络错误");
            }
        });
    }
}
