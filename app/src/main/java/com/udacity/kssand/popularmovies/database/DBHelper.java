package com.udacity.kssand.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by kssand on 07-Feb-16.
 */
public class DBHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;
    private static DBHelper dbHelper = null;

    private DBHelper() {
        super(null, null, null, 1);
    }
    /**
     * @param context
     * @return DBHelper
     */
    public static synchronized DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
