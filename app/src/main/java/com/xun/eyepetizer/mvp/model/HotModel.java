package com.xun.eyepetizer.mvp.model;

import android.content.Context;

import com.xun.eyepetizer.mvp.model.bean.HotBean;
import com.xun.eyepetizer.network.ApiService;
import com.xun.eyepetizer.network.RetrofitClient;

import io.reactivex.Observable;

/**
 * Created by xunwang on 2018/1/24.
 */

public class HotModel {
    public Observable<HotBean> loadData(Context context, String strategy) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL);
        ApiService apiService = retrofitClient.create(ApiService.class);
        return apiService.getHotData(10, strategy, "26868b32e808498db32fd51fb422d00175e179df", 83);
    }
}
