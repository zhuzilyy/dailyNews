package com.qianyi.dailynews.fragment.bean;

/**
 * Created by Administrator on 2018/5/26.
 */

public class VideoDetailBean {
    private String return_code;
    private String code;
    private String return_msg;
    private VideoDetailData data;

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

    public VideoDetailData getData() {
        return data;
    }

    public void setData(VideoDetailData data) {
        this.data = data;
    }
}
