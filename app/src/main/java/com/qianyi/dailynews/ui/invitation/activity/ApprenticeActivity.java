package com.qianyi.dailynews.ui.invitation.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiInvite;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.fragment.bean.TudiBean;
import com.qianyi.dailynews.fragment.bean.TudiInfo;
import com.qianyi.dailynews.ui.invitation.adapter.ApprenticeAdapter;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/9.
 */

public class ApprenticeActivity extends BaseActivity {
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.tv_count) public TextView tv_count;
    @BindView(R.id.tv_income) public TextView tv_income;
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.rvList) public RecyclerView rvlist;
    @BindView(R.id.no_internet_rl) public RelativeLayout no_internet_rl;
    @BindView(R.id.no_data_rl) public RelativeLayout no_data_rl;
    private ApprenticeAdapter adapter;
    private CustomLoadingDialog customLoadingDialog;
    private String userId;
    private List<TudiInfo> infoList;
    @Override
    protected void initViews() {
        infoList=new ArrayList<>();
        customLoadingDialog=new CustomLoadingDialog(this);
        title.setText("徒弟列表");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvlist.setLayoutManager(new LinearLayoutManager(ApprenticeActivity.this));
        adapter = new ApprenticeAdapter(ApprenticeActivity.this,infoList);
        rvlist.setAdapter(adapter);

        userId= (String) SPUtils.get(this,"user_id","");
    }

    @Override
    protected void initData() {
        if (Utils.hasInternet()){
            customLoadingDialog.show();
            rvlist.setVisibility(View.VISIBLE);
            no_data_rl.setVisibility(View.GONE);
            no_internet_rl.setVisibility(View.GONE);
            getData();
        }else{
            rvlist.setVisibility(View.GONE);
            no_data_rl.setVisibility(View.GONE);
            no_internet_rl.setVisibility(View.VISIBLE);
        }
    }
    //获取数据
    private void getData() {
        ApiInvite.tudiList(ApiConstant.TUDI_LIST, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("tag",s);
                        Gson gson=new Gson();
                        TudiBean tudiBean = gson.fromJson(s, TudiBean.class);
                        String cash = tudiBean.getData().getCash();
                        String cnt = tudiBean.getData().getCnt();
                        tv_income.setText(cash);
                        tv_count.setText(cnt);
                        List<TudiInfo> tudiArray = tudiBean.getData().getTudiArray();
                        if (tudiArray!=null && tudiArray.size()>0){
                            infoList.addAll(tudiArray);
                            adapter.notifyDataSetChanged();
                        }else{
                            rvlist.setVisibility(View.GONE);
                            no_data_rl.setVisibility(View.VISIBLE);
                            no_internet_rl.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                rvlist.setVisibility(View.GONE);
                no_data_rl.setVisibility(View.GONE);
                no_internet_rl.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    protected void getResLayout() {
            setContentView(R.layout.activity_apprentice);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.reload})
    public void click(View view){
        switch (view.getId()){
            case R.id.reload:
                getData();
                break;
        }
    }
}
