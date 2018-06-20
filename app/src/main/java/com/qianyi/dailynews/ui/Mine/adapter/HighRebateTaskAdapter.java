package com.qianyi.dailynews.ui.Mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qianyi.dailynews.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/20.
 */
public class HighRebateTaskAdapter extends BaseAdapter {
    private Context context;
    public HighRebateTaskAdapter(Context context) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            view=LayoutInflater.from(context).inflate(R.layout.item_high_rebate_task,null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        return view;
    }
    static class ViewHolder{
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
