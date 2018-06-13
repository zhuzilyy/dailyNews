package com.qianyi.dailynews.ui.Mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qianyi.dailynews.R;

/**
 * Created by Administrator on 2018/5/22.
 */

public class HighRebateAdapter extends BaseAdapter {
    private Context context;
    public HighRebateAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
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
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int itemType= getItemViewType(i);
        if("1".equals(itemType)){
            view=LayoutInflater.from(context).inflate(R.layout.item_register,null);
        }else {
           // if("2".equals(itemType))
            view=LayoutInflater.from(context).inflate(R.layout.item_money,null);
        }


        return view;
    }

    public class RegisterHolder{

    }
    public class MoneyHolder{

    }


}
