package com.blogofyb.elf.views.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blogofyb.elf.R;
import com.blogofyb.tools.thread.ThreadManager;

import java.io.IOException;
import java.util.TimerTask;

public class SplashActivity extends BasedActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);
        ImageView imageView = findViewById(R.id.iv_welcome);
        try {
            imageView.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("bg_welcome.jpg")));
            ThreadManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ThreadManager.getInstance().post(new Runnable() {
                        @Override
                        public void run() {
                            startMainActivity();
                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            startMainActivity();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
