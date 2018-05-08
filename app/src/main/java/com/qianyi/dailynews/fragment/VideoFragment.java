package com.qianyi.dailynews.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.adapter.TestAdapter;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.ui.video.VideoPlayingActivity;
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
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
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
        iv_back.setVisibility(View.GONE);
        tv_title.setText("视频");
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
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),VideoPlayingActivity.class);
                startActivity(intent);
            }
        });
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
