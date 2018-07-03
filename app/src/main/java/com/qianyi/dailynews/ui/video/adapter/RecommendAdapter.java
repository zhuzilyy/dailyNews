package com.qianyi.dailynews.ui.video.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.fragment.bean.VideoDetailInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/5/8.
 */

public class RecommendAdapter extends BaseAdapter {
    private Context context;
    private List<VideoDetailInfo> infoList;
    public RecommendAdapter(Context context, List<VideoDetailInfo> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=LayoutInflater.from(context).inflate(R.layout.item_recommend,null);
        TextView tv_title=view.findViewById(R.id.tv_title);
        TextView tv_viewCount=view.findViewById(R.id.tv_viewCount);
        ImageView iv_videoCover=view.findViewById(R.id.iv_videoCover);
        VideoDetailInfo videoDetailInfo = infoList.get(i);
        tv_title.setText(videoDetailInfo.getTitle());
        tv_viewCount.setText(videoDetailInfo.getViewCount()+"次播放");
        Glide.with(context).load(videoDetailInfo.getCoverUrl()).placeholder(R.mipmap.video_holder).into(iv_videoCover);
        return view;
    }
}
