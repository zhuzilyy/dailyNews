package com.qianyi.dailynews.ui.Mine.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.utils.ListActivity;
import com.qianyi.dailynews.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/4.
 */

public class WithdrawalsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back)
    public ImageView back;
    @BindView(R.id.ll_01yuan)
    public LinearLayout ll_01yuan;
    @BindView(R.id.ll_30yuan)
    public LinearLayout ll_30yuan;
    @BindView(R.id.ll_50yuan)
    public LinearLayout ll_50yuan;
    @BindView(R.id.ll_80yuan)
    public LinearLayout ll_80yuan;
    @BindView(R.id.ll_100yuan)
    public LinearLayout ll_100yuan;
    @BindView(R.id.ll_1000yuan)
    public LinearLayout ll_1000yuan;
    @BindView(R.id.bg_oneYuan)
    public RelativeLayout bg_oneYuan;

    @BindView(R.id.tv_01yuan)
    public TextView tv_01yuan;
    @BindView(R.id.tv_30yuan)
    public TextView tv_30yuan;
    @BindView(R.id.tv_50yuan)
    public TextView tv_50yuan;
    @BindView(R.id.tv_80yuan)
    public TextView tv_80yuan;
    @BindView(R.id.tv_100yuan)
    public TextView tv_100yuan;
    @BindView(R.id.tv_1000yuan)
    public TextView tv_1000yuan;
    @BindView(R.id.btn_tixian) public Button btn_tixian;
    private String withdrawalMoney,balance,userId;
    private List<TextView> textViews = new ArrayList<>();
    private double doubleBalance;
    private CustomLoadingDialog customLoadingDialog;
    private boolean oneyuan;
    @Override
    protected void initViews() {
        ListActivity.list.add(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userId= (String) SPUtils.get(this,"user_id","");
        customLoadingDialog=new CustomLoadingDialog(this);

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
    protected void initData() {
        textViews.add(tv_01yuan);
        textViews.add(tv_30yuan);
        textViews.add(tv_50yuan);
        textViews.add(tv_80yuan);
        textViews.add(tv_100yuan);
        textViews.add(tv_1000yuan);
        //getMoney();
        getUserInfo();
    }

    private void getUserInfo() {
        ApiMine.getUserInfo(ApiConstant.GET_USERINFO, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    oneyuan= data.getBoolean("waitingWithdraw");
                    balance = data.getString("balance");
                    doubleBalance=Double.parseDouble(balance);
                    if (oneyuan){
                        bg_oneYuan.setBackgroundResource(R.mipmap.unpacket_icon);
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
    protected void getResLayout() {
        setContentView(R.layout.activity_withdrawls);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }

    @OnClick({R.id.ll_01yuan, R.id.ll_30yuan, R.id.ll_50yuan, R.id.ll_80yuan,
            R.id.ll_100yuan, R.id.ll_1000yuan,R.id.btn_tixian})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_01yuan:
                if (oneyuan){
                    Toast.makeText(this, "1元提现只能提现一次", Toast.LENGTH_SHORT).show();
                    return;
                }
                showWithdrawals(tv_01yuan);
                withdrawalMoney="1元";
                break;
            case R.id.ll_30yuan:
                showWithdrawals(tv_30yuan);
                withdrawalMoney="30元";
                break;
            case R.id.ll_50yuan:
                showWithdrawals(tv_50yuan);
                withdrawalMoney="50元";
                break;
            case R.id.ll_80yuan:
                showWithdrawals(tv_80yuan);
                withdrawalMoney="80元";
                break;
            case R.id.ll_100yuan:
                showWithdrawals(tv_100yuan);
                withdrawalMoney="100元";
                break;
            case R.id.ll_1000yuan:
                showWithdrawals(tv_1000yuan);
                withdrawalMoney="1000元";
                break;
            case R.id.btn_tixian:
                if (TextUtils.isEmpty(withdrawalMoney)){
                    Toast.makeText(this, "请选择提现金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                String subMoney = withdrawalMoney.substring(0, withdrawalMoney.length()-1);
                int intSubMoney=Integer.parseInt(subMoney);
                if (TextUtils.isEmpty(balance)){
                    return;
                }
                if (intSubMoney>doubleBalance){
                    Toast.makeText(this, "提现金额不能大于余额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(withdrawalMoney)){
                    Toast.makeText(this, "请选择提现金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent =new Intent(WithdrawalsActivity.this,WithdrawalsDetailsActivity.class);
                intent.putExtra("withdrawalMoney",withdrawalMoney);
                intent.putExtra("balance",balance);
                startActivity(intent);
                break;
        }

    }

    private void showWithdrawals(TextView tv){
        for (int i = 0; i <textViews.size() ; i++) {
            if(tv!=textViews.get(i)){
                textViews.get(i).setEnabled(false);
                textViews.get(i).setTextColor(getResources().getColor(R.color.gray));
            }else {
                textViews.get(i).setEnabled(true);
                textViews.get(i).setTextColor(getResources().getColor(R.color.main_red));
            }
        }
    }
}
