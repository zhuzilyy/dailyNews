package com.qianyi.dailynews.ui;

import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.utils.WebviewUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by NEUNB on 2018/3/31.
 */

public class WebviewActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.wv_webview)
    WebView wv_webview;
    @BindView(R.id.pb_webview)
    ProgressBar pb_webview;
    private WebSettings webSettings;
    @Override
    protected void initViews() {
        BaseActivity.addActivity(this);
        Intent intent=getIntent();
        if (intent!=null){
            String title=intent.getStringExtra("title");
            String url=intent.getStringExtra("url");
            tv_title.setText(title);
            webSettings=wv_webview.getSettings();
            WebviewUtil.setWebview(wv_webview, webSettings);
            wv_webview.loadUrl(url);
        }


    }

    @Override
    protected void initData() {
        // 加载webview
        loadingWebview();
    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_webview);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    private void loadingWebview() {
        wv_webview.setWebChromeClient(new WebChromeClient() {
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
    @OnClick({R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
