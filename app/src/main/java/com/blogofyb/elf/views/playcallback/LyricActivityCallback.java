package com.blogofyb.elf.views.playcallback;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogofyb.elf.R;
import com.blogofyb.elf.presenters.GetLyricPresenter;
import com.blogofyb.elf.utils.beans.LyricBean;
import com.blogofyb.elf.utils.beans.MusicBean;
import com.blogofyb.elf.utils.interfaces.PlayCallback;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;

import java.util.List;

public class LyricActivityCallback implements PlayCallback {
    private RecyclerView mLyrics;
    private TextView mSongName;
    private GetLyricPresenter mGetLyricPresenter;

    private List<LyricBean> mLines;

    public LyricActivityCallback(GetLyricPresenter mGetLyricPresenter) {
        this.mGetLyricPresenter = mGetLyricPresenter;
    }

    @Override
    public void updateUI() {
        MusicBean music = MyMusicPlayer.getMusics().get(MyMusicPlayer.getCurrentIndex());
        mSongName.setText(music.getName());
        mGetLyricPresenter.showData();
    }

    @Override
    public void initWidgets(View view) {
        mLyrics = view.findViewById(R.id.rv_lyrics);
        mSongName = view.findViewById(R.id.tv_song_name_tool_bar);
    }

    public void updateLyrics(List<LyricBean> mLines) {
        this.mLines = mLines;
    }
}
