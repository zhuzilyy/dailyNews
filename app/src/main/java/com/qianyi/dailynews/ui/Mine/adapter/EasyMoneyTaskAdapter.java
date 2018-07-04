package com.qianyi.dailynews.ui.Mine.adapter;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.ui.Mine.bean.FanLiInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/20.
 */
public class EasyMoneyTaskAdapter extends BaseAdapter {
    private Context context;
    private  List<FanLiInfo> infoList;
    public EasyMoneyTaskAdapter(Context context, List<FanLiInfo> infoList) {
        this.context = context;
        this.infoList= infoList;
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
        FanLiInfo info =infoList.get(i);
        ViewHolder viewHolder=null;
        if (view==null){
            view=LayoutInflater.from(context).inflate(R.layout.item_easy_money_task,null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        Glide.with(context).load(info.getLogo()).placeholder(R.mipmap.logo).into(viewHolder.mine_head);
        viewHolder.tv_title.setText(info.getTitle());
        viewHolder.tv_reader.setText("有效阅读人数"+ info.getCnt()+"人");
        viewHolder.tv_money.setText("已赚"+info.getRewards()+"金币");
        String status = info.getFlag();
        if("0".equals(status)){
            viewHolder.tv_state.setTextColor(Color.parseColor("#ff5645"));
            viewHolder.tv_state.setText("分享中");
        }else if("1".equals(status)){
            viewHolder.tv_state.setText("分享结束");
        }
        return view;
    }
    static class ViewHolder{
        @BindView(R.id.mine_head) public RoundedImageView mine_head;
        @BindView(R.id.tv_title) public TextView tv_title;
        @BindView(R.id.tv_reader) public TextView tv_reader;
        @BindView(R.id.tv_state) public TextView tv_state;
        @BindView(R.id.tv_money) public TextView tv_money;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
