package com.qianyi.dailynews.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.paradoxie.autoscrolltextview.VerticalTextview;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiAccount;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiInvite;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.ErWeiMaDialog;
import com.qianyi.dailynews.dialog.SelfDialog;
import com.qianyi.dailynews.fragment.bean.BannerImgInfo;
import com.qianyi.dailynews.fragment.bean.InviteBean;
import com.qianyi.dailynews.ui.Mine.activity.WriteInvitationActivity;
import com.qianyi.dailynews.ui.WebviewActivity;
import com.qianyi.dailynews.ui.account.activity.LoginActivity;
import com.qianyi.dailynews.ui.invitation.activity.ApprenticeActivity;
import com.qianyi.dailynews.ui.invitation.activity.DailySharingAcitity;
import com.qianyi.dailynews.ui.invitation.activity.IncomeShowActivity;
import com.qianyi.dailynews.ui.invitation.activity.InviteRuleActivity;
import com.qianyi.dailynews.ui.invitation.activity.WakeFriendsActivity;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.Utils;
import com.qianyi.dailynews.utils.WhiteBgBitmapUtil;
import com.qianyi.dailynews.utils.loader.GlideImageLoader;
import com.qianyi.dailynews.views.ClearEditText;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
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
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.qianyi.dailynews.utils.SPUtils.get;

/**
 * Created by Administrator on 2018/4/30.
 */

public class InvitationFragment extends BaseFragment implements View.OnClickListener {
    private View newsView;
    @BindView(R.id.tv_title)
    public TextView title;
    @BindView(R.id.iv_back)
    public ImageView back;
    @BindView(R.id.tv_right)
    public TextView rightTv;
    @BindView(R.id.Invitationbanner)
    public Banner banner;
    private List<String> images;

    @BindView(R.id.ll_FriendIncome)
    public LinearLayout ll_FriendIncome;
    @BindView(R.id.ll_FriendNum)
    public LinearLayout ll_FriendNum;
    @BindView(R.id.ll_MyInvitationCode)
    public LinearLayout ll_MyInvitationCode;
    @BindView(R.id.ll_DailySharing)
    public LinearLayout ll_DailySharing;
    @BindView(R.id.ll_ShowIncome)
    public LinearLayout ll_ShowIncome;
    @BindView(R.id.ll_WakeUpFriends)
    public LinearLayout ll_WakeUpFriends;
    @BindView(R.id.tv_myCode)
    public TextView tv_myCode;
    @BindView(R.id.tv_income)
    public TextView tv_income;
    @BindView(R.id.tv_friendCount)
    public TextView tv_friendCount;
    private PopupWindow pw_share;
    private PopupWindow pw_onekeyshoutu;
    private View  view_onekeyshoutu;
    @BindView(R.id.ll_invitation)
    public LinearLayout ll_invitation;
    //上下滚动
    @BindView(R.id.autotext)
    public VerticalTextview autotext;
    @BindView(R.id.btn_onekey_shoutu)
    public Button btn_onekey_shoutu;
    @BindView(R.id.ll_invation)
    public LinearLayout ll_invation;
    @BindView(R.id.et_invation)
    public ClearEditText et_invation;


