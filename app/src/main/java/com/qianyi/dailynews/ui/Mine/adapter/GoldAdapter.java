package com.qianyi.dailynews.ui.Mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.ui.Mine.bean.GoldCoinData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/22.
 */

public class GoldAdapter extends BaseAdapter {
    private Context context;
    private List<GoldCoinData>  infoList;
    public GoldAdapter(Context context, List<GoldCoinData> infoList) {
        this.context = context;
        this.infoList = infoList;
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
        ViewHolder viewHolder=null;
        if (view==null){
            view=LayoutInflater.from(context).inflate(R.layout.item_gold_coin,null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        GoldCoinData goldCoinInfo = infoList.get(i);
        viewHolder.tv_name.setText(goldCoinInfo.getName());
        viewHolder.tv_time.setText(goldCoinInfo.getTime());
        String type=goldCoinInfo.getType();
        if(type.equals("balance")){
            viewHolder.tv_coinCount.setText("+"+goldCoinInfo.getCnt()+"元");
        }else if(type.equals("gold")){
            viewHolder.tv_coinCount.setText("+"+goldCoinInfo.getCnt()+"金币");
        }
        return view;
    }
    static class ViewHolder{
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_coinCount)
        TextView tv_coinCount;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
