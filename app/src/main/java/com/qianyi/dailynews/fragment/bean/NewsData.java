package com.qianyi.dailynews.fragment.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */

public class NewsData {
    private List<NewsList> newsByType;

    public List<NewsList> getNewsByType() {
        return newsByType;
    }

    public void setNewsByType(List<NewsList> newsByType) {
        this.newsByType = newsByType;
    }
}
