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
import com.qianyi.dailynews.ui.Mine.activity.MessageActivity;
import com.qianyi.dailynews.ui.Mine.adapter.HighRebateAdapter;
import com.qianyi.dailynews.ui.Mine.adapter.MessageAdapter;
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

    @BindView(R.id.no_internet_rl)
    RelativeLayout no_internet_rl;

    private HighRebateAdapter adapter;

    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_high_rebate,null);
    }

    @Override
    protected void initViews() {


        adapter=new HighRebateAdapter(getActivity());
        listview.setAdapter(adapter);
    }

    @Override
    protected void initData() {



    }

    @Override
    protected void initListener() {

    }
}
