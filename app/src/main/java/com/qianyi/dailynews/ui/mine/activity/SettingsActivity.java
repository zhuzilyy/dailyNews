package com.qianyi.dailynews.ui.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/1.
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.re_ModifyPassword) public RelativeLayout re_ModifyPassword;

    @Override
    protected void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("设置");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_settings);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.re_ModifyPassword})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.re_ModifyPassword:
               Intent intent_modifypwd = new Intent(SettingsActivity.this,ModifyPasswordActivity.class);
               startActivity(intent_modifypwd);
                break;
            default:
                break;
        }
    }
}
