package com.xingnext.bigdata.beans;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lipo on 2017/4/10.
 */
public class UserData implements Serializable {

    public String user_id;
    public String access_token;
    public String refresh_token;

    public static void fromJson(JSONObject json,UserData user){
        user.user_id = json.optString("user_id");
        user.access_token = json.optString("access_token");
        user.refresh_token = json.optString("refresh_token");
    }
}
