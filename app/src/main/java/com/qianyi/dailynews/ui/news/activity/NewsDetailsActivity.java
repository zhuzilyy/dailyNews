package com.qianyi.dailynews.ui.news.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.qianyi.dailynews.application.MyApplication;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.SelfDialog;
import com.qianyi.dailynews.fragment.NewsFragment;
import com.qianyi.dailynews.ui.Mine.activity.SettingsActivity;
import com.qianyi.dailynews.ui.WebviewActivity;
import com.qianyi.dailynews.ui.account.activity.LoginActivity;
import com.qianyi.dailynews.ui.news.adapter.HotCommentAdapterNews;
import com.qianyi.dailynews.ui.news.adapter.NewsDetailsAdapter;
import com.qianyi.dailynews.ui.news.bean.CommPublishBean;
import com.qianyi.dailynews.ui.news.bean.CommentBean;
import com.qianyi.dailynews.ui.news.bean.NewsBean;
import com.qianyi.dailynews.ui.news.bean.NewsContentBean;
import com.qianyi.dailynews.ui.news.views.KeyMapDailog;
import com.qianyi.dailynews.ui.news.views.MySingListView;
import com.qianyi.dailynews.ui.news.views.NoScrollWebView;
import com.qianyi.dailynews.utils.RegUtil;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.SoundUtils;
import com.qianyi.dailynews.utils.WebviewUtil;
import com.qianyi.dailynews.utils.WhiteBgBitmapUtil;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.statistic.WBAgent;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
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
    @BindView(R.id.tv_money2) public TextView tv_money2;
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

    @BindView(R.id.iv_getReward) public ImageView iv_getReward; //获得奖励的动画

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
    private String titleStr,subTitle;
    private String urlStr;
    private String contentStr;
    private WebSettings webSettings;
    public static String newsId="";
    private int page=1;
    private int pageSize=10;
    private int pageLevel2=1;//二级评论的也是
    private int pageSizeLevel2=10; //二级评论的页面大小

    private IWXAPI mWxApi;
    private WbShareHandler shareHandler;

    //红包奖励数
    private String redMoney;
    //已读未读
    private String ifread;
    //是否是红包新闻
    private String isRed;
    private  Timer timer;


    ///**********************************
    //计时
    private boolean timeOut=false;
    private boolean readMore=false;
    private static final String APP_ID = "101488066"; //获取的APPID
    private Tencent mTencent;
    private String jsStr = "";
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initViews() {
        mWxApi = WXAPIFactory.createWXAPI(this, ApiConstant.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(ApiConstant.APP_ID);
        newsId=getIntent().getStringExtra("id");
        urlStr=getIntent().getStringExtra("url");
        contentStr=getIntent().getStringExtra("des");
        contentStr=RegUtil.replaceSpecialStr(contentStr);
        if(contentStr.length()>51){
            contentStr=contentStr.substring(0,50);
        }
        redMoney=getIntent().getStringExtra("redMoney");
        subTitle=getIntent().getStringExtra("title");
        ifread=getIntent().getStringExtra("ifread");
        isRed=getIntent().getStringExtra("isRed");

        if(redMoney!=null){
            double redMoneyStr=Double.parseDouble(redMoney);
            if(redMoneyStr>0){
                re_money.setVisibility(View.VISIBLE);
                tv_money2.setText("+"+redMoneyStr+"");
            }else {
                re_money.setVisibility(View.GONE);
            }
        }

        if(!TextUtils.isEmpty(newsId)){
            //先调阅读新闻
            readNews(newsId);
        }
        //计时
        startTimer();

      //  tv_money.setText("+"+NewsFragment.Gold);
        if(!TextUtils.isEmpty(urlStr)){
            webSettings = news_webview.getSettings();
            WebviewUtil.setWebview(news_webview,webSettings);
            news_webview.loadUrl(urlStr);
            WebSettings webSettings2 = bottom_web.getSettings();
            WebviewUtil.setWebview(bottom_web,webSettings2);
            bottom_web.loadUrl(urlStr);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://meirisubao.cqlianbei.com/js/toutiao.js");
                        // 打开该URL对应的资源的输入流
                        InputStream in = url.openStream();
                        //InputStream in = getAssets().open("guolv.js");
                        byte buff[] = new byte[1024];
                        ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
                        do {
                            int numRead = in.read(buff);
                            if (numRead <= 0) {
                                break;
                            }
                            fromFile.write(buff, 0, numRead);
                        } while (true);
                        jsStr = fromFile.toString();
                        //jsStr= "http://meirisubao.cqlianbei.com/js/toutiao.js\"";
                        in.close();
                        fromFile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            bottom_web.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //view.loadUrl(url);
                    return false;
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if(!TextUtils.isEmpty(jsStr)&&bottom_web!=null){
                        bottom_web.loadUrl("javascript:" + jsStr );
                    }

                    //bottom_web.loadUrl("http://meirisubao.cqlianbei.com/js/toutiao.js");
                }
            });
        }
        news_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(!TextUtils.isEmpty(jsStr)&&news_webview!=null){
                    news_webview.loadUrl("javascript:" + jsStr );
                }

                //news_webview.loadUrl("http://meirisubao.cqlianbei.com/js/toutiao.js");
            }
        });
        mTencent = Tencent.createInstance(APP_ID,getApplicationContext());
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
            public void onEror(Call call, int statusCode, final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      //  Log.i("ss",e.getMessage());
                    }
                });

            }
        });

    }

    private void startTimer() {
        timer= new Timer();
        timer.schedule(new TimerTask() {
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
                                    if(lv!=null){
                                        lv.setAdapter(newsAdapter);
                                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String adType = bigCommendList.get(position).getAdType();
                                                if(adType!=null){
                                                    Intent intent=new Intent(NewsDetailsActivity.this,WebviewActivity.class);
                                                    intent.putExtra("url",bigCommendList.get(position).getUrl());
                                                    intent.putExtra("title","广告");
                                                    startActivity(intent);
                                                }else {
                                                    Intent intent=new Intent(NewsDetailsActivity.this,NewsDetailsActivity.class);
//                                                intent.putExtra("title",bigCommendList.get(position).getTitle());
//                                                intent.putExtra("url",bigCommendList.get(position).getUrl());
//                                                intent.putExtra("id",bigCommendList.get(position).getId());

                                                    intent.putExtra("title", bigCommendList.get(position).getTitle());
                                                    intent.putExtra("url", bigCommendList.get(position).getUrl());
                                                    intent.putExtra("des", bigCommendList.get(position).getContent());
                                                    intent.putExtra("id", bigCommendList.get(position).getId());
                                                    intent.putExtra("redMoney", bigCommendList.get(position).getRedMoney());
                                                    intent.putExtra("ifread", bigCommendList.get(position).getIfRead());
                                                    intent.putExtra("isRed",bigCommendList.get(position).getRedpackage());


                                                    startActivity(intent);
                                                }



                                                finish();
                                            }
                                        });
                                    }

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
        List<NewsBean> newsBeansAd = DoWithNewsForAD(adavertContents);
        List<NewsBean> newsBeansNews = DoWithNewsForNEWS(newsContentInfos);
        newsBeanList = dowithNews2(newsBeansAd, newsBeansNews);
        return newsBeanList;
    }

    /***
     * 获得金币的动画（俗称金币洒落的动画）
     */
    public void CoinAnimator(int type, final View view){
        if(1==type){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //进入动画
                    if(view!=null){
                        view.setVisibility(View.VISIBLE);
                        AnimatorSet animatorSet = new AnimatorSet();//组合动画
                        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", 0.01f, 0.1f, 0.2f, 0.5f, 1f);
                        //缩放
                        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0, 150.0f);
                        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0, 150.0f);
                        animatorSet.setDuration(800);
                        animatorSet.setInterpolator(new LinearInterpolator());
                        animatorSet.playTogether(scaleX,scaleY,anim);
                        animatorSet.start();

                    }

                }
            });


        }else if(2==type){
            //退出动画
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //进入动画
                    if(view!=null){
                        AnimatorSet animatorSet = new AnimatorSet();//组合动画
                        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.8f, 0.5f, 0.2f, 0.01f);
                        //缩放
                        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 150.0f, 0.0f);
                        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 150.0f, 0.0f);
                        animatorSet.setDuration(800);
                        animatorSet.setInterpolator(new LinearInterpolator());
                        animatorSet.playTogether(scaleX,scaleY,anim);
                        animatorSet.start();
                    }


                }
            });
        }
    }



    /***
     *
     * @param newsBeansAd
     * @param newsBeansNews
     * @return
     */
    private List<NewsBean> dowithNews2(List<NewsBean> newsBeansAd, List<NewsBean> newsBeansNews) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        int size = newsBeansAd.size() + newsBeansNews.size();

        for (int i = 0; i < size; i++) {
            if (((i + 1) % 2) == 0) {
                if (newsBeansAd.size() > 0) {
                    newsBeanList.add(newsBeansAd.get(0));
                    newsBeansAd.remove(0);
                }
            } else {
                if (newsBeansNews.size() > 0) {
                    newsBeanList.add(newsBeansNews.get(0));
                    newsBeansNews.remove(0);
                } else {
                    return newsBeanList;
                }
            }
        }
        return newsBeanList;
    }


    /**
     * 处理新闻
     *
     * @param newsContentInfos
     * @return
     */
    private List<NewsBean> DoWithNewsForNEWS(List<NewsContentBean.NewsContentData.NewsByType.NewsContentInfo> newsContentInfos) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        for (int i = 0; i < newsContentInfos.size(); i++) {
            NewsBean newsBean = new NewsBean();
            NewsContentBean.NewsContentData.NewsByType.NewsContentInfo news = newsContentInfos.get(i);
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
            newsBean.setRedpackage(news.getRedpackage());
            newsBean.setRedMoney(news.getRedMoney());
            newsBeanList.add(newsBean);
        }
        return newsBeanList;
    }

    /**
     * 处理广告
     *
     * @param adavertContents
     * @return
     */
    private List<NewsBean> DoWithNewsForAD(List<NewsContentBean.NewsContentData.AdavertContent> adavertContents) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        for (int i = 0; i < adavertContents.size(); i++) {
            NewsBean newsBean = new NewsBean();
            NewsContentBean.NewsContentData.AdavertContent ad = adavertContents.get(i);
            newsBean.setId(ad.getId());
            newsBean.setTitle(ad.getTitle());
            newsBean.setUrl(ad.getUrl());
            newsBean.setReadNum(ad.getReadNum());
            newsBean.setImgs(ad.getImgs());
            newsBean.setAdType(ad.getAdType());
            newsBeanList.add(newsBean);
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
                shareFriends();
                break;
            case R.id.ll_friendCircle:
                //朋友圈分享
                shareFriendCircle();
                break;
            case R.id.ll_QQ:
                shareQQ();
                //QQ分享
                break;
            case R.id.ll_weibo:
                //微博分享
                shareWeiBo();
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
                //重写底部webview 的高度
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) news_webview.getLayoutParams();
                //layoutParams.height=RelativeLayout.LayoutParams.WRAP_CONTENT;

                int  mWebViewTotalHeight = news_webview.getHeight() - sc.getHeight();
                layoutParams.height=mWebViewTotalHeight;
                news_webview.setLayoutParams(layoutParams);
                //获得奖励
                getReward();

                break;
            case R.id.re_comm:

                String userid = (String) SPUtils.get(NewsDetailsActivity.this,"user_id","");
                if(TextUtils.isEmpty(userid)){
                    showLogin();
                    return;
                }

                //发表一级评论
                PublishFristComm();
                break;
            default:
            break;
        }
    }
    //QQ分享
    private void shareQQ() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "每日速报");//分享标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,contentStr);//要分享的内容摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,urlStr);//内容地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,ApiConstant.QQ_SHARE_LOGO);//分享的图片URL
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "每日速报");//应用名称
        mTencent.shareToQQ(this, params, new ShareUiListener());
    }
    /**
     * 自定义监听器实现IUiListener，需要3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class ShareUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {

        }
        @Override
        public void onError(UiError uiError) {
            //分享失败
        }
        @Override
        public void onCancel() {
            //分享取消

        }
    }

    private void showLogin() {
        final SelfDialog quitDialog = new SelfDialog(NewsDetailsActivity.this);
        quitDialog.setTitle("提示");
        quitDialog.setMessage("请先登录");
        quitDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                Intent intent=new Intent(NewsDetailsActivity.this, LoginActivity.class);
                startActivity(intent);
                quitDialog.dismiss();
            }
        });
        quitDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                quitDialog.dismiss();
            }
        });
        quitDialog.show();
    }

    /***
     * 点击阅读更多，并阅读15秒获得奖励
     */
    private void getReward() {
        String userid = (String) SPUtils.get(NewsDetailsActivity.this,"user_id","");
        if(TextUtils.isEmpty(userid)){
            return;
        }
        if("1".equals(ifread)){
            //该新闻是已读，不走金币
            return;
        }else {
            if(timeOut&&readMore){
                //是红包新闻
                if("1".equals(isRed)){
                        getReward2("1");
                        //金币哗啦哗啦的声音
                      //  playSound(R.raw.mm);
                    SoundUtils.playSoundByMedia(R.raw.mm);
                }else {
                    //金币新闻[大于25就结束]

                    if(NewsFragment.CurrentGetCoinNumber == 25){
                        NewsFragment.CurrentGetCoinNumber++;
                    }
                    if( NewsFragment.CurrentGetCoinNumber <= 25){

                        getReward2("0");
                        //金币哗啦哗啦的声音
                       // playSound(R.raw.mm);
                        SoundUtils.playSoundByMedia(R.raw.mm);
                        //动画
                        CoinAnimator(1,iv_getReward);
                        try {
                            Thread.sleep(2000);
                            CoinAnimator(2,iv_getReward);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }

            }
        }


    }

    /**
     * 适合播放声音短，文件小
     * 可以同时播放多种音频
     * 消耗资源较小
     */
    public static void playSound(int rawId) {
      //  Toast.makeText(MyApplication.getApplication(), "适合播放声音短，文件小", Toast.LENGTH_SHORT).show();
        Log.i("***()()()[][][]", "适合播放声音短，文件小");
        SoundPool soundPool;
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频的数量
            builder.setMaxStreams(1);
            //AudioAttributes是一个封装音频各种属性的类
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        } else {
            //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
            soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        }
        //第一个参数Context,第二个参数资源Id，第三个参数优先级
        soundPool.load(MyApplication.getApplication(), rawId, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(1, 1, 1, 0, 0, 1);
            }
        });
        //第一个参数id，即传入池中的顺序，第二个和第三个参数为左右声道，第四个参数为优先级，第五个是否循环播放，0不循环，-1循环
        //最后一个参数播放比率，范围0.5到2，通常为1表示正常播放
        //soundPool.play(1, 1, 1, 0, 0, 1);
        //回收Pool中的资源
        //soundPool.release();

    }


    /***
     * 符合规则，获取阅读奖励
     */
    private void getReward2(String ifreadPackage) {
        String userid = (String) SPUtils.get(NewsDetailsActivity.this,"user_id","");
        if(TextUtils.isEmpty(userid)){
            return;
        }
        ApiNews.GetRewardAfterReadNews(ApiConstant.GET_REWARD_AFTER_READ_NEWS, userid, newsId,ifreadPackage, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                Log.i("8900",s);
                try {
                    JSONObject jsonObject =new JSONObject(s);
                    String code=jsonObject.getString("code");
                    if("0000".equals(code)){
                        //更新阅读奖励数
                        Log.i("8900","广播发送成功");
                        sendBroadcast(new Intent("getRewardOk"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("yuuuuuuu",e.getMessage());
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

    //*************************************************
    //分享到微信
    private void shareFriends() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = urlStr;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "每日速报";
        msg.description = contentStr;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(this, bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        msg.setThumbImage(thumbBmp);
        bmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        // req.scene = sendtype==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        mWxApi.sendReq(req);
    }
    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void shareFriendCircle() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = urlStr;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = contentStr;
        msg.description =contentStr;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(this, bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        msg.setThumbImage(thumbBmp);
        bmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        // req.scene = sendtype==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        mWxApi.sendReq(req);
    }

    //分享到微博
    private void shareWeiBo() {
        initLog();
        //startActivity(new Intent(getActivity(), WBAuthActivity.class));
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();
        // sendMessage(true, true);
        shareWebPage();
    }
    private void shareWebPage() {
      /*  WebpageObject mediaObj =new WebpageObject();
        //创建文本消息对象
        TextObject textObject =new TextObject();
        textObject.text= "你分享内容的描述"+"分享网页的话加上网络地址";

        textObject.title= "哈哈哈哈哈哈";

        //创建图片消息对象，如果只分享文字和网页就不用加图片

        WeiboMultiMessage message =new WeiboMultiMessage();

        ImageObject imageObject =new ImageObject();

        // 设置 Bitmap 类型的图片到视频对象里        设置缩略图。 注意：最终压缩过的缩略图大小 不得超过 32kb。

        Bitmap bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.test);

        imageObject.setImageObject(bitmap);

        message.textObject= textObject;

        message.imageObject= imageObject;

        message.mediaObject= mediaObj;*/


        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title =  "每日速报";
        mediaObject.description = contentStr;
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);


        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(this, bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(thumbBmp);
        mediaObject.actionUrl = urlStr;
        mediaObject.defaultText = contentStr;
        WeiboMultiMessage message = new WeiboMultiMessage();
        message.mediaObject = mediaObject;
        shareHandler.shareMessage(message, false);
    }
    private void initLog() {
        WBAgent.setAppKey(ApiConstant.APP_KEY_WEIBO);
        WBAgent.setChannel("weibo"); //这个是统计这个app 是从哪一个平台down下来的  百度手机助手
        WBAgent.openActivityDurationTrack(false);
        try {
            //设置发送时间间隔 需大于90s小于8小时
            WBAgent.setUploadInterval(91000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();

    }
}
