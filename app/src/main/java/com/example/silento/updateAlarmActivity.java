package com.example.silento;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.braunster.tutorialview.object.TutorialBuilder;
import com.braunster.tutorialview.object.TutorialIntentBuilder;*/
import com.rey.material.widget.Switch;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;


import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class updateAlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private Toolbar toolbar;

    Button startTimeUpdateButton;
    Button endTimeUpdateButton;
    String current;
    EditText profileNameUpdateEditText;
    public String currentHourTextStartUpdate = null;
    public String currentMinuteTextStartUpdate = null;
    public String currentHourTextEndUpdate = null;
    public String currentMinuteTextEndUpdate = null;
    com.rey.material.widget.CheckBox checkBoxSundayUpdate;
    com.rey.material.widget.CheckBox checkBoxMondayUpdate;
    com.rey.material.widget.CheckBox checkBoxTuesdayUpdate;
    com.rey.material.widget.CheckBox checkBoxWednesdayUpdate;
    com.rey.material.widget.CheckBox checkBoxThursdayUpdate;
    com.rey.material.widget.CheckBox checkBoxFridayUpdate;
    com.rey.material.widget.CheckBox checkBoxSaturdayUpdate;
    //CheckBox enableUpdateCheckBox;
    RadioGroup start_radioGroupUpdate;
    RadioGroup end_radioGroupUpdate;
    RadioButton start_selectedRadioUpdateButton;
    RadioButton end_selectedRadioUpdateButton;

    Switch enable_update_switch;

    Intent repeat_update = new Intent();
    Cursor updateCursor;

    DataBaseManipulator dataBaseManipulator;

    RelativeLayout activity_update_alarm_layout;

    int id;

    String SHOWCASE_ID = "2";

    TextView daysEnabledTextview;
    TextView start_profileTypeTextview;
    TextView end_profileTypeTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_alarm);

        instatntiate();
        showTutorial();


        startTimeUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        updateAlarmActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );

                current = "start";
                tpd.vibrate(false);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");

            }
        });

        endTimeUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now2 = Calendar.getInstance();
                TimePickerDialog tpd2 = TimePickerDialog.newInstance(
                        updateAlarmActivity.this,
                        now2.get(Calendar.HOUR_OF_DAY),
                        now2.get(Calendar.MINUTE),
                        false
                );

                current = "end";
                tpd2.vibrate(false);
                tpd2.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd2.show(getFragmentManager(), "Timepickerdialog");

            }
        });
    }

