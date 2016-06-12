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
public class Dialog_Exit extends Dialog implements OnTouchListener {
    public Dialog_Exit(Context context) {
        super(context);
    }

    ImageButton addOK, addCancel;
    EditText node_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);

        node_name = (EditText) findViewById(R.id.node_name);
        node_name.setTextColor(0xb0ffffff);
        node_name.setText("Exit MindMap");
        node_name.setFocusable(false);
        addOK = (ImageButton) findViewById(R.id.addOK);
        addOK.setOnTouchListener(this);
        addCancel = (ImageButton) findViewById(R.id.addCancel);
        addCancel.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == addOK) {
            System.exit(0);
        } else if (v == addCancel)
            cancel();
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            // �ϵ���� �ڷΰ��� ��ư�� �� �̺�Ʈ ����
            case KeyEvent.KEYCODE_BACK:
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}