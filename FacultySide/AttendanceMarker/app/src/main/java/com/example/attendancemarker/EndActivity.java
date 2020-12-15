package com.example.attendancemarker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class EndActivity extends AppCompatActivity {

    Teacher t;
    String sub_id,sub_code,idx;
    ArrayList<String> sn,sc,si;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        t=(Teacher) getIntent().getSerializableExtra("tech_obj");
        sn=getIntent().getStringArrayListExtra("subn");
        sc=getIntent().getStringArrayListExtra("subc");
        si=getIntent().getStringArrayListExtra("subi");
        idx=getIntent().getStringExtra("idx");
        Toast.makeText(getApplicationContext(),"Attendence Process Completed Successfully",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(EndActivity.this,HomeActivity.class);
        intent.putExtra("tech_obj",t);
        intent.putStringArrayListExtra("subn",sn);
        intent.putStringArrayListExtra("subc",sc);
        intent.putStringArrayListExtra("subi",si);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}