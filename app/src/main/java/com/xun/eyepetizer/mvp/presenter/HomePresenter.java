package com.xun.eyepetizer.mvp.presenter;

import android.content.Context;

import com.xun.eyepetizer.mvp.contract.HomeContract;
import com.xun.eyepetizer.mvp.model.HomeModel;
import com.xun.eyepetizer.mvp.model.bean.HomeBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xunwang on 2017/12/20.
 */

public class HomePresenter implements HomeContract.Presenter {
    private Context mContext;
    private HomeContract.View mView;
    private HomeModel mModel = new HomeModel();

    public HomePresenter(Context context, HomeContract.View view) {
        this.mView = view;
        this.mContext = context;
    }

    @Override
    public void start() {
        requestData();
    }

    @Override
    public void requestData() {
        Observable<HomeBean> observable = mModel.loadData(mContext, true, "0");
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HomeBean>() {
            @Override
            public void accept(HomeBean homeBean) throws Exception {
                if (mView != null) {
                    mView.setData(homeBean);
                }
            }
        });
    }

    public void moreData(String data) {
        Observable<HomeBean> observable = mModel.loadData(mContext, false, data);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HomeBean>() {
            @Override
            public void accept(HomeBean homeBean) throws Exception {
                if (mView != null) {
                    mView.setData(homeBean);
                }
            }
        });
    }
}
