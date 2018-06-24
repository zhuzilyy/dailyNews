package com.qianyi.dailynews.ui.invitation.activity;

import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiInvite;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.fragment.bean.RecallBean;
import com.qianyi.dailynews.fragment.bean.RecallInfo;
import com.qianyi.dailynews.ui.invitation.adapter.WakeUpFriendAdapter;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/1.
 */

public class WakeFriendsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    public TextView title;
    @BindView(R.id.iv_back)
    public ImageView back;
    @BindView(R.id.tv_wakefriend_title)
    public TextView tv_wakefriend_title;
    @BindView(R.id.rv)
    public RecyclerView rv;
    @BindView(R.id.tv_noData)
    public TextView tv_noData;
    @BindView(R.id.tv_noInternet)
    public TextView tv_noInternet;
    public WakeUpFriendAdapter adapter;
    private CustomLoadingDialog customLoadingDialog;
    private String userId;
    private  List<RecallInfo> recallList;
    @Override
    protected void initViews() {
        title.setText("唤醒好友");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/sxs.ttf");
        tv_wakefriend_title.setTypeface(typeface1);
        rv.setLayoutManager(new LinearLayoutManager(WakeFriendsActivity.this));
        userId= (String) SPUtils.get(this,"user_id","");

        recallList=new ArrayList<>();
        adapter = new WakeUpFriendAdapter(WakeFriendsActivity.this,recallList);
        rv.setAdapter(adapter);

    }
    private void getData() {
        ApiInvite.callBackFriendList(ApiConstant.CALL_BACK_FRIEND_LIST, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customLoadingDialog.dismiss();
                        Gson gson=new Gson();
                        RecallBean recallBean = gson.fromJson(s, RecallBean.class);
                        String code = recallBean.getCode();
                        List<RecallInfo> list = recallBean.getData().getRecallList();
                        if (code.equals(ApiConstant.SUCCESS_CODE)){
                            if (list!=null && list.size()>0){
                                tv_noData.setVisibility(View.GONE);
                                rv.setVisibility(View.VISIBLE);
                                tv_noInternet.setVisibility(View.GONE);
                                recallList.addAll(list);
                                adapter.notifyDataSetChanged();
                            }else{
                                tv_noData.setVisibility(View.VISIBLE);
                                rv.setVisibility(View.GONE);
                                tv_noInternet.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                tv_noData.setVisibility(View.GONE);
                rv.setVisibility(View.GONE);
                tv_noInternet.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    protected void initData() {
        customLoadingDialog=new CustomLoadingDialog(this);
        if (Utils.hasInternet()){
            tv_noData.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            tv_noInternet.setVisibility(View.GONE);
            customLoadingDialog.show();
            getData();
        }else{
            tv_noData.setVisibility(View.GONE);
            rv.setVisibility(View.GONE);
            tv_noInternet.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_wekefriend);
    }

    @Override
    protected void initListener() {
        adapter.setOnRecallUserListener(new WakeUpFriendAdapter.RecallUserListener() {
            @Override
            public void recall(int position) {
                recallUser(position);
            }
        });
    }
    //唤醒好友
    private void recallUser(final int position) {
        customLoadingDialog.show();
        ApiMine.recall(ApiConstant.RECALL, userId, recallList.get(position).getUserId(), new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            String return_msg = jsonObject.getString("return_msg");
                            Toast.makeText(WakeFriendsActivity.this, return_msg, Toast.LENGTH_SHORT).show();
                            recallList.remove(position);
                            adapter.notifyDataSetChanged();
                            if (recallList.size()==0){
                                tv_noData.setVisibility(View.VISIBLE);
                                rv.setVisibility(View.GONE);
                                tv_noInternet.setVisibility(View.GONE);
                            }else{
                                tv_noData.setVisibility(View.GONE);
                                rv.setVisibility(View.VISIBLE);
                                tv_noInternet.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                Toast.makeText(WakeFriendsActivity.this, "召回失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.tv_noInternet})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv_noInternet:
                getData();
                break;
        }
    }
}
