package com.xingnext.bigdata.beans;

import com.xingnext.bigdata.utils.MyPublic;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7935871433024046104L;

    private String match_id;
    private String match_sn;
    private String competition_id;
    private String season_id;
    private String match_time;
    private String host_id;
    private String season_pre;
    private String host_name;
    private String host_team_image;
    private String host_rank;
    private String host_score;
    private String away_id;
    private String away_name;
    private String away_team_image;
    private String away_rank;
    private String away_score;
    private String match_status;
    private String match_status_desc;
    private String match_title;
    private String price;
    private String order_count;
    private String forecast;
    private String precision;
    private List<String> spf = new ArrayList<String>();
    private String spf0;
    private String spf1;
    private String spf2;
    private String order_status;//当前用户订阅状态，1已订阅，0未订阅
    private String is_favorite;//关注状态，1已关注，0未关注

    private String host_power;
    private String away_power;
    private String ai_match_count;
    private String ai_model_count;
    private String ai_rule_count;
    private List<String> odds_ou = new ArrayList<String>();
    private List<String> odds_yp = new ArrayList<String>();

    private String forecast_win;
    private String forecast_draw;
    private String forecast_lose;

    public static void setSpf(GameInfo info, JSONArray array) {
        if (array != null) {
            int lent = array.length();
            if (lent == 3) {
                info.setSpf0(array.optString(0));
                info.setSpf1(array.optString(1));
                info.setSpf2(array.optString(2));
            }
        }
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getMatch_sn() {
        return match_sn;
    }

    public void setMatch_sn(String match_sn) {
        this.match_sn = match_sn;
    }

    public String getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(String competition_id) {
        this.competition_id = competition_id;
    }

    public String getSeason_id() {
        return season_id;
    }

    public void setSeason_id(String season_id) {
        this.season_id = season_id;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getHost_id() {
        return host_id;
    }

    public void setHost_id(String host_id) {
        this.host_id = host_id;
    }

    public String getSeason_pre() {
        return season_pre;
    }

    public void setSeason_pre(String season_pre) {
        this.season_pre = season_pre;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getHost_team_image() {
        return host_team_image;
    }

    public void setHost_team_image(String host_team_image) {
        this.host_team_image = host_team_image;
    }

    public String getHost_rank() {
        return host_rank;
    }

    public void setHost_rank(String host_rank) {
        this.host_rank = host_rank;
    }

    public int getHost_score() {
        return MyPublic.stringToInt(host_score);
    }

    public void setHost_score(String host_score) {
        this.host_score = host_score;
    }

    public int getAway_score() {
        return MyPublic.stringToInt(away_score);
    }

    public void setAway_score(String away_score) {
        this.away_score = away_score;
    }

    public String getAway_id() {
        return away_id;
    }

    public void setAway_id(String away_id) {
        this.away_id = away_id;
    }

    public String getAway_name() {
        return away_name;
    }

    public void setAway_name(String away_name) {
        this.away_name = away_name;
    }

    public String getAway_team_image() {
        return away_team_image;
    }

    public void setAway_team_image(String away_team_image) {
        this.away_team_image = away_team_image;
    }

    public String getAway_rank() {
        return away_rank;
    }

    public void setAway_rank(String away_rank) {
        this.away_rank = away_rank;
    }


    public String getMatch_status() {
        return match_status;
    }

    public void setMatch_status(String match_status) {
        this.match_status = match_status;
    }

    public String getMatch_status_desc() {
        return match_status_desc;
    }

    public void setMatch_status_desc(String match_status_desc) {
        this.match_status_desc = match_status_desc;
    }

    public String getMatch_title() {
        return match_title;
    }

    public void setMatch_title(String match_title) {
        this.match_title = match_title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder_count() {
        return order_count;
    }

    public void setOrder_count(String order_count) {
        this.order_count = order_count;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getSpf0() {
        return spf0;
    }

    public void setSpf0(String spf0) {
        this.spf0 = spf0;
    }

    public String getSpf1() {
        return spf1;
    }

    public void setSpf1(String spf1) {
        this.spf1 = spf1;
    }

    public String getSpf2() {
        return spf2;
    }

    public void setSpf2(String spf2) {
        this.spf2 = spf2;
    }

    public List<String> getSpf() {
        if (spf == null){
            spf = new ArrayList<String>();
            spf.add("");
            spf.add("");
            spf.add("");
        }else if(spf.size() != 3){
            spf.add("");
            spf.add("");
            spf.add("");
        }
        return spf;
    }

    public void setSpf(List<String> spf) {
        this.spf = spf;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getHost_power() {
        return host_power;
    }

    public void setHost_power(String host_power) {
        this.host_power = host_power;
    }

    public String getAway_power() {
        return away_power;
    }

    public void setAway_power(String away_power) {
        this.away_power = away_power;
    }

    public String getAi_match_count() {
        return ai_match_count;
    }

    public void setAi_match_count(String ai_match_count) {
        this.ai_match_count = ai_match_count;
    }

    public String getAi_model_count() {
        return ai_model_count;
    }

    public void setAi_model_count(String ai_model_count) {
        this.ai_model_count = ai_model_count;
    }

    public String getAi_rule_count() {
        return ai_rule_count;
    }

    public void setAi_rule_count(String ai_rule_count) {
        this.ai_rule_count = ai_rule_count;
    }

    public List<String> getOdds_ou() {
        return odds_ou;
    }

    public void setOdds_ou(List<String> odds_ou) {
        this.odds_ou = odds_ou;
    }

    public List<String> getOdds_yp() {
        return odds_yp;
    }

    public void setOdds_yp(List<String> odds_yp) {
        this.odds_yp = odds_yp;
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

    public String getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(String is_favorite) {
        this.is_favorite = is_favorite;
    }
}
