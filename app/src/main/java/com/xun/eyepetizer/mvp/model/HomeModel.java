package com.xun.eyepetizer.mvp.model;

import android.content.Context;

import com.xun.eyepetizer.mvp.model.bean.HomeBean;
import com.xun.eyepetizer.network.ApiService;
import com.xun.eyepetizer.network.RetrofitClient;

import io.reactivex.Observable;


/**
 * Created by xunwang on 2017/12/20.
 */

public class HomeModel {
    public Observable<HomeBean> loadData(Context context, boolean isFirst, String data) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL);
        ApiService apiService  = retrofitClient.create(ApiService.class);
        if(isFirst) {
            return apiService.getHomeData();
        }else{
            return apiService.getHomeMoreData(data.toString(), "2");
        }
    }

}