package com.qianyi.dailynews.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.ui.news.activity.SearchActivity;
import com.qianyi.dailynews.ui.news.adapter.FmPagerAdapter;
import com.qianyi.dailynews.ui.news.fragment.PageFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/30.
 */

public class NewsFragment extends BaseFragment implements View.OnClickListener {
    private View newsView;
    @BindView(R.id.tablayout)
    public TabLayout tabLayout;
    private String[] topics = new String[]{"推荐","热点","北京","视频","社会","图片","娱乐","娱乐","百元","电影","内涵段子","今日头条","中国新歌声"};
    @BindView(R.id.viewpager)
    public ViewPager viewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.re_home_search) public RelativeLayout re_home_search;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        newsView =  inflater.inflate(R.layout.fragment_news, null);
        return newsView;
    }

    @Override
    protected void initViews() {
        viewPager.setOffscreenPageLimit(3);
        for(int i=0;i<topics.length;i++){
            tabLayout.addTab(tabLayout.newTab());
            fragments.add(new PageFragment());
        }
        viewPager.setAdapter(new FmPagerAdapter(fragments,getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        for (int j = 0; j < topics.length; j++) {
            tabLayout.getTabAt(j).setText(topics[j]);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
    @OnClick({R.id.re_home_search})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.re_home_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
