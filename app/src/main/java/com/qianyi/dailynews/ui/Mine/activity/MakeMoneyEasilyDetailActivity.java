package com.qianyi.dailynews.ui.Mine.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.ui.invitation.activity.IncomeShowActivity;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.WebviewUtil;
import com.qianyi.dailynews.utils.WhiteBgBitmapUtil;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.statistic.WBAgent;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MakeMoneyEasilyDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.webview)
    public WebView webview;
    @BindView(R.id.iv_back)
    public ImageView back;
    @BindView(R.id.tv_title)
    public TextView title;
    @BindView(R.id.tv_shareNow)
    public TextView tv_shareNow;
    @BindView(R.id.ll_shai)
    public RelativeLayout ll_shai;
    @BindView(R.id.pb_webview)
    ProgressBar pb_webview;
    @BindView(R.id.tv_gold)
    public TextView tv_gold;
    public LinearLayout ll_friendCircle;//分享到盆友圈
    public LinearLayout ll_QQ;//分享到QQ
    public LinearLayout ll_wechat;//分享到微信
    public LinearLayout ll_weibo;//分享到微博

    private PopupWindow pw_share;
    private View view_share, view_onekeyshoutu;
    private IWXAPI mWxApi;
    private WbShareHandler shareHandler;
    private CustomLoadingDialog customLoadingDialog;
    private String userId;
    private String gold;
    private String id;
    private String token;
    private String ShareUrl;


    @Override
    protected void initViews() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        id=getIntent().getStringExtra("id");
        title.setText("");
        gold=getIntent().getStringExtra("gold");
        if(!TextUtils.isEmpty(gold)){
            tv_gold.setText("每好友阅读+"+gold+"金币");
        }

        String userid = (String) SPUtils.get(MakeMoneyEasilyDetailActivity.this, "user_id", "");
        if (!TextUtils.isEmpty(userid)) {
            ApiMine.BeforeShareForToken(ApiConstant.easyMoneyShare, id, userid, new RequestCallBack<String>() {
                @Override
                public void onSuccess(Call call, Response response, String s) {
                    Log.i("ss",s);
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
                        token= jsonObject1.getString("token");
                        Log.i("token",token);
                        //显示分享内容
                        setData2(token);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onEror(Call call, int statusCode, Exception e) {
                    Log.i("ss",e.getMessage());
                }
            });
        }


    }

    /***
     * 显示分享内容
     * @param token
     */
    private void setData2(String token) {

        WebviewUtil.setWebview(webview, webview.getSettings());
        ShareUrl=ApiConstant.ShareUrl+token;
        webview.loadUrl(ApiConstant.ShareUrl+token);
        loadingWebview();
        view_share = LayoutInflater.from(MakeMoneyEasilyDetailActivity.this).inflate(R.layout.pw_share, null);
        ll_friendCircle = view_share.findViewById(R.id.ll_friendCircle);
        ll_QQ = view_share.findViewById(R.id.ll_QQ);
        ll_wechat = view_share.findViewById(R.id.ll_wechat);
        ll_weibo = view_share.findViewById(R.id.ll_weibo);

        ll_friendCircle.setOnClickListener(this);
        ll_QQ.setOnClickListener(this);
        ll_wechat.setOnClickListener(this);
        ll_weibo.setOnClickListener(this);

        mWxApi = WXAPIFactory.createWXAPI(this, ApiConstant.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(ApiConstant.APP_ID);

        customLoadingDialog = new CustomLoadingDialog(this);
        userId = (String) SPUtils.get(this, "user_id", "");


    }

    private void loadingWebview() {
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (pb_webview!=null){
                    pb_webview.setProgress(newProgress);
                    if(newProgress==100){
                        pb_webview.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_makemoney_details);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }

    @OnClick({R.id.tv_shareNow})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shareNow:
               // shwoSharePw();
                shareFriendCircle();
                break;
            case R.id.btn_share:
                shwoSharePw();
                break;
            case R.id.ll_wechat:
                //微信分享
                //Toast.makeText(this, "微信分享", Toast.LENGTH_SHORT).show();
                shareFriends();
                pw_share.dismiss();
                break;
            case R.id.ll_friendCircle:
                shareFriendCircle();
                pw_share.dismiss();
                //朋友圈分享
                //Toast.makeText(this, "朋友圈分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_QQ:
                //QQ分享
                //Toast.makeText(this, "QQ分享", Toast.LENGTH_SHORT).show();
              /*  shareFriends();
                pw_share.dismiss();*/
                break;
            case R.id.ll_weibo:
                //微博分享
                //Toast.makeText(this, "微博分享", Toast.LENGTH_SHORT).show();
                shareWeiBo();
                pw_share.dismiss();
                break;
        }
    }

    private void shwoSharePw() {
        pw_share = new PopupWindow(MakeMoneyEasilyDetailActivity.this);
        pw_share.setContentView(view_share);
        pw_share.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pw_share.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pw_share.setTouchable(true);
        pw_share.setFocusable(true);
        pw_share.setBackgroundDrawable(new BitmapDrawable());
        pw_share.setAnimationStyle(R.style.AnimBottom);
        pw_share.showAtLocation(ll_shai, Gravity.BOTTOM, 0, 0);
        // 设置pw弹出时候的背景颜色的变化
        backgroundAlpha(0.5f);
        pw_share.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }


    //分享到微信
    private void shareFriends() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.gaokaoygzy.cn/download";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "每日速报";
        msg.description = "每日速报";
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(this, bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        msg.setThumbImage(thumbBmp);
        bmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        // req.scene = sendtype==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        mWxApi.sendReq(req);
    }
    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    private void shareFriendCircle() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ShareUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "每日速报";
        msg.description = "每日速报";
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(this, bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        msg.setThumbImage(thumbBmp);
        bmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        // req.scene = sendtype==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        mWxApi.sendReq(req);
    }

    //分享到微博
    private void shareWeiBo() {
        initLog();
        //startActivity(new Intent(getActivity(), WBAuthActivity.class));
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();
        // sendMessage(true, true);
        shareWebPage();
    }

    private void initLog() {
        WBAgent.setAppKey(ApiConstant.APP_KEY_WEIBO);
        WBAgent.setChannel("weibo"); //这个是统计这个app 是从哪一个平台down下来的  百度手机助手
        WBAgent.openActivityDurationTrack(false);
        try {
            //设置发送时间间隔 需大于90s小于8小时
            WBAgent.setUploadInterval(91000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void shareWebPage() {
      /*  WebpageObject mediaObj =new WebpageObject();
        //创建文本消息对象
        TextObject textObject =new TextObject();
        textObject.text= "你分享内容的描述"+"分享网页的话加上网络地址";

        textObject.title= "哈哈哈哈哈哈";

        //创建图片消息对象，如果只分享文字和网页就不用加图片

        WeiboMultiMessage message =new WeiboMultiMessage();

        ImageObject imageObject =new ImageObject();

        // 设置 Bitmap 类型的图片到视频对象里        设置缩略图。 注意：最终压缩过的缩略图大小 不得超过 32kb。

        Bitmap bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.test);

        imageObject.setImageObject(bitmap);

        message.textObject= textObject;

        message.imageObject= imageObject;

        message.mediaObject= mediaObj;*/


        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = "测试title";
        mediaObject.description = "测试描述";
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);

        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = "http://news.sina.com.cn/c/2013-10-22/021928494669.shtml";
        mediaObject.defaultText = "Webpage 默认文案";
        WeiboMultiMessage message = new WeiboMultiMessage();
        message.mediaObject = mediaObject;
        shareHandler.shareMessage(message, false);
    }

}
