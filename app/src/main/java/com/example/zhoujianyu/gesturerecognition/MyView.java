package com.example.zhoujianyu.gesturerecognition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class MyView extends View {
    short mdata[];
    int top = 200;
    int gap = 10;
    int length=100;
    int screenHeight = 0;
    int screenWidth = 0;
    int capaWidth = 0;
    int capaHeight= 0;
    Paint paints[];
    Rect rects[];
    Rect showRects[];
    Paint showPaints[];
    int positions[] = {3,5,7,9,11,13,14,15,16,17,19,21,23,25,27};

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mdata = new short[positions.length];
        paints = new Paint[positions.length];
        showPaints = new Paint[positions.length];
        for(int i = 0;i<paints.length;i++){
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setStyle(Paint.Style.STROKE);
            p.setTextSize(30);
            paints[i] = p;
        }
        for (int i = 0;i<showPaints.length;i++){
            Paint p = new Paint();
            p.setColor(Color.RED);
            showPaints[i] = p;
        }
        rects = new Rect[positions.length];
        for(int i = 0;i<rects.length;i++){
            int left = 400;
            int right = left+length;
            int top = this.top+i*(length+gap);
            int bottom = top+length;
            Rect rect = new Rect(left,top,right,bottom);
            rects[i]=rect;
        }
    }
    public void init(){
        this.capaWidth = screenWidth/16;
        this.capaHeight= screenHeight/32;
        showRects = new Rect[positions.length];
        for (int i = 0;i<showRects.length;i++){
            int left = 5;
            int right = left+this.capaWidth;
            int top = (positions[i]-1)*this.capaHeight;
            int bottom = top+this.capaHeight;
            Rect rect  = new Rect(left,top,right,bottom);
            showRects[i] = rect;
        }
//        for (int i = 0;i<)
    }
    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void updateData(short[] data){
        for(int i = 0;i<positions.length;i++){
            mdata[i] = data[(positions[i])*GridView.COL_NUM];
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0;i<positions.length;i++){
            int val = mdata[i];
            int left = rects[i].left;
            int top = rects[i].top;
            canvas.drawRect(rects[i],paints[i]);
            canvas.drawText(Integer.toString(i)+":"+Integer.toString(val),left+30,top+50,paints[i]);

            canvas.drawRect(showRects[i],showPaints[i]);
            showPaints[i].setColor(Color.WHITE);
            showPaints[i].setTextSize(40);
            canvas.drawText(Integer.toString(i),showRects[i].left+15,showRects[i].top+50,showPaints[i]);
            showPaints[i].setStyle(Paint.Style.STROKE);
            showPaints[i].setColor(Color.YELLOW);
            canvas.drawRect(showRects[i],showPaints[i]);
            showPaints[i].setStyle(Paint.Style.FILL);
            showPaints[i].setColor(Color.RED);
        }
    }
}
