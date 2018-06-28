package com.qianyi.dailynews.ui.Mine.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.ui.Mine.adapter.HighRebateTaskAdapter;
import com.qianyi.dailynews.ui.Mine.adapter.WithdrawalAdapter;
import com.qianyi.dailynews.ui.Mine.adapter.WithdrawalRecordAdapter;
import com.qianyi.dailynews.ui.Mine.bean.FanLiBean;
import com.qianyi.dailynews.ui.Mine.bean.FanLiInfo;
import com.qianyi.dailynews.ui.Mine.bean.GoldCoinBean;
import com.qianyi.dailynews.ui.Mine.bean.GoldCoinData;
import com.qianyi.dailynews.ui.Mine.bean.WithdrawalBean;
import com.qianyi.dailynews.ui.Mine.bean.WithdrawalInfo;
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
 * Created by Administrator on 2018/5/5.
 */

public class PresentStatusActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;

    @BindView(R.id.no_internet_rl)
    RelativeLayout no_internet_rl;
    private int page=1;
    private WithdrawalRecordAdapter withdrawalAdapter;
    private List<WithdrawalInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    private  String userId;
    @Override
    protected void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("提现状态");
        customLoadingDialog=new CustomLoadingDialog(this);
        infoList=new ArrayList<>();
        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);
        withdrawalAdapter =new WithdrawalRecordAdapter(this,infoList);
        listview.setAdapter(withdrawalAdapter);
        userId= (String) SPUtils.get(this,"user_id","");
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
    protected void getResLayout() {
        setContentView(R.layout.activity_present_status);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }

    private void firstData(int page) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        ApiMine.withdrawalRecord(ApiConstant.WITHDRAWAL_RECORD, userId,page, ApiConstant.PAGE_SIZE,new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        WithdrawalBean withdrawalBean = gson.fromJson(s, WithdrawalBean.class);
                        String code = withdrawalBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS_CODE)){
                            mPullToRefreshView.onHeaderRefreshComplete();
                            List<WithdrawalInfo> list = withdrawalBean.getData();
                            if (list!=null && list.size()>0){
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customLoadingDialog.dismiss();
                        mPullToRefreshView.setVisibility(View.GONE);
                        no_data_rl.setVisibility(View.GONE);
                        no_internet_rl.setVisibility(View.VISIBLE);
                    }
                });

            }
        });

    }
    //获取更多数据
    private void getMoreData(int page) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        ApiMine.withdrawal(ApiConstant.WITHDRAWAWAL, userId,page, ApiConstant.PAGE_SIZE, "",new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        WithdrawalBean withdrawalBean = gson.fromJson(s, WithdrawalBean.class);
                        String code = withdrawalBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS_CODE)){
                            mPullToRefreshView.onHeaderRefreshComplete();
                            List<WithdrawalInfo> list = withdrawalBean.getData();
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
}
