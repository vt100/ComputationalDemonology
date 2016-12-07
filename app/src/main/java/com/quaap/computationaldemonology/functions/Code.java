package com.quaap.computationaldemonology.functions;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.quaap.computationaldemonology.R;

/**
 * Created by tom on 12/6/16.
 */

public class Code extends Drawgorythm  {
    String hp;
    String lsp;
    String [] codes;
    String [] nouns;
    String [] lc;

    Paint mTextPaint;

    int theight;

    long tickspast = 0;

    int numlines = 10;

    public Code(Context context) {
        super(context);
        hp = context.getString(R.string.htmlparse);
        lsp = context.getString(R.string.lisp1);
        lc = context.getString(R.string.lovecraft).split(" ");
        codes = context.getString(R.string.code).split(" ");
        nouns = context.getString(R.string.nouns).split(" ");

        mTextPaint = new Paint();
        mTextPaint.setARGB(90, 128, 255, 128);
        mTextPaint.setTextSize(24);
        theight = (int) (mTextPaint.descent() - mTextPaint.ascent());
    }
    @Override
    public void doDraw(Canvas canvas, long ticks) {

        tickspast+=ticks;
        if (tickspast > 100) {
            makeText();
            tickspast = 0;

        }
        int pos1 = 0;
        int pos2 = 0;
        int y = mHeight - theight * numlines;
        for (int i = 0; i < numlines; i++) {
            pos2 = hist.indexOf("\n", pos1) + 1;
            if (pos2 > 0) {
                canvas.drawText(hist, pos1, pos2, 10, y, mTextPaint);
                y += theight;
                pos1 = pos2;
            } else {
                break;
            }
        }


    }

    StringBuilder hist = new StringBuilder(2048);
    public void makeText() {


        if (hist.length()>6) {

            hist.replace(0, hist.indexOf("\n")+1,"");
        }
        if (hist.length()<1000) {
            int low1=Integer.parseInt("2200", 16);
            int high1=Integer.parseInt("22FF", 16);
//            int low2=Integer.parseInt("1D400", 16);
//            int high2=Integer.parseInt("1D7FF", 16);
            for (int i = 0; i < 3; i++) {
                //hist.append(new String(Character.toChars((int) (Math.random() * (high2 - low2) + low2))));
//                if (Math.random() > .5) {
//                    hist.append(new String(Character.toChars((int) (Math.random() * (high2 - low2) + low2))));
//                }
                if (Math.random() > .3) {
                    hist.append(new String(Character.toChars((int) (Math.random() * (high1 - low1) + low1))));
                    hist.append((char) (Math.random() * 26 + 65));
                    for (int j=0; j<Math.random()*6+3; j++) {
                        hist.append((char) (Math.random() * 56 + 65));
                        hist.append((char) (Math.random() * 56 + 65));
                    }
                }
                if (Math.random() > .5) {
                    hist.append(String.format("%6.3e", Math.random() * 100));
                    hist.append(" ");
                }
                if (Math.random() > .5) {
                    hist.append(new String(Character.toChars((int) (Math.random() * (high1 - low1) + low1))));
                    hist.append(new String(Character.toChars((int) (Math.random() * (high1 - low1) + low1))));
                    hist.append(" ");
                }
                if (Math.random() > .8) {
                    int rnd = (int) (Math.random() * (hp.length() - 20));
                    hist.append(hp.substring(rnd, rnd + 20));
                    hist.append(" ");
                }
                if (Math.random() > .85) {
                    int rnd = (int) (Math.random() * (lsp.length() - 20));
                    hist.append(lsp.substring(rnd, rnd + 20));
                    hist.append(" ");
                }
                if (Math.random() > .9) {
                    hist.append(lc[(int)(Math.random()*lc.length)]);
                    hist.append(" ");
                }

                if (Math.random() > .98) {
                    hist.append("\n  -{{{");
                    hist.append(codes[(int)(Math.random()*codes.length)].toUpperCase());
                    hist.append("  ");
                    hist.append(nouns[(int)(Math.random()*nouns.length)].toUpperCase());
                    if (Math.random()>.3) {
                        hist.append("  ");
                        hist.append(nouns[(int) (Math.random() * nouns.length)].toUpperCase());
                    }
                    hist.append("}}}-  ");
                }
                hist.append("\n");
            }
        }
    }
}
