package com.example.attendancemarker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WaitingActivity extends AppCompatActivity {
    private TextView txtProgress;
    private ProgressBar progressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();
    String sub_id,sub_code;
    Teacher t;
    String idx;
    ArrayList<String> sn,sc,si;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        final Intent intent = getIntent();
        final String pattern = intent.getStringExtra("Pattern");
        t=(Teacher) getIntent().getSerializableExtra("tech_obj");
//        sub_id=getIntent().getStringExtra("sub_id");
//        sub_code=getIntent().getStringExtra("sub_code");
        sn=getIntent().getStringArrayListExtra("subn");
        sc=getIntent().getStringArrayListExtra("subc");
        si=getIntent().getStringArrayListExtra("subi");
        idx=getIntent().getStringExtra("idx");
        txtProgress = findViewById(R.id.txtProgress);
        progressBar = findViewById(R.id.progressBar);
        //Toast.makeText(getApplicationContext(),"Wait "+si.get(Integer.parseInt(idx))+"",Toast.LENGTH_LONG).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus <= 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(pStatus);
                            txtProgress.setText(pStatus + " %");
                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus++;
                    if(pStatus > 100){
                        Intent change_activity = new Intent(WaitingActivity.this,Pattern_drawer.class);
                        change_activity.putExtra("Pattern",pattern);
                        change_activity.putExtra("tech_obj",t);
                        change_activity.putExtra("idx",String.valueOf(idx));
                        change_activity.putStringArrayListExtra("subn",sn);
                        change_activity.putStringArrayListExtra("subc",sc);
                        change_activity.putStringArrayListExtra("subi",si);
                        startActivity(change_activity);
                        finish();
                    }
                }
            }
        }).start();
    }
}