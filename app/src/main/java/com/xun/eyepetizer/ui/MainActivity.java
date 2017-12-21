package com.xun.eyepetizer.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.xun.eyepetizer.R;
import com.xun.eyepetizer.ui.fragment.HomeFragment;
import com.xun.eyepetizer.utils.AppUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_find)
    RadioButton rbFind;
    @BindView(R.id.rb_hot)
    RadioButton rbHot;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;
    @BindView(R.id.rg_root)
    RadioGroup rgRoot;

    private HomeFragment homeFragment;

    private long mExitTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init();

        setRadioButton();
        initToolbar();
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            //异常情况
            List<Fragment> mFragments = getSupportFragmentManager().getFragments();
//            for (item in mFragments) {
//                if (item is HomeFragment) {
//                    homeFragment = item
//                }
//                if (item is FindFragment) {
//                    findFragment = item
//                }
//                if (item is HotFragment) {
//                    hotFragemnt = item
//                }
//                if (item is MineFragment) {
//                    mineFragment = item
//                }
//            }
        } else {
            homeFragment = new HomeFragment();
//            findFragment = FindFragment()
//            mineFragment = MineFragment()
//            hotFragemnt = HotFragment()
            FragmentTransaction fragmentTrans = getSupportFragmentManager().beginTransaction();
            fragmentTrans.add(R.id.fl_content, homeFragment);
//            fragmentTrans.add(R.id.fl_content, findFragment)
//            fragmentTrans.add(R.id.fl_content, mineFragment)
//            fragmentTrans.add(R.id.fl_content, hotFragemnt)
            fragmentTrans.commit();
        }
//        getSupportFragmentManager().beginTransaction().show(homeFragment)
//                .hide(findFragment)
//                .hide(mineFragment)
//                .hide(hotFragemnt)
//                .commit();
    }

    private void initToolbar() {
        String today = getToday();
        tvBarTitle.setText(today);
        tvBarTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Lobster-1.4.otf"));
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setRadioButton() {
        rbHome.setChecked(true);
        rbHome.setTextColor(Color.BLACK);
    }

    private String getToday() {
        String[] list = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (index < 0) {
            index = 0;
        }
        return list[index];
    }

    @OnClick(R.id.rb_home)
    public void onClickHome() {
        clearState();
        rbHome.setChecked(true);
        rbHome.setTextColor(Color.BLACK);
    }

    @OnClick(R.id.rb_find)
    public void onClickFind() {
        clearState();
        rbFind.setChecked(true);
        rbFind.setTextColor(Color.BLACK);
    }

    @OnClick(R.id.rb_hot)
    public void onClickHot() {
        clearState();
        rbHot.setChecked(true);
        rbHot.setTextColor(Color.BLACK);
    }

    @OnClick(R.id.rb_mine)
    public void onClickMine() {
        clearState();
        rbMine.setChecked(true);
        rbMine.setTextColor(Color.BLACK);
    }

    @Override
    public void onClick(View v) {

    }

    private void clearState() {
        rgRoot.clearCheck();
        rbHome.setTextColor(Color.GRAY);
        rbMine.setTextColor(Color.GRAY);
        rbHot.setTextColor(Color.GRAY);
        rbFind.setTextColor(Color.GRAY);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime <= 2000) {
                finish();
                toast.cancel();
            } else {
                mExitTime = System.currentTimeMillis();
                toast = AppUtil.showToast("再按一次退出程序");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
