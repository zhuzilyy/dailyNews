package com.qianyi.dailynews.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.qianyi.dailynews.MainActivity;
import com.qianyi.dailynews.utils.ListActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2018/1/25.
 */

public abstract class BaseActivity extends FragmentActivity {
    public static List<Activity> activities=new ArrayList<>();
    Unbinder unbinder;
    private boolean isExit=false;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initConfigure();
    }
    private void initConfigure() {
        //加载布局
        getResLayout();
        unbinder= ButterKnife.bind(this);
        ListActivity.list.add(this);
        //初始化控件
        initViews();
        //初始化数据
        initData();
        initListener();
        //设置状态栏的颜色
        setStatusBarColor();
    }
    public void setStatusColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
        }
    }
    private void setTranslucentStatus() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        final int status = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        params.flags |= status;
        window.setAttributes(params);
    }
    //跳转的方法
    public void jumpActivity(Context context,Class<?> targetActivity){
        Intent intent=new Intent(context,targetActivity);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ListActivity.list.remove(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
            return true;
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private void exitBy2Click() {
        Timer tExit = null;
        if (getClass().getName().equals(MainActivity.class.getName())){
            if (isExit == false) {
                isExit = true; // 准备退出
                Toast.makeText(this, "再按一次，退出程序", Toast.LENGTH_SHORT).show();
                tExit = new Timer();
                tExit.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false; // 取消退出
                    }
                },2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            } else {
               // moveTaskToBack(true);
                finish();
            }
        }else {
            finish();
        }
    }
    /**
     * 将activity添加进集合中
     * @param activity
     */
    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    /***
     * 从集合中移除activity
     */
    public static void removeActivity() {
        for (int i = 0; i < activities.size(); i++) {

            if (!activities.get(i).isFinishing()) {
                activities.get(i).finish();
            }

        }
    }




    protected abstract void initViews();
    protected abstract void initData();
    protected abstract void getResLayout();
    protected abstract void initListener();
    protected abstract void setStatusBarColor();
}
