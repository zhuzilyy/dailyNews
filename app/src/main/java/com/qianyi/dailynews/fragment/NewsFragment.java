package com.qianyi.dailynews.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.ui.news.activity.SearchActivity;
import com.qianyi.dailynews.ui.news.adapter.FmPagerAdapter;
import com.qianyi.dailynews.ui.news.bean.NewsTitleBean;
import com.qianyi.dailynews.ui.news.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/30.
 */

public class NewsFragment extends BaseFragment implements View.OnClickListener {
    private View newsView;
    @BindView(R.id.tablayout)
    public TabLayout tabLayout;
    @BindView(R.id.viewpager)
    public ViewPager viewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    public PageFragment pageFragment = new PageFragment();
    @BindView(R.id.re_home_search) public RelativeLayout re_home_search;
    public static int CurrentNewsTitle = 0;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        newsView =  inflater.inflate(R.layout.fragment_news, null);
        return newsView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

        ApiNews.GetNewsTitles(ApiConstant.NEWS_TITLES, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                Gson gson = new Gson();
                NewsTitleBean newsTitleBean = gson.fromJson(s,NewsTitleBean.class);
                if(newsTitleBean!=null){
                   String code = newsTitleBean.getCode();
                   if("0000".equals(code)){
                       NewsTitleBean.NewsTitleData newsTitleData = newsTitleBean.getData();
                       if(newsTitleData!=null){
                          List<NewsTitleBean.NewsTitleData.NewsTypeRes> newsTypeRes = newsTitleData.getNewsTypeRes();
                          if(newsTypeRes.size()>0){
                              //赋值数据
                              viewPager.setOffscreenPageLimit(3);
                              for(int i=0;i<newsTypeRes.size();i++){
                                  tabLayout.addTab(tabLayout.newTab());
                                  fragments.add(new PageFragment(newsTypeRes));
                              }
                              viewPager.setAdapter(new FmPagerAdapter(fragments,getActivity().getSupportFragmentManager()));
                              tabLayout.setupWithViewPager(viewPager);
                              tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

                              for (int j = 0; j < newsTypeRes.size(); j++) {
                                  tabLayout.getTabAt(j).setText(newsTypeRes.get(j).getCatValue());
                              }

                              //tab选择
                              tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                  @Override
                                  public void onTabSelected(TabLayout.Tab tab) {
                                      int position =tab.getPosition();
                                      CurrentNewsTitle = position;
                                  }

                                  @Override
                                  public void onTabUnselected(TabLayout.Tab tab) {

                                  }

                                  @Override
                                  public void onTabReselected(TabLayout.Tab tab) {

                                  }
                              });



                          }

                       }
                   }
                }

            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("sss",e.getMessage());
            }
        });

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
