package com.bytecoders.iface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Session_Availability extends AppCompatActivity {
    EditText session_code;
    Button gen_btn,chk_btn;
    Student s;
    String sub_code,code,idx,sub_name;
    TextView class_name,class_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session__availability);
        session_code=findViewById(R.id.session_code);
        gen_btn=findViewById(R.id.gen_btn);
        chk_btn=findViewById(R.id.chk_btn);
        class_name=findViewById(R.id.class_name);
        class_code=findViewById(R.id.class_code);
        s=(Student) getIntent().getSerializableExtra("std_obj");
        sub_code=getIntent().getStringExtra("sub_code");
        idx=getIntent().getStringExtra("idx");
        sub_name=getIntent().getStringExtra("sub_name");
        class_name.setText("Class Name: "+sub_name);
        class_code.setText("Class Code: "+sub_code);
        gen_btn.setOnClickListener((View.OnClickListener) view -> {
            DatabaseReference dbRef_code= FirebaseDatabase.getInstance().getReference("code");

            dbRef_code.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()) {
                        SessionCode sc = (SessionCode) ds.getValue(SessionCode.class);
                        //Toast.makeText(getApplicationContext(),sc+"",Toast.LENGTH_LONG).show();
                        if((sc.getClass_code().toString().equalsIgnoreCase(sub_code)) && !sc.getCode().equals("ashish"))
                        {
                            String c=sc.getCode();
                            session_code.setText(c);
                        }
//
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"Session is yet to Start",Toast.LENGTH_LONG).show();
                }
            });
        });

        chk_btn.setOnClickListener((View.OnClickListener) view -> {
            code=session_code.getText().toString();
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            String StartTime[] = sdf.format(d).split(":");
            String v=StartTime[0]+"."+StartTime[1];
            double val=Double.parseDouble(v);

            Date c = Calendar.getInstance().getTime();
            //System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            DatabaseReference dbRef_code= FirebaseDatabase.getInstance().getReference("code");
            dbRef_code.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int temp=0;
                    for(DataSnapshot ds:dataSnapshot.getChildren()) {
                        SessionCode sc = (SessionCode) ds.getValue(SessionCode.class);
                        String st_time[]=sc.getStart_time().split(":");
                        String ed_time[]=sc.getEnd_time().split(":");
                        String l=st_time[0]+"."+st_time[1];
                        String h=ed_time[0]+"."+ed_time[1];
                        double val1=Double.parseDouble(l);
                        double val2=Double.parseDouble(h);

                        if((!code.equals("ashish")) && (sc.getClass_code().toString().equals(sub_code)) && (sc.getCode().toString().equals(code)) && sc.getDate().equals(formattedDate) && val>=val1 && val<val2)
                        {
                            temp=1;
                            Intent intent = new Intent(getBaseContext(),Recognize.class);
                            intent.putExtra("std_obj",s);
                            intent.putExtra("sub_code",sub_code);
                            intent.putExtra("idx",idx);
                            intent.putExtra("pattern_code",sc.getPattern_code());
                            startActivity(intent);
                            finish();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"No"+"",Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}