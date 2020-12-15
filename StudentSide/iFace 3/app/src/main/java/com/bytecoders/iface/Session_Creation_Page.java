package com.bytecoders.iface;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Session_Creation_Page extends AppCompatActivity {

    Button start_session_creation,stop_session_creation;
    String StartTime,EndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session__creation__page);
        start_session_creation=(Button)findViewById(R.id.start_session_creation);
        stop_session_creation=(Button)findViewById(R.id.stop_session_creation);
        final String id=getIntent().getStringExtra("sub_id");
        final String sub_code=getIntent().getStringExtra("sub_code");
        final DatabaseReference db= FirebaseDatabase.getInstance().getReference("code").child(id);
        start_session_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //DatabaseReference db= FirebaseDatabase.getInstance().getReference("code").child(id);
                String str=getAlphaNumericString();
                while(str.equalsIgnoreCase("ashish"))
                {
                    str=getAlphaNumericString();
                }
                String patt=pattern_code_generator();
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                StartTime = sdf.format(d);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                cal.add(Calendar.MINUTE, 10);
                EndTime = sdf.format(cal.getTime());
                Date c = Calendar.getInstance().getTime();
                //System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                SessionCode s=new SessionCode(sub_code,str,patt,StartTime,EndTime,formattedDate);
                db.setValue(s);
            }
        });

        stop_session_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionCode s=new SessionCode(sub_code,"ashish","pattern","00:00","00:00","01-01-2001");
                db.setValue(s);
            }
        });
    }
    public String getAlphaNumericString() {
        int n = 7;
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