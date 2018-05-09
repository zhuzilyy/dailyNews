package com.qianyi.dailynews.ui.invitation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.qianyi.dailynews.R;


/**
 * Created by Administrator on 2018/4/4.
 */

public class ApprenticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;


    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);


    }

    private ApprenticeAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(ApprenticeAdapter.OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    //适配器初始化
    public ApprenticeAdapter(Context context) {
        mContext = context;

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
            View view = LayoutInflater.from(mContext).inflate(R.layout.lay_apprentice_item, parent,
                    false);//这个布局就是一个imageview用来显示图片
            ApprenticeAdapter.MyViewHolder holder = new ApprenticeAdapter.MyViewHolder(view);



            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //将数据与item视图进行绑定，如果是MyViewHolder就加载网络图片，如果是MyViewHolder2就显示页数
        if (holder instanceof ApprenticeAdapter.MyViewHolder) {

            // Picasso.with(mContext).load(datas.get(position).getUrl()).into(((MyViewHolder) holder).iv);//加载网络图片
            if(mOnItemClickListener!=null){
                ((ApprenticeAdapter.MyViewHolder) holder).apprentic_item_ll.setOnClickListener(new View.OnClickListener() {
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
        return 15;//获取数据的个数
    }


    //自定义ViewHolder，用于加载图片
    class MyViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView head;
        private TextView account;
        private TextView nick;
        private TextView money;
        private LinearLayout apprentic_item_ll;



        public MyViewHolder(View view) {
            super(view);
//            head.setImageResource(R.mipmap.logo);
//            account.setText("14523698741");
//            nick.setText("by");
//            money.setText("1000");

        }
    }
}