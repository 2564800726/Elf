package com.blogofyb.elf.views.customs;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogofyb.elf.R;
import com.blogofyb.elf.utils.beans.LyricBean;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;

import java.util.List;

public class LyricAdapter extends RecyclerView.Adapter<LyricAdapter.LyricViewHolder> {
    private List<LyricBean> mLyrics;
    private Context mContext;
    private int mHighlightLine;

    public LyricAdapter(List<LyricBean> mLyrics, Context mContext) {
        this.mLyrics = mLyrics;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public LyricViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LyricViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.lyric_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LyricViewHolder lyricViewHolder, int i) {
        if (isHighlightLine(i)) {
            mHighlightLine = i;
            lyricViewHolder.mTextView.setTextColor(mContext.getResources().getColor(R.color.color_lyric));
            lyricViewHolder.mTextView.setText(mLyrics.get(i).getLyric());
        } else {
            lyricViewHolder.mTextView.setTextColor(Color.BLACK);
            lyricViewHolder.mTextView.setText(mLyrics.get(i).getLyric());
        }
    }

    @Override
    public int getItemCount() {
        if (mLyrics == null) {
            return 0;
        } else {
            return mLyrics.size();
        }
    }

    public void refreshData(List<LyricBean> mLyrics) {
        this.mLyrics = mLyrics;
        notifyDataSetChanged();
    }

    private boolean isHighlightLine(int i) {
        int current = MyMusicPlayer.current();
        LyricBean lyric = mLyrics.get(i);
        if (i == mLyrics.size() - 1) {
            return lyric.getStart() < current;
        } else {
            LyricBean lyricAfter = mLyrics.get(i + 1);
            return lyric.getStart() < current && lyricAfter.getStart() > current;
        }
    }

    public int getHighlightLine() {
        return mHighlightLine;
    }

    public void updateHighlightLine() {
        if (mLyrics == null) {
            return;
        }
        for (int i = 0; i < mLyrics.size(); i++) {
            if (isHighlightLine(i)) {
                mHighlightLine = i;
                break;
            }
        }
    }

    static class LyricViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public LyricViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_lyric);
        }
    }
}
