package com.xingnext.bigdata.beans;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/6.
 */
public class TimeInfo implements Serializable {

    private String period_time;
    private String period_week;
    private int match_count;

    public static TimeInfo fromJson(JSONObject json){
        TimeInfo info = new TimeInfo();

        info.setPeriod_week(json.optString("period_week"));
        info.setMatch_count(json.optInt("match_count"));
        info.setPeriod_time(json.optString("period_time"));

        return info;
    }

    public String getPeriod_time() {
        return period_time;
    }

    public void setPeriod_time(String period_time) {
        this.period_time = period_time;
    }

    public String getPeriod_week() {
        return period_week;
    }

    public void setPeriod_week(String period_week) {
        this.period_week = period_week;
    }

    public int getMatch_count() {
        return match_count;
    }

    public void setMatch_count(int match_count) {
        this.match_count = match_count;
    }
}
