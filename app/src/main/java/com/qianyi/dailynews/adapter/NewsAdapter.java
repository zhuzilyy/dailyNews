package com.qianyi.dailynews.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.ui.news.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/5/5.
 */

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<NewsBean> newsBeans;
    private int AD_RIGHT_PIC = 0;
    private int AD_BIG_PIC = 1;
    private int AD_THREE_PIC = 2;
    private int NEWS_THREE_PIC = 3;
    private int NEWS_RIGHT_PIC = 4;
    private int NEWS_NOPIC = 5;

    public NewsAdapter(Context context, List<NewsBean> newsBeans) {
        this.context = context;
        this.newsBeans = newsBeans;
    }

    @Override
    public int getCount() {
        return newsBeans.size();
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
    public int getItemViewType(int position) {
        NewsBean newsBean = newsBeans.get(position);
        String newtype = newsBean.getNewsType();
        if (!TextUtils.isEmpty(newtype)) {
            switch (newtype) {
                case "3":
                    return NEWS_THREE_PIC;
                case "1":
                    return NEWS_RIGHT_PIC;
                case "0":
                    return NEWS_NOPIC;
            }
        } else {
            String adtype = newsBean.getAdType();
            if (!TextUtils.isEmpty(adtype)) {
                switch (adtype) {
                    case "1":
                        int number = new Random().nextInt(10) + 1;
                        if(number>5){
                            return AD_BIG_PIC;
                        }else {
                            return AD_RIGHT_PIC;
                        }
                    case "3":
                        return AD_THREE_PIC;
                }
            }

        }
        return 100;
    }

    @Override
    public int getViewTypeCount() {
        return 6;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        NewsBean item = newsBeans.get(i);
       int itemType= getItemViewType(i);
       if(AD_BIG_PIC == itemType){
           //广告大图
           AdCell001Holder cell001Holder = new AdCell001Holder();
           view = LayoutInflater.from(context).inflate(R.layout.item_ad_001, null);
           cell001Holder.ad001_content  = view.findViewById(R.id.ad_001_content);
           cell001Holder.ad001_bigpic = view.findViewById(R.id.ad_001_bigpic);
           view.setTag(cell001Holder);

       }else if(AD_RIGHT_PIC == itemType){
           //广告右图的
           AdCell002Holder cell002Holder = new AdCell002Holder();
           view = LayoutInflater.from(context).inflate(R.layout.item_ad_002, null);
           cell002Holder.ad002_content  = view.findViewById(R.id.ad_002_content);
           cell002Holder.ad002_bigpic = view.findViewById(R.id.ad_002_pic);
           view.setTag(cell002Holder);

       }else if(AD_THREE_PIC == itemType){

           AdCell003Holder cell003Holder = new AdCell003Holder();
           view = LayoutInflater.from(context).inflate(R.layout.item_ad_003, null);
           cell003Holder.ad_003_content  = view.findViewById(R.id.ad_003_content);
           cell003Holder.ad_003_pic001 = view.findViewById(R.id.ad_003_pic001);
           cell003Holder.ad_003_pic002 = view.findViewById(R.id.ad_003_pic002);
           cell003Holder.ad_003_pic003 = view.findViewById(R.id.ad_003_pic003);
           view.setTag(cell003Holder);
       }else if(NEWS_THREE_PIC == itemType){
           //新闻三张图
           NewsCell003Holder Holder03 =new NewsCell003Holder();
           view = LayoutInflater.from(context).inflate(R.layout.item_news_001, null);
           Holder03.news003_content = view.findViewById(R.id.news003_content);
           Holder03.news003_award = view.findViewById(R.id.news003_award);
           Holder03.news003_publishman = view.findViewById(R.id.news003_publishman);
           Holder03.news003_posttime = view.findViewById(R.id.news003_posttime);
           Holder03.news003_read = view.findViewById(R.id.news003_read);

           Holder03.news003_pic01 = view.findViewById(R.id.news003_pic01);
           Holder03.news003_pic02 = view.findViewById(R.id.news003_pic02);
           Holder03.news003_pic03 = view.findViewById(R.id.news003_pic03);
           view.setTag(Holder03);

       }else if(NEWS_RIGHT_PIC == itemType){
            //新闻右边有图
           NewsCell002Holder Holder02 =new NewsCell002Holder();
           view = LayoutInflater.from(context).inflate(R.layout.item_news_002, null);
           Holder02.news002_content = view.findViewById(R.id.news002_content);
           Holder02.news002_award = view.findViewById(R.id.news002_award);
           Holder02.news002_publishman = view.findViewById(R.id.news002_publishman);
           Holder02.news002_posttime = view.findViewById(R.id.news002_posttime);
           Holder02.news002_read = view.findViewById(R.id.news002_read);

           Holder02.news002_pic = view.findViewById(R.id.news002_pic);
           view.setTag(Holder02);
       }else if(NEWS_NOPIC == itemType){
            //新闻没有图
           NewsCell001Holder Holder01 =new NewsCell001Holder();
           view = LayoutInflater.from(context).inflate(R.layout.item_news_001, null);
           Holder01.news001_content = view.findViewById(R.id.news001_content);
           Holder01.news001_award = view.findViewById(R.id.news001_award);
           Holder01.news001_publishman = view.findViewById(R.id.news001_publishman);
           Holder01.news001_posttime = view.findViewById(R.id.news001_posttime);
           Holder01.news001_read = view.findViewById(R.id.news001_read);

           view.setTag(Holder01);
       }

        //------赋值------------------------------------
       if(NEWS_THREE_PIC == itemType){
           //新闻3张图
           NewsCell003Holder  Holder03 = (NewsCell003Holder) view.getTag();
           Holder03.news003_content.setText(item.getTitle());
           Holder03.news003_publishman.setText(item.getPosterScreenName());
           Holder03.news003_posttime.setText(item.getPublishDate());
           Holder03.news003_read.setText(item.getReadNum());
           Glide.with(context).load(item.getImgsUrl().get(0)).into(Holder03.news003_pic01);
           Glide.with(context).load(item.getImgsUrl().get(1)).into(Holder03.news003_pic02);
           Glide.with(context).load(item.getImgsUrl().get(2)).into(Holder03.news003_pic03);
       }
       if(NEWS_RIGHT_PIC == itemType){
           //新闻右边图
           NewsCell002Holder  Holder02 = (NewsCell002Holder) view.getTag();
           Holder02.news002_content.setText(item.getTitle());
           Holder02.news002_publishman.setText(item.getPosterScreenName());
           Holder02.news002_posttime.setText(item.getPublishDate());
           Holder02.news002_read.setText(item.getReadNum());
           Glide.with(context).load(item.getImgsUrl().get(0)).into(Holder02.news002_pic);
       }
        if(NEWS_NOPIC == itemType){
            //新闻没有图的
            NewsCell001Holder  Holder01 = (NewsCell001Holder) view.getTag();
            Holder01.news001_content.setText(item.getTitle());
            Holder01.news001_publishman.setText(item.getPosterScreenName());
            Holder01.news001_posttime.setText(item.getPublishDate());
            Holder01.news001_read.setText(item.getReadNum());
        }
        if(AD_BIG_PIC == itemType){
            AdCell001Holder  cell001Holder = (AdCell001Holder) view.getTag();
            cell001Holder.ad001_content.setText(item.getTitle());
            Glide.with(context).load(item.getImgs().get(0)).into(cell001Holder.ad001_bigpic);
        }
        if(AD_RIGHT_PIC == itemType){
            AdCell002Holder  cell002Holder = (AdCell002Holder) view.getTag();
            cell002Holder.ad002_content.setText(item.getTitle());
            Glide.with(context).load(item.getImgs().get(0)).into(cell002Holder.ad002_bigpic);
        }
        if(AD_THREE_PIC == itemType){
            AdCell003Holder  cell003Holder = (AdCell003Holder) view.getTag();
            cell003Holder.ad_003_content.setText(item.getTitle());
            Glide.with(context).load(item.getImgs().get(0)).into(cell003Holder.ad_003_pic001);
            Glide.with(context).load(item.getImgs().get(1)).into(cell003Holder.ad_003_pic002);
            Glide.with(context).load(item.getImgs().get(2)).into(cell003Holder.ad_003_pic003);
        }

        return view;
    }

    //新闻holder无图的
    public class NewsCell001Holder {
        public TextView news001_content;
        public TextView news001_award;
        public TextView news001_publishman;
        public TextView news001_posttime;
        public TextView news001_read;

    }

    //新闻holder一张图的
    public class NewsCell002Holder {
        public TextView news002_content;
        public TextView news002_award;
        public TextView news002_publishman;
        public TextView news002_posttime;
        public TextView news002_read;
        public ImageView news002_pic;

    }

    //新闻holder3张图的
    public class NewsCell003Holder {
        public TextView news003_content;
        public TextView news003_award;
        public TextView news003_publishman;
        public TextView news003_posttime;
        public TextView news003_read;
        public ImageView news003_pic01;
        public ImageView news003_pic02;
        public ImageView news003_pic03;

    }
    //广告一张大图的
    public class AdCell001Holder{
        public TextView ad001_content;
        public ImageView ad001_bigpic;
    }
    //广告一张右图的
    public class AdCell002Holder{
        public TextView ad002_content;
        public ImageView ad002_bigpic;
    }
    //广告三张图的
    public class AdCell003Holder{
        public TextView ad_003_content;
        public ImageView ad_003_pic001;
        public ImageView ad_003_pic002;
        public ImageView ad_003_pic003;
    }



}
