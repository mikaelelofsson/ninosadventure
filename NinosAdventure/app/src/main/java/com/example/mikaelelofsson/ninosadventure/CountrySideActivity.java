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

/*public class CountrySideActivity extends AppCompatActivity {

    FarmSurfaceView animationFarmSurfaceView;
    FarmBackSurfaceView bgFarmSurfaceView;
    SurfaceHolder surfaceViewHolder;
    SurfaceHolder bgSurfaceViewHolder;

    View decorView;
    float displayHeight, displayWidth;

    ImageView saveButton;
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

        decorView = getWindow().getDecorView();
        setContentView(R.layout.activity_country_side);

        animationFarmSurfaceView = (FarmSurfaceView)findViewById(R.id.farmSurfaceView);
        animationFarmSurfaceView.setZOrderOnTop(true);    // necessary
        surfaceViewHolder = animationFarmSurfaceView.getHolder();
        surfaceViewHolder.setFormat(PixelFormat.TRANSPARENT);

        bgFarmSurfaceView = (FarmBackSurfaceView)findViewById(R.id.farmBgSurfaceView);
        bgSurfaceViewHolder = bgFarmSurfaceView.getHolder();



        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;


        saveButton = (ImageView)findViewById(R.id.farmSave);
        homeButton = (ImageView)findViewById(R.id.farmHome);


        saveButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(CountrySideActivity.this, SaveGame.class);
                intent.putExtra("activity", "farm");
                startActivity(intent);
                return false;
            }
        });

        homeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(CountrySideActivity.this, MainActivity.class);
                startActivity(intent);

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
            Intent intent = new Intent(this,TrainStationActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
*/