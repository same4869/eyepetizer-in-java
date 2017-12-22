package com.xun.eyepetizer.mvp.contract;

import com.xun.eyepetizer.base.BasePresenter;
import com.xun.eyepetizer.base.BaseView;
import com.xun.eyepetizer.mvp.model.bean.FindBean;

import java.util.ArrayList;

/**
 * Created by xunwang on 2017/12/22.
 */

public interface FindContract {
    interface View extends BaseView<Presenter> {
        void setData(ArrayList<FindBean> beans);
    }

    interface Presenter extends BasePresenter {
        void requestData();
    }
}
