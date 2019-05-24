package com.blogofyb.elf.views.playcallback;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blogofyb.elf.R;
import com.blogofyb.elf.presenters.GetLyricPresenter;
import com.blogofyb.elf.utils.beans.LyricBean;
import com.blogofyb.elf.utils.beans.MusicBean;
import com.blogofyb.elf.utils.interfaces.PlayCallback;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;
import com.blogofyb.elf.views.customs.Lyric;
import com.blogofyb.elf.views.customs.LyricAdapter;

import java.util.List;

public class LyricActivityCallback implements PlayCallback {
    private TextView mSongName;
    private GetLyricPresenter mGetLyricPresenter;
    private Lyric mLyric;

    public LyricActivityCallback(GetLyricPresenter mGetLyricPresenter, Lyric mLyric) {
        this.mGetLyricPresenter = mGetLyricPresenter;
        this.mLyric = mLyric;
    }

    @Override
    public void updateUI() {
        MusicBean music = MyMusicPlayer.getMusics().get(MyMusicPlayer.getCurrentIndex());
        mSongName.setText(music.getName());
        mGetLyricPresenter.showData();
        mLyric.moveToCurrentHighlightLine();
    }

    @Override
    public void initWidgets(View view) {
        mSongName = view.findViewById(R.id.tv_song_name_tool_bar);
    }
}
