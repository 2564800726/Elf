package com.blogofyb.elf.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.blogofyb.elf.views.playcallback.PlayingActivityCallback;

public class PlayingMusicActivity extends BasedActivity implements View.OnClickListener {
    private PlayCallback mCallback;
    private LikeMusic mLikeMusic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_playing_music);
        initView();
        mCallback = new PlayingActivityCallback();
        mCallback.initWidgets(getWindow().getDecorView());
        mLikeMusic = new LikeMusic(this);
        if (!MyMusicPlayer.isPlaying()) {
            MyMusicPlayer.playMusic(MyMusicPlayer.getCurrentIndex());
            MyMusicPlayer.pauseMusic();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerCallback();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unRegisterCallback();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_previous_playing:
                MyMusicPlayer.playPrevious();
                break;
            case R.id.iv_play_playing:
                if (MyMusicPlayer.isPlaying()) {
                    MyMusicPlayer.pauseMusic();
                } else {
                    MyMusicPlayer.play();
                }
                break;
            case R.id.iv_next_playing:
                MyMusicPlayer.playNext();
                break;
            case R.id.iv_tool_bar_menu:

                break;
            case R.id.iv_tool_bar_back:
                finish();
                break;
            case R.id.iv_download_playing:

                break;
            case R.id.iv_star_playing:
                Intent intentForRecordActivity = new Intent(this, RecordActivity.class);
                startActivity(intentForRecordActivity);
                break;
            case R.id.iv_like_playing:
                MusicBean musicForLike = MyMusicPlayer.getMusics().get(MyMusicPlayer.getCurrentIndex());
                mLikeMusic.likeMusic(musicForLike.getName(), musicForLike.getSinger());
                break;
            case R.id.iv_comment_playing:
                Intent intentForCommentActivity = new Intent(this, CommentActivity.class);
                startActivity(intentForCommentActivity);
                break;
        }
    }

    private void initView() {
        findViewById(R.id.iv_previous_playing).setOnClickListener(this);
        findViewById(R.id.iv_play_playing).setOnClickListener(this);
        findViewById(R.id.iv_next_playing).setOnClickListener(this);
        findViewById(R.id.iv_tool_bar_back).setOnClickListener(this);
        findViewById(R.id.iv_like_playing).setOnClickListener(this);
        findViewById(R.id.iv_star_playing).setOnClickListener(this);
        findViewById(R.id.iv_comment_playing).setOnClickListener(this);
        findViewById(R.id.iv_download_playing).setOnClickListener(this);

        TextView songName = findViewById(R.id.tv_song_name_tool_bar);
        songName.setTextColor(getResources().getColor(R.color.color_black));
        ImageView menu = findViewById(R.id.iv_tool_bar_menu);
        menu.setImageResource(R.drawable.ic_share);
        menu.setOnClickListener(this);
        SeekBar progress = findViewById(R.id.sb_progress_playing);
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                unRegisterCallback();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MyMusicPlayer.seekTo(seekBar.getProgress());
                registerCallback();
            }
        });
    }

    private void registerCallback() {
        MyMusicPlayer.registerCallback(mCallback);
    }

    private void unRegisterCallback() {
        MyMusicPlayer.unregisterCallback(mCallback);
    }
}
