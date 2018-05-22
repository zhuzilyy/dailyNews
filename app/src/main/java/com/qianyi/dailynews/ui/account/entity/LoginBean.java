package com.qianyi.dailynews.ui.account.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/19.
 */

public class LoginBean {
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

    public String getLoginData() {
        return LoginData;
    }

    public void setLoginData(String loginData) {
        LoginData = loginData;
    }

    public LoginBean.LoginData getData() {
        return data;
    }

    public void setData(LoginBean.LoginData data) {
        this.data = data;
    }

    private String return_code;
    private String code;
    private String LoginData;
    private LoginData data;
    public class LoginData implements Serializable{
        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHead_portrait() {
            return head_portrait;
        }

        public void setHead_portrait(String head_portrait) {
            this.head_portrait = head_portrait;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getMy_invite_code() {
            return my_invite_code;
        }

        public void setMy_invite_code(String my_invite_code) {
            this.my_invite_code = my_invite_code;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getEarnings() {
            return earnings;
        }

        public void setEarnings(String earnings) {
            this.earnings = earnings;
        }

        private String user_id;
        private String phone;
        private String head_portrait;
        private String gold;
        private String my_invite_code;
        private String balance;
        private String earnings;


    }
}
