package com.bijoy.phonebook.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by engrb on 30-Mar-17.
 */

public class ContactDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contact.sqlite";
    private static final int DATABASE_VERSION = 1;

    public static final String CONTACT_TABLE_NAME = "contact";
    public static final String CONTACT_ID = "id";
    public static final String CONTACT_NAME = "name";
    public static final String CONTACT_NUMBER = "number";

    private static final String CONTACT_TABLE = "CREATE TABLE " + CONTACT_TABLE_NAME + "("
            + CONTACT_ID + " integer primary key autoincrement not null,"
            + CONTACT_NAME + " varchar,"
            + CONTACT_NUMBER + " varchar);";

    public ContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE_NAME);
        onCreate(db);
    }
}
