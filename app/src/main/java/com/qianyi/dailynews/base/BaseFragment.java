package com.qianyi.dailynews.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by NEUNB on 2018/2/28.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    //视图
    public View view;
    Unbinder unbinder;
    /**
     * 如果你用了support 23的库，上面的方法会提示过时，有强迫症的小伙伴，可以用下面的方法代替
     * 解决 getActivity()空指针
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    /**
     * 创建fragment时第一次调用，只能调用一次。
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /**
     * 初始化fragment布局时调用
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mActivity=getActivity();
        //初始化布局
        view = getResLayout(inflater,container);
        //绑定framgent
        unbinder = ButterKnife.bind(this, view);
        //初始化控件
        initViews();
        //初始化监听事件
        initListener();
        return view;
    }

    /**
     * 当acitivity、fragment布局初始化完毕，调用此方法，初始化数据
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化数据
        initData();
    }
    @Override
    public void onDestroyView() {
        //解除绑定
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected abstract View getResLayout(LayoutInflater inflater, ViewGroup container);
    protected abstract void initViews();
    protected abstract void initData();
    protected abstract void initListener();

}
