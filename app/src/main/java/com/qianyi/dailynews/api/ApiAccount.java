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
    public static void updatePwd(String url,String phone,String newPasswd,String code,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("phone",phone);
        params.put("newPasswd",newPasswd);
        params.put("code",code);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
}
