package com.qianyi.dailynews.fragment.bean;


import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */
public class NewsList {
    private String catId;
    private String catValue;
    private List<NewsInfo> newsInfoArray;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatValue() {
        return catValue;
    }

    public void setCatValue(String catValue) {
        this.catValue = catValue;
    }

    public List<NewsInfo> getNewsInfoArray() {
        return newsInfoArray;
    }

    public void setNewsInfoArray(List<NewsInfo> newsInfoArray) {
        this.newsInfoArray = newsInfoArray;
    }
}
