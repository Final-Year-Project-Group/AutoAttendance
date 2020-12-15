package com.example.attendancemarker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class HomeActivity extends AppCompatActivity {
    private TextView professorName;
    private Button signOut,start_attendance_button;
    private SharedPreferences loginDetails;
    String lecture_code,StartTime,EndTime;
    String correct_pattern_code,sub_id,sub_code;
    Teacher t;
    private ArrayAdapter<String> roleAdapter;
    ArrayList<String> sn,sc,si;
    private Spinner spin_id;
    int idx=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //sub_id=getIntent().getStringExtra("sub_id");
        //Toast.makeText(getApplicationContext(),"Subject_id= "+sub_id,Toast.LENGTH_LONG).show();
//        final DatabaseReference db= FirebaseDatabase.getInstance().getReference("code").child(sub_id);

        t=(Teacher) getIntent().getSerializableExtra("tech_obj");
        sn=getIntent().getStringArrayListExtra("subn");
        sc=getIntent().getStringArrayListExtra("subc");
        si=getIntent().getStringArrayListExtra("subi");
//        sub_code=getIntent().getStringExtra("sub_code");
        professorName = findViewById(R.id.salutation);
        start_attendance_button = findViewById(R.id.start_attendance);
        professorName.setText("Hello Prof. "+t.getFname()+" "+t.getLname());
        signOut = findViewById(R.id.signOutButton);
        spin_id=findViewById(R.id.spin_id);
        //spin_id =findViewById(R.id.spin_id);
        roleAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item, sn);
        spin_id.setAdapter(roleAdapter);

        spin_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                idx=i;
                //Toast.makeText(getApplicationContext(), ""+idx, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"Please select a role",Toast.LENGTH_LONG).show();
            }
        });


        start_attendance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                lecture_code = lecture_code_generation();
//                /*
//                    Use this lecture code to upload to server
//                */
                //System.out.println(lecture_code);
                //Toast.makeText(getApplicationContext(), ""+idx, Toast.LENGTH_SHORT).show();
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                StartTime = sdf.format(d);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                cal.add(Calendar.MINUTE, 10);
                EndTime = sdf.format(cal.getTime());
                /*
                     StartTime and EndTime are for class code
                */
                Date c = Calendar.getInstance().getTime();
                //System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                lecture_code = lecture_code_generation();
                while(lecture_code.equalsIgnoreCase("ashish"))
                {
                    lecture_code=lecture_code_generation();
                }
                correct_pattern_code = pattern_code_generator();
                DatabaseReference db= FirebaseDatabase.getInstance().getReference("code").child(si.get(idx));
                SessionCode s=new SessionCode(sc.get(idx),lecture_code,correct_pattern_code,StartTime,EndTime,formattedDate);
                db.setValue(s);
                /*
                    correct_pattern_code containing correct pattern
                */
                Intent intent = new Intent(HomeActivity.this,WaitingActivity.class);
                intent.putExtra("Pattern",correct_pattern_code);
                intent.putExtra("tech_obj",t);
//                intent.putExtra("sub_id",si.get(idx));
//                intent.putExtra("sub_code",sc.get(idx));
                intent.putExtra("idx",String.valueOf(idx));
                intent.putStringArrayListExtra("subn",sn);
                intent.putStringArrayListExtra("subc",sc);
                intent.putStringArrayListExtra("subi",si);
                startActivity(intent);
                finish();
            }
        });
    }


    public String lecture_code_generation() {
        int n = 6;
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    private String pattern_code_generator(){
        String[] possible_patterns = {"012465", "0124653", "01246537","012465378", "01246538", "012465387", "0124657", "01246573", "012465738",
                                      "01246578", "012465783", "0124658"};
        int index = (int)(12 * Math.random());
        return possible_patterns[index];
    }
}