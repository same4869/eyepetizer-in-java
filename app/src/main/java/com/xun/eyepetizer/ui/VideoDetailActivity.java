package com.xun.eyepetizer.ui;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.xun.eyepetizer.R;
import com.xun.eyepetizer.mvp.model.bean.VideoBean;
import com.xun.eyepetizer.utils.AppUtil;
import com.xun.eyepetizer.utils.ImageLoadUtils;
import com.xun.eyepetizer.utils.ObjectSaveUtils;
import com.xun.eyepetizer.utils.SPUtils;
import com.xun.eyepetizer.utils.VideoListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload2.RxDownload;

/**
 * Created by xunwang on 2017/12/21.
 */

public class VideoDetailActivity extends AppCompatActivity {
    private static int MSG_IMAGE_LOADED = 101;
    @BindView(R.id.gsy_player)
    StandardGSYVideoPlayer gsyPlayer;
    @BindView(R.id.iv_bottom_bg)
    ImageView ivBottomBg;
    @BindView(R.id.tv_video_title)
    TextView tvVideoTitle;
    @BindView(R.id.tv_video_time)
    TextView tvVideoTime;
    @BindView(R.id.tv_video_desc)
    TextView tvVideoDesc;
    @BindView(R.id.iv_video_favor)
    ImageView ivVideoFavor;
    @BindView(R.id.tv_video_favor)
    TextView tvVideoFavor;
    @BindView(R.id.iv_video_share)
    ImageView ivVideoShare;
    @BindView(R.id.tv_video_share)
    TextView tvVideoShare;
    @BindView(R.id.iv_video_reply)
    ImageView ivVideoReply;
    @BindView(R.id.tv_video_reply)
    TextView tvVideoReply;
    @BindView(R.id.iv_video_download)
    ImageView ivVideoDownload;
    @BindView(R.id.tv_video_download)
    TextView tvVideoDownload;
    private VideoBean bean;
    private ImageView imageView;
    private OrientationUtils orientationUtils;
    private boolean isPlay, isPause;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_IMAGE_LOADED) {
                gsyPlayer.setThumbImageView(imageView);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        bean = getIntent().getParcelableExtra("data");
        initView();
        prepareVideo();
    }

    private void prepareVideo() {
        String uri = getIntent().getStringExtra("loaclFile");
        if (uri != null) {
            gsyPlayer.setUp(uri, false, null, null);
        } else {
            gsyPlayer.setUp(bean.getPlayUrl(), false, null, null);
        }
        //增加封面
        imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        new ImageViewAsyncTask(mHandler, this, imageView).execute(bean.getFeed());
        gsyPlayer.getTitleTextView().setVisibility(View.GONE);
        gsyPlayer.getBackButton().setVisibility(View.VISIBLE);
        orientationUtils = new OrientationUtils(this, gsyPlayer);
        gsyPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        gsyPlayer.setRotateViewAuto(false);
        gsyPlayer.setLockLand(false);
        gsyPlayer.setShowFullAnimation(false);
        gsyPlayer.setNeedLockFull(true);
        gsyPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                gsyPlayer.startWindowFullscreen(VideoDetailActivity.this, true, true);
            }
        });
        gsyPlayer.setStandardVideoAllCallBack(new VideoListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                orientationUtils.backToProtVideo();
            }
        });
        gsyPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                //配合下方的onConfigurationChanged
                orientationUtils.setEnable(!lock);
            }
        });
        gsyPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        String bgUrl = bean.getBlurred();
        ImageLoadUtils.displayHigh(this, ivBottomBg, bgUrl);
        tvVideoDesc.setText(bean.getDescription());
        tvVideoDesc.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"));
        tvVideoTitle.setText(bean.getTitle());
        tvVideoTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/FZLanTingHeiS-L-GB-Regular.TTF"));
        String category = bean.getCategory();
        long duration = bean.getDuration();
        long minute = duration / 60;
        long second = duration - (minute * 60);
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
        tvVideoTime.setText(category + " / " + realMinute + "'" + realSecond + "''");
        tvVideoFavor.setText(bean.getCollect() + "");
        tvVideoShare.setText(bean.getShare() + "");
        tvVideoReply.setText(bean.getReply() + "");
        tvVideoDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击下载
                String url = SPUtils.getInstance(getApplicationContext(), "downloads").getString(bean.getPlayUrl());

                if (url.equals("")) {
                    int count = SPUtils.getInstance(getApplicationContext(), "downloads").getInt("count");
                    if (count != -1) {
                        count++;
                    } else {
                        count = 1;
                    }
                    SPUtils.getInstance(getApplicationContext(), "downloads").put("count", count);
                    ObjectSaveUtils.saveObject(getApplicationContext(), "download" + count, bean);
                    addMission(bean.getPlayUrl(), count);
                } else {
                    AppUtil.showToast("该视频已经缓存过了");
                }
            }
        });
    }

    private void addMission(final String playUrl, int count) {
        RxDownload.getInstance(this).serviceDownload(playUrl, "download" + count).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                AppUtil.showToast("开始下载");
                SPUtils.getInstance(getApplicationContext(), "downloads").put(bean.getPlayUrl(), bean.getPlayUrl());
                SPUtils.getInstance(getApplicationContext(), "download_state").put(playUrl, true);
            }

        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                AppUtil.showToast("添加任务失败");
            }
        });
    }


    private class ImageViewAsyncTask extends AsyncTask<String, Void, String> {
        private Handler handler;
        private String mPath;
        private FileInputStream mIs;
        private VideoDetailActivity mActivity;
        private ImageView imageView;

        public ImageViewAsyncTask(Handler handler, VideoDetailActivity activity, ImageView mImageView) {
            this.handler = handler;
            this.mActivity = activity;
            this.imageView = mImageView;
        }

        @Override
        protected String doInBackground(String... strings) {
            FutureTarget<File> future = Glide.with(mActivity)
                    .load(strings[0])
                    .downloadOnly(100, 100);
            try {
                File cacheFile = future.get();
                mPath = cacheFile.getAbsolutePath();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return mPath;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                mIs = new FileInputStream(s);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(mIs);
            imageView.setImageBitmap(bitmap);
            Message message = handler.obtainMessage();
            message.what = MSG_IMAGE_LOADED;
            handler.sendMessage(message);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isPlay && !isPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!gsyPlayer.isIfCurrentIsFullscreen()) {
                    gsyPlayer.startWindowFullscreen(getApplicationContext(), true, true);
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (gsyPlayer.isIfCurrentIsFullscreen()) {
                    StandardGSYVideoPlayer.backFromWindowFull(this);
                }
                orientationUtils.setEnable(true);
            }
        }
    }

    @Override
    public void onBackPressed() {
        orientationUtils.backToProtVideo();
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        orientationUtils.releaseListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }
}
