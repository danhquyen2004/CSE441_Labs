package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edt_a, edt_b, edt_result;
    Button btn_sum, btn_clear;
    TextView txt_history;
    String history = "";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edt_a = findViewById(R.id.edt_a);
        edt_b = findViewById(R.id.edt_b);
        edt_result = findViewById(R.id.edt_result);
        btn_sum = findViewById(R.id.btn_sum);
        btn_clear = findViewById(R.id.btn_clear);
        txt_history = findViewById(R.id.txt_history);
        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        history = myPrefs.getString("history", "");
        txt_history.setText(history);
        btn_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(edt_a.getText().toString());
                int b = Integer.parseInt(edt_b.getText().toString());
                int result = a + b;
                edt_result.setText(String.valueOf(result));
                history += a + " + " + b + " = " + result;
                txt_history.setText(history);
                history +="\n";
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_a.setText("");
                edt_b.setText("");
                edt_result.setText("");
                history = "";
                txt_history.setText(history);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("history", history);
        editor.commit();

    }
}