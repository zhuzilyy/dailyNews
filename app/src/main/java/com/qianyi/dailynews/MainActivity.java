package com.qianyi.dailynews;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.fragment.InvitationFragment;
import com.qianyi.dailynews.fragment.MineFragment;
import com.qianyi.dailynews.fragment.NewsFragment;
import com.qianyi.dailynews.fragment.VideoFragment;
import com.qianyi.dailynews.ui.account.activity.RegisterActivity;
import com.qianyi.dailynews.ui.news.activity.NewsDetailsActivity;
import com.qianyi.dailynews.utils.SPUtils;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener,View.OnClickListener {
    private NewsFragment newsFragment;
    private VideoFragment videoFragment;
    private InvitationFragment invitationFragment;
    private MineFragment mineFragment;
    private FragmentManager fragmentManager;
    private Fragment currentFra=new Fragment();
    @BindView(R.id.bar)
    public BottomNavigationBar bar;
    private MyReceiver myReceiver;
    @Override
    protected void initViews() {


        //注册广播邀请
        myReceiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.action.invite");
        registerReceiver(myReceiver,intentFilter);

        //注册广播阅读
        myReceiver=new MyReceiver();
        IntentFilter intentFilterRead=new IntentFilter();
        intentFilterRead.addAction("com.action.read");
        registerReceiver(myReceiver,intentFilterRead);

        //注册广播评论
        myReceiver=new MyReceiver();
        IntentFilter intentFilterComment=new IntentFilter();
        intentFilterComment.addAction("com.action.comment");
        registerReceiver(myReceiver,intentFilterComment);

        BaseActivity.addActivity(this);
      //  ButterKnife.bind(this);
        fragmentManager=getSupportFragmentManager();
        newsFragment=new NewsFragment();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        AddOrShowFra(ft,newsFragment);

        bar.setMode(BottomNavigationBar.MODE_FIXED);
        bar.setInActiveColor("#808080");
        bar.setActiveColor("#ff5645");
/*
        bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bar.addItem(new BottomNavigationItem(getResources().getDrawable(R.mipmap.choosenews_icon),"首页").setActiveColorResource(R.color.main_red))
                .addItem(new BottomNavigationItem(getResources().getDrawable(R.mipmap.choosevideo_icon),"视频").setActiveColorResource(R.color.main_red))
                .addItem(new BottomNavigationItem(getResources().getDrawable(R.mipmap.redpack_icon),"邀请").setActiveColorResource(R.color.main_red))
                .addItem(new BottomNavigationItem(getResources().getDrawable(R.mipmap.nonemy_icon),"我的").setActiveColorResource(R.color.main_red))
                .setFirstSelectedPosition(0).initialise();
*/
        bar.addItem(new BottomNavigationItem(R.mipmap.choosenews_icon,"首页")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this,R.mipmap.news_icon)))
                .addItem(new BottomNavigationItem(R.mipmap.choosevideo_icon,"视频")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this,R.mipmap.video_icon)))
                .addItem(new BottomNavigationItem(R.mipmap.yaoqing_icon,"邀请")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this,R.mipmap.yaoqing_icon)))
                .addItem(new BottomNavigationItem(R.mipmap.my_icon,"我的")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this,R.mipmap.nonemy_icon)))
                .setFirstSelectedPosition(0)//设置默认选择的按钮
                .initialise();//所有的设置需在调用该方法前完成



        bar.setTabSelectedListener(this);
        WbSdk.install(this,new AuthInfo(this, ApiConstant.APP_KEY_WEIBO, ApiConstant.REDIRECT_URL, ApiConstant.SCOPE));
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
    /***
     * 显示隐藏Fragment
     *
     * @param ft
     * @param Fra
     */
    private void AddOrShowFra(FragmentTransaction ft, Fragment Fra) {
        if (currentFra == Fra) {
            return;
        }
        if (!Fra.isAdded()) {
            ft.hide(currentFra).add(R.id.main_switch, Fra).commitAllowingStateLoss();

        } else {
            ft.hide(currentFra).show(Fra).commitAllowingStateLoss();

        }
        currentFra = Fra;


    }


    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onTabSelected(int position) {
        switch(position ){
            case 0:
                if(newsFragment==null){
                    newsFragment=new NewsFragment();
                }
                FragmentTransaction ft_news=fragmentManager.beginTransaction();
                AddOrShowFra(ft_news,newsFragment);
                break;
            case 1:
                if(videoFragment==null){
                    videoFragment=new VideoFragment();
                }
                FragmentTransaction ft_video=fragmentManager.beginTransaction();
                AddOrShowFra(ft_video,videoFragment);
                break;
            case 2:
                if(invitationFragment==null){
                    invitationFragment=new InvitationFragment();
                }
                FragmentTransaction ft_invitation=fragmentManager.beginTransaction();
                AddOrShowFra(ft_invitation,invitationFragment);
                break;
            case 3:
                if(mineFragment==null){
                    mineFragment=new MineFragment();
                }
                FragmentTransaction ft_mine=fragmentManager.beginTransaction();
                AddOrShowFra(ft_mine,mineFragment);
                break;

            default:
                break;


        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){

                 }
    }
    class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.action.invite")){
                if(invitationFragment==null){
                    invitationFragment=new InvitationFragment();
                }
                bar.setFirstSelectedPosition(2).initialise();
                FragmentTransaction ft_invitation=fragmentManager.beginTransaction();
                AddOrShowFra(ft_invitation,invitationFragment);
            }else if(action.equals("com.action.read")||action.equals("com.action.comment")){
                if(newsFragment==null){
                    newsFragment=new NewsFragment();
                }
                bar.setFirstSelectedPosition(0).initialise();
                FragmentTransaction ft_news=fragmentManager.beginTransaction();
                AddOrShowFra(ft_news,newsFragment);
            }
        }
    }

}
