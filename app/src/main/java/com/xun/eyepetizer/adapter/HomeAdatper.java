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
import android.widget.TextView;

import com.xun.eyepetizer.R;
import com.xun.eyepetizer.mvp.model.bean.HomeBean;
import com.xun.eyepetizer.mvp.model.bean.VideoBean;
import com.xun.eyepetizer.ui.VideoDetailActivity;
import com.xun.eyepetizer.utils.ImageLoadUtils;
import com.xun.eyepetizer.utils.ObjectSaveUtils;
import com.xun.eyepetizer.utils.SPUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xunwang on 2017/12/20.
 */

public class HomeAdatper extends RecyclerView.Adapter<HomeAdatper.HomeViewHolder> {
    private Context context;
    private List<HomeBean.IssueListBean.ItemListBean> list;
    private LayoutInflater inflater;

    public HomeAdatper(Context context, List<HomeBean.IssueListBean.ItemListBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewHolder(context, inflater.inflate(R.layout.item_home, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        final HomeBean.IssueListBean.ItemListBean bean = list.get(position);
        final String title = bean.getData().getTitle();
        final String category = bean.getData().getCategory();
        int minute = bean.getData().getDuration() / 60;
        final int second = bean.getData().getDuration() - (minute * 60);
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
//        String playUrl = bean.getData().getPlayUrl();
        final String photo = bean.getData().getCover().getFeed();
        HomeBean.IssueListBean.ItemListBean.DataBean.AuthorBean author = bean.getData().getAuthor();
        ImageLoadUtils.display(context, holder.ivPhoto, photo);
        holder.tvTitle.setText(title);
        holder.tvDetail.setText("发布于 " + category + " / " + realMinute + ":" + realSecond);
        if (author != null) {
            ImageLoadUtils.display(context, holder.ivUser, author.getIcon());
        } else {
            holder.ivUser.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoDetailActivity.class);
                String desc = bean.getData().getDescription();
                int duration = bean.getData().getDuration();
                String playUrl = bean.getData().getPlayUrl();
                String blurred = bean.getData().getCover().getBlurred();
                int collect = bean.getData().getConsumption().getCollectionCount();
                int share = bean.getData().getConsumption().getShareCount();
                int reply = bean.getData().getConsumption().getReplyCount();
                long time = System.currentTimeMillis();
                VideoBean videoBean = new VideoBean(photo, title, desc, duration, playUrl, category, blurred, collect, share, reply, time);
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

    class HomeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.iv_user)
        ImageView ivUser;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_detail)
        TextView tvDetail;

        Context context;

        public HomeViewHolder(Context context, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = context;
            tvTitle.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"));
        }
    }
}
