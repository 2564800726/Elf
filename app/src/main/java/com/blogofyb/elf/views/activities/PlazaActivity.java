package com.blogofyb.elf.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogofyb.elf.R;
import com.blogofyb.elf.views.mood.MoodManager;

public class PlazaActivity extends BasedActivity implements View.OnClickListener {
    private MoodManager mMoodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_plaza);
        mMoodManager = new MoodManager((LinearLayout) findViewById(R.id.ll_change_mood));
        mMoodManager.bind();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMoodManager.unBind();
    }

    private void initView() {
        findViewById(R.id.iv_playing_plaza).setOnClickListener(this);
        findViewById(R.id.iv_tool_bar_back).setOnClickListener(this);
        findViewById(R.id.iv_tool_bar_menu).setVisibility(View.GONE);
        TextView title = findViewById(R.id.tv_song_name_tool_bar);
        title.setTextColor(getResources().getColor(R.color.color_song_name_main));
        title.setText("一些想说的");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_playing_plaza:
                Intent intent = new Intent(PlazaActivity.this, PlayingMusicActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_tool_bar_back:
                finish();
                break;
        }
    }
}
