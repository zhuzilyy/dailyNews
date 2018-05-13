package com.qianyi.dailynews.ui.news.fragment;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;


import com.qianyi.dailynews.R;
import com.qianyi.dailynews.adapter.NewsAdapter;
import com.qianyi.dailynews.adapter.NewsAdapter;
import com.qianyi.dailynews.views.PullToRefreshView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class PageFragment extends LazyloadFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener{

    public PullToRefreshView mPullToRefreshView;
    public ListView listview;
    private NewsAdapter newsAdapter;

    @Override
    public int setContentView() {
        return R.layout.fragment_page;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void init() {
        listview = rootView.findViewById(R.id.listview);
        mPullToRefreshView = rootView.findViewById(R.id.pulltorefreshView);
        newsAdapter=new NewsAdapter(getActivity());
        listview.setAdapter(newsAdapter);

        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);

    }

    @Override
    public void lazyLoad() {
        Toast.makeText(mActivity, "lazyload....", Toast.LENGTH_SHORT).show();
        firstData();

    }


    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        moreData();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        firstData();
    }
    private void firstData() {
        //Toast.makeText(mActivity, "百元要刷新....", Toast.LENGTH_SHORT).show();
        mPullToRefreshView.setEnablePullTorefresh(true);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
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
                mActivity.runOnUiThread(new Runnable() {
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
