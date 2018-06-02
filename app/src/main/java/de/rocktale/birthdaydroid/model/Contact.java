package de.rocktale.birthdaydroid.model;

import android.net.Uri;

public class Contact {
    public String fullName;
    public Birthday birthday;
    public Uri profilePicture;

    public Contact(String name) {
        this.fullName = name;
    }

    public Contact(String name, String birthdayString) {
        this.fullName = name;
        this.birthday = new Birthday(birthdayString);
    }

    public Contact(String name, Birthday birthday) {
        this.fullName = name;
        this.birthday = birthday;
    }
}
