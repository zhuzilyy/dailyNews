package com.qianyi.dailynews.api;

import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.utils.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/19.
 */

public class ApiNews {
    /**
     * 获取新闻标题
     * @param callback
     */
    public static void GetNewsTitles(String url, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 获取新闻条目
     * @param callback
     */
    public static void GetNewsContent(String url,String userId,String catId,int page,int pageSize,int adPage,int adPageSize , RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId );
        params.put("catId",catId );
        params.put("page",page +"");
        params.put("pageSize",pageSize+"" );
        params.put("adPage",adPage+"" );
        params.put("adPageSize",adPageSize+"" );
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    //=======以下辛振宇=================================================================

}
