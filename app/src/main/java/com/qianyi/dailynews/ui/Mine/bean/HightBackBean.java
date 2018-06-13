package com.qianyi.dailynews.ui.Mine.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public class HightBackBean {
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

    public List<HightBackData> getData() {
        return data;
    }

    public void setData(List<HightBackData> data) {
        this.data = data;
    }

    private String return_code;
    private String code;
    private String return_msg;
    private List<HightBackData> data;



    public class HightBackData{
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String id;
        private String title;
        private String description;
        private String cash;
        private String type;
        private String status;


    }
}
