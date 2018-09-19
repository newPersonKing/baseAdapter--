package com.system.translationpen.settings.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;


public class VerticalSeekBar extends android.support.v7.widget.AppCompatSeekBar {

    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //将SeekBar旋转90度
        canvas.rotate(-90);
        //将SeekBar移动到原来的位置
        canvas.translate(-canvas.getHeight(), 0);
        super.onDraw(canvas);
    }
}
