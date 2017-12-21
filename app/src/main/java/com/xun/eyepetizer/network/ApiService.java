package com.xun.eyepetizer.network;

import com.xun.eyepetizer.mvp.model.bean.HomeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xunwang on 2017/12/20.
 */

public interface ApiService {
    public static final String BASE_URL = "http://baobab.kaiyanapp.com/api/";

    //获取首页第一页数据
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Observable<HomeBean> getHomeData();


    //获取首页第一页之后的数据  ?date=1499043600000&num=2
    @GET("v2/feed")
    Observable<HomeBean> getHomeMoreData(@Query("date")String date, @Query("num")String num);
}
