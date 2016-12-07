package com.quaap.computationaldemonology.functions;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;

import com.quaap.computationaldemonology.R;

/**
 * Created by tom on 12/7/16.
 */

public class CloudChamber extends Drawgorythm {

    int num = -1;
    private double [] sparkX;
    private double [] sparkY;
    private double [] sparkOldX;
    private double [] sparkOldY;
    private double [] sparkDX;
    private double [] sparkDY;
    private Paint  [] fgs;
    private String [] words;

    private Paint mTextPaint;
    private String word;

    private long iterations = 0;
    public CloudChamber(Context context) {
        super(context);
        words = context.getString(R.string.lovecraft).split("\\s+");
    }

    @Override
    public void canvasChanged(Canvas canvas) {
        super.canvasChanged(canvas);
        setValues();
        mTextPaint = new Paint();
        mTextPaint.setColor(mBackground.getColor()+30);
        mTextPaint.setTextSize(90);
        mTextPaint.setTypeface(Typeface.MONOSPACE);
        mTextPaint.setAlpha(255);
    }

    private void setValues() {
        num = (int)(Math.random()*50 + 20);
        sparkX = new double[num];
        sparkY = new double[num];
        sparkOldX = new double[num];
        sparkOldY = new double[num];
        sparkDX = new double[num];
        sparkDY = new double[num];
        fgs = new Paint[num];
        for (int i=0; i<num;i++) {
            setValue(i);
        }

    }

    private void setValue(int i) {
        sparkX[i] = (Math.random()-.5) * mCenterX + mCenterX;
        sparkY[i] = (Math.random()-.5) * mCenterY + mCenterY;
        sparkOldX[i] = sparkX[i];
        sparkOldY[i] = sparkY[i];
        sparkDX[i] = (Math.random()-.5);
        sparkDY[i] = (Math.random()-.5);

        fgs[i] = getRandomForeground();
        if (swirl == i) swirl=-1;
    }

    int swirl = -1;

    @Override
    public void doDraw(Canvas canvas, long ticks) {
        iterations++;

        if (swirl<0 || iterations>500) {
            swirl = (int)(Math.random()*(num+3)) - 3;
            iterations=0;
            word = words[(int)(Math.random()*words.length)];
        }

        if (swirl>=0) {
            sparkDX[swirl] = Math.cos(iterations/10.0)/2;
            sparkDY[swirl] = Math.sin(iterations/10.0)/2;
        }

        for (int m=0; m<100; m++) {

            for (int i = 0; i < num; i++) {
                sparkX[i] += sparkDX[i];
                sparkY[i] += sparkDY[i];
                canvas.drawLine((float) sparkX[i], (float) sparkY[i], (float) sparkOldX[i], (float) sparkOldY[i], fgs[i]);
                sparkOldX[i] = sparkX[i];
                sparkOldY[i] = sparkY[i];

                if (sparkX[i]>mWidth || sparkX[i]<0 || sparkY[i]>mHeight ||sparkY[i]<0) {
                    setValue(i);
                }
            }
        }

        canvas.drawText(word, 15, mCenterY, mTextPaint);


    }
}
