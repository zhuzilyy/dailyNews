package com.qianyi.dailynews.ui.invitation.activity;

import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.ui.invitation.adapter.WakeUpFriendAdapter;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/1.
 */

public class WakeFriendsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    public TextView title;
    @BindView(R.id.iv_back)
    public ImageView back;
    @BindView(R.id.tv_wakefriend_title)
    public TextView tv_wakefriend_title;
    @BindView(R.id.rv)
    public RecyclerView rv;
    public WakeUpFriendAdapter adapter;
    @Override
    protected void initViews() {

        title.setText("唤醒好友");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/sxs.ttf");
        tv_wakefriend_title.setTypeface(typeface1);

        rv.setLayoutManager(new LinearLayoutManager(WakeFriendsActivity.this));



    }

    @Override
    protected void initData() {
        adapter = new WakeUpFriendAdapter(WakeFriendsActivity.this);
        rv.setAdapter(adapter);

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_wekefriend);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
