package com.qianyi.dailynews.ui.invitation.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.ui.invitation.adapter.ApprenticeAdapter;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/9.
 */

public class ApprenticeActivity extends BaseActivity {
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.rvList) public RecyclerView rvlist;
    private ApprenticeAdapter adapter;
    @Override
    protected void initViews() {
        title.setText("徒弟列表");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvlist.setLayoutManager(new LinearLayoutManager(ApprenticeActivity.this));
        adapter = new ApprenticeAdapter(ApprenticeActivity.this);
        rvlist.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
            setContentView(R.layout.activity_apprentice);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
