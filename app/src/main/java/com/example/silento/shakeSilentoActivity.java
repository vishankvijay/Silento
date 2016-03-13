package com.example.silento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;
import com.rey.material.widget.Slider;
import com.rey.material.widget.Switch;

public class shakeSilentoActivity extends AppCompatActivity {
    Toolbar toolbar;

    Switch shakeEnablSwitch;
    RadioGroup shakeRadioGroup;
    RadioButton silentRadioButton;
    RadioButton vibrationRadioButton;
    //Slider sensivity_slider;
    HoloCircleSeekBar sensivity_slider;

    Boolean enableValue = false;
    int seekBarValue = 2;
    String shake_initial_profile_type = "Silent";

    RelativeLayout activity_shake_silento_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_silento);
        instantiate();
    }

    public void instantiate()
    {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shake Silento!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shakeEnablSwitch = (Switch) findViewById(R.id.shake_silento_switch);
        shakeRadioGroup = (RadioGroup)findViewById(R.id.shake_silento_radio_group);
        silentRadioButton = (RadioButton)findViewById(R.id.shake_silento_silent_radio_button);
        vibrationRadioButton = (RadioButton)findViewById(R.id.shake_silento_vibration_radio_button);
        sensivity_slider = (HoloCircleSeekBar) findViewById(R.id.sensivity_slider);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);

        enableValue = sharedPreferences.getBoolean("enableValue", false);
        seekBarValue = sharedPreferences.getInt("seekBarValue", 5);
        shake_initial_profile_type = sharedPreferences.getString("profileType", "Silent");

        if (shake_initial_profile_type.equals("Silent"))
            silentRadioButton.setChecked(true);
        else
            vibrationRadioButton.setChecked(true);

        shakeEnablSwitch.setChecked(enableValue);

        float seekBarValue_float = (float)seekBarValue;
        //sensivity_slider.setValue(seekBarValue_float ,false);
        sensivity_slider.setValue(seekBarValue);

        activity_shake_silento_layout = (RelativeLayout) findViewById(R.id.activity_shake_silento_layout);

        //Toast.makeText(shakeSilentoActivity.this, ""+ sensivity_slider.getValue(), Toast.LENGTH_SHORT).show();
    }


    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shake_silento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case android.R.id.home :
                onBackPressed();
                break;

            case R.id.action_save :
                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                if(shakeEnablSwitch.isChecked())
                {
                    //Toast.makeText(shakeSilentoActivity.this, "Working", Toast.LENGTH_SHORT).show();
                    RadioButton selected_Shake_RadioButton = (RadioButton)findViewById(shakeRadioGroup.getCheckedRadioButtonId());
                    String profile_type = null;
                    if (selected_Shake_RadioButton.getText().equals("Silent"))
                        profile_type = "Silent";
                    else
                        profile_type = "Vibration";



                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("enableValue", true);
                    editor.putString("profileType", profile_type);
                    editor.putInt("seekBarValue", sensivity_slider.getValue());
                    editor.commit();

                    Snackbar.make(activity_shake_silento_layout, "Shake Silento is ON !", Snackbar.LENGTH_SHORT).show();

                    Intent shakeServiceIntent2 = new Intent(shakeSilentoActivity.this, shakeService2.class);
                    shakeServiceIntent2.setAction("register");
                    startService(shakeServiceIntent2);

                    //Toast.makeText(shakeSilentoActivity.this, "Shake Silento is ON !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //SharedPreferences sharedPreferences = getSharedPreferences("MyData" , MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("enableValue", false);
                    editor.putInt("seekBarValue", sensivity_slider.getValue());
                    editor.apply();

                    Intent shakeServiceIntent2 = new Intent(shakeSilentoActivity.this, shakeService2.class);
                    shakeServiceIntent2.setAction("register");
                    // shakeServiceIntent.putExtra("seekBarValue", seek_bar_value);
                    stopService(shakeServiceIntent2);
                    Snackbar.make(activity_shake_silento_layout, "Shake Silento is OFF", Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(shakeSilentoActivity.this, "Shake Silento is OFF", Toast.LENGTH_SHORT).show();
                }
                onBackPressed();
        }

        return super.onOptionsItemSelected(menuItem);
    }

   /* @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        SharedPreferences sharedPreferences = getSharedPreferences("holdFirstActivityAnimation", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("holdFirstActivityAnimation", true);
        editor.commit();

        startActivity(new Intent(shakeSilentoActivity.this, FirstActivity.class));
        overridePendingTransition(R.anim.slide_in_right, 0);
    }*/

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right, 0);
    }
}
