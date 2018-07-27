package com.qianyi.dailynews.ui.news.activity;

import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.orhanobut.logger.Logger;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.ui.news.adapter.AllCommentAdapter;
import com.qianyi.dailynews.ui.news.adapter.OneCommentAdapter;
import com.qianyi.dailynews.ui.news.bean.CommPublishBean;
import com.qianyi.dailynews.ui.news.bean.CommentBean;
import com.qianyi.dailynews.ui.news.bean.OneNewsBean;
import com.qianyi.dailynews.ui.news.views.KeyMapDailog;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.views.CircleImageView;
import com.qianyi.dailynews.views.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
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
    private OneCommentAdapter oneCommentAdapter;
    public KeyMapDailog dialog;
    private String commId;
    private boolean isFrist=true;

    private List<OneNewsBean.OneNewsData.NewsCommentLevel2Array> bigList=new ArrayList();
    //评论
    @BindView(R.id.re_comm) public RelativeLayout re_comm;
    private View head;

    @Override
    protected void initViews() {


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("评论详情");
        commId=getIntent().getStringExtra("commId");
        newsID=getIntent().getStringExtra("id");

        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);


        oneCommentAdapter=new OneCommentAdapter(bigList,OneCommDetailsActivity.this);
        listview.setAdapter(oneCommentAdapter);
    }

    @Override
    protected void initData() {
        firstData();
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
    @OnClick({R.id.re_comm})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.re_comm:
                PublishComm(commId);
                break;
            default:
                break;
        }

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
                        String userid = (String) SPUtils.get(OneCommDetailsActivity.this,"user_id","");
                        if(TextUtils.isEmpty(userid)){
                            return;
                        }

                        ApiNews.PublishNewsCommend(ApiConstant.PUBLISH_NEWS_COMMENT, userid, NewsDetailsActivity.newsId, 1, id, 2+"", inputText, new RequestCallBack<String>() {
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
                        if(!TextUtils.isEmpty(commId)){
                            String userid = (String) SPUtils.get(OneCommDetailsActivity.this,"user_id","");
                            if(TextUtils.isEmpty(userid)){
                                return;
                            }
                            ApiNews.GetOneNewsDetails(ApiConstant.NEWS_ONE, commId, userid, page+"", pageSize+"", new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(Call call, Response response, String s) {
                                    Log.i("ss",s);
                                    Gson gson = new Gson();
                                    OneNewsBean newsBean=gson.fromJson(s,OneNewsBean.class);
                                    if(newsBean!=null){
                                       String code=  newsBean.getCode();
                                       if("0000".equals(code)){
                                           OneNewsBean.OneNewsData newsData= newsBean.getData();
                                           if(newsData!=null){

                                               setNewsComm(newsData);
                                               List<OneNewsBean.OneNewsData.NewsCommentLevel2Array> arrays=newsData.getNewsCommentLevel2Array();

                                               bigList.clear();
                                               bigList.addAll(arrays);
                                               oneCommentAdapter.notifyDataSetChanged();

                                           }
                                       }
                                    }

                                }

                                @Override
                                public void onEror(Call call, int statusCode, Exception e) {
                                    Log.i("ee",e.getMessage());
                                }
                            });
                            mPullToRefreshView.onHeaderRefreshComplete();


                        }

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

    /***
     * 赋值head 一级评论
     * @param newsData
     *
     */
    private void setNewsComm(OneNewsBean.OneNewsData newsData) {
        head= LayoutInflater.from(OneCommDetailsActivity.this).inflate(R.layout.lay_onecomment_head,null);

        CircleImageView touxiang=head.findViewById(R.id.newsComm_head);
        final TextView zan_tv=head.findViewById(R.id.newsComm_zan_tv);
        final ImageView zan_iv=head.findViewById(R.id.newsComm_zan_iv);
        LinearLayout zan_ll=head.findViewById(R.id.newsComm_zan_ll);
        TextView name=head.findViewById(R.id.newsComm_name);
        TextView time=head.findViewById(R.id.newsComm_time);
        TextView content=head.findViewById(R.id.newsComm_content);
        LinearLayout ll_SecondComm=head.findViewById(R.id.ll_SecondComm);

        //一级评论
        Glide.with(OneCommDetailsActivity.this).load(newsData.getHeadPortrait()).placeholder(R.mipmap.touxiang2).into(touxiang);
     //   name.setText(newsData.getName()==null?newsData.getUserName():newsData.getName());
        if(TextUtils.isEmpty(newsData.getName())){
            String phonenum = newsData.getUserName();
            if(!TextUtils.isEmpty(phonenum) && phonenum.length() > 6 ){
                StringBuilder sb  =new StringBuilder();
                for (int i = 0; i < phonenum.length(); i++) {
                    char c = phonenum.charAt(i);
                    if (i >= 3 && i <= 6) {
                        sb.append('*');
                    } else {
                        sb.append(c);
                    }
                }
                name.setText(sb.toString());
            }
        }else {
            name.setText(newsData.getName());
        }

        content.setText(newsData.getComment());
        time.setText(newsData.getTime());
        zan_tv.setText(newsData.getLike());

        if("true".equals(newsData.getIlike())){
            zan_iv.setImageResource(R.mipmap.houshou);
            zan_tv.setTextColor(Color.parseColor("#ff0000"));
        }


        //点赞
        zan_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid = (String) SPUtils.get(OneCommDetailsActivity.this,"user_id","");
                if(TextUtils.isEmpty(userid)){
                    return;
                }
                ApiNews.CommLike(ApiConstant.COMMENT_LIKE, commId, userid, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(Call call, Response response, String s) {
                        Logger.i(s);

                        try {
                            JSONObject jsonObject= new JSONObject(s);
                            String code=jsonObject.getString("code");
                            if("0000".equals(code)){
                                zan_tv.setText((Integer.parseInt(zan_tv.getText().toString().trim())+1)+"");
                                zan_iv.setImageResource(R.mipmap.houshou);
                                zan_tv.setTextColor(Color.parseColor("#ff0000"));
                            }else if("0008".equals(code)){
                                Toast.makeText(OneCommDetailsActivity.this, "您已点过赞了", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onEror(Call call, int statusCode, Exception e) {
                        Log.i("ss",e.getMessage());
                    }
                });



            }
        });




        if(isFrist){
            listview.addHeaderView(head);
            isFrist=false;
        }







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
