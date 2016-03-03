package com.example.silento;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class QuickSilentoActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Toolbar toolbar;
    Button setTimeButton;
    String hourString ;
    String minuteString;
    LinearLayout layoutSelectProfile;
    TextView textViewSelectProfile;

    RadioGroup radioGroupQuick ;
    RadioButton quickSilentRadioButton ;
    RadioButton quickVibrationRadioButton;
    RadioButton quickNormalRadioButton ;

    RelativeLayout mysnackbar;

    int hour;
    int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_silento);
        instantiate();


        radioGroupQuick = (RadioGroup)findViewById(R.id.quick_silento_start_profileRadioGroup);
        quickSilentRadioButton = (RadioButton)findViewById(R.id.quick_silento_start_silentRadioButton);
        quickVibrationRadioButton = (RadioButton)findViewById(R.id.quick_silento_start_vibrationRadioButton);
        quickNormalRadioButton = (RadioButton)findViewById(R.id.quick_silento_start_NormalRadioButton);

        SharedPreferences sharedPreferences_quick = getSharedPreferences("QuickData", MODE_PRIVATE);
        String quick_initial_profile_type = sharedPreferences_quick.getString("quick_change_profile", "Silent");

        if (quick_initial_profile_type.equals("Silent"))
            quickSilentRadioButton.setChecked(true);
        else if (quick_initial_profile_type.equals("Vibration"))
            quickVibrationRadioButton.setChecked(true);
        else
            quickNormalRadioButton.setChecked(true);




        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        QuickSilentoActivity.this,
                        0,
                        0,
                        true
                );

                //current = "start";
                //tpd.setThemeDark(true);
                tpd.vibrate(true);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");

            }
        });

    }


    public void instantiate() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quick Silento!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTimeButton = (Button) findViewById(R.id.quick_silento_button);
        layoutSelectProfile = (LinearLayout) findViewById(R.id.layout_what_profile);
        textViewSelectProfile = (TextView) findViewById(R.id.textview_what_profile);

        mysnackbar = (RelativeLayout) findViewById(R.id.snackbar);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minuteofDay) {
        String text = null;
        hour =hourOfDay;
        minute = minuteofDay;
        hourString = hour < 10 ? "0" + hour : "" + hour;
        minuteString = minute < 10 ? "0" + minute : "" + minute;

        text = getMinute();



        setTimeButton.setText(text);

        String first = "What profile do you want for the next ";
        String second = "<font color='#D56E83'>"+text+"</font>";
        String last = " ?";

        textViewSelectProfile.setText(Html.fromHtml(first + second + last));

        startTransition(text);

    }

    private void startTransition(String text)
    {
        if(!text.equals("Set Duration"))
        {
           /* Animation animation = null;
            animation = AnimationUtils.loadAnimation(QuickSilentoActivity.this, R.anim.slide_in_right);
            animation.setDuration(250);
            layoutSelectProfile.setVisibility(View.VISIBLE);
            layoutSelectProfile.startAnimation(animation);

            animation = null;*/

            TranslateAnimation translateAnimation = new TranslateAnimation(0 ,0 , 2 * layoutSelectProfile.getWidth() , 0);
            translateAnimation.setDuration(250);
            //translateAnimation.setFillAfter(true);
            layoutSelectProfile.setVisibility(View.VISIBLE);
            layoutSelectProfile.startAnimation(translateAnimation);
           /* Snackbar snackbar = Snackbar
                    .make(mysnackbar, "Welcome to AndroidHive", Snackbar.LENGTH_INDEFINITE);

            snackbar.show();*/
            translateAnimation= null;

        }
        else
            layoutSelectProfile.setVisibility(View.INVISIBLE);
    }

    private String getMinute()
    {
        String txt_to_return = null;
        if (hourString.equals("00"))
        {
            if(minuteString.equals("00"))
            {
                txt_to_return = "Set Duration";
            }
            else if(minuteString.equals("01"))
            {
                txt_to_return = minuteString + " minute";
            }
            else
            {
                txt_to_return = minuteString + " minutes";
            }

        }

        if (hourString.equals("01"))
        {
            if(minuteString.equals("00"))
            {
                txt_to_return = hourString + " hour ";
            }
            else if(minuteString.equals("01"))
            {
                txt_to_return = hourString+ " hour " + minuteString + " minute";
            }
            else
            {
                txt_to_return = hourString+ " hour " + minuteString + " minutes";
            }

        }

        if (!hourString.equals("00") && !hourString.equals("01"))
        {
            if(minuteString.equals("00"))
            {
                txt_to_return = hourString + " hours";
            }
            else if(minuteString.equals("01"))
            {
                txt_to_return = hourString+ " hours " + minuteString + " minute";
            }
            else
            {
                txt_to_return = hourString+ " hours " + minuteString + " minutes";
            }

        }

        return txt_to_return;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alarm_details, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.action_save:
                //Toast.makeText(QuickSilentoActivity.this, "working " + hour + " " + minute, Toast.LENGTH_SHORT).show();




               /* final RadioGroup radioGroupQuick = (RadioGroup)findViewById(R.id.quick_silento_start_profileRadioGroup);
                RadioButton quickSilentRadioButton = (RadioButton)findViewById(R.id.quick_silento_start_silentRadioButton);
                RadioButton quickVibrationRadioButton = (RadioButton)findViewById(R.id.quick_silento_start_vibrationRadioButton);
                RadioButton quickNormalRadioButton = (RadioButton)findViewById(R.id.quick_silento_start_NormalRadioButton);

                SharedPreferences sharedPreferences_quick = getSharedPreferences("QuickData", MODE_PRIVATE);
                String quick_initial_profile_type = sharedPreferences_quick.getString("quick_change_profile", "Silent");

                if (quick_initial_profile_type.equals("Silent"))
                    quickSilentRadioButton.setChecked(true);
                else if (quick_initial_profile_type.equals("Vibration"))
                    quickVibrationRadioButton.setChecked(true);
                else
                    quickNormalRadioButton.setChecked(true);


*/

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.add(Calendar.HOUR_OF_DAY, hour);
                        calendar.add(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);

                        //Toast.makeText(AlarmList.this , ""+calendar.get(Calendar.HOUR_OF_DAY)+":"+ calendar.get(Calendar.MINUTE) , Toast.LENGTH_SHORT).show();

                        if (!(minute == 0 && hour == 0)) {

                            RadioButton selectedRadioButtonQuick = (RadioButton)findViewById(radioGroupQuick.getCheckedRadioButtonId());
                            //Toast.makeText(QuickSilentoActivity.this, "inside" + selectedRadioButtonQuick.getText(), Toast.LENGTH_SHORT).show();


                            AudioManager audioManager = (AudioManager) getSystemService(QuickSilentoActivity.this.AUDIO_SERVICE);

                            SharedPreferences sharedPreferences_quick_2 = getSharedPreferences("QuickData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences_quick_2.edit();
                            editor.putInt("quick_silento_profile_if_not_conflicting" ,audioManager.getRingerMode());

                            if (selectedRadioButtonQuick.getText().equals("Silent"))
                            {
                                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                editor.putString("quick_change_profile", "Silent");

                            }
                            else if (selectedRadioButtonQuick.getText().equals("Vibration"))
                            {
                                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                editor.putString("quick_change_profile", "Vibration");
                            }
                            else
                            {
                                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                editor.putString("quick_change_profile", "Normal");
                            }
                            editor.putBoolean("quick_silento_active_state" , true);

                            showNotification(calendar);

                            Intent quick_intent = new Intent(QuickSilentoActivity.this, MyAlarmBroadcast.class);
                            quick_intent.putExtra("id", 2);
                            PendingIntent quick_pending_intent = PendingIntent.getBroadcast(QuickSilentoActivity.this, 2, quick_intent, PendingIntent.FLAG_CANCEL_CURRENT);

                            editor.putLong("time", calendar.getTimeInMillis());
                            editor.commit();

                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.cancel(quick_pending_intent);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), quick_pending_intent);

                            SharedPreferences sharedPreferences = getSharedPreferences("holdFirstActivityAnimation", MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = sharedPreferences.edit();
                            editor1.putBoolean("holdFirstActivityAnimation", true);
                            editor1.commit();
                            onBackPressed();
                            finish();

                        }
                else
                        showSnackbar("Please Set Duration First");
                           // Toast.makeText(QuickSilentoActivity.this, "Please Set Duration First", Toast.LENGTH_SHORT).show();



                break;

            case android.R.id.home :
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showNotification(Calendar calendar)
    {
        SharedPreferences sharedPreferences_quick_1 = getSharedPreferences("QuickData", Context.MODE_PRIVATE);
        Boolean get_notification_state = sharedPreferences_quick_1.getBoolean("get_notification_state" , false);


        if(get_notification_state) {
            Intent notifIntent = new Intent(QuickSilentoActivity.this, AlarmList.class);
            PendingIntent pendingNotifyIntent = PendingIntent.getActivity(QuickSilentoActivity.this, 0, notifIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText("Ends at " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
            bigText.setBigContentTitle("Silento!");
            // bigText.setSummaryText("By: Vihank Vijay");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(QuickSilentoActivity.this);
            builder.setAutoCancel(true);
            builder.setContentTitle("Silento! - Quick Change");
            builder.setContentText("Ends at " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
            builder.setSmallIcon(R.mipmap.ic_logo);
            builder.setStyle(bigText);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(QuickSilentoActivity.this);


            stackBuilder.addParentStack(AlarmList.class);
            stackBuilder.addNextIntent(notifIntent);

            builder.setContentIntent(pendingNotifyIntent);

            Notification n = builder.build();
            NotificationManager notificationManager = (NotificationManager) QuickSilentoActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, n);

        }
    }

    /*@Override
    public void onBackPressed()
    {
        super.onBackPressed();

        SharedPreferences sharedPreferences = getSharedPreferences("holdFirstActivityAnimation", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("holdFirstActivityAnimation", true);
        editor.commit();

        startActivity(new Intent(QuickSilentoActivity.this, FirstActivity.class));
        overridePendingTransition(R.anim.slide_in_right, 0);
    }*/

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right, 0);
    }


    private void showSnackbar(String message)
    {
        Snackbar.make(layoutSelectProfile, message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.mdtp_accent_color ))
                .show();
    }
}
