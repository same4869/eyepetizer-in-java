package com.xun.eyepetizer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.xun.eyepetizer.R;
import com.xun.eyepetizer.adapter.RankAdapter;
import com.xun.eyepetizer.mvp.contract.FindDetailContract;
import com.xun.eyepetizer.mvp.model.bean.HotBean;
import com.xun.eyepetizer.mvp.presenter.FindDetailPresenter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xunwang on 2017/12/22.
 */

public class FindDetailActivity extends AppCompatActivity implements FindDetailContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private FindDetailPresenter mPresenter;
    private RankAdapter mAdapter;
    private String data;
    private boolean mIsRefresh;
    private ArrayList<HotBean.ItemListBean.DataBean> mList = new ArrayList<>();
    private int mstart = 10;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_detail);
        ButterKnife.bind(this);
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init();
        setToolbar();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RankAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);
        refreshLayout.setOnRefreshListener(this);
        mPresenter = new FindDetailPresenter(this, this);
        mPresenter.requestData(name, "date");
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastPositon = layoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPositon == mList.size() - 1) {
                    if (data != null) {
                        mPresenter.requesMoreData(mstart, name, "date");
                        mstart += 10;
                    }

                }
            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        name = getIntent().getStringExtra("name");
        bar.setTitle(name);
        bar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onRefresh() {
        if (!mIsRefresh) {
            mIsRefresh = true;
            mPresenter.requestData(name, "date");
        }
    }

    @Override
    public void setData(HotBean bean) {
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
        for (int i = 0; i < bean.getItemList().size(); i++) {
            mList.add(bean.getItemList().get(i).getData());
        }
        mAdapter.notifyDataSetChanged();
    }
}
