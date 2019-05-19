package com.blogofyb.elf.utils.interfaces;

public interface PlayStyle {
    int FLAG_AUTO = 0;
    int FLAG_USER = 1;

    void playNext(int flag);

    void playPrevious(int flag);

}
