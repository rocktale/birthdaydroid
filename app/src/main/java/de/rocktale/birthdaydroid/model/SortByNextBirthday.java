package de.rocktale.birthdaydroid.model;


import java.util.Calendar;
import java.util.Comparator;

public class SortByNextBirthday implements Comparator<ContactWithBirthday> {

    private Calendar today;

    public SortByNextBirthday(Calendar today) {
        this.today = today;
    }

    public int compare(ContactWithBirthday a, ContactWithBirthday b) {
        long timeToA = a.timeToNextBirthday(today);
        long timeToB = b.timeToNextBirthday(today);
        if (timeToA < timeToB) {
            return -1;
        }
        else if (timeToA == timeToB) {
            return 0;
        } else {
            return 1;
        }
    }

}
