package com.gunhwan.mindmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;

public class Main extends Activity {
    static Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ImageButton mapButton = (ImageButton) findViewById(R.id.mapBtn);// ����
        mapButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        Mapscreen.class);
                startActivity(intent);
            }
        });

        ImageButton openButton = (ImageButton) findViewById(R.id.openBtn);// �ҷ�����
        openButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        SaveFileList.class);
                startActivity(intent);
            }
        });

        ImageButton helpButton = (ImageButton) findViewById(R.id.helpBtn);// ����
        helpButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/z4EROjn-1yE"));
                startActivity(intent);
            }
        });
        ImageButton exitButton = (ImageButton) findViewById(R.id.exitBtn);// ����
        exitButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Dialog_Exit de = new Dialog_Exit(Main.this);
                de.show();
            }
        });
    }

    /** �ڷΰ��� �̺�Ʈ **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            // �ϵ���� �ڷΰ��� ��ư�� ���� �̺�Ʈ ����
            case KeyEvent.KEYCODE_BACK:
                Dialog_Exit e=new Dialog_Exit(Main.this);
                e.show();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
    /********** ȭ�� ��� ��ȯ(����-����) **********/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
