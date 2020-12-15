package com.bytecoders.iface;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    TextView stats;
    Button btn;
    Button login,signup;
    EditText email_id,password_id;
    AlertDialog.Builder builder;
    String email,password;
    private DatabaseReference dbRef_admin,dbRef_user,dbRef_std,dbRef_tech,dbRef_sub;
    private FirebaseAuth mAuth;


    //Permissions
    private boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setReferences();
        //btn = findViewById(R.id.signInBtn);

        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.GET_ACCOUNTS,Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.WAKE_LOCK,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,Manifest.permission.INTERNET,Manifest.permission.UPDATE_DEVICE_STATS,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.WRITE_SETTINGS};

            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, 1 );
            }
        }

        login.setOnClickListener(view -> showAlertDialogButtonClicked(view));
//        signup.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(),signup.class);
//            startActivity(intent);
//        });
        //btn.setOnClickListener(v->startActivity(new Intent(getApplicationContext(),UserDeck.class)));

    }
    private void setReferences(){

        dbRef_admin = FirebaseDatabase.getInstance().getReference("admin");
        dbRef_user= FirebaseDatabase.getInstance().getReference("user");
        dbRef_tech= FirebaseDatabase.getInstance().getReference("teacher");
        dbRef_std= FirebaseDatabase.getInstance().getReference("student");
        dbRef_sub = FirebaseDatabase.getInstance().getReference("subject");
        mAuth = FirebaseAuth.getInstance();
        login =(Button)findViewById(R.id.login_btn);
        //signup =(Button)findViewById(R.id.signup);

    }

    public void showAlertDialogButtonClicked(View view) {
        // create an alert builder
        final int id =view.getId();
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Email Verification").setCancelable(false);
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);
        email_id = customLayout.findViewById(R.id.email_id);
        password_id = customLayout.findViewById(R.id.password_id);
        // add a button
        builder.setPositiveButton("CHECK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signIn();
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void signIn(){
        email=email_id.getText().toString().trim();
        password=password_id.getText().toString().trim();
        if(email.isEmpty())
        {
            email_id.setError("Email Required");
            email_id.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            password_id.setError("PassWord Required");
            password_id.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                    dbRef_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren())
                            {
                                User u=(User)ds.getValue(User.class);
                                if(email.equals(u.getEmail().toString()))
                                {
                                    String res=u.getId();
                                    if(u.getRole().equalsIgnoreCase("Student"))
                                    {
                                        dbRef_std.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot ds:dataSnapshot.getChildren())
                                                {
                                                    Student s=(Student)ds.getValue(Student.class);
                                                    if(email.equals(s.getEmail().toString()))
                                                    {
                                                        Intent i=new Intent(getApplicationContext(),student_page.class);
                                                        i.putExtra("std_obj",s);
                                                        startActivity(i);
                                                        break;
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        break;
                                    }
                                    else if(u.getRole().equalsIgnoreCase("Teacher"))
                                    {
                                        dbRef_tech.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot ds:dataSnapshot.getChildren())
                                                {
                                                    final Teacher t=(Teacher)ds.getValue(Teacher.class);
                                                    if(email.equals(t.getEmail().toString()))
                                                    {
                                                        final ArrayList<String> sn=new ArrayList<String>();
                                                        final ArrayList<String> sc=new ArrayList<String>();
                                                        final ArrayList<String> si=new ArrayList<String>();
                                                        dbRef_sub.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                                    Subject ss = (Subject) ds.getValue(Subject.class);
                                                                    if(ss.getTech_email().toString().equalsIgnoreCase(t.getEmail().toString()))
                                                                    {
                                                                        sn.add(ss.getSub_name().toString());
                                                                        sc.add(ss.getSub_code().toString());
                                                                        si.add(ss.getSub_id().toString());


                                                                    }
                                                                }
                                                                Intent i=new Intent(getApplicationContext(),Teacher_Page.class);
                                                                i.putStringArrayListExtra("subn",sn);
                                                                i.putStringArrayListExtra("subc",sc);
                                                                i.putStringArrayListExtra("subi",si);
                                                                i.putExtra("tech_obj",t);
                                                                startActivity(i);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        break;
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        break;
                                    }
                                    else if(u.getRole().equalsIgnoreCase("Admin"))
                                    {
                                        dbRef_admin.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot ds:dataSnapshot.getChildren())
                                                {
                                                    Admin a=(Admin)ds.getValue(Admin.class);
                                                    if(email.equals(a.getEmail().toString()))
                                                    {
                                                        Intent i=new Intent(getApplicationContext(),AdminPage.class);
                                                        i.putExtra("adm_obj",a);
                                                        startActivity(i);
                                                        break;
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        Toast.makeText(getApplicationContext(),"Admin Present",Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                    Toast.makeText(getApplicationContext(),"No Such User Exits",Toast.LENGTH_LONG).show();
            }
        });
    }

}
