package com.qianyi.dailynews.ui.mine.activity;

import android.view.View;
import android.widget.ImageView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/1.
 */

public class WriteInvitationActivity extends BaseActivity {
    @BindView(R.id.back) public ImageView back;
    @Override
    protected void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_write_invitation_code);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
