package com.example.stopwatch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircularProgressBar extends View {

    private Paint paint;
    private RectF rectF;
    private int progress = 0;
    private int maxProgress = 60; // 60 seconds in a minute
    private int strokeWidth = 10; // Adjust as needed
    private int startAngle = -90; // Start from the top

    public CircularProgressBar(Context context) {
        super(context);
        init();
    }

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.GREEN); // Change color as needed
        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int diameter = Math.min(getWidth(), getHeight()) - strokeWidth;
        rectF.set(strokeWidth, strokeWidth, diameter, diameter);
        float sweepAngle = 360f * progress / maxProgress;
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }
}
