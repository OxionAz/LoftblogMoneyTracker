package ru.loftschool.loftblogmoneytracker.ui.view;

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
public class Oval extends View {

    private Paint paint;
    private RectF rectF;

    public Oval(Context context, AttributeSet attr) {
        super(context, attr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int startTop = 0;
        int startLeft = 0;
        int endBottom = getWidth();
        int endRight = endBottom;

        Random random = new Random();
        rectF = new RectF(startLeft, startTop, endRight, endBottom);
        canvas.drawOval(rectF, paint);
    }

    public void SetColor(int color){
        paint.setColor(color);
    }
}
