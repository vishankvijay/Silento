package com.example.silento;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rey.material.widget.Switch;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, Switch.OnCheckedChangeListener {

    Toolbar toolbar;

    ListView listview;
    Context context;

    Switch enable_update_switch;

    ArrayList prgmName;
    public static int[] images_settings = {R.mipmap.help , R.mipmap.feedback, R.mipmap.ic_action_upcoming_features, R.mipmap.ic_action_about};
    public static String[] names_settings = {"Help", "Feedback", "Upcoming features", "About"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        instantiate();

        context = this;


        listview.setAdapter(new customSettingsAdapter(this, names_settings, images_settings));

        enable_update_switch.setOnCheckedChangeListener(this);

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(SettingsActivity.this, "Working", Toast.LENGTH_SHORT).show();
//            }
//        });

        listview.setOnItemClickListener(this);
    }

    private void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listview = (ListView) findViewById(R.id.listView);

        SharedPreferences sharedPreferences_quick_1 = getSharedPreferences("QuickData", Context.MODE_PRIVATE);
        Boolean get_notification_state = sharedPreferences_quick_1.getBoolean("get_notification_state" , false);

        enable_update_switch = (Switch) findViewById(R.id.enable_update_switch);
        enable_update_switch.setChecked(get_notification_state);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position)
        {
            case 0:
                Toast.makeText(SettingsActivity.this, "Working", Toast.LENGTH_SHORT).show();
                break;

            case 1:
                startActivity(new Intent(SettingsActivity.this, feedBackActivity.class));
                overridePendingTransition(R.anim.slide_in_left, 0);
                break;

            case 2:
                startActivity(new Intent(SettingsActivity.this, upcomingFeaturesActivity.class));
                overridePendingTransition(R.anim.slide_in_left, 0);
                break;

            case 3:
                startActivity(new Intent(SettingsActivity.this, aboutActivity.class));
                overridePendingTransition(R.anim.slide_in_left, 0);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right, 0);
    }

    @Override
    public void onCheckedChanged(Switch view, boolean checked)
    {
        SharedPreferences sharedPreferences_quick_2 = getSharedPreferences("QuickData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences_quick_2.edit();
        editor.putBoolean("get_notification_state" , checked);
        editor.apply();
    }
}
