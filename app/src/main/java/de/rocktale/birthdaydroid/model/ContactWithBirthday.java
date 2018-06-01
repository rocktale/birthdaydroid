package de.rocktale.birthdaydroid.model;

import android.net.Uri;

import java.util.Calendar;
import java.util.Date;

public class ContactWithBirthday {

    public Uri profilePicture;
    public String fullName;
    public Date birthday;

    public static int getYearDifference(Calendar first, Calendar last) {
        int diff = last.get(Calendar.YEAR) - first.get(Calendar.YEAR);
        if (first.get(Calendar.MONTH) > last.get(Calendar.MONTH) ||
                (first.get(Calendar.MONTH) == last.get(Calendar.MONTH) &&
                 first.get(Calendar.DAY_OF_MONTH) > last.get(Calendar.DAY_OF_MONTH))) {
            diff--;
        }

        return diff;
    }

    public int getAge() {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthday);
        return getYearDifference(birth, Calendar.getInstance());
    }

}
