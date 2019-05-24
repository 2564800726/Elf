package com.blogofyb.elf.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.blogofyb.elf.R;
import com.blogofyb.elf.models.GetLyricModel;
import com.blogofyb.elf.presenters.GetLyricPresenter;
import com.blogofyb.elf.utils.beans.LyricBean;
import com.blogofyb.elf.utils.interfaces.View;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;
import com.blogofyb.elf.views.customs.Lyric;
import com.blogofyb.elf.views.customs.LyricAdapter;
import com.blogofyb.elf.views.mood.MoodManager;
import com.blogofyb.elf.views.playcallback.LyricActivityCallback;
import com.blogofyb.tools.thread.ThreadManager;

import java.util.List;

public class LyricActivity extends BasedActivity implements View<LyricBean>, android.view.View.OnClickListener {
    private LyricAdapter mLyricAdapter;
    private GetLyricPresenter mGetLyricPresenter;
    private GetLyricModel mGetLyricModel;
    private LyricActivityCallback mCallback;
    private List<LyricBean> mLyrics;
    private MoodManager mMoodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lyric);
        mGetLyricPresenter = new GetLyricPresenter();
        mGetLyricModel = new GetLyricModel();
        mLyricAdapter = new LyricAdapter(mLyrics, this);
        mMoodManager = new MoodManager((LinearLayout) findViewById(R.id.ll_change_mood));
        mMoodManager.bind();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGetLyricPresenter.bind(mGetLyricModel);
        mGetLyricPresenter.bind(this);
        mGetLyricPresenter.showData();
        registerCallback();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGetLyricPresenter.unBind(this);
        mGetLyricPresenter.unBind(mGetLyricModel);
        unregisterCallback();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMoodManager.unBind();
    }

    @Override
    public void show(List<LyricBean> data) {
        if (data == null) {
            return;
        }
        mLyrics = data;
        mLyricAdapter.refreshData(data);
    }

    @Override
    public void onClick(android.view.View v) {
        switch (v.getId()) {
            case R.id.iv_tool_bar_back:
                finish();
                break;
            case R.id.iv_tool_bar_menu:

                break;
            case R.id.iv_play_lyric:
                Intent intentForPlayingMusicActivity = new Intent(this, PlayingMusicActivity.class);
                startActivity(intentForPlayingMusicActivity);
                break;
        }
    }

    private void registerCallback() {
        MyMusicPlayer.registerCallback(mCallback);
    }

    private void unregisterCallback() {
        MyMusicPlayer.unregisterCallback(mCallback);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        findViewById(R.id.iv_tool_bar_back).setOnClickListener(this);
        findViewById(R.id.iv_tool_bar_menu).setOnClickListener(this);
        findViewById(R.id.iv_play_lyric).setOnClickListener(this);
        findViewById(R.id.iv_play_current_lyric).setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.rv_lyrics);
        recyclerView.setAdapter(mLyricAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final Lyric lyric = new Lyric(recyclerView, mLyricAdapter);
        final LinearLayout linearLayout = findViewById(R.id.ll_play_control_lyric);
        linearLayout.setVisibility(android.view.View.GONE);
        final Runnable commend = new Runnable() {
            @Override
            public void run() {
                linearLayout.setVisibility(android.view.View.GONE);
                registerCallback();
            }
        };

        recyclerView.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    linearLayout.setVisibility(android.view.View.VISIBLE);
                    unregisterCallback();
                    ThreadManager.getInstance().cancelPost(commend);
                } else if (action == MotionEvent.ACTION_MOVE) {
                    lyric.updateCurrentLine();
                } else if (action == MotionEvent.ACTION_UP) {
                    ThreadManager.getInstance().postDelay(commend, 1000);
                }
                return false;
            }
        });

        findViewById(R.id.iv_play_current_lyric).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                MyMusicPlayer.seekTo(mLyrics.get(lyric.getCurrentLine()).getStart());
            }
        });
        mCallback = new LyricActivityCallback(mGetLyricPresenter, lyric);
        mCallback.initWidgets(getWindow().getDecorView());
    }
}
