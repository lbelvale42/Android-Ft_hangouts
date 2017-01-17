package com.example.lbelvale.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lbelvale on 12/23/16.
 */

public class MessageDAO  extends DAOBase{
    public static final String TABLE_NAME = "message";
    public static final String KEY = "id";
    public static final String NUMBER = "number";
    public static final String MESSAGE = "message";
    public static final String ISSENDMESSAGE = "isSendMessage";
    public static final String CREATEDAT = "createdAt";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NUMBER + " TEXT, "
            + MESSAGE + " TEXT, "
            + ISSENDMESSAGE + " INTEGER, "
            + CREATEDAT + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public MessageDAO(Context pContext) {
        super(pContext);
    }

    public void addMessage(Message m)
    {
        ContentValues value = new ContentValues();
        value.put(MessageDAO.NUMBER, m.getNumber());
        value.put(MessageDAO.MESSAGE, m.getMessage());
        value.put(MessageDAO.ISSENDMESSAGE, m.getSendMessage());
        value.put(MessageDAO.CREATEDAT, m.getCreateAt());
        Log.v("test", m.getCreateAt());
        mDb.insert(MessageDAO.TABLE_NAME, null, value);
    }

    public List<Message> getMessageFrom(String number) {
        List<Message> ret = new ArrayList<Message>();
        Message tmp;
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where " + NUMBER + " = ? OR " + NUMBER + " = ?", new String[] {number, "+33" + number.substring(1)});
        c.moveToFirst();
        if (c.getCount() != 0) {
            tmp = new Message(c.getString(1), c.getString(2), c.getInt(3), c.getString(4));
            tmp.setId(c.getInt(0));
            ret.add(tmp);
        }
        while (c.moveToNext()) {
            tmp = new Message(c.getString(1), c.getString(2), c.getInt(3), c.getString(4));
            tmp.setId(c.getInt(0));
            ret.add(tmp);
        }
        c.close();
        return ret;
    }


}
