package com.blogofyb.elf.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.blogofyb.elf.R;

public class CommentActivity extends BasedActivity implements View.OnClickListener {
    private EditText mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comment);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_comment:
                finish();
                break;
            case R.id.btn_publish_comment:
                publishComment();
                break;
        }
    }

    private void initView() {
        findViewById(R.id.iv_close_comment).setOnClickListener(this);
        findViewById(R.id.btn_publish_comment).setOnClickListener(this);
        mContent = findViewById(R.id.et_input_comment);
    }

    private void publishComment() {

    }
}
