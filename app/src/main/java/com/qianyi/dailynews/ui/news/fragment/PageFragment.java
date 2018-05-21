package com.qianyi.dailynews.ui.news.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.adapter.NewsAdapter;
import com.qianyi.dailynews.adapter.NewsAdapter;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.api.ApiVideo;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.fragment.bean.VideoBean;
import com.qianyi.dailynews.fragment.bean.VideoInfo;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.views.PullToRefreshView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class PageFragment extends LazyloadFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener{
    public PullToRefreshView mPullToRefreshView;
    public ListView listview;
    private NewsAdapter newsAdapter;
    private String user_id;
    private int page=1;
    private boolean isRefresh;
    private CustomLoadingDialog customLoadingDialog;
    private RelativeLayout no_data_rl,no_internet_rl;
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
        customLoadingDialog=new CustomLoadingDialog(getActivity());
        listview = rootView.findViewById(R.id.listview);
        mPullToRefreshView = rootView.findViewById(R.id.pulltorefreshView);
        no_data_rl = rootView.findViewById(R.id.no_data_rl);
        no_internet_rl = rootView.findViewById(R.id.no_internet_rl);
        newsAdapter=new NewsAdapter(getActivity());
        listview.setAdapter(newsAdapter);
        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);
        user_id= (String) SPUtils.get(getActivity(),"user_id","");
    }
    @Override
    public void lazyLoad() {
        page=1;
        //getData(page);
    }
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        page++;
        //getData(page);
    }
    private void getData(int page) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        ApiNews.getNews(ApiConstant.NEWS, user_id, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                Toast.makeText(getActivity(), s+"====99====", Toast.LENGTH_SHORT).show();
                Log.i("tag",s);
                customLoadingDialog.dismiss();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        VideoBean videoBean = gson.fromJson(s, VideoBean.class);
                        String code = videoBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS_CODE)){
                            mPullToRefreshView.onHeaderRefreshComplete();
                            List<VideoInfo> list = videoBean.getData().getVideos();
                            if (list!=null && list.size()>0){
                                //判断是不是刷新
                                if (isRefresh){
                                    //infoList.clear();
                                    isRefresh=false;
                                }
                                mPullToRefreshView.setVisibility(View.VISIBLE);
                                no_data_rl.setVisibility(View.GONE);
                                no_internet_rl.setVisibility(View.GONE);
                               /* infoList.addAll(list);
                                videoAdapter.notifyDataSetChanged();*/
                                //判断是不是没有更多数据了
                                if (list.size() < Integer.parseInt(ApiConstant.PAGE_SIZE)) {
                                    mPullToRefreshView.onFooterRefreshComplete(true);
                                }else{
                                    mPullToRefreshView.onFooterRefreshComplete(false);
                                }
                            }else{
                                mPullToRefreshView.setVisibility(View.GONE);
                                no_data_rl.setVisibility(View.VISIBLE);
                                no_internet_rl.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                mPullToRefreshView.setVisibility(View.GONE);
                no_data_rl.setVisibility(View.GONE);
                no_internet_rl.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        getData(page);
    }

}
