package com.bijoy.phonebook.Model;

/**
 * Created by engrb on 30-Mar-17.
 */

public class ContactItem {
    private int id;
    private String name;
    private String number;
    private int profileImage;

    public ContactItem(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public ContactItem(int id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public ContactItem(int id, String name, String number, int profileImage) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public int getProfileImage() {
        return profileImage;
    }
}
