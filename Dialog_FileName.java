package com.gunhwan.mindmap;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

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
import android.widget.Toast;

@SuppressLint("ClickableViewAccessibility")
public class Dialog_FileName extends Dialog implements OnTouchListener {
    ImageButton EaddOK, EaddCancel;
    EditText Enode_name;
    static String text = " ";
    static boolean openFlag=false;
    Context context;
    public Dialog_FileName(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);

        Enode_name = (EditText) findViewById(R.id.node_name);

        Enode_name.setTextColor(0xb0ffffff);
        Enode_name.setHint("FILE NAME");
        if(openFlag==true){
            Enode_name.setText(SaveFileList.data.getMain_Title());
            Enode_name.setSelection(Enode_name.length());
        }
        EaddOK = (ImageButton) findViewById(R.id.addOK);
        EaddCancel = (ImageButton) findViewById(R.id.addCancel);
        EaddOK.setOnTouchListener(this);
        EaddCancel.setOnTouchListener(this);
    }

    @SuppressLint("WorldReadableFiles")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == EaddOK) {
            text = Enode_name.getText().toString();
            try{
                @SuppressWarnings("deprecation")
                FileOutputStream fos = context.openFileOutput(text+".dat",
                        Context.MODE_WORLD_READABLE);
                ObjectOutputStream out = new ObjectOutputStream(fos);
                out.writeObject(Mapscreen.arr);
                out.close();
                cancel();
                android.os.Process.killProcess(android.os.Process.myPid());
            }catch (Exception e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else if (v == EaddCancel)
            cancel();

        return false;
    }
}