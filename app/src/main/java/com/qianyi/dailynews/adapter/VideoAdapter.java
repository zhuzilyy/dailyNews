package com.qianyi.dailynews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.fragment.bean.VideoInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/5.
 */

public class VideoAdapter extends BaseAdapter {
    private Context context;
    private List<VideoInfo> infoList;
    public VideoAdapter(Context context, List<VideoInfo> infoList) {
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
       ViewHolder viewHolder=null;
       if (view==null){
           view=LayoutInflater.from(context).inflate(R.layout.item_video,null);
           viewHolder=new ViewHolder(view);
           view.setTag(viewHolder);
       }else{
           viewHolder= (ViewHolder) view.getTag();
       }
        VideoInfo videoInfo = infoList.get(i);
        viewHolder.tv_title.setText(videoInfo.getTitle());
        viewHolder.tv_duration.setText(videoInfo.getDurationMin());
        viewHolder.tv_browseCount.setText(videoInfo.getViewCount()+"次播放");
        Glide.with(context).load(videoInfo.getCoverUrl()).placeholder(R.mipmap.video_test).into(viewHolder.iv_background);
        return view;
    }
    static class ViewHolder{
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_duration)
        TextView tv_duration;
        @BindView(R.id.tv_browseCount)
        TextView tv_browseCount;
        @BindView(R.id.iv_background)
        ImageView iv_background;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
