package com.qianyi.dailynews.api;

import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.utils.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/15.
 */

public class ApiNews {
    /**
     * 新闻
     * @param callback
     */
    public static void getNews(String url,String userId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId );
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
}
