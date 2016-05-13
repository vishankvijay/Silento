package my.awesome.silento;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

/**
 * Created by vishank on 11/4/16.
 */


public class RingtoneManagerService extends Service
{
    private Ringtone ringtone;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //Uri ringtoneUri = Uri.parse(intent.getExtras().getString("ringtone-uri"));

        Uri alertUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        this.ringtone = RingtoneManager.getRingtone(this, alertUri);

        //Toast.makeText(RingtoneManagerService.this, "called before", Toast.LENGTH_SHORT).show();

        if(this.ringtone != null && !this.ringtone.isPlaying())
        {
            this.ringtone.play();
           // Toast.makeText(RingtoneManagerService.this, "called after", Toast.LENGTH_SHORT).show();


        }
       // ringtone.play();

        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        ringtone.stop();
        //Toast.makeText(RingtoneManagerService.this, "stop", Toast.LENGTH_SHORT).show();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}