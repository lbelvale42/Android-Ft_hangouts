package com.example.lbelvale.ft_hangouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lbelvale on 12/22/16.
 */

public class SmsReceiver extends BroadcastReceiver {
    private String TAG = SmsReceiver.class.getSimpleName();
//    public static final String BROADCAST = "android.provider.Telephony.SMS_RECEIVED";
    public static String BROADCAST_ACTION = "com.unitedcoders.android.broadcasttest.SHOWTOAST";
    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the data (SMS data) bound to intent
        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs = null;


        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date = new Date();
            String datetime = dateformat.format(date);
            for (int i=0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                Message message = new Message(msgs[i].getOriginatingAddress(), msgs[i].getMessageBody(), 0, datetime);
                MessageDAO db = new MessageDAO(context);
                db.open();
                db.addMessage(message);
                db.close();

                ContactDAO db2 = new ContactDAO(context);
                db2.open();
                if (db2.contactExist(message.getNumber()) == false) {
                    Contact newContact = new Contact(Color.BLUE, message.getNumber(), "", message.getNumber(), "", "");
                    db2.addContact(newContact);
                }
                db2.close();
            }
        }
    }
}
