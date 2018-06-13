package com.qianyi.dailynews.fragment.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public class TudiData {
    private String cnt;
    private String cash;
    private List<TudiInfo> tudiArray;
    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public List<TudiInfo> getTudiArray() {
        return tudiArray;
    }

    public void setTudiArray(List<TudiInfo> tudiArray) {
        this.tudiArray = tudiArray;
    }
}
