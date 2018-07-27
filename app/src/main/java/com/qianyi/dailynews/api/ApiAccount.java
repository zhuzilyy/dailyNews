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
     * 微信发送验证码
     */
    public static void wxConfirmCode(String url,String phone,RequestCallBack<String> callback) {
        Map<String,String> params=new HashMap<>();
        params.put("phone",phone);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }


    /***
     * 微信登录
     */
    public static void wechatLogin(String url,String openid,String nickname,String sex,String language,String city,String province,String country,String headimgurl,String unionid,RequestCallBack<String> callback) {
        Map<String,String> params=new HashMap<>();
        params.put("openid",openid);
        params.put("nickname",nickname);
        params.put("sex",sex);
        params.put("language",language);
        params.put("city",city);
        params.put("province",province);
        params.put("country",country);
        params.put("headimgurl",headimgurl);
        params.put("unionid",unionid);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }

    /***
     * 微信绑定
     */
    public static void wechatBind(String url,String user_id,String openid,String nickname,String sex,String language,String city,String province,String country,String headimgurl,String unionid,RequestCallBack<String> callback) {
        Map<String,String> params=new HashMap<>();
        params.put("userId",user_id);
        params.put("openid",openid);
        params.put("nickname",nickname);
        params.put("sex",sex);
        params.put("language",language);
        params.put("city",city);
        params.put("province",province);
        params.put("country",country);
        params.put("headimgurl",headimgurl);
        params.put("unionid",unionid);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /***
     * 微信手机
     */
    public static void bindPhone(String url,String user_id,String phone,String password,String captcha,String inviteCode,String openid,String nickname,String sex,String language,String city,String province,String country,String headimgurl,String unionid,RequestCallBack<String> callback) {
        Map<String,String> params=new HashMap<>();
        params.put("userId",user_id);
        params.put("phone",phone);
        params.put("password",password);
        params.put("captcha",captcha);
        params.put("inviteCode",inviteCode);
        params.put("openid",openid);
        params.put("nickname",nickname);
        params.put("sex",sex);
        params.put("language",language);
        params.put("city",city);
        params.put("province",province);
        params.put("country",country);
        params.put("headimgurl",headimgurl);
        params.put("unionid",unionid);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }


    //=======以下辛振宇=================================================================
}
