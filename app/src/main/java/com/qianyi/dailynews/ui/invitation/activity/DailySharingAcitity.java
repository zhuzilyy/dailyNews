package com.qianyi.dailynews.ui.invitation.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
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

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiInvite;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.ui.Mine.activity.TaskCenterActivity;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.WhiteBgBitmapUtil;
import com.qianyi.dailynews.wxapi.WXEntryActivity;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.statistic.WBAgent;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

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

public class DailySharingAcitity extends BaseActivity implements View.OnClickListener, WbShareCallback {
    @BindView(R.id.tv_title)
    public TextView title;
    @BindView(R.id.tv_time)
    public TextView tv_time;
//    @BindView(R.id.tv_intervel)
//    public TextView tv_intervel;
    @BindView(R.id.iv_back)
    public ImageView back;
    @BindView(R.id.ll_share)
    public LinearLayout ll_share;
    @BindView(R.id.btn_share)
    Button btn_share;
    @BindView(R.id.iv_share1)
    ImageView iv_share1;
    @BindView(R.id.iv_share2)
    ImageView iv_share2;
    @BindView(R.id.iv_share3)
    ImageView iv_share3;
    @BindView(R.id.iv_share4)
    ImageView iv_share4;
    @BindView(R.id.iv_share5)
    ImageView iv_share5;
    private CustomLoadingDialog customLoadingDialog;
    private String userId;
    private PopupWindow pw_share;
    private View view_share;
    public LinearLayout ll_friendCircle;//分享到盆友圈
    public LinearLayout ll_QQ;//分享到QQ
    public LinearLayout ll_wechat;//分享到微信
    public LinearLayout ll_weibo;//分享到微博
    private IWXAPI mWxApi;
    private WbShareHandler shareHandler;
    private MyReceiver myReceiver;
    private static final String APP_ID = "101488066"; //获取的APPID
    private Tencent mTencent;
    private String count;
    private  CountDownTimer timer;
    @BindView(R.id.tvtime1) public TextView tvtime1;
    @BindView(R.id.tvtime2) public TextView tvtime2;
    @BindView(R.id.tvtime3) public TextView tvtime3;
    @BindView(R.id.ll_time) public LinearLayout ll_time;
    private Handler handler= new Handler();
    private int time;
    private List<ImageView> shareImageList;
    @Override
    protected void initViews() {
        shareImageList=new ArrayList<>();
        shareImageList.add(iv_share1);
        shareImageList.add(iv_share2);
        shareImageList.add(iv_share3);
        shareImageList.add(iv_share4);
        shareImageList.add(iv_share5);
        mTencent = Tencent.createInstance(APP_ID,getApplicationContext());
        title.setText("每日分享");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        customLoadingDialog=new CustomLoadingDialog(DailySharingAcitity.this);
        userId= (String) SPUtils.get(DailySharingAcitity.this,"user_id","");
        view_share = LayoutInflater.from(DailySharingAcitity.this).inflate(R.layout.pw_share, null);
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

        myReceiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.action.share.success");
        registerReceiver(myReceiver,intentFilter);

    }

    @Override
    protected void initData() {
        getData();
    }
    private void getData() {
        customLoadingDialog.show();
        ApiInvite.sharePre(ApiConstant.SHARE_PRE, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String lastTime = data.getString("lastTime");
                    count = data.getString("count");
                    tv_time.setText(count+"次");
                   // startTime(tv_intervel,lastTime);
                    time=Integer.parseInt(lastTime);
                    if(time>0){
                        ll_time.setVisibility(View.VISIBLE);
                        handler.postDelayed(runnable, 1000);
                        btn_share.setEnabled(false);
                        btn_share.setTextColor(Color.parseColor("#999999"));
                    }else {
                        btn_share.setEnabled(true);
                        btn_share.setTextColor(Color.parseColor("#ffffff"));
                    }
                    //设置分享的图片的颜色
                    int intCount=Integer.parseInt(count);
                    for (int i = 0; i <5-intCount ; i++) {
                        shareImageList.get(i).setImageResource(R.mipmap.already_share_icon_);
                    }
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


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time--;
            String formatLongToTimeStr = formatLongToTimeStr((long) time);
            String[] split = formatLongToTimeStr.split("：");
            for (int i = 0; i < split.length; i++) {
                if(i==0){
                    if (!TextUtils.isEmpty(split[0])){
                        tvtime1.setText(split[0]+"小时");
                    }
                }
                if(i==1){
                    if (!TextUtils.isEmpty(split[2])){
                        tvtime2.setText(split[1]+"分钟");
                    }
                }
                if(i==2){
                    if (!TextUtils.isEmpty(split[1])){
                        tvtime3.setText(split[2]+"秒");
                    }

                }
            }
            if(time>0){
                handler.postDelayed(this, 1000);
            }
        }
    };

