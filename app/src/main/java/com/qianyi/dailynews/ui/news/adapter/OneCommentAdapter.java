package com.qianyi.dailynews.ui.news.adapter;

import android.content.Context;
import android.text.TextUtils;
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
import com.qianyi.dailynews.ui.news.bean.OneNewsBean;
import com.qianyi.dailynews.views.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/6/3.
 */

public class OneCommentAdapter extends BaseAdapter {
    private List<OneNewsBean.OneNewsData.NewsCommentLevel2Array> bigList;
    private Context mContext;

    public OneCommentAdapter(List<OneNewsBean.OneNewsData.NewsCommentLevel2Array> bigList, Context mContext) {
        this.bigList = bigList;
        this.mContext = mContext;
    }



    @Override
    public int getCount() {
        return bigList.size();
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
        viewHolder holder = null;
        OneNewsBean.OneNewsData.NewsCommentLevel2Array item=bigList.get(position);
        if(convertView==null){
            holder=new viewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.lay_onecomment_details_item,null);

            holder.head=convertView.findViewById(R.id.newsComm_head);
            holder.tv_zan=convertView.findViewById(R.id.newsComm_zan_tv);
            holder.iv_zan=convertView.findViewById(R.id.newsComm_zan_iv);
            holder.ll_zan=convertView.findViewById(R.id.newsComm_zan_ll);
            holder.name=convertView.findViewById(R.id.newsComm_name);
            holder.time=convertView.findViewById(R.id.newsComm_time);
            holder.content=convertView.findViewById(R.id.newsComm_content);
            convertView.setTag(holder);

        }else {
            holder = (viewHolder) convertView.getTag();
        }


        if(!TextUtils.isEmpty(item.getHeadPortrait())){
            //一级评论
            Glide.with(mContext).load(item.getHeadPortrait()).into(holder.head);
        }

        holder.name.setText(item.getName()==null?item.getUserName():item.getName());
        holder.content.setText(item.getComment());
//        holder.time.setText(item.getTime());
//        holder.zan_tv.setText(item.getLike());


        return convertView;
    }

    class viewHolder{
        CircleImageView head;
        TextView name;
        TextView content;
        TextView time;
        TextView tv_zan;
        ImageView iv_zan;
        LinearLayout ll_zan;

    }


}
