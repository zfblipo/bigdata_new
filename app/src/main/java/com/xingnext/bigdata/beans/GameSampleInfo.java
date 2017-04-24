package com.xingnext.bigdata.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/14.
 */
public class GameSampleInfo implements Serializable {

    private String league_name;
    private String host_name;
    private String away_name;
    private String host_score;
    private String away_score;
    private String match_time;
    private String match_result;//比赛结果，3为胜1为平0为负

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getAway_name() {
        return away_name;
    }

    public void setAway_name(String away_name) {
        this.away_name = away_name;
    }

    public String getHost_score() {
        return host_score;
    }

    public void setHost_score(String host_score) {
        this.host_score = host_score;
    }

    public String getAway_score() {
        return away_score;
    }

    public void setAway_score(String away_score) {
        this.away_score = away_score;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getMatch_result() {
        return match_result;
    }

    public void setMatch_result(String match_result) {
        this.match_result = match_result;
    }
}
