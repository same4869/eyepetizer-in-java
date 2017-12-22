package com.xun.eyepetizer.mvp.presenter;

import android.content.Context;

import com.xun.eyepetizer.mvp.contract.FindDetailContract;
import com.xun.eyepetizer.mvp.model.FindDetailModel;
import com.xun.eyepetizer.mvp.model.bean.HotBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xunwang on 2017/12/22.
 */

public class FindDetailPresenter implements FindDetailContract.Presenter {
    private Context mContext;
    private FindDetailContract.View mView;
    private FindDetailModel mModel = new FindDetailModel();

    public FindDetailPresenter(Context context, FindDetailContract.View view) {
        mView = view;
        mContext = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void requestData(String categoryName, String strategy) {
        Observable<HotBean> observable = mModel.loadData(mContext, categoryName, strategy);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HotBean>() {
            @Override
            public void accept(HotBean bean) throws Exception {
                if (mView != null) {
                    mView.setData(bean);
                }
            }
        });
    }

    public void requesMoreData(int start, String categoryName, String strategy) {
        Observable<HotBean> observable = mModel.loadMoreData(mContext, start, categoryName, strategy);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HotBean>() {
            @Override
            public void accept(HotBean bean) throws Exception {
                if (mView != null) {
                    mView.setData(bean);
                }
            }
        });
    }
}
