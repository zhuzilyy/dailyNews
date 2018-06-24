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

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.adapter.NewsAdapter;
import com.qianyi.dailynews.ui.Mine.bean.MakeMoneyInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/4/4.
 */

public class EaseMoneyAdapter extends BaseAdapter {
    private Context context;
    private List<MakeMoneyInfo> infoList;
    public EaseMoneyAdapter(Context context, List<MakeMoneyInfo> infoList) {
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
            view=LayoutInflater.from(context).inflate(R.layout.lay_easymoney_item,null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.tv_title.setText(infoList.get(i).getTitle());
        viewHolder.tv_gold.setText("每个好友阅读 +"+infoList.get(i).getGold()+"金币");
        Glide.with(context).load(infoList.get(i).getImg()).placeholder(R.mipmap.logo).into(viewHolder.roundedImageView);
        return view;
    }
    static class ViewHolder{
        @BindView(R.id.mine_head)
        RoundedImageView roundedImageView;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_gold)
        TextView tv_gold;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
