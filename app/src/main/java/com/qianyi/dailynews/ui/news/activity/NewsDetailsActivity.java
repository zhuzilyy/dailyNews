package com.qianyi.dailynews.ui.news.activity;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;


import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.utils.WebviewUtil;

import butterknife.BindView;
import butterknife.OnClick;

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

    //***********************************
    private String titleStr;
    private String urlStr;
    private WebSettings webSettings;
    @Override
    protected void initViews() {

        urlStr=getIntent().getStringExtra("url");
        if(!TextUtils.isEmpty(urlStr)){
            webSettings=news_webview.getSettings();
            WebviewUtil.setWebview(news_webview, webSettings);
            news_webview.loadUrl(urlStr);
        }


    }

    @Override
    protected void initData() {
        // 加载webview
        loadingWebview();
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
    private void loadingWebview() {
        news_webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (pb_webview!=null){
                    pb_webview.setProgress(newProgress);
                    if(newProgress==100){
                        pb_webview.setVisibility(View.GONE);
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
//                news_webview.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        Toast.makeText(NewsDetailsActivity.this, "56987", Toast.LENGTH_SHORT).show();
//                        return true;
//                    }
//                });
                break;

            default:
            break;


        }
    }
}
