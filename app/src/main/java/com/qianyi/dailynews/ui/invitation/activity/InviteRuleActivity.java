package com.qianyi.dailynews.ui.invitation.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.utils.WebviewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/24.
 */

public class InviteRuleActivity extends BaseActivity{
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.wv_webview)
    WebView wv_webview;
    private WebSettings webSettings;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected void initViews() {
        tv_title.setText("邀请规则");
        webSettings=wv_webview.getSettings();
        WebviewUtil.setWebview(wv_webview, webSettings);
        customLoadingDialog=new CustomLoadingDialog(this);
        //wv_webview.loadUrl(url);
    }
    @Override
    protected void initData() {
        customLoadingDialog.show();
        ApiMine.getWebview(ApiConstant.WEBVIEW, "INVITE", new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            String return_code = jsonObject.getString("return_code");
                            String data = jsonObject.getString("data");
                            if (return_code.equals("SUCCESS")){
                                wv_webview.loadDataWithBaseURL(null, data, "text/html" , "utf-8", null);
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
            }
        });

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_invite_rule);
    }

    @Override
    protected void initListener() {

    }
    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
