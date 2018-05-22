package com.qianyi.dailynews.ui.video;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.ui.video.adapter.RecommendAdapter;
import com.qianyi.dailynews.ui.video.view.MyJCVideoPlayerStandard;
import com.qianyi.dailynews.ui.video.view.MyUserActionStandard;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

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
    private RecommendAdapter recommendAdapter;
    private Intent intent;
    private String videoUrl,viewCount,title;
    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
        }
        intent=getIntent();
        if (intent!=null){
            videoUrl=intent.getStringExtra("videoUrl");
            viewCount=intent.getStringExtra("viewCount");
            title=intent.getStringExtra("title");
        }
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
        Glide.with(this).load(R.mipmap.video_test).into(videoPlayerStandard.thumbImageView);
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        videoPlayerStandard.setUp(videoUrl
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"");
        videoPlayerStandard.startVideo();
        recommendAdapter=new RecommendAdapter(VideoPlayingActivity.this);
        lv_recommend.setAdapter(recommendAdapter);
    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_video_playing);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
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
