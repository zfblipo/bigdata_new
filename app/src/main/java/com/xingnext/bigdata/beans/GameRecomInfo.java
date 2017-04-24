package com.xingnext.bigdata.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */
public class GameRecomInfo implements Serializable {

    private String id;
    private String type_id;
    private String user_id;
    private String match_id;
    private String price;
    private String title;
    private String remark;
    private String latest_time;
    private String create_time;
    private String order_count;
    private String order_status;//当前用户订阅状态，1已订阅，0未订阅
    private UserInfo user = new UserInfo();
    private List<GameInfo> match_list = new ArrayList<GameInfo>();

    private String forecast_win;
    private String forecast_draw;
    private String forecast_lose;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLatest_time() {
        return latest_time;
    }

    public void setLatest_time(String latest_time) {
        this.latest_time = latest_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOrder_count() {
        return order_count;
    }

    public void setOrder_count(String order_count) {
        this.order_count = order_count;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public List<GameInfo> getMatch_list() {
        return match_list;
    }

    public void setMatch_list(List<GameInfo> match_list) {
        this.match_list = match_list;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getForecast_win() {
        return forecast_win;
    }

    public void setForecast_win(String forecast_win) {
        this.forecast_win = forecast_win;
    }

    public String getForecast_draw() {
        return forecast_draw;
    }

    public void setForecast_draw(String forecast_draw) {
        this.forecast_draw = forecast_draw;
    }

    public String getForecast_lose() {
        return forecast_lose;
    }

    public void setForecast_lose(String forecast_lose) {
        this.forecast_lose = forecast_lose;
    }
}
