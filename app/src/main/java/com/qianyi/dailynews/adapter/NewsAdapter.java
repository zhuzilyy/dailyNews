package com.qianyi.dailynews.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianyi.dailynews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/5.
 */

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<testNewsEntity> newsEntities =new ArrayList<>();
    public NewsAdapter(Context context) {
        for (int i = 1; i <=60 ; i++) {
            if(i%6==1){
                testNewsEntity entity = new NewsAdapter.testNewsEntity("news","0");
                newsEntities.add(entity);
            }else if(i%6==2){
                testNewsEntity entity = new NewsAdapter.testNewsEntity("ad","0");
                newsEntities.add(entity);
            }else if(i%6==3){
                testNewsEntity entity = new NewsAdapter.testNewsEntity("news","1");
                newsEntities.add(entity);
            }else if(i%6==4){
                testNewsEntity entity = new NewsAdapter.testNewsEntity("ad","1");
                newsEntities.add(entity);
            }else if(i%6==5){
                testNewsEntity entity = new NewsAdapter.testNewsEntity("news","2");
                newsEntities.add(entity);
            }else {
                testNewsEntity entity = new NewsAdapter.testNewsEntity("ad","2");
                newsEntities.add(entity);
            }

        }
        this.context = context;



    }

    @Override
    public int getCount() {
        return newsEntities.size();
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
        Log.i("jj","");
        switch(newsEntities.get(i).getType()){
            case "news":
                switch(newsEntities.get(i).getCategory()){
                    case "0":
                        //新闻的cell:01
                        view = LayoutInflater.from(context).inflate(R.layout.item_news_001,null);
                    break;
                    case "1":
                        //新闻的cell:02
                        view = LayoutInflater.from(context).inflate(R.layout.item_news_002,null);
                        break;
                    case "2":
                        //新闻的cell:03
                        view = LayoutInflater.from(context).inflate(R.layout.item_news_003,null);
                        break;
                    default:
                    break;
                }
            break;
            case "ad":
                switch(newsEntities.get(i).getCategory()){
                    case "0":
                        //广告的cell:01
                        view = LayoutInflater.from(context).inflate(R.layout.item_ad_001,null);
                    break;
                    case "1":
                        //广告的cell:02
                        view = LayoutInflater.from(context).inflate(R.layout.item_ad_002,null);
                        break;
                    case "2":
                        //广告的cell:03
                        view = LayoutInflater.from(context).inflate(R.layout.item_ad_003,null);
                        break;

                    default:
                    break;


                }
                break;

            default:
            break;


        }



        return view;
    }


    private class  testNewsEntity{
        public testNewsEntity(String type, String category) {
            this.type = type;
            this.category = category;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        private String type;
        private String category;
    }


}
