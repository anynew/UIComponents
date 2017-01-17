package com.ui.anynew.uicomponents.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by anynew on 2017/1/11.
 */

public class CircleColorBar extends View {
    private int width; // view的宽度

    private int height;  // view的高度

    private int defaultSize = 300; //默认大小

    private Paint paint;

    private float progress;
    private LinearGradient linearGradient;

    public CircleColorBar(Context context) {
        this(context, null);
    }

    public CircleColorBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleColorBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.GREEN);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

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
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        linearGradient = new LinearGradient(0,0,getMeasuredWidth(),0,new int[]{Color.parseColor("#008040"),Color.parseColor("#50AF61"),
                Color.parseColor("#879F26"),Color.parseColor("#F5B944"),Color.parseColor("#F09964"),
                Color.parseColor("#F9493C")},null, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint);
    }

    public void startAnim() {
        ValueAnimator mAngleAnim = ValueAnimator.ofFloat(20, 250);
        mAngleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAngleAnim.setDuration(5 * 1000);
        mAngleAnim.setRepeatCount(Integer.MAX_VALUE);
        mAngleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                progress = (float) valueAnimator.getAnimatedValue();
//                Log.e("progress", "onAnimationUpdate: "+ progress);
                postInvalidate();
            }
        });
        mAngleAnim.start();

    }
}
