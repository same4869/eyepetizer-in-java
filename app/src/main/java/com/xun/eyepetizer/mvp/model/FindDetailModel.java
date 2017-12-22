package com.xun.eyepetizer.mvp.model;

import android.content.Context;

import com.xun.eyepetizer.mvp.model.bean.HotBean;
import com.xun.eyepetizer.network.ApiService;
import com.xun.eyepetizer.network.RetrofitClient;

import io.reactivex.Observable;

/**
 * Created by xunwang on 2017/12/22.
 */

public class FindDetailModel {
    public Observable<HotBean> loadData(Context context, String categoryName, String strategy) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL);
        ApiService apiService = retrofitClient.create(ApiService.class);
        return apiService.getFindDetailData(categoryName, strategy, "26868b32e808498db32fd51fb422d00175e179df", 83);
    }

    public Observable<HotBean> loadMoreData(Context context, int start, String categoryName, String strategy) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL);
        ApiService apiService = retrofitClient.create(ApiService.class);
        return apiService.getFindDetailMoreData(start, 10, categoryName, strategy);
    }
}
