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
import android.widget.Toast;

public class TownActivity extends AppCompatActivity {

    TownSurfaceView animationTownSurfaceView;
    TownBackSurfaceView bgTownSurfaceView;
    SurfaceHolder surfaceViewHolder;
    SurfaceHolder bgSurfaceViewHolder;

    static boolean vehicleThreadKilled = false;
    static boolean backgroundThreadKilled = false;

    View decorView;
    float displayHeight, displayWidth;


    ImageView saveButton;
    ImageView homeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "Starting up Town", Toast.LENGTH_SHORT).show();
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


        setContentView(R.layout.activity_town);

        bgTownSurfaceView = (TownBackSurfaceView) findViewById(R.id.townBgSurfaceView);
        bgSurfaceViewHolder = bgTownSurfaceView.getHolder();

        animationTownSurfaceView = (TownSurfaceView) findViewById(R.id.townSurfaceView);
        animationTownSurfaceView.setZOrderOnTop(true);
        surfaceViewHolder = animationTownSurfaceView.getHolder();
        surfaceViewHolder.setFormat(PixelFormat.TRANSPARENT);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;


        saveButton = (ImageView)findViewById(R.id.townSave);
        homeButton = (ImageView)findViewById(R.id.townHome);


        saveButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(TownActivity.this, SaveGame.class);
                intent.putExtra("activity", "town");

                startActivity(intent);
                return false;
            }
        });

        homeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                animationTownSurfaceView.exitAndReturnToMain();

               // Intent intent = new Intent(TownActivity.this, MainActivity.class);
               // startActivity(intent);

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
