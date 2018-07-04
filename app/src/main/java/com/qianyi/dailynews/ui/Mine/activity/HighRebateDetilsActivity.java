package com.qianyi.dailynews.ui.Mine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.ui.Mine.bean.MoneyDetailBean;
import com.qianyi.dailynews.utils.CodeTimerTask;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.WebviewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/13.
 */

public class HighRebateDetilsActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title)public TextView title;
    @BindView(R.id.btn_getMoney) public Button btn_getMoney;

    @BindView(R.id.moneyLogo_iv) public RoundedImageView moneyLogo_iv;
    @BindView(R.id.moneyTitle_tv) public TextView moneyTitle_tv;
    @BindView(R.id.moneyNum_tv) public TextView moneyNum_tv;
    @BindView(R.id.moneyDes_tv) public TextView moneyDes_tv;
    @BindView(R.id.moneyTime_tv) public TextView moneyTime_tv;
    @BindView(R.id.moneyPhone_tv) public TextView moneyPhone_tv;
    @BindView(R.id.moneyType_tv) public TextView moneyType_tv;
    @BindView(R.id.moneyNum2_tv) public TextView moneyNum2_tv;
    @BindView(R.id.moneyType2_tv) public TextView moneyType2_tv;
    @BindView(R.id.moneyTime2_tv) public TextView moneyTime2_tv;
    @BindView(R.id.moneyLeft_tv) public TextView moneyLeft_tv;
    @BindView(R.id.moneyCycle_tv) public TextView moneyCycle_tv;
    @BindView(R.id.moneyImg001_iv) public ImageView moneyImg001_iv;
    @BindView(R.id.moneyImg002_iv) public ImageView moneyImg002_iv;
    @BindView(R.id.moneyImg003_iv) public ImageView moneyImg003_iv;
    @BindView(R.id.webview) public WebView webview;

    @BindView(R.id.title04) public TextView title04;
    @BindView(R.id.title03) public TextView title03;
    @BindView(R.id.title02) public TextView title02;
    @BindView(R.id.title01) public TextView title01;

    @BindView(R.id.ll_bottom02) public LinearLayout ll_bottom02;
    @BindView(R.id.tv_shots) public TextView tv_shots;
    @BindView(R.id.tv_leftTime) public TextView tv_leftTime;
    @BindView(R.id.ll_upLoad) public LinearLayout ll_upLoad;

    private CustomLoadingDialog  customLoadingDialog;

    private String id;
    private String type;
    private String time;
    private String status;
    @Override
    protected void initViews() {
        customLoadingDialog = new CustomLoadingDialog(HighRebateDetilsActivity.this);

        id=getIntent().getStringExtra("id");
        type=getIntent().getStringExtra("type");
        time=getIntent().getStringExtra("time");




        if(!TextUtils.isEmpty(time)){
            tv_leftTime.setText(time+" s");

        }
        if("1".equals(type)){
            title01.setText("投资期限");
            title02.setText("投资金额");
            title03.setText("年华收益");
            title04.setText("额外返利");
        }

        title.setText("试玩应用");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initWebView(webview);
    }

    /***
     * 初始化webview
     * @param webview
     */
    private void initWebView(WebView webview) {
        WebviewUtil.setWebview(webview,webview.getSettings());
    }

    @Override
    protected void initData() {
        String userid = (String) SPUtils.get(HighRebateDetilsActivity.this, "user_id", "");
        if (TextUtils.isEmpty(userid)) {
            return;
        }
        ApiMine.highBackMoneyDetails(ApiConstant.HIGH_BACK_MONEY_DETAILS, userid, id, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                Log.i("sss",s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson() ;
                        MoneyDetailBean detailBean = gson.fromJson(s, MoneyDetailBean.class);
                        String code=detailBean.getCode();

                        if("0000".equals(code)){
                            MoneyDetailBean.MoneyDetailData data = detailBean.getData();
                            status=data.getStatus();
                            MoneyDetailBean.MoneyDetailData.MoneyDetailInfo detailInfo =data.getMakeMoney();


                            if(detailInfo!=null){
                                setDataForPage(detailInfo);
                            }

                            if("0".equals(status)){

                            }else if("1".equals(status) || "4".equals(status)){
                                btn_getMoney.setVisibility(View.GONE);
                            }else {
                                if("2".equals(status)){
                                    btn_getMoney.setEnabled(false);
                                    ll_bottom02.setVisibility(View.GONE);
                                    btn_getMoney.setText("待审批");
                                    btn_getMoney.setTextColor(Color.parseColor("#999999"));
                                }else if("3".equals(status)){
                                    btn_getMoney.setEnabled(false);
                                    ll_bottom02.setVisibility(View.GONE);
                                    btn_getMoney.setText("审批通过");
                                    btn_getMoney.setTextColor(Color.parseColor("#999999"));
                                }else if ("5".equals(status)){
                                    btn_getMoney.setEnabled(false);
                                    ll_bottom02.setVisibility(View.GONE);
                                    btn_getMoney.setText("取消");
                                    btn_getMoney.setTextColor(Color.parseColor("#999999"));
                                }else if("6".equals(status)){
                                    btn_getMoney.setEnabled(false);
                                    ll_bottom02.setVisibility(View.GONE);
                                    btn_getMoney.setText("过期");
                                    btn_getMoney.setTextColor(Color.parseColor("#999999"));
                                }
                            }



                        }



                    }
                });



            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("sss",e.getMessage());
            }
        });


    }

    /***
     * 赋值当前界面
     * @param info
     */
    private void setDataForPage(MoneyDetailBean.MoneyDetailData.MoneyDetailInfo info) {
        if(info!=null){
            String imgStr = info.getImgs();
            String []  imgArr = imgStr.split("\\$lvmq\\$");

            moneyTitle_tv.setText(info.getTitle());
            moneyNum_tv.setText("+"+info.getCash()+"元");
            moneyDes_tv.setText(info.getExposition());
            webview.loadUrl(info.getUrl());

            moneyType2_tv.setText(info.getRewardsType());
            moneyLeft_tv.setText(info.getParticipantsNum());
            moneyTime2_tv.setText(info.getTimeLimit());



            if(imgArr.length>0){
                Glide.with(HighRebateDetilsActivity.this).load(imgArr[0]).into(moneyImg001_iv);
                if(imgArr.length>1){
                    Glide.with(HighRebateDetilsActivity.this).load(imgArr[1]).into(moneyImg002_iv);
                    if(imgArr.length>2){
                        Glide.with(HighRebateDetilsActivity.this).load(imgArr[2]).into(moneyImg003_iv);
                    }
                }
            }
        }

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_hight_rebate_details);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.btn_getMoney,R.id.tv_shots})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_getMoney:
                // 0:表示没有参与  1：待上传   2：待审批  3：审批通过   4：审批不通过  5：取消   6：过期
               // if("0".equals(status)){
                    takePartIn();
               // }
                break;
            case R.id.tv_shots:
                Intent intent  = new Intent(HighRebateDetilsActivity.this,UploadScreenshotsActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /***
     * 参与高额返利
     */
    private void takePartIn() {
        String userid = (String) SPUtils.get(HighRebateDetilsActivity.this, "user_id", "");
        if (TextUtils.isEmpty(userid)) {
            return;
        }
        customLoadingDialog.show();
        ApiMine.takePartIn(ApiConstant.takePartInUrl, id, userid, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                customLoadingDialog.dismiss();
                Log.i("ss",s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code  = jsonObject.getString("code");
                    if("0000".equals(code)){
                        btn_getMoney.setVisibility(View.GONE);
                        CodeTimerTask.getInstence(time).starrTimer(tv_leftTime);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                Log.i("ss",e.getMessage());
            }
        });
    }
}
