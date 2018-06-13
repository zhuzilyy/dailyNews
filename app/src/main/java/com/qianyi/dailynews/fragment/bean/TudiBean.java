package com.qianyi.dailynews.fragment.bean;

/**
 * Created by Administrator on 2018/6/13.
 */

public class TudiBean {
    private String return_code;
    private String code;
    private String return_msg;
    private TudiData data;
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

    public TudiData getData() {
        return data;
    }

    public void setData(TudiData data) {
        this.data = data;
    }
}
