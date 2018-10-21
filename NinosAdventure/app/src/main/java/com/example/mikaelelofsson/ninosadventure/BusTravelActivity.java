package com.example.mikaelelofsson.ninosadventure;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class BusTravelActivity extends AppCompatActivity {

    BusTravelSurfaceView busTravelSurfaceView;
    SurfaceHolder busTravelSurfaceHolder;

    View decorView;
    float displayHeight, displayWidth;

    ImageView nextButton;
    ImageView homeButton;


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


        setContentView(R.layout.activity_bustravel);

         busTravelSurfaceView = (BusTravelSurfaceView) findViewById(R.id.busTravelSurfaceView);
        busTravelSurfaceHolder = busTravelSurfaceView.getHolder();

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;


        nextButton = (ImageView)findViewById(R.id.nextLevel);
        homeButton = (ImageView)findViewById(R.id.townHome);


        nextButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(BusTravelActivity.this, TrainStationActivity.class);

                startActivity(intent);
                return false;
            }
        });

        homeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //busTravelSurfaceView.exitAndReturnToMain();

                return false;
            }
        });

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
