package com.xingnext.bigdata.beans;

import com.xingnext.bigdata.utils.MyPublic;

import java.io.Serializable;

/**
 * Created by lipo on 2017/4/10.
 */
public class UserInfo implements Serializable {

    private String user_id;
    private String nickname;
    private String pic;
    private String signature;
    private String stat_count;
    private String stat_win_count;
    private String stat_count_7;
    private String stat_count_7_hit;
    private String stat_rate_count;
    private String stat_rate;
    private String money;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSignature() {
        if(MyPublic.isEmpty(signature)){
            signature = "聚米专家";
        }
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getStat_count() {
        return stat_count;
    }

    public void setStat_count(String stat_count) {
        this.stat_count = stat_count;
    }

    public String getStat_win_count() {
        return stat_win_count;
    }

    public void setStat_win_count(String stat_win_count) {
        this.stat_win_count = stat_win_count;
    }

    public String getStat_count_7() {
        return stat_count_7;
    }

    public void setStat_count_7(String stat_count_7) {
        this.stat_count_7 = stat_count_7;
    }

    public String getStat_count_7_hit() {
        return stat_count_7_hit;
    }

    public void setStat_count_7_hit(String stat_count_7_hit) {
        this.stat_count_7_hit = stat_count_7_hit;
    }

    public String getStat_rate_count() {
        return stat_rate_count;
    }

    public void setStat_rate_count(String stat_rate_count) {
        this.stat_rate_count = stat_rate_count;
    }

    public String getStat_rate() {
        return stat_rate;
    }

    public void setStat_rate(String stat_rate) {
        this.stat_rate = stat_rate;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
