package de.rocktale.birthdaydroid.model;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

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

    public long daysTillNextBirthday(LocalDate today) {
        LocalDate nextBirthday = date.withYear(today.getYear());
        if (today.isAfter(nextBirthday))
        {
            nextBirthday = nextBirthday.plusYears(1);
        }

        return ChronoUnit.DAYS.between(today, nextBirthday);
    }

    public long currentAge(LocalDate today) {
        return ChronoUnit.YEARS.between(date, today);
    }

}
