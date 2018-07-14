package com.qianyi.dailynews.ui.Mine.bean;

/**
 * Created by Administrator on 2018/5/22.
 */

public class GoldCoinData {
    private String name;
    private String time;
    private String cnt;
    private String state;
    private String type;
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}
