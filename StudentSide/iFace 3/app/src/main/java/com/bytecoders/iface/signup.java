package com.bytecoders.iface;

//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class signup extends AppCompatActivity {

    private EditText emailId, password, fname, lname, rollno, sem, affilation;
    private LinearLayout roleSelection, student_section, teacher_section;
    private Button createAccount,take_photo;
    private Spinner roleSpinner;
    private ArrayAdapter<String> roleAdapter;
    private final List<String>  role = new ArrayList<>(Arrays.asList("Select Role","student","teacher","admin"));
    private String firstName,lastName,email,pass,aff,roleVal;
    private int roll,semester,shift;
    Map<Integer,String> map;
    // Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef_admin,dbRef_user,dbRef_std,dbRef_tech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        map=new HashMap<>();
        map.put(1,"CHT101-MAT101-CET101");
        map.put(2,"ST101-PHT101-MAT102");
        setReferneces();

        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                roleVal = role.get(i);
                if(!roleVal.equals(""))Toast.makeText(getApplicationContext(), roleVal,Toast.LENGTH_LONG).show();

                if(roleVal.equalsIgnoreCase("student")){

                    student_section.setVisibility(View.VISIBLE);
                    teacher_section.setVisibility(View.GONE);
                }else if(roleVal.equalsIgnoreCase("teacher")){
                    teacher_section.setVisibility(View.VISIBLE);
                    student_section.setVisibility(View.GONE);

                }else if(roleVal.equalsIgnoreCase("admin")){
                    student_section.setVisibility(View.GONE);
                    teacher_section.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"Please select a role",Toast.LENGTH_LONG).show();
            }
        });

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Train.class);
                startActivity(intent);
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dbRef = FirebaseDatabase.getInstance().getReference("student");
                signUp();
            }
        });

    }

    private void setReferneces() {
        emailId = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        rollno = findViewById(R.id.rollno);
        sem = findViewById(R.id.sem);
        affilation = findViewById(R.id.affilation);
        roleSelection = findViewById(R.id.role_selection);
        student_section = findViewById(R.id.student_section);
        teacher_section = findViewById(R.id.teacher_section);
        createAccount = findViewById(R.id.create_account);
        take_photo=findViewById(R.id.take_photo);

        roleSpinner =findViewById(R.id.role);
        roleAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item, role);
        roleSpinner.setAdapter(roleAdapter);
        // firebase
        mAuth = FirebaseAuth.getInstance();
        dbRef_admin = FirebaseDatabase.getInstance().getReference("admin");
        dbRef_user= FirebaseDatabase.getInstance().getReference("user");
        dbRef_tech= FirebaseDatabase.getInstance().getReference("teacher");
        dbRef_std= FirebaseDatabase.getInstance().getReference("student");

    }

    private void signUp() {
        firstName= fname.getText().toString();
        lastName = lname.getText().toString();
        email = emailId.getText().toString();
        pass  = password.getText().toString();
        if(firstName.isEmpty())
        {
            fname.setError("Name Required");
            fname.requestFocus();
            return;
        }
        if(lastName.isEmpty())
        {
            lname.setError("Name Required");
            lname.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            emailId.setError("Name Required");
            emailId.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            password.setError("Name Required");
            password.requestFocus();
            return;
        }
        if(roleVal.equalsIgnoreCase("student")){
            roll = Integer.parseInt(rollno.getText().toString());
            semester = Integer.parseInt(sem.getText().toString());
            Toast.makeText(getApplicationContext(),email+" "+pass,Toast.LENGTH_LONG).show();
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Student Registered Successfully",Toast.LENGTH_LONG).show();
                    }
                    else
                    {

                    }

                }
            });
            String id_user=dbRef_user.push().getKey();
            //String id_std=dbRef_std.push().getKey();
            String ch[]=map.get(semester).split("-");
            //Toast.makeText(getApplicationContext(),ch[0]+" "+ch[1]+" "+ch[2],Toast.LENGTH_LONG).show();
            Student s=new Student(id_user,email,pass,firstName,lastName,roll,semester,ch[0]+"-0-0",ch[1]+"-0-0",ch[2]+"-0-0");
            User u=new User(id_user,email,pass,roleVal);
            dbRef_user.child(id_user).setValue(u);
            dbRef_std.child(id_user).setValue(s);
            finish();

        }else if(roleVal.equalsIgnoreCase("teacher")){
            aff = affilation.getText().toString();

            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Teacher Registered Successfully",Toast.LENGTH_LONG).show();
                    }
                }
            });
            String id_user=dbRef_user.push().getKey();
            //String id_tech=dbRef_tech.push().getKey();
            Teacher t=new Teacher(aff,email,pass,firstName,lastName);
            User u=new User(id_user,email,pass,roleVal);
            dbRef_user.child(id_user).setValue(u);
            dbRef_tech.child(id_user).setValue(t);
            finish();

        }else if(roleVal.equalsIgnoreCase("admin")){
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Admin Registered Successfully",Toast.LENGTH_LONG).show();
                    }
                }
            });
            String id_user=dbRef_user.push().getKey();
            //String id_tech=dbRef_tech.push().getKey();
            Admin a=new Admin(firstName,lastName,email,pass);
            User u=new User(id_user,email,pass,roleVal);
            dbRef_user.child(id_user).setValue(u);
            dbRef_admin.child(id_user).setValue(a);
            finish();

        }else{
            Toast.makeText(getApplicationContext(),"Error: Role not selected",Toast.LENGTH_SHORT).show();
        }


    }
}

