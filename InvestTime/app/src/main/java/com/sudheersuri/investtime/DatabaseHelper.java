package com.sudheersuri.investtime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Investtime.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ACTIVITY";
    public static final String COL_3 = "TIMEINVESTED";
    public static final String COL_4 = "INVESTEDDATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ACTIVITY TEXT,TIMEINVESTED INTEGER,INVESTEDDATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String activityname, int timeinvested) {
        if (activityname.length() != 1)
            activityname = activityname.substring(0, 1).toUpperCase() + activityname.toLowerCase().substring(1);
        else
            activityname = activityname.toUpperCase();
        String date = new SimpleDateFormat("MM-DD-YY", Locale.getDefault()).format(new Date());
        long result = 1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select id,investeddate from " + TABLE_NAME + " WHERE ACTIVITY ='" + activityname + "' and INVESTEDDATE='" + date + "'", null);

        if (res.getCount() != 0) {
            res.moveToPosition(0);
            updateData(res.getString(0), res.getString(1), activityname, timeinvested);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, activityname);
            contentValues.put(COL_3, timeinvested);
            contentValues.put(COL_4, date);
            result = db.insert(TABLE_NAME, null, contentValues);
        }

        if (result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData(String datetext) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " WHERE INVESTEDDATE ='" + datetext + "'", null);
        return res;
    }

    public Cursor getSummaryData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ACTIVITY,sum(TIMEINVESTED) from " + TABLE_NAME + " GROUP BY ACTIVITY", null);
        return res;
    }


    public void updateData(String id, String date, String activityname, int timeinvested) {
        SQLiteDatabase db = this.getWritableDatabase();
        int intid = Integer.parseInt(id);
        Log.d("testid", String.valueOf(intid));
        Log.d("super", "update " + TABLE_NAME + " set activityname='" + activityname + "',timeinvested=" + timeinvested + " where id=" + intid, null);
        Cursor res = db.rawQuery("update " + TABLE_NAME + " set activity='" + activityname + "',timeinvested=timeinvested+" + timeinvested + " where id=" + intid + " and investeddate ='" + date + "'", null);
        res.moveToFirst();
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    public Cursor getDateData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select INVESTEDDATE from " + TABLE_NAME + " group by INVESTEDDATE order by INVESTEDDATE desc ", null);
        return res;
    }
}