   // private CustomLoadingDialog customLoadingDialog;
    private String userId;
    private LinearLayout ll_friendCircle, ll_qq, ll_wechat, ll_weibo,ll_copyLianjie,ll_shouTu;
    private IWXAPI mWxApi;
    private List<String> charBannerArray;
    private List<BannerImgInfo> imgBannerArray;
    private WbShareHandler shareHandler;
    private MyReceiver myReceiver;
    private TextView tv_cancle;
    private static final String APP_ID = "101488066"; //获取的APPID
    private Tencent mTencent;
    private ErWeiMaDialog erWeiMaDialog;
    private  String codeOfFriend;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        newsView = inflater.inflate(R.layout.fragment_invitation, null);
        return newsView;
    }
    @Override
    protected void initViews() {
        mTencent = Tencent.createInstance(APP_ID, getActivity().getApplicationContext());
        WbSdk.install(getActivity(), new AuthInfo(getActivity(), ApiConstant.APP_KEY_WEIBO, ApiConstant.REDIRECT_URL, ApiConstant.SCOPE));
        charBannerArray = new ArrayList<>();
        imgBannerArray=new ArrayList<>();
        view_onekeyshoutu = LayoutInflater.from(getActivity()).inflate(R.layout.pw_onekeyshoutu, null);
        ll_friendCircle = view_onekeyshoutu.findViewById(R.id.ll_friendCircle);
        ll_qq = view_onekeyshoutu.findViewById(R.id.ll_shareQQ);
        ll_wechat = view_onekeyshoutu.findViewById(R.id.ll_wechat);
        ll_weibo = view_onekeyshoutu.findViewById(R.id.ll_weibo);
        ll_copyLianjie = view_onekeyshoutu.findViewById(R.id.ll_copyLianjie);
        ll_shouTu = view_onekeyshoutu.findViewById(R.id.ll_shouTu);
        tv_cancle = view_onekeyshoutu.findViewById(R.id.tv_cancle);
        ll_friendCircle.setOnClickListener(this);
        ll_qq.setOnClickListener(this);
        ll_wechat.setOnClickListener(this);
        ll_weibo.setOnClickListener(this);
        ll_copyLianjie.setOnClickListener(this);
        ll_shouTu.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);



        title.setText("邀请");
        back.setVisibility(View.GONE);
        rightTv.setText("邀请规则");
        rightTv.setVisibility(View.VISIBLE);
      //  customLoadingDialog = new CustomLoadingDialog(getActivity());
        //****是否展示填写邀请码**********
        String invite_code = (String) get(getActivity(), "invite_code", "----");
        if (!TextUtils.isEmpty(invite_code)) {
            //有邀请码，隐藏输入框
            ll_invation.setVisibility(View.GONE);
        }
        mWxApi = WXAPIFactory.createWXAPI(getActivity(), ApiConstant.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(ApiConstant.APP_ID);

        ///填写邀请码
        et_invation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_SEARCH == actionId) {
                     codeOfFriend = et_invation.getText().toString().trim();
                    if (!TextUtils.isEmpty(codeOfFriend)) {
                        writeInvatiCode();
                    }
                }
                return false;
            }
        });
        myReceiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.action.login.success");
        getActivity().registerReceiver(myReceiver,intentFilter);

        IntentFilter intentFilterQuit=new IntentFilter();
        intentFilterQuit.addAction("com.action.quit");
        getActivity().registerReceiver(myReceiver,intentFilterQuit);


        erWeiMaDialog=new ErWeiMaDialog(getActivity());

    }
    /****
     * 填写好友邀请码
     */
    private void writeInvatiCode() {
        String userid = (String) get(getActivity(), "user_id", "");
        if (TextUtils.isEmpty(userid)) {
            return;
        }
        ApiInvite.writeCode(ApiConstant.WRITE_CODE, userid, codeOfFriend, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                Log.i("sss", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    if ("0000".equals(code)) {
                        ll_invation.setVisibility(View.GONE);
                        //更新用户信息
                        updateUserInfo();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("sss", e.getMessage());
            }
        });
    }

    /***
     * 更新用户信息
     */
    private void updateUserInfo() {
        String userid = (String) get(getActivity(), "user_id", "");
        if (TextUtils.isEmpty(userid)) {
            return;
        }

        ApiAccount.getUserInfo(ApiConstant.GET_USER_INFO, userid, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                Log.i("sss", s);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String code = jsonObject.getString("code");
                            if("0000".equals(code)){
                                String return_msg = jsonObject.getString("return_msg");
                                JSONObject data = jsonObject.getJSONObject("data");
                                String user_id = data.getString("user_id");
                                String phone = data.getString("phone");
                                String head_portrait = data.getString("head_portrait");
                                String gold = data.getString("gold");
                                String my_invite_code = data.getString("my_invite_code");
                                String balance = data.getString("balance");
                                String earnings = data.getString("earnings");
                                String invite_code = data.getString("invite_code");
                                SPUtils.put(getActivity(), "user_id", user_id);
                                SPUtils.put(getActivity(), "phone", phone);
                                SPUtils.put(getActivity(), "head_portrait", head_portrait);
                                SPUtils.put(getActivity(), "gold", gold);
                                SPUtils.put(getActivity(), "my_invite_code", my_invite_code);
                                SPUtils.put(getActivity(), "balance", balance);
                                SPUtils.put(getActivity(), "earnings", earnings);
                                SPUtils.put(getActivity(), "invite_code", invite_code);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("sss", e.getMessage());
            }
        });

    }
    //开始滚动
    @Override
    public void onResume() {
        super.onResume();
        getInviteData();
        String invite_code= (String) get(getActivity(),"invite_code","");
        if (TextUtils.isEmpty(invite_code)){
            ll_invation.setVisibility(View.VISIBLE);
        }else{
            ll_invation.setVisibility(View.GONE);
        }
    }
    //停止滚动
    @Override
    public void onPause() {
        super.onPause();
        if (autotext!=null){
            autotext.stopAutoScroll();
        }
    }


    @Override
    protected void initData() {
        getData();
        getInviteData();
    }

    //获取邀请数据的值
    private void getInviteData() {
        userId = (String) get(getActivity(), "user_id", "");
        ApiInvite.inviteDetail(ApiConstant.INVITE_DETAIL, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String income = data.getString("income");
                    String inviteCount = data.getString("inviteCount");
                    String myInviteCode = data.getString("myInviteCode");
                    tv_income.setText(income + "金币");
                    tv_friendCount.setText(inviteCount + "个");
                    tv_myCode.setText(myInviteCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }

    //获取数据
    private void getData() {
       // customLoadingDialog.show();
        ApiInvite.getBanner(ApiConstant.INVITE, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        InviteBean inviteBean = gson.fromJson(s, InviteBean.class);
                        String code = inviteBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS_CODE)) {
                            imgBannerArray= inviteBean.getData().getImgBannerArray();
                            charBannerArray = inviteBean.getData().getCharBannerArray();
                            setValue(imgBannerArray, charBannerArray);
                        }
                    }
                });
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
            Log.i("sss" ,e.getMessage());
            }
        });
    }

    //赋值
    private void setValue(List<BannerImgInfo> imgBannerArray, List<String> charBannerArray) {
        images = new ArrayList<>();
        for (int i = 0; i < imgBannerArray.size(); i++) {
            images.add(imgBannerArray.get(i).getImgUrl());
        }
        //设置banner
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.RIGHT);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);//设置图片源
        //banner.setBannerTitles(titles);//设置标题源
        banner.start();
        //设置上下跑马灯
        ArrayList<String> titleList = new ArrayList<>();
        for (int i = 0; i < charBannerArray.size(); i++) {
            titleList.add(charBannerArray.get(i));
        }
        autotext.setTextList(titleList);//加入显示内容,集合类型
        autotext.setText(14, 5, Color.BLACK);//设置属性,具体跟踪源码
        autotext.setTextStillTime(6000);//设置停留时长间隔
        autotext.setAnimTime(300);//设置进入和退出的时间间隔`
        autotext.setPadding(5, 5, 0, 5);
        autotext.startAutoScroll();
    }
    @Override
    protected void initListener() {
        //banner点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String bannerUrl=imgBannerArray.get(position).getUrl();
                if (!TextUtils.isEmpty(bannerUrl)){
                    Intent intent = new Intent(getActivity(), WebviewActivity.class);
                    intent.putExtra("url", imgBannerArray.get(position).getUrl());
                    intent.putExtra("title", "详情");
                    startActivity(intent);
                }
            }
        });
        //对单条文字的点击监听
        autotext.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }
    @OnClick({R.id.tv_right, R.id.ll_FriendIncome, R.id.ll_FriendNum, R.id.ll_MyInvitationCode,
            R.id.ll_DailySharing, R.id.ll_ShowIncome, R.id.ll_WakeUpFriends, R.id.btn_onekey_shoutu,
            R.id.et_invation
    })
    @Override
    public void onClick(View v) {
        String userId = (String) get(getActivity(), "user_id", "");
        switch (v.getId()) {
            case R.id.btn_onekey_shoutu:
                //一键收徒
                showOneKeyShouTu();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(getActivity(), InviteRuleActivity.class);
                intent.putExtra("title", "邀请规则");
                intent.putExtra("url", "http://www.baidu.com");
                startActivity(intent);
                break;
            case R.id.ll_FriendIncome:
                if (TextUtils.isEmpty(userId)){
                    showLogin();
                    return;
                }
                //好友收入
                Intent intent1 = new Intent(getActivity(), ApprenticeActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_FriendNum:
                if (TextUtils.isEmpty(userId)){
                    showLogin();
                    return;
                }
                //好友数量
                Intent intent2 = new Intent(getActivity(), ApprenticeActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_MyInvitationCode:
                if (TextUtils.isEmpty(userId)){
                    showLogin();
                    return;
                }
                //我的邀请码 赋值到剪贴板
                String myCode = tv_myCode.getText().toString().trim();
                try {
                    Utils.copy(myCode, getActivity());
                    Toast.makeText(mActivity, "已复制", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Logger.i(e.getMessage());
                }
                break;
            case R.id.ll_DailySharing:
                if (TextUtils.isEmpty(userId)){
                    showLogin();
                    return;
                }
                //每日分享
                Intent intent_dailyshare = new Intent(getActivity(), DailySharingAcitity.class);
                startActivity(intent_dailyshare);
                break;
            case R.id.ll_ShowIncome:
                if (TextUtils.isEmpty(userId)){
                    showLogin();
                    return;
                }
                //晒收入
                Intent intent3=new Intent(getActivity(), IncomeShowActivity.class);
                intent3.putExtra("tag","invite");
                startActivity(intent3);
                break;
            case R.id.ll_WakeUpFriends:
                if (TextUtils.isEmpty(userId)){
                    showLogin();
                    return;
                }
                //唤醒好友
                Intent intent_wakefriend = new Intent(getActivity(), WakeFriendsActivity.class);
                startActivity(intent_wakefriend);
                break;
            case R.id.ll_friendCircle:
                ApiConstant.SHARE_TAG="invitationShare";
                shareFriendCircle();
                pw_onekeyshoutu.dismiss();
                break;
            case R.id.ll_shareQQ:
                shareQQ();
                pw_onekeyshoutu.dismiss();
                break;
            case R.id.ll_weibo:
                shareWeiBo();
                pw_onekeyshoutu.dismiss();
                break;
            case R.id.ll_wechat:
                ApiConstant.SHARE_TAG="invitationShare";
                shareFriends();
                pw_onekeyshoutu.dismiss();
                break;
            case R.id.ll_shouTu:
                erWeiMaDialog.show();
                pw_onekeyshoutu.dismiss();
                break;
            case R.id.tv_cancle:
                pw_onekeyshoutu.dismiss();
                break;
            case R.id.ll_copyLianjie:
                pw_onekeyshoutu.dismiss();
                try {
                    Utils.copy(ApiConstant.LIANJIE_URL, getActivity());
                    Toast.makeText(mActivity, "已复制", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Logger.i(e.getMessage());
                }
                break;
            case R.id.et_invation:
                userId= (String) get(getActivity(),"user_id","");
                if (TextUtils.isEmpty(userId)){
                    showLogin();
                    return;
                }
                intent=new Intent(getActivity(), WriteInvitationActivity.class);
                startActivity(intent);
                break;
        }
    }
    //QQ分享
    private void shareQQ() {
        String my_invite_code= (String) SPUtils.get(getActivity(),"my_invite_code","");
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "每日速报");//分享标题
        //params.putString(QQShare.SHARE_TO_QQ_SUMMARY,"看资讯送零花，立马可提现，你也快来领吧！填我邀请码"+my_invite_code);//要分享的内容摘要
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,"我最近再玩【每日速报】APP, 读资讯就能赚钱，内容丰富又搞笑，轻轻松松赚零花,当日提现，立马到账，一起来试试吧");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,ApiConstant.DOWN_SHARE_URL);//内容地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,ApiConstant.QQ_SHARE_LOGO);//分享的图片URL
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "每日速报");//应用名称
        mTencent.shareToQQ(getActivity(), params, new ShareUiListener());
    }
    /**
     * 自定义监听器实现IUiListener，需要3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class ShareUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            //分享成功
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
    private void showLogin() {
        final SelfDialog quitDialog = new SelfDialog(getActivity());
        quitDialog.setTitle("提示");
        quitDialog.setMessage("请先登录");
        quitDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                quitDialog.dismiss();
            }
        });
        quitDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                quitDialog.dismiss();
            }
        });
        quitDialog.show();
    }

    //分享到微博
    private void shareWeiBo() {
        initLog();
        //startActivity(new Intent(getActivity(), WBAuthActivity.class));
        shareHandler = new WbShareHandler(getActivity());
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
        String my_invite_code = (String) get(getActivity(), "my_invite_code", "");
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = "每日速报";
        mediaObject.description = "看资讯送零花，立马可提现，你也快来领吧！填我邀请码"+my_invite_code;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(getActivity(), bmp);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        mediaObject.setThumbImage(thumbBmp);
        mediaObject.actionUrl = ApiConstant.DOWN_SHARE_URL;
        mediaObject.defaultText = "看资讯送零花，立马可提现，你也快来领吧！填我邀请码"+my_invite_code;
        WeiboMultiMessage message = new WeiboMultiMessage();
        message.mediaObject = mediaObject;
        shareHandler.shareMessage(message, false);
    }
    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    private void sendMessage(boolean hasText, boolean hasImage) {
        sendMultiMessage(hasText, hasImage);
    }
    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    private void sendMultiMessage(boolean hasText, boolean hasImage) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj();
        }
        if (hasImage) {
            weiboMessage.imageObject = getImageObj();
        }
        shareHandler.shareMessage(weiboMessage, false);
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = getSharedText();
        textObject.title = "xxxx";
        textObject.actionUrl = "http://www.baidu.com";
        return textObject;
    }

    /**
     * 获取分享的文本模板。
     */
    private String getSharedText() {
        int formatId = R.string.weibosdk_demo_share_text_template;
        String format = getString(formatId);
        String text = format;
      /*  if (mTextCheckbox.isChecked() || mImageCheckbox.isChecked()) {
            text = "@大屁老师，这是一个很漂亮的小狗，朕甚是喜欢-_-!! #大屁老师#http://weibo.com/p/1005052052202067/home?from=page_100505&mod=TAB&is_all=1#place";
        }*/
        return text;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        imageObject.setImageObject(bitmap);
        return imageObject;
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
        mediaObject.actionUrl = "http://47.104.73.127:8080/download/download.html";
        mediaObject.defaultText = "每日速报是一款基于数据挖掘的推荐引擎产品，它为用户推荐有价值的、个性化的信息，提供连接人与信息的新型服务。";
        return mediaObject;
    }
    //分享到微信
    private void shareFriends() {
        String my_invite_code= (String) SPUtils.get(getActivity(),"my_invite_code","");
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ApiConstant.DOWN_SHARE_URL;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "每日速报";
        msg.description = "看资讯送零花，立马可提现，你也快来领吧！填我邀请码"+my_invite_code;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(getActivity(), bmp);
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
    private void shareFriendCircle() {
        String my_invite_code= (String) SPUtils.get(getActivity(),"my_invite_code","");
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ApiConstant.DOWN_SHARE_URL;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "我最近在玩这个app,读资讯，就能赚钱，内容丰富又搞笑，轻松赚零花，提现秒速到账，一起来试试吧！";
        msg.description = "看资讯送零花，立马可提现，你也快来领吧！填我邀请码"+my_invite_code;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap bitmap = WhiteBgBitmapUtil.drawableBitmapOnWhiteBg(getActivity(), bmp);
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
    /***
     * 弹出一键收徒的弹窗
     */
    private void showOneKeyShouTu() {
        pw_onekeyshoutu = new PopupWindow(getActivity());
        pw_onekeyshoutu.setContentView(view_onekeyshoutu);
        pw_onekeyshoutu.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pw_onekeyshoutu.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pw_onekeyshoutu.setTouchable(true);
        pw_onekeyshoutu.setFocusable(true);
        pw_onekeyshoutu.setBackgroundDrawable(new BitmapDrawable());
        pw_onekeyshoutu.setAnimationStyle(R.style.AnimBottom);
        pw_onekeyshoutu.showAtLocation(ll_invitation, Gravity.BOTTOM, 0, 0);
        // 设置pw弹出时候的背景颜色的变化
        backgroundAlpha(0.5f);
        pw_onekeyshoutu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    private void shwoSharePw() {
     /*   pw_share = new PopupWindow(getActivity());
        pw_share.setContentView(view_share);
        pw_share.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pw_share.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pw_share.setTouchable(true);
        pw_share.setFocusable(true);
        pw_share.setBackgroundDrawable(new BitmapDrawable());
        pw_share.setAnimationStyle(R.style.AnimBottom);
        pw_share.showAtLocation(ll_invitation, Gravity.BOTTOM, 0, 0);
        // 设置pw弹出时候的背景颜色的变化
        backgroundAlpha(0.5f);
        pw_share.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });*/
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.action.login.success")){
                getInviteData();
            }else if(action.equals("com.action.quit")){
                tv_income.setText( "0金币");
                tv_friendCount.setText( "0个");
                tv_myCode.setText("");
                ll_invation.setVisibility(View.VISIBLE);
            }
        }
    }
}
