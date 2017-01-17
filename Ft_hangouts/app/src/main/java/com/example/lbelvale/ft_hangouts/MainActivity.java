package com.example.lbelvale.ft_hangouts;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView contactList;
    String currentDateTimeString;
    private ActionBar actionBar;
    private int color;
    List<Contact> contacts;
    IntentFilter filter1;
    ContactDAO db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        color = getIntent().getIntExtra("color", Color.parseColor("#4CAF50"));
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(color));
        contactList = (ListView) findViewById(R.id.contact_list);
        db2 = new ContactDAO(getApplicationContext());
        contacts = getContacts();

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},1);

        ContactAdapter adapter = new ContactAdapter(MainActivity.this, contacts);
        contactList.setAdapter(adapter);

        contactList.setOnItemClickListener(this);

        filter1 = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(myReceiver, filter1);

        FloatingActionButton addContactButton = (FloatingActionButton) findViewById(R.id.addContact);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                intent.putExtra("color", color);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_blue:
                color = Color.BLUE;
                actionBar.setBackgroundDrawable(new ColorDrawable(color));
                return true;
            case R.id.action_red:
                color = Color.RED;
                actionBar.setBackgroundDrawable(new ColorDrawable(color));
                return true;
            case R.id.action_green:
                color = Color.parseColor("#4CAF50");
                actionBar.setBackgroundDrawable(new ColorDrawable(color));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Context context = getApplicationContext();
        CharSequence text = "App restart at : " + currentDateTimeString;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private List<Contact> getContacts() {
        List<Contact> contacts;
        ContactDAO db = new ContactDAO(getApplicationContext());
        db.open();
        contacts = db.getAllContacts();
        db.close();


        return contacts;
    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                contacts.clear();
                contacts.addAll(getContacts());
                ((ContactAdapter) contactList.getAdapter()).notifyDataSetChanged();
            }

        }
    };


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contact contact = (Contact) contactList.getAdapter().getItem(position);
        Intent intent = new Intent(MainActivity.this, ShowContactActivity.class);
        intent.putExtra("contact", contact);
        intent.putExtra("color", color);
        startActivity(intent);
    }
}
