package com.xun.eyepetizer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xun.eyepetizer.R;
import com.xun.eyepetizer.mvp.model.bean.HotBean;
import com.xun.eyepetizer.mvp.model.bean.VideoBean;
import com.xun.eyepetizer.ui.VideoDetailActivity;
import com.xun.eyepetizer.utils.ImageLoadUtils;
import com.xun.eyepetizer.utils.ObjectSaveUtils;
import com.xun.eyepetizer.utils.SPUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xunwang on 2017/12/22.
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {
    private Context context;
    private ArrayList<HotBean.ItemListBean.DataBean> list;
    private LayoutInflater inflater;

    public RankAdapter(Context context, ArrayList<HotBean.ItemListBean.DataBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RankViewHolder(context, inflater.inflate(R.layout.item_rank, parent, false));
    }

    @Override
    public void onBindViewHolder(RankViewHolder holder, final int position) {
        String photoUrlTemp = null;
        if (list.get(position).getCover() != null) {
            photoUrlTemp = list.get(position).getCover().getFeed();
            ImageLoadUtils.display(context, holder.ivPhoto, photoUrlTemp);
        }
        final String photoUrl = photoUrlTemp;
        final String title = list.get(position).getTitle();
        holder.tvTitle.setText(title);
        final String category = list.get(position).getCategory();
        final int duration = list.get(position).getDuration();
        int minute = duration / 60;
        int second = duration - (minute * 60);
        String realMinute;
        String realSecond;
        if (minute < 10) {
            realMinute = "0" + minute;
        } else {
            realMinute = String.valueOf(minute);
        }
        if (second < 10) {
            realSecond = "0" + second;
        } else {
            realSecond = String.valueOf(second);
        }
        holder.tvTime.setText(category + " / " + realMinute + "'" + realSecond + "''");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoDetailActivity.class);
                String desc = list.get(position).getDescription();
                int duration = list.get(position).getDuration();
                String playUrl = list.get(position).getPlayUrl();
                String blurred = list.get(position).getCover().getBlurred();
                int collect = list.get(position).getConsumption().getCollectionCount();
                int share = list.get(position).getConsumption().getShareCount();
                int reply = list.get(position).getConsumption().getReplyCount();
                long time = System.currentTimeMillis();
                VideoBean videoBean = new VideoBean(photoUrl, title, desc, duration, playUrl, category, blurred, collect, share, reply, time);
                String url = SPUtils.getInstance(context, "beans").getString(playUrl);
                if ("".equals(url)) {
                    int count = SPUtils.getInstance(context, "beans").getInt("count");
                    if (count != -1) {
                        count++;
                    } else {
                        count = 1;
                    }
                    SPUtils.getInstance(context, "beans").put("count", count);
                    SPUtils.getInstance(context, "beans").put(playUrl, playUrl);
                    ObjectSaveUtils.saveObject(context, "bean" + count, videoBean);
                }
                intent.putExtra("data", (Parcelable) videoBean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    class RankViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.rl_text)
        RelativeLayout rlText;
        @BindView(R.id.ll_moban)
        LinearLayout llMoban;

        public RankViewHolder(Context context, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvTitle.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/FZLanTingHeiS-L-GB-Regular.TTF"));
        }
    }
}
