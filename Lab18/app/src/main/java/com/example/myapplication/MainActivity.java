package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Output;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
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
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database=null;
    public static String DATABASE_NAME = "arirang.sqlite";
    EditText edtSearch;
    ListView lv1, lv2, lv3;
    ArrayList<Item> list1, list2, list3;
    myarrayAdapter adapter1, adapter2, adapter3;
    TabHost tab;
    Button btnDelete;
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
        database = openOrCreateDatabase("arirang.sqlite", MODE_PRIVATE, null);
        addControl();
        addSearch();
        addEvents();
    }
    private void addControl(){
        btnDelete = findViewById(R.id.btn_delete);
        tab = findViewById(R.id.tabhost);
        tab.setup();
        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("", getResources().getDrawable(R.drawable.search));
        tab.addTab(tab1);
        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("", getResources().getDrawable(R.drawable.list));
        tab.addTab(tab2);

        TabHost.TabSpec tab3 = tab.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("", getResources().getDrawable(R.drawable.favorite));
        tab.addTab(tab3);
        edtSearch = findViewById(R.id.edt_search);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);
        list1 = new ArrayList<Item>();
        list2 = new ArrayList<Item>();
        list3 = new ArrayList<Item>();
        adapter1 = new myarrayAdapter(this, R.layout.listitem, list1);
        adapter2 = new myarrayAdapter(this, R.layout.listitem, list2);
        adapter3 = new myarrayAdapter(this, R.layout.listitem, list3);
        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);
        lv3.setAdapter(adapter3);
    }
    private void addEvents(){
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("t2")) {
                    addList();
                }
                if (tabId.equals("t3")) {
                    addFavourit();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
            }
        });
    }
    private void addFavourit(){
        adapter3.clear();
        Cursor c = MainActivity.database.rawQuery("SELECT * FROM ArirangSongList WHERE YEUTHICH=1", null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            list3.add(new Item(c.getString(1), c.getString(2), c.getInt(6)));
            c.moveToNext();
        }
        c.close();
        adapter3.notifyDataSetChanged();
    }
    private void addList(){
        adapter2.clear();
        Cursor c = MainActivity.database.rawQuery("SELECT * FROM ArirangSongList", null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            list2.add(new Item(c.getString(1), c.getString(2), c.getInt(6)));
            c.moveToNext();
        }
        c.close();
        adapter2.notifyDataSetChanged();
    }
    private void addSearch(){
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getdata();
            }
            private void getdata(){
                String search = edtSearch.getText().toString();
                adapter1.clear();
                if(!edtSearch.getText().toString().equals("")){
                    Cursor c = MainActivity.database.rawQuery("SELECT * FROM ArirangSongList WHERE MABH LIKE'%" + search + "%' OR TENBH LIKE'%" + search + "%'", null);
                    c.moveToFirst();
                    while(!c.isAfterLast()){
                        list1.add(new Item(c.getString(1), c.getString(2), c.getInt(6)));
                        c.moveToNext();
                    }
                    c.close();
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void processCopy(){
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try{
                CopyDataBaseFromAssets();
                Toast.makeText(this, "Copy database success", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
    private void CopyDataBaseFromAssets() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File file = new File(outFileName);
            if (!file.exists()) {
                file.mkdir();
            }
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}