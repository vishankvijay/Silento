package com.example.silento;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;


public class AlarmList extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton addAlarmFab;
    //private ListView alarmListview;
    private RecyclerView alarmRecyclerView;

    private DisplayMetrics metrics;
    // private SensorManager mSensorManager;
    //private Sensor mAccelerometer;
    //private  static ShakeDetector mShakeDetector = null;

    Cursor alarms;
    DataBaseManipulator dataBaseManipulator;
    int count = 0;
    int alarmId_delete = -1;
    String alarmTitle_delete;


    private NavigationView mDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private GetProfileTask task;
    customListAdapter customListAdapter;

    TextView emptyTextView;
    int animation_count = 0;

    ArrayList<ProfilesList> profilesLists_ArrayList;
    private RecyclerView.Adapter adapter;

    recycler_adapter recycler_adapter;

    //com.melnykov.fab.FloatingActionButton fab ;

    FloatingActionButton fab;

    CoordinatorLayout alarmList_coordinator;

    TextView text_hi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        /*metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);*/


        instantiate();

       /* drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();*/
        //mDrawer.setNavigationItemSelectedListener(this);


        emptyTextView = (TextView) findViewById(R.id.emptyTextView);

//        alarmListview.setEmptyView(emptyTextView);
//        alarmListview.setOnItemClickListener(AlarmList.this);
//        alarmListview.setOnItemLongClickListener(AlarmList.this);

        //fab.attachToRecyclerView(alarmRecyclerView);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmList.this, AlarmDetails.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.slide_in_left, 0);
            }
        });

       /* task = new GetProfileTask(this);
        task.execute((Void) null);*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null) {
            if (resultCode == Activity.RESULT_OK)
            {
                if(requestCode == 1)
                showSnackbar(data.getStringExtra("name") , requestCode);
                else
                    showSnackbar(data.getStringExtra("name") , requestCode);
            }
        }


    }

    public void startUpdateActivity(int position) {
        Intent intent = new Intent(AlarmList.this, updateAlarmActivity.class);
        intent.putExtra("position", position);
        startActivityForResult(intent, 2);
        overridePendingTransition(R.anim.slide_in_left, 0);
    }


    public void showSnackbar(String message , int reqCode) {

        if (message.length() > 25)
        {
            message = message.substring( 0 , 25) + "...";
        }


        if(reqCode == 1)
            message = "'" +message+ "'" + "  has been added to your Event List.";
        else if(reqCode == 2)
            message = "'" +message+ "'" + "  has been updated.";
        else
            message = "'" +message+ "'" + "  has been removed from your Event List.";



        Snackbar.make(alarmList_coordinator, message, Snackbar.LENGTH_LONG)
                .setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.mdtp_accent_color))
                .show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && animation_count == 0) {
            TranslateAnimation translateAnimation = new TranslateAnimation(2 * fab.getWidth(), 0, 0, 0);
            translateAnimation.setDuration(700);
            translateAnimation.setFillAfter(true);
            fab.startAnimation(translateAnimation);
            fab.setVisibility(View.VISIBLE);
            ++animation_count;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        task = new GetProfileTask(this);
        task.execute((Void) null);

    }

    public class GetProfileTask extends
            AsyncTask<Void, Void, ArrayList<ProfilesList>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetProfileTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<ProfilesList> doInBackground(Void... params) {
            // ArrayList<profileParcel> profileParcel1 = new ArrayList<profileParcel>();

            ArrayList<ProfilesList> profilesListArrayList = new ArrayList<ProfilesList>();

            dataBaseManipulator = new DataBaseManipulator(AlarmList.this);

            Cursor cursor = dataBaseManipulator.fetchalarms();

            while (cursor.moveToNext()) {

                ProfilesList profilesList = new ProfilesList();

                profilesList.setId(cursor.getInt(0));
                profilesList.setStatus(cursor.getInt(1));
                profilesList.setStartHour(cursor.getString(2));
                profilesList.setStartMinute(cursor.getString(3));
                profilesList.setEndHour(cursor.getString(4));
                profilesList.setEndMinute(cursor.getString(5));
                profilesList.setProfileName(cursor.getString(6));
                profilesList.setstart_profileType(cursor.getString(7));
                profilesList.setSun(cursor.getInt(8));
                profilesList.setMon(cursor.getInt(9));
                profilesList.setTue(cursor.getInt(10));
                profilesList.setWed(cursor.getInt(11));
                profilesList.setThur(cursor.getInt(12));
                profilesList.setFri(cursor.getInt(13));
                profilesList.setSat(cursor.getInt(14));
                profilesList.setend_profileType(cursor.getString(15));

                profilesListArrayList.add(profilesList);

            }


            return profilesListArrayList;
        }


        @Override
        protected void onPostExecute(ArrayList<ProfilesList> empList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                //subParcels = empList;
                //Bundle bundle = new Bundle();
                // bundle.putParcelableArrayList("array", empList);
                if (empList != null) {
                    if (empList.size() != 0) {
                        recycler_adapter = new recycler_adapter(AlarmList.this,
                                empList, metrics);
                        //alarmListview.setAdapter(customListAdapter);

                        alarmRecyclerView.setAdapter(recycler_adapter);
                        //Toast.makeText(AlarmList.this, " not 0", Toast.LENGTH_SHORT).show();
                        setEmptyView(1);
                        if(empList.size()==1)
                        showDragtutorial();
                    }
                    else
                    {
                        setEmptyView(0);
                    }
                }

            }
        }

        private void showDragtutorial()
        {
            new MaterialShowcaseView.Builder(AlarmList.this)
                    .setTarget(text_hi)
                    .setDismissText("ok")
                    .setDismissTextColor(0xffD56E83)
                    .setContentText("Drag left to get more options.")
                    .setDelay(200) // optional but starting animations immediately in onCreate can make them choppy
                    .singleUse("alarm_list_1") // provide a unique ID used to ensure it is only shown once
                    .show();
        }
    }


   /* private void initializeListView() {
        dataBaseManipulator = new DataBaseManipulator(this);
        alarms = dataBaseManipulator.fetchalarms();

        String[] alarmlist = new String[alarms.getCount()];
        if (alarms != null) {
            alarms.moveToFirst();
            count = 0;
            while (!alarms.isAfterLast()) {
                alarmlist[count] = alarms.getString(6);
                count++;
                alarms.moveToNext();
            }
            ArrayAdapter<String> alarmadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alarmlist);
            alarmListview.setAdapter(alarmadapter);
        } else
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        alarms.close();
        //dataBaseManipulator.close();
    }*/


    public void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        alarmList_coordinator = (CoordinatorLayout) findViewById(R.id.alarmList_coordinator);
        text_hi = (TextView) findViewById(R.id.text_hi);

        //alarmListview = (ListView) findViewById(R.id.alarm_list);

        alarmRecyclerView = (RecyclerView) findViewById(R.id.alarm_list);
        alarmRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        alarmRecyclerView.setHasFixedSize(true);

        alarmRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && fab.isShown())
                    fab.hide();
            }
        });
        // alarmRecyclerView.setAdapter(new recycler_adapter(this));

        fab = (FloatingActionButton) findViewById(R.id.alarm_list_fab_button);

        //mDrawer = (NavigationView) findViewById(R.id.main_drawer);
        //  mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        // profilesLists_ArrayList = dataBaseManipulator.fetchalarms();

    }


    public void setEmptyView(int size) {

        // Toast.makeText(AlarmList.this, "called", Toast.LENGTH_SHORT).show();
        if (size == 0) {
            emptyTextView.setVisibility(View.VISIBLE);
            alarmRecyclerView.setVisibility(View.GONE);
        } else {
            emptyTextView.setVisibility(View.GONE);
            alarmRecyclerView.setVisibility(View.VISIBLE);
        }

    }




  /*  @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.item_0:


                Boolean enableValue = false;
                int seekBarValue = 2;
                String shake_initial_profile_type = "Silent";
                mDrawerLayout.closeDrawer(GravityCompat.START);

                LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.shake_dialog, null, false);

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);

                enableValue = sharedPreferences.getBoolean("enableValue", false);
                seekBarValue = sharedPreferences.getInt("seekBarValue", 2);
                shake_initial_profile_type = sharedPreferences.getString("profileType", "Silent");


                final CheckBox shakeEnableCheckBox = (CheckBox) view.findViewById(R.id.checkBox_shake_enable);
                final RadioGroup shakeRadioGroup = (RadioGroup) view.findViewById(R.id.shake_radio_group);
                final RadioButton silentRadioButton = (RadioButton) view.findViewById(R.id.silent_radio_button);
                final RadioButton vibrationRadioButton = (RadioButton) view.findViewById(R.id.vibration_radio_button);

                if (shake_initial_profile_type.equals("Silent"))
                    silentRadioButton.setChecked(true);
                else
                    vibrationRadioButton.setChecked(true);

                shakeEnableCheckBox.setChecked(enableValue);
                // enableValue = shakeEnableCheckBox.isChecked();

                SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar_shake);
                seekBar.setProgress(seekBarValue);
                seekBar.incrementProgressBy(1);
                seekBar.setMax(30);

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("seekBarValue", progress);
                        editor.commit();
                        // Toast.makeText(AlarmList.this , "seekBar Value" + progress , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setView(view);
                dialog.setTitle("Shake Silento!!");
                dialog.setIcon(R.mipmap.ic_silento_logo);

                dialog.setPositiveButton("Set", new DialogInterface.OnClickListener() {

                    int seek_bar_value;
                    Boolean enable_value;


                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);

                        seek_bar_value = sharedPreferences.getInt("seekBarValue", 2);


                        if (shakeEnableCheckBox.isChecked()) {


                            RadioButton selected_Shake_RadioButton = (RadioButton) view.findViewById(shakeRadioGroup.getCheckedRadioButtonId());
                            String profile_type = null;
                            if (selected_Shake_RadioButton.getText().equals("Silent"))
                                profile_type = "Silent";
                            else
                                profile_type = "Vibration";


                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("enableValue", true);
                            editor.putString("profileType", profile_type);
                            editor.commit();


                            Intent shakeServiceIntent2 = new Intent(AlarmList.this, shakeService2.class);
                            shakeServiceIntent2.setAction("register");
                            startService(shakeServiceIntent2);


                        } else {


                            //SharedPreferences sharedPreferences = getSharedPreferences("MyData" , MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("enableValue", false);
                            editor.commit();

                            Intent shakeServiceIntent2 = new Intent(AlarmList.this, shakeService2.class);
                            shakeServiceIntent2.setAction("register");
                            // shakeServiceIntent.putExtra("seekBarValue", seek_bar_value);
                            stopService(shakeServiceIntent2);

                        }


                        //initializeShake();


                    }

                    private DialogInterface.OnClickListener init(int sbValue, boolean eValue) {
                        // seek_bar_value = sbValue;
                        enable_value = eValue;
                        return this;
                    }


                }.init(seekBarValue, enableValue));
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(AlarmList.this, "Cancel selected", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.show();


                return true;


            case R.id.item_1:
                // Toast.makeText(this, "Dummy 2", Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(GravityCompat.START);

                LayoutInflater inflater_timePicker = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view_timePicker = inflater_timePicker.inflate(R.layout.quick_profile, null, false);

                final TimePicker quickTimePicker = (TimePicker) view_timePicker.findViewById(R.id.timePicker_quick);
                quickTimePicker.setIs24HourView(true);
                quickTimePicker.setCurrentHour(0);
                quickTimePicker.setCurrentMinute(0);

                final RadioGroup radioGroupQuick = (RadioGroup) view_timePicker.findViewById(R.id.radioGroupQuick);
                RadioButton quickSilentRadioButton = (RadioButton) view_timePicker.findViewById(R.id.radioButtonQuick_silent);
                RadioButton quickVibrationRadioButton = (RadioButton) view_timePicker.findViewById(R.id.radioButtonQuick_vibration);

                SharedPreferences sharedPreferences_quick = getSharedPreferences("QuickData", MODE_PRIVATE);
                String quick_initial_profile_type = sharedPreferences_quick.getString("quick_change_profile", "Silent");

                if (quick_initial_profile_type.equals("Silent"))
                    quickSilentRadioButton.setChecked(true);
                else
                    quickVibrationRadioButton.setChecked(true);


                final AlertDialog.Builder dialog_quick_profile = new AlertDialog.Builder(this);
                dialog_quick_profile.setView(view_timePicker);

                dialog_quick_profile.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int hour = quickTimePicker.getCurrentHour();
                        int minute = quickTimePicker.getCurrentMinute();

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.add(Calendar.HOUR_OF_DAY, hour);
                        calendar.add(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);

                        //Toast.makeText(AlarmList.this , ""+calendar.get(Calendar.HOUR_OF_DAY)+":"+ calendar.get(Calendar.MINUTE) , Toast.LENGTH_SHORT).show();

                        if (!(minute == 0 && hour == 0)) {
                            RadioButton selectedRadioButtonQuick = (RadioButton) view_timePicker.findViewById(radioGroupQuick.getCheckedRadioButtonId());


                            AudioManager audioManager = (AudioManager) getSystemService(AlarmList.this.AUDIO_SERVICE);

                            SharedPreferences sharedPreferences_quick = getSharedPreferences("QuickData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences_quick.edit();
                            if (selectedRadioButtonQuick.getText().equals("silent")) {
                                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                editor.putString("quick_change_profile", "Silent");
                            } else {
                                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                editor.putString("quick_change_profile", "Vibration");

                            }

                            showNotification(calendar);

                            Intent quick_intent = new Intent(AlarmList.this, MyAlarmBroadcast.class);
                            quick_intent.putExtra("id", 2);
                            PendingIntent quick_pending_intent = PendingIntent.getBroadcast(AlarmList.this, 2, quick_intent, PendingIntent.FLAG_CANCEL_CURRENT);

                            editor.putLong("time", calendar.getTimeInMillis());
                            editor.commit();

                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.cancel(quick_pending_intent);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), quick_pending_intent);

                        }


                    }

                    private void showNotification(Calendar calendar) {


                        Intent notifIntent = new Intent(AlarmList.this, AlarmList.class);
                        PendingIntent pendingNotifyIntent = PendingIntent.getActivity(AlarmList.this, 0, notifIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                        bigText.bigText("Ends at " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
                        bigText.setBigContentTitle("Silento!");
                        // bigText.setSummaryText("By: Vihank Vijay");

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(AlarmList.this);
                        builder.setAutoCancel(true);
                        builder.setContentTitle("Silento! - Quick Change");
                        builder.setContentText("Ends at " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
                        builder.setSmallIcon(R.mipmap.ic_silento_logo);
                        builder.setStyle(bigText);

                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(AlarmList.this);


                        stackBuilder.addParentStack(AlarmList.class);
                        stackBuilder.addNextIntent(notifIntent);

                        builder.setContentIntent(pendingNotifyIntent);

                        Notification n = builder.build();
                        NotificationManager notificationManager = (NotificationManager) AlarmList.this.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(0, n);


                    }
                });

                dialog_quick_profile.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog_quick_profile.show();


                return true;


            case R.id.item_2:

                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent exceptionIntent = new Intent(AlarmList.this, ExceptionActiviy.class);
                startActivity(exceptionIntent);
                return true;


            case R.id.item_3:


                final AlertDialog.Builder deleteAllDialog = new AlertDialog.Builder(this);
                deleteAllDialog.setTitle("Wait");
                deleteAllDialog.setMessage("All Profiles will be Deleted");
                deleteAllDialog.setIcon(R.mipmap.ic_silento_logo);
                deleteAllDialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBaseManipulator = new DataBaseManipulator(AlarmList.this);
                        dataBaseManipulator.deleteAllAlarm();


                        Intent afterDeleteIntent = new Intent(AlarmList.this, AlarmList.class);
                        startActivity(afterDeleteIntent);
                        finish();

                    }
                });

                deleteAllDialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                deleteAllDialog.show();


                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;


            case R.id.item_4:

                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent aboutIntent = new Intent(AlarmList.this, aboutActivity.class);
                startActivity(aboutIntent);
                return true;


            case R.id.item_5:

                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent upcomingFeaturesIntent = new Intent(AlarmList.this, upcomingFeaturesActivity.class);
                startActivity(upcomingFeaturesIntent);
                return true;

            case R.id.item_6:
                //Toast.makeText(this, "Dummy 4", Toast.LENGTH_SHORT).show();

                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent feedBackIntent = new Intent(AlarmList.this, feedBackActivity.class);
                startActivity(feedBackIntent);
                return true;

        }

        return false;
    }*/

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        dataBaseManipulator = new DataBaseManipulator(this);

        Cursor getIdCursor = dataBaseManipulator.fetchalarms();
        getIdCursor.moveToPosition(position);

        Intent updateIntent = new Intent(AlarmList.this, updateAlarmActivity.class);
        updateIntent.putExtra("position", position);
        //id = getIdCursor.getInt(0);

        //updateIntent.putExtra("id" , getIdCursor.getInt(0));
        startActivityForResult(updateIntent, 2);
        overridePendingTransition(R.anim.slide_in_left, 0);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        final profileParcel profile = (profileParcel) parent
                .getItemAtPosition(position);


        dataBaseManipulator = new DataBaseManipulator(AlarmList.this);
        Cursor delete_alarm = dataBaseManipulator.fetchalarms();
        delete_alarm.moveToPosition(position);
        alarmId_delete = delete_alarm.getInt(0);
        alarmTitle_delete = delete_alarm.getString(6);

        AlertDialog.Builder builder = new AlertDialog.Builder(AlarmList.this);
        builder.setTitle("Wait!!");

        String first = "Delete the Event ";
        String next = "<font color='#E91E63'>" + alarmTitle_delete + "</font>";
        String last = " ?";
        //t.setText(Html.fromHtml(first + next));

        builder.setMessage(Html.fromHtml(first + next + last));

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();

            }
        });

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                dataBaseManipulator = new DataBaseManipulator(AlarmList.this);
                dataBaseManipulator.deleteAlarm(alarmId_delete);
                dataBaseManipulator.close();

                customListAdapter.remove(profile);
                //Toast.makeText(AlarmList.this, "Event "+alarmTitle_delete+" deleted", Toast.LENGTH_SHORT).show();
                //initializeListView();
                Intent i = new Intent(getBaseContext(), MyAlarm.class);
                i.setAction("setAlarm");
                startService(i);


            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        delete_alarm.close();
        dataBaseManipulator.close();
        return true;
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // drawerToggle.onConfigurationChanged(newConfig);

    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        dataBaseManipulator = new DataBaseManipulator(AlarmList.this);


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                dataBaseManipulator.alarmsave(data);
                //Toast.makeText( AlarmListActivity.this , "Sunday - " + data.getBooleanExtra("sun" , true) + "\n"  + "Monday - " + data.getBooleanExtra("mon" , true) + "\n"   + "Tuesday - " + data.getBooleanExtra("tue" , true) + "\n"  + "Wednesday - " + data.getBooleanExtra("wed" , true) + "\n"   + "Thursday - " + data.getBooleanExtra("thur" , true) + "\n"  + "Friday - " + data.getBooleanExtra("fri" , true) + "\n"   + "Saturday - " + data.getBooleanExtra("sat" , true), Toast.LENGTH_LONG).show();
            }

            if (requestCode == 2) {

                int alarmid = data.getIntExtra("id", 1);
                Log.d("Vishankkkkkkkkkkkkkkkkk", "Trick omg" + alarmid);

                dataBaseManipulator.alarmupdate(data, alarmid);

            }
        }
        dataBaseManipulator.close();

        Intent serviceIntent = new Intent(AlarmList.this, MyAlarm.class);
        serviceIntent.setAction("setAlarm");

        startService(serviceIntent);

    }*/

    @Override
    protected void onPause() {
        super.onPause();
        //++animation_count;
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();

        SharedPreferences sharedPreferences = getSharedPreferences("holdFirstActivityAnimation", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("holdFirstActivityAnimation", true);
        editor.commit();
        startActivity(new Intent(AlarmList.this, FirstActivity.class));
        overridePendingTransition(R.anim.slide_in_right, 0);
        animation_count = -1;
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right, 0);
    }

    private void showAlarmSnackbar(String message) {
        Snackbar.make(alarmList_coordinator, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.mdtp_accent_color))
                .show();
    }


}
