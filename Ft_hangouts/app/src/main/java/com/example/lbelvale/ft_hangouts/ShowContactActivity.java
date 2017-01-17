package com.example.lbelvale.ft_hangouts;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by lbelvale on 12/21/16.
 */

public class ShowContactActivity extends AppCompatActivity {

    private Contact contact;
    String currentDateTimeString;
    private ActionBar actionBar;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_contact_activity);

        color = getIntent().getIntExtra("color", Color.parseColor("#4CAF50"));
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(color));

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);

        contact = (Contact) getIntent().getSerializableExtra("contact");
        TextView firstname = (TextView) findViewById(R.id.firstnameField);
        TextView lastname = (TextView) findViewById(R.id.lastnameField);
        TextView phone = (TextView) findViewById(R.id.phoneField);
        TextView mail = (TextView) findViewById(R.id.emailField);
        TextView address = (TextView) findViewById(R.id.addressField);


        firstname.setText(contact.getFirstname());
        lastname.setText(contact.getLastname());
        phone.setText(contact.getPhone());
        mail.setText(contact.getMail());
        address.setText(contact.getAddress());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.show_contact_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ShowContactActivity.this, MainActivity.class);
        intent.putExtra("color", color);
        startActivity(intent);
    }

    public void updateContact() {
        Intent intent = new Intent(ShowContactActivity.this, UpdateContactActivity.class);
        intent.putExtra("contact", (Serializable) contact);
        intent.putExtra("color", color);
        startActivity(intent);
    }

    public void deleteContact() {
        ContactDAO db = new ContactDAO(getApplicationContext());
        db.open();
        db.delete(contact.getId());
        db.close();
        Intent intent = new Intent(ShowContactActivity.this, MainActivity.class);
        intent.putExtra("color", color);
        startActivity(intent);
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

    @Override
    protected void onStop() {
        super.onStop();
        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    }

    protected void goSMS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},1);
        }
        else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},1);
        }
        else {
            Intent intent = new Intent(ShowContactActivity.this, SendMessageActivity.class);
            intent.putExtra("contact", (Serializable) contact);
            intent.putExtra("color", color);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                updateContact();
                return true;

            case R.id.action_delete:
                deleteContact();
                return true;

            case R.id.action_sendsms:
                goSMS();
                return true;

            case R.id.action_call:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getPhone()));
                    startActivity(intent);
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
