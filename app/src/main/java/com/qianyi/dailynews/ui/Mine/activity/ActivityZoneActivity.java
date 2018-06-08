package com.qianyi.dailynews.ui.Mine.activity;

import android.view.View;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/8.
 */

public class ActivityZoneActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @Override
    protected void initViews() {
        tv_title.setText("活动专区");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_activity_zone);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick(R.id.iv_back)
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
