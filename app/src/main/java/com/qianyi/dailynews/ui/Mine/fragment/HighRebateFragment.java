package com.qianyi.dailynews.ui.Mine.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.ui.Mine.activity.AccountDetailsActivity;
import com.qianyi.dailynews.ui.Mine.activity.HighRebateDetilsActivity;
import com.qianyi.dailynews.ui.Mine.activity.HightBackMoneyRewardWebViewActivity;
import com.qianyi.dailynews.ui.Mine.adapter.HighRebateAdapter;
import com.qianyi.dailynews.ui.Mine.bean.FanLiBean;
import com.qianyi.dailynews.ui.Mine.bean.FanLiInfo;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.Utils;
import com.qianyi.dailynews.views.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/1.
 */

public class HighRebateFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener  {
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;

    @BindView(R.id.no_internet_rl)
    RelativeLayout no_internet_rl;
    private int page=1;
    private HighRebateAdapter adapter;
    private List<FanLiInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    private  String userId;
    private MyReceiver myReceiver;

    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_high_rebate,null);
    }
    @Override
    protected void initViews() {
        customLoadingDialog=new CustomLoadingDialog(getActivity());
        infoList=new ArrayList<>();

        myReceiver= new MyReceiver();
        IntentFilter filter = new IntentFilter("myReceiver");
        getActivity().registerReceiver(myReceiver,filter);

        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);
        View headview = LayoutInflater.from(getActivity()).inflate(R.layout.hight_head,null);
        adapter=new HighRebateAdapter(getActivity(),infoList);
        listview.setAdapter(adapter);
        listview.addHeaderView(headview);
        userId = (String) SPUtils.get(getActivity(),"user_id","");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initData() {
        if (!Utils.hasInternet()){
            mPullToRefreshView.setVisibility(View.GONE);
            no_data_rl.setVisibility(View.GONE);
            no_internet_rl.setVisibility(View.VISIBLE);
        }else{
            customLoadingDialog.show();
            firstData(page);
        }
    }
    @Override
    protected void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(0==i){
                    //高额奖励小课堂
                    Intent intent = new Intent(getActivity(), HightBackMoneyRewardWebViewActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), HighRebateDetilsActivity.class);
                    intent.putExtra("id",infoList.get(i-1).getId());
                    intent.putExtra("type",infoList.get(i-1).getType());
                    intent.putExtra("time",infoList.get(i-1).getTime());
                    intent.putExtra("status",infoList.get(i-1).getStatus());
                    startActivity(intent);
                }

            }
        });
       // firstData();
    }
    private void firstData(int page) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        ApiMine.fanliList(ApiConstant.FANLI_LIST, userId,page,ApiConstant.PAGE_SIZE, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                Logger.json(s);
                Logger.i(s);

                infoList.clear();
                customLoadingDialog.dismiss();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        FanLiBean fanLiBean = gson.fromJson(s, FanLiBean.class);
                        String code = fanLiBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS_CODE)){
                            mPullToRefreshView.onHeaderRefreshComplete();
                            List<FanLiInfo> list = fanLiBean.getData();
                            if (list!=null && list.size()>0){
                                //判断是不是刷新
                                mPullToRefreshView.setVisibility(View.VISIBLE);
                                no_data_rl.setVisibility(View.GONE);
                                no_internet_rl.setVisibility(View.GONE);
                                infoList.addAll(list);
                                adapter.notifyDataSetChanged();
                                //判断是不是没有更多数据了
                                if (list.size() < Integer.parseInt(ApiConstant.PAGE_SIZE)) {
                                    mPullToRefreshView.onFooterRefreshComplete(true);
                                }else{
                                    mPullToRefreshView.onFooterRefreshComplete(false);
                                }
                            }else{
                                mPullToRefreshView.setVisibility(View.GONE);
                                no_data_rl.setVisibility(View.VISIBLE);
                                no_internet_rl.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                mPullToRefreshView.setVisibility(View.GONE);
                no_data_rl.setVisibility(View.GONE);
                no_internet_rl.setVisibility(View.VISIBLE);
            }
        });

    }
    //获取更多数据
    private void getMoreData(int page) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        ApiMine.fanliList(ApiConstant.FANLI_LIST, userId, page, ApiConstant.PAGE_SIZE, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("sss",s);
                        mPullToRefreshView.onHeaderRefreshComplete();
                        Gson gson = new Gson();
                        FanLiBean fanLiBean = gson.fromJson(s, FanLiBean.class);
                        String code = fanLiBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS_CODE)) {
                            mPullToRefreshView.onHeaderRefreshComplete();
                            List<FanLiInfo> list = fanLiBean.getData();
                            if (list != null && list.size() > 0) {
                                //判断是不是没有更多数据了
                                infoList.addAll(list);
                                adapter.notifyDataSetChanged();
                                if (list.size() < Integer.parseInt(ApiConstant.PAGE_SIZE))
                                    mPullToRefreshView.onFooterRefreshComplete(true);
                                else
                                    mPullToRefreshView.onFooterRefreshComplete(false);
                            } else {
                                //已经加载到最后一条
                                mPullToRefreshView.onFooterRefreshComplete(true);
                            }
                        }
                    }
                });
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                    Log.i("ss",e.getMessage());
            }
        });
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        page=1;
        firstData(page);
    }
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page++;
        getMoreData(page);
    }
    @OnClick({R.id.reload})
    public void click(View view){
        switch (view.getId()){
            case R.id.reload:
                firstData(page);
                break;
        }
    }

    public class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if("takePartInOk".equals(action)){
                firstData(1);
            }
        }
    }


}
