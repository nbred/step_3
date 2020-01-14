package com.rdeluca118.step3;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class Player {

    private String name;
    private int id;
    private DatabaseHelper dbHelper;

    public Player(DatabaseHelper dbh, String pName) {

        name = pName;
        dbHelper = dbh;
        if(saveToDatabase() > 0 ){
            Log.i("Database ", "inserted");
        }
    }

    // method to set the name
    public void setName(String name) {
        this.name = name;
    }

    // method to retrieve the name
    public String getName() {
        return name;
    }

    public long saveToDatabase() {
        SQLiteDatabase db;
        Cursor cursor = null;
        long r;
        int i;
        // Gets the data repository in write mode
        db = dbHelper.getWritableDatabase();


        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(dbHelper.player_name, getName());

        try {
            r = db.insert(dbHelper.TABLE_PLAYER, null, values);
            cursor = db.rawQuery("SELECT _id FROM " + dbHelper.TABLE_PLAYER + " WHERE name = '" + name + "';", null);
            if (cursor != null) {
                cursor.moveToFirst();
                i = cursor.getColumnIndex(dbHelper.PLAYER_ID);
                id = cursor.getInt(i);
            }
        }
        catch(SQLException e){
            Log.d("New Player ", e.getMessage());
            throw e;
        }

        if (cursor != null) cursor.close();
        db.close();

        return r;
    }
}


