package com.qianyi.dailynews.ui.Mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.dialog.SelfDialog;
import com.qianyi.dailynews.ui.account.activity.LoginActivity;
import com.qianyi.dailynews.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/1.
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.tv_phone) public TextView tv_phone;
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

        String phone= (String) SPUtils.get(SettingsActivity.this,"phone","");
        tv_phone.setText(phone);
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
    @OnClick({R.id.re_ModifyPassword,R.id.btn_quit})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.re_ModifyPassword:
               Intent intent_modifypwd = new Intent(SettingsActivity.this,ModifyPasswordActivity.class);
               startActivity(intent_modifypwd);
                break;
            case R.id.btn_quit:
                quitAccount();
                break;
        }
    }

    private void quitAccount() {
        final SelfDialog quitDialog = new SelfDialog(this);
        quitDialog.setTitle("提示");
        quitDialog.setMessage("确定要退出登录吗");
        quitDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                SPUtils.put(SettingsActivity.this,"user_id","");
                jumpActivity(SettingsActivity.this,LoginActivity.class);
                quitDialog.dismiss();

                Intent intent=new Intent();
                intent.setAction("com.action.quit");
                sendBroadcast(intent);

                SPUtils.put(SettingsActivity.this,"user_id","");
                SPUtils.put(SettingsActivity.this,"phone","");
                SPUtils.put(SettingsActivity.this,"head_portrait","");
                SPUtils.put(SettingsActivity.this,"gold","");
                SPUtils.put(SettingsActivity.this,"my_invite_code","");
                SPUtils.put(SettingsActivity.this,"balance","");
                SPUtils.put(SettingsActivity.this,"earnings","");
                SPUtils.put(SettingsActivity.this,"invite_code","");
                finish();
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
