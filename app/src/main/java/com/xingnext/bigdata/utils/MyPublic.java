package com.xingnext.bigdata.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;

import com.lipo.utils.Arith;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lipo on 2017/3/11.
 */
public class MyPublic {

    public static void setLog(String content) {
        Log.i("zfb_json", content);
    }

    public static void getStatusHeight(Context context) {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        MyStatic.statusHeight = statusBarHeight1;
    }

    public static String numberToString(int position) {
        String strNumber = "";

        if (position < 10) {
            strNumber = numberMap.get(position);
        } else if (position < 20) {
            int yu = position % 10;
            strNumber = "十" + numberMap.get(yu);
        } else {
            int yu = position % 10;
            strNumber = numberMap.get(position / 10) + "十" + numberMap.get(yu);
        }

        return strNumber;
    }

    private static Map<Integer, String> numberMap = new HashMap<Integer, String>();

    static {
        numberMap.put(0, "");
        numberMap.put(1, "一");
        numberMap.put(2, "二");
        numberMap.put(3, "三");
        numberMap.put(4, "四");
        numberMap.put(5, "五");
        numberMap.put(6, "六");
        numberMap.put(7, "七");
        numberMap.put(8, "八");
        numberMap.put(9, "九");
    }

    //JSON 解析double类型
    public static double jiexiDouble(JSONObject json, String value) {

        if (!json.isNull(value)) {
            if (!"".equals(json.optString(value))) {
                return json.optDouble(value);
            }
        }

        return 0.0;
    }

    public static int stringToInt(String str) {
        if (com.lipo.utils.MyPublic.isNumber(str)) {
            return Integer.valueOf(str).intValue();
        }
        return 0;
    }

    public static double stringToDouble(String str) {
        if (str == null || "".equals(str)) {
            return 0.0;
        }
        return Double.valueOf(str).doubleValue();
    }

    /**
     * 字符串转换百分数
     *
     * @param str
     * @return
     */
    public static String stringToBFB(String str) {
        if(str == null || "".equals(str)){
            return "--";
        }
//        double number = com.lipo.utils.MyPublic.stringToDouble(str);
        return str + "%";
    }

    /**
     * 字符串转换百分数
     *
     * @param str
     * @return
     */
    public static String stringToBFB2(String str) {
        double number = com.lipo.utils.MyPublic.stringToDouble(str);
        return Arith.mul(number, 100) + "%";
    }

    /**
     * 获取user-agent
     *
     * @param context
     * @return
     */
    public static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }

        sb.insert(sb.indexOf("Linux;") + 6, " Jumi 1.0.1;");

        return sb.toString();
    }

    public static boolean isEmpty(String string) {
        if (string == null || "".equals(string)) {
            return true;
        }
        return false;
    }

}
