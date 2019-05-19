package com.blogofyb.elf.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blogofyb.elf.utils.constant.SQLite;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase database;

    private MySQLiteOpenHelper(Context context) {
        super(context, SQLite.DATABASE_EIF, null , 1);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null) {
            synchronized (MySQLiteOpenHelper.class) {
                if (database == null) {
                    database = new MySQLiteOpenHelper(context).getWritableDatabase();
                }
            }
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + SQLite.TABLE_STAR + "(" +
                SQLite.COLUMN_ID + " TEXT NOT NULL PRIMARY KEY, " +
                SQLite.COLUMN_SONG_NAME + " TEXT NOT NULL, " +
                SQLite.COLUMN_SINGER + " TEXT NOT NULL, " +
                SQLite.COLUMN_CONTENT + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
