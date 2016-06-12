package com.gunhwan.mindmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Listra extends Activity implements OnItemClickListener {
	/** Called when the activity is first created. */
	private static ArrayList<Custom_List_Data> Array_Data;
	static Custom_List_Data data;
	private Custom_List_Adapter adapter;
	private ListView custom_list;
	static String s;
	String text = null;
	ArrayList<Rtg> load;
	Calendar c = Calendar.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.list);
		
		s = String.format("%4d", c.get(Calendar.YEAR)) + "_" + String.format("%02d",c.get(Calendar.MONTH) + 1) + "_"
				+ String.format("%02d",c.get(Calendar.DAY_OF_MONTH)) + "_" + String.format("%02d",c.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d",c.get(Calendar.MINUTE));
		Array_Data = new ArrayList<Custom_List_Data>();

		// try {
		String path = getFilesDir().toString();
		File f = new File(path);
		String[] fn = f.list();
		for (int i = 0; i < fn.length; i++) {
			// deleteFile(fn[i]);
			editdata(fn[i].replace(".dat", ""));	// 
		}

		if (text != null) {
			editdata(text);
		}
		custom_list = (ListView) findViewById(R.id.list);
		adapter = new Custom_List_Adapter(this,
				android.R.layout.simple_list_item_1, Array_Data);
		custom_list.setAdapter(adapter);
		custom_list.setOnItemClickListener(this);
		if (Array_Data.isEmpty()) {
			Toast.makeText(getApplicationContext(), "No file",
					Toast.LENGTH_SHORT).show();
		}
	}

	public static void editdata(int _Image_ID, String _Main_Title,
			String _Sub_Title) {
		data = new Custom_List_Data(_Image_ID, _Main_Title, _Sub_Title);
		Array_Data.add(data);
	}

	// Overload
	public static void editdata(String _Main_Title) {
		data = new Custom_List_Data(R.drawable.icon_140909, _Main_Title, s);
		Array_Data.add(data);
	}

	@SuppressWarnings("unchecked")
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		data = (Custom_List_Data) parent
				.getItemAtPosition(position);
		try {
			FileInputStream fis = openFileInput(data.getMain_Title() +".dat");// ���� �ҷ�����
			ObjectInputStream ois = new ObjectInputStream(fis);// ������ ��ü �ҷ�����
			load = (ArrayList<Rtg>)ois.readObject();
			ois.close();
			Mapscreen.flag = true;
			Dialog_FileName.openFlag=true;
		} catch (ClassNotFoundException e) {
			Toast.makeText(this, "file not found", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(this, "Read Fail", Toast.LENGTH_SHORT).show();
		}
		// ����Ʈ�� ���Ѵ�.
		// ���ؽ�Ʈ�� ���� ��Ƽ��Ƽ��, ���� ��Ƽ��Ƽ�� MapscreenActivity �� �����Ѵ�.
		Intent intent = new Intent(getApplicationContext(), Mapscreen.class);
		intent.putExtra("frtg",load);	//	"frtg" �� load ��ü�� �����Ŵ
		// ��Ƽ��Ƽ�� ���Ѵ�.
		startActivity(intent);
	}
}