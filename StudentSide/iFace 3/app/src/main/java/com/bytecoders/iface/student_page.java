package com.bytecoders.iface;
//
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class student_page extends AppCompatActivity {

    ListView sub_list;
    ArrayList<ArrayList<String>> al;
    AlertDialog.Builder builder;
    EditText invite_code;
    Student s;
    String code="ashish";
    TextView tv;
    //String sem_sub[][]={{},{"Chemistry","Mathematics I","Mechanic"},{"Computer Programming","Physics","Mathematics II"},{},{"Operating Systems","Object Oriented Programming","System Programming"}};
    //String sem_sub_code[][]={{},{"CHT101","MAT101","CET101"},{"CST101","PHT101","MAT102"},{},{"CST219","CST218","CST221"}};
    Map<String,String> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);
        tv=findViewById(R.id.text_id);
        map=new HashMap<>();
        map.put("CHT101","Chemistry");map.put("MAT101","Mathematics I");map.put("CET101","Mechanic");
        map.put("CST101","Computer Programming");map.put("PHT101","Physics");map.put("MAT102","Mathematics II");
        s=(Student) getIntent().getSerializableExtra("std_obj");
        int sem=s.getSem();
        tv.setText("Hello "+s.getFname()+" "+s.getLname());
        //final String p[]={sem_sub[sem][0],sem_sub[sem][1],sem_sub[sem][2]};
        final String p[]={map.get(s.getSub1().substring(0,6)),map.get(s.getSub2().substring(0,6)),map.get(s.getSub3().substring(0,6))};
//        final String q[]={sem_sub_code[sem][0],sem_sub_code[sem][1],sem_sub_code[sem][2]};
        final String q[]={s.getSub1().substring(0,6),s.getSub2().substring(0,6),s.getSub3().substring(0,6)};
        String w1[]=s.getSub1().split("-");
        String w2[]=s.getSub2().split("-");
        String w3[]=s.getSub3().split("-");
        final String r[]={w1[1]+" / "+w1[2],w2[1]+" / "+w2[2],w3[1]+" / "+w3[2]};
        MyListAdapter adapter=new MyListAdapter(this, p, q,r);
        sub_list=(ListView)findViewById(R.id.sub_list);
        sub_list.setAdapter(adapter);

        sub_list.setOnItemClickListener((adapterView, view, i, l) -> {
            //showAlertDialogButtonClicked(view,i,q);
            Intent intent=new Intent(getApplicationContext(),Session_Availability.class);
            intent.putExtra("std_obj",s);
            intent.putExtra("sub_code",q[i]);
            intent.putExtra("sub_name",p[i]);
            intent.putExtra("idx",i+"");
            //Toast.makeText(getApplicationContext(),""+s.getStd_id()+" "+q[i],Toast.LENGTH_LONG).show();
            startActivity(intent);
        });

    }
    public void showAlertDialogButtonClicked(View view,final int i,final String q[]) {
        // create an alert builder
        final int id =view.getId();
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.code_check, null);
        builder.setView(customLayout);
        invite_code= customLayout.findViewById(R.id.invite_code);
        // add a button
        builder.setPositiveButton("CHECK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                code=invite_code.getText().toString();
                DatabaseReference dbRef_code= FirebaseDatabase.getInstance().getReference("code");
                dbRef_code.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int temp=0;
                        for(DataSnapshot ds:dataSnapshot.getChildren()) {
                            SessionCode sc = (SessionCode) ds.getValue(SessionCode.class);
                            if((sc.getClass_code().toString().equals(q[i])) && (sc.getCode().toString().equals(code)) && (!code.equals("ashish")))
                            {
                                temp=1;
//                                Intent intent=new Intent(getApplicationContext(),Face_Recognition.class);
//                                startActivity(intent);
                                Intent intent = new Intent(getBaseContext(),Recognize.class);
                                intent.putExtra("std_obj",s);
                                startActivity(intent);
                            }
                        }
//                        if(temp==0)
//                        {
//                            Toast.makeText(getApplicationContext(),"Wrong Invite Code",Toast.LENGTH_LONG).show();
//                            invite_code.setError("Wrong Invite Code");
//                            invite_code.requestFocus();
//                            return;
//                            //dialog.cancel();
//                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }).setNegativeButton("Get Code", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                DatabaseReference dbRef_code= FirebaseDatabase.getInstance().getReference("code");
                dbRef_code.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int temp=0;
                        for(DataSnapshot ds:dataSnapshot.getChildren()) {
                            SessionCode sc = (SessionCode) ds.getValue(SessionCode.class);
                            //Toast.makeText(getApplicationContext(),q[i],Toast.LENGTH_LONG).show();

                            if((sc.getClass_code().equalsIgnoreCase(q[i])))
                            {
                                temp=1;
//                                Intent intent=new Intent(getApplicationContext(),Face_Recognition.class);
//                                startActivity(intent);
                                String c=sc.getCode();
                                invite_code.setText(c);
//                                Intent intent = new Intent(getBaseContext(),Recognize.class);
//                                intent.putExtra("std_obj",s);
//                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Session is yet to Start",Toast.LENGTH_LONG).show();
                            }
                        }
//                        if(temp==0)
//                        {
//                            Toast.makeText(getApplicationContext(),"Wrong Invite Code",Toast.LENGTH_LONG).show();
//                            invite_code.setError("Wrong Invite Code");
//                            invite_code.requestFocus();
//                            return;
//                            //dialog.cancel();
//                        }
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