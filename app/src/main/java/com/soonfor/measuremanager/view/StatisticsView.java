package com.soonfor.measuremanager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by kirawu on 2017/9/5.
 */

public class StatisticsView extends View {
    private final int layout_width;
    private final int layout_height;
    private int graphic_width;
    private ArrayList<DataEntity> list = new ArrayList<>();
    private float k1;
    private float k2;
    private float z2;

    public StatisticsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int[] attrsArray = new int[]{android.R.attr.id, // 0
                android.R.attr.background, // 1
                android.R.attr.layout_width, // 2
                android.R.attr.layout_height // 3
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        layout_width = ta.getLayoutDimension(2,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layout_height = ta.getLayoutDimension(3,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ta.recycle();

        //留下50作为右边文字的预留空间
        graphic_width = layout_width - 100;

        test();
    }

    private void test() {
        ArrayList<DataEntity> list = new ArrayList<>();
        DataEntity entity = new DataEntity();
        entity.setName("意向客户");
        entity.setKey("250");
        entity.setRate("20%");
        entity.setColor(Color.RED);
        list.add(entity);
        entity = new DataEntity();
        entity.setName("预约量尺");
        entity.setKey("50");
        entity.setRate("48%");
        entity.setColor(Color.YELLOW);
        list.add(entity);
        entity = new DataEntity();
        entity.setName("完成量尺");
        entity.setKey("24");
        entity.setRate("62.5%");
        entity.setColor(Color.BLUE);
        list.add(entity);
        entity = new DataEntity();
        entity.setName("完成设计");
        entity.setKey("15");
        entity.setRate("80%");
        entity.setColor(Color.GREEN);
        list.add(entity);
        entity = new DataEntity();
        entity.setName("成交");
        entity.setKey("12");
        entity.setRate("83.3%");
        entity.setColor(Color.GRAY);
        list.add(entity);
        entity = new DataEntity();
        entity.setName("完成交付");
        entity.setKey("10");
        entity.setRate("20%");
        entity.setColor(Color.BLACK);
        list.add(entity);
        updateData(list);
    }

    public void updateData(ArrayList<DataEntity> list) {
        this.list = list;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = list.size();
        if (size < 1) {
            return;
        }
        Paint paint = new Paint();
        float height = layout_height / size;

        //先画底部，确认坐标点，底部的为一个正方形
        DataEntity entity = list.get(size - 1);
        paint.setColor(entity.getColor());
        canvas.drawRect((graphic_width - height) / 2, height * (size - 1), (graphic_width + height) / 2, height * size, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(height/5);
        drawText(canvas,paint,graphic_width/2,height*size-height*3.2f/5,entity.getName());
        drawText(canvas,paint,graphic_width/2,height*size-height*1.8f/5,entity.getKey());

        //根据y=kx+z列方程，线1：已知x1=0,y1=0,x2 = graphic_width / 2 - height / 2,y2 = height * (size - 1),
        // 所以k=2*height*(size-1)/(graphic_width-height)
        //线2：已知x1=graphic_width,y1 = 0;x2 = graphic_width / 2 + height / 2,y2 = height * (size - 1),
        // 所以k=2*height*(size-1)/(height-graphic_width)，z = 2*graphic_width*height*(size - 1)/(graphic_width-height)
        k1 = 2 * height * (size - 1) / (graphic_width - height);
        k2 = 2 * height * (size - 1) / (height - graphic_width);
        z2 = 2 * graphic_width * height * (size - 1) / (graphic_width - height);
        drawTrapezoid(canvas, paint, height);
    }

    /**
     * 画梯形
     */
    private void drawTrapezoid(Canvas canvas, Paint paint, float height) {
        for (int i = 0, size = list.size() - 1; i < size; i++) {
            DataEntity entity = list.get(i);
            paint.setColor(entity.getColor());
            paint.setStrokeWidth(2);

            float y1 = height * i;
            float x1 = y1 / k1;
            float y2 = y1;
            float x2 = (y2 - z2) / k2;
            float y4 = height * (i + 1);
            float x4 = (y4 - z2) / k2;
            float y3 = y4;
            float x3 = y3 / k1;

            spm("x3:"+x3);

            Path path = new Path();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            path.lineTo(x4, y4);
            path.lineTo(x3, y3);
            path.close();
            canvas.drawPath(path, paint);

            //画梯形右边的线和文字
            paint.setTextSize(height/6);
            canvas.drawLine(x4,y4,x2,y4,paint);
            canvas.drawText(entity.getRate(),x2+10,y4,paint);

            paint.setColor(Color.WHITE);
            paint.setTextSize(height/5);
            drawText(canvas,paint,graphic_width/2,y3-height*3.2f/5,entity.getName());
            drawText(canvas,paint,graphic_width/2,y3-height*1.8f/5,entity.getKey());
//
//            canvas.drawPath(path,paint);
//            canvas.drawPoint(x1,y1,paint);
//            canvas.drawPoint(x2,y2,paint);
//            canvas.drawPoint(x3,y3,paint);
//            canvas.drawPoint(x4,y4,paint);

//            if (i==1){
//                return;
//            }
        }
    }


    private void drawText(Canvas canvas, Paint paint, float x, float y, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);//用一个矩形去"套"字符串,获得能完全套住字符串的最小矩形
        float width = rect.width();//字符串的宽度
        float height = rect.height();//字符串的高度
        canvas.drawText(str, x - width / 2, y + height / 2, paint);
    }

    public static class DataEntity {
        private String name;
        private String key;
        private String rate;
        private int color;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }
    }

    private void spm(String msg) {
        //NLogger.d("spm", msg);
    }
}
