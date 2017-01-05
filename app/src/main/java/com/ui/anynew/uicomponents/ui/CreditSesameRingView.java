package com.ui.anynew.uicomponents.ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ViewAnimator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anynew on 2016/12/9.
 */

public class CreditSesameRingView extends View {

    private int radius; //半径

    private int defaultSize; //默认大小

    private int width; // view的宽度

    private int height;  // view的高度

    private Paint wPaint; // 外层圆环画笔

    private Paint wwPaint; // 外层圆环进度画笔

    private Paint nPaint; // 内层圆环画笔

    private Paint ltPaint; // 小刻度画笔

    private Paint bgPaint; // 大刻度画笔


    private Paint ztPaint;//刻度等级字体画笔

    private Paint btPaint;//BETA画笔

    private Paint szPaint;//675画笔

    private Paint djPaint;//信用等级画笔

    private Paint pgPaint;//评估时间画笔

    // 最小数字
    private int mMinNum = 350;
    // 最大数字
    private int mMaxNum = 950;
    //信用等级
    private String sesameLevel = "信用优秀";
    //总进度
    private float mTotalAngle = 210f;
    //评估时间
    private String evaluationTime = "2016.09.23";

    String[] str = {"350", "较差", "550", "中等", "600", "良好", "650", "优秀", "700", "极好", "950"};

    //数字变化进度
    private float progress = 0;

    //小圆点画笔
    private Paint  mBitmapPaint;

    //小圆点的实际位置
    private float[]  cir_location ;

    public CreditSesameRingView(Context context) {
        this(context, null);
    }

    public CreditSesameRingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
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

        wwPaint=new Paint();
        wwPaint.setAntiAlias(true);
        wwPaint.setColor(Color.WHITE);
        wwPaint.setStyle(Paint.Style.STROKE);
        wwPaint.setStrokeWidth(5);

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

        ztPaint = new Paint();
        ztPaint.setAntiAlias(true);
        ztPaint.setColor(Color.WHITE);
        ztPaint.setTextSize(25);
        ztPaint.setAlpha(90);


        btPaint = new Paint();
        btPaint.setAntiAlias(true);
        btPaint.setColor(Color.WHITE);
        btPaint.setTextSize(30);
        btPaint.setAlpha(100);


        szPaint = new Paint();
        szPaint.setAntiAlias(true);
        szPaint.setColor(Color.WHITE);
        szPaint.setTextSize(110);

        djPaint = new Paint();
        djPaint.setAntiAlias(true);
        djPaint.setColor(Color.WHITE);
        djPaint.setTextSize(50);
        djPaint.setAlpha(200);


        pgPaint = new Paint();
        pgPaint.setAntiAlias(true);
        pgPaint.setColor(Color.WHITE);
        pgPaint.setTextSize(30);
        pgPaint.setAlpha(100);

        mBitmapPaint = new Paint();
        mBitmapPaint.setStyle(Paint.Style.FILL);
        mBitmapPaint.setColor(Color.WHITE);
        mBitmapPaint.setAntiAlias(true);

        cir_location = new float[2];
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
        /***
         *
         * 绘制外环和内环
         *
         */
        //将要绘制的区域
        RectF rectF = new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
//        Log.e("外环", "RectF: " + getPaddingLeft());
        //绘制外层圆环
        canvas.drawArc(rectF, -195, 210, false, wPaint);     // sweep area 180 + abs(-195) * 2

        //内环间距
        float distance = 30;
        RectF rectnF = new RectF(getPaddingLeft() + distance, getPaddingTop() + distance, getWidth() - getPaddingRight() - distance, getHeight() - getPaddingBottom() - distance);
//        Log.e("内环", "rectnF: " + getPaddingLeft());
        //绘制内环
        canvas.drawArc(rectnF, -195, 210, false, nPaint);


        /***
         *
         *  绘制内环刻度
         *
         */
        canvas.save();
        canvas.rotate(-105, radius, radius); //旋转的度数 105  =  (195 -180)+90
        int startDst = (int) (getPaddingLeft() + distance - nPaint.getStrokeWidth() / 2);

