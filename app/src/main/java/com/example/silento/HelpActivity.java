package com.example.silento;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class HelpActivity extends AppCompatActivity {
    // inside Activity
    TextView event_des;
    TextView quick_silento_des;
    TextView shake_silento_des;
    TextView exceptions_des;
    TextView settings_des;
    TextView about_silento_des;

    Toolbar toolbar;

    LinearLayout events;
    LinearLayout quick_silento;
    LinearLayout shake_silento;
    LinearLayout exceptions;
    LinearLayout settings;
    LinearLayout about_silento;

    TextView event_title;
    TextView quick_title;
    TextView shake_title;
    TextView exceptions_title;
    TextView settings_title;
    TextView about_silento_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initialize();
        showTutorial();
    }

    private void showTutorial()
    {
        new MaterialShowcaseView.Builder(this)
                .setTarget(event_title)
                .setDismissText("OK")
                .setContentText("Click on any of the items to know more about them.")
                .setDelay(200) // optional but starting animations immediately in onCreate can make them choppy
                .singleUse("help_1") // provide a unique ID used to ensure it is only shown once
                .show();
    }

    private void initialize() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Help");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        event_des = (TextView) findViewById(R.id.event_des);
        event_des.setVisibility(View.GONE);

        quick_silento_des = (TextView) findViewById(R.id.quick_silento_des);
        quick_silento_des.setVisibility(View.GONE);

        shake_silento_des = (TextView) findViewById(R.id.shake_silento_des);
        shake_silento_des.setVisibility(View.GONE);

        exceptions_des = (TextView) findViewById(R.id.exceptions_des);
        exceptions_des.setVisibility(View.GONE);

        settings_des = (TextView) findViewById(R.id.settings_des);
        settings_des.setVisibility(View.GONE);

        about_silento_des = (TextView) findViewById(R.id.about_silento_des);
        about_silento_des.setVisibility(View.GONE);


        events = (LinearLayout) findViewById(R.id.event);
        quick_silento = (LinearLayout) findViewById(R.id.quick_silento);
        shake_silento = (LinearLayout) findViewById(R.id.shake_silento);
        exceptions = (LinearLayout) findViewById(R.id.exceptions);
        settings = (LinearLayout) findViewById(R.id.settings);
        about_silento = (LinearLayout) findViewById(R.id.about_silento);

        event_title = (TextView) findViewById(R.id.event_title);
        quick_title = (TextView) findViewById(R.id.quick_title);
        shake_title = (TextView) findViewById(R.id.shake_title);
        exceptions_title = (TextView) findViewById(R.id.exceptions_title);
        settings_title = (TextView) findViewById(R.id.settings_title);
        about_silento_title = (TextView) findViewById(R.id.about_silento_title);


    }

    /**
     * onClick handler
     */
    /**
     * onClick handler
     */
    public void toggle_contents(View v) {

        TextView temp_TextView = null;
        TextView temp_title = null;

        switch (v.getId()) {
            case R.id.about_silento:
                temp_TextView = about_silento_des;
                temp_title = about_silento_title;
                break;

            case R.id.event:
                temp_TextView = event_des;
                temp_title = event_title;
                break;

            case R.id.quick_silento:
                temp_TextView = quick_silento_des;
                temp_title = quick_title;
                break;

            case R.id.shake_silento:
                temp_TextView = shake_silento_des;
                temp_title = shake_title;
                break;

            case R.id.exceptions:
                temp_TextView = exceptions_des;
                temp_title = exceptions_title;
                break;

            case R.id.settings:
                temp_TextView = settings_des;
                temp_title = settings_title;
                break;

        }

        if (temp_TextView.isShown()) {
            slide_up(this, temp_TextView);
            temp_TextView.setVisibility(View.GONE);
            temp_title.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.down, 0, 0, 0);
            // Toast.makeText(HelpActivity.this, " Shown Called", Toast.LENGTH_SHORT).show();
        } else {
            temp_TextView.setVisibility(View.VISIBLE);
            temp_title.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.up, 0, 0, 0);
            slide_down(this, temp_TextView);
        }
    }


    public static void slide_down(Context ctx, View v) {

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
                a.setFillAfter(false);
            }
        }
    }


    public static void slide_up(Context ctx, View v) {

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);

            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == android.R.id.home)
            onBackPressed();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // startActivity(new Intent(AlarmDetails.this, AlarmList.class));
        finish();
        overridePendingTransition(R.anim.slide_in_right, 0);

    }
}

