package ru.loftschool.loftblogmoneytracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Александр on 23.10.2015.
 */
public class PieChartView extends View {

    private Paint slicePaint;
    private RectF rectF;
    private float[] datapoints;

    public PieChartView(Context context, AttributeSet attr) {
        super(context, attr);
        slicePaint = new Paint();
        slicePaint.setAntiAlias(true);
        slicePaint.setDither(true);
        slicePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(datapoints != null){
            int startTop = 0;
            int startLeft = 0;
            int endBottom = getWidth();
            int endRight = endBottom;
            Random random = new Random();

            rectF = new RectF(startLeft, startTop, endRight, endBottom);
            float[] scaleValues = scale();
            float sliceStartPoint = 0;
            for (float scaledValue : scaleValues){
                int color = Color.argb(1000, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                slicePaint.setColor(color);
                canvas.drawArc(rectF, sliceStartPoint, scaledValue, true, slicePaint);
                sliceStartPoint+=scaledValue;
            }
        }
    }

    private float[] scale(){
        float[] scaleValue = new float[this.datapoints.length];
        float total = getTotal();
        for(int i=0; i<this.datapoints.length; i++) {
            scaleValue[i] = (this.datapoints[i] / total) * 360;
        }
        return scaleValue;
    }

    public void setDatapoints(float[] datapoints){
        this.datapoints = datapoints;
    }

    private float getTotal(){
        float total=0;
        for(float val : this.datapoints)
            total+=val;
        return total;
    }
}
