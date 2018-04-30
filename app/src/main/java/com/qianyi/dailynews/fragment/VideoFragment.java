package com.qianyi.dailynews.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseFragment;

/**
 * Created by Administrator on 2018/4/30.
 */

public class VideoFragment extends BaseFragment {
    private View newsView;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        newsView =  inflater.inflate(R.layout.fragment_video, null);
        return newsView;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
