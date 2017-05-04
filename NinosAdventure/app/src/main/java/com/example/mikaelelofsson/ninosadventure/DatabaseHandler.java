package com.example.mikaelelofsson.ninosadventure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mikael Elofsson on 2017-03-11.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    SQLiteDatabase database;
    private static final String TABLE_SAVEDGAMES = "Saved_games";
    private static final String COLUMN_ACTIVITY = "aktivitet";



    public DatabaseHandler(Context context) {
        super(context, "month.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {


        String CREATE_TABLESAVED
                = "create table " + TABLE_SAVEDGAMES +
                " (_id integer primary key autoincrement," +
                " aktivitet text not null)";

        database.execSQL(CREATE_TABLESAVED);

        addInitialEmptyPost(database,"empty");
        addInitialEmptyPost(database,"empty");
        addInitialEmptyPost(database,"empty");
        addInitialEmptyPost(database,"empty");
        addInitialEmptyPost(database,"empty");
        addInitialEmptyPost(database,"empty");



    }


    Cursor getAllSavedGames() {

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_SAVEDGAMES,null);

        return cursor;

    }

    public void updateGameSlot (long id, String category) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTIVITY, category);

        database.update(TABLE_SAVEDGAMES,values,"_id = " +id,null);

    }
    public void addInitialEmptyPost (SQLiteDatabase database, String string) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTIVITY, string);

        database.insert(TABLE_SAVEDGAMES,null,values);



    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVEDGAMES);
        onCreate(database);

    }

    public void open() {
        database = getWritableDatabase();
    }

}
