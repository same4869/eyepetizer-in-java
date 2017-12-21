package com.xun.eyepetizer.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xun.eyepetizer.R;
import com.xun.eyepetizer.adapter.HomeAdatper;
import com.xun.eyepetizer.mvp.contract.HomeContract;
import com.xun.eyepetizer.mvp.model.bean.HomeBean;
import com.xun.eyepetizer.mvp.model.bean.HomeBean.IssueListBean.ItemListBean;
import com.xun.eyepetizer.mvp.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

/**
 * Created by xunwang on 2017/12/20.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private boolean mIsRefresh;
    private HomePresenter mPresenter;
    private ArrayList<ItemListBean> mList = new ArrayList<>();
    private HomeAdatper mAdapter;
    private String data;

    @Override
    protected void initView() {
        mPresenter = new HomePresenter(getContext(), this);
        mPresenter.start();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HomeAdatper(getContext(), mList);
        recyclerView.setAdapter(mAdapter);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastPositon = layoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPositon == mList.size() - 1) {
                    if (data != null) {
                        mPresenter.moreData(data);
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.home_fragment;
    }

    @Override
    public void onRefresh() {
        if (!mIsRefresh) {
            mIsRefresh = true;
            mPresenter.start();
        }
    }

    @Override
    public void setData(HomeBean bean) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(bean.getNextPageUrl());
        data = m.replaceAll("").subSequence(1, m.replaceAll("").length() - 1).toString();
        if (mIsRefresh) {
            mIsRefresh = false;
            refreshLayout.setRefreshing(false);
            if (mList.size() > 0) {
                mList.clear();
            }

        }

        for (int i = 0; i < bean.getIssueList().size(); i++) {
            for (int j = 0; j < bean.getIssueList().get(i).getItemList().size(); j++) {
                if (bean.getIssueList().get(i).getItemList().get(j).getType().equals("video")) {
                    mList.add(bean.getIssueList().get(i).getItemList().get(j));
                }
            }
        }

        mAdapter.notifyDataSetChanged();
    }
}
