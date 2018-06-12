package com.qianyi.dailynews.ui.Mine.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.views.PullToRefreshView;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/1.
 */

public class HighRebateFragment extends BaseFragment {
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
    @BindView(R.id.iv_back)  public ImageView back;

    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_high_rebate,null);
    }

    @Override
    protected void initViews() {

        tv_title.setText("消息中心");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
