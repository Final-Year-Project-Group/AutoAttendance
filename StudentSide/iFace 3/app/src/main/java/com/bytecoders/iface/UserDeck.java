package com.bytecoders.iface;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserDeck extends AppCompatActivity {

    Button mTrain,mRecognize,mShowAt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_deck);

        mTrain = findViewById(R.id.btnTrain);
        mTrain.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), Train.class);
            startActivity(intent);
        });
        mRecognize = findViewById(R.id.btnRecognize);
        mRecognize.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(),Recognize.class);
            startActivity(intent);
        });
        mShowAt = findViewById(R.id.showat);
        mShowAt.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(),ShowAttendance.class);
            startActivity(intent);
        });
    }
}
