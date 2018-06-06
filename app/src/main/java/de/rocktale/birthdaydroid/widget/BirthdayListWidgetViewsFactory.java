package de.rocktale.birthdaydroid.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

import de.rocktale.birthdaydroid.BirthdaysActivity;
import de.rocktale.birthdaydroid.R;
import de.rocktale.birthdaydroid.model.Birthday;
import de.rocktale.birthdaydroid.model.Contact;
import de.rocktale.birthdaydroid.model.SortContactsByNextBirthday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;

public class BirthdayListWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context = null;
    private List<Contact> contacts = new ArrayList<>();

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.");
    private static final int maxEntries = 20;

    private static final String TAG = "BirthdayWidgetViews";

    BirthdayListWidgetViewsFactory(Context context, Intent intent) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        populateBirthdays();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt: position=" + position);
        Contact c = contacts.get(position);
        RemoteViews rv;

        LocalDate today = LocalDate.now();

        if (c.birthday.isAtDate(today)) {
            rv = new RemoteViews(context.getPackageName(), R.layout.birthday_list_widget_item_highlighted);
        }
        else {
            rv = new RemoteViews(context.getPackageName(), R.layout.birthday_list_widget_item);
        }

        rv.setTextViewText(R.id.widget_item_name, c.fullName);
        rv.setTextViewText(R.id.widget_item_date, dateFormat.format(c.birthday.getDate()));
        rv.setTextViewText(
                R.id.widget_item_age,
                Long.toString(c.birthday.ageOnNextBirthday(today)));


        // set the fill event on each text view individually
        // since it seems we cannot apply it to the entire layout
        Intent launchIntent = new Intent(context, BirthdaysActivity.class);
        rv.setOnClickFillInIntent(R.id.widget_item_name, launchIntent);
        rv.setOnClickFillInIntent(R.id.widget_item_date, launchIntent);
        rv.setOnClickFillInIntent(R.id.widget_item_age, launchIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Cursor getContactsBirthdays() {
        Uri uri = ContactsContract.Data.CONTENT_URI;

        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Event.START_DATE
        };

        String where =
                ContactsContract.Data.MIMETYPE + "= ? AND " +
                        ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                        ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;
        String[] selectionArgs = new String[] {
                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
        };
        return context.getContentResolver().query(uri, projection, where, selectionArgs, null);
    }

    private void populateBirthdays() {
        Cursor cursor = getContactsBirthdays();
        int bDayColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE);
        int nameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

        // fetch a new list of contacts
        List<Contact> contacts = new ArrayList<>();
        while (cursor.moveToNext()) {

            Contact c = new Contact(cursor.getString(nameColumn));

            try
            {
                c.birthday = new Birthday(cursor.getString(bDayColumn));
            }
            catch (DateTimeParseException e)
            {
                continue;
            }

            contacts.add(c);
        }

        // sort the array in the order of next birthdays
        Collections.sort(contacts, new SortContactsByNextBirthday(LocalDate.now()));

        // keep only the first n entries
        // since subList returns only a view, we copy the relevant entries here
        this.contacts = new ArrayList<>(contacts.subList(0, min(contacts.size(), maxEntries)));
    }
}
