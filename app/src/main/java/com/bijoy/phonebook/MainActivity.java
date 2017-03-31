package com.bijoy.phonebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bijoy.phonebook.Adapter.ContactRecyclerAdapter;
import com.bijoy.phonebook.Database.ContactManager;
import com.bijoy.phonebook.Model.ContactItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context;

    ImageView profileImageView;
    String[] nameList;
    String[] numberList;

    ContactManager contactManager;
    ContactItem contactItem;

    RecyclerView contactRecyclerView;
    ArrayList<ContactItem> contactItemArrayList;

    ContactRecyclerAdapter contactRecyclerAdapter;

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

        nameList = getResources().getStringArray(R.array.name);
        numberList = getResources().getStringArray(R.array.number);


        for (int i = 0; i < nameList.length; i++) {
            contactItem = new ContactItem(nameList[i], numberList[i]);

            if (!contactManager.checkIsNameExists(nameList[i])) {
                contactManager.addContact(contactItem);
            }
        }


        GridLayoutManager manager = new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false);

        contactRecyclerView.setLayoutManager(manager);

        contactItemArrayList = contactManager.getAllContacts("1");
        contactRecyclerAdapter = new ContactRecyclerAdapter(context, contactItemArrayList);
        contactRecyclerView.setAdapter(contactRecyclerAdapter);

    }

}
