package com.example.profiles;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edtTen, edtCMND, edtBosung;
    CheckBox chkdocbao, chkdocsach, chkdoccode;
    Button btnSend;
    RadioGroup group;
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
        edtTen = findViewById(R.id.edt_name);
        edtCMND = findViewById(R.id.edt_cmnd);
        edtBosung = findViewById(R.id.edt_info);
        chkdocbao = findViewById(R.id.cb_db);
        chkdocsach = findViewById(R.id.cb_ds);
        chkdoccode = findViewById(R.id.cb_dc);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doShowInfomation();
            }
            public void doShowInfomation(){
                String ten = edtTen.getText().toString();
                ten = ten.trim();
                if(ten.length() < 3){
                    edtTen.requestFocus();
                    edtTen.selectAll();
                    Toast.makeText(MainActivity.this, "Tên phải >= 3 ký tự", Toast.LENGTH_LONG).show();
                    return;
                }

                String cmnd = edtCMND.getText().toString();
                cmnd = cmnd.trim();
                if(cmnd.length() != 9){
                    edtCMND.requestFocus();
                    edtCMND.selectAll();
                    Toast.makeText(MainActivity.this, "CMND phải đúng 9 ký tự", Toast.LENGTH_LONG).show();
                    return;
                }

                String bang = "";
                group = findViewById(R.id.id_group);
                int id = group.getCheckedRadioButtonId();
                if(id == -1){
                    Toast.makeText(MainActivity.this, "Phải chọn bằng cấp", Toast.LENGTH_LONG).show();
                    return;
                }
                RadioButton rad = findViewById(id);
                bang = rad.getText() + "";

                String sothich = "";
                if(chkdocbao.isChecked()){
                    sothich += chkdocbao.getText() + "\n";
                }
                if(chkdocsach.isChecked()){
                    sothich += chkdocsach.getText() + "\n";
                }
                if(chkdoccode.isChecked()){
                    sothich += chkdoccode.getText() + "\n";
                }

                String bosung = edtBosung.getText() + "";

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Thông tin cá nhân");
                builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                String msg = ten + "\n";
                msg += cmnd + "\n";
                msg += bang + "\n";
                msg += sothich;
                msg += "-----------------\n";
                msg += "Thông tin bổ sung: \n";
                msg += bosung + "\n";
                msg += "-----------------";
                builder.setMessage(msg);
                builder.create().show();
            }


        });
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Question");
        b.setMessage("Are you sure you want to exit?");
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        b.create().show();
    }

}