package com.qianyi.dailynews.fragment;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.adapter.VideoAdapter;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiVideo;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.fragment.bean.VideoBean;
import com.qianyi.dailynews.fragment.bean.VideoInfo;
import com.qianyi.dailynews.ui.video.VideoPlayingActivity;
import com.qianyi.dailynews.utils.Utils;
import com.qianyi.dailynews.views.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/30.
 */

public class VideoFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.no_internet_rl)
    RelativeLayout no_internet_rl;
    private int page=1;
    private View newsView;
    private VideoAdapter videoAdapter;
    private List<VideoInfo> infoList;
    private boolean isRefresh;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        newsView =  inflater.inflate(R.layout.fragment_video, null);
        return newsView;
    }
    @Override
    protected void initViews() {
        infoList=new ArrayList<>();
        iv_back.setVisibility(View.GONE);
        tv_title.setText("视频");
        customLoadingDialog=new CustomLoadingDialog(getActivity());
    }
    @Override
    protected void initData() {
        if (!Utils.hasInternet()){
            mPullToRefreshView.setVisibility(View.GONE);
            no_data_rl.setVisibility(View.GONE);
            no_internet_rl.setVisibility(View.VISIBLE);
        }else{
            customLoadingDialog.show();
            getData(page);
            videoAdapter =new VideoAdapter(getActivity(),infoList);
            listview.setAdapter(videoAdapter);
        }
    }
    //获取数据
    private void getData(int page) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        ApiVideo.getVideoList(ApiConstant.VIDEO_LIST, page, ApiConstant.PAGE_SIZE, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        VideoBean videoBean = gson.fromJson(s, VideoBean.class);
                        String code = videoBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS_CODE)){
                            mPullToRefreshView.onHeaderRefreshComplete();
                            List<VideoInfo> list = videoBean.getData().getVideos();
                            if (list!=null && list.size()>0){
                                //判断是不是刷新
                                if (isRefresh){
                                    infoList.clear();
                                    isRefresh=false;
                                }
                                mPullToRefreshView.setVisibility(View.VISIBLE);
                                no_data_rl.setVisibility(View.GONE);
                                no_internet_rl.setVisibility(View.GONE);
                                infoList.addAll(list);
                                videoAdapter.notifyDataSetChanged();
                                //判断是不是没有更多数据了
                                if (list.size() < Integer.parseInt(ApiConstant.PAGE_SIZE)) {
                                    mPullToRefreshView.onFooterRefreshComplete(true);
                                }else{
                                    mPullToRefreshView.onFooterRefreshComplete(false);
                                }
                            }else{
                                mPullToRefreshView.setVisibility(View.GONE);
                                no_data_rl.setVisibility(View.VISIBLE);
                                no_internet_rl.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                mPullToRefreshView.setVisibility(View.GONE);
                no_data_rl.setVisibility(View.GONE);
                no_internet_rl.setVisibility(View.VISIBLE);
            }
        });
    }
    private void getMoreData(int page) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        ApiVideo.getVideoList(ApiConstant.VIDEO_LIST, page, ApiConstant.PAGE_SIZE, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        VideoBean videoBean = gson.fromJson(s, VideoBean.class);
                        String code = videoBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS_CODE)){
                            mPullToRefreshView.onHeaderRefreshComplete();
                            List<VideoInfo> list = videoBean.getData().getVideos();
                            if (list!=null && list.size()>0){
                                infoList.addAll(list);
                                videoAdapter.notifyDataSetChanged();
                                //判断是不是没有更多数据了
                                if (list.size() < Integer.parseInt(ApiConstant.PAGE_SIZE)) {
                                    mPullToRefreshView.onFooterRefreshComplete(true);
                                }else{
                                    mPullToRefreshView.onFooterRefreshComplete(false);
                                }
                            }else{
                                //已经加载到最后一条
                                mPullToRefreshView.onFooterRefreshComplete(true);
                            }
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                mPullToRefreshView.setVisibility(View.GONE);
                no_data_rl.setVisibility(View.GONE);
                no_internet_rl.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    protected void initListener() {
        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),VideoPlayingActivity.class);
                VideoInfo videoInfo = infoList.get(i);
                intent.putExtra("videoUrl",videoInfo.getVideoUrls().get(0));
                intent.putExtra("viewCount",videoInfo.getViewCount());
                intent.putExtra("title",videoInfo.getTitle());
                startActivity(intent);
                addWatchNum(videoInfo.getId(),i);
            }
        });
    }
    private void addWatchNum(String videoId,final int position) {
        ApiVideo.addWatchNum(ApiConstant.ADD_WATCH_NUM, videoId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String return_code = jsonObject.getString("return_code");
                    if (return_code.equals("SUCCESS")){
                        String viewCount = infoList.get(position).getViewCount();
                        int intViewCount=Integer.parseInt(viewCount);
                        intViewCount++;
                        infoList.get(position).setViewCount(intViewCount+"");
                        videoAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }
    //刷新事件
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        isRefresh=true;
        page=1;
        getData(page);
    }
    //加载事件
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        page++;
        getMoreData(page);
    }
}
