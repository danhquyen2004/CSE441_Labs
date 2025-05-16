package com.example.intent_service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageButton btnplay, btnstop;
    Boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnplay = findViewById(R.id.imgstart);
        btnstop = findViewById(R.id.imgstop);
// Xử lý click
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Khai báo Intent công khai để khởi động Service
                Intent intent1 = new Intent(MainActivity.this, Myservice.class);
                startService(intent1);
                if (flag == true)
                {
                    btnplay.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
                    flag = false;
                }
                else
                {
                    btnplay.setImageResource(android.R.drawable.ic_media_play);
                    flag = true;
                }
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new
                        Intent(MainActivity.this,Myservice.class);
                stopService(intent2);
                btnplay.setImageResource(android.R.drawable.ic_media_play);
                flag = true;
            }
        });
    }
}