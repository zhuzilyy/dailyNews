package com.qianyi.dailynews.ui.video;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiVideo;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.fragment.bean.VideoDetailBean;
import com.qianyi.dailynews.fragment.bean.VideoDetailInfo;
import com.qianyi.dailynews.fragment.bean.VideoInfo;
import com.qianyi.dailynews.ui.WebviewActivity;
import com.qianyi.dailynews.ui.video.adapter.RecommendAdapter;
import com.qianyi.dailynews.ui.video.view.MyJCVideoPlayerStandard;
import com.qianyi.dailynews.ui.video.view.MyUserActionStandard;
import com.qianyi.dailynews.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/8.
 */

public class VideoPlayingActivity extends BaseActivity {
    @BindView(R.id.player)
    MyJCVideoPlayerStandard videoPlayerStandard;
    @BindView(R.id.lv_recommend)
    ListView lv_recommend;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_viewCount)
    TextView tv_viewCount;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.no_internet_rl)
    RelativeLayout no_internet_rl;
    @BindView(R.id.wv_webview)
    WebView wv_webview;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private RecommendAdapter recommendAdapter;
    private Intent intent;
    private String videoUrl,viewCount,title,videoId;
    private List<VideoDetailInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    private WebSettings settings;
    private String jsStr;
    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //setTranslucentStatus();
        }
        customLoadingDialog=new CustomLoadingDialog(this);
        intent=getIntent();
        if (intent!=null){
            videoUrl=intent.getStringExtra("videoUrl");
            viewCount=intent.getStringExtra("viewCount");
            title=intent.getStringExtra("title");
            videoId=intent.getStringExtra("videoId");
        }
        if (Utils.hasInternet()){
            lv_recommend.setVisibility(View.VISIBLE);
            no_data_rl.setVisibility(View.GONE);
            no_internet_rl.setVisibility(View.GONE);
        }else{
            lv_recommend.setVisibility(View.GONE);
            no_data_rl.setVisibility(View.GONE);
            no_internet_rl.setVisibility(View.VISIBLE);
        }
        tv_title.setText("视频详情");
    }
    //沉浸式管理
    private void setTranslucentStatus() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        final int status = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        params.flags |= status;
        window.setAttributes(params);
    }
    @Override
    protected void initData() {
        tv_desc.setText(title);
        tv_viewCount.setText(viewCount+"次播放");
        Glide.with(this).load(R.mipmap.video_holder).into(videoPlayerStandard.thumbImageView);
        initWebview();
        /*JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        videoPlayerStandard.setUp("blob:http://www.365yg.com/b3bac586-c961-4c6f-975b-4627bd0641c3"
                ,JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"");
        videoPlayerStandard.startVideo();*/
        getData();

    }

    private void initWebview() {
        settings=wv_webview.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setUseWideViewPort(true); // 关键点
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSupportZoom(true); // 支持缩放
        settings.setLoadWithOverviewMode(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        wv_webview.loadUrl(videoUrl);
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

        wv_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!TextUtils.isEmpty(jsStr)&&wv_webview!=null){
                    wv_webview.loadUrl("javascript:" + jsStr );
                }
            }
        });
    }

    //获取数据
    private void getData() {
        customLoadingDialog.show();
        ApiVideo.videoDetail(ApiConstant.VIDEO_DETAIL, "20", new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        VideoDetailBean videoDetailBean = gson.fromJson(s, VideoDetailBean.class);
                        infoList= videoDetailBean.getData().getVideos();
                        if (infoList!=null && infoList.size()>0){
                            lv_recommend.setVisibility(View.VISIBLE);
                            no_data_rl.setVisibility(View.GONE);
                            no_internet_rl.setVisibility(View.GONE);
                            recommendAdapter=new RecommendAdapter(VideoPlayingActivity.this,infoList);
                            lv_recommend.setAdapter(recommendAdapter);
                        }else{
                            lv_recommend.setVisibility(View.GONE);
                            no_data_rl.setVisibility(View.VISIBLE);
                            no_internet_rl.setVisibility(View.GONE);
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                lv_recommend.setVisibility(View.GONE);
                no_data_rl.setVisibility(View.GONE);
                no_internet_rl.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_video_playing);
    }

    @Override
    protected void initListener() {
        lv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VideoDetailInfo videoDetailInfo = infoList.get(i);
                List<String> videoUrls = videoDetailInfo.getVideoUrls();
                String url="";
                if (videoUrls!=null && videoUrls.size()!=0){
                     url = videoDetailInfo.getVideoUrls().get(0);
                }
                String viewCount = videoDetailInfo.getViewCount();
                String title = videoDetailInfo.getTitle();
                tv_desc.setText(title);
                tv_viewCount.setText(viewCount+"次播放");
                wv_webview.loadUrl(url);
              /*  Intent intent=new Intent(VideoPlayingActivity.this,VideoPlayingActivity.class);
                VideoDetailInfo videoDetailInfo = infoList.get(i);
                intent.putExtra("videoUrl",videoDetailInfo.getUrl());
                intent.putExtra("viewCount",videoDetailInfo.getViewCount());
                intent.putExtra("title",videoDetailInfo.getTitle());
                startActivity(intent);
                finish();*/
            }
        });
    }
    @Override
    protected void setStatusBarColor() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        try {
            if (wv_webview != null) {
                wv_webview.onPause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @OnClick({R.id.iv_back,R.id.reload})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.reload:
                getData();
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (wv_webview != null) {
                wv_webview.onResume();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
