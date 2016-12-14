package com.ui.anynew.uicomponents.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by anynew on 2016/12/14.
 */

public class TextView extends View {

    private Paint basePaint;

    private Paint tPaint;

    private  float baseX = 200;

    private String string = "nihao,glass";
    public TextView(Context context) {
        this(context, null);
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        basePaint = new Paint();
        basePaint.setColor(Color.RED);
        tPaint = new Paint();
        tPaint.setTextSize(60f);
        tPaint.setAntiAlias(true);
        tPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0,500,1000,500,basePaint);
        canvas.drawText(string,200,500,tPaint);
    }
}
