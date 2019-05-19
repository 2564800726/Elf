package com.blogofyb.elf.utils.musicmanager;

import android.content.Context;
import android.content.SharedPreferences;

import com.blogofyb.elf.utils.constant.Configuration;

public class LikeMusic {
    private Context mContext;

    public LikeMusic(Context mContext) {
        this.mContext = mContext;
    }

    public boolean checkLike(String songName, String singer) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Configuration.LIKE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(createToken(songName, singer), false);
    }

    public void likeMusic(String songName, String singer) {
        if (checkLike(songName, singer)) {
            likeMusic(songName, singer, false);
        } else {
            likeMusic(songName, singer, true);
        }
    }

    private void likeMusic(String songName, String singer, boolean like) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(Configuration.LIKE, Context.MODE_PRIVATE).edit();
        editor.putBoolean(createToken(songName, singer), like);
        editor.apply();
    }

    public String createToken(String songName, String singer) {
        return songName + "@" + singer;
    }
}
