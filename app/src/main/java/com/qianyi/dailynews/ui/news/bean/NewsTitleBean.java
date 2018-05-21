package com.qianyi.dailynews.ui.news.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/19.
 */

public class NewsTitleBean {
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

    public NewsTitleData getData() {
        return data;
    }

    public void setData(NewsTitleData data) {
        this.data = data;
    }

    private String return_code;
    private String code;
    private String return_msg;
    private NewsTitleData data;

    public class NewsTitleData{
        public List<NewsTypeRes> getNewsTypeRes() {
            return newsTypeRes;
        }

        public void setNewsTypeRes(List<NewsTypeRes> newsTypeRes) {
            this.newsTypeRes = newsTypeRes;
        }

        private List<NewsTypeRes> newsTypeRes;

        public class NewsTypeRes{
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

            private String catId;
            private String catValue;

        }

    }
}
