package com.qianyi.dailynews.ui.Mine.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HightBackMoneyRewardWebViewActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    public ImageView back;
    @BindView(R.id.tv_title)
    public TextView title;
    @BindView(R.id.btn_money)
    public Button btn_money;


    @Override
    protected void initViews() {

        title.setText("赚钱中心");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_hight_back_money_webview);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }

    @OnClick({R.id.iv_back,R.id.btn_money})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.btn_money:
                finish();
                break;
        }
    }
}
