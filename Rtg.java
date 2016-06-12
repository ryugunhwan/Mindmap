package com.gunhwan.mindmap;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;

public class Rtg implements Serializable {
    private static final long serialVersionUID = 6390090370433927430L;
    int cx, cy;
    int Id = 1;
    private int son = 0;
    String text;
    public static final int SIZE_HEIGHT = 50;
    public static final int SIZE_WIDTH = 100;
    Dialog_Rtg dialog;
    Rtg head;
    Context context;

    public Rtg(int x, int y, Rtg head) {
        cx = x;
        cy = y;
        this.head = head;
        if (head == null)
            Id = 1;
        else {
            Id = (head.Id * 10) + ++head.son;
        }
        this.text = Dialog_Rtg.text;
    }

    public Rtg(int x, int y, Rtg head, String text) {
        this(x, y, head);
        this.text = Dialog_Edit.text;
    }

    boolean InRtg(int x, int y, int px, int py, float scale)
            throws UnsupportedEncodingException {
        if (((cx - SIZE_WIDTH - (text.getBytes("MS949").length * 13) + px) * scale) < x
                && ((cy - SIZE_HEIGHT + py) * scale) < y
                && ((cx + SIZE_WIDTH + (text.getBytes("MS949").length * 13) + px) * scale) > x
                && ((cy + SIZE_HEIGHT + py) * scale) > y) {
            return true;
        }
        return false;
    }

    Canvas display(Canvas s, Paint paint) throws UnsupportedEncodingException { // ��
        // ����
        Paint paintS = new Paint();
        if (head == null) { // �θ� ������ ��°���? ��Ʈ !
            paintS.setColor(0xff0099ff);
            paintS.setAntiAlias(true);
            paintS.setStyle(Style.STROKE);
            paintS.setStrokeWidth(10);
            RectF rRect;
            rRect = new RectF(cx - SIZE_WIDTH
                    - (text.getBytes("MS949").length * 13) - 20, cy
                    - SIZE_HEIGHT - 20, cx + SIZE_WIDTH
                    + (text.getBytes("MS949").length * 13) + 20, cy
                    + SIZE_HEIGHT + 20);
            s.drawRoundRect(rRect, 60, 70, paintS);
            paint.setColor(Color.WHITE);
            s.drawRoundRect(rRect, 60, 70, paint);

            Paint paintT = new Paint();
            paintT.setColor(Color.BLACK);
            paintT.setTextSize(70);
            s.drawText(text, cx - (int) (text.getBytes("MS949").length * 17.5),
                    cy + (SIZE_HEIGHT / 2), paintT);
        } else {
            Paint paintT = new Paint();
            paintS.setStyle(Style.STROKE);
            paintS.setStrokeWidth(10);
            paintS.setColor(0xffff8300);
            if (text.getBytes("MS949").length < 8) {
                s.drawRect(cx - SIZE_WIDTH, cy - SIZE_HEIGHT, cx + SIZE_WIDTH,
                        cy + SIZE_HEIGHT, paintS);
            } else
                s.drawRect(cx - SIZE_WIDTH
                                - (text.getBytes("MS949").length * 13), cy
                                - SIZE_HEIGHT,
                        cx + SIZE_WIDTH + (text.getBytes("MS949").length * 13),
                        cy + SIZE_HEIGHT, paintS);
            paint.setColor(Color.WHITE);
            if (text.getBytes("MS949").length < 8) {
                s.drawRect(cx - SIZE_WIDTH, cy - SIZE_HEIGHT, cx + SIZE_WIDTH,
                        cy + SIZE_HEIGHT, paint);
            } else
                s.drawRect(cx - SIZE_WIDTH
                                - (text.getBytes("MS949").length * 13), cy
                                - SIZE_HEIGHT,
                        cx + SIZE_WIDTH + (text.getBytes("MS949").length * 13),
                        cy + SIZE_HEIGHT, paint);
            paintT.setColor(Color.BLACK);
            paintT.setTextSize(50);
            s.drawText(text, cx - (int) (text.getBytes("MS949").length * 12.5),
                    cy + (SIZE_HEIGHT / 2), paintT);
        }
        return s;
    }

    Canvas displayL(Canvas s, Paint paint) {
        Paint paintpath = new Paint();
        paintpath.setColor(0xff008808);
        paintpath.setStyle(Style.STROKE);
        Path p = new Path();

        if (head == null) {
            return s;
        } else if (head.head == null) {
            if (rootline()) {
                p.moveTo(head.cx, head.cy);
                p.quadTo(cx, head.cy, cx, cy);
            } else {
                p.moveTo(head.cx, head.cy);
                p.quadTo(head.cx, cy, cx, cy);
            }
        } else {
            p.moveTo(head.cx, head.cy);
            p.quadTo(getX(), getY(), cx, cy);
        }
        paintpath.setStrokeWidth(5);
        s.drawPath(p, paintpath);
        return s;
    }

    int getX() {
        return (head.cx - (head.head.cx - head.cx) / 2);
    }

    int getY() {
        return (head.cy - (head.head.cy - head.cy) / 2);
    }

    boolean rootline() {
        boolean result = false;
        try {
            if (Math.abs((cy - head.cy) * (cx - head.cx)) > 0) {
                result = true;
            }
        } catch (ArithmeticException e) {
        }
        return result;
    }

    Canvas displayS(Canvas s, Paint paint) throws UnsupportedEncodingException {
        paint.setColor(0xff00ff00);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(5);

        if (head == null) { // ��Ʈ��輱
            paint.setAntiAlias(true);
            RectF rRect = new RectF(cx - SIZE_WIDTH
                    - (text.getBytes("MS949").length * 13) - 20, cy
                    - SIZE_HEIGHT - 20, cx + SIZE_WIDTH
                    + (text.getBytes("MS949").length * 13) + 20, cy
                    + SIZE_HEIGHT + 20);
            s.drawRoundRect(rRect, 60, 70, paint);

        } else { // ������ ��輱
            if (text.getBytes("MS949").length < 8) {
                s.drawRect(cx - SIZE_WIDTH, cy - SIZE_HEIGHT, cx + SIZE_WIDTH,
                        cy + SIZE_HEIGHT, paint);
            } else
                s.drawRect(cx - SIZE_WIDTH
                                - (text.getBytes("MS949").length * 13), cy
                                - SIZE_HEIGHT,
                        cx + SIZE_WIDTH + (text.getBytes("MS949").length * 13),
                        cy + SIZE_HEIGHT, paint);
            paint.setTextSize(50);
            s.drawText(text, cx - (int) (text.getBytes("MS949").length * 12.5),
                    cy + (SIZE_HEIGHT / 2), paint);
        }
        return s;
    }

    public int getSon() {
        return son;
    }


}