package de.rocktale.birthdaydroid.model;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class ContactWithBirthdayTest {

    @Test
    public void getYearDifference() {
        Calendar refCal = new GregorianCalendar(1990, Calendar.MARCH, 26);

        Calendar beforeCal = new GregorianCalendar(2018, Calendar.JANUARY, 4);
        assertEquals(ContactWithBirthday.getYearDifference(refCal, beforeCal), 27);

        Calendar beforeWithinMonthCal = new GregorianCalendar(2018, Calendar.MARCH, 25);
        assertEquals(ContactWithBirthday.getYearDifference(refCal, beforeWithinMonthCal), 27);

        Calendar sameDayCal = new GregorianCalendar(2018, Calendar.MARCH, 26);
        assertEquals(ContactWithBirthday.getYearDifference(refCal, sameDayCal), 28);

        Calendar afterWithinMonthCal = new GregorianCalendar(2018, Calendar.MARCH, 30);
        assertEquals(ContactWithBirthday.getYearDifference(refCal, afterWithinMonthCal), 28);

        Calendar afterCal = new GregorianCalendar(2018, Calendar.MAY, 4);
        assertEquals(ContactWithBirthday.getYearDifference(refCal, afterCal), 28);

    }
}