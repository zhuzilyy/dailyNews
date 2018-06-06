package com.qianyi.dailynews.ui.video;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiVideo;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.fragment.bean.VideoDetailBean;
import com.qianyi.dailynews.fragment.bean.VideoDetailInfo;
import com.qianyi.dailynews.fragment.bean.VideoInfo;
import com.qianyi.dailynews.ui.video.adapter.RecommendAdapter;
import com.qianyi.dailynews.ui.video.view.MyJCVideoPlayerStandard;
import com.qianyi.dailynews.ui.video.view.MyUserActionStandard;
import com.qianyi.dailynews.utils.Utils;

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
    private RecommendAdapter recommendAdapter;
    private Intent intent;
    private String videoUrl,viewCount,title;
    private List<VideoDetailInfo> infoList;
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
        if (Utils.hasInternet()){
            lv_recommend.setVisibility(View.VISIBLE);
            no_data_rl.setVisibility(View.GONE);
            no_internet_rl.setVisibility(View.GONE);
        }else{
            lv_recommend.setVisibility(View.GONE);
            no_data_rl.setVisibility(View.GONE);
            no_internet_rl.setVisibility(View.VISIBLE);
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
        getData();
    }
    //获取数据
    private void getData() {
        ApiVideo.videoDetail(ApiConstant.VIDEO_DETAIL, "3", new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
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
                Intent intent=new Intent(VideoPlayingActivity.this,VideoPlayingActivity.class);
                VideoDetailInfo videoDetailInfo = infoList.get(i);
                intent.putExtra("videoUrl",videoDetailInfo.getVideoUrls().get(0));
                intent.putExtra("viewCount",videoDetailInfo.getViewCount());
                intent.putExtra("title",videoDetailInfo.getTitle());
                startActivity(intent);
                finish();
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

}
