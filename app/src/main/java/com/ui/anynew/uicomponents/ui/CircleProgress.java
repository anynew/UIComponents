package com.ui.anynew.uicomponents.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.github.lzyzsd.randomcolor.RandomColor;
import com.ui.anynew.uicomponents.R;

/**
 * Created by anynew on 2016/12/8.
 */

public class CircleProgress extends View {
    private  final String TAG = this.getClass().getSimpleName();
    private Context context;
    private Rect rect;
    private int progressColor;
    private int progressBackColor;
    private int progressWidth;
    private int textSize;
    private int textColor;
    private String textContent = "0%";//字体内容

    private Paint textPaint;
    private Paint paint;

    private Paint.FontMetrics fm;
    private float viewWidth, viewHeight;//View的宽高

    private float progress = 1.0f ;
    private float endProgress ;

    private RandomColor randomColor;


    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PolyView, defStyleAttr, 0);
        progressColor = ta.getColor(R.styleable.CircleProgress_progressColor, Color.GREEN);
        progressBackColor = ta.getColor(R.styleable.CircleProgress_progressBackColor, Color.WHITE);
        progressWidth = ta.getInt(R.styleable.CircleProgress_progressWidth, 20);
        textSize = ta.getDimensionPixelSize(R.styleable.CircleProgress_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
        textColor = ta.getColor(R.styleable.CircleProgress_textColor, Color.BLACK);
        ta.recycle();

        //初始化绘制进度条的画笔
        paint = new Paint();
        paint.setAntiAlias(true);

        //初始化绘制文字的画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        rect = new Rect();
        randomColor = new RandomColor();
    }

    /**
     * @param context
     */
    private float textX;

    /**
     * @param context
     */
    private float textY;

    /**
     * 定位文本绘制的位置
     */
    private void setTextLocation() {
        fm = textPaint.getFontMetrics();
        textPaint.getTextBounds(textContent, 0, textContent.length(), rect);
        float numTextWidth = rect.width();
        float textCenterVerticalBaselineY = viewHeight / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
        textX = viewWidth / 2 - numTextWidth / 2;
        textY = textCenterVerticalBaselineY;
    }

    Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (progress <= 360){
                progress += 1;
                textContent =(int) (progress / 3.6) + "%";
                if (progress == endProgress){
                    return;
                }
            }
            postInvalidate();
            Log.e("progress = ",progress+"");
            handler.postDelayed(this,100);
        }
    };

    public void setProgress(int progress) {
        endProgress =(float)(progress * 3.6);
        handler.post(runnable);
    }

    /*public void setProgress(int progress){
        if(progress<=100){
            textContent = progress + "%";
            this.progress= (float) (progress*3.6);
            postInvalidate();

        }

    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制文本
        setTextLocation();
        canvas.drawText(textContent, textX, textY, textPaint);
        //绘制进度条
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        float radius = centerX - getPaddingRight();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(progressBackColor);
        paint.setStrokeWidth(progressWidth);
        canvas.drawCircle(centerX, centerY, radius, paint);
        paint.setColor(randomColor.randomColor());
        Log.e(TAG, "onDraw: is called");
        RectF rf = new RectF(centerX - radius, centerX - radius, centerX + radius, centerX + radius);
        canvas.drawArc(rf, -90, progress, false, paint);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measure_wh(widthMeasureSpec, 0), measure_wh(heightMeasureSpec, 1));
    }

    /**
     * 测量宽度
     *
     * @param measureSpec
     * @return
     */
    private int measure_wh(int measureSpec, int type) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;//如果没有设置精确值，则设置一个默认值200
        }

        if (type == 0) {
            viewWidth = result;
        } else {
            viewHeight = result;
        }

        return result;
    }

    public void setOnLoadStatusListener(OnLoadStatusListener onLoadStatusListener) {
        this.onLoadStatusListener = onLoadStatusListener;
    }

    OnLoadStatusListener onLoadStatusListener;

    public interface OnLoadStatusListener {
        void onLoadFinished();
    }
}
