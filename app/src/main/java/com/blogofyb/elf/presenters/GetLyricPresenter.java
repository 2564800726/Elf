package com.blogofyb.elf.presenters;

import com.blogofyb.elf.utils.beans.LyricBean;
import com.blogofyb.elf.utils.interfaces.Callback;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;
import com.blogofyb.tools.thread.ThreadManager;

import java.util.ArrayList;
import java.util.List;

public class GetLyricPresenter extends BasedPresenter<LyricBean> {
    private String mLastLoad;

    @Override
    public void showData() {
        String currentMusicId = MyMusicPlayer.getMusics().get(MyMusicPlayer.getCurrentIndex()).getId();
        if (currentMusicId.equals(mLastLoad)) {
            return;
        } else {
            mLastLoad = currentMusicId;
        }
        if (mModel != null) {
            mModel.get(new Callback<LyricBean>() {
                @Override
                public void success(final List<LyricBean> data) {
                    if (mView != null) {
                        ThreadManager.getInstance().post(new Runnable() {
                            @Override
                            public void run() {
                                mView.show(data);
                            }
                        });
                    }
                }

                @Override
                public void failed(Exception e) {
                    final LyricBean lyric = new LyricBean();
                    lyric.setStart(0);
                    lyric.setLyric("歌词加载中...");
                    final List<LyricBean> data = new ArrayList<>();
                    data.add(lyric);
                    if (mView != null) {
                        ThreadManager.getInstance().post(new Runnable() {
                            @Override
                            public void run() {
                                mView.show(data);
                            }
                        });
                    }
                }
            });
        }
    }
}
