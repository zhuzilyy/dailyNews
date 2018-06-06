package com.qianyi.dailynews.application;

import android.app.Application;
import android.content.Context;


import com.qianyi.dailynews.ui.news.bean.NewsTitleBean;

import org.xutils.x;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by NEUNB on 2018/3/19.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;
    public static List<NewsTitleBean.NewsTitleData.NewsTypeRes> newsTypeRes;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        myApplication=this;
        //注册微信
        registToWX();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }
    /**
     * 全程作用域
     * @return
     */
    public static MyApplication getApplication(){
        return myApplication;
    }
    /***
     * 注册微信
     */
    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
      /*  mWxApi = WXAPIFactory.createWXAPI(this, apiConstant.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(apiConstant.APP_ID);*/
    }

}
