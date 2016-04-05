package com.example.silento;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;


public class aboutActivity extends AppCompatActivity {

    HoloCircleSeekBar holoCircleSeekBar;

    Toolbar toolbar;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About The App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        logo = (ImageView) findViewById(R.id.logo);

        final Animation animRotate = AnimationUtils.loadAnimation(this , R.anim.scale);
        logo.startAnimation(animRotate);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
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
