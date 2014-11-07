package com.example.m77611.exerprova.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by m77611 on 03/11/2014.
 */
public class EnergiaHelper  extends SQLiteOpenHelper {
    private static final String SQL_CREATE_TABLE_ENERGIA =
            "CREATE TABLE " + EnergiaContract.Energia.TABLE_NAME + " (" +
                    EnergiaContract.Energia._ID + " INTEGER PRIMARY KEY," +
                    EnergiaContract.Energia.COD_CLI + " INTEGER," +
                    EnergiaContract.Energia.RUA + " TEXT," +
                    EnergiaContract.Energia.NUMERO + " INTEGER," +
                    EnergiaContract.Energia.KWH + " INTEGER" +
                    " )";

    private static final String SQL_DELETE_TABLE_ENERGIA =
            "DROP TABLE IF EXISTS " + EnergiaContract.Energia.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EnergiaMobile.db";

    public EnergiaHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ENERGIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_ENERGIA);
        onCreate(db);
    }
}
