package com.example.silento;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by 1305166 on 18-09-2015.
 */
public class MyAlarmBroadcast extends BroadcastReceiver {

    DataBaseManipulator dataBaseManipulator;

    @Override
    public void onReceive(Context context, Intent intent) {

        int id = intent.getExtras().getInt("id");

        String profile_type = intent.getExtras().getString("profile_type");

  /*      AudioManager am_initial = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
        SharedPreferences sharedPreferences_initial_profile = context.getSharedPreferences("MyInitialProfile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences_initial_profile.edit();
        editor.putInt("initial_profile", am_initial.getRingerMode());
        editor.commit();
*/
        if (id == 0) {


            SharedPreferences sharedPreferences_quick_0 = context.getSharedPreferences("QuickData", Context.MODE_PRIVATE);
            Boolean quick_silento_active_state_0 = sharedPreferences_quick_0.getBoolean("quick_silento_active_state", false);

            if (!quick_silento_active_state_0) {

                AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
                if (profile_type.equals("Silent")) {

                    makeItSilent(context);

                } else if (profile_type.equals("Vibration")) {
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                } else
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                showNotification(context, "Silento!", "Silento! has successfully changed your profile.\nClick here to manage your events.");

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
            else
            {
                showNotification(context , "Profile change Postponed" , "Profile change Postponed as Quick Silento! is active.\nProfiles will be restored according to your Event List once Quick Silento! is done with it's work.");
            }


            // Toast.makeText(context, "" + quick_silento_active_state_0, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, MyAlarm.class);
            i.setAction("setAlarm");
            context.startService(i);
        }


        if (id == 1) {

            SharedPreferences sharedPreferences = context.getSharedPreferences("MyNextProfileType", Context.MODE_PRIVATE);
            String profileType = sharedPreferences.getString("next_profile_type", null);
            Log.d("subha", "" + profileType);

            SharedPreferences sharedPreferences_quick_1 = context.getSharedPreferences("QuickData", Context.MODE_PRIVATE);
            Boolean quick_silento_active_state_1 = sharedPreferences_quick_1.getBoolean("quick_silento_active_state", false);


            if (!quick_silento_active_state_1) {


                dataBaseManipulator = new DataBaseManipulator(context);
                String startHour;
                String startMinute;
                String EndHour;
                String EndMinute;

                Cursor alarms_start = dataBaseManipulator.fetchenabledalarms();
                String profileType_2 = "";

                if (alarms_start != null) {
                    int temp_start_time = 0;
                    Calendar checkCalendar = Calendar.getInstance();
                    checkCalendar.setTimeInMillis(System.currentTimeMillis());


                    int current_hour = checkCalendar.get(Calendar.HOUR_OF_DAY);
                    int current_minute = checkCalendar.get(Calendar.MINUTE);
                    //   int second = 2;
                    long current_time = (60 * current_minute) + (3600 * current_hour);


                    alarms_start.moveToFirst();
                    while (!alarms_start.isAfterLast()) {
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

                        if (((startTime > temp_start_time) || temp_start_time == 0) && (startTime < current_time) && (EndTime > current_time)) {
                            temp_start_time = (int) startTime;
                            profileType_2 = alarms_start.getString(7);
                           // Toast.makeText(context, "1 inside", Toast.LENGTH_SHORT).show();

                        }

                   /* if ((nextTime_start < nextAlarmTime_start || nextAlarmTime_start == 0) && nextTime_start > currentTime)
                    {
                        nextAlarmTime_start = nextTime_start;
                        start_position = en_alarms_start.getPosition();
                    }*/
                        alarms_start.moveToNext();
                    }

                    AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);

                    if (!profileType_2.equals("")) {

                        if (profileType_2.equals("Silent")) {
                            makeItSilent(context);
                        } else if (profileType_2.equals("Vibration")) {
                            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        } else if (profileType_2.equals("Normal")) {
                            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            //Toast.makeText(context, "Naya Maaza aa gaya", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        if (profile_type.equals("Silent")) {
                            makeItSilent(context);
                        } else if (profile_type.equals("Vibration")) {
                            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                        } else
                            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    }

                    //   Toast.makeText(context, "2 " + profileType_2, Toast.LENGTH_SHORT).show();

                    showNotification(context, "Silento!", "Silento! has successfully changed your profile.\nClick here to manage your events.");

                }









               /* AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);

                if (profile_type.equals("Silent")) {
                    am.setRingerMode(0);
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
            else
            {
                showNotification(context , "Profile change Postponed" , "Profile change Postponed as Quick Silento! is active.\nProfiles will be restored according to your Event List once Quick Silento! is done with it's work.");

            }


            // Toast.makeText(context, "" + quick_silento_active_state_1, Toast.LENGTH_SHORT).show();

            Intent i = new Intent(context, MyAlarm.class);
            i.setAction("setAlarm");
            context.startService(i);
        }


        if (id == 2) {

            // Toast.makeText(context, "called", Toast.LENGTH_SHORT).show();

            dataBaseManipulator = new DataBaseManipulator(context);
            String startHour;
            String startMinute;
            String EndHour;
            String EndMinute;

            Cursor alarms_start = dataBaseManipulator.fetchenabledalarms();
            String profileType_2 = "";

            if (alarms_start != null) {
                int temp_start_time = 0;
                Calendar checkCalendar = Calendar.getInstance();
                checkCalendar.setTimeInMillis(System.currentTimeMillis());


                int current_hour = checkCalendar.get(Calendar.HOUR_OF_DAY);
                int current_minute = checkCalendar.get(Calendar.MINUTE);
                //   int second = 2;
                long current_time = (60 * current_minute) + (3600 * current_hour);


                alarms_start.moveToFirst();
                while (!alarms_start.isAfterLast()) {
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
                    //Toast.makeText(context, "Inside " + current_hour, Toast.LENGTH_SHORT).show();

                    if (((startTime > temp_start_time) || temp_start_time == 0) && (startTime < current_time) && (EndTime > current_time)) {
                        temp_start_time = (int) startTime;
                        profileType_2 = alarms_start.getString(7);

                      //  Toast.makeText(context, "Real Inside", Toast.LENGTH_SHORT).show();

                    }

                   /* if ((nextTime_start < nextAlarmTime_start || nextAlarmTime_start == 0) && nextTime_start > currentTime)
                    {
                        nextAlarmTime_start = nextTime_start;
                        start_position = en_alarms_start.getPosition();
                    }*/
                    alarms_start.moveToNext();
                }
               // Toast.makeText(context, "called " + profileType_2, Toast.LENGTH_SHORT).show();

                AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);

                if (profileType_2.equals("Silent")) {
                    makeItSilent(context);
                   // Toast.makeText(context, "called 0", Toast.LENGTH_SHORT).show();
                } else if (profileType_2.equals("Vibration")) {
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                   // Toast.makeText(context, "called 1", Toast.LENGTH_SHORT).show();
                } else if (profileType_2.equals("Normal")) {
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                   // Toast.makeText(context, "called normal", Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences sharedPreferences_quick_2 = context.getSharedPreferences("QuickData", Context.MODE_PRIVATE);
                    int quick_silento_profile_if_not_conflicting = sharedPreferences_quick_2.getInt("quick_silento_profile_if_not_conflicting", 2);

                    switch (quick_silento_profile_if_not_conflicting) {
                        case 0:
                            makeItSilent(context);
                          //  Toast.makeText(context, "called  non con 0", Toast.LENGTH_SHORT).show();
                            break;

                        case 1:
                            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                            //Toast.makeText(context, "called  non con 1", Toast.LENGTH_SHORT).show();
                            break;

                        case 2:
                            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            //Toast.makeText(context, "called  non con 2", Toast.LENGTH_SHORT).show();
                            break;
                    }

                }

                // Toast.makeText(context, "2 " + profileType_2, Toast.LENGTH_SHORT).show();


            }

