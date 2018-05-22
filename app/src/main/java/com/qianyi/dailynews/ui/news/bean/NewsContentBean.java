package com.qianyi.dailynews.ui.news.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/21.
 */

public class NewsContentBean {
    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public NewsContentData getData() {
        return data;
    }

    public void setData(NewsContentData data) {
        this.data = data;
    }

    private String return_code;
    private String code;
    private String return_msg;
    private NewsContentData data;

    public class NewsContentData{

        public List<NewsByType> getNewsByType() {
            return newsByType;
        }

        public void setNewsByType(List<NewsByType> newsByType) {
            this.newsByType = newsByType;
        }

        private List<NewsByType> newsByType;

        public class NewsByType{
            public List<NewsContentInfo> getNewsInfoArray() {
                return newsInfoArray;
            }

            public void setNewsInfoArray(List<NewsContentInfo> newsInfoArray) {
                this.newsInfoArray = newsInfoArray;
            }

            public List<AdavertContent> getAdvertArray() {
                return advertArray;
            }

            public void setAdvertArray(List<AdavertContent> advertArray) {
                this.advertArray = advertArray;
            }

            private List<NewsContentInfo> newsInfoArray;
            private List<AdavertContent> advertArray;

            public class NewsContentInfo{
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

                public String getIfRead() {
                    return ifRead;
                }

                public void setIfRead(String ifRead) {
                    this.ifRead = ifRead;
                }

                public List<String> getImgsUrl() {
                    return imgsUrl;
                }

                public void setImgsUrl(List<String> imgsUrl) {
                    this.imgsUrl = imgsUrl;
                }

                private String id;
                private String publishDate;
                private String posterScreenName;
                private String url;
                private String title;
                private String posterId;
                private String viewCount;
                private String content;
                private String ifRead;
                private List<String> imgsUrl;

            }

            public class AdavertContent{
                private String id;
                private String title;
                private String url;
                private String readNum;
                private List<String> imgs;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
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
            }


        }




    }


}
