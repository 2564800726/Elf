package com.blogofyb.elf.views.activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogofyb.elf.R;
import com.blogofyb.elf.utils.beans.MusicBean;
import com.blogofyb.elf.utils.constant.Configuration;
import com.blogofyb.elf.utils.constant.SQLite;
import com.blogofyb.elf.utils.database.MySQLiteOpenHelper;
import com.blogofyb.elf.utils.musicmanager.StarMusic;
import com.blogofyb.elf.utils.musicplayer.MyMusicPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecordActivity extends BasedActivity implements View.OnClickListener {
    private StarMusic mStarMusic;
    private EditText mEditText;
    private TextView mDescribe;
    private TextView mDate;
    private ImageView mMood;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_record);
        mStarMusic = new StarMusic(this);
        initView();
    }

    @Override
    public void onClick(View v) {
        MusicBean music = MyMusicPlayer.getMusics().get(MyMusicPlayer.getCurrentIndex());
        switch (v.getId()) {
            case R.id.tv_finish_record:
                mStarMusic.starMusic(music.getName(), music.getSinger(), music.getId(), mEditText.getText().toString());
                finish();
                break;
            case R.id.tv_cancel_record:
                finish();
                break;
            case R.id.tv_skip_record:
                mStarMusic.starMusic(music.getName(), music.getSinger(), music.getId());
                finish();
                break;
        }
    }

    private void initView() {
        mEditText = findViewById(R.id.et_content_record);
        mDescribe = findViewById(R.id.tv_describe_record);
        mDate = findViewById(R.id.tv_date_record);
        mMood = findViewById(R.id.iv_mood_record);

        findViewById(R.id.tv_finish_record).setOnClickListener(this);
        findViewById(R.id.tv_cancel_record).setOnClickListener(this);
        findViewById(R.id.tv_skip_record).setOnClickListener(this);

        setDescribeAndMood();
        setDate();
        setContent();
    }

    private void setContent() {
        MusicBean music = MyMusicPlayer.getMusics().get(MyMusicPlayer.getCurrentIndex());
        if (mStarMusic.checkStar(music.getId())) {
            SQLiteDatabase database = MySQLiteOpenHelper.getDatabase(this);
            String sql = "SELECT * FROM " + SQLite.TABLE_STAR + " WHERE " + SQLite.COLUMN_ID + "='" + music.getId() + "'";
            Cursor cursor = database.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                mEditText.setText(cursor.getString(cursor.getColumnIndex(SQLite.COLUMN_CONTENT)));
            }
            cursor.close();
        }
    }

    private void setDescribeAndMood() {
        switch (MyMusicPlayer.getMoodCurrent()) {
            case MyMusicPlayer.MOOD_CLAM:
                mDescribe.setText("这是今日份的冷静，写点什么吧");
                mMood.setImageResource(R.drawable.ic_mood_clam);
                break;
            case MyMusicPlayer.MOOD_EXCITING:
                mDescribe.setText("这是今日份的激动，写点什么吧");
                mMood.setImageResource(R.drawable.ic_mood_exciting);
                break;
            case MyMusicPlayer.MOOD_HAPPY:
                mDescribe.setText("这是今日份的开心，写点什么吧");
                mMood.setImageResource(R.drawable.ic_mood_happy);
                break;
            case MyMusicPlayer.MOOD_UNHAPPY:
                mDescribe.setText("这是今日份的不开心，写点什么吧");
                mMood.setImageResource(R.drawable.ic_mood_unhappy);
                break;
        }
    }

    private void setDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        mDate.setText(format.format(new Date(System.currentTimeMillis())));
    }
}
