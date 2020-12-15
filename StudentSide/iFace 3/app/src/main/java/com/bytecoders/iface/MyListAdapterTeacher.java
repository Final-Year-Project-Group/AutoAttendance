package com.bytecoders.iface;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyListAdapterTeacher extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] sem_sub;
    private final String[] sem_sub_code;

    public MyListAdapterTeacher(Activity context, String[] sem_sub,String[] sem_sub_code) {
        super(context, R.layout.custom_listview_teacher, sem_sub);

        this.context=context;
        this.sem_sub=sem_sub;
        this.sem_sub_code=sem_sub_code;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_listview_teacher, null,true);


        TextView subName = (TextView) rowView.findViewById(R.id.sub_name);
        TextView subCode = (TextView) rowView.findViewById(R.id.sub_code);
        subName.setText(sem_sub[position]);
        subCode.setText(sem_sub_code[position]);

        return rowView;

    }
}
