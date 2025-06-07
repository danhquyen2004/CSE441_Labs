package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class myarrayAdapter extends ArrayAdapter<Item> {
    Activity context = null;
    ArrayList<Item> myArray = null;
    int layoutId;
    public myarrayAdapter(Activity context, int layoutId, ArrayList<Item> arr) {
        super(context, layoutId, arr);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        final Item item = myArray.get(position);
        final TextView txtTitle = convertView.findViewById(R.id.txt_title);
        txtTitle.setText(item.getTitle());
        final TextView txtId = convertView.findViewById(R.id.txt_id);
        txtId.setText(item.getId());
        final ImageView btnLike = convertView.findViewById(R.id.btn_like);
        final ImageView btnunlike = convertView.findViewById(R.id.btn_unlike);
        int like = item.getLike();
        if(like == 0){
            btnLike.setVisibility(View.INVISIBLE);
            btnunlike.setVisibility(View.VISIBLE);
        }
        else{
            btnLike.setVisibility(View.VISIBLE);
            btnunlike.setVisibility(View.INVISIBLE);
        }
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 0);
                MainActivity.database.update("ArirangSongList", values, "MABH=?", new String[]{txtId.getText().toString()});
                btnLike.setVisibility(View.INVISIBLE);
                btnunlike.setVisibility(View.VISIBLE);
            }
        });
        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setTextColor(Color.RED);
                txtId.setTextColor(Color.RED);
                Intent intent1 = new Intent(context, SubActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", txtId.getText().toString());
                bundle.putString("title", txtTitle.getText().toString());
                intent1.putExtra("package", bundle);
                context.startActivity(intent1);
            }
        });

        return convertView;
    }
}
