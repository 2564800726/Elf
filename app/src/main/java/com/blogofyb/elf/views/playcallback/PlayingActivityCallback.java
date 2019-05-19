package com.blogofyb.elf.views.playcallback;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blogofyb.elf.R;
import com.blogofyb.elf.utils.beans.MusicBean;
import com.blogofyb.elf.utils.interfaces.PlayCallback;
import com.blogofyb.elf.utils.musicmanager.LikeMusic;
import com.blogofyb.elf.utils.musicmanager.StarMusic;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;
import com.blogofyb.tools.img.ImageLoader;
import com.blogofyb.tools.img.cache.LruAndDiskLruCache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PlayingActivityCallback implements PlayCallback {
    private Context mContext;

    private ImageLoader mImageLoader;
    private StarMusic mStarMusic;
    private LikeMusic mLikeMusic;

    private TextView mSongName;
    private TextView mSinger;
    private ImageView mCoverPlaying;
    private ImageView mStar;
    private ImageView mLike;
    private SeekBar mProgress;
    private TextView mCurrentProgress;
    private TextView mTotalProgress;
    private ImageView mPlay;

    @Override
    public void updateUI() {
        MusicBean music = MyMusicPlayer.getMusics().get(MyMusicPlayer.getCurrentIndex());
        mSongName.setText(music.getName());
        mSinger.setText(music.getSinger());
        mImageLoader.load(music.getCover()).withTag(music.getCover()).into(mCoverPlaying);
        checkIsStared(music.getId());
        checkIsLiked(music.getName(), music.getSinger());
        int currentProgress = MyMusicPlayer.current();
        mProgress.setMax(MyMusicPlayer.total());
        mProgress.setProgress(currentProgress);
        SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.CHINA);
        mCurrentProgress.setText(format.format(new Date(MyMusicPlayer.current())));
        mTotalProgress.setText(format.format(new Date(MyMusicPlayer.total())));
        if (MyMusicPlayer.isPlaying()) {
            mPlay.setImageResource(R.drawable.ic_play_running);
        } else {
            mPlay.setImageResource(R.drawable.ic_play_pause);
        }
    }

    @Override
    public void initWidgets(View view) {
        mContext = view.getContext();
        mSongName = view.findViewById(R.id.tv_song_name_tool_bar);
        mSinger = view  .findViewById(R.id.tv_singer_playing);
        mCoverPlaying = view.findViewById(R.id.iv_cover_playing);
        mStar = view.findViewById(R.id.iv_star_playing);
        mLike = view.findViewById(R.id.iv_like_playing);
        mProgress = view.findViewById(R.id.sb_progress_playing);
        mCurrentProgress = view.findViewById(R.id.tv_current_progress_playing);
        mTotalProgress = view.findViewById(R.id.tv_total_progress_playing);
        mPlay = view.findViewById(R.id.iv_play_playing);

        ImageLoader.Options options = new ImageLoader.Options();
        options.context(mContext)
                .failed(R.drawable.ic_default_bottom_music_icon)
                .place(R.drawable.ic_default_bottom_music_icon)
                .cache(new LruAndDiskLruCache(mContext, "cover"))
                .scaleImage(true)
                .compressImage(false);
        mImageLoader = new ImageLoader();
        mImageLoader.apply(options);

        mStarMusic = new StarMusic(view.getContext());
        mLikeMusic = new LikeMusic(view .getContext());
    }

    private void checkIsStared(String id) {
        if (mStarMusic.checkStar(id)) {
            mStar.setImageResource(R.drawable.ic_star_on_blue);
        } else {
            mStar.setImageResource(R.drawable.ic_star_off_blue);
        }
    }

    private void checkIsLiked(String songName, String singer) {
        if (mLikeMusic.checkLike(songName, singer)) {
            mLike.setImageResource(R.drawable.ic_like_on_blue);
        } else {
            mLike.setImageResource(R.drawable.ic_like_off_blue);
        }
    }
}
