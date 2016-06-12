package com.gunhwan.mindmap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

@SuppressLint("ClickableViewAccessibility")
public class Dialog_Edit extends Dialog implements OnTouchListener {
    ImageButton EaddOK, EaddCancel;
    EditText Enode_name;
    static String text = " ";

    public Dialog_Edit(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);

        Enode_name = (EditText) findViewById(R.id.node_name);
        Enode_name.setTextColor(0xb0ffffff);
        Enode_name.setText(Mapscreen.sRtg.text);
        Enode_name.setSelection(Enode_name.length());
        EaddOK = (ImageButton) findViewById(R.id.addOK);
        EaddCancel = (ImageButton) findViewById(R.id.addCancel);
        EaddOK.setOnTouchListener(this);
        EaddCancel.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == EaddOK) {
            text = Enode_name.getText().toString();
            Rtg temp = new Rtg(Mapscreen.sRtg.cx, Mapscreen.sRtg.cy,
                    Mapscreen.sRtg, text); // ���ο� ��ü�� ���Ͽ�
            for (int i = 1; i < Mapscreen.arr.size(); i++) {
                if (Mapscreen.arr.get(i).head == Mapscreen.sRtg) { // ������ ������ �θ�� ��� �ڽĵ���
                    Mapscreen.arr.get(i).head = temp; // �θ��� �θ� �θ�� ��´�

                }
            }
            temp.Id = Mapscreen.sRtg.Id;
            temp.head = Mapscreen.sRtg.head;
            for (int i = 0; i < Mapscreen.arr.size(); i++) {
                if (Mapscreen.arr.get(i) == Mapscreen.sRtg) { // ��ü��
                    Mapscreen.arr.set(i, temp);
                    Mapscreen.sRtg = Mapscreen.arr.get(i);
                    break;// ���õ� ��ü�� ã���� �ݺ����� ����������
                }
            }
            cancel();
        } else if (v == EaddCancel)
            cancel();

        return false;
    }
}