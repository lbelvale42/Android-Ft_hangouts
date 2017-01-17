package com.example.lbelvale.ft_hangouts;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.Serializable;
import java.nio.LongBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lbelvale on 12/22/16.
 */

public class SendMessageActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private ListView messageList;
    private int color;
    private Contact contact;
    private List<Message> messages;
    ImageView sendButton;
    EditText content;
    MessageDAO db;


    IntentFilter filter1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        contact = (Contact) getIntent().getSerializableExtra("contact");
        messageList = (ListView) findViewById(R.id.listview);
        db = new MessageDAO(getApplicationContext());
        db.open();
        messages = db.getMessageFrom(contact.getPhone());
        db.close();


        MessageAdapter adapter = new MessageAdapter(SendMessageActivity.this, messages);
        messageList.setAdapter(adapter);

        color = getIntent().getIntExtra("color", Color.parseColor("#4CAF50"));
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(color));

        actionBar.setTitle(contact.getFirstname() + " " + contact.getLastname());

        sendButton = (ImageView) findViewById(R.id.imageSend);
        content = (EditText) findViewById(R.id.messageText);

        filter1 = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(myReceiver, filter1);

        messageList.setSelection(messageList.getCount()-1);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sms = content.getText().toString();

                try {
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    Date date = new Date();
                    String datetime = dateformat.format(date);
                    Message message = new Message(contact.getPhone(), sms, 1, datetime);
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(contact.getPhone(), null, sms, null, null);
                    db = new MessageDAO(getApplicationContext());
                    db.open();
                    db.addMessage(message);
                    db.close();

                    messages.add(message);

                    ((MessageAdapter) messageList.getAdapter()).notifyDataSetChanged();
                    content.setText("");
                    messageList.setSelection(messageList.getCount()-1);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed" + e,
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                db.open();
                messages.clear();
                messages.addAll(db.getMessageFrom(contact.getPhone()));
                db.close();
                ((MessageAdapter) messageList.getAdapter()).notifyDataSetChanged();
                messageList.setSelection(messageList.getCount()-1);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SendMessageActivity.this, ShowContactActivity.class);
        intent.putExtra("contact", (Serializable) contact);
        intent.putExtra("color", color);
        startActivity(intent);
    }

}
