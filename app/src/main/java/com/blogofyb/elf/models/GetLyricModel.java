package com.blogofyb.elf.models;

import com.blogofyb.elf.utils.beans.LyricBean;
import com.blogofyb.elf.utils.beans.LyricResponse;
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

public class GetLyricModel extends BasedModel<LyricBean> {
    private Callback<LyricBean> mCallback;

    @Override
    public void get(final Callback<LyricBean> callback) {
        mData = new ArrayList<>();
        mCallback = callback;
        HttpClient client = new HttpClient();
        Request request = new Request.Builder().url(API.SONG_LYRIC)
                .parameters("id", MyMusicPlayer.getMusics().get(MyMusicPlayer.getCurrentIndex()).getId())
                .header("Content-Type", API.SONG_INFORMATION_CONTENT_TYPE)
                .method("POST")
                .listener(new HttpCallback() {
                    @Override
                    public void onSuccess(Response response) {
                        MyJson json = new MyJson();
                        LyricResponse lyricResponse = json.fromJson(response.responseBody(), LyricResponse.class);
                        parseLyric(lyricResponse.getLrc().getLyric());
                    }
                })
                .build();
        ThreadManager.getInstance().execute(client.newCall(request));
    }

    private void parseLyric(String lyric) {
        String[] lines = lyric.split("\n");
        for (String line : lines) {
            LyricBean lyricBean;
            try {
                lyricBean = new LyricBean();
                lyricBean.setStart(transform(line.split("\\[")[1].split("]")[0]));
                lyricBean.setLyric(line.split("]")[1]);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                e.printStackTrace();
                continue;
            }
            mData.add(lyricBean);
        }
        mCallback.success(mData);
    }

    private int transform(String time) {
        int minute ;
        int seconds;
        int milliSeconds;
        minute = Integer.parseInt(time.split(":")[0]);
        seconds = Integer.parseInt(time.split(":")[1].split("\\.")[0]);
        milliSeconds = Integer.parseInt(time.split("\\.")[1]);
        return milliSeconds + 1000 * seconds + 60000 * minute;
    }
}
