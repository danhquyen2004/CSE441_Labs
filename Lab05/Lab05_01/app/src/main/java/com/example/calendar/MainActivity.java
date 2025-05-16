package com.example.calendar;

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

    EditText edtText, edtText2;
    Button btnCon;
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
        edtText = findViewById(R.id.editTextNumber);
        edtText2 = findViewById(R.id.editTextNumber2);
        btnCon = findViewById(R.id.button);

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer namduong = Integer.parseInt(edtText.getText().toString());
                String can, chi;
                switch (namduong%10) {
                    case 0: can = "Canh"; break;
                    case 1: can = "Tân"; break;
                    case 2: can = "Nhâm"; break;
                    case 3: can = "Quý"; break;
                    case 4: can = "Giáp"; break;
                    case 5: can = "Ất"; break;
                    case 6: can = "Bính"; break;
                    case 7: can = "Đinh"; break;
                    case 8: can = "Mậu"; break;
                    default: can = "Kỷ"; break;
                }
                switch (namduong%12) {
                    case 0: chi = "Thân"; break;
                    case 1: chi = "Dậu"; break;
                    case 2: chi = "Tuất"; break;
                    case 3: chi = "Hợi"; break;
                    case 4: chi = "Tý"; break;
                    case 5: chi = "Sửu"; break;
                    case 6: chi = "Dần"; break;
                    case 7: chi = "Mão"; break;
                    case 8: chi = "Thìn"; break;
                    case 9: chi = "Tỵ"; break;
                    case 10: chi = "Ngọ"; break;
                    default: chi = "Mùi"; break;
                }
                edtText2.setText(can + " " + chi);
            }
        });
    }
}