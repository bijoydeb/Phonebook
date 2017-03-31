package com.bijoy.phonebook.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bijoy.phonebook.Database.ContactManager;
import com.bijoy.phonebook.Model.ContactItem;
import com.bijoy.phonebook.R;
import com.bijoy.phonebook.RoundImageView;

import java.util.ArrayList;

/**
 * Created by engrb on 30-Mar-17.
 */

public class ContactRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ContactItem> contactItems;
    private ContactManager contactManager;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_MORE = 1;

    private boolean hasLoadButton = true;


    public boolean isHasLoadButton() {
        return hasLoadButton;
    }


    public ContactRecyclerAdapter(Context context, ArrayList<ContactItem> contactItems) {
        this.context = context;
        this.contactItems = contactItems;
        contactManager = new ContactManager(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
            return new ContactViewHolder(view);
        } else if (viewType == VIEW_TYPE_MORE) {
            View view = LayoutInflater.from(context).inflate(R.layout.more_item_layout, parent, false);
            return new MoreViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ContactViewHolder) {

            ContactItem contactItem = contactItems.get(position);

            ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
            contactViewHolder.nameTextView.setText(contactItem.getName());
            contactViewHolder.numberTextView.setText(contactItem.getNumber());

            Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
                    contactItem.getProfileImage());

            contactViewHolder.profileImageView.setImageBitmap(RoundImageView.getCircleBitmap(bm));


        } else {
            final MoreViewHolder moreViewHolder = (MoreViewHolder) holder;
            moreViewHolder.moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactItems = contactManager.getAllContacts("2");
                    notifyDataSetChanged();
                    moreViewHolder.moreButton.setText(R.string.no_more);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (hasLoadButton) {
            return contactItems.size() + 1;
        } else {
            return contactItems.size();
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (position < getItemCount() - 1) {
            return VIEW_TYPE_ITEM;
        } else {
            return VIEW_TYPE_MORE;
        }
    }


    private static class ContactViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImageView;
        private TextView nameTextView;
        private TextView numberTextView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            profileImageView = (ImageView) itemView.findViewById(R.id.profileImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            numberTextView = (TextView) itemView.findViewById(R.id.numberTextView);

        }


    }

    private static class MoreViewHolder extends RecyclerView.ViewHolder {

        public Button moreButton;

        public MoreViewHolder(View itemView) {
            super(itemView);
            moreButton = (Button) itemView.findViewById(R.id.moreButton);
        }

    }
}
