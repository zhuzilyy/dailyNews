package com.qianyi.dailynews.ui.Mine.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.MainActivity;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.ui.news.activity.SearchActivity;
import com.qianyi.dailynews.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/8.
 */

public class ActivityZoneActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_signState)
    TextView tv_signState;
    @BindView(R.id.tv_shareState)
    TextView tv_shareState;
    @BindView(R.id.tv_readState)
    TextView tv_readState;
    @BindView(R.id.tv_searchState)
    TextView tv_searchState;

    @BindView(R.id.tv_sign)
    TextView tv_sign;
    @BindView(R.id.tv_share)
    TextView tv_share;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_read)
    TextView tv_read;
    private CustomLoadingDialog customLoadingDialog;
    private String user_id,sign,search,read,share;
    private int count;
    private MyReceiver myReceiver;
    @Override
    protected void initViews() {
        tv_title.setText("活动专区");
        customLoadingDialog=new CustomLoadingDialog(this);
        user_id= (String) SPUtils.get(ActivityZoneActivity.this,"user_id","");

        myReceiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.action.search.mission");
        registerReceiver(myReceiver,intentFilter);
    }
    @Override
    protected void initData() {
        customLoadingDialog.show();
        getData();
    }

    private void getData() {
        ApiMine.activityZone(ApiConstant.ACTIVITY_ZONE,user_id,new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                Log.i("tag",s);
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    sign =data.getString("sign");
                    search = data.getString("search");
                    read =data.getString("read");
                    share =data.getString("share");
                    tv_sign.setText(" 1.签到("+sign+"/1)");
                    tv_share.setText(" 2.分享到朋友圈("+share+"/1)");
                    tv_read.setText(" 3.认真阅读新闻("+read+"/10)");
                    tv_search.setText(" 4.搜索新闻阅读("+search+"/2)");
                    if (sign.equals("1")){
                        tv_signState.setBackgroundResource(R.drawable.bg_activity_finish);
                        tv_signState.setTextColor(Color.parseColor("#999999"));
                        tv_signState.setText("已完成");
                        count++;
                    }
                    if (share.equals("1")){
                        tv_shareState.setBackgroundResource(R.drawable.bg_activity_finish);
                        tv_shareState.setTextColor(Color.parseColor("#999999"));
                        tv_shareState.setText("已完成");
                        count++;
                    }
                    if (read.equals("10")){
                        tv_readState.setBackgroundResource(R.drawable.bg_activity_finish);
                        tv_readState.setTextColor(Color.parseColor("#999999"));
                        tv_readState.setText("已完成");
                        count++;
                    }
                    if (search.equals("2")){
                        tv_searchState.setBackgroundResource(R.drawable.bg_activity_finish);
                        tv_searchState.setTextColor(Color.parseColor("#999999"));
                        tv_searchState.setText("已完成");
                        count++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                Log.i("tag",e.getMessage());
            }
        });
    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_activity_zone);
    }

    @Override
    protected void initListener() {

    }
    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back,R.id.btn_confrim,R.id.tv_signState,R.id.tv_shareState,R.id.tv_readState,R.id.tv_searchState})
    public void click(View view){
        Intent intent=null;
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_confrim:
                if (count!=4){
                    Toast.makeText(this, "先去完成新手任务", Toast.LENGTH_SHORT).show();
                    return;
                }
                jumpActivity(this,WithdrawalsActivity.class);
                break;
            case R.id.tv_signState:
                if (sign.equals("1")){
                    return;
                }
                finish();
                break;
            case R.id.tv_shareState:
                if (share.equals("1")){
                    return;
                }
                //jumpActivity(ActivityZoneActivity.this,TaskCenterActivity.class);
                break;
            case R.id.tv_readState:
                if (read.equals("10")){
                    return;
                }
                intent=new Intent();
                intent.setAction("com.action.read");
                sendBroadcast(intent);
                jumpActivity(ActivityZoneActivity.this, MainActivity.class);
                break;
            case R.id.tv_searchState:
                if (search.equals("2")){
                    return;
                }
                intent=new Intent(ActivityZoneActivity.this,SearchActivity.class);
                intent.putExtra("tag","mission");
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myReceiver!=null){
            unregisterReceiver(myReceiver);
        }
    }
    class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.action.search.mission")){
                getData();
            }
        }
    }
}
