package com.qianyi.dailynews.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiInvite;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.ui.account.bean.WXAccessTokenInfo;
import com.qianyi.dailynews.ui.account.bean.WXErrorInfo;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.Utils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import okhttp3.Call;
import okhttp3.Response;


public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    public  int WX_LOGIN = 1;
    private Gson gson=new Gson();

    private IWXAPI iwxapi;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        iwxapi = WXAPIFactory.createWXAPI(this, ApiConstant.APP_ID, false);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        iwxapi.handleIntent(getIntent(), this);
        userId= (String) SPUtils.get(WXEntryActivity.this,"user_id","");

    }
    @Override
    public void onReq(BaseReq baseReq) {
    }
    //请求回调结果处理
    @Override
    public void onResp(BaseResp baseResp) {

        //微信登录为getType为1，分享为0


        if (baseResp.getType() == WX_LOGIN){
            //登录回调
            SendAuth.Resp resp = (SendAuth.Resp) baseResp;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = String.valueOf(resp.code);
                    //获取用户信息
                    getAccessToken(code);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    break;
                default:
                    break;
            }
        }else{
            //分享成功回调
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //分享成功
                    Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_LONG).show();
                    //shareSuccess();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    //分享取消
                    Toast.makeText(WXEntryActivity.this, "分享取消", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //分享拒绝
                    Toast.makeText(WXEntryActivity.this, "分享拒绝", Toast.LENGTH_LONG).show();
                    break;
            }
        }
        finish();
    }
    //分享成功
    private void shareSuccess() {
        ApiInvite.shareAfter(ApiConstant.SHARE_AFTER, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                finish();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String code = jsonObject.getString("code");
                    if (code.equals(ApiConstant.SUCCESS_CODE)){
                        Intent intent=new Intent();
                        intent.setAction("com.action.share.success");
                        sendBroadcast(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }
    /***
     * 获取AccessToken
     * @param code
     */
    private void getAccessToken(String code) {
        String GetAccessTokenURL="https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + ApiConstant.APP_ID +
                "&secret=" + ApiConstant.APP_SECRET +
                "&code=" + code +
                "&grant_type=authorization_code";

        RequestParams params=new RequestParams(GetAccessTokenURL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                // 判断是否获取成功，成功则去获取用户信息，否则提示失败
                processGetAccessTokenResult(result);
                Log.i("ee",result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("err","错误=="+ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    /**
     * 处理获取的授权信息结果
     * @param response 授权信息结果
     */
    private void processGetAccessTokenResult(String response) {
        // 验证获取授权口令返回的信息是否成功
        if (validateSuccess(response)) {
            // 使用Gson解析返回的授权口令信息
            WXAccessTokenInfo tokenInfo = gson.fromJson(response, WXAccessTokenInfo.class);
            // 保存信息到手机本地
            Utils.saveAccessInfotoLocation(tokenInfo,WXEntryActivity.this);
            // 获取用户信息
            getUserInfo(tokenInfo.getAccess_token(), tokenInfo.getOpenid());
        } else {
            // 授权口令获取失败，解析返回错误信息
            WXErrorInfo wxErrorInfo = gson.fromJson(response, WXErrorInfo.class);
            // 提示错误信息
            Log.i("wxErrorInfo","错误信息: " + wxErrorInfo.getErrmsg());

        }
    }
    /**
     * 验证是否成功
     *
     * @param response 返回消息
     * @return 是否成功
     */
    private boolean validateSuccess(String response) {
        String errFlag = "errmsg";
        return (errFlag.contains(response) && !"ok".equals(response))
                || (!"errcode".contains(response) && !errFlag.contains(response));
    }

    /*****
     * 获取用户信息
     * @param access_token
     * @param openid
     */
    private void getUserInfo(String access_token, String openid){
        String url="https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + access_token +
                "&openid=" + openid;
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //Toast.makeText(WXEntryActivity.this, result+"", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    String openid=jsonObject.getString("openid");
                    String unionid=jsonObject.getString("unionid");
                    String nickname=jsonObject.getString("nickname");
                    int sex=jsonObject.getInt("sex");
                    String headimgurl=jsonObject.getString("headimgurl");
                    Intent intent=new Intent();
                    intent.putExtra("openid",openid);
                    intent.putExtra("unionid",unionid);
                    intent.putExtra("nickname",nickname);
                    intent.putExtra("sex",sex);
                    intent.putExtra("headimgurl",headimgurl);
                    intent.setAction("com.action.wechat");
                    sendBroadcast(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //在这个地方获取到用户的信息后，将信息上传到自己分服务器，通过判断 是否绑定openid 来决定去哪：
                //a:手机号注册 并绑定openid  直接登录操作，返回userInfo
                //b:手机号注册，但未绑定openid,弹出绑定手机号和输入验证码界面，提交到后台，进行openid和手机号的绑定
                //c:手机号未注册，第一次直接微信登录，跳转到注册界面，完善信息并将手机号和openID绑定并登录
                //finish();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("wx",ex+"获取用户信息==错误");
            }
            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("wx",cex+"获取用户信息==错误");
            }
            @Override
            public void onFinished() {
            }
        });
    }
}
