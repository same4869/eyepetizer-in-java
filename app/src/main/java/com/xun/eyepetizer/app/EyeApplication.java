package com.xun.eyepetizer.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by xunwang on 2017/12/20.
 */

public class EyeApplication extends Application {
    private static EyeApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getApplication() {
        return instance;
    }
}
