package com.qianyi.dailynews.api;

import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.utils.OkHttpManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/22.
 */

public class ApiMine {
    /**
     * 签到
     * @param callback
     */
    public static void sign(String url,String userId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 金币详情
     * @param callback
     */
    public static void goldCoinDetail(String url,String userId,int curPage,String pageSize,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("curPage",curPage+"");
        params.put("pageSize",pageSize);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 提现状态
     * @param callback
     */
    public static void withdrawal(String url,String userId,int curPage,String pageSize,String state,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("curPage",curPage+"");
        params.put("pageSize",pageSize);
        params.put("state","");
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 活动专区
     * @param callback
     */
    public static void activityZone(String url,String userId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 新手答题
     * @param callback
     */
    public static void greendHandQuestion(String url,String userId,String type,String answers ,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("type",type);
        params.put("answers",answers);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 签到状态
     * @param callback
     */
    public static void signState(String url,String userId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 晒收入
     * @param callback
     */
    public static void shareIncome(String url,String userId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 高额返利
     * @param callback
     */
    public static void fanliList(String url,String userId,int page,String pageSize, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("page",page+"");
        params.put("pageSize",pageSize);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 高额返利任务列表
     * @param callback
     */
    public static void fanliTaskList(String url,String userId,int page,String pageSize, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("page",page+"");
        params.put("pageSize",pageSize);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 轻松赚钱
     * @param callback
     */
    public static void makeMoney(String url,String userId,int page,String pageSize, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("page",page+"");
        params.put("pageSize",pageSize);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 获取个人信息
     * @param callback
     */
    public static void getUserInfo(String url,String userId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 获取个人信息
     * @param callback
     */
    public static void recall(String url,String userId,String recallId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("recallUser",recallId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 查询可提现金额
     * @param callback
     */
    public static void withdrawalMoney(String url,String userId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 确认提现
     * @param callback
     */
    public static void doWithdrawal(String url,String userId,String fee,String captcha,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("fee",fee );
        params.put("captcha",captcha);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 提现记录
     * @param callback
     */
    public static void withdrawalRecord(String url,String userId,int curPage,String pageSize,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("curPage",curPage+"");
        params.put("pageSize",pageSize);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 加载的webiew
     * @param callback
     */
    public static void getWebview(String url,String type,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("type",type);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     *日常任务状态
     * @param callback
     */
    public static void dailyMission(String url,String userId ,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     *日常任务晒收入奖励
     * @param callback
     */
    public static void dailyMissionShare(String url,String userId ,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     *新手任务分享到朋友圈
     * @param callback
     */
    public static void greenHandMissionShare(String url,String userId ,String fo ,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("fo",fo);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }

























    /**
     * 高额返利
     * @param callback
     */
    public static void highBackMoney(String url,String userId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }

    /**
     * 高额返利详情
     * @param callback
     */
    public static void highBackMoneyDetails(String url,String userId,String id,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("id",id);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }

    /**
     * 上传图片路劲
     * @param callback
     */
    public static void saveImgs(String url,String imgUrls,String userId, String id,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("id",id);
        params.put("imgUrls ",imgUrls );
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }

    /**
     * 事例图片
     * @param callback
     */
    public static void getMakeMoneyExample(String url,String id, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("id",id);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 事例图片
     * @param
     */
    public static void BeforeShareForToken(String url,String id,String userId ,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("id",id);
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 事例图片
     * @param
     */
    public static void takePartIn(String url,String id,String userId ,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("id",id);
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }






}
