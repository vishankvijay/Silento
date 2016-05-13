package my.awesome.silento;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FirstActivity extends Activity {

    LinearLayout firstLayout;
    LinearLayout secondLayout;
    LinearLayout thirdLayout;
    LinearLayout fourthLayout;
    LinearLayout fifthLayout;
    LinearLayout parentLayout;
    LinearLayout first_layout_content;
    LinearLayout second_layout_content;
    LinearLayout third_layout_content;
    LinearLayout fourth_layout_content;
    LinearLayout fifth_layout_content;
    ImageView img_first;
    ImageView img_second;
    ImageView img_third;
    ImageView img_fourth;
    ImageView img_fifth;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);



        checkAppIntro();


        instantiate();


        firstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = null;
                animation = AnimationUtils.loadAnimation(FirstActivity.this, R.anim.shake);
                animation.setDuration(300);
                first_layout_content.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(FirstActivity.this, AlarmList.class));
                        overridePendingTransition(R.anim.slide_in_left, 0);
                        //finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });



               /* TranslateAnimation animate = new TranslateAnimation(0, 2*firstLayout.getWidth(), 0, 0);
                animate.setDuration(450);
                animate.setFillAfter(true);


                TranslateAnimation animate2 = new TranslateAnimation(0, -firstLayout.getWidth(), 0, 0);
                animate2.setDuration(450);
                animate2.setFillAfter(true);
                firstLayout.startAnimation(animate);
                secondLayout.startAnimation(animate2);
                thirdLayout.startAnimation(animate2);
                fourthLayout.startAnimation(animate2);
                fifthLayout.startAnimation(animate2);

                animate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        startActivity(new Intent(FirstActivity.this, AlarmList.class));
                        //Toast.makeText(FirstActivity.this, "Called", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/


                //animation = null;

                //holdFirstActivityAnimation(false);

            }
        });

        secondLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Animation animation = null;
                animation = AnimationUtils.loadAnimation(FirstActivity.this, R.anim.shake);
                animation.setDuration(300);
                second_layout_content.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(FirstActivity.this, QuickSilentoActivity.class));
                        overridePendingTransition(R.anim.slide_in_left, 0);
                        //finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                animation = null;

                // holdFirstActivityAnimation(false);

            }
        });

        thirdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Animation animation = null;
                animation = AnimationUtils.loadAnimation(FirstActivity.this, R.anim.shake);
                animation.setDuration(300);
                third_layout_content.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(FirstActivity.this, shakeSilentoActivity.class));
                        overridePendingTransition(R.anim.slide_in_left, 0);
                        //finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                animation = null;

                //holdFirstActivityAnimation(false);

            }
        });


        fourthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = null;
                animation = AnimationUtils.loadAnimation(FirstActivity.this, R.anim.shake);
                animation.setDuration(300);
                fourth_layout_content.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(FirstActivity.this, ExceptionActiviy.class));
                        overridePendingTransition(R.anim.slide_in_left, 0);
                        //finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                animation = null;

                //holdFirstActivityAnimation(false);

            }
        });


        fifthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = null;
                animation = AnimationUtils.loadAnimation(FirstActivity.this, R.anim.shake);
                animation.setDuration(300);
                fifth_layout_content.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(FirstActivity.this, SettingsActivity.class));
                        overridePendingTransition(R.anim.slide_in_left, 0);
                        // finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                animation = null;

                //holdFirstActivityAnimation(false);

            }
        });



        /*TranslateAnimation animate = new TranslateAnimation(fifthLayout.getWidth(),0,0,0);
        animate.setDuration(1000);
        animate.setFillAfter(true);
        fifthLayout.startAnimation(animate);
        fifthLayout.setVisibility(View.VISIBLE);*/
    }

    private void checkAppIntro() {

        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(FirstActivity.this, AppIntroActivity.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                    finish();
                }
            }
        });

        // Start the thread
        t.start();
    }

    private void instantiate() {
        firstLayout = (LinearLayout) findViewById(R.id.first_layout);
        secondLayout = (LinearLayout) findViewById(R.id.second_layout);
        thirdLayout = (LinearLayout) findViewById(R.id.third_layout);
        fourthLayout = (LinearLayout) findViewById(R.id.fourth_layout);
        fifthLayout = (LinearLayout) findViewById(R.id.fifth_layout);
        parentLayout = (LinearLayout) findViewById(R.id.parent_layout);

        first_layout_content = (LinearLayout) findViewById(R.id.first_layout_content);
        second_layout_content = (LinearLayout) findViewById(R.id.second_layout_content);
        third_layout_content = (LinearLayout) findViewById(R.id.third_layout_content);
        fourth_layout_content = (LinearLayout) findViewById(R.id.fourth_layout_content);
        fifth_layout_content = (LinearLayout) findViewById(R.id.fifth_layout_content);

        img_first = (ImageView) findViewById(R.id.img_first);
        img_second = (ImageView) findViewById(R.id.img_second);
        img_third = (ImageView) findViewById(R.id.img_third);
        img_fourth = (ImageView) findViewById(R.id.img_fourth);
        img_fifth = (ImageView) findViewById(R.id.img_fifth);

    }

    private void holdFirstActivityAnimation(boolean b) {
        SharedPreferences sharedPreferences_2 = getSharedPreferences("holdFirstActivityAnimation", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences_2.edit();
        editor2.putBoolean("holdFirstActivityAnimation", b);
        editor2.commit();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // super.onWindowFocusChanged(hasFocus);


        SharedPreferences sharedPreferences_1 = getSharedPreferences("holdFirstActivityAnimation", MODE_PRIVATE);
        Boolean holdFirstActivityAnimation = sharedPreferences_1.getBoolean("holdFirstActivityAnimation", false);


        if ((!holdFirstActivityAnimation) && count == 0) {
            if (hasFocus) {
            /*
                TranslateAnimation animate = new TranslateAnimation(-firstLayout.getWidth(), 0, 0, 0);
                animate.setDuration(150);
                animate.setFillAfter(true);
                firstLayout.startAnimation(animate);
                firstLayout.setVisibility(View.VISIBLE);
                animate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        TranslateAnimation animate_second = new TranslateAnimation(-secondLayout.getWidth(), 0, 0, 0);
                        animate_second.setDuration(150);
                        animate_second.setFillAfter(true);
                        secondLayout.startAnimation(animate_second);
                        //parentLayout.setBackgroundColor(0xffee6d66);
                        secondLayout.setVisibility(View.VISIBLE);
                        animate_second.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                TranslateAnimation animate_third = new TranslateAnimation(-thirdLayout.getWidth(), 0, 0, 0);
                                animate_third.setDuration(150);
                                animate_third.setFillAfter(true);
                                thirdLayout.startAnimation(animate_third);
                                thirdLayout.setVisibility(View.VISIBLE);
                                // parentLayout.setBackgroundColor(0xffD56E83);
                                animate_third.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        TranslateAnimation animate_fourth = new TranslateAnimation(-fourthLayout.getWidth(), 0, 0, 0);
                                        animate_fourth.setDuration(150);
                                        animate_fourth.setFillAfter(true);
                                        fourthLayout.startAnimation(animate_fourth);
                                        fourthLayout.setVisibility(View.VISIBLE);
                                        animate_fourth.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                TranslateAnimation animate_fifth = new TranslateAnimation(-fifthLayout.getWidth(), 0, 0, 0);
                                                animate_fifth.setDuration(150);
                                                animate_fifth.setFillAfter(true);
                                                fifthLayout.startAnimation(animate_fifth);
                                                fifthLayout.setVisibility(View.VISIBLE);
                                                holdFirstActivityAnimation(true);
                                                //Toast.makeText(FirstActivity.this, "called", Toast.LENGTH_SHORT).show();
                                                //count++;
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/


                TranslateAnimation animate = new TranslateAnimation(-firstLayout.getWidth(), 0, 0, 0);
                animate.setDuration(650);
                animate.setFillAfter(true);
                firstLayout.startAnimation(animate);
                firstLayout.setVisibility(View.VISIBLE);

                thirdLayout.startAnimation(animate);
                thirdLayout.setVisibility(View.VISIBLE);

                fifthLayout.startAnimation(animate);
                fifthLayout.setVisibility(View.VISIBLE);


                TranslateAnimation animate2 = new TranslateAnimation(secondLayout.getWidth(), 0, 0, 0);
                animate2.setDuration(650);
                animate2.setFillAfter(true);

                secondLayout.startAnimation(animate2);
                secondLayout.setVisibility(View.VISIBLE);

                fourthLayout.startAnimation(animate2);
                fourthLayout.setVisibility(View.VISIBLE);

                ++count;





                /*TranslateAnimation animate = new TranslateAnimation(0, 0, 5 * firstLayout.getWidth(), 0);
                animate.setDuration(250);
                animate.setFillAfter(true);
                firstLayout.startAnimation(animate);
                firstLayout.setVisibility(View.VISIBLE);
                animate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        TranslateAnimation animate_second = new TranslateAnimation(0, 0, 4 * firstLayout.getWidth(), 0);
                        animate_second.setDuration(250);
                        animate_second.setFillAfter(true);
                        secondLayout.startAnimation(animate_second);
                        //parentLayout.setBackgroundColor(0xffee6d66);
                        secondLayout.setVisibility(View.VISIBLE);
                        animate_second.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                TranslateAnimation animate_third = new TranslateAnimation(0, 0, 3 * firstLayout.getWidth(), 0);
                                animate_third.setDuration(250);
                                animate_third.setFillAfter(true);
                                thirdLayout.startAnimation(animate_third);
                                thirdLayout.setVisibility(View.VISIBLE);
                                // parentLayout.setBackgroundColor(0xffD56E83);
                                animate_third.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        TranslateAnimation animate_fourth = new TranslateAnimation(0, 0, 2 * firstLayout.getWidth(), 0);
                                        animate_fourth.setDuration(250);
                                        animate_fourth.setFillAfter(true);
                                        fourthLayout.startAnimation(animate_fourth);
                                        fourthLayout.setVisibility(View.VISIBLE);
                                        animate_fourth.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                TranslateAnimation animate_fifth = new TranslateAnimation(0, 0, 1 * firstLayout.getWidth(), 0);
                                                animate_fifth.setDuration(250);
                                                animate_fifth.setFillAfter(true);
                                                fifthLayout.startAnimation(animate_fifth);
                                                fifthLayout.setVisibility(View.VISIBLE);
                                                holdFirstActivityAnimation(true);
                                                //Toast.makeText(FirstActivity.this, "called", Toast.LENGTH_SHORT).show();
                                                //count++;
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
*/





              /*  TranslateAnimation animate = new TranslateAnimation(0, 0, -5 * firstLayout.getWidth(), 0);
                animate.setDuration(350);
                animate.setFillAfter(true);
                fifthLayout.startAnimation(animate);
                fifthLayout.setVisibility(View.VISIBLE);
                animate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        TranslateAnimation animate_second = new TranslateAnimation(0, 0, -4 * firstLayout.getWidth(), 0);
                        animate_second.setDuration(200);
                        animate_second.setFillAfter(true);
                        fourthLayout.startAnimation(animate_second);
                        //parentLayout.setBackgroundColor(0xffee6d66);
                        fourthLayout.setVisibility(View.VISIBLE);
                        animate_second.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                TranslateAnimation animate_third = new TranslateAnimation(0, 0, -3 * firstLayout.getWidth(), 0);
                                animate_third.setDuration(200);
                                animate_third.setFillAfter(true);
                                thirdLayout.startAnimation(animate_third);
                                thirdLayout.setVisibility(View.VISIBLE);
                                // parentLayout.setBackgroundColor(0xffD56E83);
                                animate_third.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        TranslateAnimation animate_fourth = new TranslateAnimation(0, 0, -2 * firstLayout.getWidth(), 0);
                                        animate_fourth.setDuration(200);
                                        animate_fourth.setFillAfter(true);
                                        secondLayout.startAnimation(animate_fourth);
                                        secondLayout.setVisibility(View.VISIBLE);
                                        animate_fourth.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                TranslateAnimation animate_fifth = new TranslateAnimation(0, 0, -1 * firstLayout.getWidth(), 0);
                                                animate_fifth.setDuration(200);
                                                animate_fifth.setFillAfter(true);
                                                firstLayout.startAnimation(animate_fifth);
                                                firstLayout.setVisibility(View.VISIBLE);
                                                holdFirstActivityAnimation(true);
                                                //Toast.makeText(FirstActivity.this, "called", Toast.LENGTH_SHORT).show();
                                                //count++;
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/






           /* Animation animation = null;
            animation = AnimationUtils.loadAnimation(FirstActivity.this, R.anim.push_up_in);
            animation.setDuration(150);
            fifthLayout.startAnimation(animation);
            fifthLayout.setVisibility(View.VISIBLE);
            animation = null;*/

            } else {
                //holdFirstActivityAnimation(false);
                //Toast.makeText(FirstActivity.this, "without focus", Toast.LENGTH_SHORT).show();

            }

        } else {
            firstLayout.setVisibility(View.VISIBLE);
            secondLayout.setVisibility(View.VISIBLE);
            thirdLayout.setVisibility(View.VISIBLE);
            fourthLayout.setVisibility(View.VISIBLE);
            fifthLayout.setVisibility(View.VISIBLE);
        }


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(firstLayout.getWidth() / 4, 0, 0, 0);
        img_first.setLayoutParams(lp);
        img_second.setLayoutParams(lp);
        img_third.setLayoutParams(lp);
        img_fourth.setLayoutParams(lp);
        img_fifth.setLayoutParams(lp);
        //Toast.makeText(FirstActivity.this, ""+i, Toast.LENGTH_SHORT).show();


        //Toast.makeText(FirstActivity.this, "Window Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        /*if (menuItem.getItemId() == android.R.id.home) {
            Toast.makeText(FirstActivity.this, "called", Toast.LENGTH_SHORT).show();
        }*/
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Toast.makeText(FirstActivity.this, "back pressed", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences_3 = getSharedPreferences("holdFirstActivityAnimation", MODE_PRIVATE);
        SharedPreferences.Editor editor_3 = sharedPreferences_3.edit();
        editor_3.putBoolean("holdFirstActivityAnimation", false);
        editor_3.commit();
    }


    @Override
    protected void onPause() {
        super.onPause();
        holdFirstActivityAnimation(false);
        ++count;
        //Toast.makeText(FirstActivity.this, "onPause Called " + count, Toast.LENGTH_SHORT).show();
    }
}
