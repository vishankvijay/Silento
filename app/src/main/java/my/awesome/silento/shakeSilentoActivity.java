package my.awesome.silento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;
import com.rey.material.widget.Switch;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class shakeSilentoActivity extends AppCompatActivity implements Switch.OnCheckedChangeListener, HoloCircleSeekBar.OnCircleSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {
    Toolbar toolbar;

    //Switch shakeEnablSwitch;
    Switch shakeEnablSwitch;
    RadioGroup shakeRadioGroup;
    RadioButton silentRadioButton;
    RadioButton vibrationRadioButton;
    //Slider sensivity_slider;
    HoloCircleSeekBar sensivity_slider;

    Boolean enableValue = false;
    int seekBarValue = 2;
    String shake_initial_profile_type = "Silent";

    CoordinatorLayout activity_shake_silento_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_silento);
        instantiate();
    }

    public void instantiate() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shake Silento!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // shakeEnablSwitch = (Switch) findViewById(R.id.shake_silento_switch);
        shakeRadioGroup = (RadioGroup) findViewById(R.id.shake_silento_radio_group);
        silentRadioButton = (RadioButton) findViewById(R.id.shake_silento_silent_radio_button);
        vibrationRadioButton = (RadioButton) findViewById(R.id.shake_silento_vibration_radio_button);
        sensivity_slider = (HoloCircleSeekBar) findViewById(R.id.sensivity_slider);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);

        enableValue = sharedPreferences.getBoolean("enableValue", false);
        seekBarValue = sharedPreferences.getInt("seekBarValue", 5);
        shake_initial_profile_type = sharedPreferences.getString("profileType", "Silent");

        if (shake_initial_profile_type.equals("Silent"))
            silentRadioButton.setChecked(true);
        else
            vibrationRadioButton.setChecked(true);

       // shakeEnablSwitch.setChecked(enableValue);

        //float seekBarValue_float = (float) seekBarValue;
        //sensivity_slider.setValue(seekBarValue_float ,false);
        if(seekBarValue == 0 || seekBarValue ==1)
        sensivity_slider.setValue(2);
        else
        sensivity_slider.setValue(seekBarValue);
        sensivity_slider.setOnSeekBarChangeListener(this);

        activity_shake_silento_layout = (CoordinatorLayout) findViewById(R.id.activity_shake_silento_layout);

        shakeRadioGroup.setOnCheckedChangeListener(this);

        //Toast.makeText(shakeSilentoActivity.this, ""+ sensivity_slider.getValue(), Toast.LENGTH_SHORT).show();z
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shake_silento, menu);

        MenuItem menuItem = menu.findItem(R.id.action_shake_switch);
        View view = MenuItemCompat.getActionView(menuItem);
        shakeEnablSwitch = (Switch) view.findViewById(R.id.shake_custom_switch);
        shakeEnablSwitch.setOnCheckedChangeListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);

        enableValue = sharedPreferences.getBoolean("enableValue", false);
        shakeEnablSwitch.setChecked(enableValue);

        //showTutorial(shakeEnablSwitch);

       // Toast.makeText(shakeSilentoActivity.this, "Called", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

           /* case R.id.action_save:
                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                if (shakeEnablSwitch.isChecked()) {
                    //Toast.makeText(shakeSilentoActivity.this, "Working", Toast.LENGTH_SHORT).show();
                    RadioButton selected_Shake_RadioButton = (RadioButton) findViewById(shakeRadioGroup.getCheckedRadioButtonId());
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
                } else {
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
                onBackPressed();*/
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void showTutorial(Switch shakeEnablSwitch)
    {
        new MaterialShowcaseView.Builder(this)
                .setTarget(shakeEnablSwitch)
                .setDismissText("GOT IT")
                .setDismissTextColor(0xffD56E83)
                .setContentText("Use this to ENABLE or Disable your Event.")
                .setDelay(200) // optional but starting animations immediately in onCreate can make them choppy
                .singleUse("shake_1") // provide a unique ID used to ensure it is only shown once
                .show();
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right, 0);
    }

    @Override
    public void onCheckedChanged(Switch view, boolean checked)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        if (checked) {

            //Toast.makeText(shakeSilentoActivity.this, "Working", Toast.LENGTH_SHORT).show();
            RadioButton selected_Shake_RadioButton = (RadioButton) findViewById(shakeRadioGroup.getCheckedRadioButtonId());
            String profile_type = null;
            if (selected_Shake_RadioButton.getText().equals("Silent"))
                profile_type = "Silent";
            else
                profile_type = "Vibration";


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("enableValue", true);
            editor.putString("profileType", profile_type);
            editor.putInt("seekBarValue", sensivity_slider.getValue());
            editor.apply();

            showSnackbar("Shake Silento! is enabled");

            Intent shakeServiceIntent2 = new Intent(shakeSilentoActivity.this, shakeService2.class);
            shakeServiceIntent2.setAction("register");
            startService(shakeServiceIntent2);

            //Toast.makeText(shakeSilentoActivity.this, "Shake Silento is ON !", Toast.LENGTH_SHORT).show();


        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("enableValue", false);
            editor.putInt("seekBarValue", sensivity_slider.getValue());
            editor.apply();

            Intent shakeServiceIntent2 = new Intent(shakeSilentoActivity.this, shakeService2.class);
            shakeServiceIntent2.setAction("register");
            // shakeServiceIntent.putExtra("seekBarValue", seek_bar_value);
            stopService(shakeServiceIntent2);

            showSnackbar("Shake Silento! is disabled. To enable it click on the upper right switch.");


        }

    }

    @Override
    public void onProgressChanged(HoloCircleSeekBar holoCircleSeekBar, int i, boolean b)
    {
     //   Toast.makeText(shakeSilentoActivity.this, ""+ i, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

        //Toast.makeText(shakeSilentoActivity.this, "on start tracking" + sensivity_slider.getValue(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

        //Toast.makeText(shakeSilentoActivity.this, "on stop tracking"+ sensivity_slider.getValue(), Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("seekBarValue", sensivity_slider.getValue());
        editor.apply();

        new shakeService2(shakeSilentoActivity.this);

        /*Intent shakeServiceIntent2 = new Intent(shakeSilentoActivity.this, shakeService2.class);
        shakeServiceIntent2.setAction("register");
        startService(shakeServiceIntent2);*/


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);

        RadioButton selected_Shake_RadioButton = (RadioButton) findViewById(checkedId);
        String profile_type = null;
        if (selected_Shake_RadioButton.getText().equals("Silent"))
            profile_type = "Silent";
        else
            profile_type = "Vibration";

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profileType", profile_type);
        editor.apply();

        new shakeService2(shakeSilentoActivity.this);

        /*Intent shakeServiceIntent2 = new Intent(shakeSilentoActivity.this, shakeService2.class);
        shakeServiceIntent2.setAction("register");
        startService(shakeServiceIntent2);*/

    }

    private void showSnackbar(String message)
    {
        /*Snackbar.make(activity_shake_silento_layout, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.mdtp_accent_color ))
                .show();*/

        Snackbar.make(activity_shake_silento_layout , message , Snackbar.LENGTH_LONG).show();
    }
}
