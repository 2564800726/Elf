package com.blogofyb.elf.views.lyric;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogofyb.elf.R;
import com.blogofyb.elf.utils.beans.LyricBean;

import java.util.List;

public class LyricAdapter extends RecyclerView.Adapter<LyricAdapter.LyricViewHolder> {
    private List<LyricBean> mLyrics;

    public LyricAdapter(List<LyricBean> mLyrics) {
        this.mLyrics = mLyrics;
    }

    @NonNull
    @Override
    public LyricViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LyricViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.lyric_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LyricViewHolder lyricViewHolder, int i) {
        lyricViewHolder.mTextView.setText(mLyrics.get(i).getLyric());
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

    static class LyricViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public LyricViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_lyric);
        }
    }
}
