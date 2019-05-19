package com.blogofyb.elf.views.mood;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blogofyb.elf.R;
import com.blogofyb.elf.models.GetMusicModel;
import com.blogofyb.elf.presenters.MoodSongListPresenter;
import com.blogofyb.elf.utils.beans.MusicBean;
import com.blogofyb.elf.utils.interfaces.View;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;

import java.util.List;

public class MoodManager implements View<MusicBean> {
    private MoodSongListPresenter mMoodSongListPresenter;
    private GetMusicModel mGetMusicModel;
    private LinearLayout mSwitchMood;
    private ImageView mFirst;
    private ImageView mSecond;
    private ImageView mThird;
    private ImageView mCurrent;
    private android.view.View mDivider;
    private boolean mIsHiding = true;
    private int[] mMoodResources = {
            R.drawable.ic_mood_clam,
            R.drawable.ic_mood_exciting,
            R.drawable.ic_mood_unhappy,
            R.drawable.ic_mood_happy
    };
    private String[] mMood = {
            MyMusicPlayer.MOOD_CLAM,
            MyMusicPlayer.MOOD_EXCITING,
            MyMusicPlayer.MOOD_UNHAPPY,
            MyMusicPlayer.MOOD_HAPPY
    };

    public MoodManager(LinearLayout mSwitchMood) {
        mMoodSongListPresenter = new MoodSongListPresenter();
        mGetMusicModel = new GetMusicModel();
        this.mSwitchMood = mSwitchMood;
        mFirst = mSwitchMood.findViewById(R.id.iv_mood_first);
        mSecond = mSwitchMood.findViewById(R.id.src_mood_second);
        mThird = mSwitchMood.findViewById(R.id.iv_mood_third);
        mCurrent = mSwitchMood.findViewById(R.id.iv_mood_current);
        mDivider = mSwitchMood.findViewById(R.id.v_divider_change_mood);
        addOnClickListener();
        hide();
        updateView();
    }


    @Override
    public void show(List<MusicBean> data) {
        if (data != null) {
            MyMusicPlayer.setMusics(data);
            MyMusicPlayer.playMusic(MyMusicPlayer.getCurrentIndex());
            MyMusicPlayer.pauseMusic();
            System.out.println(MyMusicPlayer.getMoodCurrent());
        }
    }

    private void expand() {
        mIsHiding = false;
        mSwitchMood.setBackgroundResource(R.drawable.bg_frame_short);
        mFirst.setVisibility(android.view.View.VISIBLE);
        mSecond.setVisibility(android.view.View.VISIBLE);
        mThird.setVisibility(android.view.View.VISIBLE);
        mDivider.setVisibility(android.view.View.VISIBLE);
    }

    private void hide() {
        mIsHiding = true;
        mSwitchMood.setBackgroundColor(mSwitchMood.getContext().getResources().getColor(R.color.color_none));
        mFirst.setVisibility(android.view.View.GONE);
        mSecond.setVisibility(android.view.View.GONE);
        mThird.setVisibility(android.view.View.GONE);
        mDivider.setVisibility(android.view.View.GONE);
    }

    private void addOnClickListener() {
        mFirst.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                switchResource(0);
            }
        });
        mSecond.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                switchResource(1);
            }
        });
        mThird.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                switchResource(2);
            }
        });
        mCurrent.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (mIsHiding) {
                    expand();
                } else {
                    hide();
                }
            }
        });
    }

    private void updateView() {
        mFirst.setImageResource(mMoodResources[0]);
        mSecond.setImageResource(mMoodResources[1]);
        mThird.setImageResource(mMoodResources[2]);
        mCurrent.setImageResource(mMoodResources[3]);
    }

    private void switchResource(int indexA) {
        int temp = mMoodResources[indexA];
        mMoodResources[indexA] = mMoodResources[3];
        mMoodResources[3] = temp;
        String tempMood = mMood[indexA];
        mMood[indexA] = mMood[3];
        mMood[3] = tempMood;
        updateView();
        hide();
        MyMusicPlayer.setMoodCurrent(mMood[3]);
        mMoodSongListPresenter.showData();
    }

    public void bind() {
        mMoodSongListPresenter.bind(this);
        mMoodSongListPresenter.bind(mGetMusicModel);
        if (MyMusicPlayer.getMusics() == null) {
            mMoodSongListPresenter.showData();
        }
    }

    public void unBind() {
        mMoodSongListPresenter.unBind(this);
        mMoodSongListPresenter.unBind(mGetMusicModel);
    }
}
