package com.qianyi.dailynews.ui.Mine.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/13.
 */

public class HighRebateDetilsActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title)public TextView title;
    @BindView(R.id.btn_getMoney) public Button btn_getMoney;
    @Override
    protected void initViews() {
        title.setText("试玩应用");
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
        setContentView(R.layout.activity_hight_rebate_details);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.btn_getMoney})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_getMoney:
                Toast.makeText(this, "789", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
