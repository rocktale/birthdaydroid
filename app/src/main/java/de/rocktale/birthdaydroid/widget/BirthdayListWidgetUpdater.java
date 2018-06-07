package de.rocktale.birthdaydroid.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import de.rocktale.birthdaydroid.R;

public class BirthdayListWidgetUpdater implements AlarmManager.OnAlarmListener {

    private Duration interval;
    private LocalDateTime lastAlarmTarget;
    private Context context;
    private AppWidgetManager widgetManager;
    private int[] widgetIds;

    private static final String TAG = "BirthdayListWidgetUpdater";

    BirthdayListWidgetUpdater(Context context, AppWidgetManager widgetManager, int[] ids) {
        this.context = context;
        this.widgetManager = widgetManager;
        this.widgetIds = ids;
    }

    public void set(LocalTime startTime, Duration interval) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = LocalDateTime.of(LocalDate.now(), startTime);
        if (now.isAfter(target))
        {
            target = target.plusDays(1);
        }

        this.interval = interval;
        schedule(target);
    }

    public void cancel() {
        getAlarmManager().cancel(this);
    }

    @Override
    public void onAlarm() {
        Log.d(TAG, "Received alarm");
        if (widgetIds != null) {
            Log.d(TAG, "Updating widgets: " + Arrays.toString(widgetIds));
            widgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.birthday_list_widget);
        }
        else
        {
            Log.d(TAG, "No widgets to update");
        }

        // schedule next alarm
        schedule(lastAlarmTarget.plus(interval));
    }

    private void schedule(LocalDateTime when) {
        Log.d(TAG, "Scheduling alarm for " + when.toString());

        LocalDateTime now = LocalDateTime.now();
        long delay = 0;
        if (when.isAfter(now)) {
            delay = now.until(when, ChronoUnit.MILLIS);
        }
        Log.d(TAG, "Calculated delay: " + Long.toString(delay) + "ms");

        lastAlarmTarget = when;
        getAlarmManager().setExact(AlarmManager.RTC, System.currentTimeMillis() + delay, TAG, this, null);
    }

    private AlarmManager getAlarmManager() {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
}
