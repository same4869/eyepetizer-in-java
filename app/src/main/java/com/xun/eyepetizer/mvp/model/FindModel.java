package com.xun.eyepetizer.mvp.model;

import android.content.Context;

import com.xun.eyepetizer.mvp.model.bean.FindBean;
import com.xun.eyepetizer.network.ApiService;
import com.xun.eyepetizer.network.RetrofitClient;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by xunwang on 2017/12/22.
 */

public class FindModel {
    public Observable<ArrayList<FindBean>> loadData(Context context) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL);
        ApiService apiService = retrofitClient.create(ApiService.class);
        return apiService.getFindData();
    }
}
