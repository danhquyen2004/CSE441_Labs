package com.example.project_cal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnSub, btnMul, btnDiv;
    EditText etNum1, etNum2, etResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSubtract);
        btnMul = findViewById(R.id.btnMultiply);
        btnDiv = findViewById(R.id.btnDivide);
        etNum1 = findViewById(R.id.edtA);
        etNum2 = findViewById(R.id.edtB);
        etResult = findViewById(R.id.edtKQ);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(etNum1.getText().toString());
                int b = Integer.parseInt(etNum2.getText().toString());
                int result = a + b;
                etResult.setText(String.valueOf(result));
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(etNum1.getText().toString());
                int b = Integer.parseInt(etNum2.getText().toString());
                int result = a - b;
                etResult.setText(String.valueOf(result));
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(etNum1.getText().toString());
                int b = Integer.parseInt(etNum2.getText().toString());
                int result = a * b;
                etResult.setText(String.valueOf(result));
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(etNum1.getText().toString());
                int b = Integer.parseInt(etNum2.getText().toString());
                if (b == 0) {
                    etResult.setText("Không thể chia cho 0");
                } else {
                    int result = a / b;
                    etResult.setText(String.valueOf(result));
                }
            }
        });

    }
}