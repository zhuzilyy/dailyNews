package com.qianyi.dailynews.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.paradoxie.autoscrolltextview.VerticalTextview;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiInvite;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.fragment.bean.BannerImgInfo;
import com.qianyi.dailynews.fragment.bean.InviteBean;
import com.qianyi.dailynews.ui.WebviewActivity;
import com.qianyi.dailynews.ui.invitation.activity.ApprenticeActivity;
import com.qianyi.dailynews.ui.invitation.activity.DailySharingAcitity;
import com.qianyi.dailynews.ui.invitation.activity.WakeFriendsActivity;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.ToastUtils;
import com.qianyi.dailynews.utils.Utils;
import com.qianyi.dailynews.utils.loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/30.
 */

public class InvitationFragment extends BaseFragment implements View.OnClickListener{
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
    private View view_share,view_onekeyshoutu;
    @BindView(R.id.ll_invitation)
    public LinearLayout ll_invitation;
    //上下滚动
    @BindView(R.id.autotext)
    public VerticalTextview autotext;
    @BindView(R.id.btn_onekey_shoutu)
    public Button btn_onekey_shoutu;
    private CustomLoadingDialog customLoadingDialog;
    private String userId;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        newsView =  inflater.inflate(R.layout.fragment_invitation, null);
        return newsView;
    }

    @Override
    protected void initViews() {
        view_share=LayoutInflater.from(getActivity()).inflate(R.layout.pw_share,null);
        view_onekeyshoutu = LayoutInflater.from(getActivity()).inflate(R.layout.pw_onekeyshoutu,null);
        title.setText("邀请");
        back.setVisibility(View.GONE);
        rightTv.setText("邀请规则");
        rightTv.setVisibility(View.VISIBLE);
        customLoadingDialog=new CustomLoadingDialog(getActivity());
        userId= (String) SPUtils.get(getActivity(),"user_id","");

    }
    //开始滚动
    @Override
    public void onResume() {
        super.onResume();
    }
    //停止滚动
    @Override
    public void onPause() {
        super.onPause();
        autotext.stopAutoScroll();
    }
    @Override
    protected void initData() {
        getData();
        getInviteData();
    }
    //获取邀请数据的值
    private void getInviteData() {
        ApiInvite.inviteDetail(ApiConstant.INVITE_DETAIL, userId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String income = data.getString("income");
                    String inviteCount = data.getString("inviteCount");
                    String myInviteCode = data.getString("myInviteCode");
                    tv_income.setText(income+"金币");
                    tv_friendCount.setText(inviteCount+"个");
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
        customLoadingDialog.show();
        ApiInvite.getBanner(ApiConstant.INVITE, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customLoadingDialog.dismiss();
                        Gson gson=new Gson();
                        InviteBean inviteBean = gson.fromJson(s, InviteBean.class);
                        String code = inviteBean.getCode();
                        if (code.equals(ApiConstant.SUCCESS_CODE)){
                            List<BannerImgInfo> imgBannerArray = inviteBean.getData().getImgBannerArray();
                            List<String> charBannerArray = inviteBean.getData().getCharBannerArray();
                            setValue(imgBannerArray,charBannerArray);
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
    //赋值
    private void setValue(List<BannerImgInfo> imgBannerArray, List<String> charBannerArray) {
        images= new ArrayList<>();
        for (int i = 0; i <imgBannerArray.size() ; i++) {
            images.add(imgBannerArray.get(i).getUrl());
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
        ArrayList<String> titleList =new ArrayList<>();
        for (int i = 0; i <charBannerArray.size() ; i++) {
            titleList.add(charBannerArray.get(i));
        }
        autotext.setTextList(titleList);//加入显示内容,集合类型
        autotext.setText(14, 5, Color.BLACK);//设置属性,具体跟踪源码
        autotext.setTextStillTime(6000);//设置停留时长间隔
        autotext.setAnimTime(300);//设置进入和退出的时间间隔`
        autotext.setPadding(5,5,0,5);
        autotext.startAutoScroll();
        //对单条文字的点击监听
        autotext.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }
    @Override
    protected void initListener() {

    }
    @OnClick({R.id.tv_right,R.id.ll_FriendIncome,R.id.ll_FriendNum,R.id.ll_MyInvitationCode,
            R.id.ll_DailySharing,R.id.ll_ShowIncome,R.id.ll_WakeUpFriends,R.id.btn_onekey_shoutu})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_onekey_shoutu:
                //一键收徒
                showOneKeyShouTu();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("title","邀请规则");
                intent.putExtra("url","http://www.baidu.com");
                startActivity(intent);
                break;
            case R.id.ll_FriendIncome:
                //好友收入
                Intent intent1 = new Intent(getActivity(), ApprenticeActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_FriendNum:
                //好友数量
                Intent intent2 = new Intent(getActivity(), ApprenticeActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_MyInvitationCode:
                //我的邀请码 赋值到剪贴板
                String myCode = tv_myCode.getText().toString().trim();
                try {
                    Utils.copy(myCode,getActivity());
                    Toast.makeText(mActivity, "已复制", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Logger.i(e.getMessage());
                }
                break;
            case R.id.ll_DailySharing:
                //每日分享
                Intent intent_dailyshare = new Intent(getActivity(), DailySharingAcitity.class);
                startActivity(intent_dailyshare);
                break;
            case R.id.ll_ShowIncome:
                //晒收入
                shwoSharePw();
                break;
            case R.id.ll_WakeUpFriends:
                //唤醒好友
                Intent intent_wakefriend = new Intent(getActivity(), WakeFriendsActivity.class);
                startActivity(intent_wakefriend);
                break;
            default:
                break;
        }


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
        pw_share = new PopupWindow(getActivity());
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
        });
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
}
