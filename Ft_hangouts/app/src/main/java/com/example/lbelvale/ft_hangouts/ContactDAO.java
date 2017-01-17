package com.example.lbelvale.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lbelvale on 12/20/16.
 */

public class ContactDAO extends DAOBase{
    public static final String TABLE_NAME = "contact";
    public static final String KEY = "id";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String PHONE = "phone";
    public static final String MAIL = "mail";
    public static final String ADDRESS = "address";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FIRSTNAME + " TEXT, "
            + LASTNAME + " TEXT, "
            + PHONE + " TEXT, "
            + MAIL + " TEXT, "
            + ADDRESS + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public ContactDAO(Context pContext) {
        super(pContext);
    }

    public void addContact(Contact m) {
        ContentValues value = new ContentValues();
        value.put(ContactDAO.FIRSTNAME, m.getFirstname());
        value.put(ContactDAO.LASTNAME, m.getLastname());
        value.put(ContactDAO.PHONE, m.getPhone());
        value.put(ContactDAO.MAIL, m.getMail());
        value.put(ContactDAO.ADDRESS, m.getAddress());
        mDb.insert(ContactDAO.TABLE_NAME, null, value);

    }

    public void delete(long id) {
        mDb.delete(ContactDAO.TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    public void update(Contact m) {
        ContentValues value = new ContentValues();
        value.put(FIRSTNAME, m.getFirstname());
        value.put(ContactDAO.LASTNAME, m.getLastname());
        value.put(ContactDAO.PHONE, m.getPhone());
        value.put(ContactDAO.MAIL, m.getMail());
        value.put(ContactDAO.ADDRESS, m.getAddress());
        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(m.getId())});
    }

    public Boolean contactExist(String number) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where " + PHONE + " =  ? OR " + PHONE + " = ?", new String[] {number,  "0" + number.substring(3)});
        if (c.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }
    }


    public List<Contact> getAllContacts() {
        List<Contact> ret = new ArrayList<Contact>();
        Contact tmp;
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
        c.moveToFirst();
        if (c.getCount() != 0) {
            tmp = new Contact(Color.BLUE, c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
            tmp.setId(c.getInt(0));
            ret.add(tmp);
        }
        while (c.moveToNext()) {
            tmp = new Contact(Color.BLUE, c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
            tmp.setId(c.getInt(0));
            ret.add(tmp);
        }
        c.close();
        return ret;
    }
}
