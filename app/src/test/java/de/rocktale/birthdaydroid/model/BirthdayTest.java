package de.rocktale.birthdaydroid.model;

import org.junit.Test;
import org.threeten.bp.LocalDate;

import static org.junit.Assert.*;
import static org.threeten.bp.Month.*;

public class BirthdayTest {

    @Test
    public void daysTillNextBirthday() {
        Birthday b = new Birthday("90-03-26");

        LocalDate today = LocalDate.of(2018, MARCH, 22);
        assertEquals(4, b.daysTillNextBirthday(today));

        today = LocalDate.of(2018, MARCH, 30);
        assertEquals(365-4, b.daysTillNextBirthday(today));
    }

    @Test
    public void ageAtDate() {
        Birthday b = new Birthday("1990-03-26");

        LocalDate before = LocalDate.of(2018, JANUARY, 4);
        assertEquals(27, b.ageAtDate(before));

        LocalDate beforeWithinMonth = LocalDate.of(2018, MARCH, 25);
        assertEquals(27, b.ageAtDate(beforeWithinMonth));

        LocalDate sameDay = LocalDate.of(2018, MARCH, 26);
        assertEquals(28, b.ageAtDate(sameDay));

        LocalDate afterWithinMonth = LocalDate.of(2018, MARCH, 30);
        assertEquals(28, b.ageAtDate(afterWithinMonth));

        LocalDate after = LocalDate.of(2018, MAY, 4);
        assertEquals(28, b.ageAtDate(after));
    }

    @Test
    public void isAtDate() {
        Birthday b = new Birthday("1990-03-26");

        LocalDate itIsToday = LocalDate.of(2018, MARCH, 26);
        assertEquals(true, b.isAtDate(itIsToday));

        LocalDate itIsNotToday = LocalDate.of(2018, MARCH, 27);
        assertEquals(false, b.isAtDate(itIsNotToday));
    }
}