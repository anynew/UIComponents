package com.ui.anynew.uicomponents.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by anynew on 2016/12/14.
 */

public class TextView extends View {

    private Paint basePaint;

    private Paint tPaint;

    private float baseX = 200;

    private float[] cir_location;

    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float length;

    public TextView(Context context) {
        this(context, null);
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        basePaint = new Paint();
        basePaint.setStyle(Paint.Style.STROKE);
        basePaint.setStrokeWidth(20);
        basePaint.setAntiAlias(true);
        basePaint.setColor(Color.BLUE);

        tPaint = new Paint();
        tPaint.setStyle(Paint.Style.FILL);
        tPaint.setAntiAlias(true);
        tPaint.setColor(Color.RED);

        cir_location = new float[2];

        mPath = new Path();
        mPath.moveTo(250, 250);
        mPath.lineTo(450, 250);
        mPath.lineTo(550, 380);
        mPath.lineTo(450, 510);
        mPath.lineTo(250, 510);
        mPath.lineTo(150, 380);
        mPath.close();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

//        mPath.addCircle(350,350,200, Path.Direction.CW);
        mPathMeasure = new PathMeasure(mPath, false);
        length = mPathMeasure.getLength();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(cir_location[0], cir_location[1], 10, tPaint);

        Log.e("TAG", "cir_location[0]: " + cir_location[0] + " cir_location[1] :" + cir_location[1]);

    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0,length);
        animator.setDuration(3000);
        animator.setRepeatCount(Integer.MAX_VALUE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mPathMeasure.getPosTan(value, cir_location, null);
//                mEffect = new DashPathEffect(new float[]{length, length}, progress * length);
//                mPaint.setPathEffect(mEffect);
                postInvalidate();
                Log.e("TAG", "progress value is " + value);
            }
        });
        animator.start();
    }
}
