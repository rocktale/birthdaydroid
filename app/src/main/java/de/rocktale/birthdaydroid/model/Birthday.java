package de.rocktale.birthdaydroid.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Birthday {

    private LocalDate date;
    private static final DateTimeFormatter defaulInputFormatter =
            DateTimeFormatter.ofPattern(
                    "[yy-MM-dd]" +
                    "[yyyy-MM-dd]");

    public Birthday(LocalDate date) {
        this.date = date;
    }

    public Birthday(String dateString) {
        this.date = LocalDate.parse(dateString, defaulInputFormatter);
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalDate nextBirthday(LocalDate today) {
        LocalDate nextBirthday = date.withYear(today.getYear());
        if (today.isAfter(nextBirthday))
        {
            return nextBirthday.plusYears(1);
        }
        else
        {
            return nextBirthday;
        }
    }

    public long daysTillNextBirthday(LocalDate today) {
        return ChronoUnit.DAYS.between(today, nextBirthday(today));
    }

    public boolean isAtDate(LocalDate date) {
        return date.equals(this.date.withYear(date.getYear()));
    }

    public long ageAtDate(LocalDate date) {
        return ChronoUnit.YEARS.between(this.date, date);
    }

    public long ageOnNextBirthday(LocalDate today) {
        if (isAtDate(today)) {
            return ageAtDate(today);
        }
        else {
            return ageAtDate(today) + 1;
        }
    }
}
