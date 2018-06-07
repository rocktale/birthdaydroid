package de.rocktale.birthdaydroid;

import android.Manifest;
import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.rocktale.birthdaydroid.model.Birthday;
import de.rocktale.birthdaydroid.model.Contact;
import de.rocktale.birthdaydroid.model.SortContactsByNextBirthday;
import de.rocktale.birthdaydroid.ui.BirthdaysAdapter;
import de.rocktale.birthdaydroid.widget.BirthdayListWidget;

public class BirthdaysActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private BirthdaysAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout refreshLayout;

    private static final String TAG = "BirthdaysActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthdays);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.birthdayList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.ItemDecoration deviderItemDecoration =
                new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(deviderItemDecoration);

        // set the adapter on the recycler view
        mAdapter = new BirthdaysAdapter();
        mRecyclerView.setAdapter(mAdapter);

        refreshLayout = findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(this);

        refreshLayout.post(new Runnable() {

            @Override
            public void run() {

                refreshLayout.setRefreshing(true);

                // Fetching data from server
                requestContacts();
            }
        });
    }


    // Request code for READ_CONTACTS
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private void requestContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            showBirthdays();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showBirthdays();
            } else {
                Log.e("Permissions", "Access denied");
            }
        }
    }


    // method to get name, contact id, and birthday
    private Cursor getContactsBirthdays() {
        Uri uri = ContactsContract.Data.CONTENT_URI;

        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Event.START_DATE,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        };

        String where =
                ContactsContract.Data.MIMETYPE + "= ? AND " +
                        ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                        ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;
        String[] selectionArgs = new String[] {
                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
        };
        return getContentResolver().query(uri, projection, where, selectionArgs, null);
    }

    private void showBirthdays() {
        Cursor cursor = getContactsBirthdays();
        int bDayColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE);
        int nameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        int idColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);

        List<Contact> contacts = new ArrayList<>(cursor.getCount());

        while (cursor.moveToNext()) {

            Contact c = new Contact(cursor.getString(nameColumn));

            try
            {
                c.birthday = new Birthday(cursor.getString(bDayColumn));
            }
            catch (DateTimeParseException e)
            {
                Log.e(TAG, "Failed to parse birthday for contact '" + c.fullName + "': " + cursor.getString(bDayColumn));
                continue;
            }

            long contactId = cursor.getLong(idColumn);
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
            c.profilePicture = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);
            contacts.add(c);
        }

        // sort the array in the order of next birthdays
        Collections.sort(contacts, new SortContactsByNextBirthday(LocalDate.now()));

        // update the data
        mAdapter.setContacts(contacts);
        mAdapter.notifyDataSetChanged();

        refreshLayout.setRefreshing(false);
    }

    public void updateWidgets() {
        Application thisApp = getApplication();
        ComponentName widgetComponent = new ComponentName(thisApp, BirthdayListWidget.class);
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(thisApp);
        int[] ids = widgetManager.getAppWidgetI‌​ds(widgetComponent);
        Log.d(TAG, "Updating widgets: " + Arrays.toString(ids));

        widgetManager.notifyAppWidgetViewDataChanged(ids, R.id.birthday_list_widget);
    }

    // ActionMenu related stuff

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                refreshLayout.setRefreshing(true);
                requestContacts();
                updateWidgets();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        requestContacts();
        updateWidgets();
    }
}
