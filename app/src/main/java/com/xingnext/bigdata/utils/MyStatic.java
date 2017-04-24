package com.xingnext.bigdata.utils;

import com.xingnext.bigdata.beans.GameTypeInfo;
import com.xingnext.bigdata.beans.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipo on 2017/3/12.
 */
public class MyStatic {

    public static int meun_status = 0;//0表示没有滑动过，1表示显示出来菜单项

    public static int mine_stattus = 0;//0表示初始的，1表示滑到最上端了。

    public static int mainf_status = 0;//0表示什么都没有，1处于状态中
    
    public static String version_name = "";
    public static int version_code = 0;

    public static int statusHeight = 0;

    public static boolean isFristUpgrade = true;

    public static String startImage = "";

    public static UserData userData = new UserData();

    public static final int pageSize = 49;

    public static List<GameTypeInfo> gameTypeInfos = new ArrayList<GameTypeInfo>();

}
