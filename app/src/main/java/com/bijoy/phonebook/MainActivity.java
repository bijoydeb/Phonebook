package com.bijoy.phonebook;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bijoy.phonebook.Adapter.ContactRecyclerAdapter;
import com.bijoy.phonebook.Database.ContactManager;
import com.bijoy.phonebook.Model.ContactItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context;

    ImageView profileImageView;


    ContactManager contactManager;
    ContactItem contactItem;

    RecyclerView contactRecyclerView;
    ArrayList<ContactItem> contactItemArrayList;

    ContactRecyclerAdapter contactRecyclerAdapter;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;
        contactManager = new ContactManager(context);

        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        contactRecyclerView = (RecyclerView) findViewById(R.id.contactRecyclerView);

        contactItemArrayList = new ArrayList<>();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.bijoy);

        profileImageView.setImageBitmap(RoundImageView.getCircleBitmap(bitmap));

        manager = new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false);


        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);


        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            showContacts();
        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        }

//        contactRecyclerView.setLayoutManager(manager);
//        contactItemArrayList = contactManager.getAllContacts("1");
//        contactRecyclerAdapter = new ContactRecyclerAdapter(context, contactItemArrayList);
//        contactRecyclerView.setAdapter(contactRecyclerAdapter);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void showContacts() {
        Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");

        if (c != null) {
            while (c.moveToNext()) {

                String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactItem = new ContactItem(contactName, phNumber);

                if (!contactManager.checkIsNameExists(contactName)) {
                    contactManager.addContact(contactItem);
                }

            }
        }

        if (c != null) {
            c.close();
        }

        contactItemArrayList = contactManager.getAllContacts(10);
        contactRecyclerView.setLayoutManager(manager);
        contactRecyclerAdapter = new ContactRecyclerAdapter(context, contactItemArrayList);
        contactRecyclerView.setAdapter(contactRecyclerAdapter);

    }
}
