package com.qianyi.dailynews.ui.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qianyi.dailynews.R;

/**
 * Created by Administrator on 2018/5/31.
 */

public class NewsDetailsAdapter extends BaseAdapter {
    private Context mContext;

    public NewsDetailsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 5;
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
        convertView= LayoutInflater.from(mContext).inflate(R.layout.lay_news_details_recommed_item,null);
        return convertView;
    }
}
