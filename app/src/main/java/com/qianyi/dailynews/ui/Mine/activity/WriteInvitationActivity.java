package com.qianyi.dailynews.ui.Mine.activity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_write_invitation_code);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
