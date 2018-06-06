package com.qianyi.dailynews.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.application.MyApplication;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.ui.news.activity.SearchActivity;
import com.qianyi.dailynews.ui.news.adapter.FmPagerAdapter;
import com.qianyi.dailynews.ui.news.bean.NewsTitleBean;
import com.qianyi.dailynews.ui.news.fragment.PageFragment;
import com.qianyi.dailynews.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

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

    @BindView(R.id.tv_redNumber) public TextView tv_redNumber;
    @BindView(R.id.ll_redNumber) public LinearLayout ll_redNumber;

    private String CurretUsed;
    private String TotleCount;

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

        //获取红包奖励数
        getRedPackage();

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
                              MyApplication.newsTypeRes=newsTypeRes;
                              //赋值数据
                              viewPager.setOffscreenPageLimit(3);
                              for(int i=0;i<newsTypeRes.size();i++){
                                  tabLayout.addTab(tabLayout.newTab());
                                  fragments.add(new PageFragment());
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

    /***
     * 获取红包奖励数
     */
    private void getRedPackage() {
        String userid = (String) SPUtils.get(getActivity(), "user_id", "");
        if (TextUtils.isEmpty(userid)) {
            return;
        }

        ApiNews.GetNewsAward(ApiConstant.NEWS_REWARD, userid, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                Log.i("ss",s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code=jsonObject.getString("code");
                    if("0000".equals(code)){
                       JSONObject jsonObject1= jsonObject.getJSONObject("data");
                       if(jsonObject1!=null){
                           CurretUsed= jsonObject1.getString("used");
                           TotleCount=jsonObject1.getString("cnt");
                           getActivity().runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   if(!TextUtils.isEmpty(CurretUsed)||!TextUtils.isEmpty(TotleCount)){
                                       //
                                       ll_redNumber.setVisibility(View.VISIBLE);
                                       tv_redNumber.setText(CurretUsed+"/"+TotleCount);
                                   }
                               }
                           });

                       }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("ss",e.getMessage());
            }
        });


    }
}
