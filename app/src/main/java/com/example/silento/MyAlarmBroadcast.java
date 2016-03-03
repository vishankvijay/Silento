package com.example.silento;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by 1305166 on 18-09-2015.
 */
public class MyAlarmBroadcast extends BroadcastReceiver{

    DataBaseManipulator dataBaseManipulator;

    @Override
    public void onReceive(Context context, Intent intent)
    {

        int id = intent.getExtras().getInt("id");

        String profile_type = intent.getExtras().getString("profile_type");

    AudioManager am_initial = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
        SharedPreferences sharedPreferences_initial_profile = context.getSharedPreferences("MyInitialProfile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences_initial_profile.edit();
        editor.putInt("initial_profile" , am_initial.getRingerMode());
        editor.commit();

        if (id==0) {



            SharedPreferences sharedPreferences_quick_0 = context.getSharedPreferences("QuickData", Context.MODE_PRIVATE);
            Boolean quick_silento_active_state_0 = sharedPreferences_quick_0.getBoolean("quick_silento_active_state", false);

            if (!quick_silento_active_state_0)
            {

                AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
                if (profile_type.equals("Silent"))
                {
                    am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                else if (profile_type.equals("Vibration"))
                {
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                }
                else
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                showNotification(context);

/*
            Intent notifIntent = new Intent(context, AlarmList.class);
            PendingIntent pendingNotifyIntent = PendingIntent.getActivity(context, 0, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText("Silento! has successfully changed your profile.click here to manage your profiles.");
            bigText.setBigContentTitle("Silento!");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setAutoCancel(true);
            builder.setContentTitle("Silento!");
            builder.setContentText("Silento! has successfully changed your profile.\nclick here to manage your profiles.");
            builder.setSmallIcon(R.mipmap.ic_logo);
            builder.setStyle(bigText);


            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);


            stackBuilder.addParentStack(AlarmList.class);
            stackBuilder.addNextIntent(notifIntent);

            builder.setContentIntent(pendingNotifyIntent);

            Notification n = builder.build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, n);*/
        }


           // Toast.makeText(context, "" + quick_silento_active_state_0, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, MyAlarm.class);
            i.setAction("setAlarm");
            context.startService(i);
        }





        if (id==1)
        {

            SharedPreferences sharedPreferences = context.getSharedPreferences("MyNextProfileType", Context.MODE_PRIVATE);
            String profileType = sharedPreferences.getString("next_profile_type", null);
            Log.d("subha", "" + profileType);

            SharedPreferences sharedPreferences_quick_1 = context.getSharedPreferences("QuickData", Context.MODE_PRIVATE);
            Boolean quick_silento_active_state_1 = sharedPreferences_quick_1.getBoolean("quick_silento_active_state", false);


            if(!quick_silento_active_state_1) {






                dataBaseManipulator = new DataBaseManipulator(context);
                String startHour;
                String startMinute;
                String EndHour;
                String EndMinute;

                Cursor alarms_start = dataBaseManipulator.fetchenabledalarms();
                String profileType_2 = "";

                if (alarms_start != null)
                {
                    int temp_start_time = 0;
                    Calendar checkCalendar = Calendar.getInstance();
                    checkCalendar.setTimeInMillis(System.currentTimeMillis());


                    int current_hour = checkCalendar.get(Calendar.HOUR);
                    int current_minute = checkCalendar.get(Calendar.MINUTE);
                    //   int second = 2;
                    long current_time = (60 * current_minute) + (3600 * current_hour);


                    alarms_start.moveToFirst();
                    while (!alarms_start.isAfterLast())
                    {
                        startHour = alarms_start.getString(2);
                        startMinute = alarms_start.getString(3);
                        EndHour = alarms_start.getString(4);
                        EndMinute = alarms_start.getString(5);

                        int startHour_int = Integer.parseInt(startHour);
                        int startMinute_int = Integer.parseInt(startMinute);
                        int EndHour_int = Integer.parseInt(EndHour);
                        int EndMinute_int = Integer.parseInt(EndMinute);

                        long startTime = (60 * startMinute_int) + (3600 * startHour_int);
                        long EndTime = (60 * EndMinute_int) + (3600 * EndHour_int);

                        if(((startTime > temp_start_time) || temp_start_time == 0) && (startTime < current_time) &&(EndTime > current_time))
                        {
                            temp_start_time = (int) startTime;
                            profileType_2 = alarms_start.getString(7);

                        }

                   /* if ((nextTime_start < nextAlarmTime_start || nextAlarmTime_start == 0) && nextTime_start > currentTime)
                    {
                        nextAlarmTime_start = nextTime_start;
                        start_position = en_alarms_start.getPosition();
                    }*/
                        alarms_start.moveToNext();
                    }

                    AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);

                    if(!profileType_2.equals(""))
                    {

                        if (profileType_2.equals("Silent")) {
                            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        }

                        else if (profileType_2.equals("Vibration"))
                        {
                            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        }

                        else if (profileType_2.equals("Normal"))
                        {
                            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            //Toast.makeText(context, "Naya Maaza aa gaya", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else
                    {
                        if (profile_type.equals("Silent"))
                        {
                            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        }
                        else if (profile_type.equals("Vibration"))
                        {
                            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                        }
                        else
                            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    }

                 //   Toast.makeText(context, "2 " + profileType_2, Toast.LENGTH_SHORT).show();

                    showNotification(context);

                }









               /* AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);

                if (profile_type.equals("Silent")) {
                    am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                } else if (profile_type.equals("Vibration")) {
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                } else
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                Intent notifIntent = new Intent(context, AlarmList.class);
                PendingIntent pendingNotifyIntent = PendingIntent.getActivity(context, 0, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                bigText.bigText("Silento! has successfully changed your profile.click here to manage your profiles.");
                bigText.setBigContentTitle("Silento!");

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(true);
                builder.setContentTitle("Silento!");
                builder.setContentText("Silento! has successfully changed your profile.\nclick here to manage your profiles.");
                builder.setSmallIcon(R.mipmap.ic_logo);
                builder.setStyle(bigText);


                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);


                stackBuilder.addParentStack(AlarmList.class);
                stackBuilder.addNextIntent(notifIntent);

                builder.setContentIntent(pendingNotifyIntent);

                Notification n = builder.build();
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, n);

*/
            }


           // Toast.makeText(context, "" + quick_silento_active_state_1, Toast.LENGTH_SHORT).show();

            Intent i = new Intent(context, MyAlarm.class);
            i.setAction("setAlarm");
            context.startService(i);
        }


        if(id == 2)
        {


            dataBaseManipulator = new DataBaseManipulator(context);
            String startHour;
            String startMinute;
            String EndHour;
            String EndMinute;

            Cursor alarms_start = dataBaseManipulator.fetchenabledalarms();
            String profileType_2 = "";

            if (alarms_start != null)
            {
                int temp_start_time = 0;
                Calendar checkCalendar = Calendar.getInstance();
                checkCalendar.setTimeInMillis(System.currentTimeMillis());
                

                int current_hour = checkCalendar.get(Calendar.HOUR);
                int current_minute = checkCalendar.get(Calendar.MINUTE);
             //   int second = 2;
                long current_time = (60 * current_minute) + (3600 * current_hour);


                alarms_start.moveToFirst();
                while (!alarms_start.isAfterLast())
                {
                    startHour = alarms_start.getString(2);
                    startMinute = alarms_start.getString(3);
                    EndHour = alarms_start.getString(4);
                    EndMinute = alarms_start.getString(5);

                    int startHour_int = Integer.parseInt(startHour);
                    int startMinute_int = Integer.parseInt(startMinute);
                    int EndHour_int = Integer.parseInt(EndHour);
                    int EndMinute_int = Integer.parseInt(EndMinute);

                    long startTime = (60 * startMinute_int) + (3600 * startHour_int);
                    long EndTime = (60 * EndMinute_int) + (3600 * EndHour_int);

                    if(((startTime > temp_start_time) || temp_start_time == 0) && (startTime < current_time) &&(EndTime > current_time))
                    {
                        temp_start_time = (int) startTime;
                        profileType_2 = alarms_start.getString(7);

                    }

                   /* if ((nextTime_start < nextAlarmTime_start || nextAlarmTime_start == 0) && nextTime_start > currentTime)
                    {
                        nextAlarmTime_start = nextTime_start;
                        start_position = en_alarms_start.getPosition();
                    }*/
                    alarms_start.moveToNext();
                }

                AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);

                if(profileType_2.equals("Silent"))
                {
                    am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                else if(profileType_2.equals("Vibration"))
                {
                 am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                }
                else if(profileType_2.equals(""))
                {
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                  //  Toast.makeText(context, "Maaza aa gaya", Toast.LENGTH_SHORT).show();
                }

               // Toast.makeText(context, "2 " + profileType_2, Toast.LENGTH_SHORT).show();


            }

            SharedPreferences sharedPreferences_quick = context.getSharedPreferences("QuickData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_quick = sharedPreferences_quick.edit();
            editor_quick.putBoolean("quick_silento_active_state" , false);
            editor_quick.commit();

            showNotification(context);




        }







    }

    private void showNotification(Context context)
    {
        SharedPreferences sharedPreferences_quick_1 = context.getSharedPreferences("QuickData", Context.MODE_PRIVATE);
        Boolean get_notification_state = sharedPreferences_quick_1.getBoolean("get_notification_state" , false);

        if(get_notification_state) {
            Intent notifIntent = new Intent(context, AlarmList.class);
            PendingIntent pendingNotifyIntent = PendingIntent.getActivity(context, 0, notifIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText("Silento! has successfully changed your profile.click here to manage your profiles.");
            bigText.setBigContentTitle("Silento!");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setAutoCancel(true);
            builder.setContentTitle("Silento!");
            builder.setContentText("Silento! has successfully changed your profile.\nclick here to manage your profiles.");
            builder.setSmallIcon(R.mipmap.ic_logo);
            builder.setStyle(bigText);


            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);


            stackBuilder.addParentStack(AlarmList.class);
            stackBuilder.addNextIntent(notifIntent);

            builder.setContentIntent(pendingNotifyIntent);

            Notification n = builder.build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, n);
        }
    }
}