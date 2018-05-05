package com.qianyi.dailynews.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.adapter.TestAdapter;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.views.PullToRefreshView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/30.
 */

public class VideoFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.listview)
    ListView listview;
    private View newsView;
    private TestAdapter testAdapter;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        newsView =  inflater.inflate(R.layout.fragment_video, null);
        return newsView;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        testAdapter=new TestAdapter(getActivity());
        listview.setAdapter(testAdapter);
    }

    @Override
    protected void initListener() {
        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);
    }
    //刷新事件
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        firstData();
    }
    //加载事件
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        moreData();
    }
    private void firstData() {
        mPullToRefreshView.setEnablePullTorefresh(true);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.onHeaderRefreshComplete();
                    }
                });

            }
        }, 2000);
        //请求成功
       /* if (list.size() < Integer.parseInt(Constants.PAGE_SIZE_STR)) {
            mPullToRefreshView.onFooterRefreshComplete(true);
        }else{
            mPullToRefreshView.onFooterRefreshComplete(false);
        }*/

    }
    private void moreData() {
        mPullToRefreshView.setEnablePullTorefresh(true);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.onHeaderRefreshComplete();
                        mPullToRefreshView.onFooterRefreshComplete(false);
                    }
                });
            }
        }, 2000);
    }
}
