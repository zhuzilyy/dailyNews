package com.qianyi.dailynews.ui.video;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

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
    private RecommendAdapter recommendAdapter;
    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
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
        Glide.with(this).load(R.mipmap.video_test).into(videoPlayerStandard.thumbImageView);
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        videoPlayerStandard.setUp("http://video.jiecao.fm/8/16/%E4%BF%AF%E5%8D%A7%E6%92%91.mp4"
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
