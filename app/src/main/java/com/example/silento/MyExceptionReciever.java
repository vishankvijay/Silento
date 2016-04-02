package com.example.silento;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
    int count = 0;



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
                            checkCurrentProfile();
                            makeitNormal();
                            ++count;

                            SharedPreferences sharedPreferences = context.getSharedPreferences("MyExceptionProfile", context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("incoming" , true);
                            editor.apply();

                            break;
                        }

                        getNumberCursor.moveToNext();
                    }
                }




            }


                if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
                {

                    Toast.makeText(context, "count offkook "+count, Toast.LENGTH_SHORT).show();

                   // if(count>0)
                   // restorePreviousProfile();

                 //   checkCurrentProfile();
                    //makeitNormal();

                }


            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {
                Toast.makeText(context, "IDLE"+count, Toast.LENGTH_SHORT).show();

               // if(count>0)
                SharedPreferences sharedPreferences76 = context.getSharedPreferences("MyExceptionProfile", context.MODE_PRIVATE);
               Boolean incoming = sharedPreferences76.getBoolean("incoming", false);
                if(incoming)
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


        Toast.makeText(context, "restore mode "+ ringer, Toast.LENGTH_SHORT).show();


        if (ringer==0)
        {


            int count = 0;
            try
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if(Settings.Global.getInt(context.getContentResolver(), "zen_mode") == 2 || Settings.Global.getInt(context.getContentResolver(), "zen_mode") == 1)
                        ++count;
                }
            }
            catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            if(count == 0) {
                vishankManager.setRingerMode(0);
                vishankManager.setRingerMode(0);
            }

        }
        else if(ringer==AudioManager.RINGER_MODE_NORMAL){

            vishankManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        else if(ringer==AudioManager.RINGER_MODE_VIBRATE)
        {
            vishankManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
        else
            Toast.makeText(context, "YOU Were right", Toast.LENGTH_SHORT).show();


        SharedPreferences sharedPreferences = context.getSharedPreferences("MyExceptionProfile", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("incoming", false);
        editor.apply();


    }

    private void makeitNormal()
    {

        AudioManager manager=(AudioManager)context.getSystemService(context.AUDIO_SERVICE);

        manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        //manager.setStreamVolume(AudioManager.STREAM_MUSIC, 7, 0);


    }

    private void checkCurrentProfile()
    {


        AudioManager maudio=(AudioManager)context.getSystemService(context.AUDIO_SERVICE);
        ringerMode=maudio.getRingerMode();

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyExceptionProfile", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Toast.makeText(context, " mode " + ringerMode, Toast.LENGTH_SHORT).show();


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            try {
                switch (Settings.Global.getInt(context.getContentResolver(), "zen_mode"))
                {
                    case 2: ;
                    case 1:
                        editor.putInt("profileType", 0);

                        break;

                    case  0 :
                        if(ringerMode == 1)
                        editor.putInt("profileType", 1);
                        else
                            editor.putInt("profileType", 2);

                }
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            editor.putInt("profileType", ringerMode);
        }




        editor.apply();


    }





}

