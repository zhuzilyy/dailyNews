package com.qianyi.dailynews.ui.Mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
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
import com.qianyi.dailynews.ui.Mine.adapter.MessageAdapter;
import com.qianyi.dailynews.ui.Mine.adapter.WithdrawalRecordAdapter;
import com.qianyi.dailynews.ui.Mine.bean.MessageBean;
import com.qianyi.dailynews.ui.Mine.bean.MessageInfo;
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
 * Created by Administrator on 2018/5/9.
 */

public class MessageActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener{
    @BindView(R.id.tv_title)
    TextView tv_title;
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
    private String userId;
    private MessageAdapter messageAdapter;
    private List<MessageInfo> infoList;
    @Override
    protected void initViews() {
        tv_title.setText("消息中心");
        customLoadingDialog=new CustomLoadingDialog(this);
        infoList=new ArrayList<>();
        userId= (String) SPUtils.get(this,"user_id","");
        messageAdapter=new MessageAdapter(MessageActivity.this,infoList);
        listview.setAdapter(messageAdapter);
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
    private void getData(int page) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        ApiMine.getMessageList(ApiConstant.MESSAGE_LIST, userId,page, ApiConstant.PAGE_SIZE,new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        MessageBean messageBean = gson.fromJson(s, MessageBean.class);
                        String code = messageBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS)){
                            mPullToRefreshView.onHeaderRefreshComplete();
                            List<MessageInfo> list = messageBean.getData();
                            if (list!=null && list.size()>0){
                                mPullToRefreshView.setVisibility(View.VISIBLE);
                                no_data_rl.setVisibility(View.GONE);
                                no_internet_rl.setVisibility(View.GONE);
                                infoList.addAll(list);
                                messageAdapter.notifyDataSetChanged();
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
    private void getMoreData(int page) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        ApiMine.getMessageList(ApiConstant.MESSAGE_LIST, userId,page, ApiConstant.PAGE_SIZE,new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        MessageBean messageBean = gson.fromJson(s, MessageBean.class);
                        String code = messageBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS)){
                            mPullToRefreshView.onHeaderRefreshComplete();
                            List<MessageInfo> list = messageBean.getData();
                            if (list!=null && list.size()>0){
                                //判断是不是刷新
                                infoList.addAll(list);
                                messageAdapter.notifyDataSetChanged();
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
    //刷新事件
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        infoList.clear();
        page=0;
        getData(page);
    }
    //加载事件
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        page++;
        getMoreData(page);
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_message);
    }

    @Override
    protected void initListener() {
        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MessageActivity.this,MessageDetailActivity.class);
                intent.putExtra("url",infoList.get(i).getMessage());
                intent.putExtra("title","消息详情");
                startActivity(intent);
            }
        });
    }
    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back,R.id.reload})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.reload:
                getData(0);
                break;
        }
    }
}
