package com.lipo.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyPublic {

    private Context context;

    public MyPublic() {

    }

    public MyPublic(Context context) {
        this.context = context;
    }

    private void getPixels() {

        DisplayMetrics dm2 = context.getResources().getDisplayMetrics();
        System.out.println("heigth2 : " + dm2.heightPixels);
        System.out.println("width2 : " + dm2.widthPixels);
    }

    public static void mapClearEmpty(Map<String, String> params) {
        if (params != null) {
            Set<String> set = params.keySet();
            Iterator<String> its = set.iterator();
            while (its.hasNext()) {
                String key = its.next();
                String value = params.get(key);
                if (value == null || value.isEmpty()) {
                    params.put(key,"");
                }
            }
        }
    }

    /**
     * 正则匹配 是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    // 判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static double stringToDouble(String str) {
        double doubleValue = 0.0;
        if (str == null || "".equals(str)) {
            return 0.0;
        }

        try {
            doubleValue = Double.valueOf(str).doubleValue();
        } catch (NumberFormatException e) {
            doubleValue = 0.0;
        } finally {

        }

        return doubleValue;
    }

    public static int stringToInt(String str) {
        if (com.lipo.utils.MyPublic.isNumber(str)) {
            return Integer.valueOf(str).intValue();
        }
        return 0;
    }

    public static String convertTwo(String value) {
        DecimalFormat format = new DecimalFormat("######0.00");
        return format.format(stringToDouble(value));
    }

    public static double convertTwo(double value) {
        DecimalFormat format = new DecimalFormat("######0.00");
        return stringToDouble(format.format(value));
    }

}
