package com.example.lbelvale.ft_hangouts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lbelvale on 12/20/16.
 */

public abstract class DAOBase {
    protected final static int VERSION = 3;
    protected final static String NOM = "database.db";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;

    public DAOBase(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}
