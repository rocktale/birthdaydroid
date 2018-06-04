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

    public boolean isToday(LocalDate today) {
        return today == nextBirthday(today);
    }

    public long currentAge(LocalDate today) {
        return ChronoUnit.YEARS.between(date, today);
    }

    public long ageOnNextBirthday(LocalDate today) {
        if (isToday(today)) {
            return currentAge(today);
        }
        else {
            return currentAge(today) + 1;
        }
    }
}
