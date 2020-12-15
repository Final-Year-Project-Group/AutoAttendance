package com.bytecoders.iface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Pattern_Recognition extends AppCompatActivity {
    private int time;
    private Timer timer;
    private String correct_pattern;
    private boolean match;
    TextView counter;
    Student s;
    String sub_code,idx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern__recognition);
        counter = findViewById(R.id.time_view);
        s=(Student) getIntent().getSerializableExtra("std_obj");
        sub_code=getIntent().getStringExtra("sub_code");
        correct_pattern=getIntent().getStringExtra("pattern_code");
        idx=getIntent().getStringExtra("idx");

        time = 30;
        match = false;
        //correct_pattern = "0136";
        counter.setText(time+"");
        final PatternLockView input_pattern = findViewById(R.id.pattern_input);
        startTimer();

        input_pattern.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {}

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {}

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                if(PatternLockUtils.patternToString(input_pattern, pattern).equalsIgnoreCase(correct_pattern)){
                    input_pattern.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                    //timer.cancel();
                    match = true;
                    changeActivity();
                }else{
                    input_pattern.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    Toast.makeText(getApplicationContext(), "Incorrect Pattern!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCleared() {}
        });
    }

    public void startTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        counter.setText(time+"");
                        if (time > 0) {
                            time -= 1;
                            if(time<5){
                                counter.setTextColor(Color.parseColor("#FF0000"));
                            }
                        }
                        else {
                            counter.setText("0");
                            timer.cancel();
                            changeActivity();
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1500);
    }

    public void changeActivity(){
        final DatabaseReference db= FirebaseDatabase.getInstance().getReference("student").child(s.getStd_id());
        if(match){

            if(idx.equals("0"))
            {
                String str[]=s.getSub1().split("-");
                str[1]=String.valueOf(Integer.parseInt(str[1])+1);
                str[2]=String.valueOf(Integer.parseInt(str[2])+1);
                String p=str[0]+"-"+str[1]+"-"+str[2];
                Student stdn=new Student(s.getStd_id(),s.getEmail(),s.getPassword(),s.getFname(),s.getLname(),s.getRollno(),s.getSem(),p,s.getSub2(),s.getSub3());
                db.setValue(stdn);
            }
            else if(idx.equals("1"))
            {
                String str[]=s.getSub2().split("-");
                str[1]=String.valueOf(Integer.parseInt(str[1])+1);
                str[2]=String.valueOf(Integer.parseInt(str[2])+1);
                String p=str[0]+"-"+str[1]+"-"+str[2];
                Student stdn=new Student(s.getStd_id(),s.getEmail(),s.getPassword(),s.getFname(),s.getLname(),s.getRollno(),s.getSem(),s.getSub1(),p,s.getSub3());
                db.setValue(stdn);
            }
            else if(idx.equals("2"))
            {
                String str[]=s.getSub2().split("-");
                str[1]=String.valueOf(Integer.parseInt(str[1])+1);
                str[2]=String.valueOf(Integer.parseInt(str[2])+1);
                String p=str[0]+"-"+str[1]+"-"+str[2];
                Student stdn=new Student(s.getStd_id(),s.getEmail(),s.getPassword(),s.getFname(),s.getLname(),s.getRollno(),s.getSem(),s.getSub1(),s.getSub2(),p);
                db.setValue(stdn);
            }
            Toast.makeText(getApplicationContext(), "Marked has Present", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Sorry! Your pattern was incorrect.",Toast.LENGTH_LONG).show();
            if(idx.equals("0"))
            {
                String str[]=s.getSub1().split("-");
                str[1]=String.valueOf(Integer.parseInt(str[1]));
                str[2]=String.valueOf(Integer.parseInt(str[2])+1);
                String p=str[0]+"-"+str[1]+"-"+str[2];
                Student stdn=new Student(s.getStd_id(),s.getEmail(),s.getPassword(),s.getFname(),s.getLname(),s.getRollno(),s.getSem(),p,s.getSub2(),s.getSub3());
                db.setValue(stdn);
            }
            else if(idx.equals("1"))
            {
                String str[]=s.getSub2().split("-");
                str[1]=String.valueOf(Integer.parseInt(str[1]));
                str[2]=String.valueOf(Integer.parseInt(str[2])+1);
                String p=str[0]+"-"+str[1]+"-"+str[2];
                Student stdn=new Student(s.getStd_id(),s.getEmail(),s.getPassword(),s.getFname(),s.getLname(),s.getRollno(),s.getSem(),s.getSub1(),p,s.getSub3());
                db.setValue(stdn);
            }
            else if(idx.equals("2"))
            {
                String str[]=s.getSub2().split("-");
                str[1]=String.valueOf(Integer.parseInt(str[1]));
                str[2]=String.valueOf(Integer.parseInt(str[2])+1);
                String p=str[0]+"-"+str[1]+"-"+str[2];
                Student stdn=new Student(s.getStd_id(),s.getEmail(),s.getPassword(),s.getFname(),s.getLname(),s.getRollno(),s.getSem(),s.getSub1(),s.getSub2(),p);
                db.setValue(stdn);
            }
            Toast.makeText(getApplicationContext(), "Marked has Absent", Toast.LENGTH_SHORT).show();

        }
        finish();
    }
}