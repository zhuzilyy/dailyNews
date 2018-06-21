package com.qianyi.dailynews.api;

import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.utils.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/13.
 */

public class ApiAccount {
    /**
     * 登录
     * @param callback
     */
    public static void login(String url,String userName ,String pwd, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userName",userName);
        params.put("passwd",pwd);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 获取验证码
     * @param callback
     */
    public static void getConfirmCode(String url,String phone,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("phone",phone);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 注册
     * @param callback
     */
    public static void register(String url,String userName,String passwd,String code,String inviteCode,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userName",userName);
        params.put("passwd",passwd);
        params.put("code",code);
        params.put("inviteCode",inviteCode);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 忘记密码发送验证码
     * @param callback
     */
    public static void getForgetPwdConfirmCode(String url,String phone,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("phone",phone);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 修改密码
     * @param callback
     */
    public static void updatePwd(String url,String userName,String newPasswd,String code,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userName",userName);
        params.put("newPasswd",newPasswd);
        params.put("code",code);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }

    /***
     * 获取用户信息
     */
    public static void getUserInfo(String url,String userId,RequestCallBack<String> callback) {
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /***
     * 修改密码
     */
    public static void modifyPwd(String url,String userId,String oldPasswd,String newPasswd ,RequestCallBack<String> callback) {
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("oldPasswd",oldPasswd);
        params.put("newPasswd",newPasswd);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }





















    /***
     * 微信登录
     */
    public static void wechatLogin(String url,String userId,String oldPasswd,String newPasswd ,RequestCallBack<String> callback) {
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("oldPasswd",oldPasswd);
        params.put("newPasswd",newPasswd);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }


    //=======以下辛振宇=================================================================
}
