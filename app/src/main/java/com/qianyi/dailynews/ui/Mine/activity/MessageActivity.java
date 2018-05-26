package com.qianyi.dailynews.ui.Mine.activity;

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.ui.Mine.adapter.MessageAdapter;
import com.qianyi.dailynews.views.PullToRefreshView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/9.
 */

public class MessageActivity extends BaseActivity {
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.no_internet_rl)
    RelativeLayout no_internet_rl;
    private MessageAdapter messageAdapter;
    @Override
    protected void initViews() {
        tv_title.setText("消息中心");
        messageAdapter=new MessageAdapter(MessageActivity.this);
        listview.setAdapter(messageAdapter);
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_message);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
