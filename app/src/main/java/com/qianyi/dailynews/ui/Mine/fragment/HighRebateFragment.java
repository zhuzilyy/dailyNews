package com.qianyi.dailynews.ui.Mine.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.ui.Mine.activity.HighRebateDetilsActivity;
import com.qianyi.dailynews.ui.Mine.activity.MessageActivity;
import com.qianyi.dailynews.ui.Mine.adapter.HighRebateAdapter;
import com.qianyi.dailynews.ui.Mine.adapter.MessageAdapter;
import com.qianyi.dailynews.ui.Mine.bean.HightBackBean;
import com.qianyi.dailynews.ui.news.activity.NewsDetailsActivity;
import com.qianyi.dailynews.ui.news.activity.OneCommDetailsActivity;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.views.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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

    private HighRebateAdapter adapter;
    private List<HightBackBean.HightBackData> bigList=new ArrayList<>();

    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_high_rebate,null);
    }

    @Override
    protected void initViews() {
        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);
        View headview = LayoutInflater.from(getActivity()).inflate(R.layout.hight_head,null);
        adapter=new HighRebateAdapter(getActivity());
        listview.setAdapter(adapter);
        listview.addHeaderView(headview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent = new Intent(getActivity(), HighRebateDetilsActivity.class);
               startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {



    }

    private void firstData() {
        String userid = (String) SPUtils.get(getActivity(),"user_id","");
        if(TextUtils.isEmpty(userid)){
            return;
        }
        ApiMine.highBackMoney(ApiConstant.HIGH_BACK_MONEY, userid, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                Log.i("sss",s);
                Gson gson = new Gson();
                HightBackBean hightBackBean=gson.fromJson(s,HightBackBean.class);
                if(hightBackBean!=null){
                    String code = hightBackBean.getCode();
                    if("0000".equals(code)){
                       List<HightBackBean.HightBackData> beanList= hightBackBean.getData();
                        if(beanList.size()>0){
                            bigList.clear();
                            bigList.addAll(beanList);
                            adapter.notifyDataSetChanged();
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

    private void moreData() {
    }


    @Override
    protected void initListener() {

    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        moreData();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        firstData();
    }
}
