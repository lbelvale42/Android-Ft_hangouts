package com.example.lbelvale.ft_hangouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by lbelvale on 12/21/16.
 */

public class UpdateContactActivity extends AppCompatActivity{

    private Contact contact;
    String currentDateTimeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.update_contact_activity);

        contact = (Contact) getIntent().getSerializableExtra("contact");
        final EditText firstname = (EditText) findViewById(R.id.firstnameField);
        final EditText lastname = (EditText) findViewById(R.id.lastnameField);
        final EditText phone = (EditText) findViewById(R.id.phoneField);
        final EditText mail = (EditText) findViewById(R.id.emailField);
        final EditText address = (EditText) findViewById(R.id.addressField);

        Button saveButton = (Button) findViewById(R.id.saveContact);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contact.setFirstname(firstname.getText().toString());
                contact.setLastname(lastname.getText().toString());
                contact.setPhone(phone.getText().toString());
                contact.setMail(mail.getText().toString());
                contact.setAddress(address.getText().toString());

                ContactDAO db = new ContactDAO(getApplicationContext());
                db.open();
                if (contact.getFirstname().isEmpty() && contact.getLastname().isEmpty() && contact.getPhone().isEmpty())
                {
                    db.delete(contact.getId());
                    db.close();
                    Intent intent = new Intent(UpdateContactActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                else {
                    db.update(contact);
                    db.close();
                    Intent intent = new Intent(UpdateContactActivity.this, ShowContactActivity.class);
                    intent.putExtra("contact", (Serializable) contact);
                    startActivity(intent);
                }
            }
        });

        firstname.setText(contact.getFirstname());
        lastname.setText(contact.getLastname());
        phone.setText(contact.getPhone());
        mail.setText(contact.getMail());
        address.setText(contact.getAddress());
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
        super.onBackPressed();
        Intent intent = new Intent(UpdateContactActivity.this, ShowContactActivity.class);
        intent.putExtra("contact", (Serializable) contact);
        startActivity(intent);
    }
}
