package com.xun.eyepetizer.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xun.eyepetizer.R;
import com.xun.eyepetizer.adapter.HotAdatpter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by xunwang on 2018/1/24.
 */

public class HotFragment extends BaseFragment {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private ArrayList<String> mTabs = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] STRATEGY = {"weekly", "monthly", "historical"};

    @Override
    protected void initView() {
        mTabs.add("周排行");
        mTabs.add("月排行");
        mTabs.add("总排行");

        RankFragment weekFragment = new RankFragment();
        Bundle weekBundle = new Bundle();
        weekBundle.putString("strategy", STRATEGY[0]);
        weekFragment.setArguments(weekBundle);
        RankFragment monthFragment = new RankFragment();
        Bundle monthBundle = new Bundle();
        monthBundle.putString("strategy", STRATEGY[1]);
        monthFragment.setArguments(monthBundle);
        RankFragment allFragment = new RankFragment();
        Bundle allBundle = new Bundle();
        allBundle.putString("strategy", STRATEGY[2]);
        allFragment.setArguments(allBundle);
        mFragments.add(weekFragment);
        mFragments.add(monthFragment);
        mFragments.add(allFragment);
        vpContent.setAdapter(new HotAdatpter(getFragmentManager(), mFragments, mTabs));
        tabs.setupWithViewPager(vpContent);
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.hot_fragment;
    }

}
