package com.qianyi.dailynews.ui.Mine.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.qianyi.dailynews.ui.Mine.bean.MoneyDetailBean;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.WebviewUtil;

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




    private String id;
    @Override
    protected void initViews() {
        id=getIntent().getStringExtra("id");
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
            public void onSuccess(Call call, Response response, String s) {
                Log.i("sss",s);
                Gson gson = new Gson() ;
                MoneyDetailBean detailBean=gson.fromJson(s, MoneyDetailBean.class);
                String code=detailBean.getCode();
                if("0000".equals(code)){
                    MoneyDetailBean.MoneyDetailData data = detailBean.getData();
                    MoneyDetailBean.MoneyDetailData.MoneyDetailInfo detailInfo =data.getMakeMoney();
                    if(detailInfo!=null){
                        setDataForPage(detailInfo);
                    }
                }


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

        Glide.with(HighRebateDetilsActivity.this).load(info.getLogo()).into(moneyLogo_iv);
        moneyTitle_tv.setText(info.getTitle());
        moneyNum_tv.setText("+"+info.getCash()+"元");
        moneyDes_tv.setText(info.getExposition());
        webview.loadUrl(info.getUrl());


//        @BindView(R.id.moneyLogo_iv) public RoundedImageView moneyLogo_iv;
//        @BindView(R.id.moneyTitle_tv) public TextView moneyTitle_tv;
//        @BindView(R.id.moneyNum_tv) public TextView moneyNum_tv;
//        @BindView(R.id.moneyDes_tv) public TextView moneyDes_tv;
//        @BindView(R.id.moneyTime_tv) public TextView moneyTime_tv;
//        @BindView(R.id.moneyPhone_tv) public TextView moneyPhone_tv;
//        @BindView(R.id.moneyType_tv) public TextView moneyType_tv;
//        @BindView(R.id.moneyNum2_tv) public TextView moneyNum2_tv;
//        @BindView(R.id.moneyType2_tv) public TextView moneyType2_tv;
//        @BindView(R.id.moneyTime2_tv) public TextView moneyTime2_tv;
//        @BindView(R.id.moneyLeft_tv) public TextView moneyLeft_tv;
//        @BindView(R.id.moneyCycle_tv) public TextView moneyCycle_tv;
//        @BindView(R.id.moneyImg001_iv) public TextView moneyImg001_iv;
//        @BindView(R.id.moneyImg002_iv) public TextView moneyImg002_iv;
//        @BindView(R.id.moneyImg003_iv) public TextView moneyImg003_iv;


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
    @OnClick({R.id.btn_getMoney})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_getMoney:

                break;
            default:
                break;
        }
    }
}
