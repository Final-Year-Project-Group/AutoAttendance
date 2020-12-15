package com.bytecoders.iface;

import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class WiFinder extends AppCompatActivity {

    private String secretKey;
    TextView stats;
    Student stu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_finder);

        stats = findViewById(R.id.curr_wifi_stat);

        secretKey = getString(R.string.psswd);
        connectWifi();
    }

    private void connectWifi()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String fd = df.format(c);
        try {
            String ssid = AESHelper.encrypt(fd, secretKey);
            String cntpsswd = AESHelper.encrypt(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()), secretKey);
            if(ssid.length()>9)
                ssid = ssid.substring(0,9);
            if(cntpsswd.length()>9)
                cntpsswd = cntpsswd.substring(0,9);
            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.SSID = String.format("\"%s\"", ssid);
            wifiConfig.preSharedKey = String.format("\"%s\"", cntpsswd);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(this.getApplicationContext())) {

                } else {
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + this.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }


            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//remember id

            int netId = wifiManager.addNetwork(wifiConfig);
            wifiManager.disconnect();
            wifiManager.enableNetwork(netId, true);
            wifiManager.reconnect();

            if(wifiManager.getConnectionInfo()!=null && wifiManager.getConnectionInfo().getSSID().equals(ssid))
            {
                stats.setText("Authentication success");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uname = mAuth.getCurrentUser().getEmail().split("@+")[0].toUpperCase();
                db.collection("students").document("3-CSE-B").collection(uname).document("Subs").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        stu = documentSnapshot.toObject(Student.class);
                    }
                });
                //Depending on TimeTable
                if(stu!=null) {
                    //stu.OOAD += 1;
                    db.collection("students").document("3-CSE-B").collection(uname).document("Subs").set(stu);
                }
                stats.setText("Attendance posted successfully");
                Intent intent=new Intent(getBaseContext(),UserDeck.class);
                startActivity(intent);
            }
        }
        catch (Exception e)
        {
            Log.d("IFACE:WIFI","NOT CONNECTED");
        }
    }
}
