package com.xun.eyepetizer.utils;

import android.util.Log;

import com.xun.eyepetizer.config.Constants;

/**
 * Created by xunwang on 2017/12/20.
 */

public class LogUtil {
    public static void d(String tag, String msg) {
        if (Constants.IS_DEBUG) {
            Log.d(tag, msg);
        }
    }
}
