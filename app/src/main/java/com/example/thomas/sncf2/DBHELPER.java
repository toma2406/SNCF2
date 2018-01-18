package com.example.thomas.sncf2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thomas on 17/12/2017.
 */

public class DBHELPER extends SQLiteOpenHelper{

    private static final int DATABASE_Version = 1 ;

    private static final String DATABASE_NAME = "object";

    public DBHELPER(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_OBJECT ="CREATE TABLE " + ObjetTrouve.TABLE+ "("
                +ObjetTrouve.KEY_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +ObjetTrouve.KEY_date+" TEXT,"
                +ObjetTrouve.KEY_gare+" TEXT,"
                +ObjetTrouve.KEY_nature+" TEXT,"
                +ObjetTrouve.KEY_type +" TEXT )";
        sqLiteDatabase.execSQL(CREATE_TABLE_OBJECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS "+ObjetTrouve.TABLE);
        onCreate(sqLiteDatabase);
    }
}
