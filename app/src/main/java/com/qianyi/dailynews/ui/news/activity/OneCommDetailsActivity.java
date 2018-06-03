package com.qianyi.dailynews.ui.news.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.ui.news.adapter.AllCommentAdapter;
import com.qianyi.dailynews.ui.news.bean.CommentBean;
import com.qianyi.dailynews.ui.news.views.KeyMapDailog;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.views.PullToRefreshView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/3.
 */

public class OneCommDetailsActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener,View.OnClickListener {
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.pulltorefreshView) public PullToRefreshView mPullToRefreshView;
    @BindView(R.id.listview) public ListView listview;

    //****************************************************
    private String newsID;
    private int page=1;
    private int pageSize=10;
    private int pageLevel2=1;//二级评论的也是
    private int pageSizeLevel2=10; //二级评论的页面大小
    private AllCommentAdapter allCommentAdapter;
    public KeyMapDailog dialog;
    //评论
    @BindView(R.id.re_comm) public RelativeLayout re_comm;
    private View footer;

    @Override
    protected void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("评论详情");

        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_onecomm_details);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }

    @Override
    public void onClick(View v) {

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
        page=1;
        mPullToRefreshView.setEnablePullTorefresh(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                OneCommDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       }
                });
            }
        }, 250);
        //请求成功
       /* if (list.size() < Integer.parseInt(Constants.PAGE_SIZE_STR)) {
            mPullToRefreshView.onFooterRefreshComplete(true);
        }else{
            mPullToRefreshView.onFooterRefreshComplete(false);
        }*/

    }

    private void moreData() {
        page++;
        mPullToRefreshView.setEnablePullTorefresh(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                OneCommDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        OneCommDetailsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });


                        mPullToRefreshView.onHeaderRefreshComplete();
                        mPullToRefreshView.onFooterRefreshComplete(false);
                    }
                });
            }
        }, 250);
    }
}
