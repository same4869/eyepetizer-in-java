package com.xun.eyepetizer.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by xunwang on 2017/12/21.
 */

public class VideoBean implements Parcelable, Serializable {
    private String feed;
    private String title;
    private String description;
    private long duration;
    private String playUrl;
    private String category;
    private String blurred;
    private long collect;
    private long share;
    private long reply;
    private long time;

    public VideoBean(String feed, String title, String description, long duration, String playUrl, String category, String blurred, int collect, int share, int reply, long time) {
        this.feed = feed;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.playUrl = playUrl;
        this.collect = collect;
        this.share = share;
        this.reply = reply;
        this.time = time;
        this.category = category;
        this.blurred = blurred;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBlurred() {
        return blurred;
    }

    public void setBlurred(String blurred) {
        this.blurred = blurred;
    }

    public long getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public long getShare() {
        return share;
    }

    public void setShare(long share) {
        this.share = share;
    }

    public long getReply() {
        return reply;
    }

    public void setReply(long reply) {
        this.reply = reply;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    protected VideoBean(Parcel in) {
        feed = in.readString();
        title = in.readString();
        description = in.readString();
        duration = in.readLong();
        blurred = in.readString();
        collect = in.readLong();
        share = in.readLong();
        reply = in.readLong();
        time = in.readLong();
        playUrl = in.readString();
        category = in.readString();
    }

    public static final Creator<VideoBean> CREATOR = new Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel in) {
            return new VideoBean(in);
        }

        @Override
        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(feed);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(duration);
        dest.writeString(blurred);
        dest.writeLong(collect);
        dest.writeLong(share);
        dest.writeLong(reply);
        dest.writeLong(time);
        dest.writeString(playUrl);
        dest.writeString(category);
    }
}
