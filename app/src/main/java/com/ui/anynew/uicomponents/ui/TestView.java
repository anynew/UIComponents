package com.ui.anynew.uicomponents.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;

/**
 * Created by anynew on 2016/12/13.
 */

public class TestView extends View {
    private int width; // view的宽度

    private int height;  // view的高度

    private int defaultSize = 150; //默认大小



    int rad;

    private Paint p1;

    private Paint p2;

    private Paint p3;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p1 = new Paint();
        p1.setStrokeWidth(40);
        p1.setColor(Color.CYAN);

        p2 = new Paint();
        p2.setColor(Color.BLUE);

        p3 = new Paint();
        p3.setColor(Color.RED);
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
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int distance = 30;
        canvas.drawLine(30,140,150,140,p1);
        canvas.drawLine(30,50+distance,150,50+distance,p2);
        float startY = 140 - p1.getStrokeWidth() /2;
        float endY = 140 + p1.getStrokeWidth();
        canvas.drawLine(30,startY,30,endY,p3);
    }
}
