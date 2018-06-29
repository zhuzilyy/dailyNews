package com.qianyi.dailynews.ui.Mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

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
 * Created by Administrator on 2018/5/5.
 */

public class WithdrawalsDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_withdrawalMoney)
    TextView tv_withdrawalMoney;
    @BindView(R.id.tv_wechatMoney)
    TextView tv_wechatMoney;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    private Intent intent;
    private String withdrawalMoney,balance,userId;
    private CustomLoadingDialog customLoadingDialog;
    private double doubleBalance;
    @Override
    protected void initViews() {
        intent=getIntent();
        if (intent!=null){
            withdrawalMoney=intent.getStringExtra("withdrawalMoney");
            balance=intent.getStringExtra("balance");
            tv_withdrawalMoney.setText(withdrawalMoney);
            tv_wechatMoney.setText(withdrawalMoney);
            tv_balance.setText(balance);
        }
        userId= (String) SPUtils.get(this,"user_id","");
        customLoadingDialog=new CustomLoadingDialog(this);
    }
    @Override
    protected void initData() {
        getMoney();
    }
    private void getMoney() {
        customLoadingDialog.show();
        ApiMine.withdrawalMoney(ApiConstant.WITHDRAWAL_MONEY, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, final Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            balance = jsonObject.getString("data");
                            doubleBalance=Double.parseDouble(balance);
                            tv_balance.setText(balance);
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
    protected void getResLayout() {
        setContentView(R.layout.activity_withdrawlas_details);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back,R.id.btn_withdrawal})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_withdrawal:
                String subMoney = withdrawalMoney.substring(0, withdrawalMoney.length()-1);
                double doubleSubMoney = Double.parseDouble(subMoney);
                if (doubleSubMoney<1){
                    return;
                }
                Intent intent=new Intent(WithdrawalsDetailsActivity.this,ConfirmOrderActivity.class);
                intent.putExtra("withdrawalMoney",withdrawalMoney);
                startActivity(intent);
                break;
        }
    }
}
