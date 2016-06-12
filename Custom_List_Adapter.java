package com.gunhwan.mindmap;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_List_Adapter extends ArrayAdapter<Custom_List_Data> {
    static int Id = 0;
    static String fname = "";
    private ArrayList<Custom_List_Data> items;

    public Custom_List_Adapter(Context context, int textViewResourceId,
                               ArrayList<Custom_List_Data> items) {
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final Custom_List_Data custom_list_data = items.get(position);
        fname = custom_list_data.getMain_Title();
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.custom_list, null);
        }
        Button btn = (Button) v.findViewById(R.id.del);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                getContext().deleteFile(
                        custom_list_data.getMain_Title() + ".dat"); // �ȵ�
                remove(custom_list_data);
            }
        });
        // �ϳ��� �̹������ 2���� �ؽ�Ʈ�� ������ �޾ƿ´�.
        ImageView iv = (ImageView) v.findViewById(R.id.custom_list_image);
        TextView tv_Main = (TextView) v
                .findViewById(R.id.custom_list_title_main);
        TextView tv_Sub = (TextView) v.findViewById(R.id.custom_list_title_sub);
        LayoutParams params = (LayoutParams) iv.getLayoutParams();
        params.width = 120;
        params.height = 120;
        // existing height is ok as is, no need to edit it
        iv.setLayoutParams(params);
        // ���� item�� position�� �´� �̹����� ���� �־��ش�.
        iv.setBackgroundResource(custom_list_data.getImage_ID());
        tv_Main.setText(custom_list_data.getMain_Title());
        tv_Sub.setText(custom_list_data.getSub_Title());
        // }
        return v;
    }
}

class Custom_List_Data {
    private int Image_ID;
    private String Main_Title;
    private String Sub_Title;

    public Custom_List_Data(int _Image_ID, String _Main_Title, String _Sub_Title) {
        this.setImage_ID(_Image_ID);
        this.setMain_Title(_Main_Title);
        this.setSub_Title(_Sub_Title);
    }

    public int getImage_ID() {
        return Image_ID;
    }

    public void setImage_ID(int image_ID) {
        Image_ID = image_ID;
    }

    public String getMain_Title() {
        return Main_Title;
    }

    public void setMain_Title(String main_Title) {
        Main_Title = main_Title;
    }

    public String getSub_Title() {
        return Sub_Title;
    }

    public void setSub_Title(String sub_Title) {
        Sub_Title = sub_Title;
    }

    public Custom_List_Data getData_ID() {
        return this;
    }
}