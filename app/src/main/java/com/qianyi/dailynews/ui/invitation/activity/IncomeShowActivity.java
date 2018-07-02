package com.qianyi.dailynews.ui.invitation.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.WhiteBgBitmapUtil;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
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

/**
 * Created by Administrator on 2018/6/12.
 */

public class IncomeShowActivity extends BaseActivity implements View.OnClickListener, WbShareCallback {
    @BindView(R.id.btn_share) public Button btn_share;
    @BindView(R.id.ll_shai) public LinearLayout ll_shai;
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.tv_income) public TextView tv_income;
    @BindView(R.id.iv_erweima) public ImageView iv_erweima;
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
    @Override
    protected void initViews() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("晒收入");

        view_share = LayoutInflater.from(IncomeShowActivity.this).inflate(R.layout.pw_share, null);
        ll_friendCircle=view_share.findViewById(R.id.ll_friendCircle);
        ll_QQ=view_share.findViewById(R.id.ll_QQ);
        ll_wechat=view_share.findViewById(R.id.ll_wechat);
        ll_weibo=view_share.findViewById(R.id.ll_weibo);

        ll_friendCircle.setOnClickListener(this);
        ll_QQ.setOnClickListener(this);
        ll_wechat.setOnClickListener(this);
        ll_weibo.setOnClickListener(this);

        mWxApi = WXAPIFactory.createWXAPI(this, ApiConstant.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(ApiConstant.APP_ID);

        customLoadingDialog=new CustomLoadingDialog(this);
        userId= (String) SPUtils.get(this,"user_id","");


    }
    @Override
    protected void initData(){
        customLoadingDialog.show();
        ApiMine.shareIncome(ApiConstant.ISHARE_INCOME, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String income = data.getString("income");
                    String url = data.getString("url");
                    tv_income.setText(income+"元");
                    Glide.with(IncomeShowActivity.this).load(url).into(iv_erweima);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
            }
        });
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_income_show);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.btn_share})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
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
            default:
                break;
        }
    }
    private void shareFriendCircle() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ApiConstant.INCOME_SHOW+userId;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "每日速报";
        msg.description = "每日速报是一款基于数据挖掘的推荐引擎产品，它为用户推荐有价值的、个性化的信息，提供连接人与信息的新型服务";
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(this, bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        msg.setThumbImage(thumbBmp);
        bmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        // req.scene = sendtype==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        mWxApi.sendReq(req);
    }
    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
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
        mediaObject.title = "每日速报";
        mediaObject.description = "每日速报是一款基于数据挖掘的推荐引擎产品，它为用户推荐有价值的、个性化的信息，提供连接人与信息的新型服务";
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);

        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = ApiConstant.INCOME_SHOW+userId;
        mediaObject.defaultText = "每日速报是一款基于数据挖掘的推荐引擎产品，它为用户推荐有价值的、个性化的信息，提供连接人与信息的新型服务";
        WeiboMultiMessage message = new WeiboMultiMessage();
        message.mediaObject = mediaObject;
        shareHandler.shareMessage(message, false);
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

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = "测试title";
        mediaObject.description = "测试描述";
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);

        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = "http://news.sina.com.cn/c/2013-10-22/021928494669.shtml";
        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }

    //分享到微信
    private void shareFriends() {
        WXWebpageObject webpage = new WXWebpageObject();
        //http://47.104.73.127:8080/share/index.html?id=2c9fd8b3635c7e840163631a72190002
        webpage.webpageUrl = ApiConstant.INCOME_SHOW+userId;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "每日速报";
        msg.description = "每日速报是一款基于数据挖掘的推荐引擎产品，它为用户推荐有价值的、个性化的信息，提供连接人与信息的新型服务";
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

    private void shwoSharePw() {
        pw_share = new PopupWindow(IncomeShowActivity.this);
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
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent,this);
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
    //微博分享以后的回调
    @Override
    public void onWbShareSuccess() {

    }
    @Override
    public void onWbShareCancel() {

    }

    @Override
    public void onWbShareFail() {

    }
}
