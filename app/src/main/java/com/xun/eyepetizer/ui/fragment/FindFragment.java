package com.xun.eyepetizer.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xun.eyepetizer.R;
import com.xun.eyepetizer.adapter.FindAdapter;
import com.xun.eyepetizer.mvp.contract.FindContract;
import com.xun.eyepetizer.mvp.model.bean.FindBean;
import com.xun.eyepetizer.mvp.presenter.FindPresenter;
import com.xun.eyepetizer.ui.FindDetailActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by xunwang on 2017/12/22.
 */

public class FindFragment extends BaseFragment implements FindContract.View {
    @BindView(R.id.gv_find)
    GridView gvFind;

    private FindPresenter mPresenter;
    private FindAdapter mAdapter;
    private ArrayList<FindBean> mList;

    @Override
    protected void initView() {
        mPresenter = new FindPresenter(getContext(), this);
        mPresenter.start();
        mAdapter = new FindAdapter(getContext(), mList);
        gvFind.setAdapter(mAdapter);
        gvFind.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FindBean bean = mList.get(position);
                String name = bean.getName();
                Intent intent = new Intent(getActivity(), FindDetailActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.find_fragment;
    }

    @Override
    public void setData(ArrayList<FindBean> beans) {
        mAdapter.setData(beans);
        mList = beans;
    }

}
