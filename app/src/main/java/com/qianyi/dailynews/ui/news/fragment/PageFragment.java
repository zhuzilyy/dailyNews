package com.qianyi.dailynews.ui.news.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import com.qianyi.dailynews.R;
import com.qianyi.dailynews.adapter.NewsAdapter;
import com.qianyi.dailynews.adapter.NewsAdapter;
import com.qianyi.dailynews.fragment.NewsFragment;
import com.qianyi.dailynews.ui.news.bean.NewsTitleBean;
import com.qianyi.dailynews.views.PullToRefreshView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class PageFragment extends LazyloadFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener{

    public PullToRefreshView mPullToRefreshView;
    public ListView listview;
    private NewsAdapter newsAdapter;
    private List<NewsTitleBean.NewsTitleData.NewsTypeRes> newsTypeRes ;


    public PageFragment (){

    }

    @SuppressLint("ValidFragment")
    public PageFragment(List<NewsTitleBean.NewsTitleData.NewsTypeRes> newsTypeRes) {
        this.newsTypeRes= newsTypeRes;
    }

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
        int position = NewsFragment.CurrentNewsTitle;
        Toast.makeText(mActivity, "NewsFragment.CurrentNewsTitle==" +NewsFragment.CurrentNewsTitle, Toast.LENGTH_SHORT).show();
        firstData(position);

    }


    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        moreData();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        firstData(NewsFragment.CurrentNewsTitle);
    }
    private void firstData(int position) {

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
