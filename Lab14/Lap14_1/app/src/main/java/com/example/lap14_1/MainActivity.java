package com.example.lap14_1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtA, edtB;
    Button btnAdd;
    ListView listHistory;
    ArrayList<String> history = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        // Tab 1
        TabHost.TabSpec spec1 = tabHost.newTabSpec("t1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Phép cộng", getResources().getDrawable(android.R.drawable.ic_menu_add));
        tabHost.addTab(spec1);

        // Tab 2
        TabHost.TabSpec spec2 = tabHost.newTabSpec("t2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Lịch sử", getResources().getDrawable(android.R.drawable.ic_menu_recent_history));
        tabHost.addTab(spec2);

        // Tab 1 xử lý phép cộng
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(view -> {
            try {
                int a = Integer.parseInt(edtA.getText().toString());
                int b = Integer.parseInt(edtB.getText().toString());
                String result = a + " + " + b + " = " + (a + b);
                history.add(result);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Vui lòng nhập số", Toast.LENGTH_SHORT).show();
            }
        });

        // Tab 2 hiển thị lịch sử
        listHistory = findViewById(R.id.listHistory);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, history);
        listHistory.setAdapter(adapter);
    }
}
