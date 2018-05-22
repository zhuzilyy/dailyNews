package com.qianyi.dailynews.fragment.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */

public class InviteData {
    private List<BannerImgInfo> imgBannerArray;
    private List<String> charBannerArray;
    public List<BannerImgInfo> getImgBannerArray() {
        return imgBannerArray;
    }

    public void setImgBannerArray(List<BannerImgInfo> imgBannerArray) {
        this.imgBannerArray = imgBannerArray;
    }

    public List<String> getCharBannerArray() {
        return charBannerArray;
    }

    public void setCharBannerArray(List<String> charBannerArray) {
        this.charBannerArray = charBannerArray;
    }
}
