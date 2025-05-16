package com.example.body_mass_index;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtHeight, edtWeight, edtBMI, edtDiagnosis;
    Button btnBMI;
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
        edtName = findViewById(R.id.edt_Name);
        edtHeight = findViewById(R.id.edt_Height);
        edtWeight = findViewById(R.id.edt_Weight);
        edtBMI = findViewById(R.id.edt_Result);
        edtDiagnosis = findViewById(R.id.edt_diagnosis);
        btnBMI = findViewById(R.id.btn_BMI);

        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double Height = Double.parseDouble(edtHeight.getText() + "");
                Double Weight = Double.parseDouble(edtWeight.getText() + "");
                Double BMI = Weight / Math.pow(Height, 2);
                String Diagnosis = "";
                if (BMI < 18) {
                    Diagnosis = "Bạn gầy";
                } else if (BMI < 25) {
                    Diagnosis = "Bạn bình thường";
                } else if (BMI < 30) {
                    Diagnosis = "Bạn béo phì độ 1";
                } else if (BMI < 35) {
                    Diagnosis = "Bạn béo phì độ 2";
                } else {
                    Diagnosis = "Bạn béo phì độ 3";
                }
                DecimalFormat dcf = new DecimalFormat("#.0");
                edtBMI.setText(dcf.format(BMI));
                edtDiagnosis.setText(Diagnosis);
            }
        });
    }
}