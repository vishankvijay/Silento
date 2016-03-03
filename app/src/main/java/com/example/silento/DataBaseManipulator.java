package com.example.silento;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by 1305166 on 16-09-2015.
 */
public class DataBaseManipulator {

    private static final String DATABASE_NAME = "dataBase";
    private static final int DATABASE_VERSION = 12;
    public static final String TABLE_NAME1 = "alarm";
    public static final String TABLE_NAME2 = "exception";
    private static Context context;

    OpenHelper openHelper;
    ContentValues alarm;
    ContentValues exception;
    static SQLiteDatabase db;

    public DataBaseManipulator(Context context)
    {
        DataBaseManipulator.context = context;
        openHelper = new OpenHelper(DataBaseManipulator.context);
        DataBaseManipulator.db = openHelper.getWritableDatabase();

    }

    public void alarmsave(Intent intent)
    {
        alarm = new ContentValues();
        alarm = putvalues(intent,alarm);
        db.insert(TABLE_NAME1, null, alarm);
        Log.d("Vishankkkkkkkkkkkkkkkkk", "alarm save called");

    }

    public void alarmupdate(Intent intent,int alarmid)
    {
        alarm = new ContentValues();
        alarm = putvalues(intent,alarm);
        db.update(TABLE_NAME1, alarm, String.format("id=%d", alarmid), null);
        Log.d("Vishankkkkkkkkkkkkkkkkk", "alarm update called" + alarmid);

    }

    public void saveException(Intent intent)
    {
        exception = new ContentValues();
        exception = putExceptionValues(intent, exception);
        db.insert(TABLE_NAME2, null, exception);
        Log.d("Vishankkkkkkkkkkkkkkkkk", "exception save called");

    }


    public void updateException(Intent intent,int exception_id)
    {
        exception = new ContentValues();
        exception = putExceptionValues(intent, exception);
        db.update(TABLE_NAME2, exception, String.format("id=%d", exception_id), null);
        Log.d("Vishankkkkkkkkkkkkkkkkk", "exception update called" + exception_id);

    }

    public void deleteAlarm(int alarmid)
    {
        db.delete(TABLE_NAME1, String.format("id=%d", alarmid), null);
    }

    public void deleteException(int exceptionid)
    {
        db.delete(TABLE_NAME2, String.format("id=%d", exceptionid), null);
    }


    public void deleteAllAlarm()
    {
        db.delete(TABLE_NAME1, null, null);
    }

    public Cursor fetchalarms()
    {
        return db.query(TABLE_NAME1, null, null, null, null, null, "id"+" DESC");
    }

    public Cursor fetchenabledalarms()
    {
        return db.query(TABLE_NAME1, null, "status=?", new String[]{"1"}, null, null, null);
    }

    public Cursor fetchException()
    {
        return db.query(TABLE_NAME2, null, null, null, null, null, null);
    }


    public ContentValues putvalues(Intent intent, ContentValues alarm){

        alarm.put("profileName",intent.getStringExtra("profileName"));
        alarm.put("startHour",intent.getStringExtra("startHour"));
        alarm.put("startMinute",intent.getStringExtra("startMinute"));
        alarm.put("endHour",intent.getStringExtra("endHour"));
        alarm.put("endMinute",intent.getStringExtra("endMinute"));
        alarm.put("sun", intent.getBooleanExtra("sun", false)?1:0);
        alarm.put("mon", intent.getBooleanExtra("mon", false)?1:0);
        alarm.put("tue", intent.getBooleanExtra("tue", false)?1:0);
        alarm.put("wed", intent.getBooleanExtra("wed", false)?1:0);
        alarm.put("thur", intent.getBooleanExtra("thur", false)?1:0);
        alarm.put("fri", intent.getBooleanExtra("fri", false)?1:0);
        alarm.put("sat", intent.getBooleanExtra("sat", false)?1:0);
        alarm.put("start_profileType", intent.getStringExtra("start_profileType"));
        alarm.put("end_profileType", intent.getStringExtra("end_profileType"));
        alarm.put("status" , intent.getIntExtra("status", 1));

        return alarm;
    }





    public ContentValues putExceptionValues(Intent intent, ContentValues exception){

        exception.put("exceptionName",intent.getStringExtra("exceptionName"));
        exception.put("exceptionNumber",intent.getStringExtra("exceptionNumber"));

        return exception;
    }






    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {

            db.execSQL("CREATE TABLE " + TABLE_NAME1 + " (id INTEGER PRIMARY KEY AUTOINCREMENT, status INTEGER, startHour TEXT, startMinute TEXT, endHour TEXT, endMinute TEXT, profileName TEXT, start_profileType TEXT , sun INTEGER, mon INTEGER, tue INTEGER, wed INTEGER, thur INTEGER, fri INTEGER, sat INTEGER, end_profileType TEXT)");
                                                                            // 0                        1               2                   3               4           5               6                   7                   8           9           10          11              12          13            14                    15
            db.execSQL("CREATE TABLE " + TABLE_NAME2 + " (id INTEGER PRIMARY KEY AUTOINCREMENT, exceptionName TEXT, exceptionNumber TEXT , UNIQUE(exceptionName, exceptionNumber) ON CONFLICT REPLACE )");
                                                                        // 0                                1                   2

            Log.d("Vishankkkkkkkkkkkkkkkkk" , "Entry Done");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
            Log.d("Vishankkkkkkkkkkkkkkkkk", "onUpgrade called");
            onCreate(db);
        }
    }

    public void close()
    {
        openHelper.close();
        db.close();
    }

}


