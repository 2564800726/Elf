package com.blogofyb.elf.models;

import android.util.Log;

import com.blogofyb.elf.utils.beans.MoodSongListResponse;
import com.blogofyb.elf.utils.beans.MusicBean;
import com.blogofyb.elf.utils.beans.SongListInformationResponse;
import com.blogofyb.elf.utils.constant.API;
import com.blogofyb.elf.utils.interfaces.Callback;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;
import com.blogofyb.tools.http.HttpClient;
import com.blogofyb.tools.http.Request;
import com.blogofyb.tools.http.Response;
import com.blogofyb.tools.http.interfaces.HttpCallback;
import com.blogofyb.tools.json.MyJson;
import com.blogofyb.tools.thread.ThreadManager;

import java.util.ArrayList;
import java.util.List;

public class GetMusicModel extends BasedModel<MusicBean> {
    private Callback mCallback;

    @Override
    public void get(Callback callback) {
        mData = new ArrayList<>();
        mCallback = callback;
        getMoodSongListId();
    }

    private void getMoodSongListId() {
        HttpClient client = new HttpClient();
        Request request = new Request.Builder().url(API.MOOD_SONG_LIST)
                .parameters("type", MyMusicPlayer.getMoodCurrent())
                .method("GET")
                .listener(new HttpCallback() {
                    @Override
                    public void onSuccess(Response response) {
                        MyJson json = new MyJson();
                        MoodSongListResponse moodSongList = json.fromJson(response.responseBody(),
                                MoodSongListResponse.class);
                        getSongList(moodSongList.getData().getId());
                    }
                })
                .build();
        ThreadManager.getInstance().execute(client.newCall(request));
    }

    private void getSongList(long songListId) {
        HttpClient client = new HttpClient();
        Request request = new Request.Builder().url(API.SONG_LIST_INFORMATION)
                .method("POST")
                .parameters("id", String.valueOf(songListId))
                .header("Content-Type", API.SONG_INFORMATION_CONTENT_TYPE)
                .listener(new HttpCallback() {
                    @Override
                    public void onSuccess(Response response) {
                        MyJson json = new MyJson();
                        SongListInformationResponse songListInformation = json.fromJson(response.responseBody(),
                                SongListInformationResponse.class);
                        addMusic(songListInformation.getPlaylist().getTracks());
                    }
                })
                .build();
        ThreadManager.getInstance().execute(client.newCall(request));
    }

    private void addMusic(List<SongListInformationResponse.PlaylistBean.TracksBean> list) {
        for (SongListInformationResponse.PlaylistBean.TracksBean music : list) {
            if (music == null) {
                continue;
            }
            MusicBean bean = new MusicBean();
            bean.setName(music.getName());
            bean.setAbsolutePath(API.getMp3Address(String.valueOf(music.getId())));
            bean.setCover(music.getAl().getPicUrl());
            bean.setSinger(getAuthorName(music.getAr()));
            bean.setId(String.valueOf(music.getId()));
            mData.add(bean);
        }
        ThreadManager.getInstance().post(new Runnable() {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                mCallback.success(mData);
            }
        });
    }

    private String getAuthorName(List<SongListInformationResponse.PlaylistBean.TracksBean.ArBean> authors) {
        StringBuilder builder = new StringBuilder();
        for (SongListInformationResponse.PlaylistBean.TracksBean.ArBean author : authors) {
            builder.append(author.getName()).append("/");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
