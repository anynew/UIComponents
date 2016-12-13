package com.ui.anynew.uicomponents.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by anynew on 2016/12/9.
 */

public class CreditSesameRingView extends View {

    private int radius; //半径

    private int defaultSize; //默认大小

    private int width; // view的宽度

    private int height;  // view的高度

    private Paint wPaint; // 外层圆环画笔

    private Paint nPaint; // 内层圆环画笔

    private Paint ltPaint; // 小刻度画笔

    private Paint bgPaint; // 大刻度画笔

    public CreditSesameRingView(Context context) {
        this(context,null);
    }

    public CreditSesameRingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CreditSesameRingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        defaultSize = dp2px(defaultSize);
        initPaint();
    }

    /**
     * 初始化各种画笔
     */
    private void initPaint() {
        wPaint = new Paint();
        wPaint.setAntiAlias(true);
        wPaint.setColor(Color.WHITE);
        wPaint.setStyle(Paint.Style.STROKE);
        wPaint.setAlpha(50);
        wPaint.setStrokeWidth(10);

        nPaint = new Paint();
        nPaint.setAntiAlias(true);
        nPaint.setColor(Color.WHITE);
        nPaint.setStyle(Paint.Style.STROKE);
        nPaint.setAlpha(50);
        nPaint.setStrokeWidth(30);

        ltPaint = new Paint();
        ltPaint.setAntiAlias(true);
        ltPaint.setColor(Color.WHITE);
        ltPaint.setAlpha(100);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(2);
        bgPaint.setColor(Color.WHITE);
        bgPaint.setAlpha(130);
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
        radius = width / 2;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将要绘制的区域
        RectF rectF = new RectF(getPaddingLeft(),getPaddingTop(),getWidth() - getPaddingRight(),getHeight() - getPaddingBottom());
        Log.e("外环", "RectF: "+ getPaddingLeft());
        //绘制外层圆环
        canvas.drawArc(rectF,-195,210,false,wPaint);     // sweep area 180 + abs(-195) * 2

        //内环间距
        float distance = 30;
        RectF rectnF = new RectF(getPaddingLeft()+distance,getPaddingTop()+distance,getWidth() - getPaddingRight()-distance,getHeight() - getPaddingBottom()-distance);
        Log.e("内环", "rectnF: "+ getPaddingLeft());
        //绘制内环
        canvas.drawArc(rectnF, -195,210,false,nPaint);


        //绘制内环刻度
        canvas.save();
        canvas.rotate(-105,radius,radius); //旋转的度数 105  =  (195 -180)+90
        int startDst = (int)(getPaddingLeft() + distance - nPaint.getStrokeWidth() / 2  );

        int endDst = (int) (startDst + nPaint.getStrokeWidth());
        Log.e("TAG", "startDst: "+ startDst + " getPaddingLeft: "+getPaddingLeft()+" distance: "+distance+" nPaint.getStrokeWidth(): "+nPaint.getStrokeWidth());
        int endDsts = (int) (startDst + nPaint.getStrokeWidth()+5);
        canvas.drawLine(radius,startDst,radius,endDst,ltPaint);
        for (int i = 0; i < 31; i++) {
            canvas.drawLine(radius,startDst,radius,endDst,ltPaint);
            Log.e("TAG", "drawLine: "+ radius +" startDst: "+startDst);
            canvas.rotate(7,radius,radius);
        }
        canvas.restore();
        canvas.save();
        canvas.rotate(-105,radius,radius); //旋转的度数 105  =  (195 -180)+90
        for (int i = 0; i < 6; i++) {
            canvas.drawLine(radius,startDst,radius,endDsts,bgPaint);
            canvas.rotate(42,radius,radius);
        }
        canvas.restore();
    }

    /**
     * dp转px
     *
     * @param values
     * @return
     */
    public int dp2px(int values) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }
}
