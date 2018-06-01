package de.rocktale.birthdaydroid.model;


import java.util.Calendar;
import java.util.Comparator;

public class SortByNextBirthday implements Comparator<ContactWithBirthday> {

    private Calendar today;

    public SortByNextBirthday(Calendar today) {
        this.today = today;
    }

    public int compare(ContactWithBirthday a, ContactWithBirthday b) {
        return Long.compare(a.timeToNextBirthday(today), b.timeToNextBirthday(today));
    }

}