    public  String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue() ;
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = hour+"："+minute+"："+second;
        return strtime;

    }




    /***
     * 进行倒计时
     * @param tv_intervel
     * @param lastTime
     */
    private void startTime(final TextView tv_intervel, String lastTime) {

        if(!TextUtils.isEmpty(lastTime)){
            int time2 = Integer.parseInt(lastTime);

            if(time2>0){
                btn_share.setEnabled(false);
                btn_share.setTextColor(Color.parseColor("#999999"));
            }

            if(time2>0){
                /** 倒计时一次1秒 */
                timer = new CountDownTimer(time2*1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        if(tv_intervel!=null){
                            tv_intervel.setText("还剩"+(millisUntilFinished/1000)+"秒");
                        }
                    }
                    @Override
                    public void onFinish() {
                        tv_intervel.setText("还剩0秒");
                        btn_share.setEnabled(true);
                        btn_share.setTextColor(Color.parseColor("#ffffff"));
                    }
                }.start();
            }
        }
    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_dailyshare);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.btn_share})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_share:
                if (count.equals("0")){
                    Toast.makeText(this, "今日分享次数已经用尽", Toast.LENGTH_SHORT).show();
                    return;
                }
                shwoSharePw();
                break;
            case R.id.ll_wechat:
                //微信分享
                //Toast.makeText(this, "微信分享", Toast.LENGTH_SHORT).show();
                ApiConstant.SHARE_TAG="dailyShare";
                shareFriends();
                pw_share.dismiss();
                break;
            case R.id.ll_friendCircle:
                ApiConstant.SHARE_TAG="dailyShare";
                shareFriendCircle();
                pw_share.dismiss();
                //朋友圈分享
                //Toast.makeText(this, "朋友圈分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_QQ:
                shareQQ();
                pw_share.dismiss();
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
    //QQ分享
    private void shareQQ() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "每日速报");//分享标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,"每日分享的分享");//要分享的内容摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,ApiConstant.DAILY_SHARE_URL);//内容地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,ApiConstant.QQ_SHARE_LOGO);//分享的图片URL
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "每日速报");//应用名称
        mTencent.shareToQQ(this, params, new ShareUiListener());
    }
    /**
     * 自定义监听器实现IUiListener，需要3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class ShareUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            //分享成功
            shareSuccess();
        }
        @Override
        public void onError(UiError uiError) {
            //分享失败

        }
        @Override
        public void onCancel() {
            //分享取消

        }
    }

    private void shareFriendCircle() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ApiConstant.DAILY_SHARE_URL;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "每日速报";
        msg.description = "每日分享的分享";
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
        String my_invite_code = (String) SPUtils.get(DailySharingAcitity.this, "my_invite_code", "");
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
        mediaObject.description = "每日分享的分享";
       /* Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);

        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);*/
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(this, bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        mediaObject.setThumbImage(thumbBmp);
        mediaObject.actionUrl = ApiConstant.DAILY_SHARE_URL;
        mediaObject.defaultText = "陪我一起阅读，每天做不一样的自己，阅读拆红包，每天都有不一样的惊喜，阅读邀请码"+my_invite_code;
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

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(this, bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        mediaObject.setThumbImage(thumbBmp);
       /* msg.setThumbImage(thumbBmp);
        bmp.recycle();*/

      /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);

        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);*/



        mediaObject.actionUrl = "http://news.sina.com.cn/c/2013-10-22/021928494669.shtml";
        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }

    //分享到微信
    private void shareFriends() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ApiConstant.DAILY_SHARE_URL;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "每日速报";
        msg.description = "每日分享的分享";
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(this, bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        msg.setThumbImage(thumbBmp);
        bmp.recycle();

     /*   Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.mipmap.logo);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
        msg.setThumbImage(thumbBmp);
        bmp.recycle();*/


        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        // req.scene = sendtype==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        mWxApi.sendReq(req);
    }

    private void shwoSharePw() {
        pw_share = new PopupWindow(DailySharingAcitity.this);
        pw_share.setContentView(view_share);
        pw_share.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pw_share.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pw_share.setTouchable(true);
        pw_share.setFocusable(true);
        pw_share.setBackgroundDrawable(new BitmapDrawable());
        pw_share.setAnimationStyle(R.style.AnimBottom);
        pw_share.showAtLocation(ll_share, Gravity.BOTTOM, 0, 0);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        if (myReceiver!=null){
            unregisterReceiver(myReceiver);
        }
        if(timer!=null){
            timer.cancel();
        }


    }
    //分享的回调
    @Override
    public void onWbShareSuccess() {
        shareSuccess();
    }
    @Override
    public void onWbShareCancel() {
    }
    @Override
    public void onWbShareFail() {

    }
    //分享成功
    private void shareSuccess() {
        ApiInvite.shareAfter(ApiConstant.SHARE_AFTER, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String code = jsonObject.getString("code");
                    if (code.equals(ApiConstant.SUCCESS_CODE)){
                        finish();
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

    class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.action.share.success")){
                getData();
            }
        }
    }
}
