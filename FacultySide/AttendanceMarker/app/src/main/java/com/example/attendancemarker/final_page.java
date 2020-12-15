package com.example.attendancemarker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class final_page extends AppCompatActivity {

    String sub_id,sub_code;
    Teacher t;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_page);
        tv=findViewById(R.id.tv);
        t=(Teacher) getIntent().getSerializableExtra("tech_obj");
        sub_id=getIntent().getStringExtra("sub_id");
        sub_code=getIntent().getStringExtra("sub_code");
        tv.setText(sub_id+" "+sub_code);
    }
}