package com.qianyi.dailynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qianyi.dailynews.MainActivity;
import com.qianyi.dailynews.ui.account.activity.LoginActivity;
import com.qianyi.dailynews.ui.account.activity.WelcomeActiity;
import com.qianyi.dailynews.utils.SPUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/5/9.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<ImageView> imageViews;
    private Context context;
    public MyPagerAdapter(Context context,List<ImageView> imageViews) {
        this.context=context;
        this.imageViews = imageViews;
    }
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(imageViews.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }
    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        //将xml布局转换为view对象
        container.addView(imageViews.get(position));
        imageViews.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((WelcomeActiity)context).finish();
            }
        });

        return imageViews.get(position);
    }
}
