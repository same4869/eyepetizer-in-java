package com.xun.eyepetizer.ui.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.xun.eyepetizer.R;

import butterknife.BindView;

/**
 * Created by xunwang on 2018/1/24.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_watch)
    TextView tvWatch;
    @BindView(R.id.tv_advise)
    TextView tvAdvise;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_watch:
//                Intent intentWatch = new Intent(getActivity(), WatchActivity.class);
//                startActivity(intentWatch);
                break;
            case R.id.tv_save:
//                Intent intentSave = new Intent(getActivity(), CacheActivity.class);
//                startActivity(intentSave);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initView() {
        tvSave.setOnClickListener(this);
        tvWatch.setOnClickListener(this);
        tvWatch.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"));
        tvAdvise.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"));
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.mine_fragment;
    }

}
