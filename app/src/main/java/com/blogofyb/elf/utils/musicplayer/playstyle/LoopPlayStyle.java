package com.blogofyb.elf.utils.musicplayer.playstyle;

import com.blogofyb.elf.utils.interfaces.PlayStyle;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;

public class LoopPlayStyle implements PlayStyle {

    @Override
    public void playNext(int flag) {
        int index = MyMusicPlayer.getCurrentIndex();
        if (MyMusicPlayer.getCurrentIndex() == MyMusicPlayer.getMusics().size() - 1) {
            index = 0;
        } else {
            index += 1;
        }
        MyMusicPlayer.playMusic(index);
    }

    @Override
    public void playPrevious(int flag) {
        int index = MyMusicPlayer.getCurrentIndex();
        if (MyMusicPlayer.getCurrentIndex() == 0) {
            index = MyMusicPlayer.getMusics().size() - 1;
        } else {
            index -= 1;
        }
        MyMusicPlayer.playMusic(index);
    }
}
