package com.qianyi.dailynews.ui.Mine.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
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
    private String user_id;
    private int count;
    @Override
    protected void initViews() {
        tv_title.setText("活动专区");
        customLoadingDialog=new CustomLoadingDialog(this);
        user_id= (String) SPUtils.get(ActivityZoneActivity.this,"user_id","");
    }
    @Override
    protected void initData() {
        customLoadingDialog.show();
        ApiMine.activityZone(ApiConstant.ACTIVITY_ZONE,user_id,new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                Log.i("tag",s);
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String sign =data.getString("sign");
                    String search = data.getString("search");
                    String read =data.getString("read");
                    String share =data.getString("share");
                    tv_sign.setText(" 1.签到("+sign+"/1)");
                    tv_share.setText(" 2.分享到朋友圈("+share+"/1)");
                    tv_read.setText(" 3.认真阅读新闻("+read+"/10)");
                    tv_search.setText(" 4.搜索新闻阅读("+search+"/2)");
                    if (sign.equals("1")){
                        tv_signState.setBackgroundResource(R.drawable.bg_activity_finish);
                        tv_signState.setText("已完成");
                        count++;
                    }
                    if (share.equals("1")){
                        tv_shareState.setBackgroundResource(R.drawable.bg_activity_finish);
                        tv_shareState.setText("已完成");
                        count++;
                    }
                    if (read.equals("10")){
                        tv_readState.setBackgroundResource(R.drawable.bg_activity_finish);
                        tv_readState.setText("已完成");
                        count++;
                    }
                    if (search.equals("2")){
                        tv_searchState.setBackgroundResource(R.drawable.bg_activity_finish);
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
    @OnClick({R.id.iv_back,R.id.btn_confrim})
    public void click(View view){
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
        }
    }
}
