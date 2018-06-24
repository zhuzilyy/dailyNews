package com.qianyi.dailynews.ui.news.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */

public class NewsBean {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPosterScreenName() {
        return posterScreenName;
    }

    public void setPosterScreenName(String posterScreenName) {
        this.posterScreenName = posterScreenName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImgsUrl() {
        return imgsUrl;
    }

    public void setImgsUrl(List<String> imgsUrl) {
        this.imgsUrl = imgsUrl;
    }

    public String getIfRead() {
        return ifRead;
    }

    public void setIfRead(String ifRead) {
        this.ifRead = ifRead;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    private String id;
    private String publishDate;
    private String posterScreenName;
    private String url;
    private String title;
    private String posterId;
    private String viewCount;
    private String content;
    private List<String> imgsUrl;
    private String ifRead;
    private String  newsType;

    private String readNum;
    private List<String> imgs;
    private String adType;


    public String getRedpackage() {
        return redpackage;
    }

    public void setRedpackage(String redpackage) {
        this.redpackage = redpackage;
    }

    public String getRedMoney() {
        return redMoney;
    }

    public void setRedMoney(String redMoney) {
        this.redMoney = redMoney;
    }

    private String redpackage;
    private String redMoney;

}
