package com.qianyi.dailynews.ui.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
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
import com.orhanobut.logger.Logger;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.ui.news.activity.NewsDetailsActivity;
import com.qianyi.dailynews.ui.news.activity.OneCommDetailsActivity;
import com.qianyi.dailynews.ui.news.bean.CommentBean;
import com.qianyi.dailynews.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/31.
 */

public class HotCommentAdapterNews extends BaseAdapter {
    private Context mContext;
    private List<CommentBean.CommentData.NewsCommentRes> newsCommentRes;
    public CommPublishListener listener;

    public HotCommentAdapterNews(Context mContext, List<CommentBean.CommentData.NewsCommentRes> newsCommentRes) {
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
        return newsCommentRes.size()>5?5:newsCommentRes.size();
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
        final TextView zan_tv=convertView.findViewById(R.id.newsComm_zan_tv);
        ImageView zan_iv=convertView.findViewById(R.id.newsComm_zan_iv);
        LinearLayout zan_ll=convertView.findViewById(R.id.newsComm_zan_ll);
        TextView name=convertView.findViewById(R.id.newsComm_name);
        TextView time=convertView.findViewById(R.id.newsComm_time);
        TextView content=convertView.findViewById(R.id.newsComm_content);
        LinearLayout ll_SecondComm=convertView.findViewById(R.id.ll_SecondComm);
        final ImageView newsComm_zan_iv = convertView.findViewById(R.id.newsComm_zan_iv);

        TextView comm_write_tv=convertView.findViewById(R.id.comm_write_tv);
        TextView comm_level2_title01=convertView.findViewById(R.id.comm_level2_title01);
        TextView comm_level2_content01=convertView.findViewById(R.id.comm_level2_content01);
        TextView comm_level2_title02=convertView.findViewById(R.id.comm_level2_title02);
        TextView comm_level2_content02=convertView.findViewById(R.id.comm_level2_content02);
        TextView comm_level2_more=convertView.findViewById(R.id.comm_level2_more);

        //一级评论
        Glide.with(mContext).load(commentRes.getHeadPortrait()).placeholder(R.mipmap.touxiang2).into(head);
        name.setText(commentRes.getName()==null?commentRes.getUserName():commentRes.getName());
        content.setText(commentRes.getComment());
        time.setText(commentRes.getTime());
        zan_tv.setText(commentRes.getLike());


        //点赞
        zan_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = (String) SPUtils.get(mContext,"user_id","");
                if(TextUtils.isEmpty(userid)){
                    return;
                }
                ApiNews.CommLike(ApiConstant.COMMENT_LIKE, commentRes.getId(), userid, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(Call call, Response response, String s) {
                        Logger.i(s);

                        try {
                            JSONObject jsonObject= new JSONObject(s);
                            String code=jsonObject.getString("code");
                            if("0000".equals(code)){
                                zan_tv.setText((Integer.parseInt(zan_tv.getText().toString().trim())+1)+"");
                                zan_tv.setTextColor(Color.parseColor("#ff0000"));
                                newsComm_zan_iv.setImageResource(R.mipmap.houshou);

                            }else if("0008".equals(code)){
                                Toast.makeText(mContext, "您已点过赞了", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onEror(Call call, int statusCode, Exception e) {
                        Log.i("ss",e.getMessage());
                    }
                });


            }
        });



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
                    Intent intent = new Intent(mContext, OneCommDetailsActivity.class);
                    intent.putExtra("commId",commentRes.getId());
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
