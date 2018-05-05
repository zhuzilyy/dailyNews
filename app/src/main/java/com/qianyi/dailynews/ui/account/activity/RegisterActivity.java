package com.qianyi.dailynews.ui.account.activity;

import android.view.View;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/4.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.register_close_tv) public TextView register_close_tv;
    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_register);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.register_close_tv})
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register_close_tv:
                finish();
            break;

            default:
            break;


        }
    }
}
