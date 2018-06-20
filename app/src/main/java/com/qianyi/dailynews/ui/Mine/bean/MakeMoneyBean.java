package com.qianyi.dailynews.ui.Mine.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class MakeMoneyBean {
    private String return_code;
    private String code;
    private String return_msg;
    private List<MakeMoneyInfo> data;
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

    public List<MakeMoneyInfo> getData() {
        return data;
    }

    public void setData(List<MakeMoneyInfo> data) {
        this.data = data;
    }
}
