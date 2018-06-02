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
        assertEquals(27, ContactWithBirthday.getYearDifference(refCal, beforeCal));

        Calendar beforeWithinMonthCal = new GregorianCalendar(2018, Calendar.MARCH, 25);
        assertEquals(27, ContactWithBirthday.getYearDifference(refCal, beforeWithinMonthCal));

        Calendar sameDayCal = new GregorianCalendar(2018, Calendar.MARCH, 26);
        assertEquals(28, ContactWithBirthday.getYearDifference(refCal, sameDayCal));

        Calendar afterWithinMonthCal = new GregorianCalendar(2018, Calendar.MARCH, 30);
        assertEquals(28, ContactWithBirthday.getYearDifference(refCal, afterWithinMonthCal));

        Calendar afterCal = new GregorianCalendar(2018, Calendar.MAY, 4);
        assertEquals(28, ContactWithBirthday.getYearDifference(refCal, afterCal));

    }

}