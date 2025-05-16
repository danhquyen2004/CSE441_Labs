package com.example.lap14_2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends Activity {

    EditText edttim;
    ListView lv1, lv2, lv3;
    TabHost tab;
    ArrayList<Item> list1, list2, list3;
    MyArrayAdapter myarray1, myarray2, myarray3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();
        addEvent();
    }

    private void addEvent() {
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equalsIgnoreCase("t1")) {
                    list1.clear();
                    list1.add(new Item("52300", "Em là ai Tôi là ai", 0));
                    list1.add(new Item("52800", "Buồn của Anh", 1));
                    myarray1.notifyDataSetChanged();
                }

                if (tabId.equalsIgnoreCase("t2")) {
                    list2.clear();
                    list2.add(new Item("57236", "Ghé em đi cuối sông hồng", 0));
                    list2.add(new Item("51548", "Quê hương tuổi thơ tôi", 1));
                    myarray2.notifyDataSetChanged();
                }

                if (tabId.equalsIgnoreCase("t3")) {
                    list3.clear();
                    list3.add(new Item("57689", "Hát với dòng sông", 1));
                    list3.add(new Item("58716", "Say tình – Remix", 0));
                    myarray3.notifyDataSetChanged();
                }
            }
        });
    }

    private void addControl() {
        tab = (TabHost) findViewById(R.id.tabhost);
        tab.setup();

        // Tab 1
        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Tìm kiếm", getResources().getDrawable(R.drawable.search));
        tab.addTab(tab1);

        // Tab 2
        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Nghe nhiều", getResources().getDrawable(R.drawable.clock));
        tab.addTab(tab2);

        // Tab 3
        TabHost.TabSpec tab3 = tab.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("Yêu thích", getResources().getDrawable(R.drawable.favourite));
        tab.addTab(tab3);

        // Liên kết view
        edttim = findViewById(R.id.edttim);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        myarray1 = new MyArrayAdapter(this, R.layout.listitem, list1);
        myarray2 = new MyArrayAdapter(this, R.layout.listitem, list2);
        myarray3 = new MyArrayAdapter(this, R.layout.listitem, list3);

        lv1.setAdapter(myarray1);
        lv2.setAdapter(myarray2);
        lv3.setAdapter(myarray3);
    }
}
