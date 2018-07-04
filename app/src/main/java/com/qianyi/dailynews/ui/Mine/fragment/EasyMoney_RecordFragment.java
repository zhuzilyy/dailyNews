package com.qianyi.dailynews.ui.Mine.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.ui.Mine.activity.MakeMoneyEasilyDetailActivity;
import com.qianyi.dailynews.ui.Mine.adapter.EasyMoneyTaskAdapter;
import com.qianyi.dailynews.ui.Mine.adapter.HighRebateTaskAdapter;
import com.qianyi.dailynews.ui.Mine.bean.FanLiBean;
import com.qianyi.dailynews.ui.Mine.bean.FanLiInfo;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.Utils;
import com.qianyi.dailynews.views.PullToRefreshView;
import com.tencent.wxop.stat.EasyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/1.
 */

public class EasyMoney_RecordFragment extends BaseFragment  implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener,View.OnClickListener{
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;

    @BindView(R.id.no_internet_rl)
    RelativeLayout no_internet_rl;
    private int page=1;

    private EasyMoneyTaskAdapter adapter;
    private List<FanLiInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    private  String userId;


    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_easy_record_money,null);
    }

    @Override
    protected void initViews() {
        customLoadingDialog=new CustomLoadingDialog(getActivity());
        infoList=new ArrayList<>();
        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);
        adapter=new EasyMoneyTaskAdapter(getActivity(),infoList);
        listview.setAdapter(adapter);
        userId= (String) SPUtils.get(getActivity(),"user_id","");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), MakeMoneyEasilyDetailActivity.class);
                intent.putExtra("gold",infoList.get(i).getGold());
                intent.putExtra("id",infoList.get(i).getId());
                startActivity(intent);
            }
        });

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

    }
    private void firstData(int page) {
        mPullToRefreshView.setEnablePullTorefresh(true);

        ApiMine.fanliTaskList(ApiConstant.MAKE_MONEY_LIST, userId,page,ApiConstant.PAGE_SIZE, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                Log.i("ssss",s);
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
                getActivity().runOnUiThread(new Runnable() {
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
        ApiMine.fanliTaskList(ApiConstant.FANLI_TASK_LIST, userId, page, ApiConstant.PAGE_SIZE, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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

            }
        });
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page=1;
        firstData(page);
    }
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        page++;
        getMoreData(page);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reload:
                firstData(page);
                break;
        }
    }
}
