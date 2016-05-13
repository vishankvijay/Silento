package my.awesome.silento;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.widget.Switch;


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

    private  static  final  int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    final private int REQUEST_CODE_ASK_PERMISSIONS_phoneState = 200;


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
                //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {

                   // requestPermissions(new String[]{Manifest.permission.READ_CONTACTS , Manifest.permission.READ_PHONE_STATE} , PERMISSIONS_REQUEST_READ_CONTACTS);

                    /*Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, 0);*/

                    getPermission();
                }
                else
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, 0);
                }

            }



        });




    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, 0);
                } else {
                    // Permission Denied
                    /*Toast.makeText(ExceptionActiviy.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show()*/;

                    Snackbar.make( coordinatorLayout , "Exceptions may not work without this permission.", Snackbar.LENGTH_LONG).show();
                }
                break;

            case REQUEST_CODE_ASK_PERMISSIONS_phoneState:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // Permission Granted
                    /*Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, 0);*/

                    exceptionEnablSwitch.setChecked(true);
                } else {
                    // Permission Denied
                    /*Toast.makeText(ExceptionActiviy.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show()*/;

                    exceptionEnablSwitch.setChecked(false);

                    Snackbar.make( coordinatorLayout , "Exceptions may not work without this permission.", Snackbar.LENGTH_LONG).show();
                }
                break;


            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }




    private void getPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_CONTACTS);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    showMessageOKCancel("Silento! needs permission to access contacts in order to help you pick important callers from your contacts list.",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ExceptionActiviy.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


   /* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 0);

            }

            else
                Snackbar.make(coordinatorLayout , "Permission required for better functioning." , Snackbar.LENGTH_LONG).show();


            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 0);

            }
        }

    }*/

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
                showSnackbar(exceptionName + " has been added to your Exception List.");
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

        builder.setMessage("Remove '" + exceptionName + "' from Exception list?");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();

            }
        });

        builder.setPositiveButton("Yeah", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                dataBaseManipulator = new DataBaseManipulator(ExceptionActiviy.this);
                dataBaseManipulator.deleteException(exceptionId_delete);
                dataBaseManipulator.close();

                showSnackbar(exceptionName + " has been removed from your Exception List.");
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
        editor.putBoolean("exception_enable", checked);
        editor.apply();

        if(checked) {
            //showSnackbar("Exceptions is enabled.");

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {

                // requestPermissions(new String[]{Manifest.permission.READ_CONTACTS , Manifest.permission.READ_PHONE_STATE} , PERMISSIONS_REQUEST_READ_CONTACTS);

                    /*Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, 0);*/


                getPermission_phone_state();
            }
            else
            {

                showSnackbar("Exceptions is enabled.");
                /*Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 0);*/
            }
        }
        else
            showSnackbar("Exceptions is disabled. To enable it click on the upper right switch.");

    }



    private void getPermission_phone_state() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {

                    exceptionEnablSwitch.setChecked(false);
                    showMessageOKCancel("Silento! needs permission to read phone state in order to know if someone important is calling.",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                                                REQUEST_CODE_ASK_PERMISSIONS_phoneState);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        REQUEST_CODE_ASK_PERMISSIONS_phoneState);
                return;
            }
        }
        showSnackbar("Exceptions is enabled.");
    }

   /* private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ExceptionActiviy.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }*/


    private void showSnackbar(String message)
    {
        /*Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.mdtp_accent_color ))
                .show();*/


        Snackbar.make(coordinatorLayout , message , Snackbar.LENGTH_LONG).show();
    }

}
