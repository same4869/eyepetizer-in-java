package com.xun.eyepetizer.mvp.contract;

import com.xun.eyepetizer.base.BasePresenter;
import com.xun.eyepetizer.base.BaseView;
import com.xun.eyepetizer.mvp.model.bean.HomeBean;

/**
 * Created by xunwang on 2017/12/20.
 */

public interface HomeContract {
    interface View extends BaseView<Presenter>{
        void setData(HomeBean bean);
    }

    interface Presenter extends BasePresenter{
        void requestData();
    }
}
