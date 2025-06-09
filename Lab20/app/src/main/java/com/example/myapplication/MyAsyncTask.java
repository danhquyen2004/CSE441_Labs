package com.example.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
    Activity context;
    public MyAsyncTask(Activity context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "onPreExecute", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        for (int i = 0; i <= 100; i++) {
            SystemClock.sleep(100);
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        ProgressBar bar = context.findViewById(R.id.progressBar2);
        int progress = values[0];
        bar.setProgress(progress);
        TextView msg = context.findViewById(R.id.textview);
        msg.setText(progress + "%");
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
    }
}
