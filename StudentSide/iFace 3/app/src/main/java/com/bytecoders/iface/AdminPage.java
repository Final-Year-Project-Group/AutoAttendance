package com.bytecoders.iface;

//import android.support.v7.app.AppCompatActivity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AdminPage extends AppCompatActivity {

    ListView listView;
    String str[]={"Add (Student/Faculty)","Display Members","Subjects"};
    String sem1[]={"Mathematic I","Chemistry","Mechanics"};
    String sem_code1[]={"MAT101","CHT101","CET101"};
    String sem2[]={"Computer Programming","Physics","Mathematics II"};
    String sem_code2[]={"CST101","PHT101","MAT102"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        listView=(ListView)findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,str);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    Intent intent=new Intent(getApplicationContext(),signup.class);
                    startActivity(intent);
                }
                else if(i==1)
                {
                    PopupMenu popup = new PopupMenu(AdminPage.this,view);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            return true;
                        }
                    });

                    popup.show();
                }
                else if(i==2)
                {
                    PopupMenu popup = new PopupMenu(AdminPage.this,view);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_sublist, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            //Toast.makeText(getApplicationContext(),"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                            int id=item.getItemId();
                            switch(id)
                            {
                                case R.id.one:
                                    Intent io=new Intent(getApplicationContext(),subject_list.class);
                                    io.putStringArrayListExtra("sem", new ArrayList<String>(Arrays.asList(sem1)));
                                    io.putStringArrayListExtra("sem_code", new ArrayList<String>(Arrays.asList(sem_code1)));
                                    startActivity(io);
                                    break;
                                case R.id.two:
                                    Intent it=new Intent(getApplicationContext(),subject_list.class);
                                    it.putStringArrayListExtra("sem", new ArrayList<String>(Arrays.asList(sem2)));
                                    it.putStringArrayListExtra("sem_code", new ArrayList<String>(Arrays.asList(sem_code2)));
                                    startActivity(it);
                                    break;
                            }
                            return true;
                        }
                    });

                    popup.show();
                }
            }
        });
    }
}
