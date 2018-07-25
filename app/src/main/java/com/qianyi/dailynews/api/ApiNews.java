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


    /**
     * 获取新闻详情里的热门评论
     * @param callback
     */
    public static void GetNewsCommend(String url,String userId,String newsId,int page,int pageSize,int pageLevel2,int pageSizeLevel2 , RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId );
        params.put("newsId",newsId );
        params.put("page",page +"");
        params.put("pageSize",pageSize+"" );
        params.put("pageLevel2",pageLevel2+"" );
        params.put("pageSizeLevel2",pageSizeLevel2+"" );
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }

    /**
     * 发表评论
     * @param callback
     */
    public static void PublishNewsCommend(String url,String userId,String newsId,int page,String parentId ,String level,String comment , RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId );
        params.put("newsId",newsId );
        params.put("page",page +"");
        params.put("parentId",parentId );
        params.put("level",level);
        params.put("comment",comment);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 评论点赞
     * @param callback
     */
    public static void CommLike(String url,String commentId ,String userId , RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("commentId",commentId);
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 获取新闻相关推荐
     * @param callback
     */
    public static void GetRemmond(String url,String newsPageSize ,String adPageSize , RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("newsPageSize",newsPageSize);
        params.put("adPageSize",adPageSize);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }

    /**
     * 获取一条新闻
     * @param callback
     */
    public static void GetOneNewsDetails(String url,String commentId  ,String userId ,String page  ,String pageSize  , RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("commentId",commentId );
        params.put("userId",userId);
        params.put("page",page);
        params.put("pageSize",pageSize );
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }

    /**
     * 获取新闻奖励数量
     * @param callback
     */
    public static void GetNewsAward(String url,String userId , RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 到新闻详情界面首先调该接口
     * @param callback
     */
    public static void ReadNews(String url,String userId,String newsId , RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("newsId",newsId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 到新闻详情界面首先调该接口
     * @param callback
     */
    public static void GetRewardAfterReadNews(String url,String userId,String newsId ,String ifreadPackage, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("newsId",newsId);
        params.put("ifReadPackage",ifreadPackage);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 热词
     * @param callback
     */
    public static void hotWord(String url,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 任务热词
     * @param callback
     */
    public static void missionHotWord(String url,String userId,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }

    /**
     * 删除新闻
     * @param callback
     */
    public static void deleteNews(String url,String userid,String newsId, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("id",newsId);
        params.put("userId",userid);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }


    //=======以下辛振宇=================================================================

}
