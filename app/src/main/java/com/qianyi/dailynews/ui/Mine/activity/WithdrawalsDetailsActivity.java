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
    private String withdrawalMoney,balance;
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
    }
    @Override
    protected void initData() {

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
                Intent intent=new Intent(WithdrawalsDetailsActivity.this,ConfirmOrderActivity.class);
                intent.putExtra("withdrawalMoney",withdrawalMoney);
                startActivity(intent);
                break;
        }
    }
}
