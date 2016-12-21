package com.ui.anynew.uicomponents.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by anynew on 2016/12/19.
 */

public class HeartView extends View {

    private static final int PATH_WIDTH = 2;
    // 起始点
    private static final int[] START_POINT = new int[]{300, 270};
    // 爱心下端点
    private static final int[] BOTTOM_POINT = new int[]{300, 400};
    // 左侧控制点
    private static final int[] LEFT_CONTROL_POINT = new int[]{450, 200};
    // 右侧控制点
    private static final int[] RIGHT_CONTROL_POINT = new int[]{150, 200};

    private float[] mCurrentPosition = new float[2];

    private Paint mPaint;

    private Path mPath;
    private PathMeasure mPathMeasure;
    private Paint nPaint;

    public HeartView(Context context) {
        this(context, null);
    }

    public HeartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(PATH_WIDTH);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);

        nPaint = new Paint();
        nPaint.setStyle(Paint.Style.STROKE);
        nPaint.setStrokeWidth(8);
        nPaint.setColor(Color.BLUE);

        mPath = new Path();
        mPath.moveTo(START_POINT[0], START_POINT[1]);
        mPath.quadTo(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], BOTTOM_POINT[0],
                BOTTOM_POINT[1]);
        mPath.quadTo(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], START_POINT[0], START_POINT[1]);

        mPathMeasure = new PathMeasure(mPath,false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], 5, mPaint);
        canvas.drawCircle(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], 5, mPaint);

        canvas.drawCircle(mCurrentPosition[0],mCurrentPosition[1],8,nPaint);
    }
    public void startPathAnim(){

        ValueAnimator animator = ValueAnimator.ofFloat(0,mPathMeasure.getLength());
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(3000);
        animator.setRepeatCount(Integer.MAX_VALUE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                mPathMeasure.getPosTan(value,mCurrentPosition,null);

                postInvalidate();
            }
        });
        animator.start();
    }
}
