package com.example.lap14_2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Item> {
    Activity context;
    int layoutId;
    ArrayList<Item> list;

    public MyArrayAdapter(Activity context, int layoutId, ArrayList<Item> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        TextView txtMaso = convertView.findViewById(R.id.txtmaso);
        TextView txtTieude = convertView.findViewById(R.id.txttieude);
        ImageButton btnLike = convertView.findViewById(R.id.btnlike);

        Item item = list.get(position);
        txtMaso.setText(item.getMaso());
        txtTieude.setText(item.getTieude());

        if (item.getThich() == 1) {
            btnLike.setImageResource(R.drawable.liked);
        } else {
            btnLike.setImageResource(R.drawable.unlike);
        }

        return convertView;
    }
}
