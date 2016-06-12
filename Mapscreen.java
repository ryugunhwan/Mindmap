package com.gunhwan.mindmap;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Mapscreen extends Activity {
    private ArrayList<Rtg> fRtg = null;
    public static Activity MapscreenClass;
    int x, y;
    int mx, my;
    int dx, dy; // ȭ���� �̵�����, ��ġ�̺�Ʈ���� �������� ��ġ�� �ʱ�ȭ�� �������� ����Ŭ�������� ��
    static Rtg sRtg = null;
    Dialog_Save save;
    Dialog_Rtg dialog;
    Dialog_Edit edit;
    int px = 0, py = 0; // �е��
    static MyView w;
    RelativeLayout layout;
    static ArrayList<Rtg> arr = new ArrayList<Rtg>();
    static ArrayList<Rtg> parr = new ArrayList<Rtg>();
    private final float MIN_ZOOM = 0.1f;
    private final float MAX_ZOOM = 5.0f;
    private int Width, Height;
    int mWidth, mHeight;
    static boolean flag = false;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MapscreenClass = Mapscreen.this;
        setContentView(R.layout.map);
        Intent intent = getIntent();
        fRtg = (ArrayList<Rtg>) intent.getSerializableExtra("frtg");
        layout = (RelativeLayout) findViewById(R.id.map);
        w = new MyView(this);
        layout.addView(w);
        Width = this.getResources().getDisplayMetrics().widthPixels;
        Height = this.getResources().getDisplayMetrics().heightPixels;
        // ���� ��Ƽ��Ƽ�κ��� �Ѿ�� �����͸� ������.
    }

    protected class MyView extends View {
        private ScaleGestureDetector scaledetector;
        private float mScaleFactor = 1.0f;
        // private int mx, my; // �������������� �߰������� �޴´�
        int countX = 0;
        int countY = 0;
        private boolean ban = false;

        /** ���̺� */
        public MyView(Context context) {
            super(context);
            setBackgroundColor(0x800099ff);
            scaledetector = new ScaleGestureDetector(getContext(),
                    new ScaleListener());
            if (flag == false) {
                dialog = new Dialog_Rtg(context);
                dialog.setTitle("TITLE");
                dialog.setCanceledOnTouchOutside(false); // �ܺ���ġ�� dismiss�� �߻�����
                // ����
                dialog.show();
            } else {
                arr = fRtg;
                for (int i = 1; i < arr.size(); i++) { // root�� ������ ��� ��带
                    // �������(i)
                    for (int j = 0; j < arr.size(); j++) { // ��� ��带 �˻��Ͽ�(j)
                        if (arr.get(i).Id / 10 == arr.get(j).Id) { // i�� ���̵���
                            // ���ڸ��� ������
                            // ���ڰ� j��
                            // ���̵�� �����ϸ�
                            arr.get(i).head = arr.get(j); // i�� �θ�� j�� �ȴ�.
                            break;
                        }
                    }
                }
            }
        }

        /** �µ�ο� */
        @SuppressLint("DrawAllocation")
        @Override
        protected void onDraw(Canvas c) {
            super.onDraw(c);
            c.save();
            c.scale(mScaleFactor, mScaleFactor);
            c.translate(px, py);
            Paint paint = new Paint();
            for (int i = 1; i < arr.size(); i++) { // �������� ���� �׸��� ���� �ٸ����� �׷���
                // �������� �׻� �� �ڿ� ��ġ�Ѵ�
                c = arr.get(i).displayL(c, paint);
            }
            for (int i = arr.size() - 1; i >= 0; i--) { // ��Ʈ�� ���� �������� �׸��°����� ����
                try {
                    c = arr.get(i).display(c, paint);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (sRtg != null) { // ����ǥ��
                try {
                    c = sRtg.display(c, paint);// ���õ� ������ ���� ���� �׸���
                    c = sRtg.displayS(c, paint); // ���õ������ǰ�輱���׸���<�ݴ���ϸ��輱�̺���������>
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            invalidate();
            c.restore();
        }

        /************** ����ġ�̺�Ʈ ************/
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (scaledetector.onTouchEvent(event))
                ;
            // px = (int) (px + mx);
            // py = (int) (py + my);
            // }
            x = (int) event.getX();
            y = (int) event.getY();
            mWidth = (int) (Width / mScaleFactor);
            mHeight = (int) (Height / mScaleFactor);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (ban)
                        break;
                    for (int i = 0; i < arr.size(); i++) {
                        try {
                            if (arr.get(i).InRtg(x, y, px, py, mScaleFactor)) { // ��ü����ġ�Ѱ��.

                                sRtg = arr.get(i); // ��ü ���û��°� �ȴ�.
                                mx = x - sRtg.cx;
                                my = y - sRtg.cy;
                                break;// ���õ� ��ü�� ã���� �ݺ����� ����������
                            } else if (i == arr.size() - 1) { // ��ü�� �ƴ� ȭ���� ��ġ�� ���
                                dx = x; // �̵��Ÿ��� �������� ��������
                                dy = y;
                                sRtg = null; // ��ü ��ġ���°� Ǯ����..
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (ban)
                        break;
                    if (sRtg != null) { // ��ġ�� ��ü�� ������
                        sRtg.cx = (int) (x / mScaleFactor) - px; // - Rtg.SIZE_WIDTH
                        sRtg.cy = (int) (y / mScaleFactor) - py; // -Rtg.SIZE_HEIGHT
                    } else {
                        if (px >= -mWidth && px <= mWidth) {
                            px = px + (x - dx);
                            dx = x;
                        } else {
                            if (px > mWidth)
                                px = mWidth;
                            else
                                px = -mWidth;
                        }
                        if (py >= -mHeight && py <= mHeight) {
                            py = py + (y - dy);
                            dy = y;
                        } else {
                            if (py > mHeight)
                                py = mHeight;
                            else
                                py = -mHeight;
                        }
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_POINTER_DOWN:
                    ban = true;
                    break;
                case MotionEvent.ACTION_UP:
                    ban = false;
                    break;
            }
            invalidate();
            return true;
        }// onTouchEvent_END

        /** �����ϸ����� */
        private class ScaleListener extends
                ScaleGestureDetector.SimpleOnScaleGestureListener {
            @Override
            public boolean onScale(ScaleGestureDetector scaledetector) {
                mScaleFactor *= scaledetector.getScaleFactor();
                // mx = (int) scaledetector.getFocusX(); //�������������� ��Ŀ����ǥ �Է�
                // my = (int) scaledetector.getFocusY();
                if (mScaleFactor < MIN_ZOOM) {
                    mScaleFactor = MIN_ZOOM;
                }
                if (mScaleFactor > MAX_ZOOM) {
                    mScaleFactor = MAX_ZOOM;
                }
                invalidate();
                return true;
            }
        }// Listener_END
    }// Myview_END

    /******* �ɼǸ޴� *******/
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu); // �θ� ���� �Ű����� ����
        menu.add(0, 1, 0, "�߰�"); // �߰� �޴��׸� ��
        menu.add(0, 2, 0, "����");
        menu.add(0, 3, 0, "����");
        return true;
    }

    /************ �ɼ� �޴� ���ý� �̺�Ʈ ****************/
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1: // �߰�
                if (sRtg != null) { // ��ġ�� ��ü�� ������
                    dialog = new Dialog_Rtg(this);
                    dialog.show();
                } else
                    Toast.makeText(this, "Select Node", Toast.LENGTH_SHORT).show();
                w.invalidate();
                return true;
            case 2: // ����
                if (sRtg != null) {
                    if (sRtg == arr.get(0)) { // ������ ������ ��Ʈ���
                        Toast.makeText(this, "Can't delete", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    }
                    ReRtg(sRtg);
                    arr.remove(sRtg);
                    sRtg = null;
                } else
                    Toast.makeText(this, "Select Node", Toast.LENGTH_SHORT).show();
                w.invalidate();
                return true;
            case 3:
                if (sRtg != null) {
                    edit = new Dialog_Edit(this);
                    edit.show();
                } else
                    Toast.makeText(this, "Select Node", Toast.LENGTH_SHORT).show();
                w.invalidate();
                return true;
        }
        return false;
    }

    void ReRtg(Rtg parent) {
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).head == parent) { // ������ ������ �θ�� ��� �ڽĵ���
                arr.get(i).head = parent.head; // �θ��� �θ� �θ�� ��´�
                arr.get(i).Id = parent.Id;
                ReRtg(arr.get(i));
            }
        }
        return;
    }

    /** ���μ��κ�ȯ **/
    /********** ȭ�� ��� ��ȯ(����-����) **********/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /** �ڷΰ��� �̺�Ʈ **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            // �ϵ���� �ڷΰ��� ��ư�� �� �̺�Ʈ ����
            case KeyEvent.KEYCODE_BACK:
                save = new Dialog_Save(this);
                save.show();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}