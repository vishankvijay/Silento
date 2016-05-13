package my.awesome.silento;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by 1305166 on 18-09-2015.
 */
public class MyAlarm extends Service {
    AlarmManager alarmManager_start = null;
    AlarmManager alarmManager_end = null;
    PendingIntent setAlarm_start;
    PendingIntent setAlarm_end;
    DataBaseManipulator dataBaseManipulator;
    String start_profile_type;
    String end_profile_of_start_selected;
    String end_profile_of_next_to_next_start_selected;
    long end_time_of_start_selected;
    long end_time_of_next_to_next_start_selected;
    String end_profile_type;

    String profile_type_next_to_next;
    long start_time_check = 0;
    long end_time_check = 0;


    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        action(intent);

        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }


    public void action(Intent intent) {
        String actiontodo = intent.getAction();

        if (actiontodo.equals("setAlarm"))
            setAlarm();
        else if (actiontodo.equals("bootsetalarm"))
            bootSetAlarm();

    }

    public void bootSetAlarm()
    {
        long nxt_time_start = getnextAlarmTime_start();

        Intent intent_start1 = new Intent(MyAlarm.this, MyAlarmBroadcast.class);
        intent_start1.setAction("set_alarm");
        intent_start1.putExtra("id", 0);
        PendingIntent pendingAlarms_start = PendingIntent.getBroadcast(this, 0, intent_start1, PendingIntent.FLAG_CANCEL_CURRENT);
        if (nxt_time_start > 0)
            pushAlarm_start(nxt_time_start, pendingAlarms_start);



        long nxt_time_end = getnextAlarmTime_end();
        Intent intent_end1 = new Intent(MyAlarm.this, MyAlarmBroadcast.class);
        intent_end1.setAction("set_alarm");
        intent_end1.putExtra("id", 1);
        PendingIntent pendingAlarms_end = PendingIntent.getBroadcast(this, 1, intent_end1, PendingIntent.FLAG_CANCEL_CURRENT);
        if (nxt_time_end > 0)
            pushAlarm_end(nxt_time_end, pendingAlarms_end);

        stopSelf();

    }

    public void setAlarm() {

        long nxt_time_start = getnextAlarmTime_start();
        long nxt_time_end = getnextAlarmTime_end();
        long nxt_to_nxt_time_start = getnext_to_next_AlarmTime_start();
        long nxt_to_nxt_time_end = getnext_to_next_AlarmTime_end();


        if(end_time_of_start_selected>nxt_to_nxt_time_start&&end_time_of_start_selected<end_time_of_next_to_next_start_selected)
        {
           // Toast.makeText(MyAlarm.this, "Working", Toast.LENGTH_SHORT).show();
        }

        //Start Time Intent;
        Intent intent_start = new Intent(MyAlarm.this, MyAlarmBroadcast.class);
        intent_start.setAction("set_alarm");
        intent_start.putExtra("id", 0);
        intent_start.putExtra("profile_type", start_profile_type);
        setAlarm_start = PendingIntent.getBroadcast(this, 0, intent_start, PendingIntent.FLAG_CANCEL_CURRENT);

        if (nxt_time_start > System.currentTimeMillis()) {
            pushAlarm_start(nxt_time_start, setAlarm_start);
           // Toast.makeText(MyAlarm.this, "Push alarm called ", Toast.LENGTH_SHORT).show();
        } else {
            alarmManager_start = (AlarmManager) this.getSystemService(ALARM_SERVICE);
            alarmManager_start.cancel(setAlarm_start);
        }


        //End Time Intent

        Intent intent_end = new Intent(MyAlarm.this, MyAlarmBroadcast.class);
        intent_end.setAction("set_alarm");
        intent_end.putExtra("id", 1);
        intent_end.putExtra("profile_type", end_profile_type);
        setAlarm_end = PendingIntent.getBroadcast(this, 1, intent_end, PendingIntent.FLAG_CANCEL_CURRENT);

        if (nxt_time_end > System.currentTimeMillis()) {
            pushAlarm_end(nxt_time_end, setAlarm_end);
        } else {
            alarmManager_end = (AlarmManager) this.getSystemService(ALARM_SERVICE);
            alarmManager_end.cancel(setAlarm_end);

        }


        stopSelf();


    }

    public void pushAlarm_start(long nxt_time_start, PendingIntent setAlarm_start) {
        alarmManager_start = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        alarmManager_start.cancel(setAlarm_start);
        alarmManager_start.set(AlarmManager.RTC_WAKEUP, nxt_time_start, setAlarm_start);


    }


    public void pushAlarm_end(long nxt_time_end, PendingIntent setAlarm_end) {
        alarmManager_end = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        alarmManager_end.cancel(setAlarm_end);
        alarmManager_end.set(AlarmManager.RTC_WAKEUP, nxt_time_end, setAlarm_end);


    }


    public long getnextAlarmTime_start() {

        dataBaseManipulator = new DataBaseManipulator(this);
        String startHour;
        String startMinute;

        long nextTime_start = 0;
        long nextAlarmTime_start = 0;

        Calendar cal = Calendar.getInstance();
        final long currentTime = cal.getTimeInMillis();

        int start_position = 0;


        Cursor en_alarms_start = dataBaseManipulator.fetchenabledalarms();
        if (en_alarms_start != null) {
            en_alarms_start.moveToFirst();
            while (!en_alarms_start.isAfterLast()) {
                startHour = en_alarms_start.getString(2);
                startMinute = en_alarms_start.getString(3);


                ContentValues week = new ContentValues();
                week.put("sun", en_alarms_start.getInt(8) != 0);
                week.put("mon", en_alarms_start.getInt(9) != 0);
                week.put("tue", en_alarms_start.getInt(10) != 0);
                week.put("wed", en_alarms_start.getInt(11) != 0);
                week.put("thu", en_alarms_start.getInt(12) != 0);
                week.put("fri", en_alarms_start.getInt(13) != 0);
                week.put("sat", en_alarms_start.getInt(14) != 0);
                nextTime_start = alarmGetMillis_start(startHour, startMinute, week);

                if ((nextTime_start < nextAlarmTime_start || nextAlarmTime_start == 0) && nextTime_start > currentTime) {
                    nextAlarmTime_start = nextTime_start;
                    start_position = en_alarms_start.getPosition();
                }
                en_alarms_start.moveToNext();
            }


        }

        if (en_alarms_start.moveToFirst()) {
            en_alarms_start.moveToPosition(start_position);
            start_profile_type = en_alarms_start.getString(7);
            end_profile_of_start_selected = en_alarms_start.getString(15);
            String endHour_of_start_selected;
            String endMinute_of_start_selected;

            endHour_of_start_selected = en_alarms_start.getString(4);
            endMinute_of_start_selected = en_alarms_start.getString(5);


            ContentValues week = new ContentValues();
            week.put("sun", en_alarms_start.getInt(8) != 0);
            week.put("mon", en_alarms_start.getInt(9) != 0);
            week.put("tue", en_alarms_start.getInt(10) != 0);
            week.put("wed", en_alarms_start.getInt(11) != 0);
            week.put("thu", en_alarms_start.getInt(12) != 0);
            week.put("fri", en_alarms_start.getInt(13) != 0);
            week.put("sat", en_alarms_start.getInt(14) != 0);
            end_time_of_start_selected = alarmGetMillis_end(endHour_of_start_selected, endMinute_of_start_selected, week);

           // Toast.makeText(MyAlarm.this, start_profile_type, Toast.LENGTH_SHORT).show();
        }


        en_alarms_start.close();
        dataBaseManipulator.close();

        Calendar checkCalendar = Calendar.getInstance();

        if (nextAlarmTime_start != 0) {
            checkCalendar.setTimeInMillis(nextAlarmTime_start);
            //Toast.makeText(MyAlarm.this, "start - " + checkCalendar.get(Calendar.HOUR) + " : " + checkCalendar.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();

        }
        start_time_check = nextAlarmTime_start;

        return nextAlarmTime_start;


    }


    public long alarmGetMillis_start(String startHour, String startMinute, ContentValues week) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startHour));

        cal.set(Calendar.MINUTE, Integer.parseInt(startMinute));

        cal.set(Calendar.SECOND, 2);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_YEAR, getdayadd(week, true));
        if (cal.getTimeInMillis() < System.currentTimeMillis())
            cal.add(Calendar.DAY_OF_YEAR, getdayadd(week, false));
        return cal.getTimeInMillis();

    }

    private int getdayadd(ContentValues week, boolean todayFlag) {
        Calendar cal = Calendar.getInstance();
        int addcount = 0;
        if (!todayFlag) {
            addcount = 1;
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        while (true) {
            if (dayofweek == Calendar.SUNDAY) {
                if (week.getAsBoolean("sun"))
                    break;
                else {

                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    dayofweek = cal.get(Calendar.DAY_OF_WEEK);
                    addcount += 1;

                }
            } else if (dayofweek == Calendar.MONDAY) {
                if (week.getAsBoolean("mon"))
                    break;
                else {
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    dayofweek = cal.get(Calendar.DAY_OF_WEEK);
                    addcount += 1;
                }
            } else if (dayofweek == Calendar.TUESDAY) {
                if (week.getAsBoolean("tue"))
                    break;
                else {
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    dayofweek = cal.get(Calendar.DAY_OF_WEEK);
                    addcount += 1;
                }
            } else if (dayofweek == Calendar.WEDNESDAY) {
                if (week.getAsBoolean("wed"))
                    break;
                else {
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    dayofweek = cal.get(Calendar.DAY_OF_WEEK);
                    addcount += 1;
                }
            } else if (dayofweek == Calendar.THURSDAY) {
                if (week.getAsBoolean("thu"))
                    break;
                else {
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    dayofweek = cal.get(Calendar.DAY_OF_WEEK);
                    addcount += 1;
                }
            } else if (dayofweek == Calendar.FRIDAY) {
                if (week.getAsBoolean("fri"))
                    break;
                else {
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    dayofweek = cal.get(Calendar.DAY_OF_WEEK);
                    addcount += 1;
                }
            } else if (dayofweek == Calendar.SATURDAY) {
                if (week.getAsBoolean("sat"))
                    break;
                else {
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    dayofweek = cal.get(Calendar.DAY_OF_WEEK);
                    addcount += 1;
                }
            }
        }
        return addcount;

    }


    public long getnextAlarmTime_end() {

        dataBaseManipulator = new DataBaseManipulator(this);
        String endHour;
        String endMinute;

        long nextTime_end = 0;
        long nextAlarmTime_end = 0;
        Calendar calendar_end = Calendar.getInstance();
        final long currentTime = calendar_end.getTimeInMillis();

        int end_position = 0;


        Cursor en_alarms_end = dataBaseManipulator.fetchenabledalarms();
        if (en_alarms_end != null) {
            en_alarms_end.moveToFirst();
            while (!en_alarms_end.isAfterLast()) {


                endHour = en_alarms_end.getString(4);
                endMinute = en_alarms_end.getString(5);


                ContentValues week = new ContentValues();
                week.put("sun", en_alarms_end.getInt(8) != 0);
                week.put("mon", en_alarms_end.getInt(9) != 0);
                week.put("tue", en_alarms_end.getInt(10) != 0);
                week.put("wed", en_alarms_end.getInt(11) != 0);
                week.put("thu", en_alarms_end.getInt(12) != 0);
                week.put("fri", en_alarms_end.getInt(13) != 0);
                week.put("sat", en_alarms_end.getInt(14) != 0);
                nextTime_end = alarmGetMillis_end(endHour, endMinute, week);

                if ((nextTime_end < nextAlarmTime_end || nextAlarmTime_end == 0) && nextTime_end > currentTime) {
                    nextAlarmTime_end = nextTime_end;
                    end_position = en_alarms_end.getPosition();

                }
                en_alarms_end.moveToNext();
            }
        }


        if (en_alarms_end.moveToFirst()) {
            en_alarms_end.moveToPosition(end_position);
            end_profile_type = en_alarms_end.getString(15);
        }

        en_alarms_end.close();
        dataBaseManipulator.close();

        if (nextAlarmTime_end != 0) {
            Calendar checkCalendar_end = Calendar.getInstance();
            checkCalendar_end.setTimeInMillis(nextAlarmTime_end);
            //Toast.makeText(MyAlarm.this, "End - " + checkCalendar_end.get(Calendar.HOUR) + " : " + checkCalendar_end.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();

        }

        end_time_check = nextAlarmTime_end;


        return nextAlarmTime_end;


    }

    private long getnext_to_next_AlarmTime_start() {

        dataBaseManipulator = new DataBaseManipulator(this);
        String startHour_next_to_next;
        String startMinute_next_to_next;

        long next_to_next_Time_start = 0;
        long next_toNext_AlarmTime_start = 0;

        Calendar cal = Calendar.getInstance();
        final long currentTime = cal.getTimeInMillis();

        int position_next_to_next = 0;


        Cursor en_alarms_start_nex_to_next = dataBaseManipulator.fetchenabledalarms();
        if (en_alarms_start_nex_to_next != null) {
            en_alarms_start_nex_to_next.moveToFirst();
            while (!en_alarms_start_nex_to_next.isAfterLast()) {
                startHour_next_to_next = en_alarms_start_nex_to_next.getString(2);
                startMinute_next_to_next = en_alarms_start_nex_to_next.getString(3);


                ContentValues week = new ContentValues();
                week.put("sun", en_alarms_start_nex_to_next.getInt(8) != 0);
                week.put("mon", en_alarms_start_nex_to_next.getInt(9) != 0);
                week.put("tue", en_alarms_start_nex_to_next.getInt(10) != 0);
                week.put("wed", en_alarms_start_nex_to_next.getInt(11) != 0);
                week.put("thu", en_alarms_start_nex_to_next.getInt(12) != 0);
                week.put("fri", en_alarms_start_nex_to_next.getInt(13) != 0);
                week.put("sat", en_alarms_start_nex_to_next.getInt(14) != 0);
                next_to_next_Time_start = alarmGetMillis_start(startHour_next_to_next, startMinute_next_to_next, week);
                Log.d("subha", "n to n " + next_to_next_Time_start + " start  " + start_time_check);

                if ((next_to_next_Time_start != start_time_check) && (next_to_next_Time_start < next_toNext_AlarmTime_start || next_toNext_AlarmTime_start == 0) && next_to_next_Time_start > currentTime) {
                    next_toNext_AlarmTime_start = next_to_next_Time_start;
                    position_next_to_next = en_alarms_start_nex_to_next.getPosition();
                }
                en_alarms_start_nex_to_next.moveToNext();
            }


        }

        if (en_alarms_start_nex_to_next.moveToFirst()) {
            en_alarms_start_nex_to_next.moveToPosition(position_next_to_next);
            profile_type_next_to_next = en_alarms_start_nex_to_next.getString(7);
            end_profile_of_next_to_next_start_selected = en_alarms_start_nex_to_next.getString(15);
            String endHour_of_next_to_next_start_selected;
            String endMinute_of_next_to_next_start_selected;

            endHour_of_next_to_next_start_selected = en_alarms_start_nex_to_next.getString(4);
            endMinute_of_next_to_next_start_selected = en_alarms_start_nex_to_next.getString(5);


            ContentValues week = new ContentValues();
            week.put("sun", en_alarms_start_nex_to_next.getInt(8) != 0);
            week.put("mon", en_alarms_start_nex_to_next.getInt(9) != 0);
            week.put("tue", en_alarms_start_nex_to_next.getInt(10) != 0);
            week.put("wed", en_alarms_start_nex_to_next.getInt(11) != 0);
            week.put("thu", en_alarms_start_nex_to_next.getInt(12) != 0);
            week.put("fri", en_alarms_start_nex_to_next.getInt(13) != 0);
            week.put("sat", en_alarms_start_nex_to_next.getInt(14) != 0);
            end_time_of_next_to_next_start_selected = alarmGetMillis_end(endHour_of_next_to_next_start_selected, endMinute_of_next_to_next_start_selected, week);
        }


        Log.d("subha", "n to n" + next_toNext_AlarmTime_start + " end " + end_time_check);
        if ((next_toNext_AlarmTime_start < end_time_check) && (next_toNext_AlarmTime_start != 0)) {
            SharedPreferences sharedPreferences = getSharedPreferences("MyNextProfileType", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();


            editor.putString("next_profile_type", start_profile_type);
            editor.commit();

        }


        en_alarms_start_nex_to_next.close();
        dataBaseManipulator.close();
        if (next_toNext_AlarmTime_start != 0) {
            Calendar checkCalendar_next = Calendar.getInstance();
            checkCalendar_next.setTimeInMillis(next_toNext_AlarmTime_start);
           // Toast.makeText(MyAlarm.this, "Next to Next" + checkCalendar_next.get(Calendar.HOUR) + " : " + checkCalendar_next.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();


        }

        return next_toNext_AlarmTime_start;


    }

    private long getnext_to_next_AlarmTime_end() {

        dataBaseManipulator = new DataBaseManipulator(this);
        String endHour_next_to_next;
        String endMinute_next_to_next;

        long next_to_next_Time_end = 0;
        long next_toNext_AlarmTime_end = 0;

        Calendar cal = Calendar.getInstance();
        final long currentTime = cal.getTimeInMillis();

        int position_next_to_next = 0;


        Cursor en_alarms_end_nex_to_next = dataBaseManipulator.fetchenabledalarms();
        if (en_alarms_end_nex_to_next != null) {
            en_alarms_end_nex_to_next.moveToFirst();
            while (!en_alarms_end_nex_to_next.isAfterLast()) {
                endHour_next_to_next = en_alarms_end_nex_to_next.getString(4);
                endMinute_next_to_next = en_alarms_end_nex_to_next.getString(5);


                ContentValues week = new ContentValues();
                week.put("sun", en_alarms_end_nex_to_next.getInt(8) != 0);
                week.put("mon", en_alarms_end_nex_to_next.getInt(9) != 0);
                week.put("tue", en_alarms_end_nex_to_next.getInt(10) != 0);
                week.put("wed", en_alarms_end_nex_to_next.getInt(11) != 0);
                week.put("thu", en_alarms_end_nex_to_next.getInt(12) != 0);
                week.put("fri", en_alarms_end_nex_to_next.getInt(13) != 0);
                week.put("sat", en_alarms_end_nex_to_next.getInt(14) != 0);
                next_to_next_Time_end = alarmGetMillis_end(endHour_next_to_next, endMinute_next_to_next, week);
                Log.d("subha", "n to n " + next_to_next_Time_end + " start  " + start_time_check);

                if ((next_to_next_Time_end != end_time_check) && (next_to_next_Time_end < next_toNext_AlarmTime_end || next_toNext_AlarmTime_end == 0) && next_to_next_Time_end > currentTime) {
                    next_toNext_AlarmTime_end = next_to_next_Time_end;
                    position_next_to_next = en_alarms_end_nex_to_next.getPosition();
                }
                en_alarms_end_nex_to_next.moveToNext();
            }


        }

        if (en_alarms_end_nex_to_next.moveToFirst()) {
            en_alarms_end_nex_to_next.moveToPosition(position_next_to_next);
            profile_type_next_to_next = en_alarms_end_nex_to_next.getString(15);
        }


        Log.d("subha", "n to n" + next_toNext_AlarmTime_end + " end " + end_time_check);
      /*  if ((next_toNext_AlarmTime_end < end_time_check) && (next_toNext_AlarmTime_end != 0)) {
            SharedPreferences sharedPreferences = getSharedPreferences("MyNextProfileType", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();


            editor.putString("next_profile_type", end_profile_type);
            editor.commit();

        }*/


        en_alarms_end_nex_to_next.close();
        dataBaseManipulator.close();
        if (next_toNext_AlarmTime_end != 0) {
            Calendar checkCalendar_next_end = Calendar.getInstance();
            checkCalendar_next_end.setTimeInMillis(next_toNext_AlarmTime_end);
           // Toast.makeText(MyAlarm.this, "Next to Next End" + checkCalendar_next_end.get(Calendar.HOUR) + " : " + checkCalendar_next_end.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
        }

        return next_toNext_AlarmTime_end;


    }

    public long alarmGetMillis_end(String endHour, String endMinute, ContentValues week) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endHour));
        cal.set(Calendar.MINUTE, Integer.parseInt(endMinute));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);


        cal.add(Calendar.DAY_OF_YEAR, getdayadd(week, true));
        if (cal.getTimeInMillis() < System.currentTimeMillis())
            cal.add(Calendar.DAY_OF_YEAR, getdayadd(week, false));


        return cal.getTimeInMillis();

    }


}
