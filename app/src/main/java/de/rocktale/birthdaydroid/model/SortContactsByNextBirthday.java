package de.rocktale.birthdaydroid.model;


import org.threeten.bp.LocalDate;

import java.util.Comparator;

public class SortByNextBirthday implements Comparator<Contact> {

    private LocalDate today;

    public SortByNextBirthday(LocalDate today) {
        this.today = today;
    }

    public int compare(Contact a, Contact b) {
        return Long.compare(a.birthday.daysTillNextBirthday(today), b.birthday.daysTillNextBirthday(today));
    }

}
