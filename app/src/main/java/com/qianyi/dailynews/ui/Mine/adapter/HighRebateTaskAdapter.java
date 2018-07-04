package com.qianyi.dailynews.ui.Mine.adapter;

import android.content.Context;
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
public class HighRebateTaskAdapter extends BaseAdapter {
    private Context context;
    private List<FanLiInfo> infoList;
    public HighRebateTaskAdapter(Context context, List<FanLiInfo> infoList) {
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
        FanLiInfo info =infoList.get(i);
        ViewHolder viewHolder=null;
        if (view==null){
            view=LayoutInflater.from(context).inflate(R.layout.item_high_rebate_task,null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        Glide.with(context).load(info.getLogo()).into(viewHolder.mine_head);
        viewHolder.tv_title.setText(info.getTitle());
        viewHolder.tv_time.setText("结束时间:"+info.getEndTime());
        String status = info.getStatus();
        if (status.equals("1")){
            viewHolder.tv_state.setText("待上传");
        }else if(status.equals("2")){
            viewHolder.tv_state.setText("待审批");
        }else if(status.equals("3")){
            viewHolder.tv_state.setText("审批通过");
        }else if(status.equals("4")){
            viewHolder.tv_state.setText("审批不通过");
        }else if(status.equals("5")){
            viewHolder.tv_state.setText("取消");
        }else if(status.equals("6")){
            viewHolder.tv_state.setText("过期");
        }
        return view;
    }
    static class ViewHolder{
        @BindView(R.id.mine_head) public RoundedImageView mine_head;
        @BindView(R.id.tv_title) public TextView tv_title;
        @BindView(R.id.tv_time) public TextView tv_time;
        @BindView(R.id.tv_state) public TextView tv_state;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
