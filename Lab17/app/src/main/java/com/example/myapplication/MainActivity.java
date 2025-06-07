package com.example.myapplication;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String DB_PATH_SUFFIX = "/databases/";
    private static final String DATABASE_NAME = "qlsach.db";
    private SQLiteDatabase database = null;
    private ListView lv;
    private ArrayList<String> mylist;
    private ArrayAdapter<String> adapter;

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

        // Khởi tạo ListView và Adapter
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(adapter);

        // Sao chép cơ sở dữ liệu
        processCopy();

        // Mở cơ sở dữ liệu
        try {
            database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            Log.d("MainActivity", "Database path: " + getDatabasePath(DATABASE_NAME).getPath());

            // Kiểm tra danh sách bảng
            Cursor cursor = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            boolean tableExists = false;
            StringBuilder tables = new StringBuilder("Tables in database: ");
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String tableName = cursor.getString(0);
                    tables.append(tableName).append(", ");
                    if (tableName.equals("tbsach")) {
                        tableExists = true;
                    }
                }
                cursor.close();
            }
            Toast.makeText(this, tables.toString(), Toast.LENGTH_LONG).show();

            if (!tableExists) {
                Toast.makeText(this, "Table 'tbsach' does not exist in database", Toast.LENGTH_LONG).show();
                return;
            }

            // Truy vấn dữ liệu từ bảng tbsach
            try (Cursor c = database.query("tbsach", null, null, null, null, null, null)) {
                if (c != null && c.moveToFirst()) {
                    do {
                        String data = c.getString(0) + "-" + c.getString(1) + "-" + c.getString(2);
                        mylist.add(data);
                    } while (c.moveToNext());
                    adapter.notifyDataSetChanged();
                    Log.d("MainActivity", "List size: " + mylist.size());
                } else {
                    Toast.makeText(this, "No data found in tbsach table", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Query error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Database error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                // Kiểm tra file trong assets
                String[] assetFiles = getAssets().list("");
                boolean fileExists = false;
                if (assetFiles != null) {
                    for (String file : assetFiles) {
                        if (file.equals(DATABASE_NAME)) {
                            fileExists = true;
                            break;
                        }
                    }
                }
                if (!fileExists) {
                    Toast.makeText(this, "Database file 'qlsach.db' not found in assets", Toast.LENGTH_LONG).show();
                    return;
                }
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copy database success", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Copy failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath(DATABASE_NAME).getPath();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) {
                f.mkdirs();
            }
            try (OutputStream myOutput = new FileOutputStream(outFileName)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error copying database", e);
        }
    }
}