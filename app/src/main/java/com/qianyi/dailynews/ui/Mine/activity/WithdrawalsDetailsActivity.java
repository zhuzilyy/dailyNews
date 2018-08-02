package com.qianyi.dailynews.ui.Mine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.dialog.SelfDialog;
import com.qianyi.dailynews.dialog.WithdrawalDialog;
import com.qianyi.dailynews.ui.account.activity.LoginActivity;
import com.qianyi.dailynews.utils.ListActivity;
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
    @BindView(R.id.wv_webview)
    WebView wv_webview;
    private WithdrawalDialog withdrawalDialog;
    @Override
    protected void initViews() {
        ListActivity.list.add(this);
        ListActivity.list2.add(this);
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
        getWebview();
    }
    private void getWebview() {
        ApiMine.getWebview(ApiConstant.WEBVIEW, "DEPOSIT", new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            String return_code = jsonObject.getString("return_code");
                            String data = jsonObject.getString("data");
                            if (return_code.equals("SUCCESS")){
                                wv_webview.loadDataWithBaseURL(null, data, "text/html" , "utf-8", null);
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
    private void getMoney() {
        customLoadingDialog.show();
        ApiMine.withdrawalMoney(ApiConstant.GET_USER_INFO, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, final Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            JSONObject data = jsonObject.getJSONObject("data");
                            balance = data.getString("balance");
                            doubleBalance=Double.parseDouble(balance);
                            tv_balance.setText(balance+"元");
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
                if (doubleBalance<doubleSubMoney){
                    Toast.makeText(this, "余额不足,不能提现", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (doubleSubMoney<1){
                    return;
                }
                getZoneData();
                break;
        }
    }
    private void getZoneData() {
        customLoadingDialog.show();
        ApiMine.activityZone(ApiConstant.ACTIVITY_ZONE,userId,new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                Log.i("tag",s);
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String sign =data.getString("sign");
                    String search = data.getString("search");
                    String  read =data.getString("read");
                    String share =data.getString("share");
                    if (!sign.equals("1")||!search.equals("2")||!read.equals("10")||!share.equals("1")){
                        showWithdrawalDialog("完成新手任务才能提现1元");
                    }else{
                        Intent intent=new Intent(WithdrawalsDetailsActivity.this,ConfirmOrderActivity.class);
                        intent.putExtra("withdrawalMoney",withdrawalMoney);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                Log.i("tag",e.getMessage());
            }
        });

    }
    //显示提现的对话框
    private void showWithdrawalDialog(String content) {
        final SelfDialog quitDialog = new SelfDialog(this);
        quitDialog.setTitle("提示");
        quitDialog.setMessage(content);
        quitDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                quitDialog.dismiss();
                Intent intent=new Intent(WithdrawalsDetailsActivity.this, ActivityZoneActivity.class);
                intent.putExtra("tag","withdrawalDetail");
                startActivity(intent);
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
}
