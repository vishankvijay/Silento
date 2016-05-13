package my.awesome.silento;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class feedBackActivity extends AppCompatActivity {


    Toolbar toolbar;

    EditText title, message;
    TextInputLayout textTitle, textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (EditText) findViewById(R.id.feedtitle);
        message = (EditText) findViewById(R.id.feedmsg);

        textTitle = (TextInputLayout) findViewById(R.id.textTitle);
        textMessage = (TextInputLayout) findViewById(R.id.textMsg);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed_back, menu);
        return true;
    }


    @Override
    protected void onStop() {
        super.onStop();

        title.setText("");
        message.setText("");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home)
            onBackPressed();

        if (id == R.id.action_send) {

            String sub = title.getText().toString();
            String msg = message.getText().toString();

            if (sub.matches("") && msg.matches("")) {

                textTitle.setError("Title cannot be blank");
                textMessage.setError("Message cannot be blank");
            } else if (sub.matches("")) {
                textTitle.setError("Title cannot be blank");
                textMessage.setError(null);
            } else if (msg.matches("")) {
                textTitle.setError(null);
                textMessage.setError("Message cannot be blank");
            } else {

                textTitle.setError(null);
                textMessage.setError(null);

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"vvishank@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, sub);
                email.putExtra(Intent.EXTRA_TEXT, msg);

                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email Client"));
            }


        }



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
