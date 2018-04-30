package com.qianyi.dailynews.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.utils.loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/30.
 */

public class InvitationFragment extends BaseFragment {
    private View newsView;
    @BindView(R.id.tv_title)
    public TextView title;
    @BindView(R.id.iv_back)
    public ImageView back;
    @BindView(R.id.tv_right)
    public TextView rightTv;
    @BindView(R.id.Invitationbanner)
    public Banner banner;
    private List<String> images;

    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        newsView =  inflater.inflate(R.layout.fragment_invitation, null);
        return newsView;
    }

    @Override
    protected void initViews() {
        images= new ArrayList<>();
        images.add("http://pic3.zhimg.com/f665508fc07c122a7d79670600ca6c9e.jpg");
        images.add("http://pic3.zhimg.com//144edd4fa57e8b0b9c70bfea5c6b5dee.jpg");
        images.add("http://pic4.zhimg.com/ea2e46e40b74da68960775b1cbcfd3bb.jpg");
        images.add("http://pic1.zhimg.com/9213def521eb908e37c15016c9d0ed24.jpg");
        images.add("http://pic3.zhimg.com/32e1aaa65945ec773d3ffdf614c0b07e.jpg");


        title.setText("邀请");
        back.setVisibility(View.GONE);
        rightTv.setText("邀请规则");
        rightTv.setVisibility(View.VISIBLE);
        //设置banner
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.RIGHT);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);//设置图片源
        //banner.setBannerTitles(titles);//设置标题源
        banner.start();


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
