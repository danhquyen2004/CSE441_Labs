package com.example.bai24;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log; // Thêm import này
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap; // Thêm HashMap để ánh xạ tên ảnh với resource ID

public class MainActivity extends AppCompatActivity {
    ListView lvTigia;
    TextView txtdate;
    ArrayList<Tygia> dstygia;
    MyArrayAdapter myadapter;

    // HashMap để ánh xạ tên hình ảnh từ API với resource ID cục bộ
    private HashMap<String, Integer> imageMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTigia = findViewById(R.id.lv1); // Sử dụng findViewById
        txtdate = findViewById(R.id.txtdate); // Sử dụng findViewById

        getdate();
        dstygia = new ArrayList<>(); // Sử dụng cú pháp diamond
        myadapter = new MyArrayAdapter(MainActivity.this, R.layout.layout_listview, dstygia);
        lvTigia.setAdapter(myadapter);

        // Khởi tạo HashMap cho hình ảnh (thêm các ánh xạ khác nếu cần)
        initImageMap();

        TyGiaTask task = new TyGiaTask();
        task.execute();
    }

    public void getdate() {
        //Lấy ngày giờ hệ thống
        Date currentDate = Calendar.getInstance().getTime();
        //Format theo định dạng dd/mm/yyyy
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        //Hiển thị lên TextView
        txtdate.setText("Hôm Nay: " + simpleDate.format(currentDate));
    }

    private void initImageMap() {
        imageMap = new HashMap<>();
        // Ví dụ: ánh xạ tên hình ảnh từ API với resource ID trong drawable/mipmap
        // Bạn cần phải có các file hình ảnh này trong thư mục res/drawable hoặc res/mipmap
        imageMap.put("AUD.png", R.drawable.aud); // Ví dụ: bạn có một file "aud.png" trong drawable
        imageMap.put("CAD.png", R.drawable.cad);
        imageMap.put("CHF.png", R.drawable.chf);
        imageMap.put("EUR.png", R.drawable.eur);
        imageMap.put("GBP.png", R.drawable.gbp);
        imageMap.put("JPY.png", R.drawable.jpy);
        imageMap.put("USD.png", R.drawable.usd);
        imageMap.put("VND.png", R.drawable.vnd);
        // Thêm các loại tiền tệ khác nếu API trả về và bạn có hình ảnh tương ứng
    }

    class TyGiaTask extends AsyncTask<Void, Void, ArrayList<Tygia>> {
        @Override
        protected ArrayList<Tygia> doInBackground(Void... params) {
            ArrayList<Tygia> ds = new ArrayList<>();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String json = "";
            try {
                //Đây là link Server
                URL url = new URL("https://dongabank.com.vn/exchange/export");
                //Mở Connection ra
                connection = (HttpURLConnection) url.openConnection();
                // Thiết lập Method là Get dữ liệu
                connection.setRequestMethod("GET");
                //Thiết lập thuộc tính nó thuộc loại Json nào, để biết sử dụng công cụ HttpRequester trong FireFox
                connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible)");
                connection.setRequestProperty("Accept", "*/*");

                //lấy dữ liệu mà server trả về
                // Lấy chuỗi dữ liệu InputStream trả về
                InputStream is = connection.getInputStream();
                //Chuyển kiểu về kiểu UTF-8 và Đưa vào bộ đọc dữ liệu
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                //Lưu vào bộ đệm
                reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                json = builder.toString();

                //Bỏ hai ngoặc tròn trong dữ liệu trả về
                json = json.replace("(", "");
                json = json.replace(")", "");

                Log.d("JSON_DONGA", json); // Đưa Log vào đây để xem toàn bộ JSON

                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    Tygia tiGia = new Tygia();

                    // Đặt các thuộc tính từ JSON
                    if (item.has("type")) {
                        tiGia.setType(item.getString("type"));
                    }
                    if (item.has("image")) { // Giả sử "image" là tên file ảnh như "USD.png"
                        String imageName = item.getString("image");
                        tiGia.setImageurl(imageName); // Lưu tên ảnh vào imageurl

                        // Lấy Bitmap từ resource cục bộ
                        Integer resourceId = imageMap.get(imageName);
                        if (resourceId != null) {
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
                            tiGia.setBitmap(bitmap);
                        } else {
                            // Nếu không tìm thấy hình ảnh, có thể đặt một hình ảnh mặc định
                            tiGia.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                            Log.w("TyGiaTask", "Image resource not found for: " + imageName);
                        }
                    } else {
                        // Nếu không có trường "image", đặt hình ảnh mặc định
                        tiGia.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                    }

                    if (item.has("muatienmat")) {
                        tiGia.setMuatienmat(item.getString("muatienmat"));
                    }
                    if (item.has("muack")) {
                        tiGia.setMuack(item.getString("muack"));
                    }
                    if (item.has("bantienmat")) {
                        tiGia.setBantuenmat(item.getString("bantienmat"));
                    }
                    if (item.has("banck")) {
                        tiGia.setBanck(item.getString("banck"));
                    }
                    ds.add(tiGia); // Thêm đối tượng Tygia vào danh sách
                }
            } catch (Exception ex) {
                Log.e("TyGiaTask", "Error fetching or parsing data: " + ex.toString());
                ex.printStackTrace(); // In stack trace để debug chi tiết hơn
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return ds;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myadapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<Tygia> result) {
            super.onPostExecute(result);
            if (result != null && !result.isEmpty()) {
                myadapter.clear();
                myadapter.addAll(result);
                myadapter.notifyDataSetChanged(); // Thông báo cho adapter dữ liệu đã thay đổi
            } else {
                Log.d("TyGiaTask", "No data received or parsed.");
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}