            SharedPreferences sharedPreferences_quick = context.getSharedPreferences("QuickData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_quick = sharedPreferences_quick.edit();
            editor_quick.putBoolean("quick_silento_active_state", false);
            editor_quick.commit();

            showNotification(context, "Silento!", "Silento! has successfully changed your profile.\nClick here to manage your events.");


        }


    }

    private void makeItSilent(Context context) {
        AudioManager silent_manager = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
        int count = 0;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (Settings.Global.getInt(context.getContentResolver(), "zen_mode") == 2 || Settings.Global.getInt(context.getContentResolver(), "zen_mode") == 1)
                    ++count;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (count == 0) {
            silent_manager.setRingerMode(2);
            silent_manager.setRingerMode(0);
            silent_manager.setRingerMode(0);
        }

    }

    private void showNotification(Context context, String title, String message) {
        SharedPreferences sharedPreferences_quick_1 = context.getSharedPreferences("QuickData", Context.MODE_PRIVATE);
        Boolean get_notification_state = sharedPreferences_quick_1.getBoolean("get_notification_state", true);

        if (get_notification_state) {
            Intent notifIntent = new Intent(context, AlarmList.class);
            PendingIntent pendingNotifyIntent = PendingIntent.getActivity(context, 0, notifIntent, PendingIntent.FLAG_CANCEL_CURRENT);

         /*   NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText("Silento! has successfully changed your profile.click here to manage your profiles.");
            bigText.setBigContentTitle("Silento!");*/

        /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                final Notification.Builder builder = new Notification.Builder(context);

                builder.setStyle(new Notification.BigTextStyle(builder)
                        .bigText(message)
                        .setBigContentTitle(title));

                builder.setAutoCancel(true);

                builder.setSmallIcon(R.mipmap.ic_silento_logo);
                builder.setContentIntent(pendingNotifyIntent);

                Notification n = builder.build();
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, n);
            }
            else
            {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setContentTitle(title);
                builder.setContentText(message);
                builder.setAutoCancel(true);

                builder.setSmallIcon(R.mipmap.ic_silento_logo);
                builder.setContentIntent(pendingNotifyIntent);

                Notification n = builder.build();
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, n);
            }*/


            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
            style.setBigContentTitle(title);
            style.bigText(message);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(title);
            builder.setContentText(message);
            builder.setAutoCancel(true);
            builder.setTicker("Silento! Profile Changed.");
            builder.setSmallIcon(R.mipmap.ic_silento_logo);
            builder.setContentIntent(pendingNotifyIntent);

            builder.setStyle(style);

            Notification n = builder.build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, n);




            // NotificationCompat.Builder builder = new NotificationCompat.Builder(context);


           /* builder.setAutoCancel(true);

            builder.setSmallIcon(R.mipmap.ic_silento_logo);*/
            //  builder.setStyle(bigText);


          /*  TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);


            stackBuilder.addParentStack(AlarmList.class);
            stackBuilder.addNextIntent(notifIntent);*/

          //  builder.setContentIntent(pendingNotifyIntent);

            /*Notification n = builder.build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, n);*/
        }
    }
}
