package my.awesome.silento;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by vishank on 23/3/16.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationListenerService extends android.service.notification.NotificationListenerService
{
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean start = intent.getBooleanExtra("start", false);
        if(start)
        {
            Log.d("TAG","START");

            //Check if at least Lollipop, otherwise use old method
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                requestInterruptionFilter(INTERRUPTION_FILTER_PRIORITY);

               // Toast.makeText(NotificationListenerService.this, "Called", Toast.LENGTH_LONG).show();
            }
            else{
                AudioManager am = (AudioManager) getBaseContext().getSystemService(AUDIO_SERVICE);
                am.setRingerMode(0);
            }


        }
        else
        {
            Log.d("TAG", "STOP");
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                requestInterruptionFilter(INTERRUPTION_FILTER_ALL);
            else{
                AudioManager am = (AudioManager) getBaseContext().getSystemService(AUDIO_SERVICE);
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        }
        return super.onStartCommand(intent, flags, startId);

    }
}
