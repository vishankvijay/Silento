package com.example.silento;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class AppIntroActivity extends AppIntro2 {

    private ViewPager mPager;

    @Override
    public void init(Bundle savedInstanceState)
    {
       /* addSlide(AppIntroFragment.newInstance("Title", "Discription", R.mipmap.ic_launcher, R.color.mdtp_accent_color));

        addSlide(AppIntroFragment.newInstance("Title 2", "Discription2", R.mipmap.fiftha, R.color.primaryColor));

        addSlide(AppIntroFragment.newInstance("Title 2", "Discription2", R.mipmap.fiftha, R.color.accentColor));

        addSlide(AppIntroFragment.newInstance("Title 2", "Discription2", R.mipmap.fiftha, R.color.orange_second));

        addSlide(AppIntroFragment.newInstance("Title 2", "Discription2", R.mipmap.fiftha, R.color.primaryColor));*/

        /*mPager = getPager();
        mPager.setOffscreenPageLimit(1);*/

        FirstFragment firstFragment  = new FirstFragment();
        SecondFragment secondFragment = new SecondFragment();
        ThirdFragment thirdFragment = new ThirdFragment();
        FragmentShake fragmentShake = new FragmentShake();
        FourthFragment fourthFragment = new FourthFragment();
        FifthFragment fifthFragment = new FifthFragment();



        addSlide(firstFragment);
        addSlide(secondFragment);
        addSlide(thirdFragment);
        addSlide(fragmentShake);
        addSlide(fourthFragment);
        addSlide(fifthFragment);


        // OPTIONAL METHODS

        // SHOW or HIDE the statusbar
        showStatusBar(false);

        // Edit the color of the nav bar on Lollipop+ devices
        //setNavBarColor(Color.parseColor("#3F51B5"))

        // Turn vibration on and set intensity
        // NOTE: you will need to ask VIBRATE permission in Manifest if you haven't already
        setVibrate(false);
        setVibrateIntensity(30);


        //setFadeAnimation(); // OR
        //setZoomAnimation(); // OR
        //setFlowAnimation(); // OR
        //setSlideOverAnimation(); // OR
        //setDepthAnimation();

    }

  /*  @Override
    public void onSkipPressed() {
        startActivity(new Intent(AppIntroActivity.this, FirstActivity.class));
        overridePendingTransition(R.anim.slide_in_left, 0);
        finish();

    }*/

    @Override
    public void onDonePressed() {

        startActivity(new Intent(AppIntroActivity.this, FirstActivity.class));
        overridePendingTransition(R.anim.slide_in_left, 0);
        finish();

    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }

}
