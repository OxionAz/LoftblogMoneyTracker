package ru.loftschool.moneytrackerbyoxion.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Александр on 23.10.2015.
 */
public class PieChartView extends View {

    private Paint slicePaint;
    private Paint textPaint;
    private RectF rectF;
    private RectF rectF2;
    private float[] datapoints;

    public PieChartView(Context context, AttributeSet attr) {
        super(context, attr);
        slicePaint = new Paint();
        slicePaint.setAntiAlias(true);
        slicePaint.setDither(true);
        slicePaint.setStyle(Paint.Style.FILL);

        textPaint = slicePaint;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        if(datapoints != null){
            int startTop = 0;
            int startLeft = 0;
            int endBottom = getWidth();
            int endRight = endBottom;
            int scale = 100;
            Random random = new Random();

            rectF = new RectF(startLeft, startTop, endRight, endBottom);
            rectF2 = new RectF(startLeft+scale, startTop+scale, endRight-scale, endBottom-scale);
            float[] scaleValues = scale();
            float sliceStartPoint = 0;
            for (float scaledValue : scaleValues){
                int color = Color.argb(1000, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                slicePaint.setColor(color);
                canvas.drawArc(rectF, sliceStartPoint, scaledValue, true, slicePaint);
                sliceStartPoint+=scaledValue;
            }
            slicePaint.setColor(Color.WHITE);
            canvas.drawArc(rectF2, 0, 360, true, slicePaint);
            textPaint.setColor(Color.BLACK);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(80);
            textPaint.setFakeBoldText(true);
            canvas.drawText("Категории",endBottom/2, endBottom/2, textPaint);
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
        for(float val : this.datapoints) total+=val;
        return total;
    }
}
