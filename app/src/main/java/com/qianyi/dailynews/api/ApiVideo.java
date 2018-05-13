package com.qianyi.dailynews.api;

import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.utils.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/13.
 */

public class ApiVideo {
    /**
     * 获取视频列表
     * @param callback
     */
    public static void getVideoList(String url,int page, String pageSize, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("page",page+"");
        params.put("pageSize",pageSize);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
}
