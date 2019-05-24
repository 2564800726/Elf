package com.blogofyb.elf.views.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.blogofyb.elf.R;
import com.blogofyb.tools.pixel.Pixel;

public class Wave extends View {
    private Paint mPaint;
    private int mRadius;
    private int mWaveColor;
    private int mWaveWidth;
    private int pivotX;
    private int pivotY;

    public Wave(Context context) {
        super(context);
        initPaint();
        initAnimation();
    }

    public Wave(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Wave);
        mWaveColor = array.getColor(R.styleable.Wave_waveColor, Color.BLACK);
        mWaveWidth = (int) array.getDimension(R.styleable.Wave_waveWidth, Pixel.dp2px(context, 5));
        array.recycle();
        initPaint();
        initAnimation();
    }

    public Wave(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Wave(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width, height;
        if (widthMode == MeasureSpec.AT_MOST) {
            width = Pixel.dp2px(getContext(), 100);
        } else {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = Pixel.dp2px(getContext(), 100);
        } else {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        pivotX = width / 2;
        pivotY = width / 2;
        mRadius = Math.min(pivotX, pivotY);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(pivotX, pivotY, mRadius, mPaint);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mWaveColor);
        mPaint.setStrokeWidth(mWaveWidth);
    }

    private void initAnimation() {
        Animation scaleAnimation = new ScaleAnimation(1, 1.2f, 1, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.RESTART);
        Animation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation. RESTART);
        Animation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(5000);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.setInterpolator(new LinearInterpolator());
        setAnimation(animationSet);
    }
}
