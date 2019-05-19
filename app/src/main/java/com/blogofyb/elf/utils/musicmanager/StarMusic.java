package com.blogofyb.elf.utils.musicmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blogofyb.elf.utils.constant.SQLite;
import com.blogofyb.elf.utils.database.MySQLiteOpenHelper;

public class StarMusic {
    private Context mContext;
    private boolean mIsStar;

    public StarMusic(Context mContext) {
        this.mContext = mContext;
    }

    public boolean checkStar(String id) {
        SQLiteDatabase database = MySQLiteOpenHelper.getDatabase(mContext);
        String sql = "SELECT * FROM " + SQLite.TABLE_STAR + " WHERE " + SQLite.COLUMN_ID + "='" + id + "'";
        Cursor cursor = database.rawQuery(sql, null);
        mIsStar = cursor.moveToNext();
        cursor.close();
        return mIsStar;
    }

    private void starMusic(String songName, String singer, String id, String content, boolean star) {
        SQLiteDatabase database = MySQLiteOpenHelper.getDatabase(mContext);
        String sql;
        if (star) {
            sql = "INSERT INTO " + SQLite.TABLE_STAR + " VALUES('" +
                    id + "', '" +
                    songName + "', '" +
                    singer + "', '" +
                    content + "')";
        } else {
            sql = "DELETE FROM " + SQLite.TABLE_STAR + " WHERE " +
                    SQLite.COLUMN_ID + "='" + id + "'";
        }
        database.execSQL(sql);
    }

    public void starMusic(String songName, String singer, String id) {
        starMusic(songName, singer, id, " ");
    }

    public void starMusic(String songName, String singer, String id, String content) {
        if (checkStar(id)) {
            starMusic(songName, singer, id, content, false);
            mIsStar = false;
        } else {
            starMusic(songName, singer, id, content, true);
            mIsStar = true;
        }
    }
}
