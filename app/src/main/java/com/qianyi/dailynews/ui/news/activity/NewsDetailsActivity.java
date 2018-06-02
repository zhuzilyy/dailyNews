package com.qianyi.dailynews.ui.news.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.ui.account.entity.LoginBean;
import com.qianyi.dailynews.ui.news.adapter.HotCommentAdapterNews;
import com.qianyi.dailynews.ui.news.adapter.NewsDetailsAdapter;
import com.qianyi.dailynews.ui.news.bean.CommentBean;
import com.qianyi.dailynews.ui.news.views.ListViewForScorollView;
import com.qianyi.dailynews.ui.news.views.MySingListView;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.Utils;
import com.qianyi.dailynews.utils.WebviewUtil;

import java.util.List;

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
    @BindView(R.id.sc) public ScrollView sc;


    private NewsDetailsAdapter adapter;
    private HotCommentAdapterNews commentAdapterNews;

    //***********************************
    private String titleStr;
    private String urlStr;
    private WebSettings webSettings;
    private String newsId="";
    private int page=1;
    private int pageSize=10;
    private int pageLevel2=1;//二级评论的也是
    private int pageSizeLevel2=10; //二级评论的页面大小
    @Override
    protected void initViews() {
        newsId=getIntent().getStringExtra("id");
        urlStr=getIntent().getStringExtra("url");
        if(!TextUtils.isEmpty(urlStr)){
            webSettings=news_webview.getSettings();
            WebviewUtil.setWebview(news_webview, webSettings);
            news_webview.loadUrl(urlStr);

            WebviewUtil.setWebview(bottom_web,webSettings);
            bottom_web.loadUrl(urlStr);
        }


        adapter=new NewsDetailsAdapter(NewsDetailsActivity.this);
        lv.setAdapter(adapter);



    }

    @Override
    protected void initData() {
        // 加载webview
        loadingWebview(news_webview);
        loadingWebview(bottom_web);
        //获取热门评论
        getCommend();
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
                Log.i("SS",e.getMessage());
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
           View footer= LayoutInflater.from(NewsDetailsActivity.this).inflate(R.layout.lay_news_comm_footer,null);
           lv_comment.addFooterView(footer);
           footer.findViewById(R.id.footer_more).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   //Toast.makeText(NewsDetailsActivity.this, "加载更多", Toast.LENGTH_SHORT).show();
                   Intent intent=new Intent(NewsDetailsActivity.this,MoreCommActivity.class);
                   intent.putExtra("newsID",newsId);
                   startActivity(intent);

               }
           });
       }


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
    @OnClick({R.id.iv_back,R.id.tv_news_more})
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_back:
                finish();
            break;
            case R.id.tv_news_more:
                re_newsmore.setVisibility(View.GONE);
                top_web_re.setVisibility(View.GONE);
                bottom_web.setVisibility(View.VISIBLE);
                //让ScrollVeiw 回到顶部
               // sc.scrollTo(0,0);
                sc.fullScroll(View.FOCUS_UP);
               // sc.smoothScrollTo(0, 0);

                break;

            default:
            break;


        }
    }
}
