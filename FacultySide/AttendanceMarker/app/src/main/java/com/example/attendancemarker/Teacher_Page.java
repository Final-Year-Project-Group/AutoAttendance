package com.example.attendancemarker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Teacher_Page extends AppCompatActivity {

    ListView sub_list_tech;
    DatabaseReference dbRef_sub;
    Teacher t;
    int k;
    ArrayList<String> sn,sc,si;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__page);
        dbRef_sub = FirebaseDatabase.getInstance().getReference("subject");
        t=(Teacher) getIntent().getSerializableExtra("tech_obj");
        sn=getIntent().getStringArrayListExtra("subn");
        sc=getIntent().getStringArrayListExtra("subc");
        si=getIntent().getStringArrayListExtra("subi");
        final String sb_n[] = new String[sn.size()];
        for (int i = 0; i < sn.size(); i++) sb_n[i] = sn.get(i).toString();
        final String sb_c[] = new String[sc.size()];
        for (int i = 0; i < sc.size(); i++) sb_c[i] = sc.get(i).toString();
        final String sb_i[] = new String[si.size()];
        for (int i = 0; i < si.size(); i++) sb_i[i] = si.get(i).toString();
        MyListAdapter adapter = new MyListAdapter(this, sb_n, sb_c);
        sub_list_tech=(ListView)findViewById(R.id.sub_list_tech);
        sub_list_tech.setAdapter(adapter);
        sub_list_tech.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PopupMenu popup = new PopupMenu(Teacher_Page.this,view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_tech, popup.getMenu());
                final int x=i;
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();
                        switch(id)
                        {
                            case R.id.take_attendence:
                                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                                intent.putExtra("tech_obj",t);
                                intent.putExtra("sub_id",sb_i[x]);
                                intent.putExtra("sub_code",sb_c[x]);
                                startActivity(intent);

                        }

                        return true;
                    }
                });

                popup.show();

            }
        });
    }

}