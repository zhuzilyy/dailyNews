package com.qianyi.dailynews.ui.Mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/4/4.
 */

public class EaseMoneyAdapter extends BaseAdapter {
    private Context context;

    public EaseMoneyAdapter(Context context) {

        this.context = context;



    }

    @Override
    public int getCount() {
        return 15;
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

        view=LayoutInflater.from(context).inflate(R.layout.lay_easymoney_item,null);


        return view;
    }


}
