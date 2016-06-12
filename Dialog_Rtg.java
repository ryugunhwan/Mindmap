package com.gunhwan.mindmap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

@SuppressLint("ClickableViewAccessibility")
public class Dialog_Rtg extends Dialog implements OnTouchListener {
    ImageButton addOK, addCancel;
    EditText node_name;
    static String text = "";
    Mapscreen fMap = (Mapscreen) Mapscreen.MapscreenClass;

    public Dialog_Rtg(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);

        node_name = (EditText) findViewById(R.id.node_name);
        node_name.setTextColor(0xb0ffffff);
        node_name.setHintTextColor(0x80ffffff);
        addOK = (ImageButton) findViewById(R.id.addOK);
        addOK.setOnTouchListener(this);
        addCancel = (ImageButton) findViewById(R.id.addCancel);
        addCancel.setOnTouchListener(this);
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == addOK) {
            text = node_name.getText().toString();
            if (Mapscreen.arr.size() == 0) {
                Rtg root = new Rtg(500, 800, null);
                Mapscreen.arr.add(root);
            } else {
                Rtg temp = new Rtg(Mapscreen.sRtg.cx - 200,
                        Mapscreen.sRtg.cy - 150, Mapscreen.sRtg); // ���ο� ��ü��
                // ���Ͽ�
                Mapscreen.sRtg = temp;
                Mapscreen.arr.add(temp); // ����Ʈ�� �߰��Ѵ�
            }
            cancel();
        } else if (v == addCancel)
            if (Mapscreen.arr.size() == 0)
                fMap.finish();
            else
                cancel();
        Mapscreen.w.invalidate();
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            // �ϵ���� �ڷΰ��� ��ư�� �� �̺�Ʈ ����
            case KeyEvent.KEYCODE_BACK:
                if (Mapscreen.arr.size() == 0) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}