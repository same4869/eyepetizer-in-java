package com.xun.eyepetizer.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.xun.eyepetizer.app.EyeApplication;

/**
 * Created by xunwang on 2017/12/20.
 */

public class AppUtil {
    public static Toast showToast(String msg) {
        Toast toast = Toast.makeText(EyeApplication.getApplication(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return toast;
    }
}
