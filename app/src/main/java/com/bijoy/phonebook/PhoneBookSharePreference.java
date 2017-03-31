package com.bijoy.phonebook;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by engrb on 31-Mar-17.
 */

public class PhoneBookSharePreference {
    private SharedPreferences sharedPreferences;
    private Context context;

    public PhoneBookSharePreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("phoneBook", Context.MODE_PRIVATE);
    }

    public void saveLimit(int limit) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("limit", limit);
        editor.apply();
        editor.commit();
    }

    public int getLimit() {
        return sharedPreferences.getInt("limit", 0);
    }

}
