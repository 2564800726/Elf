package com.blogofyb.elf.views.customs;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Lyric {
    private RecyclerView mRecyclerView;
    private LyricAdapter mAdapter;
    private int mCenterOffset;
    private LinearLayoutManager mLayoutManager;
    private int mCurrentLine;

    public Lyric(RecyclerView mRecyclerView, LyricAdapter mAdapter) {
        this.mRecyclerView = mRecyclerView;
        this.mAdapter = mAdapter;
        mLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        if (mLayoutManager == null) {
            throw new RuntimeException("need a LinearLayoutManager");
        }
    }

    public void moveToCurrentHighlightLine() {
        mAdapter.notifyDataSetChanged();
        mAdapter.updateHighlightLine();
        updateCurrentLine();
        int first = mLayoutManager.findFirstVisibleItemPosition();
        int last = mLayoutManager.findLastVisibleItemPosition();
        mCenterOffset = (last - first) / 2;
        int position;
        if (mCurrentLine > mAdapter.getHighlightLine()) {
            position = mAdapter.getHighlightLine() - mCenterOffset;
        } else {
            position = mAdapter.getHighlightLine() + mCenterOffset;
        }
        if (position < 0) {
            position = 0;
        }
        mLayoutManager.smoothScrollToPosition(mRecyclerView, null, position);
    }

    public void updateCurrentLine() {
        mCurrentLine = mLayoutManager.findFirstVisibleItemPosition() + mCenterOffset;
    }

    public int getCurrentLine() {
        return mCurrentLine;
    }
}
