package com.qianyi.dailynews.ui.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.ui.news.bean.CommentBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/31.
 */

public class AllCommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<CommentBean.CommentData.NewsCommentRes> newsCommentRes;

    public AllCommentAdapter(Context mContext, List<CommentBean.CommentData.NewsCommentRes> newsCommentRes) {
        this.mContext = mContext;
        this.newsCommentRes = newsCommentRes;
    }

    @Override
    public int getCount() {
        return newsCommentRes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentBean.CommentData.NewsCommentRes commentRes=newsCommentRes.get(position);


        convertView= LayoutInflater.from(mContext).inflate(R.layout.lay_hotcomment_item,null);
        RoundedImageView head=convertView.findViewById(R.id.newsComm_head);
        TextView zan_tv=convertView.findViewById(R.id.newsComm_zan_tv);
        ImageView zan_iv=convertView.findViewById(R.id.newsComm_zan_iv);
        LinearLayout zan_ll=convertView.findViewById(R.id.newsComm_zan_ll);
        TextView name=convertView.findViewById(R.id.newsComm_name);
        TextView time=convertView.findViewById(R.id.newsComm_time);
        TextView content=convertView.findViewById(R.id.newsComm_content);


        Glide.with(mContext).load(commentRes.getHeadPortrait()).placeholder(R.mipmap.headportrait_icon).into(head);
        name.setText(commentRes.getName());
        content.setText(commentRes.getComment());
        time.setText(commentRes.getTime());



        return convertView;
    }



}
