package com.xun.eyepetizer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by xunwang on 2017/12/21.
 */

public class SPUtils {
    private SharedPreferences sp;
    private static HashMap<String, SPUtils> sSPMap = new HashMap<>();

    public SPUtils(Context context, String spName) {
        sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public int getInt(String key) {
        return sp.getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void put(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public void put(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public void put(String key, Boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public static SPUtils getInstance(Context context, String spName) {
        if (isSpace(spName)) {
            spName = "spUtils";
        }
        SPUtils sp = sSPMap.get(spName);
        if (sp == null) {
            sp = new SPUtils(context, spName);
            sSPMap.put(spName, sp);
        }
        return sp;
    }

    private static Boolean isSpace(String s) {
        if (s == null) {
            return true;
        }
        int i = 0;
        int len = s.length();
        while (i < len) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
            ++i;
        }
        return true;
    }
}
