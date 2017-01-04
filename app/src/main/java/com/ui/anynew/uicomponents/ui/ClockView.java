package com.ui.anynew.uicomponents.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.ui.anynew.uicomponents.R;

/**
 * Created by anynew on 2017/1/4.
 */

public class ClockView extends View {
    //圆盘的颜色
    private Paint mCirclePaint;
    //大刻度画笔
    private Paint mScalePaint;
    //小刻度画笔
    private Paint mLscalePaint;
    //指针画笔
    private Paint mPointerPaint;
    //圆盘颜色
    private int mCircleColor;

    private int mCircleWidth;
    //小刻度宽度
    private int mLScaleWidth;

    private int mScaleColor;

    private int mScaleWidth;

    private int width, height;
    //指针颜色
    private int pointColor;

    private int defaultSize = 150; //默认大小

    private int radius; //半径

    private float sacleLength;
    private Runnable runnable;
    private Handler mHandler;
    private Path path;
    private float progress = 0;
    private float[]  cir_location ;
    private PathMeasure pathMeasure;


    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ClockView, defStyleAttr, 0);

        mCircleColor = ta.getColor(R.styleable.ClockView_CircleColor, Color.CYAN);
        mCircleWidth = ta.getDimensionPixelSize(R.styleable.ClockView_CircleWidth, 10);
        mScaleColor = ta.getColor(R.styleable.ClockView_ScaleColor, Color.WHITE);
        mScaleWidth = ta.getDimensionPixelSize(R.styleable.ClockView_ScaleWidth, 15);
        sacleLength = ta.getDimensionPixelSize(R.styleable.ClockView_ScaleLength, 10);
        pointColor = ta.getColor(R.styleable.ClockView_PointColor, Color.parseColor("#CC7832"));
        ta.recycle();

        mCirclePaint = new Paint();
        mScalePaint = new Paint();
        mPointerPaint = new Paint();
        mLscalePaint = new Paint();

        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mCircleWidth);

        mScalePaint.setAntiAlias(true);
        mScalePaint.setColor(mScaleColor);
        mScalePaint.setStyle(Paint.Style.STROKE);
        mScalePaint.setStrokeWidth(mScaleWidth);

        mLscalePaint.setAntiAlias(true);
        mLscalePaint.setColor(mScaleColor);
        mLscalePaint.setStyle(Paint.Style.STROKE);
        mLscalePaint.setStrokeWidth(2);

        mPointerPaint.setAntiAlias(true);
        mPointerPaint.setColor(pointColor);
        mPointerPaint.setStyle(Paint.Style.STROKE);
        mPointerPaint.setStrokeWidth(3);

        mHandler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                //线程中刷新界面
                mHandler.postDelayed(this, 1000);
                postInvalidate();
                Log.e("Tag","run is called");

            }
        };

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int wMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(widthMeasureSpec);
        if (wMode == MeasureSpec.EXACTLY) {
            width = wSize;
        } else {
            width = Math.min(wSize, defaultSize);
        }
        if (hMode == MeasureSpec.EXACTLY) {
            height = hSize;
        } else {
            height = Math.min(hSize, defaultSize);
        }
        radius = width / 2;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        //确定表盘区域
        RectF rectF = new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        canvas.drawArc(rectF, 0, 360, false, mCirclePaint);
        //刻度
        canvas.save();
        for (int i = 0; i < 12; i++) {
            canvas.drawLine(radius, getPaddingTop(), radius, getPaddingTop() + sacleLength, mScalePaint);
            canvas.rotate(30, radius, radius);
        }
        canvas.restore();
        canvas.save();
        //绘制小刻度
        for (int i = 0; i < 60; i++) {
            canvas.drawLine(radius, getPaddingTop(), radius, getPaddingTop() + sacleLength - 10, mLscalePaint);
            canvas.rotate(6, radius, radius);
        } //
        canvas.restore();
        canvas.save();

        path = new Path();
        path.moveTo(radius,radius);
        path.addArc(rectF,0,progress);
        pathMeasure = new PathMeasure(path,false);
        pathMeasure.getPosTan(pathMeasure.getLength(),cir_location,null);
        //绘制刻度笔
        canvas.drawLine(radius, radius, radius + progress, getPaddingTop()+30 -progress, mPointerPaint);
//        canvas.rotate(progress,radius,radius);
    }

    public void startAnim(){

        ValueAnimator mAngleAnim = ValueAnimator.ofFloat(progress,360);
        mAngleAnim.setInterpolator(new LinearInterpolator());
        mAngleAnim.setDuration(60 * 1000);
        mAngleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                postInvalidate();
                Log.e("progress", "onAnimationUpdate: "+ progress);
            }
        });
        mAngleAnim.start();
    }


}
