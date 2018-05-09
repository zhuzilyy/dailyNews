package com.qianyi.dailynews.ui.Mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

    private List<TextView> textViews = new ArrayList<>();

    @Override
    protected void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                showWithdrawals(tv_01yuan);
                break;
            case R.id.ll_30yuan:
                showWithdrawals(tv_30yuan);
                break;
            case R.id.ll_50yuan:
                showWithdrawals(tv_50yuan);
                break;
            case R.id.ll_80yuan:
                showWithdrawals(tv_80yuan);
                break;
            case R.id.ll_100yuan:
                showWithdrawals(tv_100yuan);
                break;
            case R.id.ll_1000yuan:
                showWithdrawals(tv_1000yuan);
                break;
            case R.id.btn_tixian:
                Intent intent =new Intent(WithdrawalsActivity.this,WithdrawalsDetailsActivity.class);
                startActivity(intent);
                break;

            default:
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
