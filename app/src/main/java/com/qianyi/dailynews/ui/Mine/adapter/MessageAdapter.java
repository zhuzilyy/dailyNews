package com.qianyi.dailynews.ui.Mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianyi.dailynews.BuildConfig;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.ui.Mine.bean.MessageInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/22.
 */

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<MessageInfo> infoList;
    public MessageAdapter(Context context, List<MessageInfo> infoList) {
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
            view=LayoutInflater.from(context).inflate(R.layout.item_message,null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        MessageInfo messageInfo = infoList.get(i);
        viewHolder.tv_title.setText(messageInfo.getTitle());
        viewHolder.tv_time.setText(messageInfo.getCreateTime());
        return view;
    }
    class ViewHolder{
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_time)
        TextView tv_time;
       public ViewHolder(View view){
           ButterKnife.bind(this,view);
       }
    }
}
