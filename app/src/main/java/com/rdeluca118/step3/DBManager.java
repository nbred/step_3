package com.rdeluca118.step3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public SQLiteDatabase getBatabase(){

        return database;
    }
    public void close() {
        dbHelper.close();
    }


    // =============================================================================================
    // player table operations
    // =============================================================================================
    public void insert_player(String name) {
       ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.player_name, name);
        database.insert(DatabaseHelper.TABLE_PLAYER, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper.GAME_ID, DatabaseHelper.game_date, DatabaseHelper.game_player1, DatabaseHelper.game_player2 };
        Cursor cursor = database.query(DatabaseHelper.TABLE_GAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.SUBJECT, name);
//        contentValues.put(DatabaseHelper.DESC, desc);
//        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return 1; //i
    }

    public void delete(long _id) {
//        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }
}
