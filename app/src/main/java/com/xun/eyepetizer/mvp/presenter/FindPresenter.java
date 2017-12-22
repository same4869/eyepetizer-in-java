package com.xun.eyepetizer.mvp.presenter;

import android.content.Context;

import com.xun.eyepetizer.mvp.contract.FindContract;
import com.xun.eyepetizer.mvp.model.FindModel;
import com.xun.eyepetizer.mvp.model.bean.FindBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xunwang on 2017/12/22.
 */

public class FindPresenter implements FindContract.Presenter {
    private Context mContext;
    private FindContract.View mView;
    private FindModel mModel = new FindModel();

    public FindPresenter(Context context, FindContract.View view) {
        this.mView = view;
        this.mContext = context;
    }

    @Override
    public void start() {
        requestData();
    }

    @Override
    public void requestData() {
        Observable<ArrayList<FindBean>> observable = mModel.loadData(mContext);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ArrayList<FindBean>>() {
            @Override
            public void accept(ArrayList<FindBean> beans) throws Exception {
                if (mView != null) {
                    mView.setData(beans);
                }
            }
        });
    }
}
