package my.awesome.silento;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by 1305166 on 30-09-2015.
 */
public class shakeService2 extends Service implements SensorEventListener
{


    private static String profileType ;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;


    // Minimum acceleration needed to count as a shake movement
    private static  int MIN_SHAKE_ACCELERATION = 6;


    // Minimum number of movements to register a shake
    private static final int MIN_MOVEMENTS = 2;

    // Maximum time (in milliseconds) for the whole shake to occur
    private static final int MAX_SHAKE_DURATION = 500;

    // Arrays to store gravity and linear acceleration values
    private float[] mGravity = { 0.0f, 0.0f, 0.0f };
    private float[] mLinearAcceleration = { 0.0f, 0.0f, 0.0f };

    // Indexes for x, y, and z values
    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;

    // OnShakeListener that will be notified when the shake is detected

    // Start time for the shake detection
    long startTime = 0;

    // Counter for shake movements
    int moveCount = 0;

    public shakeService2()
    {}

    public shakeService2(shakeSilentoActivity context)
    {
       //Toast.makeText(shakeService2.this, "construtor called", Toast.LENGTH_SHORT).show();

        //Log.d("subha" , "construtor " + MIN_SHAKE_ACCELERATION + " " + profileType);

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyData", context.MODE_PRIVATE);

        int  seekBarValue = sharedPreferences.getInt("seekBarValue", 2);
        profileType = sharedPreferences.getString("profileType", null);
        //profileType = sharedPreferences.getString("profileType", null);

        if(seekBarValue == 0 || seekBarValue == 1)
            MIN_SHAKE_ACCELERATION = 2;
        else
            MIN_SHAKE_ACCELERATION = seekBarValue;

       // Log.d("subha" , "construtor " + MIN_SHAKE_ACCELERATION + " " + profileType);
    }



    @Override
    public void onCreate() {
        super.onCreate();
       // Toast.makeText(shakeService2.this, "Vishank called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //Toast.makeText(shakeService2.this, "Vishank bind called", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Toast.makeText(shakeService2.this, "Vishank  command called", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getSharedPreferences("MyData" , MODE_PRIVATE);

        int  seekBarValue = sharedPreferences.getInt("seekBarValue", 2);
        profileType = sharedPreferences.getString("profileType", null);

        if(seekBarValue == 0 || seekBarValue == 1)
            MIN_SHAKE_ACCELERATION = 2;
        else
        MIN_SHAKE_ACCELERATION = seekBarValue;




        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);


