package com.blogofyb.elf.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.blogofyb.elf.R;
import com.blogofyb.elf.models.GetLyricModel;
import com.blogofyb.elf.presenters.GetLyricPresenter;
import com.blogofyb.elf.utils.beans.LyricBean;
import com.blogofyb.elf.utils.interfaces.View;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;
import com.blogofyb.elf.views.lyric.LyricAdapter;
import com.blogofyb.elf.views.mood.MoodManager;
import com.blogofyb.elf.views.playcallback.LyricActivityCallback;

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
        mLyricAdapter = new LyricAdapter(mLyrics);
        mCallback = new LyricActivityCallback(mGetLyricPresenter);
        mCallback.initWidgets(getWindow().getDecorView());
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

    private void initView() {
        findViewById(R.id.iv_tool_bar_back).setOnClickListener(this);
        findViewById(R.id.iv_tool_bar_menu).setOnClickListener(this);
        findViewById(R.id.iv_play_lyric).setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.rv_lyrics);
        recyclerView.setAdapter(mLyricAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
