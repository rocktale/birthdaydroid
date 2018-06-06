package de.rocktale.birthdaydroid.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import de.rocktale.birthdaydroid.BirthdaysActivity;
import de.rocktale.birthdaydroid.R;

/**
 * Implementation of App Widget functionality.
 */
public class BirthdayListWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Setup intent to use the service to populate the class
        Intent intent = new Intent(context, BirthdayListWidgetService.class);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.birthday_list_widget);

        // set the adapter to populate the widget
        views.setRemoteAdapter(R.id.birthday_list_widget, intent);

        // TODO: check empty view usage
        // views.setEmptyView(R.id.birthday_list_widget, ...);


        // Launch the app when clicking on the widget
        Intent launchIntent = new Intent(context, BirthdaysActivity.class);
        PendingIntent launchPendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);

        views.setPendingIntentTemplate(R.id.birthday_list_widget, launchPendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

