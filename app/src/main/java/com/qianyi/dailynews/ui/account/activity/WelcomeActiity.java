package com.qianyi.dailynews.ui.account.activity;

import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.adapter.MyPagerAdapter;
import com.qianyi.dailynews.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/9.
 */

public class WelcomeActiity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private MyPagerAdapter myPagerAdapter;
    private int[] res={R.mipmap.welcome1,R.mipmap.welcome2,R.mipmap.welcome3};
    private List<ImageView> imageViews;
    @Override
    protected void initViews() {
        imageViews=new ArrayList<>();
        for (int i = 0; i <res.length; i++) {
            ImageView imageView=new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(res[i]);
            imageViews.add(imageView);
        }
        myPagerAdapter=new MyPagerAdapter(this,imageViews);
        viewpager.setAdapter(myPagerAdapter);
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
