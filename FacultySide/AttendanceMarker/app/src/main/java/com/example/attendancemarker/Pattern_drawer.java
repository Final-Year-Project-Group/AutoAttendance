package com.example.attendancemarker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Pattern_drawer extends AppCompatActivity {
    private BaseSurface surface;
    String sub_id,sub_code;
    Teacher t;
    String idx;
    ArrayList<String> sn,sc,si;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_drawer);
        Intent intent = getIntent();
        t=(Teacher) getIntent().getSerializableExtra("tech_obj");
//        sub_id=getIntent().getStringExtra("sub_id");
//        sub_code=getIntent().getStringExtra("sub_code");
        sn=getIntent().getStringArrayListExtra("subn");
        sc=getIntent().getStringArrayListExtra("subc");
        si=getIntent().getStringArrayListExtra("subi");
        idx=getIntent().getStringExtra("idx");
        String password = intent.getStringExtra("Pattern");
        surface = findViewById(R.id.baseSurface);
        surface.SetPattern(t,sn,sc,si,idx,password,this.getApplicationContext());

    }

    protected void onResume() {
        super.onResume();
        // start the drawing
        surface.startDrawThread();
    }

    protected void onPause()
    {
        // stop the drawing to save cpu time
        surface.stopDrawThread();
        super.onPause();
    }
}