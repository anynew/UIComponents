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

    private String string = "nihao,glass";

    private float[] cir_location;

    private float progress;

    private float startX = 0, startY = 0;
    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float length;
    private DashPathEffect mEffect;
    private Path nPath;

    public TextView(Context context) {
        this(context, null);
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        basePaint = new Paint();
        basePaint.setStyle(Paint.Style.STROKE);
        basePaint.setStrokeWidth(5);
        basePaint.setAntiAlias(true);
        basePaint.setColor(Color.BLUE);

        tPaint = new Paint();
        tPaint.setStyle(Paint.Style.FILL);
        tPaint.setAntiAlias(true);
        tPaint.setColor(Color.RED);

        cir_location = new float[2];


       /* mPath.moveTo(250, 250);
        mPath.lineTo(450, 250);
        mPath.lineTo(550, 380);
        mPath.lineTo(450, 510);
        mPath.lineTo(250, 510);
        mPath.lineTo(150, 380);
        mPath.close();*/

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath = new Path();
        RectF rectF = new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        mPath.addOval(rectF, Path.Direction.CCW);
        mPathMeasure = new PathMeasure(mPath, false);
        length = mPathMeasure.getLength();
        canvas.drawPath(mPath, mPaint);
     /*   Path path = new Path();
        path.moveTo(300, 300);
        path.lineTo(300 + progress,300+ progress);

        canvas.drawPath(path, basePaint);*/
        PathMeasure pathMeasure = new PathMeasure(mPath, false);
        pathMeasure.getPosTan(pathMeasure.getLength(), cir_location, null);
        canvas.drawCircle(cir_location[0], cir_location[1], 8, tPaint);

        Log.e("TAG", "cir_location[0]: " + cir_location[0] + " cir_location[1] :" + cir_location[1]);

    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                mEffect = new DashPathEffect(new float[]{length, length}, progress * length);
                mPaint.setPathEffect(mEffect);
                postInvalidate();
                Log.e("TAG", "progress value is " + progress);
            }
        });
        animator.start();
    }
}
