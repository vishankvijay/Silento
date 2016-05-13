package my.awesome.silento;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by 1305166 on 20-09-2015.
 */
public class BootSetAlarmBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //Toast.makeText( context , " Time Broadcast Called" , Toast.LENGTH_LONG).show();
        Intent i = new Intent(context,MyAlarm.class);
        i.setAction("setAlarm");
        context.startService(i);




        SharedPreferences sharedPreferences = context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        Boolean enableValue = sharedPreferences.getBoolean("enableValue", false);
      //  Toast.makeText(context , "enable value after : "+enableValue ,Toast.LENGTH_LONG).show();

        if (enableValue == true)
        {
            Intent shakeIntent = new Intent(context , shakeService2.class);
            context.startService(shakeIntent);
        }


        SharedPreferences quickSharedPreferences = context.getSharedPreferences("QuickData", Context.MODE_PRIVATE);
        Long time  = quickSharedPreferences.getLong("time" ,0);

       if(time > System.currentTimeMillis())
       {
           Intent quick_boot_intent = new Intent(context , MyAlarmBroadcast.class);
           quick_boot_intent.putExtra("id", 2);
           PendingIntent quick_pending_intent = PendingIntent.getBroadcast(context, 2, quick_boot_intent, PendingIntent.FLAG_CANCEL_CURRENT);

           AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
           alarmManager.cancel(quick_pending_intent);
           alarmManager.set(AlarmManager.RTC_WAKEUP, time, quick_pending_intent);
          // Toast.makeText(context , "Boot Last" +time , Toast.LENGTH_SHORT).show();
       }



    }
}
