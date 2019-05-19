package com.blogofyb.elf.views.playcallback;

import android.view.View;
import android.widget.TextView;

import com.blogofyb.elf.R;
import com.blogofyb.elf.utils.beans.MusicBean;
import com.blogofyb.elf.utils.interfaces.PlayCallback;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;
import com.blogofyb.tools.img.ImageLoader;
import com.blogofyb.tools.img.cache.LruAndDiskLruCache;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityCallback implements PlayCallback {
    private CircleImageView mCover;
    private TextView mSongName;
    private TextView mSinger;

    private ImageLoader mImageLoader;

    @Override
    public void updateUI() {
        MusicBean music = MyMusicPlayer.getMusics().get(MyMusicPlayer.getCurrentIndex());
        mImageLoader.load(music.getCover()).withTag(music.getCover()).into(mCover);
        mSongName.setText(music.getName());
        mSinger.setText(music.getSinger());
    }

    @Override
    public void initWidgets(View view) {
        mCover = view.findViewById(R.id.civ_cover_main);
        mSongName = view.findViewById(R.id.tv_song_name_main);
        mSinger = view.findViewById(R.id.tv_singer_name_main);
        mCover = view.findViewById(R.id.civ_cover_main);

        ImageLoader.Options options = new ImageLoader.Options();
        options.context(view.getContext())
                .cache(new LruAndDiskLruCache(view.getContext(), "cover"))
                .scaleImage(true)
                .place(R.drawable.ic_default_bottom_music_icon)
                .failed(R.drawable.ic_default_bottom_music_icon);
        mImageLoader = new ImageLoader();
        mImageLoader.apply(options);
    }
}
