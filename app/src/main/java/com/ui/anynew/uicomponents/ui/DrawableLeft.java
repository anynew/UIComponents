package com.ui.anynew.uicomponents.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anynew on 2017/1/13.
 */

public class DrawableLeft extends View {

    private Paint mPaint;
    private Paint nPaint;
    private SweepGradient sw;
    private RadialGradient rg;

    public DrawableLeft(Context context) {
        this(context, null);
    }

    public DrawableLeft(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        nPaint = new Paint();
        nPaint.setColor(Color.BLUE);
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#F09964"));
//        mPaint.setStrokeWidth(30);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        sw = new SweepGradient(240, 360, new int[]{Color.parseColor("#008040"),Color.parseColor("#50AF61"),
                Color.parseColor("#879F26"),Color.parseColor("#F5B944"),Color.parseColor("#F09964"),
                Color.parseColor("#F9493C")}, null);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        RectF rectF = new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
//        Log.e("外环", "RectF: " + getPaddingLeft());
        //绘制外层圆环
//        canvas.drawArc(rectF, -195, 210, false, mPaint);
        canvas.drawCircle(240, 360, 200, nPaint);
        canvas.drawCircle(240, 360, 200, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        rg = new RadialGradient(event.getX(), event.getY(), 200,new int[]{Color.parseColor("#008040"),Color.parseColor("#50AF61"),
                Color.parseColor("#879F26"),Color.parseColor("#F5B944"),Color.parseColor("#F09964"),
                Color.parseColor("#F9493C")}, null,
                Shader.TileMode.REPEAT);
        mPaint.setShader(rg);
        postInvalidate();
        return super.onTouchEvent(event);

    }
}
