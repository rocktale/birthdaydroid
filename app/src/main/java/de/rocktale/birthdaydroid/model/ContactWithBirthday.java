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
        return getYearDifference(getCalendar(birthday), Calendar.getInstance());
    }

    public long timeToNextBirthday(Calendar today) {
        Calendar nextBirthday = getCalendar(birthday);
        nextBirthday.set(Calendar.YEAR, today.get(Calendar.YEAR));
        if (!nextBirthday.after(today)) {
            nextBirthday.add(Calendar.YEAR, 1);
        }

        return nextBirthday.getTimeInMillis() - today.getTimeInMillis();
    }

    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
