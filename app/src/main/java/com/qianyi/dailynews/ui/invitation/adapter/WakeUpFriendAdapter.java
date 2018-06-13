package com.qianyi.dailynews.ui.invitation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.fragment.bean.RecallInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/4/4.
 */

public class WakeUpFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;
    private List<RecallInfo> infoList;
    public WakeUpFriendAdapter(Context mContext, List<RecallInfo> infoList) {
        this.mContext = mContext;
        this.infoList = infoList;
    }
    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);


    }
    private WakeUpFriendAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(WakeUpFriendAdapter.OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }



    @Override
    public int getItemViewType(int position) {
        //判断item类别，是图还是显示页数（图片有URL）
        if (1==1) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据item类别加载不同ViewHolder
        if (viewType == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.lay_wakeup_item, parent,
                    false);//这个布局就是一个imageview用来显示图片
            WakeUpFriendAdapter.MyViewHolder holder = new WakeUpFriendAdapter.MyViewHolder(view);
            return holder;
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //将数据与item视图进行绑定，如果是MyViewHolder就加载网络图片，如果是MyViewHolder2就显示页数
        if (holder instanceof WakeUpFriendAdapter.MyViewHolder) {
            String phone=infoList.get(position).getPhone();
            String preThirdNum = phone.substring(0, 3);
            String lastFourthNum = phone.substring(phone.length()-4,phone.length());
            ((MyViewHolder) holder).tv_name.setText(preThirdNum+"****"+lastFourthNum);
            ((MyViewHolder) holder).tv_phone.setText(infoList.get(position).getPhone());
            // Picasso.with(mContext).load(datas.get(position).getUrl()).into(((MyViewHolder) holder).iv);//加载网络图片
            if(mOnItemClickListener!=null){
                ((WakeUpFriendAdapter.MyViewHolder) holder).pic_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //把条目的位置回调回去
                        mOnItemClickListener.onItemClick(position);
                    }
                });
            }
        }
    }
    @Override
    public int getItemCount() {
        return infoList.size();//获取数据的个数
    }
    //自定义ViewHolder，用于加载图片
    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_phone)
        TextView tv_phone;
        @BindView(R.id.tv_name)
        TextView tv_name;
        private TextView pic_item;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            pic_item = view.findViewById(R.id.tv_wakeup);
        }
    }
}
