package com.quaap.computationaldemonology;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by tom on 12/4/16.
 */

public abstract class Drawgorythm {
    protected Paint mForeground;

    protected Paint mBackground;

    protected int mCenterX;
    protected int mCenterY;

    protected int mWidth;
    protected int mHeight;

    protected boolean done=false;

    public void setPaints(Paint foreground, Paint background) {
        mForeground = foreground;
        mBackground = background;
    }

    public void canvasChanged(final Canvas canvas) {
        mWidth = canvas.getWidth();
        mHeight = canvas.getHeight();
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
        done = false;
    }

    public abstract void doDraw(final Canvas canvas, final long ticks);
}


abstract class Ring extends Drawgorythm {

    double r = 0;
    double rad = 0;

    @Override
    public void canvasChanged(final Canvas canvas) {
        super.canvasChanged(canvas);
        r = Math.min(mHeight, mWidth) / 3;
        rad = 0;
    }

}



class FuzzyRing extends Ring {

    @Override
    public void doDraw(final Canvas canvas, final long ticks) {

        for (long j=0; j<ticks; j++) {

            for (int i = 0; i < 2; i++) {
                double rad1 = rad;
                if (i == 1) rad1 = i * 2 * Math.PI - rad;

                double rnd = Math.random();
                int sizex = (int) (rnd * r / 10 * Math.cos(rad1 * 150)) + 1;
                int sizey = (int) (rnd * r / 10 * Math.sin(rad1 * 150)) + 1;
                float x = (float) (r * Math.sin(rad1)) + mCenterX + sizex;
                float y = (float) (r * Math.cos(rad1)) + mCenterY + sizey;


                canvas.drawLine(x, y, x + 8, y + 8, mForeground);
            }

            rad += .001;

        }


    }
}


class BarbedRing extends Ring {

    public void doDraw(final Canvas canvas, final long ticks) {

        for (long j=0; j<ticks; j++) {
            for (int i = 0; i < 2; i++) {
                double rad1 = rad;
                if (i == 1) rad1 = i * Math.PI - rad;
                float x = (float) (r * Math.sin(rad1));
                float y = (float) (r * Math.cos(rad1));
                double rnd = Math.random();
                int sizex = (int) (rnd * r / 10 * Math.cos(rad1 * 50)) + 1;
                int sizey = (int) (rnd * r / 10 * Math.sin(rad1 * 50)) + 1;
                canvas.drawLine(mCenterX + x, mCenterY + y, mCenterX + x + sizex, mCenterY + y + sizey, mForeground);
            }

            rad += .001;
        }

    }

}


class PentaRing extends Ring {
    private float lastX = 0;
    private float lastY = 0;
    private float nextX = 0;
    private float nextY = 0;

    private double x;
    private double y;
    private double dX;
    private double dY;

    private double rad2 = rad;
    private boolean donext = true;


    public void doDraw(final Canvas canvas, final long ticks) {

        if (done) return;
        if (donext) {
            if (rad>Math.PI*3) {
                done=true;
                return;
            }
            lastX = (float) (r * Math.sin(rad)) + mCenterX;
            lastY = (float) (r * Math.cos(rad)) + mCenterY;;
            rad += Math.PI * 2.0 / 5.0;
            nextX = (float) (r * Math.sin(rad)) + mCenterX;
            nextY = (float) (r * Math.cos(rad)) + mCenterY;

            x = lastX;
            y = lastY;

            dX = nextX - lastX;
            dY = nextY - lastY;
            donext = false;
        } else {

            for (long j = 0; j < ticks; j++) {
                double fac = Math.abs(dY/dX) + 1;
                double xp = x + Math.signum(dX)/( fac * 15 );
                double yp = lastY + dY * (x - lastX) / dX;

                //canvas.drawLine((float)x, (float)y, (float)xp, (float)yp, mForeground);

                rad2 = Math.atan((y-mCenterY)/(x-mCenterX));

                for (int p=1; p<7; p++) {
                    int sizex = (int) (r / 10/p * Math.cos((Math.PI*p - rad2) * 10 * p)) + 1;
                    int sizey = (int) (r / 10/p * Math.sin((Math.PI*p - rad2) * 10 * p)) + 1;
                    canvas.drawPoint((float) xp + sizex, (float) yp + sizey, mForeground);
                }

                x = xp;
                y = yp;

                if (Math.round(x)==Math.round(nextX)) {
                    donext = true;
                }

            }
        }
        //rad += Math.PI * 2.0 / 5.0;

    }

}

/////////////////////////
//penta
//    double r = 0;
//    double rad = 0;
//    float lastX = 0;
//    float lastY = 0;
//    private void doDraw(final Canvas canvas) {
//        // Log.d("GraphicDmn", "doDraw");
//
//        float mx = canvas.getWidth() / 2;
//        float my = canvas.getHeight() / 2;
//
//
//        if (rad/Math.PI < Math.PI*10) {
//
//            r = Math.min(canvas.getWidth(), canvas.getHeight()) / 3;
//
//            float x = (float) (r * Math.sin(rad)) + mx;
//            float y = (float) (r * Math.cos(rad)) + my;
//            if (lastX != 0 && lastY != 0) {
//                canvas.drawLine(x,y, lastX, lastY, mLinePaint);
//               // canvas.drawLine(x,y, mx, my, mLinePaint);
//            }
//
//            lastX = x;
//            lastY = y;
//
//            rad += Math.PI * 2.0 / 5.0;
//
//        }
//
//
//    }

/////////////////////////
//fuzzy circle
//    double r = 0;
//    double rad = 0;
//    private void doDraw(final Canvas canvas) {
//       // Log.d("GraphicDmn", "doDraw");
//
//
//
//        if (rad/Math.PI < Math.PI*10) {
//            float mx = canvas.getWidth() / 2;
//            float my = canvas.getHeight() / 2;
//
//            r = Math.min(canvas.getWidth(), canvas.getHeight()) / 3;
//
//            for (int i = 0; i < 2; i++) {
//                double rad1 = rad;
//                if (i == 1) rad1 = i * Math.PI - rad;
//                float x = (float) (r * Math.sin(rad1));
//                float y = (float) (r * Math.cos(rad1));
//                double rnd = Math.random();
//                int sizex = (int) (rnd * 4 * r / 10 * Math.sin(rad1)) + 1;
//                int sizey = (int) (rnd * 4 * r / 10 * Math.cos(rad1)) + 1;
//                canvas.drawLine(mx + x, my + y, mx + x + sizex, my + y + sizey, mLinePaint);
//            }
//
//            rad += .05;
//        }
//
//    }