package com.gunhwan.mindmap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageButton;

@SuppressLint("ClickableViewAccessibility")
public class Dialog_Save extends Dialog implements OnTouchListener {
    ImageButton Save, NoSave, Cancel;
    Context context;
    Dialog_FileName fnD;
    public Dialog_Save(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.savedialog);
        Save = (ImageButton) findViewById(R.id.save);
        NoSave = (ImageButton) findViewById(R.id.nosave);
        Cancel = (ImageButton) findViewById(R.id.cancel);
        Save.setOnTouchListener(this);
        NoSave.setOnTouchListener(this);
        Cancel.setOnTouchListener(this);
        fnD = new Dialog_FileName(context);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == Save) {
            fnD.show();
            cancel();
        } else if (v == NoSave) {
            android.os.Process.killProcess(android.os.Process.myPid());
        } else if (v == Cancel) {
            cancel();
        }
        return false;
    }
}
