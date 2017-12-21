package com.xun.eyepetizer.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xun.eyepetizer.R;
import com.xun.eyepetizer.mvp.model.bean.HomeBean;
import com.xun.eyepetizer.utils.ImageLoadUtils;

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
        HomeBean.IssueListBean.ItemListBean bean = list.get(position);
        String title = bean.getData().getTitle();
        String category = bean.getData().getCategory();
        int minute = bean.getData().getDuration() / 60;
        int second = bean.getData().getDuration() - (minute * 60);
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
        String playUrl = bean.getData().getPlayUrl();
        String photo = bean.getData().getCover().getFeed();
        HomeBean.IssueListBean.ItemListBean.DataBean.AuthorBean author = bean.getData().getAuthor();
        ImageLoadUtils.display(context , holder.ivPhoto, photo);
        holder.tvTitle.setText(title);
        holder.tvDetail.setText("发布于 " + category + " / " + realMinute + ":" + realSecond);
        if (author != null) {
            ImageLoadUtils.display(context, holder.ivUser, author.getIcon());
        } else {
            holder.ivUser.setVisibility(View.GONE);
        }
//        holder ?.itemView ?.setOnClickListener {
//            //跳转视频详情页
//            var intent :Intent = Intent(context, VideoDetailActivity:: class.java)
//            var desc = bean ?.data ?.description
//            var duration = bean ?.data ?.duration
//            var playUrl = bean ?.data ?.playUrl
//            var blurred = bean ?.data ?.cover ?.blurred
//            var collect = bean ?.data ?.consumption ?.collectionCount
//            var share = bean ?.data ?.consumption ?.shareCount
//            var reply = bean ?.data ?.consumption ?.replyCount
//            var time = System.currentTimeMillis()
//            var videoBean = VideoBean(photo, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
//            var url = SPUtils.getInstance(context !!, "beans").getString(playUrl !!)
//            if (url.equals("")) {
//                var count = SPUtils.getInstance(context !!, "beans").getInt("count")
//                if (count != -1) {
//                    count = count.inc()
//                } else {
//                    count = 1
//                }
//                SPUtils.getInstance(context !!, "beans").put("count", count)
//                SPUtils.getInstance(context !!, "beans").put(playUrl !!, playUrl)
//                ObjectSaveUtils.saveObject(context !!, "bean$count", videoBean)
//            }
//            intent.putExtra("data", videoBean as Parcelable)
//            context ?.let {
//                context -> context.startActivity(intent)
//            }
//        }
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
