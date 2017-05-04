package com.example.mikaelelofsson.ninosadventure;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
/*
public class TrainStationActivity extends AppCompatActivity {

    TrainSurfaceView surfaceView;
    TrainBackSurfaceView bgSurfaceView;
    SurfaceHolder surfaceViewHolder;
    SurfaceHolder bgSurfaceViewHolder;
    ImageView saveButton;
    ImageView homeButton;

    View decorView;
    float displayHeight, displayWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_train_station);

        bgSurfaceView = (TrainBackSurfaceView)findViewById(R.id.trainBgSurfaceView);
        bgSurfaceViewHolder = bgSurfaceView.getHolder();

        surfaceView = (TrainSurfaceView)findViewById(R.id.trainSurfaceView);
        surfaceView.setZOrderOnTop(true);    // necessary
        surfaceViewHolder = surfaceView.getHolder();
        surfaceViewHolder.setFormat(PixelFormat.TRANSPARENT);

        ;

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;


        saveButton = (ImageView)findViewById(R.id.trainSave);
        homeButton = (ImageView)findViewById(R.id.trainHome);


        saveButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(TrainStationActivity.this, SaveGame.class);
                intent.putExtra("activity", "train");
                startActivity(intent);
                return false;
            }
        });

        homeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(TrainStationActivity.this, MainActivity.class);
                startActivity(intent);

                return false;
            }
        });


    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(TrainStationActivity.this,TownActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

}
*/