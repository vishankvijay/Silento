package com.example.silento;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 1305166 on 07-10-2015.
 */
public class MyExceptionReciever extends BroadcastReceiver
{
    private Context context;
    private String PhoneNumber;
    private int ringerMode=1;
    String state=null;
    Bundle bundle=null;
    public static final String TAG = "PHONE STATE";
    private static String mLastState;
    DataBaseManipulator dataBaseManipulator;



    @Override
    public void onReceive(Context context, Intent intent)
    {

        this.context = context;


        Log.d("subha","Exception Broadcast Called");



    try
    {

        bundle = intent.getExtras();
        PhoneNumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);


        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);


        if (!state.equals(mLastState))
        {

            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING))
            {

                checkCurrentProfile();


                dataBaseManipulator = new DataBaseManipulator(context);
                Cursor getNumberCursor = dataBaseManipulator.fetchException();

                if (getNumberCursor != null)
                {
                    getNumberCursor.moveToFirst();
                    while (!getNumberCursor.isAfterLast())
                    {

                        String numberInDB = getNumberCursor.getString(2);

                        if(PhoneNumber.equals(numberInDB))
                        {
                            makeitNormal();
                            break;
                        }

                        getNumberCursor.moveToNext();
                    }
                }




            }


                if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
                {


                    restorePreviousProfile();

                }


            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {


                restorePreviousProfile();
            }

            mLastState = state;
        }
    }
    catch(Exception e)
    {
    }
    }

    private void restorePreviousProfile()
    {
        AudioManager vishankManager=(AudioManager)context.getSystemService(context.AUDIO_SERVICE);
        int ringer;
        SharedPreferences sharedPreferences76 = context.getSharedPreferences("MyExceptionProfile", context.MODE_PRIVATE);
        ringer = sharedPreferences76.getInt("profileType", 3);


        if (ringer==AudioManager.RINGER_MODE_SILENT)
        {

            vishankManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

        }
        else if(ringer==AudioManager.RINGER_MODE_NORMAL){
            vishankManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        else if(ringer==AudioManager.RINGER_MODE_VIBRATE)
        {
            vishankManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }


    }

    private void makeitNormal()
    {

        AudioManager manager=(AudioManager)context.getSystemService(context.AUDIO_SERVICE);

        manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        manager.setStreamVolume(AudioManager.STREAM_MUSIC, 7, 0);
    }

    private void checkCurrentProfile()
    {
        AudioManager maudio=(AudioManager)context.getSystemService(context.AUDIO_SERVICE);
        ringerMode=maudio.getRingerMode();


        SharedPreferences sharedPreferences = context.getSharedPreferences("MyExceptionProfile", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("profileType", ringerMode);
        editor.commit();


    }





}

