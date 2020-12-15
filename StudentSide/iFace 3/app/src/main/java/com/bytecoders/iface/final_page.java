package com.bytecoders.iface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class final_page extends AppCompatActivity {

    TextView final_message;
    Student s;
    String sub_code,idx;
    private DatabaseReference dbRef_std;
    Button con_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_page);
        Bundle bundle = getIntent().getExtras();
        boolean match = bundle.getBoolean("flag");
        Toast.makeText(getApplicationContext(),"ashish",Toast.LENGTH_LONG).show();
        dbRef_std= FirebaseDatabase.getInstance().getReference("student");
        s=(Student) getIntent().getSerializableExtra("std_obj");
        sub_code=getIntent().getStringExtra("sub_code");
        idx=getIntent().getStringExtra("idx");
        final_message = findViewById(R.id.final_page);
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
        }
        else{
            final_message.setText("Sorry! Your pattern was incorrect.");
        }
    }
//    public void onBackPressed(){
//        finishAffinity();
//    }
}