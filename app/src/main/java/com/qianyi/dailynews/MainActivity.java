package com.qianyi.dailynews;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.fragment.InvitationFragment;
import com.qianyi.dailynews.fragment.MineFragment;
import com.qianyi.dailynews.fragment.NewsFragment;
import com.qianyi.dailynews.fragment.VideoFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private NewsFragment newsFragment;
    private VideoFragment videoFragment;
    private InvitationFragment invitationFragment;
    private MineFragment mineFragment;
    private FragmentManager fragmentManager;
    private Fragment currentFra=new Fragment();
    @BindView(R.id.bar)
    public BottomNavigationBar bar;



    @Override
    protected void initViews() {
        BaseActivity.addActivity(this);
      //  ButterKnife.bind(this);
        fragmentManager=getSupportFragmentManager();
        newsFragment=new NewsFragment();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        AddOrShowFra(ft,newsFragment);

        bar.setMode(BottomNavigationBar.MODE_FIXED);
        bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bar.addItem(new BottomNavigationItem(getResources().getDrawable(R.mipmap.choosenews_icon),"新闻").setActiveColorResource(R.color.main_red))
                .addItem(new BottomNavigationItem(getResources().getDrawable(R.mipmap.choosevideo_icon),"视频").setActiveColorResource(R.color.main_red))
                .addItem(new BottomNavigationItem(getResources().getDrawable(R.mipmap.redpack_icon),"邀请").setActiveColorResource(R.color.main_red))
                .addItem(new BottomNavigationItem(getResources().getDrawable(R.mipmap.nonemy_icon),"我的").setActiveColorResource(R.color.main_red))
                .setFirstSelectedPosition(0).initialise();
        bar.setTabSelectedListener(this);
        Log.i("location_",sHA1(MainActivity.this));
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
            ft.hide(currentFra).add(R.id.main_switch, Fra).commit();

        } else {
            ft.hide(currentFra).show(Fra).commit();

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
}
