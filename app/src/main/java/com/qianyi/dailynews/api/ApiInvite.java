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
    /**
     * 邀请详情
     * @param callback
     */
    public static void inviteDetail(String url,String userId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 召回好友列表
     * @param callback
     */
    public static void callBackFriendList(String url,String userId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 徒弟列表
     * @param callback
     */
    public static void tudiList(String url,String userId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }





























































    /**
     * 填写好友邀请码
     * @param callback
     */
    public static void writeCode(String url,String userId,String inviteCode,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("inviteCode",inviteCode);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
}
