package de.rocktale.birthdaydroid.model;

import org.junit.Test;
import java.time.LocalDate;
import java.time.Month;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SortContactsByNextBirthdayTest {

    @Test
    public void compare() {
        List<Contact> contacts = new ArrayList<>(3);

        contacts.add(new Contact("a", "1990-03-24"));
        contacts.add(new Contact("b", "1976-05-03"));
        contacts.add(new Contact("c", "2013-11-11"));

        Collections.sort(
                contacts,
                new SortContactsByNextBirthday(LocalDate.of(2018, Month.JUNE, 1)));

        assertEquals("c", contacts.get(0).fullName);
        assertEquals("a", contacts.get(1).fullName);
        assertEquals("b", contacts.get(2).fullName);
    }
}