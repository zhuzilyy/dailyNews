package com.qianyi.dailynews.ui.Mine.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.ui.Mine.adapter.GoldAdapter;
import com.qianyi.dailynews.ui.Mine.adapter.WithdrawalAdapter;
import com.qianyi.dailynews.ui.Mine.bean.GoldCoinBean;
import com.qianyi.dailynews.ui.Mine.bean.GoldCoinData;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.Utils;
import com.qianyi.dailynews.views.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/1.
 */

public class WithdrawalsFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener{
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.no_internet_rl)
    RelativeLayout no_internet_rl;
    private int page=0;
    private CustomLoadingDialog customLoadingDialog;
    private WithdrawalAdapter withdrawalAdapter;
    private List<GoldCoinData> infoList;
    private boolean isRefresh;
    private String userId;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_withdrawals,null);
    }

    @Override
    protected void initViews() {
        customLoadingDialog=new CustomLoadingDialog(getActivity());
        infoList=new ArrayList<>();
        withdrawalAdapter =new WithdrawalAdapter(getActivity(),infoList);
        listview.setAdapter(withdrawalAdapter);
        userId= (String) SPUtils.get(getActivity(),"user_id","");
    }

    @Override
    protected void initData() {
        if (!Utils.hasInternet()){
            mPullToRefreshView.setVisibility(View.GONE);
            no_data_rl.setVisibility(View.GONE);
            no_internet_rl.setVisibility(View.VISIBLE);
        }else{
            customLoadingDialog.show();
            getData(page);
        }
    }

    //获取数据
    private void getData(int page) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        ApiMine.withdrawal(ApiConstant.WITHDRAWAWAL, userId,page, ApiConstant.PAGE_SIZE, "",new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        GoldCoinBean goldCoinBean = gson.fromJson(s, GoldCoinBean.class);
                        String code = goldCoinBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS_CODE)){
                            mPullToRefreshView.onHeaderRefreshComplete();
                            List<GoldCoinData> list = goldCoinBean.getData();
                            if (list!=null && list.size()>0){
                                //判断是不是刷新
                                if (isRefresh){
                                    infoList.clear();
                                    isRefresh=false;
                                }
                                mPullToRefreshView.setVisibility(View.VISIBLE);
                                no_data_rl.setVisibility(View.GONE);
                                no_internet_rl.setVisibility(View.GONE);
                                infoList.addAll(list);
                                withdrawalAdapter.notifyDataSetChanged();
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
    private void getMoreData(int page) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        ApiMine.withdrawal(ApiConstant.WITHDRAWAWAL, userId,page, ApiConstant.PAGE_SIZE, "",new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                        public void run() {
                        Gson gson=new Gson();
                        GoldCoinBean goldCoinBean = gson.fromJson(s, GoldCoinBean.class);
                        String code = goldCoinBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS_CODE)){
                            mPullToRefreshView.onHeaderRefreshComplete();
                            List<GoldCoinData> list = goldCoinBean.getData();
                            if (list!=null && list.size()>0){
                                //判断是不是刷新
                                infoList.addAll(list);
                                withdrawalAdapter.notifyDataSetChanged();
                                //判断是不是没有更多数据了
                                if (list.size() < Integer.parseInt(ApiConstant.PAGE_SIZE)) {
                                    mPullToRefreshView.onFooterRefreshComplete(true);
                                }else{
                                    mPullToRefreshView.onFooterRefreshComplete(false);
                                }
                            }else{
                                //已经加载到最后一条
                                mPullToRefreshView.onFooterRefreshComplete(true);
                            }
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }
    @Override
    protected void initListener() {
        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);
    }
    //刷新事件
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        isRefresh=true;
        page=0;
        getData(page);
    }
    //加载事件
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        page++;
        getMoreData(page);
    }
}
