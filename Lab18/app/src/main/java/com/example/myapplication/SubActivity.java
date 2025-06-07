package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SubActivity extends AppCompatActivity {
    TextView txtCode, txtName, txtContent, txtAuthor;
    ImageButton btnLike, btnUnlike;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtCode = findViewById(R.id.txt_code);
        txtName = findViewById(R.id.txt_name);
        txtContent = findViewById(R.id.txt_content);
        txtAuthor = findViewById(R.id.txt_author);
        btnLike = findViewById(R.id.btn_like1);
        btnUnlike = findViewById(R.id.btn_unlike1);
        Bundle bundle = getIntent().getBundleExtra("package");
        String id = bundle.getString("id");
        Cursor c = MainActivity.database.rawQuery("SELECT * FROM ArirangSongList WHERE MABH LIKE'" + id+ "'", null);
        txtCode.setText(id);
        c.moveToFirst();
        txtName.setText(c.getString(2));
        txtContent.setText(c.getString(3));
        txtAuthor.setText(c.getString(4));
        if(c.getInt(6) == 0){
            btnLike.setVisibility(View.INVISIBLE);
            btnUnlike.setVisibility(View.VISIBLE);
        }
        else{
            btnLike.setVisibility(View.VISIBLE);
        }
        c.close();
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 0);
                MainActivity.database.update("ArirangSongList", values, "MABH=?", new String[]{txtCode.getText().toString()});
                btnLike.setVisibility(View.INVISIBLE);
                btnUnlike.setVisibility(View.VISIBLE);
            }
        });
        btnUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 1);
                MainActivity.database.update("ArirangSongList", values, "MABH=?", new String[]{txtCode.getText().toString()});
                btnLike.setVisibility(View.VISIBLE);
                btnUnlike.setVisibility(View.INVISIBLE);
            }
        });


    }
}