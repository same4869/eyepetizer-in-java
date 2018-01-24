package com.xun.eyepetizer.mvp.presenter;

import android.content.Context;

import com.xun.eyepetizer.mvp.contract.HotContract;
import com.xun.eyepetizer.mvp.model.HotModel;
import com.xun.eyepetizer.mvp.model.bean.FindBean;
import com.xun.eyepetizer.mvp.model.bean.HotBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xunwang on 2018/1/24.
 */

public class HotPresenter implements HotContract.Presenter {
    private Context mContext;
    private HotContract.View mView;
    private HotModel mModel = new HotModel();

    public HotPresenter(Context context, HotContract.View view) {
        mView = view;
        mContext = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void requestData(String strategy) {
        Observable<HotBean> observable = mModel.loadData(mContext,strategy);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HotBean>() {
            @Override
            public void accept(HotBean beans) throws Exception {
                if (mView != null) {
                    mView.setData(beans);
                }
            }
        });
    }
}
