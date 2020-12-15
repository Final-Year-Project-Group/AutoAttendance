package com.bytecoders.iface;

//import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.os.Bundle;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class subject_list extends AppCompatActivity {

    EditText tech_add;
    ListView sub1_list;
    AlertDialog.Builder builder;
    String email_id;
    int x;
    ArrayList<String> sem, sem_code;
    DatabaseReference dbRef_sub, dbRef_code, dbRef_tech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        sub1_list = (ListView) findViewById(R.id.sub1_list);
        dbRef_sub = FirebaseDatabase.getInstance().getReference("subject");
        dbRef_code = FirebaseDatabase.getInstance().getReference("code");
        dbRef_tech = FirebaseDatabase.getInstance().getReference("teacher");
        Intent k = getIntent();
        sem = k.getStringArrayListExtra("sem");
        sem_code = k.getStringArrayListExtra("sem_code");
        String s[] = new String[sem.size()];
        for (int i = 0; i < sem.size(); i++) s[i] = sem.get(i);
        String ss[] = new String[sem_code.size()];
        for (int i = 0; i < sem_code.size(); i++) ss[i] = sem_code.get(i);
        MyListAdapterTeacher adapter = new MyListAdapterTeacher(this, s, ss);
        sub1_list = (ListView) findViewById(R.id.sub1_list);
        sub1_list.setAdapter(adapter);

        sub1_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PopupMenu popup = new PopupMenu(subject_list.this, view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_opt, popup.getMenu());
                x = i;
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getApplicationContext(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.add:
                                showAlertDialogButtonClicked();

                        }
                        return true;
                    }
                });

                popup.show();
            }
        });
    }


    public void showAlertDialogButtonClicked() {
        // create an alert builder
        builder = new AlertDialog.Builder(this);
        //builder.setTitle("Email Verification");
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_add, null);
        builder.setView(customLayout);
        tech_add = customLayout.findViewById(R.id.tech_add);

        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                email_id = tech_add.getText().toString();
                dbRef_tech.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Teacher t = (Teacher) ds.getValue(Teacher.class);
                            if (email_id.equalsIgnoreCase(t.getEmail())) {
                                String id_sub = dbRef_sub.push().getKey();
                                //String id_code = dbRef_code.push().getKey();
                                Subject s = new Subject(id_sub,sem.get(x), sem_code.get(x), t.getAffination()+" "+t.getFname()+" "+t.getLname() ,email_id);
                                SessionCode sc = new SessionCode(sem_code.get(x), "ashish","pattern","00:00","00:00","01-01-2001");
                                dbRef_sub.child(id_sub).setValue(s);
                                dbRef_code.child(id_sub).setValue(sc);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}