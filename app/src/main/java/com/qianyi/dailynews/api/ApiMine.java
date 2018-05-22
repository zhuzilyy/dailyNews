package com.qianyi.dailynews.api;

import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.utils.OkHttpManager;

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
}