        return START_STICKY;
    }



    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        /*SharedPreferences sharedPreferences = getSharedPreferences("MyData" , MODE_PRIVATE);

       int  seekBarValue = sharedPreferences.getInt("seekBarValue", 2);
        MIN_SHAKE_ACCELERATION = seekBarValue;
        Toast.makeText(shakeService2.this, "Set selected" +  MIN_SHAKE_ACCELERATION , Toast.LENGTH_SHORT).show();

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

        Toast.makeText(shakeService2.this , "onstart 2 called" , Toast.LENGTH_LONG).show();*/


    }


    @Override
    public void onSensorChanged(SensorEvent event)
    {
        // This method will be called when the accelerometer detects a change.

        // Call a helper method that wraps code from the Android developer site
        setCurrentAcceleration(event);

        // Get the max linear acceleration in any direction
        float maxLinearAcceleration = getMaxCurrentLinearAcceleration();

        // Check if the acceleration is greater than our minimum threshold
        if (maxLinearAcceleration > MIN_SHAKE_ACCELERATION) {
            long now = System.currentTimeMillis();

            // Set the startTime if it was reset to zero
            if (startTime == 0) {
                startTime = now;
            }

            long elapsedTime = now - startTime;

            // Check if we're still in the shake window we defined
            if (elapsedTime > MAX_SHAKE_DURATION) {
                // Too much time has passed. Start over!
                resetShakeDetection();
            }
            else {
                // Keep track of all the movements
                moveCount++;

                // Check if enough movements have been made to qualify as a shake
                if (moveCount > MIN_MOVEMENTS) {
                    // It's a shake! Notify the listener.
                    onShake();

                    // Reset for the next one!
                    resetShakeDetection();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void setCurrentAcceleration(SensorEvent event) {
       	/*
    	 *  BEGIN SECTION from Android developer site. This code accounts for
    	 *  gravity using a high-pass filter
    	 */

        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate

        final float alpha = 0.8f;

        // Gravity components of x, y, and z acceleration
        mGravity[X] = alpha * mGravity[X] + (1 - alpha) * event.values[X];
        mGravity[Y] = alpha * mGravity[Y] + (1 - alpha) * event.values[Y];
        mGravity[Z] = alpha * mGravity[Z] + (1 - alpha) * event.values[Z];

        // Linear acceleration along the x, y, and z axes (gravity effects removed)
        mLinearAcceleration[X] = event.values[X] - mGravity[X];
        mLinearAcceleration[Y] = event.values[Y] - mGravity[Y];
        mLinearAcceleration[Z] = event.values[Z] - mGravity[Z];

        /*
         *  END SECTION from Android developer site
         */
    }

    private float getMaxCurrentLinearAcceleration() {
        // Start by setting the value to the x value
        float maxLinearAcceleration = mLinearAcceleration[X];

        // Check if the y value is greater
        if (mLinearAcceleration[Y] > maxLinearAcceleration) {
            maxLinearAcceleration = mLinearAcceleration[Y];
        }

        // Check if the z value is greater
        if (mLinearAcceleration[Z] > maxLinearAcceleration) {
            maxLinearAcceleration = mLinearAcceleration[Z];
        }

        // Return the greatest value
        return maxLinearAcceleration;
    }

    private void resetShakeDetection() {
        startTime = 0;
        moveCount = 0;
    }

    /*
     * Definition for OnShakeListener definition. I would normally put this
     * into it's own .java file, but I included it here for quick reference
     * and to make it easier to include this file in our project.
     */

    public void onShake()
    {
       // Toast.makeText(shakeService2.this , "Profile Changed " + MIN_SHAKE_ACCELERATION + profileType, Toast.LENGTH_SHORT).show();
        Toast.makeText(shakeService2.this , "Profile Changed " , Toast.LENGTH_SHORT).show();


        AudioManager am = (AudioManager) getSystemService(shakeService2.this.AUDIO_SERVICE);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Vibrate for 400 milliseconds



        if(profileType.equals("Silent"))
        {

           // Toast.makeText(shakeService2.this, ""+am.getRingerMode(), Toast.LENGTH_SHORT).show();
            if (am.getRingerMode() == 1 )
            {
                am.setRingerMode(0);
                am.setRingerMode(0);
            }
            else if(am.getRingerMode() == 0)
            {

                int count1 = 0;
                try
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if( Settings.Global.getInt(getContentResolver(), "zen_mode") == 2)
                            ++count1;


                    }
                }
                catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }

                if(count1==0)
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                else
                {
                    am.setRingerMode(2);
                    am.setRingerMode(0);
                    am.setRingerMode(0);
                }

            }
            else
            {

                int count = 0;
                try
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if( Settings.Global.getInt(getContentResolver(), "zen_mode") == 1)
                            ++count;


                    }
                }
                catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }

                if(count == 0) {
                    am.setRingerMode(2);
                    am.setRingerMode(0);
                    am.setRingerMode(0);
                }
                else
                {
                    am.setRingerMode(2);
                }
            }
        }
        else
        {

          //  Toast.makeText(shakeService2.this, ""+am.getRingerMode(), Toast.LENGTH_SHORT).show();
            if (am.getRingerMode() == 0 )
            {
                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
            else if(am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE)
            {
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
            else
            {
                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
        }

        v.vibrate(350);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSensorManager.unregisterListener(this);

      //  Toast.makeText(shakeService2.this, "onDestroy called", Toast.LENGTH_SHORT).show();

      //  new shakeService2();

    }
}