        int endDst = (int) (startDst + nPaint.getStrokeWidth());
//        Log.e("TAG", "startDst: " + startDst + " getPaddingLeft: " + getPaddingLeft() + " distance: " + distance + " nPaint.getStrokeWidth(): " + nPaint.getStrokeWidth());
        int endDsts = (int) (startDst + nPaint.getStrokeWidth() + 5);
        canvas.drawLine(radius, startDst, radius, endDst, ltPaint);
        for (int i = 0; i < 31; i++) {
            canvas.drawLine(radius, startDst, radius, endDst, ltPaint);
            canvas.rotate(7, radius, radius);
        }
        canvas.restore();
        canvas.save();
        canvas.rotate(-105, radius, radius); //旋转的度数 105  =  (195 -180)+90
        for (int i = 0; i < 6; i++) {
            canvas.drawLine(radius, startDst, radius, endDsts, bgPaint);
            canvas.rotate(42, radius, radius);
        }
        canvas.restore();

        /**
         *
         * 绘制内环各刻度等级文本
         *
         * */
        canvas.save();
        canvas.rotate(-105, radius, radius);
        float dst = 40;
        for (int i = 0; i < str.length; i++) {
            float textLen = ztPaint.measureText(str[i]);
            canvas.drawText(str[i], radius - textLen / 2, endDst + dst, ztPaint);
            canvas.rotate(21, radius, radius);
        }
        canvas.restore();
        String beta = "BETA";
        float betaLen = btPaint.measureText(beta);
        canvas.drawText(beta, radius - betaLen / 2, dst * 5, btPaint);

        float numLen = szPaint.measureText(String.valueOf(mMinNum));
        canvas.drawText(String.valueOf(mMinNum), radius - numLen / 2, dst * 8, szPaint);

        float djLen = djPaint.measureText(sesameLevel);
        canvas.drawText(sesameLevel, radius - djLen / 2, dst * 10, djPaint);

        float evaLen = pgPaint.measureText(evaluationTime);
        canvas.drawText(evaluationTime, radius - evaLen / 2, dst * 11 + 10, pgPaint);

        /**
         * 绘制路径曲线
         */
        canvas.drawArc(rectF,-195,progress,false,wwPaint);

        //绘制外环小圆点
        Path path = new Path();
        path.addArc(rectF, -195, progress);
        PathMeasure pathMeasure = new PathMeasure(path, false);
        pathMeasure.getPosTan(pathMeasure.getLength() * 1, cir_location, null);
        mBitmapPaint.setColor(Color.GREEN);
        canvas.drawCircle(cir_location[0], cir_location[1], 8, mBitmapPaint);
        Log.e("Tag", "cir_location:[0] "+ cir_location[0] + " cir_location[1] :" + cir_location[1] );
    }

    // 设置信用等级350~950之间
    public void setSesameValues(int values) {

        if (values <= 350) {
            mMaxNum = values;
            mTotalAngle = 0f;
            sesameLevel = "信用较差";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 550) {
            mMaxNum = values;
            mTotalAngle = (values - 350) * 80 / 400f + 2;
            sesameLevel = "信用较差";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 700) {
            mMaxNum = values;
            if (values > 550 && values <= 600) {
                sesameLevel = "信用中等";
            } else if (values > 600 && values <= 650) {
                sesameLevel = "信用良好";
            } else {
                sesameLevel = "信用优秀";
            }
            mTotalAngle = (values - 550) * 120 / 150f + 47;
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 950) {
            mMaxNum = values;
            mTotalAngle = (values - 700) * 40 / 250f + 170;
            sesameLevel = "信用极好";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else {
            mTotalAngle = 210f;
        }
        startAnim();

    }

    //数字动画
    public void startAnim() {

        ValueAnimator mAngleAnim = ValueAnimator.ofFloat(progress, mTotalAngle);
        mAngleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAngleAnim.setDuration(3500);
        mAngleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                progress = (float) valueAnimator.getAnimatedValue();
//                Log.e("progress", "onAnimationUpdate: "+ progress);
                postInvalidate();
            }
        });
        mAngleAnim.start();

        ValueAnimator mNumAnim = ValueAnimator.ofInt(mMinNum, mMaxNum);
        mNumAnim.setDuration(3500);
        mNumAnim.setInterpolator(new DecelerateInterpolator());
        mNumAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mMinNum = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mNumAnim.start();
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd");
        return format.format(new Date());
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
