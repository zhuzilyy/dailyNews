package com.qianyi.dailynews.ui.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.ui.news.activity.OneCommDetailsActivity;
import com.qianyi.dailynews.ui.news.bean.CommentBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/31.
 */

public class AllCommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<CommentBean.CommentData.NewsCommentRes> newsCommentRes;
    public CommPublishListener listener;

    public AllCommentAdapter(Context mContext, List<CommentBean.CommentData.NewsCommentRes> newsCommentRes) {
        this.mContext = mContext;
        this.newsCommentRes = newsCommentRes;
    }
    //********************
    public interface CommPublishListener{
        void commPublish(String id);
    }

    public void setOnCommPublishListener(CommPublishListener l){
        this.listener=l;
    }


    //*******************

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
        final CommentBean.CommentData.NewsCommentRes commentRes=newsCommentRes.get(position);


        convertView= LayoutInflater.from(mContext).inflate(R.layout.lay_hotcomment_item,null);
        RoundedImageView head=convertView.findViewById(R.id.newsComm_head);
        TextView zan_tv=convertView.findViewById(R.id.newsComm_zan_tv);
        ImageView zan_iv=convertView.findViewById(R.id.newsComm_zan_iv);
        LinearLayout zan_ll=convertView.findViewById(R.id.newsComm_zan_ll);
        TextView name=convertView.findViewById(R.id.newsComm_name);
        TextView time=convertView.findViewById(R.id.newsComm_time);
        TextView content=convertView.findViewById(R.id.newsComm_content);
        LinearLayout ll_SecondComm=convertView.findViewById(R.id.ll_SecondComm);



        TextView comm_write_tv=convertView.findViewById(R.id.comm_write_tv);
        TextView comm_level2_title01=convertView.findViewById(R.id.comm_level2_title01);
        TextView comm_level2_content01=convertView.findViewById(R.id.comm_level2_content01);
        TextView comm_level2_title02=convertView.findViewById(R.id.comm_level2_title02);
        TextView comm_level2_content02=convertView.findViewById(R.id.comm_level2_content02);
        TextView comm_level2_more=convertView.findViewById(R.id.comm_level2_more);

        //一级评论
        Glide.with(mContext).load(commentRes.getHeadPortrait()).placeholder(R.mipmap.headportrait_icon).into(head);
        name.setText(commentRes.getName());
        content.setText(commentRes.getComment());
        time.setText(commentRes.getTime());
        //二级评论
        if(commentRes.getNewsCommentLevel2Array().size()>0){
            CommentBean.CommentData.NewsCommentRes.NewsCommentLevel2Array item01=commentRes.getNewsCommentLevel2Array().get(0);
            CommentBean.CommentData.NewsCommentRes.NewsCommentLevel2Array item02=null;
            if(commentRes.getNewsCommentLevel2Array().size()>1){
                item02=commentRes.getNewsCommentLevel2Array().get(1);
            }else if(commentRes.getNewsCommentLevel2Array().size()>2){
                comm_level2_more.setVisibility(View.VISIBLE);
            }

            if(item01!=null){
                comm_level2_title01.setText(item01.getUserName());
                comm_level2_content01.setText(item01.getComment());
            }else {
                comm_level2_title01.setVisibility(View.GONE);
                comm_level2_content01.setVisibility(View.GONE);
            }
            if(item02!=null){
                comm_level2_title02.setText(item02.getUserName());
                comm_level2_content02.setText(item02.getComment());
            }else {
                comm_level2_title02.setVisibility(View.GONE);
                comm_level2_content02.setVisibility(View.GONE);
            }
            //点击查看二级评论更多
            comm_level2_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,OneCommDetailsActivity .class);
                    mContext.startActivity(intent);
                }
            });

        }else {
            //没有二级评论
            ll_SecondComm.setVisibility(View.GONE);
        }
        if(commentRes.getNewsCommentLevel2Array().size()>2){
            comm_level2_more.setVisibility(View.VISIBLE);
        }

        comm_write_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.commPublish(commentRes.getId());
            }
        });




        return convertView;
    }



}
