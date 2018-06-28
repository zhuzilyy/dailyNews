package com.qianyi.dailynews.ui.Mine.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/27.
 */

public class ConfirmOrderActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.et3)
    EditText et3;
    @BindView(R.id.et4)
    EditText et4;
    @BindView(R.id.et5)
    EditText et5;
    @BindView(R.id.et6)
    EditText et6;
    private String userId,withdrawalMoney,subMoney;
    private CustomLoadingDialog customLoadingDialog;
    private Intent intent;
    @Override
    protected void initViews() {
        tv_title.setText("确认订单");
        userId= (String) SPUtils.get(this,"user_id","");
        customLoadingDialog=new CustomLoadingDialog(this);
        intent=getIntent();
        if (intent!=null){
            withdrawalMoney=intent.getStringExtra("withdrawalMoney");
            subMoney = withdrawalMoney.substring(0, withdrawalMoney.length()-1);
        }
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_confrim_order);
    }
    @Override
    protected void initListener() {

    }
    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back,R.id.btn_confrim})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_confrim:
                String first = et1.getText().toString().trim();
                String second = et2.getText().toString().trim();
                String third = et3.getText().toString().trim();
                String fourth = et4.getText().toString().trim();
                String fifth = et5.getText().toString().trim();
                String sixth = et6.getText().toString().trim();
                if (TextUtils.isEmpty(first)){
                    Toast.makeText(this, "请输入完整验证码第1位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(second)){
                    Toast.makeText(this, "请输入完整验证码第2位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(third)){
                    Toast.makeText(this, "请输入完整验证码第3位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fourth)){
                    Toast.makeText(this, "请输入完整验证码第4位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fifth)){
                    Toast.makeText(this, "请输入完整验证码第5位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sixth)){
                    Toast.makeText(this, "请输入完整验证码第6位", Toast.LENGTH_SHORT).show();
                    return;
                }
                doWithDrawal(first,second,third,fourth,fifth,sixth);
                break;
        }
    }
    //提现
    private void doWithDrawal(String first, String second, String third, String fourth, String fifth, String sixth) {
        String confrimCode=first+second+third+fourth+fifth+sixth;
        customLoadingDialog.show();
        ApiMine.doWithdrawal(ApiConstant.DO_WITHDRAWAL, userId, subMoney, confrimCode, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            String return_code = jsonObject.getString("return_code");
                            String return_msg = jsonObject.getString("return_msg");
                            Toast.makeText(ConfirmOrderActivity.this, return_msg, Toast.LENGTH_SHORT).show();
                            if (return_code.equals(ApiConstant.SUCCESS)){
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
                Toast.makeText(ConfirmOrderActivity.this, "网络错误提现失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
