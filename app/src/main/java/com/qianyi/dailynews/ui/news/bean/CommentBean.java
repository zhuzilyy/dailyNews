package com.qianyi.dailynews.ui.news.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/31.
 */

public class CommentBean {
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

    public CommentData getData() {
        return data;
    }

    public void setData(CommentData data) {
        this.data = data;
    }

    private String return_code;
    private String code;
    private String return_msg;
    private CommentData data;
    public class CommentData{
        public List<NewsCommentRes> getNewsCommentRes() {
            return newsCommentRes;
        }

        public void setNewsCommentRes(List<NewsCommentRes> newsCommentRes) {
            this.newsCommentRes = newsCommentRes;
        }

        private List<NewsCommentRes> newsCommentRes;
        public class NewsCommentRes{
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getHeadPortrait() {
                return headPortrait;
            }

            public void setHeadPortrait(String headPortrait) {
                this.headPortrait = headPortrait;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLike() {
                return like;
            }

            public void setLike(String like) {
                this.like = like;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getIlike() {
                return ilike;
            }

            public void setIlike(String ilike) {
                this.ilike = ilike;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public List<NewsCommentLevel2Array> getNewsCommentLevel2Array() {
                return newsCommentLevel2Array;
            }

            public void setNewsCommentLevel2Array(List<NewsCommentLevel2Array> newsCommentLevel2Array) {
                this.newsCommentLevel2Array = newsCommentLevel2Array;
            }

            private String id;
            private String headPortrait;
            private String name;
            private String like;
            private String time;
            private String comment;
            private String ilike;
            private String userName;
            private List<NewsCommentLevel2Array> newsCommentLevel2Array;

            public class NewsCommentLevel2Array{
                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getComment() {
                    return comment;
                }

                public void setComment(String comment) {
                    this.comment = comment;
                }

                private String name;
                private String userName;
                private String comment;

            }


        }

    }
}
