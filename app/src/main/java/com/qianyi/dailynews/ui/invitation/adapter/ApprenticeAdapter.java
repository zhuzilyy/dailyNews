package com.qianyi.dailynews.ui.invitation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.fragment.bean.TudiInfo;
import com.qianyi.dailynews.views.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/4/4.
 */

public class ApprenticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;
    private List<TudiInfo> infoList;
    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }
    private ApprenticeAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(ApprenticeAdapter.OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }
    public ApprenticeAdapter(Context mContext, List<TudiInfo> infoList) {
        this.mContext = mContext;
        this.infoList = infoList;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.lay_apprentice_item, parent,
                false);//这个布局就是一个imageview用来显示图片
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //将数据与item视图进行绑定，如果是MyViewHolder就加载网络图片，如果是MyViewHolder2就显示页数
        if (holder instanceof ApprenticeAdapter.MyViewHolder) {
            TudiInfo tudiInfo = infoList.get(position);
            String userName = tudiInfo.getUserName();
            String nickName = tudiInfo.getNickName();
            if (!TextUtils.isEmpty(userName)){
                ((MyViewHolder) holder).account.setText(userName);
                String firstName = userName.substring(0, 3);
                String lastName = userName.substring(7, userName.length());
                ((MyViewHolder) holder).nick.setText(firstName+"****"+lastName);
            }else if(!TextUtils.isEmpty(nickName)){
                ((MyViewHolder) holder).account.setText(nickName);
                ((MyViewHolder) holder).nick.setText(nickName);
            }else{
                ((MyViewHolder) holder).account.setText("每日速报");
                ((MyViewHolder) holder).nick.setText("每日速报");
            }
             ((MyViewHolder) holder).money.setText(infoList.get(position).getCash());
            Glide.with(mContext).load(infoList.get(position).getHeadPortrait()).placeholder(R.mipmap.logo).into(((MyViewHolder) holder).head);
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
        return infoList.size();//获取数据的个数
    }

    //自定义ViewHolder，用于加载图片
    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.apprentic_item_head)
        public CircleImageView head;
        @BindView(R.id.apprentic_item_account)
        public TextView account;
        @BindView(R.id.apprentic_item_nick)
        public TextView nick;
        @BindView(R.id.apprentic_item_money)
        public TextView money;
        @BindView(R.id.apprentic_item_ll)
        public LinearLayout apprentic_item_ll;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
//            head.setImageResource(R.mipmap.logo);
//            account.setText("14523698741");
//            nick.setText("by");
//            money.setText("1000");

        }
    }
}
