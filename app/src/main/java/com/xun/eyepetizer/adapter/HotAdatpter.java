package com.xun.eyepetizer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by xunwang on 2018/1/24.
 */

public class HotAdatpter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mList;
    private ArrayList<String> mTitles;

    public HotAdatpter(FragmentManager fm, ArrayList<Fragment> list, ArrayList<String> titles) {
        super(fm);
        mList = list;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
