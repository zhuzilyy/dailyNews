package com.qianyi.dailynews.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.MainActivity;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.application.MyApplication;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.ui.account.activity.RegisterActivity;
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
    public static String Gold="";
    private MyReceiver myReceiver;
    public static int CurrentGetCoinNumber=0;


    //注册送好礼=========================
    @BindView(R.id.re_newPeople)
    public RelativeLayout re_newPeople;
    @BindView(R.id.iv_newPeople_del)
    public ImageView iv_newPeople_del;
    @BindView(R.id.iv_newPeople_details)
    public ImageView iv_newPeople_details;


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
        myReceiver= new MyReceiver();
        IntentFilter filter01=new IntentFilter("getRewardOk");
        IntentFilter filter02=new IntentFilter("loginOk");
        IntentFilter filter03=new IntentFilter("com.action.quit");

        getActivity().registerReceiver(myReceiver,filter01);
        getActivity().registerReceiver(myReceiver,filter02);
        getActivity().registerReceiver(myReceiver,filter03);

        //获取红包奖励数
        getRedPackage();

        //--------显示是否显示新手状态----------
        String userid = (String) SPUtils.get(getActivity(),"user_id","");
        if(TextUtils.isEmpty(userid)){
            //没有登录就没有注册
            re_newPeople.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        CurrentNewsTitle = 0;
        fragments.clear();
        ApiNews.GetNewsTitles(ApiConstant.NEWS_TITLES, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                Log.i("ss",s);
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
    @OnClick({R.id.re_home_search,R.id.iv_newPeople_del,R.id.iv_newPeople_details,R.id.re_newPeople})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.re_home_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("tag","news");
                startActivity(intent);
                break;
            case R.id.iv_newPeople_del:
                re_newPeople.setVisibility(View.GONE);
                break;
            case R.id.iv_newPeople_details:
            case R.id.re_newPeople:
                //注册好礼
                showNewPeopleGift();
                re_newPeople.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /**
     *   //注册好礼
     */
    private void showNewPeopleGift() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.lay_new_register,null);
        View delete_v =contentView.findViewById(R.id.v_delete);
        TextView tv_register_details = contentView.findViewById(R.id.tv_register_details);
        Button btn_register_now = contentView.findViewById(R.id.btn_register_now);
        dialog.setContentView(contentView);

        delete_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_register_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "详情", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btn_register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                dialog.dismiss();
            }
        });

        dialog.show();

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
                Log.i("8900","getOk="+s);
              //  Toast.makeText(mActivity, "99999", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code=jsonObject.getString("code");
                    if("0000".equals(code)){
                       JSONObject jsonObject1= jsonObject.getJSONObject("data");
                       if(jsonObject1!=null){
                           CurretUsed= jsonObject1.getString("used");
                           TotleCount=jsonObject1.getString("cnt");
                           Gold=jsonObject1.getString("gold");
                           getActivity().runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   if(!TextUtils.isEmpty(CurretUsed)||!TextUtils.isEmpty(TotleCount)){
                                       ll_redNumber.setVisibility(View.VISIBLE);
                                       tv_redNumber.setText(CurretUsed+"/"+TotleCount);
                                       CurrentGetCoinNumber=Integer.parseInt(CurretUsed);

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

    public class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
          String action =  intent.getAction();
            if("getRewardOk".equals(action)){
                getRedPackage();
            }
            if("loginOk".equals(action)){
                //注册送好礼隐藏
                re_newPeople.setVisibility(View.GONE);
                ll_redNumber.setVisibility(View.VISIBLE);
                //获取当前金币新闻总数
                getRedPackage();

            }
            if("com.action.quit".equals(action)){
                //退出登录
                ll_redNumber.setVisibility(View.GONE);
            }

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myReceiver!=null){
            getActivity().unregisterReceiver(myReceiver);
        }
    }
}
