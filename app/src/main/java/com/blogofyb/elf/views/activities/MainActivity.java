package com.blogofyb.elf.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogofyb.elf.R;
import com.blogofyb.elf.models.GetMusicModel;
import com.blogofyb.elf.presenters.MoodSongListPresenter;
import com.blogofyb.elf.utils.beans.MusicBean;
import com.blogofyb.elf.utils.interfaces.Model;
import com.blogofyb.elf.utils.interfaces.PlayCallback;
import com.blogofyb.elf.utils.interfaces.Presenter;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;
import com.blogofyb.elf.utils.musicplayer.PlayMusicServiceConnection;
import com.blogofyb.elf.utils.musicplayer.PlayingService;
import com.blogofyb.elf.views.mood.MoodManager;
import com.blogofyb.elf.views.playcallback.MainActivityCallback;


import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BasedActivity implements View.OnClickListener {
    private CircleImageView mSongCover;
    private TextView mSongName;
    private TextView mSinger;
    private LinearLayout mMood;
    private ImageView mPlay;
    private FrameLayout mCover;

    private MoodManager mMoodManager;
    private PlayCallback mCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        bindService();
        initView();
        init();
        mMoodManager.bind();
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
    protected void onDestroy() {
        super.onDestroy();
        unbindService(PlayMusicServiceConnection.getInstance());
        mMoodManager.unBind();
    }

    @Override
    public void onClick(android.view.View v) {
        switch (v.getId()) {
            case R.id.iv_play_main:
                Intent intentForMusicInformationActivity = new Intent(this, PlayingMusicActivity.class);
                startActivity(intentForMusicInformationActivity);
                break;
            case R.id.fl_cover_main:
                Intent intentForLyricActivity = new Intent(this, LyricActivity.class);
                startActivity(intentForLyricActivity);
                break;
            case R.id.iv_tool_bar_menu:

                break;
            case R.id.iv_tool_bar_back:
                DrawerLayout drawerLayout = findViewById(R.id.dl_main);
                drawerLayout.openDrawer(GravityCompat.END);
                break;
        }
    }

    private void initView() {
        mSongCover = findViewById(R.id.civ_cover_main);
        mSongName = findViewById(R.id.tv_song_name_main);
        mSinger = findViewById(R.id.tv_singer_name_main);
        mMood = findViewById(R.id.ll_change_mood);
        mPlay = findViewById(R.id.iv_play_main);
        mCover = findViewById(R.id.fl_cover_main);

        mPlay.setOnClickListener(this);
        mCover.setOnClickListener(this);
        findViewById(R.id.iv_tool_bar_back).setOnClickListener(this);
        findViewById(R.id.iv_tool_bar_menu).setOnClickListener(this);

        mSongCover.setAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_cover));

        mCallback = new MainActivityCallback();
        mCallback.initWidgets(getWindow().getDecorView());
    }

    private void init() {
        mMoodManager = new MoodManager((LinearLayout) findViewById(R.id.ll_change_mood));
    }

    private void bindService() {
        Intent intent = new Intent(this, PlayingService.class);
        bindService(intent, PlayMusicServiceConnection.getInstance(), BIND_AUTO_CREATE);
    }

    private void registerCallback() {
        MyMusicPlayer.registerCallback(mCallback);
    }

    private void unRegisterCallback() {
        MyMusicPlayer.unregisterCallback(mCallback);
    }
}
