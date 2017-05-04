package com.example.mikaelelofsson.ninosadventure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;

/**
 * Created by Mikael Elofsson on 2017-05-01.
 */

 class AsyncTaskRunner extends AsyncTask<Integer, Integer, Integer> {

    Intent intent;
    Activity activity;
    ProgressBar pBar;
    ProgressDialog progressDialog;
    Context context;

    public AsyncTaskRunner (Intent intent, Activity activity, ProgressBar pBar) {

        this.intent = intent;
        this.activity = activity;
        this.context = activity;
        this.pBar = pBar;

    }

    @Override
    protected Integer doInBackground(Integer... params) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.startActivity(intent);
        return 1;
    }

    @Override
    protected void onPostExecute(Integer result) {


    }


    @Override
    protected void onPreExecute() {
        pBar.setVisibility(View.VISIBLE);

    }


    @Override
    protected void onProgressUpdate(Integer... text) {

    }


}