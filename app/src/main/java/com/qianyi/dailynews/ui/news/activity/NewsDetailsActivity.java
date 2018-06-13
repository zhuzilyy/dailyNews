package com.qianyi.dailynews.ui.news.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.adapter.NewsAdapter;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.fragment.NewsFragment;
import com.qianyi.dailynews.ui.news.adapter.HotCommentAdapterNews;
import com.qianyi.dailynews.ui.news.adapter.NewsDetailsAdapter;
import com.qianyi.dailynews.ui.news.bean.CommPublishBean;
import com.qianyi.dailynews.ui.news.bean.CommentBean;
import com.qianyi.dailynews.ui.news.bean.NewsBean;
import com.qianyi.dailynews.ui.news.bean.NewsContentBean;
import com.qianyi.dailynews.ui.news.views.KeyMapDailog;
import com.qianyi.dailynews.ui.news.views.MySingListView;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.WebviewUtil;

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
 * Created by Administrator on 2018/5/29.
 */

public class NewsDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.tv_money) public TextView tv_money;
    @BindView(R.id.re_money) public RelativeLayout re_money;
    @BindView(R.id.news_webview) public WebView news_webview;
    @BindView(R.id.pb_webview) public ProgressBar pb_webview;
    @BindView(R.id.tv_news_more) public TextView tv_news_more;
    @BindView(R.id.re_newsmore) public RelativeLayout re_newsmore;
    @BindView(R.id.bottom_web) public WebView bottom_web;
    @BindView(R.id.top_web_re) public RelativeLayout top_web_re;
    @BindView(R.id.lv_) public MySingListView lv;
    @BindView(R.id.lv_comment) public MySingListView lv_comment;
    @BindView(R.id.sc) public NestedScrollView sc;
    @BindView(R.id.ll_share) public LinearLayout ll_share;
    @BindView(R.id.ll_friendCircle) public LinearLayout ll_friendCircle;//分享到盆友圈
    @BindView(R.id.ll_QQ) public LinearLayout ll_QQ;//分享到QQ
    @BindView(R.id.ll_wechat) public LinearLayout ll_wechat;//分享到微信
    @BindView(R.id.ll_weibo) public LinearLayout ll_weibo;//分享到微博

    //****************************
    //评论
    @BindView(R.id.re_comm) public RelativeLayout re_comm;
    public KeyMapDailog dialog;
    private View footer;
    private NewsDetailsAdapter adapter;
    private HotCommentAdapterNews commentAdapterNews;

    //相关推荐
    private NewsAdapter newsAdapter;
    private List<NewsBean> bigCommendList=new ArrayList<>();

    //***********************************
    private String titleStr;
    private String urlStr;
    private WebSettings webSettings;
    public static String newsId="";
    private int page=1;
    private int pageSize=10;
    private int pageLevel2=1;//二级评论的也是
    private int pageSizeLevel2=10; //二级评论的页面大小
    ///**********************************
    //计时
    private boolean timeOut=false;
    private boolean readMore=false;
    @Override
    protected void initViews() {
        newsId=getIntent().getStringExtra("id");
        urlStr=getIntent().getStringExtra("url");
        if(!TextUtils.isEmpty(newsId)){
            //先调阅读新闻
            readNews(newsId);
        }
        //计时
        startTimer();

        tv_money.setText("+"+NewsFragment.Gold);
        if(!TextUtils.isEmpty(urlStr)){
            webSettings = news_webview.getSettings();
            WebviewUtil.setWebview(news_webview,webSettings);
            news_webview.loadUrl(urlStr);
            WebviewUtil.setWebview(bottom_web,webSettings);
            bottom_web.loadUrl(urlStr);
        }




    }

    /***
     * 到详情界面先调阅读新闻接口
     * @param newsId
     */
    private void readNews(String newsId) {
        String userid = (String) SPUtils.get(NewsDetailsActivity.this,"user_id","");
        if(TextUtils.isEmpty(userid)){
            return;
        }
        ApiNews.ReadNews(ApiConstant.READ_NEWS, userid, newsId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                Log.i("ss",s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    if("0000".equals(code)){

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

    private void startTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                timeOut=true;
                getReward();
            }
        },15*1000);


        }

    @Override
    protected void initData() {
        // 加载webview
        loadingWebview(news_webview);
        loadingWebview(bottom_web);
        //获取相关推荐
        getRecommend();
        //获取热门评论
        getCommend();
    }
    /****
     * 获得相关推荐
     */
    private void getRecommend() {
        ApiNews.GetRemmond(ApiConstant.NEWS_WONDERFUL_REMMOND, 5+"", 5+"", new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                Log.i("ss",s);
                Gson gson = new Gson();
                NewsContentBean contentBean = gson.fromJson(s, NewsContentBean.class);
                if (contentBean != null) {
                    String code = contentBean.getCode();
                    if ("0000".equals(code)) {
                        NewsContentBean.NewsContentData contentData = contentBean.getData();
                        if (contentData != null){
                            List<NewsContentBean.NewsContentData.NewsByType> newsByTypes = contentData.getNewsByType();
                            if (newsByTypes.size() > 0) {
                                List<NewsContentBean.NewsContentData.AdavertContent> adavertContents = contentData.getAdvertArray();
                                List<NewsContentBean.NewsContentData.NewsByType.NewsContentInfo> newsContentInfos = newsByTypes.get(0).getNewsInfoArray();
                                if (adavertContents.size() > 0 || newsContentInfos.size() > 0) {
                                    List<NewsBean> newsBeans = dowithNews(adavertContents, newsContentInfos);
                                    bigCommendList.clear();
                                    bigCommendList.addAll(newsBeans);
                                    newsAdapter=new NewsAdapter(NewsDetailsActivity.this,bigCommendList, "0");
                                    lv.setAdapter(newsAdapter);
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent=new Intent(NewsDetailsActivity.this,NewsDetailsActivity.class);
                                            intent.putExtra("title",bigCommendList.get(position).getTitle());
                                            intent.putExtra("url",bigCommendList.get(position).getUrl());
                                            intent.putExtra("id",bigCommendList.get(position).getId());
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("ss",e.getMessage());
            }
        });
    }
    /**
     * 将新闻和广告按11排列
     *
     * @param adavertContents
     * @param newsContentInfos
     * @return
     */
    private List<NewsBean> dowithNews(List<NewsContentBean.NewsContentData.AdavertContent> adavertContents, List<NewsContentBean.NewsContentData.NewsByType.NewsContentInfo> newsContentInfos) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        boolean isNews = newsContentInfos.size() > 0 ? true : false;
        boolean isAd = isNews == true ? false : true;
        int size = (adavertContents.size() + newsContentInfos.size());
        for (int i = 1; i <= size; i++) {
            if (isNews) {
                if ((newsContentInfos.size() > 0)) {
                    for (int j = 0; j < newsContentInfos.size(); j++) {
                        NewsBean newsBean = new NewsBean();
                        NewsContentBean.NewsContentData.NewsByType.NewsContentInfo news = newsContentInfos.get(j);
                        newsBean.setId(news.getId());
                        newsBean.setPublishDate(news.getPublishDate());
                        newsBean.setPosterScreenName(news.getPosterScreenName());
                        newsBean.setUrl(news.getUrl());
                        newsBean.setTitle(news.getTitle());
                        newsBean.setPosterId(news.getPosterId());
                        newsBean.setViewCount(news.getViewCount());
                        newsBean.setContent(news.getContent());
                        newsBean.setImgsUrl(news.getImgsUrl());
                        newsBean.setIfRead(news.getIfRead());
                        newsBean.setNewsType(news.getNewsTyps());
                        newsBeanList.add(newsBean);
                        newsContentInfos.remove(0);
                        break;
                    }
                    if (0 == 0) {
                        isAd = true;
                        isNews = false;
                    }
                    continue;
                } else {
                    isAd = true;
                    isNews = false;
                }
            } else if (isAd) {
                if ((adavertContents.size() > 0)) {
                    for (int j = 0; j < adavertContents.size(); j++) {
                        NewsBean newsBean = new NewsBean();
                        NewsContentBean.NewsContentData.AdavertContent ad = adavertContents.get(j);
                        newsBean.setId(ad.getId());
                        newsBean.setTitle(ad.getTitle());
                        newsBean.setUrl(ad.getUrl());
                        newsBean.setReadNum(ad.getReadNum());
                        newsBean.setImgs(ad.getImgs());
                        newsBean.setAdType(ad.getAdType());
                        newsBeanList.add(newsBean);
                        adavertContents.remove(0);
                        break;
                    }
                    isAd = false;
                    isNews = true;
                    continue;
                } else {
                    for (int j = 0; j < newsContentInfos.size(); j++) {
                        NewsBean newsBean = new NewsBean();
                        NewsContentBean.NewsContentData.NewsByType.NewsContentInfo news = newsContentInfos.get(j);
                        newsBean.setId(news.getId());
                        newsBean.setPublishDate(news.getPublishDate());
                        newsBean.setPosterScreenName(news.getPosterScreenName());
                        newsBean.setUrl(news.getUrl());
                        newsBean.setTitle(news.getTitle());
                        newsBean.setPosterId(news.getPosterId());
                        newsBean.setViewCount(news.getViewCount());
                        newsBean.setContent(news.getContent());
                        newsBean.setImgsUrl(news.getImgsUrl());
                        newsBean.setIfRead(news.getIfRead());
                        newsBean.setNewsType(news.getNewsTyps());
                        newsBeanList.add(newsBean);
                        newsContentInfos.remove(0);
                        break;
                    }
                    isAd = false;
                    isNews = true;
                }
            }
        }
        return newsBeanList;
    }
    /***
     * 获取热门评论
     */
    private void getCommend() {
        String userid = (String) SPUtils.get(NewsDetailsActivity.this,"user_id","");
        if(TextUtils.isEmpty(userid)){
          return;
        }
        if(TextUtils.isEmpty(newsId)){
            return;
        }
        ApiNews.GetNewsCommend(ApiConstant.NEWS_COMMENT, userid, newsId, page, pageSize, pageLevel2, pageSizeLevel2, new RequestCallBack<String>() {
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
                            //赋值热门评论
                            setCommentData(newsCommentRes);
                        }
                    }
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
              //  Log.i("SS",e.getMessage());
            }
        });
    }
    /***
     * 赋值热门评论
     * @param newsCommentRes
     */
    private void setCommentData(final List<CommentBean.CommentData.NewsCommentRes> newsCommentRes) {
        commentAdapterNews =new HotCommentAdapterNews(NewsDetailsActivity.this,newsCommentRes);
        lv_comment.setAdapter(commentAdapterNews);
       if(newsCommentRes.size()>5){
           if(!lv_comment.removeFooterView(footer)){
               lv_comment.removeFooterView(footer);
           }
           footer= LayoutInflater.from(NewsDetailsActivity.this).inflate(R.layout.lay_news_comm_footer,null);
           lv_comment.addFooterView(footer);
           footer.findViewById(R.id.footer_more).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=new Intent(NewsDetailsActivity.this,MoreCommActivity.class);
                   intent.putExtra("newsID",newsId);
                   startActivity(intent);
               }
           });
       }
        //发表评论
        commentAdapterNews.setOnCommPublishListener(new HotCommentAdapterNews.CommPublishListener() {
            @Override
            public void commPublish(String id) {
                PublishComm(id);
            }
        });
    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_newsdetails);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    private void loadingWebview(WebView news_webview) {
        news_webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (pb_webview!=null){
                    pb_webview.setProgress(newProgress);
                    if(newProgress==100){
                        pb_webview.setVisibility(View.GONE);
                        tv_news_more.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }
    @OnClick({R.id.iv_back,R.id.tv_news_more,R.id.re_comm,
            R.id.ll_wechat,R.id.ll_friendCircle,R.id.ll_QQ,R.id.ll_weibo})
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ll_wechat:
                //微信分享
                Toast.makeText(this, "微信分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_friendCircle:
                //朋友圈分享
                Toast.makeText(this, "朋友圈分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_QQ:
                //QQ分享
                Toast.makeText(this, "QQ分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_weibo:
                //微博分享
                Toast.makeText(this, "微博分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_back:
                finish();
            break;
            case R.id.tv_news_more:
                re_newsmore.setVisibility(View.GONE);
                top_web_re.setVisibility(View.GONE);
                bottom_web.setVisibility(View.VISIBLE);
                ll_share.setVisibility(View.VISIBLE);
                sc.fullScroll(View.FOCUS_UP);
                readMore=true;
                //获得奖励
                getReward();

                break;
            case R.id.re_comm:
                //发表一级评论
                PublishFristComm();
                break;
            default:
            break;
        }
    }

    /***
     * 点击阅读更多，并阅读15秒获得奖励
     */
    private void getReward() {
        if(timeOut&&readMore){
            getReward2();
        }
    }

    /***
     * 符合规则，获取阅读奖励
     */
    private void getReward2() {
        String userid = (String) SPUtils.get(NewsDetailsActivity.this,"user_id","");
        if(TextUtils.isEmpty(userid)){
            return;
        }
        ApiNews.GetRewardAfterReadNews(ApiConstant.GET_REWARD_AFTER_READ_NEWS, userid, newsId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                Log.i("sss",s);
                try {
                    JSONObject jsonObject =new JSONObject(s);
                    String code=jsonObject.getString("code");
                    if("0000".equals(code)){
                        //更新阅读奖励数
                        sendBroadcast(new Intent("getRewardOk"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("sss",e.getMessage());
            }
        });
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
                        String userid = (String) SPUtils.get(NewsDetailsActivity.this,"user_id","");
                        if(TextUtils.isEmpty(userid)){
                            return;
                        }
                        if(TextUtils.isEmpty(newsId)){
                            return;
                        }
                        ApiNews.PublishNewsCommend(ApiConstant.PUBLISH_NEWS_COMMENT, userid, newsId, 1, 0+"", 1+"", inputText, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(Call call, Response response, final String s) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
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
                                                    getCommend();
                                                }
                                            }
                                        }
                                    }
                                });

                            }
                            @Override
                            public void onEror(Call call, int statusCode, final Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.hideProgressdialog();
                                        dialog.dismiss();
                                        Log.i("ss",e.getMessage());
                                    }
                                });

                            }
                        });
                    }
                }, 250);
            }
        });
        dialog.show(getSupportFragmentManager(), "dialog");
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
                        String userid = (String) SPUtils.get(NewsDetailsActivity.this,"user_id","");
                        if(TextUtils.isEmpty(userid)){
                            return;
                        }
                        if(TextUtils.isEmpty(newsId)){
                            return;
                        }
                        ApiNews.PublishNewsCommend(ApiConstant.PUBLISH_NEWS_COMMENT, userid, newsId, 1, id, 2+"", inputText, new RequestCallBack<String>() {
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
                                            getCommend();
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