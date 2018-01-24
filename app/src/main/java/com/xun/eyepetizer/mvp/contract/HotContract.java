package com.xun.eyepetizer.mvp.contract;

import com.xun.eyepetizer.base.BasePresenter;
import com.xun.eyepetizer.base.BaseView;
import com.xun.eyepetizer.mvp.model.bean.HotBean;

/**
 * Created by xunwang on 2018/1/24.
 */

public interface HotContract {

    interface View extends BaseView<Presenter> {
        void setData(HotBean bean);
    }

    interface Presenter extends BasePresenter {
        void requestData(String strategy);
    }

}
