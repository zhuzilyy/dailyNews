package com.qianyi.dailynews.fragment.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */

public class NewsInfo {
    private String id;
    private String publishDate;
    private String posterScreenName;
    private String url;
    private String title;
    private String posterId;
    private String viewCount;
    private String content;
    private List<String> imgsUrl;

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
}
