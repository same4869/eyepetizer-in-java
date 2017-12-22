package com.xun.eyepetizer.mvp.contract;

import com.xun.eyepetizer.base.BasePresenter;
import com.xun.eyepetizer.base.BaseView;
import com.xun.eyepetizer.mvp.model.bean.HotBean;

/**
 * Created by xunwang on 2017/12/22.
 */

public interface FindDetailContract {
    interface View extends BaseView<Presenter>{
        void setData(HotBean bean);
    }

    interface  Presenter  extends BasePresenter {
        void requestData(String categoryName, String strategy);
    }
}
