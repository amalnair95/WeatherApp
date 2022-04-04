package com.example.weatherapp.databaseFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String TAG = DatabaseHelper.class.getSimpleName();
    public DatabaseHelper(Context context) {
        super(context, "virenxia.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table TokenDetails(id Integer primary key,RefreshToken TEXT,UserName TEXT,Password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop Table if exists TokenDetails");
    }

    public Boolean insertTokenData(String RefreshToken,String Username,String Password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("refreshToken",RefreshToken);
        contentValues.put("userName",Username);
        contentValues.put("password",Password);
        long result=db.insert("TokenDetails",null,contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor ExecuteBiQuery(String Query) {
        SQLiteDatabase db=this.getWritableDatabase();
        try {
            if (db.isOpen()) {
                Cursor c = db.rawQuery(Query, null);
                if (c.moveToFirst()) {
                    Log.d(TAG, "Get cursor column count " + c.getCount());
                    return c;
                }
                return null;

            } else {
                Log.d(TAG, "Database is not open unfortunately!");
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in bi Query " + e.toString());
        } finally {

        }
        return null;
    }


    public Boolean updateTokenData(String RefreshToken){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("refreshToken",RefreshToken);
        Cursor cursor =db.rawQuery("Select * from TokenDetails where id=1",new String[]{"1"});
        if (cursor.getCount()>0){
            long result=db.update("TokenDetails",contentValues,"id=?",new String[]{"1"});
            if (result==-1){
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }

    }

    public Boolean deleteTokenData(String RefreshToken){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("refreshToken",RefreshToken);
        Cursor cursor =db.rawQuery("Select * from TokenDetails where id=?",new String[]{"1"});
        if (cursor.getCount()>0){
            long result=db.delete("TokenDetails","id=?",new String[]{"1"});
            if (result==-1){
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }

    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("Select * from TokenDetails",null);
        return cursor;
    }
}
