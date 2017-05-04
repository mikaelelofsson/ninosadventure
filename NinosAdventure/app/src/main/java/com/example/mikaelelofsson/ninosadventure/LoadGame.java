package com.example.mikaelelofsson.ninosadventure;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Mikael Elofsson on 2017-03-11.
 */
/*
public class LoadGame extends AppCompatActivity implements View.OnTouchListener {

    static int displayHeight;
    static int displayWidth;
    View decorView;

    ImageView view;
    ImageView view1;
    ImageView view2;
    ImageView view3;
    ImageView view4;
    ImageView view5;
    ImageView view6;

    DatabaseHandler databaseHandler;

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

        setContentView(R.layout.activity_load);

        databaseHandler = new DatabaseHandler(this);
        databaseHandler.open();

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;

        view1 = (ImageView)findViewById(R.id.imageView6);
        view2 = (ImageView)findViewById(R.id.imageView7);
        view3 = (ImageView)findViewById(R.id.imageView8);
        view4 = (ImageView)findViewById(R.id.imageView9);
        view5 = (ImageView)findViewById(R.id.imageView10);
        view6 = (ImageView)findViewById(R.id.imageView11);

        homeButton = (ImageView)findViewById(R.id.loadHome);

        homeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(LoadGame.this, MainActivity.class);
                startActivity(intent);

                return false;
            }
        });

        view1.setOnTouchListener(this);
        view2.setOnTouchListener(this);
        view3.setOnTouchListener(this);
        view4.setOnTouchListener(this);
        view5.setOnTouchListener(this);
        view6.setOnTouchListener(this);

        buildTables();
    }

    public void buildTables() {

        Cursor cursor = databaseHandler.getAllSavedGames();

        cursor.moveToFirst();

        int count = 1;

        while (!cursor.isAfterLast()) {



                switch (count) {
                    case 1:
                        view = (ImageView) findViewById(R.id.imageView6);
                        break;
                    case 2:
                        view = (ImageView) findViewById(R.id.imageView7);
                        break;
                    case 3:
                        view = (ImageView) findViewById(R.id.imageView8);
                        break;
                    case 4:
                        view = (ImageView) findViewById(R.id.imageView9);
                        break;
                    case 5:
                        view = (ImageView) findViewById(R.id.imageView10);
                        break;
                    case 6:
                        view = (ImageView) findViewById(R.id.imageView11);
                        break;
                    default:
                        break;

                }

                switch (cursor.getString(1).toString()) {

                    case "empty":
                        view.setImageResource(R.drawable.empty);
                        break;

                    case "town":
                        view.setImageResource(R.drawable.townsaved);
                        break;

                    case "train":
                        view.setImageResource(R.drawable.trainsaved);
                        break;

                    case "ocean":
                        view.setImageResource(R.drawable.oceansaved);
                        break;

                    case "airport":
                        view.setImageResource(R.drawable.airportsaved);

                        break;

                    case "farm":
                        view.setImageResource(R.drawable.farmsaved);
                        break;

                    default:
                        break;


                }

cursor.moveToNext();
            count++;
        }
        cursor.close();
    }

    public void loadSavedGame(long id) {
        Cursor cursor = databaseHandler.getAllSavedGames();
        Intent intent;
        int count = 1;
        cursor.moveToFirst();

        if(id!=1) {
            do {
                cursor.moveToNext();
                count++;
            }
            while (count < id);
        }

        switch (cursor.getString(1)) {
            case "empty":
                Toast.makeText(LoadGame.this, "Empty Slot", Toast.LENGTH_SHORT).show();
                break;
            case "town":
                intent = new Intent(LoadGame.this, TownActivity.class);
                startActivity(intent);
                break;
            case "ocean":
                intent = new Intent(LoadGame.this, OceanActivity.class);
                startActivity(intent);
                break;
            case "farm":
                intent = new Intent(LoadGame.this, CountrySideActivity.class);
                startActivity(intent);
                break;
            case "airport":
               intent = new Intent(LoadGame.this, AirportActivity.class);
                startActivity(intent);
                break;
            case "train":
                intent = new Intent(LoadGame.this, TrainStationActivity.class);
                startActivity(intent);
                break;
            default:
                break;


        }
        cursor.close();
    }


    @Override
    protected void onResume() {
        super.onResume();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View .SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

      //  Cursor cursor;
        switch(v.getId()) {
            case R.id.imageView6:
                loadSavedGame(1);
                break;
           case  R.id.imageView7:
                loadSavedGame(2);
                break;
            case  R.id.imageView8:
                loadSavedGame(3);
                break;
            case  R.id.imageView9:
                loadSavedGame(4);
                break;
            case  R.id.imageView10:
                loadSavedGame(5);
                break;
            case  R.id.imageView11:
                loadSavedGame(6);
                break;
        }


        return false;
    }
}
*/