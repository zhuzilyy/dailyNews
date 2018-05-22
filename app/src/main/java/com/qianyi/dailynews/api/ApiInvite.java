package com.qianyi.dailynews.api;

import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.utils.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/22.
 */

public class ApiInvite {
    /**
     * 邀请轮播
     * @param callback
     */
    public static void getBanner(String url,RequestCallBack<String> callback){
        OkHttpManager.getInstance().postRequest(url,null,callback);
    }
}
