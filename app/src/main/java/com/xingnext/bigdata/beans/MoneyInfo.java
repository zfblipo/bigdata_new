package com.xingnext.bigdata.beans;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lipo on 2017/3/21.
 */
public class MoneyInfo implements Serializable {


    public String id;
    public String title;
    public String price;

    public boolean isChoiced;//是否被选中

    public MoneyInfo(){

    }

    public MoneyInfo(boolean isChoiced){
        this.isChoiced = isChoiced;
    }

    public static MoneyInfo fromJson(JSONObject json){
        MoneyInfo info = new MoneyInfo();

        info.id = json.optString("id");
        info.title = json.optString("title");
        info.price = json.optString("price");
        info.isChoiced = false;

        return info;
    }

}
