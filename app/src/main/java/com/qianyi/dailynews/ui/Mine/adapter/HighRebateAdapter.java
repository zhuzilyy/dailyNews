package com.qianyi.dailynews.ui.Mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.ui.Mine.bean.FanLiInfo;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/22.
 */

public class HighRebateAdapter extends BaseAdapter {
    private Context context;
    private List<FanLiInfo> infoList;
    private int itemType=0;
    public HighRebateAdapter(Context context, List<FanLiInfo> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int i) {
        return infoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        String type = infoList.get(position).getType();
        if (type.equals("0")){
            itemType=0;
        }else{
            itemType=1;
        }
        return 0;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RegisterHolder registerHolder=null;
        MoneyHolder moneyHolder=null;
        if(0==itemType){
            if (registerHolder==null){
                view=LayoutInflater.from(context).inflate(R.layout.item_register,null);
                registerHolder=new RegisterHolder(view);
                view.setTag(registerHolder);
            }else{
                registerHolder= (RegisterHolder) view.getTag();
            }
            FanLiInfo fanLiInfo = infoList.get(i);
            registerHolder.tv_title.setText(fanLiInfo.getTitle());
            registerHolder.tv_coin.setText(fanLiInfo.getCash()+"元");
            registerHolder.tv_type.setText(fanLiInfo.getDescription());
            String status= fanLiInfo.getStatus();
            if("0".equals(status)){

            }else if("1".equals(status) || "4".equals(status)){
                registerHolder.tv_register.setText("任务中");
            }else {
                if("2".equals(status)){

                    registerHolder.tv_register.setText("待审批");
                    registerHolder.tv_register.setTextColor(Color.parseColor("#ffffff"));
                }else if("3".equals(status)){

                    registerHolder.tv_register.setText("审批通过");
                    registerHolder.tv_register.setTextColor(Color.parseColor("#ffffff"));
                }else if ("5".equals(status)){

                    registerHolder.tv_register.setText("取消");
                    registerHolder.tv_register.setTextColor(Color.parseColor("#ffffff"));
                }else if("6".equals(status)){

                    registerHolder.tv_register.setText("已过期");
                    registerHolder.tv_register.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        }else {
            if (moneyHolder==null){
                view=LayoutInflater.from(context).inflate(R.layout.item_money,null);
                moneyHolder=new MoneyHolder(view);
                view.setTag(moneyHolder);
            }else{
                moneyHolder= (MoneyHolder) view.getTag();
            }
            final FanLiInfo fanLiInfo = infoList.get(i);
            moneyHolder.tv_title.setText(fanLiInfo.getTitle());
            moneyHolder.tv_cash.setText(fanLiInfo.getCash()+"元");
            moneyHolder.tv_rate.setText(fanLiInfo.getDescription());
            moneyHolder.tv_time.setText("投资拿返利");
            String status= fanLiInfo.getStatus();
            if("0".equals(status)){

            }else if("1".equals(status) || "4".equals(status)){
                moneyHolder.tv_time.setText("任务中");
            }else {
                if("2".equals(status)){

                    moneyHolder.tv_time.setText("待审批");
                    moneyHolder.tv_time.setTextColor(Color.parseColor("#ffffff"));
                }else if("3".equals(status)){

                    moneyHolder.tv_time.setText("审批通过");
                    moneyHolder.tv_time.setTextColor(Color.parseColor("#ffffff"));
                }else if ("5".equals(status)){

                    moneyHolder.tv_time.setText("取消");
                    moneyHolder.tv_time.setTextColor(Color.parseColor("#ffffff"));
                }else if("6".equals(status)){

                    moneyHolder.tv_time.setText("已过期");
                    moneyHolder.tv_time.setTextColor(Color.parseColor("#ffffff"));
                }
            }

        }
        return view;
    }

    public class RegisterHolder{
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_type)
        TextView tv_type;
        @BindView(R.id.tv_coin)
        TextView tv_coin;
        @BindView(R.id.tv_register)
        TextView tv_register;
        public RegisterHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    public class MoneyHolder{
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_rate)
        TextView tv_rate;
        @BindView(R.id.tv_cash)
        TextView tv_cash;
        @BindView(R.id.tv_time)
        TextView tv_time;
        public MoneyHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    public static String cal(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
        return h + "时" + d + "分" + s + "秒";
    }


}