/*    private void showTutorial()
    {

        new MaterialIntroView.Builder(this)
                .enableDotAnimation(true)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.ALL)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText("Hi There! Click this card and see what happens.")
                .setTarget(enable_update_switch)
                .setUsageId("intro_Update") //THIS SHOULD BE UNIQUE ID
                .show();


     *//*   TutorialIntentBuilder builder = new TutorialIntentBuilder(updateAlarmActivity.this);

        TutorialBuilder tBuilder = new TutorialBuilder();

        tBuilder.setTitle("The Title")
                .setViewToSurround(profileNameUpdateEditText)
                .setInfoText("This is the explanation about the view.")
                .setBackgroundColor(R.color.mdtp_accent_color)
                .setTutorialTextColor(Color.WHITE)
                .setTutorialTextTypeFaceName("fonts/test_name.ttf")
                .setTutorialTextSize(25)
                .setAnimationDuration(500);

        builder.setTutorial(tBuilder.build());

        startActivity(builder.getIntent());

// Override the default animation of the entering activity.
// This will allow the nice wrapping of the view by the tutorial activity.
        overridePendingTransition(R.anim.dummy, R.anim.dummy);*//*
    }*/


    private void showTutorial()
    {
        new MaterialShowcaseView.Builder(this)
                .setTarget(enable_update_switch)
                .setDismissText("OK")
                .setContentText("Use this to ENABLE or Disable your Event.")
                .setDelay(200) // optional but starting animations immediately in onCreate can make them choppy
                .singleUse("update_1") // provide a unique ID used to ensure it is only shown once
                .show();
    }
    public void instatntiate() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Update");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();




        profileNameUpdateEditText = (EditText) findViewById(R.id.profileNameUpdateEditText);
        startTimeUpdateButton = (Button) findViewById(R.id.startTimeUpdateButton);
        endTimeUpdateButton = (Button) findViewById(R.id.endTimeUpdateButton);

        checkBoxSundayUpdate = (com.rey.material.widget.CheckBox) findViewById(R.id.sundayUpdateCheckBox);
        checkBoxMondayUpdate = (com.rey.material.widget.CheckBox) findViewById(R.id.MondayUpdateCheckBox);
        checkBoxTuesdayUpdate = (com.rey.material.widget.CheckBox) findViewById(R.id.TuesdayUpdateCheckBox);
        checkBoxWednesdayUpdate = (com.rey.material.widget.CheckBox) findViewById(R.id.wednesdayUpdateCheckBox);
        checkBoxThursdayUpdate = (com.rey.material.widget.CheckBox) findViewById(R.id.thursdayUpdateCheckBox);
        checkBoxFridayUpdate = (com.rey.material.widget.CheckBox) findViewById(R.id.fridayUpdateCheckBox);
        checkBoxSaturdayUpdate = (com.rey.material.widget.CheckBox) findViewById(R.id.saturdayUpdateCheckBox);

        enable_update_switch = (Switch) findViewById(R.id.enable_update_switch);

        start_radioGroupUpdate = (RadioGroup) findViewById(R.id.start_profileUpdateRadioGroup);
        end_radioGroupUpdate = (RadioGroup) findViewById(R.id.end_profileUpdateRadioGroup);

        activity_update_alarm_layout = (RelativeLayout) findViewById(R.id.activity_update_alarm_layout);

        daysEnabledTextview = (TextView) findViewById(R.id.daysEnabledTextview);
        end_profileTypeTextview = (TextView) findViewById(R.id.end_profileTypeTextview);
        start_profileTypeTextview = (TextView) findViewById(R.id.start_profileTypeTextview);


        getValuesFromDatabase(intent);


    }

    public void getValuesFromDatabase(Intent intent) {

        dataBaseManipulator = new DataBaseManipulator(this);
        updateCursor = dataBaseManipulator.fetchalarms();
        updateCursor.moveToPosition(intent.getIntExtra("position", -1));




        enable_update_switch.setChecked((updateCursor.getInt(1) == 0 ? false : true));


        profileNameUpdateEditText.setText(updateCursor.getString(6));

        startTimeUpdateButton.setText("From\n" + updateCursor.getString(2) + ":" + updateCursor.getString(3));
        currentHourTextStartUpdate = updateCursor.getString(2);
        currentMinuteTextStartUpdate = updateCursor.getString(3);
        endTimeUpdateButton.setText("Till\n" + updateCursor.getString(4) + ":" + updateCursor.getString(5));
        currentHourTextEndUpdate = updateCursor.getString(4);
        currentMinuteTextEndUpdate = updateCursor.getString(5);

        checkBoxSundayUpdate.setChecked((updateCursor.getInt(8) == 0 ? false : true));
        checkBoxMondayUpdate.setChecked((updateCursor.getInt(9) == 0 ? false : true));
        checkBoxTuesdayUpdate.setChecked((updateCursor.getInt(10) == 0 ? false : true));
        checkBoxWednesdayUpdate.setChecked((updateCursor.getInt(11) == 0 ? false : true));
        checkBoxThursdayUpdate.setChecked((updateCursor.getInt(12) == 0 ? false : true));
        checkBoxFridayUpdate.setChecked((updateCursor.getInt(13) == 0 ? false : true));
        checkBoxSaturdayUpdate.setChecked((updateCursor.getInt(14) == 0 ? false : true));

        if (updateCursor.getString(7).equals("Silent"))
            start_radioGroupUpdate.check(R.id.start_silentRadioUpdateButton);
        else if(updateCursor.getString(7).equals("Vibration"))
            start_radioGroupUpdate.check(R.id.start_vibrationRadioUpdateButton);
        else
            start_radioGroupUpdate.check(R.id.start_normalRadioUpdateButton);


        if (updateCursor.getString(15).equals("Silent"))
            end_radioGroupUpdate.check(R.id.end_silentRadioUpdateButton);
        else if(updateCursor.getString(15).equals("Vibration"))
            end_radioGroupUpdate.check(R.id.end_vibrationRadioUpdateButton);
        else
            end_radioGroupUpdate.check(R.id.end_normalRadioUpdateButton);


        id = updateCursor.getInt(0);
        repeat_update.putExtra("id", updateCursor.getInt(0));
        Log.d("Vishankkkkkkkkkkkkkkkkk", "Before" + updateCursor.getInt(0));


        updateCursor.close();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home :
                onBackPressed();
                break;

            case R.id.action_update:

                start_selectedRadioUpdateButton = (RadioButton) findViewById(start_radioGroupUpdate.getCheckedRadioButtonId());
                end_selectedRadioUpdateButton = (RadioButton) findViewById(end_radioGroupUpdate.getCheckedRadioButtonId());


                if (!profileNameUpdateEditText.getText().toString().isEmpty()) {
                    repeat_update.putExtra("profileName", profileNameUpdateEditText.getText().toString());
                    //check whether the user has provided Start Time and End Time
                    if (!(currentHourTextStartUpdate == null) && !(currentHourTextEndUpdate == null)) {
                        repeat_update.putExtra("startHour", currentHourTextStartUpdate.toString());
                        repeat_update.putExtra("startMinute", currentMinuteTextStartUpdate.toString());
                        repeat_update.putExtra("endHour", currentHourTextEndUpdate.toString());
                        repeat_update.putExtra("endMinute", currentMinuteTextEndUpdate.toString());

                        //check whether the user has selected atleast one Repeat day
                        if (checkBoxSundayUpdate.isChecked() || checkBoxMondayUpdate.isChecked() || checkBoxTuesdayUpdate.isChecked() || checkBoxWednesdayUpdate.isChecked() || checkBoxThursdayUpdate.isChecked() || checkBoxFridayUpdate.isChecked() || checkBoxSaturdayUpdate.isChecked()) {
                            repeat_update.putExtra("sun", checkBoxSundayUpdate.isChecked());
                            repeat_update.putExtra("mon", checkBoxMondayUpdate.isChecked());
                            repeat_update.putExtra("tue", checkBoxTuesdayUpdate.isChecked());
                            repeat_update.putExtra("wed", checkBoxWednesdayUpdate.isChecked());
                            repeat_update.putExtra("thur", checkBoxThursdayUpdate.isChecked());
                            repeat_update.putExtra("fri", checkBoxFridayUpdate.isChecked());
                            repeat_update.putExtra("sat", checkBoxSaturdayUpdate.isChecked());

                            //check whether the user has selected profile type or not.
                            if (start_selectedRadioUpdateButton != null)
                            {


                                if (start_selectedRadioUpdateButton.getText().equals("Silent"))
                                {
                                    repeat_update.putExtra("start_profileType", "Silent");
                                   // Toast.makeText(updateAlarmActivity.this, "Silent Selected", Toast.LENGTH_SHORT).show();
                                }
                                else if(start_selectedRadioUpdateButton.getText().equals("Vibration"))
                                {
                                    repeat_update.putExtra("start_profileType", "Vibration");
                                    //Toast.makeText(updateAlarmActivity.this, "Vibration Selected", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    repeat_update.putExtra("start_profileType" , "Normal");



                                if (end_selectedRadioUpdateButton != null) {

                                    if (end_selectedRadioUpdateButton.getText().equals("Silent")) {
                                        repeat_update.putExtra("end_profileType", "Silent");
                                        // Toast.makeText(updateAlarmActivity.this, "Silent Selected", Toast.LENGTH_SHORT).show();
                                    } else if (end_selectedRadioUpdateButton.getText().equals("Vibration")) {
                                        repeat_update.putExtra("end_profileType", "Vibration");
                                        //Toast.makeText(updateAlarmActivity.this, "Vibration Selected", Toast.LENGTH_SHORT).show();
                                    } else
                                        repeat_update.putExtra("end_profileType", "Normal");
                                }



                            }
                            else
                            {
                               // Toast.makeText(updateAlarmActivity.this, "Select  Profile Type", Toast.LENGTH_SHORT).show();
                            }
                            if (start_selectedRadioUpdateButton != null && end_selectedRadioUpdateButton != null)
                            {

                                if (enable_update_switch.isChecked())
                                    repeat_update.putExtra("status", 1);
                                else
                                    repeat_update.putExtra("status", 0);


                                //  repeat_update.putExtra("id", id);


                               // setResult(Activity.RESULT_OK, repeat_update);

                                dataBaseManipulator.alarmupdate(repeat_update, id);
                                dataBaseManipulator.close();

                                Intent serviceIntent = new Intent(updateAlarmActivity.this, MyAlarm.class);
                                serviceIntent.setAction("setAlarm");

                                startService(serviceIntent);
                                //finish();
                                //onBackPressed();

                                Intent intent = new Intent();
                                intent.putExtra("name" , profileNameUpdateEditText.getText().toString());
                                setResult(Activity.RESULT_OK ,intent);
                                finish();


                            }
                        } else
                            showSnackbar("Select Repeat Days.");
                            //Snackbar.make(activity_update_alarm_layout, "Select Repeat Days.", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(updateAlarmActivity.this, "Select Repeat Days", Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder = builder.setTitle("oops!!");
                        builder.setMessage("You Must Enter both a Start and End Time before saving this event");

                        builder.setNeutralButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                        Log.d("VISHANKKKKKKKKK", "onClickkkkkkk");
                                    }
                                });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder = builder.setTitle("oops!!");
                    builder.setMessage("You Must Enter a Profile Name before saving this event");

                    builder.setNeutralButton("Ok",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    Log.d("VISHANKKKKKKKKK", "onClickkkkkkk");
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                break;


            /*case R.id.action_cancel_update:
                finish();
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {

        if (current.equals("start")) {
            String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
            String minuteString = minute < 10 ? "0" + minute : "" + minute;


            currentHourTextStartUpdate = hourString.toString();
            currentMinuteTextStartUpdate = minuteString.toString();

            startTimeUpdateButton.setText("From\n"
                    + currentHourTextStartUpdate.toString() + ":"
                    + currentMinuteTextStartUpdate.toString());
        }

        if (current.equals("end")) {
            String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
            String minuteString = minute < 10 ? "0" + minute : "" + minute;


            currentHourTextEndUpdate = hourString.toString();
            currentMinuteTextEndUpdate = minuteString.toString();

            endTimeUpdateButton.setText("Till\n"
                    + currentHourTextEndUpdate + ":"
                    + currentMinuteTextEndUpdate);
        }

    }


    private void showSnackbar(String message)
    {
        Snackbar.make(activity_update_alarm_layout, message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.mdtp_accent_color ))
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Toast.makeText(updateAlarmActivity.this, "on Back called", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(R.anim.slide_in_right, 0);
    }
}
