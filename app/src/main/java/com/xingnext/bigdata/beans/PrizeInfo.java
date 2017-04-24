package com.xingnext.bigdata.beans;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lipo on 2016/7/20.
 */
public class PrizeInfo implements Serializable{

    public String id;
    public String goodsid;
    public String periods;
    public String endtime;
    public String avatar;
    public String openid;
    public String nickname;
    public String partakes;
    public String code;
    public String period_number;
    public String picarr;
    public String init_money;
    public String title;
    public String nowtime;
    public String change_endtime;
    public boolean isStarting;
    public long surplus;
    public boolean isTiming;
    public String zong_codes;
    public String shengyu_codes;
    public String canyurenshu;
    public String status;
    public String exchange_type;//兑换方式，0未兑换，1快递，2卡密，3云币
    public int temp_status;

    public static PrizeInfo fromJson(JSONObject json){
        PrizeInfo info = new PrizeInfo();
        info.zong_codes = json.optString("zong_codes");
        info.shengyu_codes = json.optString("shengyu_codes");
        info.canyurenshu = json.optString("canyurenshu");
        info.status = json.optString("status");

        info.id = json.optString("id");
        info.goodsid = json.optString("goodsid");
        info.periods = json.optString("periods");
        info.endtime = isempty(json.optString("endtime"));
        info.avatar = json.optString("avatar");
        info.openid = json.optString("openid");
        info.nickname = json.optString("nickname");

        if(info.nickname == null||"null".equals(info.nickname)||"".equals(info.nickname)){
            info.nickname = "参与者";
        }

        info.partakes = json.optString("partakes");
        info.code = json.optString("code");
        info.period_number = json.optString("period_number");
        info.picarr = json.optString("picarr");
        info.init_money = json.optString("init_money");
        info.title = json.optString("title");
        info.nowtime = isempty(json.optString("nowtime"));
        info.change_endtime = json.optString("change_endtime");
        info.exchange_type = json.optString("exchange_type");

        if(!"".equals(info.endtime)&&!"".equals(info.nowtime)){
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间

            info.surplus = Long.valueOf(info.endtime)*1000 - Long.valueOf(info.nowtime)*1000;
            if (info.surplus > 0) {
                info.isTiming = true;
            }else{
                info.isTiming = false;
            }
        }

        info.isStarting = false;
        info.temp_status = 2;

        return info;
    }

    private static String isempty(String string){

        if(string == null||"".equals(string)){
            return "0";
        }else{
            return string;
        }

    }


}
