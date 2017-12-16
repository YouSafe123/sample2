package com.sample.com.sample2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by SAKSHAM on 24-10-2017.
 */

public class ClockView extends View {

    private int height, width=0;
    private int padding=0;
    private int fontsize=0;
    private int numeral_spacing=0;
    private int handTruncation, hourHandTruncation=0;
    private int radius=0;
    private Paint paint;
    private boolean isInit;
    private int[] numbers={1,2,3,4,5,6,7,8,9,10,11,12};
    private Rect rect=new Rect();
    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    private void initClock()
    {
        height=getHeight();
        width=getWidth();
        padding=numeral_spacing+50;
        fontsize=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,13,getResources().getDisplayMetrics());
        int min=Math.min(height,width);
        radius=min/2-padding;
        handTruncation=min/20;
        hourHandTruncation=min/7;
        paint=new Paint();
        isInit=true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(isInit)
        {
            initClock();
        }
        canvas.drawColor(Color.BLACK);
//        drawCircle(0);
//        )
    }
}
