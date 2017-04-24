package com.xingnext.bigdata.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/10.
 */
public class AccountInfo implements Serializable {

    private String id;
    private String user_id;
    private String money;
    private String balance;
    private String change_time;
    private String change_desc;
    private String change_type;
    private String change_type_desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getChange_time() {
        return change_time;
    }

    public void setChange_time(String change_time) {
        this.change_time = change_time;
    }

    public String getChange_desc() {
        return change_desc;
    }

    public void setChange_desc(String change_desc) {
        this.change_desc = change_desc;
    }

    public String getChange_type() {
        return change_type;
    }

    public void setChange_type(String change_type) {
        this.change_type = change_type;
    }

    public String getChange_type_desc() {
        return change_type_desc;
    }

    public void setChange_type_desc(String change_type_desc) {
        this.change_type_desc = change_type_desc;
    }
}
