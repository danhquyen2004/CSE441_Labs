package com.example.vidu_ptb2;

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

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText edtA, edtB, edtC;
    TextView txtKQ;
    Button btnGiai, btnXoa, btnTieptuc;
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
        edtA = findViewById(R.id.input_a);
        edtB = findViewById(R.id.input_b);
        edtC = findViewById(R.id.input_c);
        btnTieptuc = findViewById(R.id.btn_continue);
        btnGiai = findViewById(R.id.btn_Sumit);
        btnXoa = findViewById(R.id.btn_clear);
        txtKQ = findViewById(R.id.result);


        btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtA.setText("");
                edtB.setText("");
                edtC.setText("");
                edtA.requestFocus();
            }
        });

        btnGiai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int A = Integer.parseInt(edtA.getText().toString());
                int B = Integer.parseInt(edtB.getText().toString());
                int C = Integer.parseInt(edtC.getText().toString());
                String kq = "";
                DecimalFormat dcf = new DecimalFormat("#.00");
                if (A == 0) {
                    if (B == 0) {
                        if (C == 0) {
                            kq = "Phương trình vô số nghiệm";
                        } else {
                            kq = "Phương trình vô nghiệm";
                        }
                    } else {
                        kq = "Pt có n1 No, x = " + dcf.format(-C / B);
                    }
                } else {
                    double delta = B * B - 4 * A * C;
                    if (delta < 0) {
                        kq = "Phương trình vô nghiệm";
                    } else if (delta == 0) {
                        kq = "Pt có No kép x1 = x2 = " + dcf.format(-B / (2.0 * A));
                    } else {
                        kq = "Pt có 2 No phân biệt:\n" +
                                "x1 = " + dcf.format((-B + Math.sqrt(delta)) / (2.0 * A)) + "\n" +
                                "x2 = " + dcf.format((-B - Math.sqrt(delta)) / (2.0 * A));
                    }
                }
                txtKQ.setText(kq);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}