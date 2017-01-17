package com.example.lbelvale.ft_hangouts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lbelvale on 12/20/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String KEY = "id";
    public static final String CONTACT_FIRSTNAME = "firstname";
    public static final String CONTACT_LASTNAME = "lastname";
    public static final String CONTACT_PHONE = "phone";
    public static final String CONTACT_MAIL = "mail";
    public static final String CONTACT_ADDRESS = "address";


    public static final String MESSAGE_TABLE_NAME = "Message";
    public static final String MESSAGE_NUMBER = "number";
    public static final String MESSAGE_MESSAGE = "message";
    public static final String MESSAGE_ISSENDMESSAGE = "isSendMessage";
    public static final String MESSAGE_CREATEDAT = "createdAt";

    public static final String CONTACT_TABLE_NAME = "Contact";
    public static final String CONTACT_TABLE_DROP = "DROP TABLE IF EXISTS " + CONTACT_TABLE_NAME + ";";
    public static final String MESSAGE_TABLE_DROP = "DROP TABLE IF EXISTS " + MESSAGE_TABLE_NAME + ";";
    public static final String CONTACT_TABLE_CREATE =
            "CREATE TABLE " + CONTACT_TABLE_NAME + " (" +
                    KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CONTACT_FIRSTNAME + " TEXT, " +
                    CONTACT_LASTNAME + " TEXT, " +
                    CONTACT_PHONE + " TEXT, " +
                    CONTACT_MAIL + " TEXT, " +
                    CONTACT_ADDRESS + " TEXT);";

    public static final String MESSAGE_TABLE_CREATE =
            "CREATE TABLE " + MESSAGE_TABLE_NAME + " (" +
             KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MESSAGE_NUMBER + " TEXT, "
            + MESSAGE_MESSAGE + " TEXT, "
            + MESSAGE_ISSENDMESSAGE + " INTEGER, "
            + MESSAGE_CREATEDAT + " TEXT);";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CONTACT_TABLE_CREATE);
        db.execSQL(MESSAGE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CONTACT_TABLE_DROP);
        db.execSQL(MESSAGE_TABLE_DROP);
        onCreate(db);
    }
}
