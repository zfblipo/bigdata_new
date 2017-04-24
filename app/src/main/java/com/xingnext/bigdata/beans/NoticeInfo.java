package com.xingnext.bigdata.beans;

import java.io.Serializable;

/**
 * Created by lipo on 2017/4/20.
 */
public class NoticeInfo implements Serializable {

    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
