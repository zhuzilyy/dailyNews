package com.qianyi.dailynews.ui.news.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.fragment.NewsFragment;
import com.qianyi.dailynews.ui.news.adapter.AllCommentAdapter;
import com.qianyi.dailynews.ui.news.adapter.HotCommentAdapterNews;
import com.qianyi.dailynews.ui.news.bean.CommPublishBean;
import com.qianyi.dailynews.ui.news.bean.CommentBean;
import com.qianyi.dailynews.ui.news.bean.NewsBean;
import com.qianyi.dailynews.ui.news.bean.NewsContentBean;
import com.qianyi.dailynews.ui.news.views.KeyMapDailog;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.views.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/1.
 */

public class MoreCommActivity extends BaseActivity  implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener,View.OnClickListener {
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.pulltorefreshView) public PullToRefreshView mPullToRefreshView;
    @BindView(R.id.listview) public ListView listview;
    //评论
    @BindView(R.id.re_comm) public RelativeLayout re_comm;
    private View footer;


    //****************************************************
    private String newsID;
    private int page=1;
    private int pageSize=10;
    private int pageLevel2=1;//二级评论的也是
    private int pageSizeLevel2=10; //二级评论的页面大小
    private AllCommentAdapter allCommentAdapter;
    public KeyMapDailog dialog;
    private List<CommentBean.CommentData.NewsCommentRes> BigList=new ArrayList<>();
    @Override
    protected void initViews() {
        newsID=getIntent().getStringExtra("newsID");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("全部评论");


        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);

        allCommentAdapter=new AllCommentAdapter(MoreCommActivity.this,BigList);
        listview.setAdapter(allCommentAdapter);



        //发表评论
        allCommentAdapter.setOnCommPublishListener(new AllCommentAdapter.CommPublishListener() {
            @Override
            public void commPublish(String id) {
                PublishComm(id);
            }
        });



    }

    @Override
    protected void initData() {
        firstData();

    }


    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_morecomm);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

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
                MoreCommActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String userid = (String) SPUtils.get(MoreCommActivity.this,"user_id","");
                        if(TextUtils.isEmpty(userid)){
                            return;
                        }
                        if(TextUtils.isEmpty(newsID)){
                            return;
                        }
                        ApiNews.GetNewsCommend(ApiConstant.NEWS_COMMENT, userid, newsID, page, pageSize, pageLevel2, pageSizeLevel2, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(Call call, Response response, String s) {
                                Log.i("SS",s);
                                Gson gson =new Gson();
                                CommentBean commentBean=gson.fromJson(s,CommentBean.class);
                                if(commentBean!=null){
                                    String code=commentBean.getCode();
                                    if("0000".equals(code)){
                                        CommentBean.CommentData commentData=commentBean.getData();
                                        List<CommentBean.CommentData.NewsCommentRes> newsCommentRes=commentData.getNewsCommentRes();
                                        if(newsCommentRes!=null&&newsCommentRes.size()>0.){
                                            BigList.clear();
                                            BigList.addAll(newsCommentRes);
                                            allCommentAdapter.notifyDataSetChanged();

                                        }

                                    }
                                }

                            }

                            @Override
                            public void onEror(Call call, int statusCode, Exception e) {
                                Log.i("SS",e.getMessage());
                            }
                        });

                        mPullToRefreshView.onHeaderRefreshComplete();

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
                MoreCommActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MoreCommActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String userid = (String) SPUtils.get(MoreCommActivity.this,"user_id","");
                                if(TextUtils.isEmpty(userid)){
                                    return;
                                }
                                if(TextUtils.isEmpty(newsID)){
                                    return;
                                }
                                ApiNews.GetNewsCommend(ApiConstant.NEWS_COMMENT, userid, newsID, page, pageSize, pageLevel2, pageSizeLevel2, new RequestCallBack<String>() {
                                    @Override
                                    public void onSuccess(Call call, Response response, String s) {
                                        Log.i("SS",s);
                                        Gson gson =new Gson();
                                        CommentBean commentBean=gson.fromJson(s,CommentBean.class);
                                        if(commentBean!=null){
                                            String code=commentBean.getCode();
                                            if("0000".equals(code)){
                                                CommentBean.CommentData commentData=commentBean.getData();
                                                List<CommentBean.CommentData.NewsCommentRes> newsCommentRes=commentData.getNewsCommentRes();
                                                if(newsCommentRes!=null&&newsCommentRes.size()>0.){
                                                    BigList.addAll(newsCommentRes);
                                                    allCommentAdapter.notifyDataSetChanged();

                                                }

                                            }
                                        }

                                    }

                                    @Override
                                    public void onEror(Call call, int statusCode, Exception e) {
                                        Log.i("SS",e.getMessage());
                                    }
                                });
                            }
                        });


                        mPullToRefreshView.onHeaderRefreshComplete();
                        mPullToRefreshView.onFooterRefreshComplete(false);
                    }
                });
            }
        }, 250);
    }

    /***
     * 发表2级评论
     * @param id
     */
    private void PublishComm(final String id) {

        dialog = new KeyMapDailog("发表评论：", new KeyMapDailog.SendBackListener() {
            @Override
            public void sendBack(final String inputText) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String userid = (String) SPUtils.get(MoreCommActivity.this,"user_id","");
                        if(TextUtils.isEmpty(userid)){
                            return;
                        }
                        if(TextUtils.isEmpty(newsID)){
                            return;
                        }
                        ApiNews.PublishNewsCommend(ApiConstant.PUBLISH_NEWS_COMMENT, userid, newsID, 1, id, 2+"", inputText, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(Call call, Response response, String s) {
                                dialog.hideProgressdialog();
                                dialog.dismiss();
                                Gson gson=new Gson();
                                CommPublishBean bean=gson.fromJson(s,CommPublishBean.class);
                                if(bean!=null){
                                    String code= bean.getCode();
                                    if("0000".equals(code)){
                                        CommPublishBean.CommPublisData data=bean.getData();
                                        if(data!=null){
                                            firstData();
                                        }
                                    }
                                }



                            }

                            @Override
                            public void onEror(Call call, int statusCode, Exception e) {
                                dialog.hideProgressdialog();
                                dialog.dismiss();
                                Log.i("ss",e.getMessage());
                            }
                        });


                    }
                }, 250);
            }
        });
        dialog.show(getSupportFragmentManager(), "dialog");
    }
    @OnClick({R.id.re_comm})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.re_comm:
                //发表一级评论
                PublishFristComm();
                break;
            default:
                break;
        }
    }
    /***
     * 发表一级评论
     */
    private void PublishFristComm() {

        dialog = new KeyMapDailog("发表评论：", new KeyMapDailog.SendBackListener() {
            @Override
            public void sendBack(final String inputText) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String userid = (String) SPUtils.get(MoreCommActivity.this,"user_id","");
                        if(TextUtils.isEmpty(userid)){
                            return;
                        }
                        if(TextUtils.isEmpty(newsID)){
                            return;
                        }
                        ApiNews.PublishNewsCommend(ApiConstant.PUBLISH_NEWS_COMMENT, userid, newsID, 1, 0+"", 1+"", inputText, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(Call call, Response response, String s) {
                                dialog.hideProgressdialog();
                                dialog.dismiss();
                                Log.i("ss",s);
                                Gson gson=new Gson();
                                CommPublishBean bean=gson.fromJson(s,CommPublishBean.class);
                                if(bean!=null){
                                    String code= bean.getCode();
                                    if("0000".equals(code)){
                                        CommPublishBean.CommPublisData data=bean.getData();
                                        if(data!=null){
                                           firstData();
                                        }
                                    }
                                }




                            }

                            @Override
                            public void onEror(Call call, int statusCode, Exception e) {
                                dialog.hideProgressdialog();
                                dialog.dismiss();
                                Log.i("ss",e.getMessage());
                            }
                        });


                    }
                }, 250);
            }
        });
        dialog.show(getSupportFragmentManager(), "dialog");
    }
}
