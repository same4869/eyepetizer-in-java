package com.xun.eyepetizer.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xun.eyepetizer.R;
import com.xun.eyepetizer.adapter.RankAdapter;
import com.xun.eyepetizer.mvp.contract.HotContract;
import com.xun.eyepetizer.mvp.model.bean.HotBean;
import com.xun.eyepetizer.mvp.presenter.HotPresenter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by xunwang on 2018/1/24.
 */

public class RankFragment extends BaseFragment implements HotContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private HotPresenter mPresenter;
    private String mStrategy;
    private RankAdapter mAdapter;
    private ArrayList<HotBean.ItemListBean.DataBean> mList = new ArrayList<>();

    @Override
    public void setData(HotBean bean) {
        if (mList.size() > 0) {
            mList.clear();
        }
        for (int i = 0; i < bean.getItemList().size(); i++) {
            mList.add(bean.getItemList().get(i).getData());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RankAdapter(getContext(), mList);
        recyclerView.setAdapter(mAdapter);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mStrategy = arguments.getString("strategy");
            mPresenter = new HotPresenter(getContext(), this);
            mPresenter.requestData(mStrategy);
        }
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.rank_fragment;
    }
}
