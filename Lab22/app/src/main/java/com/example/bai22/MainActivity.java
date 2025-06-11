package com.example.bai22;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class MainActivity extends Activity {
    Button btnparse;
    ListView lv1;
    ArrayAdapter<String> myadapter;
    ArrayList<String> mylist;
    String URL= "https://api.androidhive.info/pizza/?format=xml";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnparse=(Button) findViewById(R.id.btnparse);
        lv1= (ListView) findViewById(R.id.lv1);
        mylist = new ArrayList<>();
        myadapter = new
                ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,mylist);
        lv1.setAdapter(myadapter);
        btnparse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadExampleTask task = new LoadExampleTask();
                task.execute();
            }
        });
    }
    class LoadExampleTask extends AsyncTask<Void, Void, ArrayList<String>>
    {
        @Override
        protected ArrayList<String> doInBackground(Void... voids)
        {
            ArrayList<String> list = new ArrayList<String>();
            try {
// Tạo đối tượng Parser để chứa dữ liệu từ file XML
                XmlPullParserFactory fc=XmlPullParserFactory.newInstance();
                XmlPullParser parser= fc.newPullParser();
//Tạo mới và gọi đến phương thức getXmlFromUrl(URL)
                XMLParser myparser = new XMLParser();
                String xml = myparser.getXmlFromUrl(URL); // getting XML from URL
//Copy dữ liệu từ String xml vào đối tượng parser
                parser.setInput(new StringReader(xml));
//Bắt đầu duyệt parser
                int eventType=-1;
                String nodeName;
                String datashow="";
                while(eventType!= XmlPullParser.END_DOCUMENT)
                {
                    eventType=parser.next();
                    switch(eventType)
                    {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            nodeName=parser.getName();
                            if(nodeName.equals("id")){
                                datashow+=parser.nextText()+"-";
                            }
                            else if(nodeName.equals("name")){
                                datashow+=parser.nextText();
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            nodeName=parser.getName();
                            if(nodeName.equals("item")){
                                list.add(datashow);
                                datashow="";
                            }
                            break;
                    }
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }
        @Override
        protected void onPreExecute() {
// TODO Auto-generated method stub
            super.onPreExecute();
            myadapter.clear();
        }
        @Override
        protected void onPostExecute(ArrayList<String> result) {
// TODO Auto-generated method stub
            super.onPostExecute(result);
            myadapter.clear();
            myadapter.addAll(result);
        }
        @Override
        protected void onProgressUpdate(Void... values) {
// TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }
    }
}