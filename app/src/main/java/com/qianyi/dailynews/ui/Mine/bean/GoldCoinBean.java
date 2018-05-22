package com.qianyi.dailynews.ui.Mine.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */

public class GoldCoinBean {
    private String code;
    private List<GoldCoinData> data;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<GoldCoinData> getData() {
        return data;
    }

    public void setData(List<GoldCoinData> data) {
        this.data = data;
    }
}
