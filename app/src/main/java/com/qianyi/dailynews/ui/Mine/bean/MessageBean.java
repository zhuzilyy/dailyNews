package com.qianyi.dailynews.ui.Mine.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class MessageBean {
    private String return_code;
    private String code;
    private String return_msg;
    private List<MessageInfo> data;
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

    public List<MessageInfo> getData() {
        return data;
    }

    public void setData(List<MessageInfo> data) {
        this.data = data;
    }
}
