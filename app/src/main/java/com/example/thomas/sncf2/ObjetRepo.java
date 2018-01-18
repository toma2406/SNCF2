package com.example.thomas.sncf2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomas on 17/12/2017.
 */

public class ObjetRepo {
    private DBHELPER dbhelper;

    public ObjetRepo(Context context){
        dbhelper= new DBHELPER(context);
    }

    public int insert(ObjetTrouve objetTrouve){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ObjetTrouve.KEY_date,objetTrouve.date);
        values.put(ObjetTrouve.KEY_gare,objetTrouve.gare);
        values.put(ObjetTrouve.KEY_nature,objetTrouve.nature);
        values.put(ObjetTrouve.KEY_type,objetTrouve.type);


        long objetTrouve_id = db.insert(ObjetTrouve.TABLE,null,values);
        db.close();
        return (int) objetTrouve_id;
    }

    public ArrayList<HashMap<String ,String>> getObjetList(){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String selectquery = "SELECT " +
                ObjetTrouve.KEY_id+","+
                ObjetTrouve.KEY_date+","+
                ObjetTrouve.KEY_gare+","+
                ObjetTrouve.KEY_nature+","+
                ObjetTrouve.KEY_type+
                " FROM " + ObjetTrouve.TABLE;

        ArrayList<HashMap<String ,String>> objettrouveList = new ArrayList();

        Cursor cursor = db.rawQuery(selectquery,null);

        if (cursor.moveToFirst()){
            do{HashMap<String, String> objet = new HashMap<String, String>();
                objet.put("id",cursor.getString(cursor.getColumnIndex(ObjetTrouve.KEY_id)));
                objet.put("date",cursor.getString(cursor.getColumnIndex(ObjetTrouve.KEY_date)));
                objet.put("gare",cursor.getString(cursor.getColumnIndex(ObjetTrouve.KEY_gare)));
                objet.put("nature",cursor.getString(cursor.getColumnIndex(ObjetTrouve.KEY_nature)));
                objet.put("type",cursor.getString(cursor.getColumnIndex(ObjetTrouve.KEY_type)));
                objettrouveList.add(objet);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return objettrouveList;

    }

}
