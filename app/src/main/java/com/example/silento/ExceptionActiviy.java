package com.example.silento;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.Switch;

import java.util.Objects;


public class ExceptionActiviy extends AppCompatActivity implements AdapterView.OnItemClickListener, Switch.OnCheckedChangeListener {

    ListView exceptionListView;
    private FloatingActionButton addExceptionfab;
    DataBaseManipulator dataBaseManipulator;
    Cursor exceptionCursor;
    int count = 0;
    int count_animation = 0;
    ArrayAdapter<String> exceptionAdapter;
    String[] exceptionList;
    TextView exceptionEmptyTextView;

    Toolbar toolbar;

    //com.melnykov.fab.FloatingActionButton fab ;

    FloatingActionButton fab;

    Switch exceptionEnablSwitch;

    CoordinatorLayout coordinatorLayout;
    Boolean enableValue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_activiy);


        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Exceptions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        exceptionListView = (ListView) findViewById(R.id.exception_listView);
        exceptionListView.setOnItemClickListener(this);
        exceptionEmptyTextView = (TextView) findViewById(R.id.exception_emptyTextView);
        exceptionListView.setEmptyView(exceptionEmptyTextView);


        initializeListView();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.attachToListView(exceptionListView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent , 0);
            }
        });




    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //Toast.makeText(ExceptionActiviy.this, "" + count_animation, Toast.LENGTH_SHORT).show();
        if(hasFocus && count_animation == 0)
        {

        //TranslateAnimation animate = new TranslateAnimation(0,0, 2 *addExceptionfab.getWidth(),0);
         TranslateAnimation animate = new TranslateAnimation(0,0, 2 *fab.getWidth() ,0);
        animate.setDuration(800);
        animate.setFillAfter(true);

            fab.startAnimation(animate);
            fab.setVisibility(View.VISIBLE);
            ++count_animation;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
        case 0:
            if (resultCode == Activity.RESULT_OK)
            {
                String exceptionNumber = null;
                String exceptionName = null;
                Uri contactData = data.getData();
                Cursor c =  getContentResolver().query(contactData, null, null, null, null);
                if (c.moveToFirst())
                {
                    exceptionName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));



                    String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

                    String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    if ((Integer.parseInt(hasPhone)==1 ))
                    {
                        Cursor phones =
                                  getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
                                null, null);
                        while (phones.moveToNext())
                        {
                            exceptionNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            exceptionNumber = exceptionNumber.replaceAll("\\s+","");

                            if(exceptionNumber.length()==10)
                            {
                                exceptionNumber = "+91"+exceptionNumber;
                            }
                            if(exceptionNumber.length()==11)
                            {
                                exceptionNumber = exceptionNumber.substring(1);
                                exceptionNumber = "+91"+exceptionNumber;
                            }



                        }
                        phones.close();
                    }

                }

                Intent saveIntent = new Intent();
                saveIntent.putExtra("exceptionName", exceptionName );
                saveIntent.putExtra("exceptionNumber" , exceptionNumber);

                dataBaseManipulator = new DataBaseManipulator(this);
                dataBaseManipulator.saveException(saveIntent);
                initializeListView();


            }
            break;
        }
    }

    private void initializeListView()
    {


        dataBaseManipulator = new DataBaseManipulator(this);
        exceptionCursor = dataBaseManipulator.fetchException();

        exceptionList = new String[exceptionCursor.getCount()];
        if (exceptionCursor != null)
        {
            exceptionCursor.moveToFirst();
            count = 0;
            while (!exceptionCursor.isAfterLast())
            {
                exceptionList[count] = exceptionCursor.getString(1);
                count++;
                exceptionCursor.moveToNext();
            }
            exceptionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exceptionList);
            exceptionListView.setAdapter(exceptionAdapter);
        } else
        exceptionCursor.close();
        dataBaseManipulator.close();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exception_activiy, menu);

        MenuItem menuItem = menu.findItem(R.id.action_exception_switch);
        View view = MenuItemCompat.getActionView(menuItem);
        exceptionEnablSwitch = (Switch) view.findViewById(R.id.exceptions_custom_switch);
        exceptionEnablSwitch.setOnCheckedChangeListener(this);


        SharedPreferences sharedPreferences = getSharedPreferences("MyExceptionProfile", MODE_PRIVATE);

        enableValue = sharedPreferences.getBoolean("exception_enable", false);
        exceptionEnablSwitch.setChecked(enableValue);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
    {
        dataBaseManipulator = new DataBaseManipulator(this);
        Cursor delete_alarm = dataBaseManipulator.fetchException();
        delete_alarm.moveToPosition(position);

        final int exceptionId_delete = delete_alarm.getInt(0);
        final String exceptionName = delete_alarm.getString(1);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wait!!");

        builder.setMessage("Delete  " + exceptionName + " ?");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();

            }
        });

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                dataBaseManipulator = new DataBaseManipulator(ExceptionActiviy.this);
                dataBaseManipulator.deleteException(exceptionId_delete);
                dataBaseManipulator.close();

                initializeListView();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


        delete_alarm.close();
        dataBaseManipulator.close();

    }



/*
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        SharedPreferences sharedPreferences = getSharedPreferences("holdFirstActivityAnimation", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("holdFirstActivityAnimation", true);
        editor.commit();

        startActivity(new Intent(ExceptionActiviy.this, FirstActivity.class));
        overridePendingTransition(R.anim.slide_in_right, 0);
        count_animation = -1;
    }*/

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //++count_animation;
        //Toast.makeText(ExceptionActiviy.this, ""+ count_animation, Toast.LENGTH_SHORT).show();
        //Toast.makeText(FirstActivity.this, "onPause Called " + count, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(Switch view, boolean checked)
    {
        SharedPreferences sharedPreferences_quick = getSharedPreferences("MyExceptionProfile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences_quick.edit();
        editor.putBoolean("exception_enable" , checked);
        editor.apply();

        if(checked)
            showSnackbar("Exceptions is ON!");
        else
            showSnackbar("Exceptions is OFF!");

    }


    private void showSnackbar(String message)
    {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.mdtp_accent_color ))
                .show();
    }

}
