package com.example.lbelvale.ft_hangouts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by lbelvale on 12/20/16.
 */

public class AddContactActivity extends AppCompatActivity{

    private Button saveButton;
    private EditText firstname;
    private EditText lastname;
    private EditText phone;
    private EditText mail;
    private EditText address;
    private Contact newContact;
    String currentDateTimeString;

    private ActionBar actionBar;
    private int color;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity);


        color = getIntent().getIntExtra("color", Color.parseColor("#4CAF50"));
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(color));

        saveButton = (Button) findViewById(R.id.saveContact);
        firstname = (EditText) findViewById(R.id.firstnameField);
        lastname = (EditText) findViewById(R.id.lastnameField);
        phone = (EditText) findViewById(R.id.phoneField);
        mail = (EditText) findViewById(R.id.emailField);
        address = (EditText) findViewById(R.id.addressField);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!firstname.getText().toString().isEmpty() || !firstname.getText().toString().isEmpty()) && !phone.getText().toString().isEmpty()) {
                    newContact = new Contact(Color.BLUE, firstname.getText().toString(), lastname.getText().toString(), phone.getText().toString(), mail.getText().toString(), address.getText().toString());

                    ContactDAO db = new ContactDAO(getApplicationContext());
                    db.open();
                    db.addContact(newContact);
                    db.close();
                    Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                    intent.putExtra("color", color);
                    startActivity(intent);

                }
                else {
                    Context context = getApplicationContext();
                    CharSequence text = "You have to enter name and phone number";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
        intent.putExtra("color", color);
        startActivity(intent);
    }
}
