package com.xingnext.bigdata.beans;

import java.io.Serializable;

/**
 * Created by lipo on 2017/4/23.
 */
public class GameTypeInfo implements Serializable {

    private String id;
    private String title;
    private boolean isChoiced;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChoiced() {
        return isChoiced;
    }

    public void setChoiced(boolean choiced) {
        isChoiced = choiced;
    }
}
