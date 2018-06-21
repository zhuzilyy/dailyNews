package com.qianyi.dailynews.ui.Mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianyi.dailynews.R;

import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */

public class HotWordAdapter extends BaseAdapter {
    private List<String> infoList;
    private Context context;
    public HotWordAdapter(List<String> infoList, Context context) {
        this.infoList = infoList;
        this.context = context;
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
        view=LayoutInflater.from(context).inflate(R.layout.item_hot_word,null);
        TextView tv_hotWord=view.findViewById(R.id.tv_hotWord);
        tv_hotWord.setText(infoList.get(i));
        return view;
    }
}
