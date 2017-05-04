package com.example.mikaelelofsson.ninosadventure;

import android.content.Intent;
import android.media.ToneGenerator;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    static int displayHeight;
    static int displayWidth;
    static Intent intent;
    View decorView;

    ImageView playButton;
    TextView loadGame;
    ImageView logo;
    ProgressBar pBar;
    static boolean closeUpView = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        closeUpView = false;
       // if (Build.VERSION.SDK_INT < 16) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
       /*}
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);*/

        setContentView(R.layout.activity_main);



        logo = (ImageView)findViewById(R.id.imageView);
        pBar = (ProgressBar)findViewById(R.id.progressBar2);

        playButton = (ImageView)findViewById(R.id.imageView2);
        playButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                logo.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.INVISIBLE);
                loadGame.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(MainActivity.this, TownActivity.class);

                AsyncTaskRunner runner = new AsyncTaskRunner(intent, MainActivity.this, pBar);
                runner.execute();


                return false;
            }
        });

        loadGame = (TextView)findViewById(R.id.textView2);
        loadGame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
/*
                Intent intent = new Intent(MainActivity.this, LoadGame.class);
                startActivity(intent);
                */
                return false;
            }
        });

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;

            }


    @Override
    protected void onResume() {

        super.onResume();
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                        */
    }


}

