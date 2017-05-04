/*package com.example.mikaelelofsson.ninosadventure;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
*/
/*
public class ConstructionActivity extends AppCompatActivity {

    private static final String IMAGEVIEW_TAG = "carImage";
    ImageView carView;
    private android.widget.RelativeLayout.LayoutParams layoutParams;
    ConstructionSurfaceView surfaceView;
    SurfaceHolder surfaceViewHolder;

    static Context context;
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
        setContentView(R.layout.activity_construction);

        surfaceView = (ConstructionSurfaceView) findViewById(R.id.airportSurfaceView);
        surfaceView.setZOrderOnTop(true);    // necessary
        surfaceViewHolder = surfaceView.getHolder();
        surfaceViewHolder.setFormat(PixelFormat.TRANSPARENT);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;

        context = this;

        ImageButton refreshButton = (ImageButton) findViewById(R.id.imageButton2);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

    }
 static void startIntent(){

        Intent intent = new Intent(context, AirportActivity.class);
         context.startActivity(intent);
}


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.surfaceDestroyed(surfaceViewHolder);
    }
}
*/