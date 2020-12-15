package com.bytecoders.iface;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowAttendance extends AppCompatActivity {

    Student stu;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);
        tv = findViewById(R.id.ar);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uname = mAuth.getCurrentUser().getEmail().split("@+")[0].toUpperCase();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("students").document("3-CSE-B").collection(uname).document("Subs").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                stu = documentSnapshot.toObject(Student.class);
            }
        });
        if(stu==null)
        {
            stu = new Student();
        }
//        String ans = "CD: "+stu.CD+"\nWT "+stu.WT+"\nOOAD: "+stu.OOAD+"\nEC: "+stu.EC+"\nEH: "+stu.EH+"\nCET: "+stu.CET;
//        Double percentage =  ((stu.CD+stu.CET+stu.EH+stu.EC+stu.OOAD+stu.WT)/6D)*100;
//        ans+="\n"+percentage;
//        tv.setText(ans);
    }
}
