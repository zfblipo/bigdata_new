package com.xingnext.bigdata.beans;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */
public class VersionInfo implements Serializable {

    public String version_name;
    public int version_code;
    public String title;
    public List<String> contents = new ArrayList<String>();
    public String url;

    public static VersionInfo fromJson(JSONObject json){
        VersionInfo info = new VersionInfo();

        info.version_code = json.optInt("version_code");
        info.version_name = json.optString("version_name");

        JSONObject upgrade = json.optJSONObject("upgrade");
        if(upgrade!=null){
            info.title = upgrade.optString("title");
            info.url = upgrade.optString("url");

            JSONArray array = upgrade.optJSONArray("content");
            if(array!=null){
                int lent = array.length();
                for (int i = 0; i < lent ; i++) {
                    String str = array.optString(i);
                    info.contents.add(str);
                }
            }
        }

        return info;
    }

}
