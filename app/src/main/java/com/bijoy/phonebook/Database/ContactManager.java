package com.bijoy.phonebook.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bijoy.phonebook.Model.ContactItem;
import com.bijoy.phonebook.PhoneBookSharePreference;
import com.bijoy.phonebook.RoundImageView;

import java.util.ArrayList;

/**
 * Created by engrb on 30-Mar-17.
 */

public class ContactManager {

    private SQLiteDatabase database;
    private ContactDbHelper contactDbHelper;
    private Context context;
    private Cursor cursor = null;
    private PhoneBookSharePreference phoneBookSharePreference;

    public ContactManager(Context context) {
        this.context = context;
        contactDbHelper = new ContactDbHelper(context);
        phoneBookSharePreference = new PhoneBookSharePreference(context);
    }

    public void addContact(ContactItem contactItem) {

        ContentValues values = new ContentValues();
        values.put(ContactDbHelper.CONTACT_NAME, contactItem.getName());
        values.put(ContactDbHelper.CONTACT_NUMBER, contactItem.getNumber());


        try {
            database = contactDbHelper.getWritableDatabase();
            database.insert(ContactDbHelper.CONTACT_TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
        }


    }

    public boolean checkIsNameExists(String name) {
        try {
            database = contactDbHelper.getReadableDatabase();
            String Query = "Select " + ContactDbHelper.CONTACT_NAME + " from " + ContactDbHelper.CONTACT_TABLE_NAME + " where " + ContactDbHelper.CONTACT_NAME + " = '" + name + "'";

            cursor = database.rawQuery(Query, null);
            if (cursor.getCount() <= 0) {
                cursor.close();
                return false;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
        }

        return true;
    }

    public ArrayList<ContactItem> getAllContacts(int limit) {
        ArrayList<ContactItem> contactItemArrayList = new ArrayList<>();
        ContactItem contactItem;
        String query;
        int preLimit;
        int imgPosition = 0;

        if (phoneBookSharePreference.getLimit() == 0) {
            phoneBookSharePreference.saveLimit(limit);
        } else {
            preLimit = phoneBookSharePreference.getLimit();

            phoneBookSharePreference.saveLimit(preLimit += limit);
        }

        try {
            database = contactDbHelper.getReadableDatabase();

            query = "select * from " + ContactDbHelper.CONTACT_TABLE_NAME + " limit " + phoneBookSharePreference.getLimit();
            cursor = database.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(ContactDbHelper.CONTACT_ID));
                        String name = cursor.getString(cursor.getColumnIndex(ContactDbHelper.CONTACT_NAME));
                        String number = cursor.getString(cursor.getColumnIndex(ContactDbHelper.CONTACT_NUMBER));

                        contactItem = new ContactItem(id, name, number, RoundImageView.imageAll[imgPosition]);
                        contactItemArrayList.add(contactItem);

                        if (imgPosition == 19) {
                            imgPosition = 0;
                        }
                        imgPosition++;

                    } while (cursor.moveToNext());
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
            cursor.close();
        }
        return contactItemArrayList;
    }

/*
    public ArrayList<ContactItem> getLastContacts() {
        ArrayList<ContactItem> contactItemArrayList = new ArrayList<>();
        ContactItem contactItem;

        try {
            database = contactDbHelper.getReadableDatabase();
            String query = "select * from " + ContactDbHelper.CONTACT_TABLE_NAME ;
          String Q=  "select * from (select * from "+ContactDbHelper.CONTACT_TABLE_NAME+" order by "+ContactDbHelper.CONTACT_ID+" ASC limit 10) order by "+ContactDbHelper.CONTACT_ID+" DESC";
//            cursor = database.query(ContactDbHelper.CONTACT_TABLE_NAME, null, null, null, null, null, null);

            cursor = database.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(ContactDbHelper.CONTACT_ID));
                        String name = cursor.getString(cursor.getColumnIndex(ContactDbHelper.CONTACT_NAME));
                        String number = cursor.getString(cursor.getColumnIndex(ContactDbHelper.CONTACT_NUMBER));

                        contactItem = new ContactItem(id, name, number);
                        contactItemArrayList.add(contactItem);

                    } while (cursor.moveToNext());
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (database.isOpen()) {
                database.close();
            }
            cursor.close();
        }
        return contactItemArrayList;
    }
*/


}
