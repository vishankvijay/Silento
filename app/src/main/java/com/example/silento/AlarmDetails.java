package com.example.silento;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class AlarmDetails extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, CompoundButton.OnCheckedChangeListener {

    private Toolbar toolbar;

    Button startTimeButton;
    Button endTimeButton;
    String current;
    EditText profileNameEditText;
    public String currentHourTextStart = null;
    public String currentMinuteTextStart = null;
    public String currentHourTextEnd = null;
    public String currentMinuteTextEnd = null;
    com.rey.material.widget.CheckBox checkBoxSunday;
    com.rey.material.widget.CheckBox checkBoxMonday;
    com.rey.material.widget.CheckBox checkBoxTuesday;
    com.rey.material.widget.CheckBox checkBoxWednesday;
    com.rey.material.widget.CheckBox checkBoxThursday;
    com.rey.material.widget.CheckBox checkBoxFriday;
    com.rey.material.widget.CheckBox checkBoxSaturday;
    RadioGroup start_radioGroup;
    RadioGroup end_radioGroup;
    RadioButton start_selectedRadioButton;
    RadioButton end_selectedRadioButton;

    CardView carview_with_edittext;
    CardView carview_with_buttons;
    CardView carview_with_profile_types;

    RelativeLayout activity_alarm_details_layout;



    TextView daysEnabledTextview;
    TextView start_profileTypeTextview;
    TextView end_profileTypeTextview;

    DataBaseManipulator dataBaseManipulator;

    LinearLayout layout_containing_buttons;
    LinearLayout layout_containing_chechkbox;
    LinearLayout layout_containing_profile_types;
    LinearLayout test ;
    int count_layout_containing_buttons = 0;
    int count_layout_containing_chechkbox_start = 0;
    int count_layout_containing_chechkbox_end = 0;
    int count_layout_containing_profile_types = 0;

    String SHOWCASE_ID = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_details);

        instantiate();
        showTutorial();


       /* profileNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count_layout_containing_buttons == 0)
                {
                    TranslateAnimation animate = new TranslateAnimation(carview_with_buttons.getWidth(), 0, 0, 0);
                    animate.setDuration(400);
                    animate.setFillAfter(true);
                    carview_with_buttons.startAnimation(animate);
                    carview_with_buttons.setVisibility(View.VISIBLE);
                    count_layout_containing_buttons++;
                }

            }
        });
*/
        profileNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("") && count_layout_containing_buttons == 0) {
                    TranslateAnimation animate = new TranslateAnimation(0, 0, 3 * carview_with_buttons.getHeight(), 0);
                    animate.setDuration(400);
                    animate.setFillAfter(false);
                    carview_with_buttons.startAnimation(animate);
                    carview_with_buttons.setVisibility(View.VISIBLE);
                    count_layout_containing_buttons++;
                }

            }
        });

        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AlarmDetails.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );

                current = "start";
                //tpd.setThemeDark(true);
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



        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now2 = Calendar.getInstance();
                TimePickerDialog tpd2 = TimePickerDialog.newInstance(
                        AlarmDetails.this,
                        now2.get(Calendar.HOUR_OF_DAY),
                        now2.get(Calendar.MINUTE),
                        false
                );

                current = "end";
                //tpd.setThemeDark(true);
                tpd2.vibrate(false);
                tpd2.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd2.show(getFragmentManager(), "Timepickerdialog");



                /*TranslateAnimation animate = new TranslateAnimation(daysEnabledTextview.getWidth(),0,0,0);
                animate.setDuration(500);
                animate.setFillAfter(true);
                daysEnabledTextview.startAnimation(animate);
                daysEnabledTextview.setVisibility(View.VISIBLE);*/
                /*daysEnabledTextview.setVisibility(View.VISIBLE);
                daysEnabledTextview.setAlpha(0.0f);

// Start the animation
                daysEnabledTextview.animate()
                        .translationY(daysEnabledTextview.getHeight())
                        .alpha(1.0f);*/

            }
        });
        /*checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AlarmDetails.this, "Sunday - " + checkBoxSunday.isChecked() + "\n" + "Monday - " + checkBoxMonday.isChecked() + "\n" + "Tuesday - " + checkBoxTuesday.isChecked() + "\n" + "Wednesday - " + checkBoxWednesday.isChecked() + "\n" + "Thursday - " + checkBoxThursday.isChecked() + "\n" + "Friday - " + checkBoxFriday.isChecked() + "\n" + "Saturday - " + checkBoxSaturday.isChecked() + "\n" , Toast.LENGTH_LONG).show();
            }
        });*/

        /*test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateAnimation animate = new TranslateAnimation(layout_containing_profile_types.getWidth(), 0, 0, 0);
                animate.setDuration(500);
                animate.setFillAfter(true);
                layout_containing_profile_types.startAnimation(animate);
                layout_containing_profile_types.setVisibility(View.VISIBLE);
            }
        });*/

    }

    /*private void showTutorial()
    {
        carview_with_buttons.setVisibility(View.VISIBLE);
        carview_with_profile_types.setVisibility(View.VISIBLE);
        Toast.makeText(AlarmDetails.this, ""+ SHOWCASE_ID, Toast.LENGTH_SHORT).show();

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setConfig(config);

        sequence.addSequenceItem(profileNameEditText,
                "This is button one", "GOT IT");

        sequence.addSequenceItem(startTimeButton,
                "This is button two", "GOT IT");

        sequence.addSequenceItem(endTimeButton,
                "This is button three", "GOT IT");
        sequence.setOnItemDismissedListener(new MaterialShowcaseSequence.OnSequenceItemDismissedListener() {
            @Override
            public void onDismiss(MaterialShowcaseView materialShowcaseView, int i) {
                carview_with_buttons.setVisibility(View.INVISIBLE);
                carview_with_profile_types.setVisibility(View.INVISIBLE);
            }
        });

        sequence.start();

    }*/


    private void showTutorial()
    {
       /* new MaterialIntroView.Builder(this)
                .enableDotAnimation(true)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.ALL)
                .setDelayMillis(200)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("Hi There! Click this card and see what happens.")
                .setTarget(startTimeButton)
                .setListener(new MaterialIntroListener() {
                    @Override
                    public void onUserClicked(String s) {


                        new MaterialIntroView.Builder(AlarmDetails.this)
                                .enableDotAnimation(true)
                                .setFocusGravity(FocusGravity.CENTER)
                                .setFocusType(Focus.ALL)
                                .setDelayMillis(200)
                                .enableFadeAnimation(true)
                                .performClick(false)
                                .setInfoText("Hi There! Click this card and see what happens.")
                                .setTarget(endTimeButton)
                                .setListener(new MaterialIntroListener() {
                                    @Override
                                    public void onUserClicked(String s) {
                                        new MaterialIntroView.Builder(AlarmDetails.this)
                                                .enableDotAnimation(true)
                                                .setFocusGravity(FocusGravity.CENTER)
                                                .setFocusType(Focus.ALL)
                                                .setDelayMillis(200)
                                                .enableFadeAnimation(true)
                                                .performClick(false)
                                                .setInfoText("Hi There! Click this card and see what happens.")
                                                .setTarget(daysEnabledTextview)
                                                .setListener(new MaterialIntroListener() {
                                                    @Override
                                                    public void onUserClicked(String s) {
                                                        new MaterialIntroView.Builder(AlarmDetails.this)
                                                                .enableDotAnimation(true)
                                                                .setFocusGravity(FocusGravity.CENTER)
                                                                .setFocusType(Focus.ALL)
                                                                .setDelayMillis(200)
                                                                .enableFadeAnimation(true)
                                                                .performClick(false)
                                                                .setInfoText("Hi There! Click this card and see what happens.")
                                                                .setTarget(start_profileTypeTextview)
                                                                .setListener(new MaterialIntroListener() {
                                                                    @Override
                                                                    public void onUserClicked(String s) {
                                                                        new MaterialIntroView.Builder(AlarmDetails.this)
                                                                                .enableDotAnimation(true)
                                                                                .setFocusGravity(FocusGravity.CENTER)
                                                                                .setFocusType(Focus.ALL)
                                                                                .setDelayMillis(200)
                                                                                .enableFadeAnimation(true)
                                                                                .performClick(false)
                                                                                .setInfoText("Hi There! Click this card and see what happens.")
                                                                                .setTarget(end_profileTypeTextview)
                                                                                .setUsageId("5") //THIS SHOULD BE UNIQUE ID
                                                                                .show();
                                                                    }
                                                                })
                                                                .setUsageId("4") //THIS SHOULD BE UNIQUE ID
                                                                .show();
                                                    }
                                                })
                                                .setUsageId("3") //THIS SHOULD BE UNIQUE ID
                                                .show();
                                    }
                                })
                                .setUsageId("2") //THIS SHOULD BE UNIQUE ID
                                .show();
                    }
                })
                .setUsageId("1") //THIS SHOULD BE UNIQUE ID
                .show();
*/

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);

        Boolean show = sharedPreferences.getBoolean("show_alarm_tutorial", true);


        if(show) {

            carview_with_buttons.setVisibility(View.VISIBLE);
            carview_with_profile_types.setVisibility(View.VISIBLE);

            new MaterialShowcaseView.Builder(this)
                    .setTarget(startTimeButton)
                    .setDismissText("OK")
                    .setContentText("Use this to set the START TIME of your event.")
                    .setDelay(100) // optional but starting animations immediately in onCreate can make them choppy
                    .singleUse("1")// provide a unique ID used to ensure it is only shown once
                    .setListener(new IShowcaseListener() {
                        @Override
                        public void onShowcaseDisplayed(MaterialShowcaseView materialShowcaseView) {
                            SharedPreferences sharedPreferences = getSharedPreferences("MyData" , MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("show_alarm_tutorial", false);
                            editor.apply();

                        }

                        @Override
                        public void onShowcaseDismissed(MaterialShowcaseView materialShowcaseView) {

                            new MaterialShowcaseView.Builder(AlarmDetails.this)
                                    .setTarget(endTimeButton)
                                    .setDismissText("OK")
                                    .setContentText("Use this to set the END TIME of your event.")
                                            //.setDelay(0) // optional but starting animations immediately in onCreate can make them choppy
                                    .singleUse("2") // provide a unique ID used to ensure it is only shown once
                                    .setListener(new IShowcaseListener() {
                                        @Override
                                        public void onShowcaseDisplayed(MaterialShowcaseView materialShowcaseView) {

                                        }

                                        @Override
                                        public void onShowcaseDismissed(MaterialShowcaseView materialShowcaseView) {


                                            new MaterialShowcaseView.Builder(AlarmDetails.this)
                                                    .setTarget(daysEnabledTextview)
                                                    .setDismissText("OK")
                                                    .setContentText("Use this to set the Enabled Days of your event.")
                                                            //.setDelay(0) // optional but starting animations immediately in onCreate can make them choppy
                                                    .singleUse("3") // provide a unique ID used to ensure it is only shown once
                                                    .setListener(new IShowcaseListener() {
                                                        @Override
                                                        public void onShowcaseDisplayed(MaterialShowcaseView materialShowcaseView) {

                                                        }

                                                        @Override
                                                        public void onShowcaseDismissed(MaterialShowcaseView materialShowcaseView)
                                                        {
                                                            new MaterialShowcaseView.Builder(AlarmDetails.this)
                                                                    .setTarget(start_profileTypeTextview)
                                                                    .setDismissText("OK")
                                                                    .setContentText("Use this to set the PROFILE you want on the START of your EVENT")
                                                                            //.setDelay(0) // optional but starting animations immediately in onCreate can make them choppy
                                                                    .singleUse("4") // provide a unique ID used to ensure it is only shown once
                                                                    .setListener(new IShowcaseListener() {
                                                                        @Override
                                                                        public void onShowcaseDisplayed(MaterialShowcaseView materialShowcaseView) {

                                                                        }

                                                                        @Override
                                                                        public void onShowcaseDismissed(MaterialShowcaseView materialShowcaseView) {
                                                                            new MaterialShowcaseView.Builder(AlarmDetails.this)
                                                                                    .setTarget(end_profileTypeTextview)
                                                                                    .setDismissText("Cool! Lets Start")
                                                                                    .setContentText("Use this to set the PROFILE you want on the END of your EVENT")
                                                                                            //.setDelay(0) // optional but starting animations immediately in onCreate can make them choppy
                                                                                    .singleUse("5") // provide a unique ID used to ensure it is only shown once
                                                                                    .setListener(new IShowcaseListener() {
                                                                                        @Override
                                                                                        public void onShowcaseDisplayed(MaterialShowcaseView materialShowcaseView) {

                                                                                        }

                                                                                        @Override
                                                                                        public void onShowcaseDismissed(MaterialShowcaseView materialShowcaseView) {
                                                                                            make_view_invisible();
                                                                                        }
                                                                                    })
                                                                                    .show();
                                                                        }
                                                                    })
                                                                    .show();
                                                        }
                                                    })
                                                    .show();

                                        }
                                    })
                                    .show();

                        }
                    })
                    .show();
        }

    }

    private void make_view_invisible()
    {

        carview_with_buttons.setVisibility(View.INVISIBLE);
        carview_with_profile_types.setVisibility(View.INVISIBLE);



    }

    public void instantiate() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Event Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileNameEditText = (EditText) findViewById(R.id.profileNameEditText);

        startTimeButton = (Button) findViewById(R.id.startTimeButton);
        endTimeButton = (Button) findViewById(R.id.endTimeButton);

        checkBoxSunday = (com.rey.material.widget.CheckBox) findViewById(R.id.sundayCheckBox);
        checkBoxSunday.setOnCheckedChangeListener(this);
        checkBoxMonday = (com.rey.material.widget.CheckBox) findViewById(R.id.MondayCheckBox);
        checkBoxMonday.setOnCheckedChangeListener(this);
        checkBoxTuesday = (com.rey.material.widget.CheckBox) findViewById(R.id.TuesdayCheckBox);
        checkBoxTuesday.setOnCheckedChangeListener(this);
        checkBoxWednesday = (com.rey.material.widget.CheckBox) findViewById(R.id.wednesdayCheckBox);
        checkBoxWednesday.setOnCheckedChangeListener(this);
        checkBoxThursday = (com.rey.material.widget.CheckBox) findViewById(R.id.thursdayCheckBox);
        checkBoxThursday.setOnCheckedChangeListener(this);
        checkBoxFriday = (com.rey.material.widget.CheckBox) findViewById(R.id.fridayCheckBox);
        checkBoxFriday.setOnCheckedChangeListener(this);
        checkBoxSaturday = (com.rey.material.widget.CheckBox) findViewById(R.id.saturdayCheckBox);
        checkBoxSaturday.setOnCheckedChangeListener(this);

        start_radioGroup = (RadioGroup) findViewById(R.id.start_profileRadioGroup);
        end_radioGroup = (RadioGroup) findViewById(R.id.end_profileRadioGroup);
        //layout_containing_buttons = (LinearLayout) findViewById(R.id.layout_containing_buttons);
        layout_containing_chechkbox = (LinearLayout) findViewById(R.id.layout_containing_chechkbox);
        layout_containing_profile_types = (LinearLayout) findViewById(R.id.layout_containing_profile_types);
        test = (LinearLayout) findViewById(R.id.test);

        carview_with_edittext  = (CardView) findViewById(R.id.carview_with_edittext);
        carview_with_buttons  = (CardView) findViewById(R.id.carview_with_buttons);
        carview_with_profile_types  = (CardView) findViewById(R.id.carview_with_profile_types);

        activity_alarm_details_layout = (RelativeLayout) findViewById(R.id.activity_alarm_details_layout);

        daysEnabledTextview = (TextView) findViewById(R.id.daysEnabledTextview);
        start_profileTypeTextview = (TextView) findViewById(R.id.start_profileTypeTextview);
        end_profileTypeTextview = (TextView) findViewById(R.id.end_profileTypeTextview);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alarm_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case android.R.id.home :
                onBackPressed();
                break;

            case R.id.action_save:

                Intent repeat = new Intent();

                start_selectedRadioButton = (RadioButton) findViewById(start_radioGroup.getCheckedRadioButtonId());
                end_selectedRadioButton = (RadioButton) findViewById(end_radioGroup.getCheckedRadioButtonId());

                //check whether the user has provided the profile name

                if (!profileNameEditText.getText().toString().isEmpty()) {
                    repeat.putExtra("profileName", profileNameEditText.getText().toString());
                    //check whether the user has provided Start Time and End Time
                    if (!(currentHourTextStart == null) && !(currentHourTextEnd == null)) {
                        repeat.putExtra("startHour", currentHourTextStart.toString());
                        repeat.putExtra("startMinute", currentMinuteTextStart.toString());
                        repeat.putExtra("endHour", currentHourTextEnd.toString());
                        repeat.putExtra("endMinute", currentMinuteTextEnd.toString());

                        //check whether the user has selected atleast one Repeat day
                        if (checkBoxSunday.isChecked() || checkBoxMonday.isChecked() || checkBoxTuesday.isChecked() || checkBoxWednesday.isChecked() || checkBoxThursday.isChecked() || checkBoxFriday.isChecked() || checkBoxSaturday.isChecked()) {
                            repeat.putExtra("sun", checkBoxSunday.isChecked());
                            repeat.putExtra("mon", checkBoxMonday.isChecked());
                            repeat.putExtra("tue", checkBoxTuesday.isChecked());
                            repeat.putExtra("wed", checkBoxWednesday.isChecked());
                            repeat.putExtra("thur", checkBoxThursday.isChecked());
                            repeat.putExtra("fri", checkBoxFriday.isChecked());
                            repeat.putExtra("sat", checkBoxSaturday.isChecked());

                            //check whether the user has selected profile type or not.
                            if (start_selectedRadioButton != null) {
                                if (start_selectedRadioButton.getText().equals("Silent")) {
                                    repeat.putExtra("start_profileType", "Silent");
                                    //Toast.makeText(AlarmDetails.this, "Silent Selected", Toast.LENGTH_SHORT).show();
                                } else if (start_selectedRadioButton.getText().equals("Vibration")) {
                                    repeat.putExtra("start_profileType", "Vibration");
                                    // Toast.makeText(AlarmDetails.this, "Vibration Selected", Toast.LENGTH_SHORT).show();
                                } else {
                                    repeat.putExtra("start_profileType", "Normal");
                                    //Toast.makeText(AlarmDetails.this, "Normal Selected", Toast.LENGTH_SHORT).show();
                                }

                                if (end_selectedRadioButton != null) {

                                    if (end_selectedRadioButton.getText().equals("Silent")) {
                                        repeat.putExtra("end_profileType", "Silent");
                                        // Toast.makeText(AlarmDetails.this, " End Silent Selected", Toast.LENGTH_SHORT).show();
                                    } else if (end_selectedRadioButton.getText().equals("Vibration")) {
                                        repeat.putExtra("end_profileType", "Vibration");
                                        // Toast.makeText(AlarmDetails.this, "End Vibration Selected", Toast.LENGTH_SHORT).show();
                                    } else {
                                        repeat.putExtra("end_profileType", "Normal");
                                        // Toast.makeText(AlarmDetails.this, "End Normal Selected", Toast.LENGTH_SHORT).show();
                                    }

                                } else
                                {
                                    // if (toastCount == 1)
                                    // Snackbar.make(activity_alarm_details_layout, "Select End Profile Type.", Snackbar.LENGTH_SHORT).show();
                                    showSnackbar("Select End Profile Type.");

                                    //Toast.makeText(AlarmDetails.this, "Select End  Profile Type", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                // if (toastCount == 1)

                                showSnackbar("Select Start Profile Type.");
                                // Snackbar.make(activity_alarm_details_layout, "Select Start Profile Type.", Snackbar.LENGTH_SHORT).show();

                                //Toast.makeText(AlarmDetails.this, "Select Start  Profile Type", Toast.LENGTH_SHORT).show();
                            }
                            // toastCount = 1;
                            if (start_selectedRadioButton != null && end_selectedRadioButton != null)
                            {

                                repeat.putExtra("status", 1);

                                //setResult(Activity.RESULT_OK, repeat);
                                dataBaseManipulator = new DataBaseManipulator(this);
                                dataBaseManipulator.alarmsave(repeat);
                                dataBaseManipulator.close();

                                Intent serviceIntent = new Intent(AlarmDetails.this, MyAlarm.class);
                                serviceIntent.setAction("setAlarm");

                                startService(serviceIntent);
                                onBackPressed();


                            }
                        } else
                            showSnackbar("Select Repeat Days.");
                        // Snackbar.make(activity_alarm_details_layout, "Select Repeat Days.", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(AlarmDetails.this, "Select Repeat Days", Toast.LENGTH_LONG).show();
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


        }

        return super.onOptionsItemSelected(item);
    }

    private void showSnackbar(String message)
    {
        Snackbar.make(activity_alarm_details_layout, message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.mdtp_accent_color ))
                .show();
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {

        if (current.equals("start")) {
            String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
            String minuteString = minute < 10 ? "0" + minute : "" + minute;


            currentHourTextStart = hourString;
            currentMinuteTextStart = minuteString;

            startTimeButton.setText("From\n"
                    + currentHourTextStart + ":"
                    + currentMinuteTextStart);

            if(count_layout_containing_chechkbox_start == 0 && count_layout_containing_profile_types > 0 && count_layout_containing_chechkbox_end > 0)
            {
                show_cardviw_with_profiles();
            }

            ++count_layout_containing_chechkbox_start;
        }

        if (current.equals("end")) {
            String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
            String minuteString = minute < 10 ? "0" + minute : "" + minute;

            currentHourTextEnd = hourString;
            currentMinuteTextEnd = minuteString;

            endTimeButton.setText("Till\n"
                    + currentHourTextEnd + ":"
                    + currentMinuteTextEnd);

            if(count_layout_containing_chechkbox_end == 0 && count_layout_containing_profile_types > 0 && count_layout_containing_chechkbox_start > 0)
            {
                show_cardviw_with_profiles();
            }

            ++count_layout_containing_chechkbox_end;

           /* if(count_layout_containing_chechkbox == 0) {
                TranslateAnimation animate = new TranslateAnimation(-layout_containing_chechkbox.getWidth(), 0, 0, 0);
                animate.setDuration(400);
                animate.setFillAfter(true);
                layout_containing_chechkbox.startAnimation(animate);
                layout_containing_chechkbox.setVisibility(View.VISIBLE);
                count_layout_containing_chechkbox++;
            }*/
        }


    }

    private void show_cardviw_with_profiles()
    {
        //TranslateAnimation animate = new TranslateAnimation(-carview_with_profile_types.getWidth(), 0, 0, 0);

        TranslateAnimation animate = new TranslateAnimation(0, 0, carview_with_profile_types.getWidth(), 0);
        animate.setDuration(400);
        animate.setFillAfter(false);
        carview_with_profile_types.startAnimation(animate);
        carview_with_profile_types.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if(count_layout_containing_profile_types == 0 && count_layout_containing_chechkbox_start >= 1 && count_layout_containing_chechkbox_end >= 1)
        {
            show_cardviw_with_profiles();
            //count_layout_containing_profile_types++;
        }

        count_layout_containing_profile_types++;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // startActivity(new Intent(AlarmDetails.this, AlarmList.class));
        finish();
        overridePendingTransition(R.anim.slide_in_right, 0);

    }
}
